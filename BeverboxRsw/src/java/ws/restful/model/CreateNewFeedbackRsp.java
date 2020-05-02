/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author boonghim
 */
public class CreateNewFeedbackRsp {
    private Long newFeedbackId;

    public CreateNewFeedbackRsp(Long newFeedbackId) {
        this.newFeedbackId = newFeedbackId;
    }

    public Long getNewFeedbackId() {
        return newFeedbackId;
    }

    public void setNewFeedbackId(Long newFeedbackId) {
        this.newFeedbackId = newFeedbackId;
    }
    
    
}
