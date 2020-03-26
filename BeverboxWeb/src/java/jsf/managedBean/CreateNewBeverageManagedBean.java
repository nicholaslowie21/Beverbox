/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author boonghim
 */
@Named(value = "createNewBeverageManagedBean")
@RequestScoped
public class CreateNewBeverageManagedBean {

    /**
     * Creates a new instance of CreateNewBeverageManagedBean
     */
    public CreateNewBeverageManagedBean() {
    }
    
}
