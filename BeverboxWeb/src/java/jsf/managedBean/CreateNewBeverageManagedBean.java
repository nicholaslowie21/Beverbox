/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.BeverageSessionBeanLocal;
import entity.Beverage;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import util.exception.BeverageNotFoundException;
import util.exception.CreateNewBeverageException;
import util.exception.InputDataValidationException;

/**
 *
 * @author boonghim
 */
@Named(value = "createNewBeverageManagedBean")
@RequestScoped
public class CreateNewBeverageManagedBean {

    @EJB(name = "BeverageSessionBeanLocal")
    private BeverageSessionBeanLocal beverageSessionBeanLocal;
    
    private Beverage newBeverage;
    private List<Beverage> beverages;
    private List<String> beverageTypes;
    /**
     * Creates a new instance of CreateNewBeverageManagedBean
     */
    public CreateNewBeverageManagedBean() {
        newBeverage = new Beverage();
        beverages = new ArrayList<>();
        beverageTypes = new ArrayList<>();
        retrieveAllTypes();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        beverages = beverageSessionBeanLocal.retrieveAllBeverages();
    }
    
    public void createNewBeverage(ActionEvent actionEvent) {
        
        try
        {
            Long beverageId = beverageSessionBeanLocal.createNewBeverage(newBeverage);
            Beverage beverage = beverageSessionBeanLocal.retrieveBeverageByBeverageId(beverageId);
            beverages.add(beverage);
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Flash flash = facesContext.getExternalContext().getFlash();
            flash.setKeepMessages(true);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Beverage created successfully (Beverage ID: " + beverageId + ")", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllBeverage.xhtml");
            
        }
        catch(InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new beverage: " + ex.getMessage(), null));
        } catch (BeverageNotFoundException | CreateNewBeverageException ex) {
            Logger.getLogger(BoxManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreateNewBeverageManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void retrieveAllTypes() {
        beverageTypes.add("Alcoholic");
        beverageTypes.add("Non-Alcoholic");
        for(Beverage b: beverages) {
            beverageTypes.add(b.getType());
        }
    }
    
    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<Beverage> beverages) {
        this.beverages = beverages;
    }

    public Beverage getNewBeverage() {
        return newBeverage;
    }

    public void setNewBeverage(Beverage newBeverage) {
        this.newBeverage = newBeverage;
    }

    public List<String> getBeverageTypes() {
        return beverageTypes;
    }

    public void setBeverageTypes(List<String> beverageTypes) {
        this.beverageTypes = beverageTypes;
    }
    
    
    
    
}
