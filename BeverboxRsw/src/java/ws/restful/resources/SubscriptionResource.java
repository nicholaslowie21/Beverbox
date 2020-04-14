package ws.restful.resources;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.OptionSessionBeanLocal;
import ejb.session.stateless.SubscriptionSessionBeanLocal;
import entity.Customer;
import entity.OptionEntity;
import entity.Subscription;
import java.util.Date;
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
import util.exception.CreateNewSubscriptionException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.InvalidPromotionException;
import util.exception.OptionNotFoundException;
import util.exception.PromoCodeNotFoundException;
import util.exception.SubscriptionNotFoundException;
import util.exception.TransactionNotFoundException;
import util.exception.UnknownPersistenceException;
import ws.restful.model.CreateSubReq;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RenewSubReq;
import ws.restful.model.RetrieveAllSubscriptionsRsp;
import ws.restful.model.SubscriptionRsp;

@Path("Subscription")
public class SubscriptionResource {

    @Context
    private UriInfo context;

    SubscriptionSessionBeanLocal subscriptionSessionBean = lookupSubscriptionSessionBeanLocal();
    CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();
    OptionSessionBeanLocal optionSessionBean = lookupOptionSessionBeanLocal();
    
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

    @Path("createSubscription")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSubscription(CreateSubReq createSubReq) {
        String promoCode = "";
        Boolean cashback = true;
        long optId = 0l;
        long custId = 0l;
        String email = "";
        String password = "";
        if(createSubReq!=null){
            promoCode = createSubReq.getPromoCode();
            cashback = createSubReq.isCashback();
            optId = createSubReq.getOptId();
            custId = createSubReq.getCustId();
            email = createSubReq.getEmail();
            password = createSubReq.getPassword();
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid renewal request!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
        
        //for authorizatioin purpose
        try {
            Customer customerCheck = customerSessionBean.retrieveCustomerByEmail(email);
            
            if(!customerCheck.getCustomerPassword().equals(password)){
                ErrorRsp errorRsp = new ErrorRsp("Password is wrong.");
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
            
        } catch (CustomerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
        
        OptionEntity theOption = new OptionEntity();
        
        try {
            OptionEntity tempOption = optionSessionBean.retrieveOptionByOptionId(optId);
            theOption = tempOption;
        } catch (OptionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Such option is not found!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
        Date startDate = new Date();
        Date endDate = addMonths(new Date(), theOption.getDuration());
        Subscription newSub = new Subscription(startDate,endDate);
        
        
        Subscription theNewSub = new Subscription();
        try {
            Long theSubsId = subscriptionSessionBean.createNewSubscription(newSub, optId, custId, promoCode, cashback);
            theNewSub = subscriptionSessionBean.retrieveSubscriptionBySubscriptionId(theSubsId);
        } catch (SubscriptionNotFoundException| TransactionNotFoundException | CustomerNotFoundException | PromoCodeNotFoundException | OptionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Something went missing!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        } catch (CreateNewSubscriptionException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Something went wrong while creating subscription!");
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        } catch (InputDataValidationException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Input data is wrong!");
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        } catch (UnknownPersistenceException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Ooops! Something went wrong!");
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        } catch (InvalidPromotionException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
        
        return Response.status(Response.Status.OK).entity(new SubscriptionRsp(theNewSub)).build();
    }
    
    
    @Path("renewSubscription")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response renewSubscription(RenewSubReq renewSubReq) {
        String promoCode = "";
        Boolean cashback = true;
        long optId = 0l;
        long custId = 0l;
        String email = "";
        String password = "";
        try {
            promoCode = renewSubReq.getPromoCode();
            cashback = renewSubReq.isCashback();
            optId = subscriptionSessionBean.retrieveSubscriptionBySubscriptionId(renewSubReq.getSubsId()).getOption().getOptionId() ;
            custId = renewSubReq.getCustId();
            email = renewSubReq.getEmail();
            password = renewSubReq.getPassword();
        } catch(SubscriptionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Unable to find old subscription!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
        
        //for authorizatioin purpose
        try {
            Customer customerCheck = customerSessionBean.retrieveCustomerByEmail(email);
            
            if(!customerCheck.getCustomerPassword().equals(password)){
                ErrorRsp errorRsp = new ErrorRsp("Password is wrong.");
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
            
        } catch (CustomerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
        
        OptionEntity theOption = new OptionEntity();
        
        try {
            OptionEntity tempOption = optionSessionBean.retrieveOptionByOptionId(optId);
            theOption = tempOption;
        } catch (OptionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Such option is not found!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
//        Same month addition system in renew subscriptions and create new subscriptions bcs the button will only appear 
//        when the current month is the same as the end month. Otherwise you can't renew subscriptions
        Date startDate = new Date();
        Date endDate = addMonths(new Date(), theOption.getDuration());
        Subscription newSub = new Subscription(startDate,endDate);
        
        
        Subscription theNewSub = new Subscription();
        try {
            Long theSubsId = subscriptionSessionBean.createNewSubscription(newSub, optId, custId, promoCode, cashback);
            theNewSub = subscriptionSessionBean.retrieveSubscriptionBySubscriptionId(theSubsId);
        } catch (SubscriptionNotFoundException| TransactionNotFoundException | CustomerNotFoundException | PromoCodeNotFoundException | OptionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Something went missing!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        } catch (CreateNewSubscriptionException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Something went wrong while creating subscription!");
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        } catch (InputDataValidationException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Input data is wrong!");
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        } catch (UnknownPersistenceException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Ooops! Something went wrong!");
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        } catch (InvalidPromotionException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
        
        return Response.status(Response.Status.OK).entity(new SubscriptionRsp(theNewSub)).build();

    }    
    
    
    public static Date addMonths(Date date, int numMonths){
        date.setMonth((date.getMonth() + numMonths));
        return date;
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
    
    private OptionSessionBeanLocal lookupOptionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OptionSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/OptionSessionBean!ejb.session.stateless.OptionSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
