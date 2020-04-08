package ws.restful.model;

import entity.Article;
import java.util.List;

/**
 *
 * @author vanes
 */
public class RetrieveAllArticlesRsp {
    
    private List<Article> articles;

    
    public RetrieveAllArticlesRsp() {
    }

    public RetrieveAllArticlesRsp(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
     
}
