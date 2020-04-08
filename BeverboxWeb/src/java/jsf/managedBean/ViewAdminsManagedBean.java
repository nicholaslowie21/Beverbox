package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.AdminNotFoundException;

/**
 *
 * @author zhixuan
 */
@Named(value = "viewAdminsManagedBean")
@ViewScoped
public class ViewAdminsManagedBean implements Serializable {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    private List<Admin> admins;
    private Admin selectedAdminToUpdate;
    
    public ViewAdminsManagedBean() {
        selectedAdminToUpdate = new Admin();
    }
    
    @PostConstruct
    public void postConstruct() {
        setAdmins(adminSessionBeanLocal.retrieveAllAdmins());
    }
    
    public void doUpdateAdmin(ActionEvent event) {
        selectedAdminToUpdate = (Admin)event.getComponent().getAttributes().get("adminToUpdate");
    }
    
    public void updateAdmin(ActionEvent event) 
    {
        try 
        {
            adminSessionBeanLocal.updateAdmin(selectedAdminToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin updated successfully", null));
        } 
        catch (AdminNotFoundException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
    public void deleteAdmin(ActionEvent event) throws IOException 
    {
        Admin adminToDelete = (Admin)event.getComponent().getAttributes().get("adminToDelete");
        try 
        {
            adminSessionBeanLocal.deleteAdmin(adminToDelete.getAdminId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin deleted successfully", null));
        } 
        catch (AdminNotFoundException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public Admin getSelectedAdminToUpdate() {
        return selectedAdminToUpdate;
    }

    public void setSelectedAdminToUpdate(Admin selectedAdminToUpdate) {
        this.selectedAdminToUpdate = selectedAdminToUpdate;
    }
    
}