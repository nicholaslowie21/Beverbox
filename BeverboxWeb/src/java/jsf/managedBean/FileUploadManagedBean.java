/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

/**
 *
 * @author boonghim
 */
@Named(value = "fileUploadManagedBean")
@RequestScoped
public class FileUploadManagedBean {
    private UploadedFile file;
    /**
     * Creates a new instance of FIleUploadManagedBean
     */
    public FileUploadManagedBean() {
        
    }
    
    
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
 
 
    public void upload(FileUploadEvent event) throws IOException {
        file = event.getFile();

        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext scontext = (ServletContext)context.getExternalContext().getContext();
            String rootpath = scontext.getRealPath("/");
            String filename = FilenameUtils.getBaseName(file.getFileName()); 
            String[] splitted = rootpath.split("Beverbox");
            File files = new File(splitted[0]+"BeverboxAngular"+File.separator+"src"+File.separator + "assets" + File.separator + "images" + File.separator + filename +".png");

            try (InputStream input = file.getInputstream()) {
                Files.copy(input, files.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println("Uploaded file successfully saved in " + file);
        }
    }
     
     
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}