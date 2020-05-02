package jsf.managedBean;

import ejb.session.stateless.OptionSessionBeanLocal;
import entity.OptionEntity;
import entity.Subscription;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.DeleteOptionException;
import util.exception.InputDataValidationException;
import util.exception.OptionNotFoundException;

@Named(value = "viewOptionsManagedBean")
@ViewScoped
public class ViewOptionsManagedBean implements Serializable {

    @EJB(name = "OptionSessionBeanLocal")
    private OptionSessionBeanLocal optionSessionBeanLocal;

    private List<OptionEntity> optionEntities;
    private List<OptionEntity> filteredOptionEntities;
    private OptionEntity selectedOptionEntityToUpdate;
    private List<String> availableDurations;
    private List<String> availableTypes;
    private Boolean allowChange;
    
    public ViewOptionsManagedBean() {          
    }
    
    @PostConstruct
    public void postConstruct() {
        List<OptionEntity> allOptions = optionSessionBeanLocal.retrieveAllOptions();
        optionEntities = new ArrayList<>();
        
        for(OptionEntity optionEntity: allOptions) {
            if(optionEntity.getActive()) {
                optionEntities.add(optionEntity);
            }
        }
        
        allowChange = true;
        
        setAvailableTypes(new ArrayList<>());
        getAvailableTypes().add("REGULAR");
        getAvailableTypes().add("HEALTHY");
        getAvailableTypes().add("ALCOHOL");
        
        setAvailableDurations(new ArrayList<>());
        getAvailableDurations().add("3");
        getAvailableDurations().add("6");
        getAvailableDurations().add("9");
        getAvailableDurations().add("12");
    }
    
//    View Options only for active options
    public void doUpdateOption(ActionEvent event) {
        selectedOptionEntityToUpdate = (OptionEntity) event.getComponent().getAttributes().get("optionEntityToUpdate");
        allowChange = true;
        List<Subscription> subscriptions = selectedOptionEntityToUpdate.getSubscriptions();
        
        if(subscriptions.size() != 0) {
            allowChange = false;
        }
        System.out.println("I am at doUpdate");
    }
    
    public void updateOption(ActionEvent event) {
        try {
            if (allowChange) {
                optionSessionBeanLocal.updateOption(selectedOptionEntityToUpdate);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Option updated successfully", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update option not possible as there are subscriptions attached to the option", null));
            }
        } catch (OptionNotFoundException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating option: " + ex.getMessage(), null));
        }
    }
    
    public void deleteOption(ActionEvent event) {
    
        try {
            OptionEntity optionEntityToDelete = (OptionEntity) event.getComponent().getAttributes().get("optionEntityToDelete");
            optionSessionBeanLocal.deleteOption(optionEntityToDelete.getOptionId());
            optionEntities.remove(optionEntityToDelete);
        } catch(OptionNotFoundException | DeleteOptionException ex) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting option: " + ex.getMessage(), null));
        }
    }
    
    public List<OptionEntity> getOptionEntities() {
        return optionEntities;
    }
    
    public void setProductEntities(List<OptionEntity> optionEntities) {
        this.optionEntities = optionEntities;
    }
    
    public OptionEntity getSelectedOptionEntityToUpdate() {
        return selectedOptionEntityToUpdate;
    }
    
    public void setSelectedOptionEntityToUpdate(OptionEntity selectedOptionEntityToUpdate){
        this.selectedOptionEntityToUpdate = selectedOptionEntityToUpdate;
    }
    
    public Boolean getAllowChange() {
        return allowChange;
    }
    
    public void setAllowChange(Boolean allowChange) {
        this.allowChange = allowChange;
    }

    public List<OptionEntity> getFilteredOptionEntities() {
        return filteredOptionEntities;
    }

    public void setFilteredOptionEntities(List<OptionEntity> filteredOptionEntities) {
        this.filteredOptionEntities = filteredOptionEntities;
    }
    
    public List<String> getAvailableDurations() {
        return availableDurations;
    }

    public void setAvailableDurations(List<String> availableDurations) {
        this.availableDurations = availableDurations;
    }

    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    public void setAvailableTypes(List<String> availableTypes) {
        this.availableTypes = availableTypes;
    }

    
}
