/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Box;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author boonghim
 */
@Named(value = "viewReviewsManagedBean")
@ViewScoped
public class ViewReviewsManagedBean implements Serializable {
    private Box boxToView;
    /**
     * Creates a new instance of viewReviewsManagedBean
     */
    public ViewReviewsManagedBean() {
        boxToView = new Box();
    }
    
    @PostConstruct
    public void postConstruct()
    {        
    }

    public Box getBoxToView() {
        return boxToView;
    }

    public void setBoxToView(Box boxToView) {
        this.boxToView = boxToView;
    }
    
    
}
