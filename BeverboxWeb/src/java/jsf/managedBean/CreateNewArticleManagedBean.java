package jsf.managedBean;

import ejb.session.stateless.ArticleSessionBeanLocal;
import entity.Article;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CreateNewArticleException;

/**
 *
 * @author vanes
 */
@Named(value = "createNewArticleManagedBean")
@RequestScoped

public class CreateNewArticleManagedBean {

    @EJB
    private ArticleSessionBeanLocal articleSessionBeanLocal;

    private String title;
    private String content;
    private String img;
    
    public CreateNewArticleManagedBean() {
    }
    
    public void createNewArticle(ActionEvent event) throws IOException 
    {
        Article newArticle = new Article(title, content, img);
        try 
        {
            articleSessionBeanLocal.createNewArticle(newArticle);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Article created successfully!", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllArticles.xhtml");
        } 
        catch (CreateNewArticleException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new article: " + ex.getMessage(), null));
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
}
