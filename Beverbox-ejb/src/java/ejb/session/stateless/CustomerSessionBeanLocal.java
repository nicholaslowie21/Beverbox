package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewCustomerException;
import util.exception.CustomerNotFoundException;

/**
 *
 * @author zhixuan
 */
@Local
public interface CustomerSessionBeanLocal {
 
    public Long createNewCustomer(Customer newCustomer) throws CreateNewCustomerException;    
    public List<Customer> retrieveAllCustomers();
    public Customer retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException;
    public Customer retrieveCustomerByCustomerName(String customerName) throws CustomerNotFoundException;
    public void updateCustomer(Customer customer) throws CustomerNotFoundException;
}
