package ejb.session.stateless;

import entity.Article;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.ArticleNotFoundException;
import util.exception.CreateNewArticleException;

/**
 *
 * @author vanes
 */
@Stateless
public class ArticleSessionBean implements ArticleSessionBeanLocal {

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewArticle(Article newArticle) throws CreateNewArticleException 
    {
        try 
        {
            em.persist(newArticle);
            em.flush();
            return newArticle.getArticleId();
        }
        catch (PersistenceException ex) 
        {
            throw new CreateNewArticleException();
        }
    }
    
    @Override
    public List<Article> retrieveAllArticles() 
    {
        Query query = em.createQuery("SELECT a FROM Article a ORDER BY a.articleId DESC");
        return query.getResultList();
    }
    
    @Override
    public Article retrieveArticleByArticleId(Long articleId) throws ArticleNotFoundException 
    {
        if (articleId == null) 
        {
            throw new ArticleNotFoundException("Article ID " + articleId + " does not exist!");
        }
        return em.find(Article.class, articleId);
    }
    
    @Override
    public void updateArticle(Article article) throws ArticleNotFoundException 
    {
        if (article != null) 
        {
            Article articleToUpdate = retrieveArticleByArticleId(article.getArticleId());
            articleToUpdate.setArticleTitle(article.getArticleTitle());
            articleToUpdate.setArticleContent(article.getArticleContent());
            articleToUpdate.setArticleDate(article.getArticleDate());
            articleToUpdate.setArticleImg(article.getArticleImg());
            
        }
        else 
        {
            throw new ArticleNotFoundException("Article ID not provided for article to be updated");
        }
    }
    
    
    @Override
    public void deleteArticle(Long articleId) throws ArticleNotFoundException
    {
        Article articleToDelete;
        try 
        {
            articleToDelete = retrieveArticleByArticleId(articleId);
            em.remove(articleToDelete);
        } 
        catch (ArticleNotFoundException ex) 
        {
            throw new ArticleNotFoundException("Article does not exist!");
        }
        
    }
    
}
