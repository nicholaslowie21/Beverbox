package ws.restful.model;

import entity.Customer;

/**
 *
 * @author zhixuan
 */
public class UpdateProfileReq {
    
    private Customer customer;

    public UpdateProfileReq() {
    }

    public UpdateProfileReq(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}
