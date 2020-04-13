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
public class RetrieveAllPromotions {
    private List<Promotion> promotions;

    public RetrieveAllPromotions() {
    }

    public RetrieveAllPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
    
    
}
