package ws.restful.model;

import entity.OptionEntity;
import entity.Subscription;
import java.util.List;

public class OptionWrapper {
    private Long optionId;
    private String name;
    private Integer duration;
    private Boolean sharing;
    private String description;
    private Double price;
    private Boolean active;
    private String type;
    private List<Subscription> subscriptions;
    private Double priceSharing;
    private Long sharingOptionId;

    public OptionWrapper(OptionEntity option) {
        this.optionId = option.getOptionId();
        this.name = option.getName();
        this.duration = option.getDuration();
        this.sharing = option.getSharing();
        this.description = option.getDescription();
        this.price = option.getPrice();
        this.active = option.getActive();
        this.type = option.getType();
        this.priceSharing = 0.00;
        this.sharingOptionId = 0l;
//        this.subscriptions = option.getSubscriptions();
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getSharing() {
        return sharing;
    }

    public void setSharing(Boolean sharing) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPriceSharing() {
        return priceSharing;
    }

    public void setPriceSharing(Double priceSharing) {
        this.priceSharing = priceSharing;
    }

    public Long getSharingOptionId() {
        return sharingOptionId;
    }

    public void setSharingOptionId(Long sharingOptionId) {
        this.sharingOptionId = sharingOptionId;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
