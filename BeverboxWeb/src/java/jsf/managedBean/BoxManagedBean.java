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
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.BeverageNotFoundException;
import util.exception.BoxNotFoundException;
import util.exception.CreateNewBoxException;
import util.exception.DeleteBoxException;
import util.exception.InputDataValidationException;

/**
 *
 * @author boonghim
 */
@Named(value = "boxManagedBean")
@ViewScoped
public class BoxManagedBean implements Serializable {

    @EJB(name = "BoxSessionBeanLocal")
    private BoxSessionBeanLocal boxSessionBeanLocal;
    
    @EJB(name = "BeverageSessionBeanLocal")
    private BeverageSessionBeanLocal beverageSessionBeanLocal;
    
    @Inject
    private ViewReviewsManagedBean viewReviewsManagedBean;

    
    private Box newBox;
    private List<Box> boxes;
    private List<Beverage> selectedBeverages;
    private List<Beverage> activeBeverages;
    private Box box;

    private List<Box> filteredBoxes;
    private List<String> boxDescList;
    private List<Beverage> beverages;
    private Box selectedBoxToUpdate;
    
    /**
     * Creates a new instance of ViewBoxesManagedBean
     */
    public BoxManagedBean() {
        newBox = new Box();
        box = new Box();
        boxes = new ArrayList<>();
        filteredBoxes = new ArrayList<>();
        selectedBeverages =new ArrayList();
        boxDescList = new ArrayList<>();
        boxDescList.add("Regular");
        boxDescList.add("Healthy");
        boxDescList.add("Alcoholic");
        
    }
    
    @PostConstruct
    public void postConstruct() {
        boxes = boxSessionBeanLocal.retrieveAllBoxes();
        beverages = beverageSessionBeanLocal.retrieveAllBeverages();
    }
    
    public void viewBoxDetails(ActionEvent event) throws IOException
    {
        Box boxToView = (Box)event.getComponent().getAttributes().get("box");
        Long boxIdToView = boxToView.getBoxId();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("boxIdToView", boxIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewBoxDetails.xhtml");
    }
    
    public void deleteBox(ActionEvent event)
    {
        try
        {
            Box boxToDelete = (Box)event.getComponent().getAttributes().get("boxToDelete");
            boxSessionBeanLocal.deleteBox(boxToDelete.getBoxId());
            
            boxes.remove(boxToDelete);
            
            if(filteredBoxes != null)
            {
                filteredBoxes.remove(boxToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Box deleted successfully", null));
        }
        catch(BoxNotFoundException | DeleteBoxException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting box: " + ex.getMessage(), null));
        }
    }
    
    public void createNewBox(ActionEvent actionEvent) {
        
        try
        {
            Long boxId = boxSessionBeanLocal.createNewBox(newBox, selectedBeverages);
            
            for(Beverage b:selectedBeverages) {
                b.getBoxes().add(newBox);
                beverageSessionBeanLocal.updateBeverage(b);
                
            }
            boxes.add(newBox);
            
           FacesContext facesContext = FacesContext.getCurrentInstance();
           Flash flash = facesContext.getExternalContext().getFlash();
           flash.setKeepMessages(true);
           facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Box created successfully (Box ID: " + boxId + ")", null));               
           try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllBox.xhtml");
           } catch (IOException ex) {
                Logger.getLogger(CreateNewOptionManagedBean.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        catch(InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new box: " + ex.getMessage(), null));
        } catch (CreateNewBoxException ex) {
            Logger.getLogger(BoxManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BeverageNotFoundException ex) {
            Logger.getLogger(BoxManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void doUpdateBox(ActionEvent event)
    {
        selectedBoxToUpdate = (Box)event.getComponent().getAttributes().get("boxToUpdate");
        
    }
     
    public void updateBox(ActionEvent event)
    {        
        try
        {

            boxSessionBeanLocal.updateBox(selectedBoxToUpdate);
           

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Box updated successfully", null));
        }
        catch(BoxNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating box: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<Box> getFilteredBoxes() {
        return filteredBoxes;
    }

    public void setFilteredBoxes(List<Box> filteredBoxes) {
        this.filteredBoxes = filteredBoxes;
    }

    public List<String> getBoxDescList() {

        return boxDescList;
    }

    public void setBoxDescList(List<String> boxDescList) {
        this.boxDescList = boxDescList;
    }

    public ViewReviewsManagedBean getViewReviewsManagedBean() {
        return viewReviewsManagedBean;
    }

    public void setViewReviewsManagedBean(ViewReviewsManagedBean viewReviewsManagedBean) {
        this.viewReviewsManagedBean = viewReviewsManagedBean;
    }

    public BoxSessionBeanLocal getBoxSessionBeanLocal() {
        return boxSessionBeanLocal;
    }

    public void setBoxSessionBeanLocal(BoxSessionBeanLocal boxSessionBeanLocal) {
        this.boxSessionBeanLocal = boxSessionBeanLocal;
    }

    public Box getNewBox() {
        return newBox;
    }

    public void setNewBox(Box newBox) {
        this.newBox = newBox;
    }

    public List<Beverage> getSelectedBeverages() {
        return selectedBeverages;
    }

    public void setSelectedBeverages(List<Beverage> selectedBeverages) {
        this.selectedBeverages = selectedBeverages;
    }

    public List<Beverage> getActiveBeverages() {
        return activeBeverages;
    }

    public void setActiveBeverages(List<Beverage> activeBeverages) {
        this.activeBeverages = activeBeverages;
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<Beverage> beverages) {
        this.beverages = beverages;
    }

    public Box getSelectedBoxToUpdate() {
        return selectedBoxToUpdate;
    }

    public void setSelectedBoxToUpdate(Box selectedBoxToUpdate) {
        this.selectedBoxToUpdate = selectedBoxToUpdate;
    }
    
    
    
}
