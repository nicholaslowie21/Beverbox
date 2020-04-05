package ws.restful.model;

import entity.Review;

/**
 *
 * @author vanes
 */
public class CreateNewReviewReq {
    
    
    private Review review;
    private Long boxId;
    private Long customerId;

    
    public CreateNewReviewReq() {
    }

    public CreateNewReviewReq(Review review, Long boxId, Long customerId) {
        this.review = review;
        this.boxId = boxId;
        this.customerId = customerId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    
    
}
