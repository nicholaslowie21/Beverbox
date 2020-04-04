/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.BeverageSessionBeanLocal;
import entity.Beverage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.inject.Named;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.BeverageNotFoundException;
import util.exception.DeleteBeverageException;

/**
 *
 * @author boonghim
 */
@Named(value="beverageManagedBean")
@ViewScoped
public class BeverageManagedBean implements Serializable {

    @EJB(name = "BeverageSessionBeanLocal")
    private BeverageSessionBeanLocal beverageSessionBeanLocal;
    
    private List<Beverage> beverages;
    private List<Beverage> filteredBeverages;
    private List<Beverage> activeBeverages;
    private Beverage beverage;
    private Beverage selectedBeverageToUpdate;
    private List<String> beverageTypes;
    private List<Beverage> noBoxBeverages;
    private List<Boolean> actives;
   
    /**
     * Creates a new instance of viewBeveragesManagedBean
     */
    public BeverageManagedBean() {
        beverages = new ArrayList<>();
        filteredBeverages = new ArrayList<>();
        activeBeverages = new ArrayList<>();
        selectedBeverageToUpdate = new Beverage();
        beverageTypes = new ArrayList<>();
        retrieveAllTypes();
        actives = new ArrayList<>();
        actives.add(true);
        actives.add(false);
    }
    
     @PostConstruct
    public void postConstruct()
    {
       
        beverages = beverageSessionBeanLocal.retrieveAllBeverages();
        activeBeverages = beverageSessionBeanLocal.retrieveAllActive();
       

    }
    
    public void retrieveAllTypes() {
        beverageTypes.add("Alcoholic");
        beverageTypes.add("Non-Alcoholic");
        for(Beverage b: beverages) {
            beverageTypes.add(b.getType());
        }
    }
     
    public void updateBeverage(ActionEvent event)
    {        
        try
        {

            beverageSessionBeanLocal.updateBeverage(selectedBeverageToUpdate);
           

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Beverage updated successfully", null));
        }
        catch(BeverageNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating beverage: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteBeverage(ActionEvent event)
    {
        try
        {
            
            Beverage beverageDelete = (Beverage)event.getComponent().getAttributes().get("beverageToDelete");
            beverageSessionBeanLocal.deleteBeverage(beverageDelete.getBeverageId());
            
            beverages.remove(beverageDelete);
            
            if(filteredBeverages != null)
            {
                filteredBeverages.remove(beverageDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Beverage deleted successfully", null));
        }
        catch(BeverageNotFoundException | DeleteBeverageException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting beverage: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public List<Beverage> getAllBeverages(ActionEvent event) {
        
        return beverages;
    }
    
    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<Beverage> beverages) {
        this.beverages = beverages;
    }

    public List<Beverage> getFilteredBeverages() {
        return filteredBeverages;
    }

    public void setFilteredBeverages(List<Beverage> filteredBeverages) {
        this.filteredBeverages = filteredBeverages;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public Beverage getSelectedBeverageToUpdate() {
        return selectedBeverageToUpdate;
    }

    public void setSelectedBeverageToUpdate(Beverage selectedBeverageToUpdate) {
        this.selectedBeverageToUpdate = selectedBeverageToUpdate;
    }

    public List<Beverage> getActiveBeverages() {
        return activeBeverages;
    }

    public void setActiveBeverages(List<Beverage> activeBeverages) {
        this.activeBeverages = activeBeverages;
    }

    public List<String> getBeverageTypes() {
        return beverageTypes;
    }

    public void setBeverageTypes(List<String> beverageTypes) {
        this.beverageTypes = beverageTypes;
    }

    public List<Beverage> getNoBoxBeverages() {
        return noBoxBeverages;
    }

    public void setNoBoxBeverages(List<Beverage> noBoxBeverages) {
        this.noBoxBeverages = noBoxBeverages;
    }

    public List<Boolean> getActives() {
        return actives;
    }

    public void setActives(List<Boolean> actives) {
        this.actives = actives;
    }   
    
    
}
