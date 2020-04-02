/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.BeverageSessionBeanLocal;
import ejb.session.stateless.BoxSessionBeanLocal;
import entity.Beverage;
import entity.Box;
import java.io.Serializable;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.BeverageNotFoundException;
import util.exception.BoxNotFoundException;
import util.exception.CreateNewBoxException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author boonghim
 */
@Named(value = "createNewBoxManagedBean")
@RequestScoped
public class CreateNewBoxManagedBean implements Serializable {

    @EJB(name = "BeverageSessionBeanLocal")
    private BeverageSessionBeanLocal beverageSessionBeanLocal;

    @EJB
    private BoxSessionBeanLocal boxSessionBeanLocal;
    
    private Box newBox;
    private List<Box> boxes;
    private List<Beverage> selectedBeverages;
    private List<Beverage> activeBeverages;
    /**
     * Creates a new instance of createNewBoxManagedBean
     */
    public CreateNewBoxManagedBean() {
        newBox = new Box();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        boxes = boxSessionBeanLocal.retrieveAllBoxes();
        activeBeverages = beverageSessionBeanLocal.retrieveAllActive();
    }
    
    public void createNewBox(ActionEvent actionEvent) {
        
        try
        {
            Long boxId = boxSessionBeanLocal.createNewBox(newBox);
            
            for(Beverage b:selectedBeverages) {
                b.setBox(newBox);
                beverageSessionBeanLocal.updateBeverage(b);
                
            }
            newBox.setBeverages(selectedBeverages);
            
            boxes.add(newBox);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Box created successfully (Box ID: " + boxId + ")", null));
        }
        catch(InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new box: " + ex.getMessage(), null));
        } catch (CreateNewBoxException ex) {
            Logger.getLogger(CreateNewBoxManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BeverageNotFoundException ex) {
            Logger.getLogger(CreateNewBoxManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Box getNewBox() {
        return newBox;
    }

    public void setNewBox(Box newBox) {
        this.newBox = newBox;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<Beverage> getSelectedBeverages() {
        return selectedBeverages;
    }

    public void setSelectedBeverages(List<Beverage> selectedBeverages) {
        this.selectedBeverages = selectedBeverages;
    }
    
    
    
}
