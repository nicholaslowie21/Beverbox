/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Feedback;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewFeedbackException;
import util.exception.FeedbackNotFoundException;

/**
 *
 * @author boonghim
 */
@Local
public interface FeedbackSessionBeanLocal {
    
    public Long createNewFeedback(Feedback newFeedback) throws CreateNewFeedbackException;
    public List<Feedback> retrieveAllFeedback();

    public void deleteFeedback(Long feedbackId) throws FeedbackNotFoundException;

    public Feedback retrieveFeedbackByFeedbackId(Long feedbackId) throws FeedbackNotFoundException;
    
}
