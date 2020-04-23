package ws.restful.model;

/**
 *
 * @author zhixuan
 */
public class CreateNewCustomerRsp {
    
    private Long newCustomerId;

    public CreateNewCustomerRsp(Long newCustomerId) {
        this.newCustomerId = newCustomerId;
    }

    public Long getNewCustomerId() {
        return newCustomerId;
    }

    public void setNewCustomerId(Long newCustomerId) {
        this.newCustomerId = newCustomerId;
    }
    
    
    
}
