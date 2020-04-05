package ws.restful.model;

import entity.Review;
import java.util.List;

/**
 *
 * @author vanes
 */
public class RetrieveAllReviewsRsp {
    
    
    private List<Review> reviews;

    
    public RetrieveAllReviewsRsp() {
    }

    public RetrieveAllReviewsRsp(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
}
