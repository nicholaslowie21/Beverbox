/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Feedback;

/**
 *
 * @author boonghim
 */
public class CreateNewFeedbackReq {
    private Feedback newFeedback;

    public CreateNewFeedbackReq() {
    }

    public CreateNewFeedbackReq(Feedback newFeedback) {
        this.newFeedback = newFeedback;
    }

    public Feedback getNewFeedback() {
        return newFeedback;
    }

    public void setNewFeedback(Feedback newFeedback) {
        this.newFeedback = newFeedback;
    }
    
    
}
