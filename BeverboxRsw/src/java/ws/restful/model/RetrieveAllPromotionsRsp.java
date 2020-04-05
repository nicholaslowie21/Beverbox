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
    
    private List<Promotion> promotions;

    public RetrieveAllPromotionsRsp() {
    }

    public RetrieveAllPromotionsRsp(List<Promotion> prompotions) {
        this.promotions = prompotions;
    }

    public List<Promotion> getPrompotions() {
        return promotions;
    }

    public void setPrompotions(List<Promotion> prompotions) {
        this.promotions = prompotions;
    }
    
    
}
