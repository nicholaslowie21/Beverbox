package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CreateNewAdminException;

/**
 *
 * @author zhixuan
 */
@Named(value = "createNewAdminManagedBean")
@RequestScoped
public class CreateNewAdminManagedBean {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    private Admin newAdmin;
    
    public CreateNewAdminManagedBean() {
        newAdmin = new Admin();
    }
    
    public void createNewAdmin(ActionEvent event) {
        try 
        {
            adminSessionBeanLocal.createNewAdmin(newAdmin);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin created successfully!", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllAdmins.xhtml");
        } 
        catch (CreateNewAdminException | IOException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new admin: " + ex.getMessage(), null));
        }
    }

    public Admin getNewAdmin() {
        return newAdmin;
    }

    public void setNewAdmin(Admin newAdmin) {
        this.newAdmin = newAdmin;
    }
    
}
