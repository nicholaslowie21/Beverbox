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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;


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
    
    private Promotion newPromotion;
    private Promotion selectedPromotion;
    
    public PromotionManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        promoTypes = new ArrayList<>();
        promoTypes.add("NEW MEMBER");
        promoTypes.add("GENERAL");
        allPromotions = promotionSessionBean.retrieveAllPromotions();
        newPromotion = new Promotion();
    }
    
    public void createNewPromo(ActionEvent event){
        
        try {
            long theId = promotionSessionBean.createNewPromotion(newPromotion);
            newPromotion = new Promotion();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New promotion created successfully (Promotion ID: " +theId + ")", null));
        } catch (UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new product", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid input data" , null));
        }
        
    }
    
    public void doUpdatePromotion(ActionEvent event){
        selectedPromotion = (Promotion)event.getComponent().getAttributes().get("promotionEntityToUpdate");
    }

    public void UpdatePromotion(ActionEvent event){
        long thePromoId;
        
        try {
            thePromoId = promotionSessionBean.updatePromotion(selectedPromotion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Promotion updated successfully (Promotion ID: " +thePromoId + ")", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid input data" , null));
        }
    }
    
    public void deletePromotion(ActionEvent event){
        Promotion promoToDel = (Promotion)event.getComponent().getAttributes().get("promotionEntityToDelete");
        long id = promotionSessionBean.deletePromotion(promoToDel);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Promotion disabled successfully (Promotion ID: " +id + ")", null));
    }
    
    public void restorePromotion(ActionEvent event){
        Promotion promoToRestore = (Promotion)event.getComponent().getAttributes().get("promotionEntityToRestore");
        long id = promotionSessionBean.restorePromotion(promoToRestore);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Promotion restored successfully (Promotion ID: " +id + ")", null));
    }
    
    public Promotion getSelectedPromotion() {
        return selectedPromotion;
    }

    public void setSelectedPromotion(Promotion selectedPromotion) {
        this.selectedPromotion = selectedPromotion;
    }

    
    public Promotion getNewPromotion() {
        return newPromotion;
    }

    public void setNewPromotion(Promotion newPromotion) {
        this.newPromotion = newPromotion;
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
