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
import util.exception.ReviewNotFoundException;

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
    public Long createNewReview(Review newReview, Long boxId, Long customerId) throws CustomerNotFoundException, BoxNotFoundException, CreateNewReviewException 
    {
        
        try 
        {
            if (boxId == null) 
            {
                throw new BoxNotFoundException("Box Id is null");
            }
            if(customerId == null) 
            {
                throw new CustomerNotFoundException("Customer Id is null");
            }
            
            em.persist(newReview);
            
            Box box = boxSessionBeanLocal.retrieveBoxByBoxId(boxId);
            newReview.setBox(box);
            box.getReviews().add(newReview);
            
            Customer customer = customerSessionBeanLocal.retrieveCustomerByCustomerId(customerId);
            newReview.setCustomer(customer);
            customer.getReviews().add(newReview);
            
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
    public List<Review> retrieveAllReviews() 
    {
        Query query = em.createQuery("SELECT r FROM Review r");
        return query.getResultList();
    }
    
    
    @Override
    public List<Review> retrieveAllReviewsByCustomerId(Long customerId) throws CustomerNotFoundException 
    {
        if (customerId == null) 
        {
            throw new CustomerNotFoundException("Customer Id is null!");
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
            throw new BoxNotFoundException("Box Id is null!");
        }
        Query query = em.createQuery("SELECT r FROM Review r WHERE r.box.boxId = :inBoxId ORDER BY r.reviewId DESC");
        query.setParameter("inBoxId", boxId);
        return query.getResultList();
    }
    
    
    @Override
    public void deleteReview(Long reviewId) throws ReviewNotFoundException 
    {
        Review reviewToDelete = em.find(Review.class, reviewId);
        if (reviewToDelete == null) 
        {
            throw new ReviewNotFoundException();
        }
        Customer customer = reviewToDelete.getCustomer();
        customer.getReviews().remove(reviewToDelete);
        
        Box box = reviewToDelete.getBox();
        box.getReviews().remove(reviewToDelete);
        
        em.remove(reviewToDelete);
    }
}
