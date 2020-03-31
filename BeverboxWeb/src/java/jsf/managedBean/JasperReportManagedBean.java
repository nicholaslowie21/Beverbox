package jsf.managedBean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.TransactionSessionBeanLocal;
import entity.Customer;
import entity.Transaction;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import util.exception.CreateNewCustomerException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author vanes
 */
@Named(value = "jasperReportManagedBean")
@RequestScoped
public class JasperReportManagedBean {


    @Resource(name = "beverboxDataSource")
    private DataSource beverboxDataSource;

    
    
    public JasperReportManagedBean() {
    }
    

    public void generateTransactionReport(ActionEvent event) 
    {
            
        try 
        {
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreports/Beverbox_transaction.jasper");
            OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
        
            JasperRunManager.runReportToPdfStream(reportStream, outputStream, new HashMap<>(), beverboxDataSource.getConnection());
        } 
        catch (IOException | JRException | SQLException ex) 
        {
            Logger.getLogger(JasperReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generateSubscriptionReport(ActionEvent event) 
    {
            
        try 
        {
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreports/Beverbox_subscription.jasper");
            OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
        
            JasperRunManager.runReportToPdfStream(reportStream, outputStream, new HashMap<>(), beverboxDataSource.getConnection());
        } 
        catch (IOException | JRException | SQLException ex) 
        {
            Logger.getLogger(JasperReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
