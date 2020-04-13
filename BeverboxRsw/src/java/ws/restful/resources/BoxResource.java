/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.BoxSessionBeanLocal;
import entity.Beverage;
import entity.Box;
import entity.Review;
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
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllBoxesRsp;
import ws.restful.model.RetrieveBoxRsp;
import ws.restful.model.SearchBoxRsp;

/**
 * REST Web Service
 *
 * @author boonghim
 */
@Path("Box")
public class BoxResource {

    private BoxSessionBeanLocal boxSessionBean = lookupBoxSessionBeanLocal();

    @Context
    private UriInfo context;
    

    /**
     * Creates a new instance of BoxResource
     */
    public BoxResource() {
    }

    /**
     * Retrieves representation of an instance of ws.restful.resources.BoxResource
     * @return an instance of java.lang.String
     */
    @Path("retrieveAllBoxes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllBoxes() {
        try{
            List<Box> boxes = boxSessionBean.retrieveAllActive();
            for(Box b: boxes){
                
                for(Beverage beverage: b.getBeverages()) {
                   beverage.setBoxes(null);
                   beverage.getTransactions().clear();
                }
                for(Review r: b.getReviews()){
                    r.setBox(null);
                }
                b.getReviews().clear();
                //b.getBeverages().clear();
               
            }
            
            return Response.status(Response.Status.OK).entity(new RetrieveAllBoxesRsp(boxes)).build();
        }catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveBox")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBox(@QueryParam("boxId") Long boxId) {
        try{
            Box box = boxSessionBean.retrieveBoxByBoxId(boxId);
            
            for(Beverage beverage: box.getBeverages()) {
                   beverage.setBoxes(null);
                   beverage.getTransactions().clear();
            }
            
            box.getReviews().clear();
            
            return Response.status(Response.Status.OK).entity(new RetrieveBoxRsp(box)).build();
        }catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("searchBox")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBox(@QueryParam("boxName") String boxName) {
        try{
            List<Box> boxes = boxSessionBean.searchBoxesByName(boxName);
            for(Box b: boxes){
               
                for(Beverage beverage: b.getBeverages()) {
                   beverage.setBoxes(null);
                   beverage.getTransactions().clear();
                }
               b.getReviews().clear();
               
            }
            
            return Response.status(Response.Status.OK).entity(new SearchBoxRsp(boxes)).build();
        }catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of BoxResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private BoxSessionBeanLocal lookupBoxSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (BoxSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/BoxSessionBean!ejb.session.stateless.BoxSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
