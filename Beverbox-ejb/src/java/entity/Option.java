package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;
    @Column(nullable = true, length = 64)
    @Size(max = 64)
    private String name;
    @Column(nullable = false)
    @NotNull
    private Integer duration;
    @NotNull
    private Boolean sharing;
    @Column(nullable = true, length = 256)
    @Size(max = 256)
    private String description;
    @NotNull
    private Float price;
    
    @OneToMany (mappedBy="Subscription")
    private List<Subscription> subscriptions;

    public Option() {
        subscriptions = new ArrayList<>();
    }

    public Option(String name, Integer duration, Boolean sharing, String description, Float price) {
        this();
        
        this.name = name;
        this.duration = duration;
        this.sharing = sharing;
        this.description = description;
        this.price = price;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optionId != null ? optionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the optionId fields are not set
        if (!(object instanceof Option)) {
            return false;
        }
        Option other = (Option) object;
        if ((this.optionId == null && other.optionId != null) || (this.optionId != null && !this.optionId.equals(other.optionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Option[ id=" + optionId + " ]";
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

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    
}
