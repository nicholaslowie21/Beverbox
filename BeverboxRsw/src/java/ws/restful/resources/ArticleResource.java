package ws.restful.resources;

import ejb.session.stateless.ArticleSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Article;
import entity.Customer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.ArticleNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllArticlesRsp;
import ws.restful.model.RetrieveArticleRsp;

/**
 * REST Web Service
 *
 * @author vanes
 */
@Path("Article")
public class ArticleResource {

    
    @Context
    private UriInfo context;
    
    ArticleSessionBeanLocal articleSessionBean = lookupArticleSessionBeanLocal();
    CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();
    
    
    public ArticleResource() {
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllArticles() {
        List<Article> articles = articleSessionBean.retrieveAllArticles();
        
        return Response.status(Response.Status.OK).entity(new RetrieveAllArticlesRsp(articles)).build();
    }

    
    @Path("retrieveArticle/{articleId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveArticleByArticleId(@PathParam("articleId") Long articleId) {
        try 
        {
            Article article = articleSessionBean.retrieveArticleByArticleId(articleId);
            return Response.status(Response.Status.OK).entity(new RetrieveArticleRsp(article)).build();
        }
        catch (ArticleNotFoundException ex) 
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
        
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    
    private ArticleSessionBeanLocal lookupArticleSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ArticleSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/ArticleSessionBean!ejb.session.stateless.ArticleSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CustomerSessionBeanLocal lookupCustomerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/CustomerSessionBean!ejb.session.stateless.CustomerSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
