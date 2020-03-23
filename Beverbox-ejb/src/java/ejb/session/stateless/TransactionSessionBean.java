/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Beverage;
import entity.Customer;
import entity.Promotion;
import entity.Transaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.PromoCodeNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author User
 */
@Stateless
public class TransactionSessionBean implements TransactionSessionBeanLocal {

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public TransactionSessionBean(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
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
    
    public List<Transaction> retrieveAllTransaction(){
        Query query = em.createQuery("SELECT t FROM Transaction t");
        
        List<Transaction> theList = query.getResultList();
        
        return theList;
    }
    
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
    
    public long createBevTransaction(Beverage bev, String promoCode, Integer qty, boolean useCashBack, Customer cust) throws PromoCodeNotFoundException{
        Promotion thePromo = null;
        Double newCashBack; // to add into wallet
        
        Double totalamt = bev.getPrice()*qty;
        
        if(useCashBack){
            Double theCashBackNow = cust.getAccumulatedCashback();
            if(theCashBackNow>=totalamt){
                cust.setAccumulatedCashback(cust.getAccumulatedCashback()-totalamt);
                totalamt = 0.0;
            }else{
                totalamt-=cust.getAccumulatedCashback();
                cust.setAccumulatedCashback(0.0);
            }
        }
        
        if( !promoCode.equals("")){
            thePromo = promotionSessionBean.retrievePromotionByPromoCode(promoCode);
            if(thePromo!=null){
                newCashBack = totalamt * thePromo.getPromoPercentage()/100;
                cust.setAccumulatedCashback(cust.getAccumulatedCashback()+newCashBack);
            } else {
                throw new PromoCodeNotFoundException("Promotion not found!");
            }
        }
        
        Transaction newTrans = new Transaction(cust.getCustomerCCNum(), totalamt, cust.getCustomerCVV(), new Date());
        newTrans.setBevNumber(qty);
        
        newTrans.setBeverage(bev);
        bev.setTransaction(newTrans);
        
        newTrans.setCustomer(cust);
        cust.getTransactions().add(newTrans);
        
        if(thePromo!=null) {
            newTrans.setPromotion(thePromo);
            thePromo.getTransactions().add(newTrans);
        }
        
        em.persist(newTrans);
        em.flush();
        
        return newTrans.getTransactionId();
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
}