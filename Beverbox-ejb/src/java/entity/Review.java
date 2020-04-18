package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 *
 * @author vanes
 */
@Entity
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @Column(nullable = false)
    private String reviewContent;
    
    @Column(nullable = false)
    @NotNull
    @Positive
    @Min(1)
    @Max(5)
    private Integer reviewRating;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Box box;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;
    
    
    public Review() 
    {
    }
    
    public Review(String reviewContent, Integer reviewRating) 
    {
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewId != null ? reviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the reviewId fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.reviewId == null && other.reviewId != null) || (this.reviewId != null && !this.reviewId.equals(other.reviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Review[ id=" + reviewId + " ]";
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Integer getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(Integer reviewRating) {
        this.reviewRating = reviewRating;
    }
    
}
