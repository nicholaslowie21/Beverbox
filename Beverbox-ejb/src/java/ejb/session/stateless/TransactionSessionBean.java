/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Beverage;
import entity.Customer;
import entity.OptionEntity;
import entity.Promotion;
import entity.Subscription;
import entity.Transaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.BevTransactionLimitException;
import util.exception.BeverageNotFoundException;
import util.exception.CreateNewSubscriptionException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidPromotionException;
import util.exception.OptionNotFoundException;
import util.exception.PromoCodeNotFoundException;
import util.exception.QuantityLimitException;
import util.exception.QuantityNotEnoughException;
import util.exception.TransactionNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author User
 */
@Stateless
public class TransactionSessionBean implements TransactionSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBean;

    @EJB
    private BeverageSessionBeanLocal beverageSessionBean;

    @EJB
    private SubscriptionSessionBeanLocal subscriptionSessionBean;

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;
    
    
    
    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    @Resource
    private EJBContext eJBContext;
    
    
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public TransactionSessionBean(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public long createNewTransaction(Transaction newTransaction) throws UnknownPersistenceException, InputDataValidationException{
        try
        {
            Set<ConstraintViolation<Transaction>>constraintViolations = validator.validate(newTransaction);
            
            if(constraintViolations.isEmpty())
            {
                em.persist(newTransaction);
                em.flush();
                
                return newTransaction.getTransactionId();
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(javax.persistence.PersistenceException ex)
        {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
    
    @Override
    public Transaction retrieveTransactionByTransactionId(long id) throws TransactionNotFoundException{
        Transaction temp =  em.find(Transaction.class, id);
        if(temp != null){
            return temp;
        } else {
            throw new TransactionNotFoundException();
        }
    }
    
    
    @Override
    public List<Transaction> retrieveAllTransaction(){
        Query query = em.createQuery("SELECT t FROM Transaction t");
        
        List<Transaction> theList = query.getResultList();
        
        return theList;
    }
    
    @Override
    public List<Transaction> retrieveCustBeverageTrans(Customer customer){
        long custId = customer.getCustomerId();
        
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.customer.customerId = :inId")
                .setParameter("inId", custId);
        
        List<Transaction> temp = query.getResultList();
        
        List<Transaction> theList = new ArrayList<>();
        
        for(Transaction t: temp){
            if(t.getBeverage()!=null){
                theList.add(t);
            }
        }
        
        return theList;
    }
    
    @Override
    public List<Transaction> retrieveCustSubscriptionTrans(Customer customer){
        long custId = customer.getCustomerId();
        
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.customer.customerId = :inId")
                .setParameter("inId", custId);
        
        List<Transaction> temp = query.getResultList();
        
        List<Transaction> theList = new ArrayList<>();
        
        for(Transaction t: temp){
            if(t.getSubscription()!=null){
                theList.add(t);
            }
        }
        
        return theList;
    }
    
    public List<Transaction> retrieveCustTransaction(long custId){
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.customer.customerId = :inId")
                .setParameter("inId", custId);
        
        List<Transaction> temp = query.getResultList();
        
        return temp;
    }
    
    @Override
    public long createBevTransaction(long bevId, String promoCode, Integer qty, boolean useCashBack, long custId) throws InvalidPromotionException,BevTransactionLimitException,QuantityLimitException, PromoCodeNotFoundException, QuantityNotEnoughException, BeverageNotFoundException, CustomerNotFoundException{
        Promotion thePromo = null;
        Double newCashBack; // to add into wallet
        
        Beverage bev = beverageSessionBean.retrieveBeverageByBeverageId(bevId);
        Customer cust = customerSessionBean.retrieveCustomerByCustomerId(custId);
        
        System.out.println("cust nya pn "+cust.getAccumulatedCashback());
        
        Double totalamt = bev.getPrice()*qty;
        double totalamtCopy = new Double(totalamt);
        
        
        if(qty>bev.getQuantityOnHand()){
            throw new QuantityNotEnoughException("There is no enough stock for the ordered quantity");
        }
        
        if(qty>bev.getMaxPurchase()){
            throw new QuantityLimitException("The quantity ordered is more than the limit");
        }
        
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.customer.customerId = :inId and t.beverage IS NOT NULL ORDER BY t.transDate")
                .setParameter("inId", custId);
        
        List<Transaction> transactionsHistory = query.getResultList();
        
        if(transactionsHistory.size()!=0){
            long timeNow = new Date().getTime();
            long subsDate = transactionsHistory.get(0).getTransDate().getTime();
        
            long diff = timeNow - subsDate;
            if(diff>=0 && diff < 7*(24 * 60 * 60 * 1000)){
                throw new BevTransactionLimitException("Only 1 beverage transaction per week is allowed!");
            }
        }   
            
        if(useCashBack){
            Double theCashBackNow = cust.getAccumulatedCashback();
            if(theCashBackNow>=totalamt){
                cust.setAccumulatedCashback(theCashBackNow-totalamt);
                totalamt = 0.0;
            }else{
                totalamt-=cust.getAccumulatedCashback();
                cust.setAccumulatedCashback(0.0);
            }
        }
        
        if( !promoCode.equals("")){
            thePromo = promotionSessionBean.retrievePromotionByPromoCode(promoCode);
            
            if(thePromo.getPromoType().equals("NEW MEMBER")){
                if(retrieveCustTransaction(cust.getCustomerId()).size()!=0){
                    throw new InvalidPromotionException("Sorry, this promo code is invalid for you");
                }
            }
            
            if(thePromo!=null){
                newCashBack = totalamtCopy * thePromo.getPromoPercentage()/100;
                cust.setAccumulatedCashback(cust.getAccumulatedCashback()+newCashBack);
            } else {
                throw new PromoCodeNotFoundException("Promotion not found!");
            }
        }
        
        
        Transaction newTrans = new Transaction(cust.getCustomerCCNum(), totalamt, cust.getCustomerCVV(), new Date());
        newTrans.setBevNumber(qty);
        
        newTrans.setBeverage(bev);
        bev.getTransactions().add(newTrans);
        
        newTrans.setCustomer(cust);
        cust.getTransactions().add(newTrans);
        
        if(thePromo!=null) {
            newTrans.setPromotion(thePromo);
            thePromo.getTransactions().add(newTrans);
        }
        
        bev.setQuantityOnHand(bev.getQuantityOnHand()-qty);
        
        em.persist(newTrans);
        em.flush();
        
        return newTrans.getTransactionId();
    }
    
    public long renewSubscriptionTransaction(Subscription subs){
//        Customer customer = subs.getCustomer();
//        OptionEntity option = subs.getOption();
//        
//        long transId = 0;
//        try{ 
//            transId = createNewTransaction(new Transaction(customer.getCustomerCCNum(), option.getPrice(), customer.getCustomerCVV(), new Date()));
//            em.flush();
//            
//            Transaction tempTrans = em.find(Transaction.class, transId);
//            tempTrans.setCustomer(customer);
//            customer.getTransactions().add(tempTrans);
//            
//            int tempEndYear = subs.getEndDate().getYear();
//            int tempEndMonth = subs.getEndDate().getMonth();
//            int tempEndDate = subs.getEndDate().getDate();
//            int tempMonthSubs = subs.getOption().getDuration();
//            
//            tempEndMonth += tempMonthSubs;
//            if(tempEndMonth > 12){
//                tempEndMonth -=12;
//                tempEndYear +=1;
//            }
//            
//           // long subsId = subscriptionSessionBean.createNewSubscription(new Subscription(subs.getEndDate(),new Date(tempEndYear, tempEndMonth, tempEndDate) , tempMonthSubs),option.getOptionId(), customer.getCustomerId(), transId);
//            
//            
//        }catch( TransactionNotFoundException | CustomerNotFoundException | CreateNewSubscriptionException | OptionNotFoundException | InputDataValidationException | UnknownPersistenceException ex){
//            eJBContext.setRollbackOnly();
//        }
//        
        return 1l;
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Transaction>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }    

    public void persist(Object object) {
        em.persist(object);
    }
}
