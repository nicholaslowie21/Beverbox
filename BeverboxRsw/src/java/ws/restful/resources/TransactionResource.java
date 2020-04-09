/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.BeverageSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.OptionSessionBeanLocal;
import ejb.session.stateless.SubscriptionSessionBeanLocal;
import ejb.session.stateless.TransactionSessionBeanLocal;
import entity.Beverage;
import entity.Customer;
import entity.OptionEntity;
import entity.Subscription;
import entity.Transaction;
import java.util.ArrayList;
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
import util.exception.BeverageNotFoundException;
import util.exception.CreateNewSubscriptionException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OptionNotFoundException;
import util.exception.PromoCodeNotFoundException;
import util.exception.QuantityNotEnoughException;
import util.exception.SubscriptionNotFoundException;
import util.exception.TransactionNotFoundException;
import util.exception.UnknownPersistenceException;
import ws.restful.model.BevTransaction;
import ws.restful.model.BuyBevReq;
import ws.restful.model.CreateSubReq;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RenewSubReq;
import ws.restful.model.RetrieveBevTransactions;
import ws.restful.model.RetrieveSubTransactions;
import ws.restful.model.SubTransaction;
import ws.restful.model.SubscriptionRsp;


/**
 * REST Web Service
 *
 * @author User
 */
@Path("Transaction")
public class TransactionResource {

    @Context
    private UriInfo context;

    CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();
    TransactionSessionBeanLocal transactionSessionBean = lookupTransactionSessionBeanLocal();
    BeverageSessionBeanLocal beverageSessionBean = lookupBeverageSessionBeanLocal();
    OptionSessionBeanLocal optionSessionBean = lookupOptionSessionBeanLocal();
    SubscriptionSessionBeanLocal subscriptionSessionBean = lookupSubscriptionSessionBeanLocal();
    
    /**
     * Creates a new instance of TransactionResource
     */
    public TransactionResource() {
    }

    /**
     * Retrieves representation of an instance of ws.restful.resources.TransactionResource
     * @return an instance of java.lang.String
     */
    @Path("bevTransactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response retrieveMyBevTransactions(@QueryParam("email") String email, 
                                @QueryParam("password") String password) {
        Customer cust = new Customer();
        try {
            Customer customer = customerSessionBean.retrieveCustomerByEmail(email);
            
            if(!customer.getCustomerPassword().equals(password)){
                ErrorRsp errorRsp = new ErrorRsp("Password is wrong.");
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
            cust = customer;
            
        } catch (CustomerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
        List<Transaction> theTrans = transactionSessionBean.retrieveCustBeverageTrans(cust);
        List<BevTransaction> bevTransactions = new ArrayList<>();
//        for(Transaction t: theTrans){
//            if(t.getCustomer()!=null) t.getCustomer().getTransactions().clear();
//            if(t.getPromotion()!=null) t.getPromotion().getTransactions().clear();
//            if(t.getBeverage()!=null) t.getBeverage().getTransactions().clear();
//            if(t.getSubscription()!=null) t.getSubscription().setTransaction(null);
//            t.setCustomer(null);
//            t.setPromotion(null);
//            t.setBeverage(null);
//            t.setSubscription(null);
//        }
        for(Transaction t: theTrans){
            bevTransactions.add(new BevTransaction(t));
        }
        return Response.status(Response.Status.OK).entity(new RetrieveBevTransactions(bevTransactions)).build();
    }
    
    @Path("subsTransactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response retrieveMySubTransactions(@QueryParam("email") String email, 
                                @QueryParam("password") String password) {
        Customer cust = new Customer();
        try {
            Customer customer = customerSessionBean.retrieveCustomerByEmail(email);
            
            if(!customer.getCustomerPassword().equals(password)){
                ErrorRsp errorRsp = new ErrorRsp("Password is wrong.");
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
            cust = customer;
            
        } catch (CustomerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
        List<Transaction> theTrans = transactionSessionBean.retrieveCustSubscriptionTrans(cust);
        List<SubTransaction> subTransactions = new ArrayList<>();
        for(Transaction t: theTrans){
            subTransactions.add(new SubTransaction(t));
        }
        return Response.status(Response.Status.OK).entity(new RetrieveSubTransactions(subTransactions)).build();
    }
    
    @Path("renewSubscription")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response renewSubscription(RenewSubReq renewSubReq) {
        String promoCode = "";
        Boolean cashback = true;
        long subId = 0l;
        long custId = 0l;
        
        String email = "";
        String password = "";
        
        
        if(renewSubReq!=null){
            promoCode = renewSubReq.getPromoCode();
            cashback = renewSubReq.isCashback();
            subId = renewSubReq.getSubsId();
            custId = renewSubReq.getCustId();
            email = renewSubReq.getEmail();
            password = renewSubReq.getPassword();
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
        
        
        Subscription theNewSub = new Subscription();
        try {
            Subscription newSub = subscriptionSessionBean.renewSubscription(promoCode, cashback, subId, custId);
            theNewSub = newSub;
        } catch (SubscriptionNotFoundException |TransactionNotFoundException | CustomerNotFoundException | PromoCodeNotFoundException | OptionNotFoundException ex) {
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
        }
        
        return Response.status(Response.Status.OK).entity(new SubscriptionRsp(theNewSub)).build();
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
        }
        
        return Response.status(Response.Status.OK).entity(new SubscriptionRsp(theNewSub)).build();
    }
    
    @Path("createBevTransaction")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBevTransaction(BuyBevReq buyBevReq) {
        String promoCode = "";
        Boolean cashback = true;
        long bevId = 0l;
        long custId = 0l;
        int qty = 0;
        String email = "";
        String password = "";
        
        if(buyBevReq!=null){
            promoCode = buyBevReq.getPromoCode();
            cashback = buyBevReq.isCashback();
            bevId = buyBevReq.getBevId();
            custId = buyBevReq.getCustId();
            qty = buyBevReq.getQty();
            email = buyBevReq.getEmail();
            password = buyBevReq.getPassword();
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
        
        
        long theTransId = 0l;
        try {
            theTransId = transactionSessionBean.createBevTransaction(bevId, promoCode, qty, cashback, custId);
        } catch (PromoCodeNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp("This promo code is not found!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        } catch (QuantityNotEnoughException ex) {
            ErrorRsp errorRsp = new ErrorRsp("This quantity requested is too many!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        } catch (BeverageNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Beverage not found!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        } catch (CustomerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp("This customer is not found!");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
        Transaction thisNewTrans = new Transaction();
                
        try {
            thisNewTrans = transactionSessionBean.retrieveTransactionByTransactionId(theTransId);
        } catch (TransactionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp("Something is wrong! Seems like transaction went missing");
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
        
        return Response.status(Response.Status.OK).entity(new BevTransaction(thisNewTrans)).build();
    }
    
    public static Date addMonths(Date date, int numMonths){
        date.setMonth((date.getMonth() + numMonths));
        return date;
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

    private TransactionSessionBeanLocal lookupTransactionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TransactionSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/TransactionSessionBean!ejb.session.stateless.TransactionSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    private OptionSessionBeanLocal lookupOptionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OptionSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/OptionSessionBean!ejb.session.stateless.OptionSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
