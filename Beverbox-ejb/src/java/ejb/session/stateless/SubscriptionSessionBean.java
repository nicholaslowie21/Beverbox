package ejb.session.stateless;

import entity.Customer;
import entity.Option;
import entity.Subscription;
import entity.Transaction;
import java.time.LocalDate;
import java.time.Period;
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
import util.exception.SubscriptionNotFoundException;
import util.exception.UnknownPersistenceException;

@Stateless
public class SubscriptionSessionBean implements SubscriptionSessionBeanLocal {

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
    public Long createNewSubscription(Subscription newSubscription, Long optionId, Long customerId, Long transactionId) throws CreateNewSubscriptionException, OptionNotFoundException, CustomerNotFoundException, InputDataValidationException
    {
        Set<ConstraintViolation<Subscription>>constraintViolations = validator.validate(newSubscription);
        
        if(constraintViolations.isEmpty())
        {  
        
            try {

                if (optionId == null) {
                    throw new OptionNotFoundException();
                }

                Option option = optionSessionBeanLocal.retrieveOptionByOptionId(optionId);

                if (customerId == null) {
                    throw new CustomerNotFoundException();
                }

                Customer customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(customerId);

    //            Nicholas needs to implemeent a retrieveTransactionByTransactionId, with its corresponding exception as well
    //            if (transactionId == null) {
    //                throw new TransactionNotFoundException();
    //            }
    //            Transaction transaction = transactionSessionBeanLocal.retrieveTransactionByTransactionId(transactionId);

                em.persist(newSubscription);
                newSubscription.setOption(option);
                newSubscription.setCustomer(customer);
    //            ZhiXuan needs to associate Customer with subscription entities TT
    //            customer.getSubscriptionEntities().add(newSubscription);
    //            newSubscription.setTransaction(transaction);
    //            transaction.setSubscription(newSubscription);

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
        Query query = em.createQuery("SELECT s FROM Subscriptions s ORDER BY s.name ASC");        
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
    public List<Subscription> retrieveAllSubscriptionsByCustomerId(Long customerId) throws CustomerNotFoundException, SubscriptionNotFoundException
    {
        try {
            if (customerId == null) {
                throw new CustomerNotFoundException();
            }
            
            Query query = em.createQuery("SELECT s FROM Subscription s WHERE s.customer = :inCustomer ORDER BY s.subscriptionId ASC");
        
            query.setParameter("inCustomer", customerId);
            List<Subscription> subscriptions = query.getResultList();

            for(Subscription subscription:subscriptions)
            {
                subscription.getCustomer();
                subscription.getOption();
                subscription.getTransaction();
            }

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

            for(Subscription subscription:subscriptions)
            {
                subscription.getCustomer();
                subscription.getOption();
                subscription.getTransaction();
            }

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
            subscription.getCustomer();
            subscription.getOption();
            subscription.getTransaction();
            
            return subscription;
        }
        else
        {
            throw new SubscriptionNotFoundException("Subscription ID " + subscriptionId + " does not exist!");
        }               
    }
    
    public Long renewSubscription(Long subscriptionId) throws SubscriptionNotFoundException, CreateNewSubscriptionException {
        
//        try{
//            Subscription oldSubscription = retrieveSubscriptionBySubscriptionId(subscriptionId);
//        
//            Date startDate = oldSubscription.getStartDate();
//            Date endDate = oldSubscription.getEndDate();
//            
//            int startMonth = oldSubscription.getStartDate().getMonth();
//            int startYear = oldSubscription.getStartDate().getYear();
//            int endMonth = oldSubscription.getEndDate().getMonth();
//            int endYear = oldSubscription.getEndDate().getYear();
//            
//            Period monthDiff = Period.between(startDate, endDate);
//            
//            if (startYear == endYear) {
//                int duration = endMonth - startMonth;
//                
//            } else {
//                
//            }
//        } catch (SubscriptionNotFoundException ex){
//            throw new CreateNewSubscriptionException("An error occured while renewing your subscription" + ex.getMessage());
//        }
//      Not completed, will finish up later
        return subscriptionId;
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
