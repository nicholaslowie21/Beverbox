package jsf.managedBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

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
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreports/TransactionReport.jasper");
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


    public void generateCustomerReport(ActionEvent event) 
    {
            
        try 
        {
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreports/CustomerReport.jasper");
            OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
        
            JasperRunManager.runReportToPdfStream(reportStream, outputStream, new HashMap<>(), beverboxDataSource.getConnection());
        } 
        catch (IOException | JRException | SQLException ex) 
        {
            Logger.getLogger(JasperReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
