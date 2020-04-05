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
public class SubTransaction {
    
    private String ccNum;
    private Integer cvv;
    private Date transDate;
    private Double transAmt;
    private Long transId;
    private Long subsId;
    private Date startDate;
    private Date endDate;
    private Integer monthDuration;
    private boolean active;

    public SubTransaction() {
    }
    
    public SubTransaction(Transaction trans) {
        this.transId = trans.getTransactionId();
        this.ccNum = trans.getCcNum();
        this.cvv = trans.getCvv();
        this.transDate = trans.getTransDate();
        this.transAmt = trans.getTransactionAmt();
        this.subsId = trans.getSubscription().getSubscriptionId();
        this.startDate = trans.getSubscription().getStartDate();
        this.endDate = trans.getSubscription().getEndDate();
        this.monthDuration = trans.getSubscription().getOption().getDuration();
        this.active = trans.getSubscription().getActive();
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

    public Long getSubsId() {
        return subsId;
    }

    public void setSubsId(Long subsId) {
        this.subsId = subsId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getMonthDuration() {
        return monthDuration;
    }

    public void setMonthDuration(Integer monthDuration) {
        this.monthDuration = monthDuration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    

}
