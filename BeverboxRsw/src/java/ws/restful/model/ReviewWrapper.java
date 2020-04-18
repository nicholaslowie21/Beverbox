package ws.restful.model;

import entity.Review;

public class ReviewWrapper {

    private Long reviewId;
    private String reviewContent;
    private Integer reviewRating;
    private Long boxId;
    private String boxName;
    private Long customerId;
    private String customerName;
    private String customerEmail;
    
    
    public ReviewWrapper(Review r) 
    {
        this.reviewId = r.getReviewId();
        this.reviewContent = r.getReviewContent();
        this.reviewRating = r.getReviewRating();
        this.boxId = r.getBox().getBoxId();
        this.boxName = r.getBox().getBoxName();
        this.customerId = r.getCustomer().getCustomerId();
        this.customerName = r.getCustomer().getCustomerName();
        this.customerEmail = r.getCustomer().getCustomerEmail();
    }
    

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(Integer reviewRating) {
        this.reviewRating = reviewRating;
    }

}
