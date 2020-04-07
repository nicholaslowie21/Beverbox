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
public class RenewSubReq {
    
    private String email;
    private String password;
    private String promoCode;
    private boolean cashback;
    private long subsId;
    private long custId;

    public RenewSubReq() {
    }

    public RenewSubReq(String email, String password, String promoCode, boolean cashback, long subsId, long custId) {
        this.email = email;
        this.password = password;
        this.promoCode = promoCode;
        this.cashback = cashback;
        this.subsId = subsId;
        this.custId = custId;
    }

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public long getSubsId() {
        return subsId;
    }

    public void setSubsId(long subsId) {
        this.subsId = subsId;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }
    
    
}
