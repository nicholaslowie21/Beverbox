package ejb.session.stateless;

import entity.Customer;
import entity.OptionEntity;
import entity.Promotion;
import entity.Subscription;
import entity.Transaction;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewSubscriptionException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OptionNotFoundException;
import util.exception.PromoCodeNotFoundException;
import util.exception.SubscriptionNotFoundException;
import util.exception.TransactionNotFoundException;
import util.exception.UnknownPersistenceException;

@Stateless
public class SubscriptionSessionBean implements SubscriptionSessionBeanLocal {

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

    @EJB(name = "TransactionSessionBeanLocal")
    private TransactionSessionBeanLocal transactionSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "OptionSessionBeanLocal")
    private OptionSessionBeanLocal optionSessionBeanLocal;
    
    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public SubscriptionSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewSubscription(Subscription newSubscription, Long optionId, Long customerId, String promoCode, Boolean cashback) throws CreateNewSubscriptionException, OptionNotFoundException, CustomerNotFoundException, InputDataValidationException, UnknownPersistenceException, TransactionNotFoundException, PromoCodeNotFoundException
    {
        Set<ConstraintViolation<Subscription>>constraintViolations = validator.validate(newSubscription);
        
        if(constraintViolations.isEmpty())
        {  
        
            try {

                if (optionId == null) {
                    throw new OptionNotFoundException();
                }

                OptionEntity option = optionSessionBeanLocal.retrieveOptionByOptionId(optionId);

                if (customerId == null) {
                    throw new CustomerNotFoundException();
                }

                Customer customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(customerId);
                
                Double totalPrice = option.getPrice();
                Double addLogs = 0.0;
                
                Promotion thePromo = new Promotion();
        
                if(!promoCode.equals("")){
                    Promotion promo = promotionSessionBean.retrievePromotionByPromoCode(promoCode);
                    addLogs = promo.getPromoPercentage()*1.0/100*totalPrice;
                    thePromo = promo;
                }
                Double currLogs = customer.getAccumulatedCashback();
                if(cashback){
                    if(totalPrice<=currLogs){
                        totalPrice = 0.0;
                        currLogs -= totalPrice;
                    } else {
                        totalPrice -= currLogs;
                        currLogs = 0.0;
                    }
                    customer.setAccumulatedCashback(currLogs);
                }

                if(!promoCode.equals("")){
                    customer.setAccumulatedCashback(customer.getAccumulatedCashback()+addLogs);
                }
        
                
                Long newTransId = transactionSessionBeanLocal.createNewTransaction(new Transaction(customer.getCustomerCCNum(), totalPrice, customer.getCustomerCVV(), new Date()));
                Transaction newTrans = transactionSessionBeanLocal.retrieveTransactionByTransactionId(newTransId);
                
                em.persist(newSubscription);
                newSubscription.setOption(option);
                option.getSubscriptions().add(newSubscription);
                
                if(!promoCode.equals("")){
                    newTrans.setPromotion(thePromo);
                    thePromo.getTransactions().add(newTrans);
                }
                
                newSubscription.setCustomer(customer);
                customer.getSubscriptions().add(newSubscription);
                
                newSubscription.setTransaction(newTrans);
                newTrans.setSubscription(newSubscription);
                
                newTrans.setCustomer(customer);
                customer.getTransactions().add(newTrans);
                
                em.flush();

                return newSubscription.getSubscriptionId();
            }
            catch(PersistenceException ex) {
                throw new CreateNewSubscriptionException();
            }
            catch(OptionNotFoundException | CustomerNotFoundException ex) {
                throw new CreateNewSubscriptionException("An error has occured when creating a new Subscription" + ex.getMessage());
            }
        
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public List<Subscription> retrieveAllSubscriptions()
    {
        Query query = em.createQuery("SELECT s FROM Subscription s");        
        List<Subscription> subscriptions = query.getResultList();
        
        for(Subscription subscription: subscriptions)
        {
            subscription.getCustomer();
            subscription.getOption();
            subscription.getTransaction();
        }
        
        return subscriptions;
    }
    
    @Override
    public List<Subscription> retrieveAllActiveSubscriptionsByCustomerId(Long customerId) throws CustomerNotFoundException, SubscriptionNotFoundException
    {
        try {
            if (customerId == null) {
                throw new CustomerNotFoundException();
            }
            
            Query query = em.createQuery("SELECT s FROM Subscription s WHERE s.customer = :inCustomer ORDER BY s.subscriptionId ASC");
        
            Customer c = customerSessionBeanLocal.retrieveCustomerByCustomerId(customerId);
            query.setParameter("inCustomer", c);
            List<Subscription> subscriptions = query.getResultList();

//            for(Subscription subscription:subscriptions)
//            {
//                subscription.getCustomer();
//                subscription.getOption();
//                subscription.getTransaction();
//            }

            return subscriptions;
        }
        catch (Exception ex) {
            throw new SubscriptionNotFoundException("Subscription does not exist: " + ex.getMessage());
        }
    }
    
    @Override
    public List<Subscription> retrieveAllSubscriptionsByOptionId(Long optionId) throws OptionNotFoundException, SubscriptionNotFoundException
    {
        try {
            if (optionId == null) {
                throw new OptionNotFoundException();
            }
            
            Query query = em.createQuery("SELECT s FROM Subscription s WHERE s.option = :inOption ORDER BY s.subscriptionId ASC");
        
            query.setParameter("inOption", optionId);
            List<Subscription> subscriptions = query.getResultList();

//            for(Subscription subscription:subscriptions)
//            {
//                subscription.getCustomer();
//                subscription.getOption();
//                subscription.getTransaction();
//            }

            return subscriptions;
        }
        catch (Exception ex) {
            throw new SubscriptionNotFoundException("Subscription does not exist: " + ex.getMessage());
        }
    }
    
    
    @Override
    public Subscription retrieveSubscriptionBySubscriptionId(Long subscriptionId) throws SubscriptionNotFoundException
    {
        Subscription subscription = em.find(Subscription.class, subscriptionId);
        
        if(subscription!= null)
        {
//            But actually if one to one, this is not necessary right
//            subscription.getCustomer();
//            subscription.getOption();
//            subscription.getTransaction();
            
            return subscription;
        }
        else
        {
            throw new SubscriptionNotFoundException("Subscription ID " + subscriptionId + " does not exist!");
        }               
    }
    

    @Override
    public void deleteSubscription (Subscription subscription) throws SubscriptionNotFoundException
    {
        if(subscription != null)
        {
            Subscription subscriptionToUpdate = retrieveSubscriptionBySubscriptionId(subscription.getSubscriptionId());
            subscriptionToUpdate.setActive(false);
            //If it is associated with another object, do we need to update the subscription obj associated there too?
        }
        else
        {
            throw new SubscriptionNotFoundException("Subscription ID not provided for option to be updated");
        }
    }

    @Override
    public Subscription renewSubscription(String promoCode, boolean cashback, long subsId, long custId) throws SubscriptionNotFoundException, CustomerNotFoundException, PromoCodeNotFoundException, CreateNewSubscriptionException, OptionNotFoundException, InputDataValidationException, UnknownPersistenceException, TransactionNotFoundException{
        Subscription subscription = em.find(Subscription.class,subsId);
        Customer customer = em.find(Customer.class,custId);
        
        if(subscription==null){
            throw new SubscriptionNotFoundException("Subscription is not found!");
        }
        
        if(customer==null){
            throw new CustomerNotFoundException("Customer is not found!");
        }
        
        
        Date newStartDate = new Date(subscription.getEndDate().getTime());
        Date newEndDate = addMonths(subscription.getEndDate(), subscription.getOption().getDuration());
        Long theId = createNewSubscription(new Subscription(newStartDate, newEndDate), subscription.getOption().getOptionId(), custId,promoCode,cashback);
        
        Subscription newSubs = retrieveSubscriptionBySubscriptionId(theId);
        
        return newSubs;
    }
    
    public static Date addMonths(Date date, int numMonths){
        date.setMonth((date.getMonth() + numMonths));
        return date;
     }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Subscription>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }    
}
