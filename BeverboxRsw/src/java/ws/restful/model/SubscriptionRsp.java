/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Subscription;
import java.util.Date;

/**
 *
 * @author User
 */
public class SubscriptionRsp {
    private long subsId;
    private Date startDate;
    private Date endDate;
    private boolean subActive;
    private long optionId;
    private String optionName;
    private int duration;
    private boolean sharing;
    private String description;
    private Double price;
    private boolean optActive;
    private String type;

    public SubscriptionRsp() {
    }
    
    public SubscriptionRsp(Subscription subs){
        this.subsId = subs.getSubscriptionId();
        this.startDate = subs.getStartDate();
        this.endDate = subs.getEndDate();
        this.subActive = subs.getActive();
        this.optionId = subs.getOption().getOptionId();
        this.optionName = subs.getOption().getName();
        this.duration = subs.getOption().getDuration();
        this.sharing = subs.getOption().getSharing();
        this.description = subs.getOption().getDescription();
        this.price = subs.getOption().getPrice();
        this.optActive = subs.getOption().getActive();
        this.type = subs.getOption().getType();
    }
    
    public long getSubsId() {
        return subsId;
    }

    public void setSubsId(long subsId) {
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

    public boolean isSubActive() {
        return subActive;
    }

    public void setSubActive(boolean subActive) {
        this.subActive = subActive;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isSharing() {
        return sharing;
    }

    public void setSharing(boolean sharing) {
        this.sharing = sharing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isOptActive() {
        return optActive;
    }

    public void setOptActive(boolean optActive) {
        this.optActive = optActive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
