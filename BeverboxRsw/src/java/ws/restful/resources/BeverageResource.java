/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.BeverageSessionBeanLocal;
import entity.Beverage;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllBeveragesRsp;
import ws.restful.model.RetrieveBeverageRsp;

/**
 * REST Web Service
 *
 * @author boonghim
 */
@Path("Beverage")
public class BeverageResource {

    @Context
    private UriInfo context;
    
    private BeverageSessionBeanLocal beverageSessionBean = lookupBeverageSessionBeanLocal();
    /**
     * Creates a new instance of BeverageResource
     */
    public BeverageResource() {
    }

    /**
     * Retrieves representation of an instance of ws.restful.resources.BeverageResource
     * @return an instance of java.lang.String
     */
    @Path("retrieveAllBeverages")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllBeverages() {
        try{
            List<Beverage> beverages = beverageSessionBean.retrieveAllActive();
            for(Beverage b: beverages){
                b.getBoxes().clear();
                b.getTransactions().clear();
            }
            return Response.status(Response.Status.OK).entity(new RetrieveAllBeveragesRsp(beverages)).build();
        }catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    @Path("retrieveBeverage")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBeverage(@QueryParam("beverageId") Long beverageId) {
        try{
            Beverage beverage = beverageSessionBean.retrieveBeverageByBeverageId(beverageId);
            beverage.getBoxes().clear();
            beverage.getTransactions().clear();
            
            return Response.status(Response.Status.OK).entity(new RetrieveBeverageRsp(beverage)).build();
        }catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    /**
     * PUT method for updating or creating an instance of BeverageResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    

    private BeverageSessionBeanLocal lookupBeverageSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (BeverageSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/BeverageSessionBean!ejb.session.stateless.BeverageSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
