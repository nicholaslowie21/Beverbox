package ejb.session.stateless;

import entity.Article;
import java.util.List;
import javax.ejb.Local;
import util.exception.ArticleNotFoundException;
import util.exception.CreateNewArticleException;

/**
 *
 * @author vanes
 */
@Local
public interface ArticleSessionBeanLocal {

    public Long createNewArticle(String articleTitle, String articleContent, String articleImg) throws CreateNewArticleException;

    public List<Article> retrieveAllArticles();

    public Article retrieveArticleByArticleId(Long articleId) throws ArticleNotFoundException;

    public void updateArticle(Article article) throws ArticleNotFoundException;

    public void deleteArticle(Long articleId) throws ArticleNotFoundException;

}
