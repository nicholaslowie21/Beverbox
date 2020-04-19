package jsf.managedBean;

import ejb.session.stateless.ArticleSessionBeanLocal;
import entity.Article;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.ArticleNotFoundException;

/**
 *
 * @author vanes
 */
@Named(value = "viewArticlesManagedBean")
@ViewScoped

public class ViewArticlesManagedBean implements Serializable {

    @EJB
    private ArticleSessionBeanLocal articleSessionBeanLocal;
    
    private List<Article> articles;
    private Article articleToUpdate;
    
    
    public ViewArticlesManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() 
    {
        articles = articleSessionBeanLocal.retrieveAllArticles();
    }
    
    
    public void doUpdateArticle(ActionEvent event) 
    {
        articleToUpdate = (Article)event.getComponent().getAttributes().get("articleToUpdate");
    }
    
    
    public void updateArticle(ActionEvent event) 
    {
        try 
        {
            articleToUpdate.setArticleDate(new Date());
            articleSessionBeanLocal.updateArticle(articleToUpdate);
        } 
        catch (ArticleNotFoundException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
    public void deleteArticle(ActionEvent event) throws IOException 
    {
        Article articleToDelete = (Article)event.getComponent().getAttributes().get("articleToDelete");
        try 
        {
            articleSessionBeanLocal.deleteArticle(articleToDelete.getArticleId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Article deleted successfully", null));
        } 
        catch (ArticleNotFoundException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Article getArticleToUpdate() {
        return articleToUpdate;
    }

    public void setArticleToUpdate(Article articleToUpdate) {
        this.articleToUpdate = articleToUpdate;
    }
    
    
}
