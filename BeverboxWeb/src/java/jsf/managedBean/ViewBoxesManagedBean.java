/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.BoxSessionBeanLocal;
import entity.Box;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.BoxNotFoundException;
import util.exception.DeleteBoxException;

/**
 *
 * @author boonghim
 */
@Named(value = "viewBoxesManagedBean")
@ViewScoped
public class ViewBoxesManagedBean implements Serializable {

    @EJB(name = "BoxSessionBeanLocal")
    private BoxSessionBeanLocal boxSessionBeanLocal;
    
    private Box box;
    private List<Box> boxes;
    private List<Box> filteredBoxes;
    private List<String> boxDescList;
    
    /**
     * Creates a new instance of ViewBoxesManagedBean
     */
    public ViewBoxesManagedBean() {
        box = new Box();
        boxes = new ArrayList<>();
        filteredBoxes = new ArrayList<>();
        boxDescList = new ArrayList<>();
        boxDescList.add("Regular");
        boxDescList.add("Healthy");
        boxDescList.add("Alcoholic");
    }
    
    @PostConstruct
    public void postConstruct() {
        boxes = boxSessionBeanLocal.retrieveAllBoxes();
    }
    
    public void viewBoxDetails(ActionEvent event) throws IOException
    {
        Long productIdToView = (Long)event.getComponent().getAttributes().get("boxId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("boxIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewBoxDetails.xhtml");
    }
    
    public void deleteProduct(ActionEvent event)
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
    
    
    
}
