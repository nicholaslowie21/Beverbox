/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.BeverageSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Beverage;
import entity.Box;
import entity.Customer;
import entity.Review;
import entity.Transaction;
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
    
    private CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();
    
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
    public Response retrieveAllBeverages(@QueryParam("email") String email, @QueryParam("password") String password) {
        try{
            Customer c = customerSessionBean.customerLogin(email, password);
            List<Beverage> beverages = beverageSessionBean.retrieveAllActive();
             for(Beverage be:beverages){
                for(Box box:be.getBoxes()) {
                    box.getBeverages().clear();
                    box.getReviews().clear();
                    for(Review r: box.getReviews()) {
                        r.setBox(null);
                        r.getCustomer().getReviews().clear();
                        r.getCustomer().getSubscriptions().clear();
                        r.getCustomer().getTransactions().clear();
                        r.setCustomer(null);
                    }
                    be.getTransactions().clear();
                   
            }
                for(Transaction transaction: be.getTransactions()) {
                    transaction.setBeverage(null);
                    transaction.setCustomer(null);
                    
                }
                
                be.getTransactions().clear();
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
    public Response retrieveBeverage(@QueryParam("email") String email, @QueryParam("password") String password, @QueryParam("beverageId") Long beverageId) {
        try{
            Customer c = customerSessionBean.customerLogin(email, password);
            Beverage beverage = beverageSessionBean.retrieveBeverageByBeverageId(beverageId);
            
            for(Box box:beverage.getBoxes()) {
                box.getBeverages().clear();
                box.getReviews().clear();
                for(Review r: box.getReviews()) {
                    r.setBox(null);
                    r.getCustomer().getReviews().clear();
                    r.getCustomer().getSubscriptions().clear();
                    r.getCustomer().getTransactions().clear();
                }

            }
             
            beverage.getTransactions().clear();
            
            
            return Response.status(Response.Status.OK).entity(new RetrieveBeverageRsp(beverage)).build();
        }catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveLimited")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveLimited(@QueryParam("email") String email, @QueryParam("password") String password, @QueryParam("beverageId") Long beverageId) {
        try{
            Customer c = customerSessionBean.customerLogin(email, password);
            List<Beverage> beverages = beverageSessionBean.retrieveAllLimited();
            for(Beverage be:beverages){
                for(Box box:be.getBoxes()) {
                    box.getBeverages().clear();
                    box.getReviews().clear();
                    for(Review r: box.getReviews()) {
                        r.setBox(null);
                        r.getCustomer().getReviews().clear();
                        r.getCustomer().getSubscriptions().clear();
                        r.getCustomer().getTransactions().clear();
                        r.setCustomer(null);
                    }
                    be.getTransactions().clear();
                   
            }
                for(Transaction transaction: be.getTransactions()) {
                    transaction.setBeverage(null);
                    transaction.setCustomer(null);
                    
                }
                
                be.getTransactions().clear();
            }

            
            return Response.status(Response.Status.OK).entity(new RetrieveAllBeveragesRsp(beverages)).build();
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
