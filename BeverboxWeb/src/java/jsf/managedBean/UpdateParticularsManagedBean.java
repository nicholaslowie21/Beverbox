package jsf.managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
import java.io.Serializable;
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
@Named(value = "updateParticularsManagedBean")
@ViewScoped
public class UpdateParticularsManagedBean implements Serializable {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    private Admin adminToUpdate;

    public UpdateParticularsManagedBean() {
    }

    public void doUpdateParticulars(ActionEvent event) {
        adminToUpdate = (Admin)event.getComponent().getAttributes().get("adminToUpdate");
    }
    
    public void updateParticulars(ActionEvent event) 
    {
        try 
        {
            adminSessionBeanLocal.updateAdmin(getAdminToUpdate());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Particulars updated successfully", null));
        } 
        catch (AdminNotFoundException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public Admin getAdminToUpdate() {
        return adminToUpdate;
    }

    public void setAdminToUpdate(Admin adminToUpdate) {
        this.adminToUpdate = adminToUpdate;
    }
}
