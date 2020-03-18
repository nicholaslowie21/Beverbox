package ejb.session.stateless;

import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.BoxNotFoundException;
import util.exception.CreateNewReviewException;
import util.exception.CustomerNotFoundException;

/**
 *
 * @author vanes
 */
@Local
public interface ReviewSessionBeanLocal {

    public Long createNewReview(String reviewContent, Long boxId, Long customerId) throws CustomerNotFoundException, BoxNotFoundException, CreateNewReviewException;

    public List<Review> retrieveAllReviewsByCustomerId(Long customerId) throws CustomerNotFoundException;

    public List<Review> retrieveAllReviewsByBoxId(Long boxId) throws BoxNotFoundException;
    
}
