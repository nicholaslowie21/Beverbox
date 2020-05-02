/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Feedback;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CreateNewFeedbackException;
import util.exception.FeedbackNotFoundException;

/**
 *
 * @author boonghim
 */
@Stateless
public class FeedbackSessionBean implements FeedbackSessionBeanLocal {

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    
    @Override
    public Long createNewFeedback(Feedback newFeedback) throws CreateNewFeedbackException {
        em.persist(newFeedback);
        em.flush();
        return newFeedback.getFeedbackId();
    }
    
    @Override
    public List<Feedback> retrieveAllFeedback() {
        Query query = em.createQuery("SELECT f FROM Feedback f ORDER BY f.name ASC");
        
        return query.getResultList();
    }
    
    @Override
    public Feedback retrieveFeedbackByFeedbackId(Long feedbackId) throws FeedbackNotFoundException 
    {
        if (feedbackId == null) 
        {
            throw new FeedbackNotFoundException("Feedback ID " + feedbackId + " does not exist!");
        }
        return em.find(Feedback.class, feedbackId);
    }
    
    @Override
    public void deleteFeedback(Long feedbackId) throws FeedbackNotFoundException
    {
        Feedback feedbackToDelete;
        try 
        {
            feedbackToDelete = retrieveFeedbackByFeedbackId(feedbackId);
            em.remove(feedbackToDelete);
        } 
        catch (FeedbackNotFoundException ex) 
        {
            throw new FeedbackNotFoundException("Article does not exist!");
        }
        
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
