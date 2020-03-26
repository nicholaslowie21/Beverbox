/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.BoxSessionBeanLocal;
import entity.Box;
import java.awt.event.ActionEvent;
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
public class CreateNewBoxManagedBean {

    @EJB
    private BoxSessionBeanLocal boxSessionBeanLocal;
    private Box newBox;
    private List<Box> boxes;
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
    }
    
    public void createNewBox(ActionEvent actionEvent) {
        
        try
        {
            Long boxId = boxSessionBeanLocal.createNewBox(newBox);
            Box box = boxSessionBeanLocal.retrieveBoxByBoxId(boxId);
            boxes.add(box);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Box created successfully (Box ID: " + boxId + ")", null));
        }
        catch(InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new box: " + ex.getMessage(), null));
        } catch (BoxNotFoundException | CreateNewBoxException ex) {
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
    
    
}
