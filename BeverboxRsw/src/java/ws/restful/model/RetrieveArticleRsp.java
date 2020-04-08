package ws.restful.model;

import entity.Article;

/**
 *
 * @author vanes
 */
public class RetrieveArticleRsp {
    
    private Article article;

    
    public RetrieveArticleRsp() {
    }

    public RetrieveArticleRsp(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    
}
