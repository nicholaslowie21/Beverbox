package jsf.managedBean;

import ejb.session.stateless.OptionSessionBeanLocal;
import entity.OptionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CreateNewOptionException;
import util.exception.InputDataValidationException;

@Named(value = "createNewOptionManagedBean")
@ViewScoped
public class CreateNewOptionManagedBean implements Serializable {

    @EJB
    private OptionSessionBeanLocal optionSessionBeanLocal;

    private String name;
    private Integer duration;
    private String type;
    private Double price;
    private String description;
    private Boolean sharing;
    private Double sharingPriceIncrement;
//    private Boolean sharing2;
    
    private List<Integer> availableDurations;
    private List<String> availableTypes;

    public CreateNewOptionManagedBean() {
        sharingPriceIncrement = 6.00;
    }
    
    @PostConstruct
    public void postConstruct() {
        availableDurations = new ArrayList<>();
        availableDurations.add(3);
        availableDurations.add(6);
        availableDurations.add(9);
        availableDurations.add(12);
        
        availableTypes = new ArrayList<>();
        availableTypes.add("REGULAR");
        availableTypes.add("HEALTHY");
        availableTypes.add("ALCOHOL");
    }
    
    public void createNewOption (ActionEvent event) {
        try {
            //Option 1 is sharing option, while option 2 is no sharing. Sharing is just whether sharing option is allowed or not
            List<OptionEntity> options = optionSessionBeanLocal.retrieveAllOptions();
            Boolean option1Exist = false;
            Boolean option2Exist = false;
            
            for (OptionEntity option: options) {
                if(option.getName().equals(name)) {
                    if(option.getSharing() == false) {
                        option2Exist = true;
                    } else {
                        option1Exist = true;
                    }  
                }
            }
            
            if (!option2Exist) {
                Long option2Id = optionSessionBeanLocal.createNewOption(new OptionEntity(name, duration, false, description, price, type));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New option created successfully (Product ID: " + option2Id + ")", null));
            } else {
                throw new CreateNewOptionException("Option without sharing already exists");
            }
            
            if (getSharing()) {
                if (!option1Exist) {
                    Long option1Id = optionSessionBeanLocal.createNewOption(new OptionEntity(name, duration, true, description, price + sharingPriceIncrement, type));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New option created successfully (Product ID: " + option1Id + ")", null));
                } else {
                    throw new CreateNewOptionException("Option with sharing already exists");
                }
            }
            
            
        } catch (CreateNewOptionException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new option: " + ex.getMessage(), null));
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSharing() {
        return sharing;
    }

    public void setSharing(Boolean sharing) {
        this.sharing = sharing;
    }

//    public Boolean getSharing2() {
//        return sharing2;
//    }
//
//    public void setSharing2(Boolean sharing2) {
//        this.sharing2 = sharing2;
//    }

    public List<Integer> getAvailableDurations() {
        return availableDurations;
    }

    public void setAvailableDurations(List<Integer> availableDurations) {
        this.availableDurations = availableDurations;
    }

    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    public void setAvailableTypes(List<String> availableTypes) {
        this.availableTypes = availableTypes;
    }

    public Double getSharingPriceIncrement() {
        return sharingPriceIncrement;
    }

    public void setSharingPriceIncrement(Double sharingPriceIncrement) {
        this.sharingPriceIncrement = sharingPriceIncrement;
    }
    
}
