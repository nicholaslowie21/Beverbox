package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private String confirmPassword;
    
    public CreateNewAdminManagedBean() {
        newAdmin = new Admin();
        confirmPassword = "";
    }
    
    public void createNewAdmin(ActionEvent event) {
        try 
        {   
            if(!confirmPassword.equals(newAdmin.getAdminPassword())){
                throw new Exception();
            }
            adminSessionBeanLocal.createNewAdmin(newAdmin);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin created successfully!", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllAdmins.xhtml");
        } 
        catch (CreateNewAdminException | IOException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new admin: " + ex.getMessage(), null));
        } catch (Exception ex) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The passwords are mismatched", null));
        }
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    
    public Admin getNewAdmin() {
        return newAdmin;
    }

    public void setNewAdmin(Admin newAdmin) {
        this.newAdmin = newAdmin;
    }
    
}
