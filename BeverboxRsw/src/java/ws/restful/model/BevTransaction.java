/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Transaction;
import java.util.Date;

/**
 *
 * @author User
 */
public class BevTransaction {
    
    private Integer qty;
    private String ccNum;
    private Integer cvv;
    private Date transDate;
    private Double transAmt;
    private Long transId;
    private String bevName;
    
    public BevTransaction() {
    }
    
    public BevTransaction(Transaction trans) {
        this.transId = trans.getTransactionId();
        this.qty = trans.getBevNumber();
        this.ccNum = trans.getCcNum();
        this.cvv = trans.getCvv();
        this.transDate = trans.getTransDate();
        this.transAmt = trans.getTransactionAmt();
        this.bevName = trans.getBeverage().getBeverageName();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCcNum() {
        return ccNum;
    }

    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Double getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(Double transAmt) {
        this.transAmt = transAmt;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public String getBevName() {
        return bevName;
    }

    public void setBevName(String bevName) {
        this.bevName = bevName;
    }
    
    
    
}
