package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CreateNewCustomerException;
import util.exception.CustomerNotFoundException;

/**
 *
 * @author zhixuan
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;

    public CustomerSessionBean() {
    }

    @Override
    public Long createNewCustomer(Customer newCustomer) throws CreateNewCustomerException {
        try {
            em.persist(newCustomer);
            em.flush();
            
            return newCustomer.getCustomerId();
        }
        catch (PersistenceException ex) {
            throw new CreateNewCustomerException();
        }
    }
    
    @Override
    public List<Customer> retrieveAllCustomers() {
        Query query = em.createQuery("SELECT c FROM Customer c ORDER BY c.customerName ASC");
        
        return query.getResultList();
    }
    
    @Override
    public Customer retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        
        if (customer != null) {
            return customer;
        } else {
            throw new CustomerNotFoundException();
        }
    }
    
    @Override
    public Customer retrieveCustomerByCustomerName(String customerName) throws CustomerNotFoundException {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.customerName = :inCustomerName");
        query.setParameter("inCustomerName", customerName);
        try
        {
            return (Customer)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CustomerNotFoundException("Customer with name " + customerName + " does not exist!");
        }
    }
    
    @Override
    public void updateCustomer(Customer customer) throws CustomerNotFoundException {
        if(customer != null)
        {
            Customer customerToUpdate = retrieveCustomerByCustomerId(customer.getCustomerId());

            customerToUpdate.setCustomerName(customer.getCustomerName());
            customerToUpdate.setCustomerEmail(customer.getCustomerEmail());
            customerToUpdate.setCustomerPassword(customer.getCustomerPassword());
        }
        else
        {
            throw new CustomerNotFoundException("Customer ID not provided for customer to be updated");
        }
    }
    
}
