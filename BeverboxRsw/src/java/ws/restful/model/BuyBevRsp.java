/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author boonghim
 */
public class BuyBevRsp {
    
    private Long bevTransactionId;
    
    public BuyBevRsp() {
    }

    public BuyBevRsp(Long bevTransactionId) {
        this.bevTransactionId = bevTransactionId;
    }

    public Long getBevTransactionId() {
        return bevTransactionId;
    }

    public void setBevTransactionId(Long bevTransactionId) {
        this.bevTransactionId = bevTransactionId;
    }
    
    
    
}
