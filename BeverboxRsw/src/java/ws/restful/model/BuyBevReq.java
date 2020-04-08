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
public class BuyBevReq {
    
    private String email;
    private String password;
    private String promoCode;
    private boolean cashback;
    private long bevId;
    private long custId;
    private int qty;

    public BuyBevReq() {
    }

    public BuyBevReq(String email, String password, String promoCode, boolean cashback, long bevId, long custId, int qty) {
        this.email = email;
        this.password = password;
        this.promoCode = promoCode;
        this.cashback = cashback;
        this.bevId = bevId;
        this.custId = custId;
        this.qty = qty;
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

    public long getBevId() {
        return bevId;
    }

    public void setBevId(long bevId) {
        this.bevId = bevId;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
    
}
