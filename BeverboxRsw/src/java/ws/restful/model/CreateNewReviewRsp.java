package ws.restful.model;

/**
 *
 * @author vanes
 */
public class CreateNewReviewRsp {
    
    private Long reviewId;

    
    public CreateNewReviewRsp() {
    }

    public CreateNewReviewRsp(Long reviewId) {
        this.reviewId = reviewId;
    }
    
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
    
    
    
}
