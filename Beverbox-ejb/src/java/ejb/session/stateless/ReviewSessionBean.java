package ejb.session.stateless;

import entity.Box;
import entity.Customer;
import entity.Review;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.BoxNotFoundException;
import util.exception.CreateNewReviewException;
import util.exception.CustomerNotFoundException;

/**
 *
 * @author vanes
 */
@Stateless
public class ReviewSessionBean implements ReviewSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB
    private BoxSessionBeanLocal boxSessionBeanLocal;
    
    
    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewReview(String reviewContent, Long boxId, Long customerId) throws CustomerNotFoundException, BoxNotFoundException, CreateNewReviewException 
    {
        
        try 
        {
            if (boxId == null) 
            {
                throw new BoxNotFoundException();
            }
            if(customerId == null) 
            {
                throw new CustomerNotFoundException();
            }
            
            Review newReview = new Review(reviewContent);
            em.persist(newReview);
            
            Box box = boxSessionBeanLocal.retrieveBoxByBoxId(boxId);
            newReview.setBox(box);
            //box.getReviews().add(newReview);
            
//            Customer customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(customerId);
//            newReview.setCustomer(customer);
//            customer.getReviews().add(newReview);
            
            em.flush();
            return newReview.getReviewId();
        }
        catch (PersistenceException ex) 
        {
            throw new CreateNewReviewException();
        }
        catch (BoxNotFoundException | CustomerNotFoundException ex) 
        {
            throw new CreateNewReviewException("An error occurred while creating the new review: " + ex.getMessage());
        }
    }
    
    
    @Override
    public List<Review> retrieveAllReviewsByCustomerId(Long customerId) throws CustomerNotFoundException 
    {
        if (customerId == null) 
        {
            throw new CustomerNotFoundException();
        }
        Query query = em.createQuery("SELECT r FROM Review r WHERE r.customer.customerId = :inCustomerId ORDER BY r.reviewId DESC");
        query.setParameter("inCustomerId", customerId);
        return query.getResultList();
    }
    
    
    @Override
    public List<Review> retrieveAllReviewsByBoxId(Long boxId) throws BoxNotFoundException 
    {
        if (boxId == null) 
        {
            throw new BoxNotFoundException();
        }
        Query query = em.createQuery("SELECT r FROM Review r WHERE r.box.boxId = :inBoxId ORDER BY r.reviewId DESC");
        query.setParameter("inBoxId", boxId);
        return query.getResultList();
    }
}
