/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.BeverageSessionBeanLocal;
import entity.Beverage;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author boonghim
 */
@Named(value = "viewBeveragesManagedBean")
@RequestScoped
public class viewBeveragesManagedBean {

    @EJB(name = "BeverageSessionBeanLocal")
    private BeverageSessionBeanLocal beverageSessionBeanLocal;
    
    private List<Beverage> beverages;
    private List<Beverage> selectedBeverages;
    private Beverage beverage;
    /**
     * Creates a new instance of viewBeveragesManagedBean
     */
    public viewBeveragesManagedBean() {
        beverages = new ArrayList<>();
        selectedBeverages = new ArrayList<>();
    }
    
    public List<Beverage> getAllBeverages(ActionEvent event) {
        beverages = beverageSessionBeanLocal.retrieveAllBeverages();
        return beverages;
    }
    
    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<Beverage> beverages) {
        this.beverages = beverages;
    }

    public List<Beverage> getSelectedBeverages() {
        return selectedBeverages;
    }

    public void setSelectedBeverages(List<Beverage> selectedBeverages) {
        this.selectedBeverages = selectedBeverages;
    }
    
    
    
    
    
}
