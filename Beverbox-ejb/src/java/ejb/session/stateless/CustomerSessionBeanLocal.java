package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewCustomerException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

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

    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException;

    public Customer customerLogin(String email, String password) throws CustomerNotFoundException, InvalidLoginCredentialException;
}
