/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Promotion;
import java.util.List;

/**
 *
 * @author User
 */
public class RetrieveAllPromotionsRsp {
    
    private List<Promotion> prompotions;

    public RetrieveAllPromotionsRsp() {
    }

    public RetrieveAllPromotionsRsp(List<Promotion> prompotions) {
        this.prompotions = prompotions;
    }

    public List<Promotion> getPrompotions() {
        return prompotions;
    }

    public void setPrompotions(List<Promotion> prompotions) {
        this.prompotions = prompotions;
    }
    
    
}
