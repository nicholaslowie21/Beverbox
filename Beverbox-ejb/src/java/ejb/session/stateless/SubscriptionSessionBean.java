package ejb.session.stateless;

import entity.Option;
import entity.Subscription;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CreateNewSubscriptionException;
import util.exception.CustomerNotFoundException;
import util.exception.OptionNotFoundException;
import util.exception.SubscriptionNotFoundException;

@Stateless
public class SubscriptionSessionBean implements SubscriptionSessionBeanLocal {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "OptionSessionBeanLocal")
    private OptionSessionBeanLocal optionSessionBeanLocal;

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    //Need to inject Transaction Entity Session Bean as well
    

    public SubscriptionSessionBean() {
//        Initialise Validator if needed
    }

    //Need to associate with Transaction entity as well
    @Override
    public Long createNewSubscription(Subscription newSubscription, Long optionId, Long customerId) throws CreateNewSubscriptionException, OptionNotFoundException, CustomerNotFoundException
    {
        try {
            
            if (optionId == null) {
                throw new OptionNotFoundException();
            }
            
            Option option = optionSessionBeanLocal.retrieveOptionByOptionId(optionId);
            
            if (customerId == null) {
                throw new CustomerNotFoundException();
            }
//            Uncomment this when customer session bean has implemented CRUD methods and Transaction Session Bean as well
//            Customer customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(customerId);

//            if (transactionId == null) {
//                throw new TransactionNotFoundException();
//            }
//            Transaction transaction = transactionSessionBeanLocal.retrieveTransactionByTransactionId(transactionId);
            
            em.persist(newSubscription);
            newSubscription.setOption(option);
//            newSubscription.setCustomer(customer);
//            customer.add(newSubscription);
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
//            subscription.getTransaction();
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
    //            subscription.getTransaction();
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
    //            subscription.getTransaction();
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
//            subscription.getTransaction();
            
            return subscription;
        }
        else
        {
            throw new SubscriptionNotFoundException("Subscription ID " + subscriptionId + " does not exist!");
        }               
    }

//    I don't think I need an update function
    @Override
    public void updateSubscription (Subscription subscription) throws SubscriptionNotFoundException
    {
        if(subscription != null)
        {
            Subscription subscriptionToUpdate = retrieveSubscriptionBySubscriptionId(subscription.getSubscriptionId());

            subscriptionToUpdate.setStartDate(subscription.getStartDate());
            subscriptionToUpdate.setEndDate(subscription.getEndDate());
            //If it is associated with another object, do we need to update the subscription obj associated there too?
        }
        else
        {
            throw new SubscriptionNotFoundException("Subscription ID not provided for option to be updated");
        }
    }
    
    //    I don't think I need a delete function
    @Override
    public void deleteSubscription(Long subscriptionId) throws SubscriptionNotFoundException
    {
        try {
            if (subscriptionId == null) {
                throw new SubscriptionNotFoundException();
            }
            
            Subscription subscriptionToRemove = retrieveSubscriptionBySubscriptionId(subscriptionId);
            Option option = subscriptionToRemove.getOption();
            option.getSubscriptions().remove(subscriptionToRemove);
//            Customer customer = subscriptionToRemove.getCustomer();
//            customer.getSubscriptions().remove(subscriptionToRemove);
//            Transaction transaction = subscriptionToRemove.getTransaction();
//            transaction.setSubscription(null);         
        }
    
        catch(Exception ex)
        {
            throw new SubscriptionNotFoundException();
        }
    }
    
    
}
