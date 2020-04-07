package ws.restful.resources;

import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Review;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.BoxNotFoundException;
import util.exception.CreateNewReviewException;
import util.exception.CustomerNotFoundException;
import util.exception.ReviewNotFoundException;
import ws.restful.model.CreateNewReviewReq;
import ws.restful.model.CreateNewReviewRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllReviewsRsp;

/**
 * REST Web Service
 *
 * @author vanes
 */
@Path("Review")
public class ReviewResource {

    
    @Context
    private UriInfo context;
    
    ReviewSessionBeanLocal reviewSessionBean = lookupReviewSessionBeanLocal();

    
    /**
     * Creates a new instance of ReviewResource
     */
    public ReviewResource() {
    }


    
    @Path("retrieveAllReviews")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllReviewsByCustomerId(@QueryParam("customerId") Long customerId) 
    {
        System.err.println("Customer ID: " + customerId.toString());
        try 
        {
            List<Review> reviews = reviewSessionBean.retrieveAllReviewsByCustomerId(customerId);
            RetrieveAllReviewsRsp retrieveAllReviewsRsp =  new RetrieveAllReviewsRsp(reviews);
            return Response.status(Response.Status.OK).entity(retrieveAllReviewsRsp).build();
        } 
        catch (CustomerNotFoundException ex) 
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
        
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAllReviews/{boxId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllReviewsByBoxId(@PathParam("boxId") Long boxId) 
    {
        System.out.println("ENTER HERE");
        System.err.println("Box ID: " + boxId);
        try 
        {
            List<Review> reviews = reviewSessionBean.retrieveAllReviewsByBoxId(boxId);
            RetrieveAllReviewsRsp retrieveAllReviewsRsp =  new RetrieveAllReviewsRsp(reviews);
            return Response.status(Response.Status.OK).entity(retrieveAllReviewsRsp).build();
        }
        catch (BoxNotFoundException ex) 
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
        
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewReview(CreateNewReviewReq createNewReviewReq) {
        if(createNewReviewReq != null) 
        {
            try 
            {
                Long reviewId = reviewSessionBean.createNewReview(createNewReviewReq.getReview(), createNewReviewReq.getBoxId(), createNewReviewReq.getCustomerId());
                
                CreateNewReviewRsp createNewReviewRsp = new CreateNewReviewRsp(reviewId);
                return Response.status(Response.Status.OK).entity(createNewReviewRsp).build();
            }
            catch(CustomerNotFoundException | BoxNotFoundException | CreateNewReviewException ex) 
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            }
            
        }
        else 
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new review request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReview(@PathParam("reviewId") Long reviewId) 
    {
        try 
        {
            reviewSessionBean.deleteReview(reviewId);
            
            return Response.status(Response.Status.OK).build(); 
        } 
        catch (ReviewNotFoundException ex) 
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    
    private ReviewSessionBeanLocal lookupReviewSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReviewSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/ReviewSessionBean!ejb.session.stateless.ReviewSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
