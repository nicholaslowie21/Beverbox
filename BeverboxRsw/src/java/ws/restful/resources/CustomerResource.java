/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import entity.Review;
import entity.Subscription;
import entity.Transaction;
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
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import ws.restful.model.CustomerLoginRsp;
import ws.restful.model.ErrorRsp;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("Customer")
public class CustomerResource {

    CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();

    @Context
    private UriInfo context;

    
    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }

    @Path("customerLogin")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response retrieveMyBevTransactions(@QueryParam("email") String email, 
                                @QueryParam("password") String password) {
        
        Customer customer = new Customer();
        
        try {
            customer = customerSessionBean.customerLogin(email, password);
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
           
           customer.getReviews().clear();
           customer.getSubscriptions().clear();
           customer.getTransactions().clear();
        
        return Response.status(Response.Status.OK).entity(new CustomerLoginRsp(customer)).build();
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
