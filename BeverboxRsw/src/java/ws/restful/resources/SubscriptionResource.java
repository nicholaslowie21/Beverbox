package ws.restful.resources;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.SubscriptionSessionBeanLocal;
import entity.Customer;
import entity.Subscription;
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
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.SubscriptionNotFoundException;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllSubscriptionsRsp;

@Path("Subscription")
public class SubscriptionResource {

    @Context
    private UriInfo context;

    SubscriptionSessionBeanLocal subscriptionSessionBean = lookupSubscriptionSessionBeanLocal();
    CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();
    
    public SubscriptionResource() {
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCustomerSubscriptions(@QueryParam("email") String email, @QueryParam("password") String password) {
        try {
            Customer c = customerSessionBean.customerLogin(email, password);
            List<Subscription> subscriptions = subscriptionSessionBean.retrieveAllActiveSubscriptionsByCustomerId(c.getCustomerId());
//            List<Subscription> subscriptions = subscriptionSessionBean.retrieveAllSubscriptions();
            
            for (Subscription subscription: subscriptions) {
                subscription.setCustomer(null);
                subscription.setTransaction(null);
                subscription.setOption(null);
            }
            
            return Response.status(Response.Status.OK).entity(new RetrieveAllSubscriptionsRsp(subscriptions)).build();
        } catch (InvalidLoginCredentialException | CustomerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (SubscriptionNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of SubscriptionResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    private SubscriptionSessionBeanLocal lookupSubscriptionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (SubscriptionSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/SubscriptionSessionBean!ejb.session.stateless.SubscriptionSessionBeanLocal");
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
