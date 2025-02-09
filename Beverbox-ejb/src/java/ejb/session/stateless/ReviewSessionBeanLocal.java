package ejb.session.stateless;

import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.BoxNotFoundException;
import util.exception.CreateNewReviewException;
import util.exception.CustomerNotFoundException;
import util.exception.DeleteReviewException;
import util.exception.ReviewNotFoundException;

/**
 *
 * @author vanes
 */
@Local
public interface ReviewSessionBeanLocal {

    public Long createNewReview(Review newReview, Long boxId, Long customerId) throws CustomerNotFoundException, BoxNotFoundException, CreateNewReviewException;

    public List<Review> retrieveAllReviewsByCustomerEmail(String email) throws CustomerNotFoundException;

    public List<Review> retrieveAllReviewsByBoxId(Long boxId) throws BoxNotFoundException;

    public List<Review> retrieveAllReviews();

    public void deleteReview(Long reviewId, String email) throws ReviewNotFoundException, DeleteReviewException;

}
