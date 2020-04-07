/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author User
 */
public class CreateSubReq {
    
    private String promoCode;
    private boolean cashback;
    private long optId;
    private long custId;

    public CreateSubReq() {
    }

    public CreateSubReq(String promoCode, boolean cashback, long optId, long custId) {
        this.promoCode = promoCode;
        this.cashback = cashback;
        this.optId = optId;
        this.custId = custId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public boolean isCashback() {
        return cashback;
    }

    public void setCashback(boolean cashback) {
        this.cashback = cashback;
    }

    public long getOptId() {
        return optId;
    }

    public void setOptId(long optId) {
        this.optId = optId;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }
    
    
}
