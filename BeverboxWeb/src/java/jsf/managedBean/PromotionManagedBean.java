/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.PromotionSessionBeanLocal;
import entity.Promotion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;


/**
 *
 * @author User
 */
@Named(value = "promotionManagedBean")
@ViewScoped
public class PromotionManagedBean implements Serializable{

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

    private List<Promotion> allPromotions;
    private List<Promotion> filteredPromotions;
    private List<String> promoTypes;
    
    public PromotionManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        promoTypes = new ArrayList<>();
        promoTypes.add("NEW MEMBER");
        promoTypes.add("GENERAL");
        allPromotions = promotionSessionBean.retrieveAllPromotions();
    }

    public List<Promotion> getFilteredPromotions() {
        return filteredPromotions;
    }

    public void setFilteredPromotions(List<Promotion> filteredPromotions) {
        this.filteredPromotions = filteredPromotions;
    }

    public List<String> getPromoTypes() {
        return promoTypes;
    }

    public void setPromoTypes(List<String> promoTypes) {
        this.promoTypes = promoTypes;
    }

    
    
    public List<Promotion> getAllPromotions() {
        return allPromotions;
    }

    public void setAllPromotions(List<Promotion> allPromotions) {
        this.allPromotions = allPromotions;
    }
    
    
    
}
