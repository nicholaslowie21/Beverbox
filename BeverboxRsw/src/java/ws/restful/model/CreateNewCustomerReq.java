package ws.restful.model;

import entity.Customer;

/**
 *
 * @author zhixuan
 */
public class CreateNewCustomerReq {
    
    private Customer newCustomer;

    public CreateNewCustomerReq() {
    }

    public CreateNewCustomerReq(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }
    
}
