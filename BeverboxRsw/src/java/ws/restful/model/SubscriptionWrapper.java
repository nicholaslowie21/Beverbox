package ws.restful.model;

import entity.Subscription;
import java.util.Date;


public class SubscriptionWrapper {
    
    private long subscriptionId;
    private Date startDate;
    private Date endDate;
    private Boolean active;
    private Long optionId;
    private String name;
    private Boolean sharing;

    public SubscriptionWrapper() {
    }
    
    public SubscriptionWrapper(Subscription s) {
        this.subscriptionId = s.getSubscriptionId();
        this.startDate = s.getStartDate();
        this.endDate = s.getEndDate();
        this.active = s.getActive();
        this.optionId = s.getOption().getOptionId();
        this.name = s.getOption().getName();
        this.sharing = s.getOption().getSharing();
    }
    

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSharing() {
        return sharing;
    }

    public void setSharing(Boolean sharing) {
        this.sharing = sharing;
    }
    
}
