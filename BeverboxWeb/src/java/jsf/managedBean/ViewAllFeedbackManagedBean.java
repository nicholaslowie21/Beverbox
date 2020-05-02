/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.FeedbackSessionBeanLocal;
import entity.Feedback;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.FeedbackNotFoundException;

/**
 *
 * @author boonghim
 */
@Named(value = "viewAllFeedbackManagedBean")
@ViewScoped
public class ViewAllFeedbackManagedBean implements Serializable {

    @EJB
    private FeedbackSessionBeanLocal feedbackSessionBeanLocal;
    private List<Feedback> feedbacks;
    
    /**
     * Creates a new instance of FeedbackManagedBean
     */
    public ViewAllFeedbackManagedBean() {
        feedbacks = new ArrayList<>();
    }
    
    
    public void deleteFeedback(ActionEvent event) throws IOException 
    {
        Feedback feedbackToDelete = (Feedback)event.getComponent().getAttributes().get("feedbackToDelete");
        try 
        {
            feedbackSessionBeanLocal.deleteFeedback(feedbackToDelete.getFeedbackId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Feedback deleted successfully", null));
        } 
        catch (FeedbackNotFoundException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    @PostConstruct
    public void postConstruct() {
        feedbacks = feedbackSessionBeanLocal.retrieveAllFeedback();
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
    
    
    
}
