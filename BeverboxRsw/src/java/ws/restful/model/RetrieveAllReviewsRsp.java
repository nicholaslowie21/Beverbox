package ws.restful.model;

import entity.Review;
import java.util.List;

/**
 *
 * @author vanes
 */
public class RetrieveAllReviewsRsp {
    
    
    private List<ReviewWrapper> reviews;

    
    public RetrieveAllReviewsRsp() {
    }

    public RetrieveAllReviewsRsp(List<ReviewWrapper> reviews) {
        this.reviews = reviews;
    }
    
    
    public List<ReviewWrapper> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewWrapper> reviews) {
        this.reviews = reviews;
    }
    
}
