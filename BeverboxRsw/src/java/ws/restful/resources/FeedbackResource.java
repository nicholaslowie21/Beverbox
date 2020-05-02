/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.FeedbackSessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.CreateNewFeedbackException;
import ws.restful.model.CreateNewCustomerRsp;
import ws.restful.model.CreateNewFeedbackReq;
import ws.restful.model.CreateNewFeedbackRsp;
import ws.restful.model.ErrorRsp;

/**
 * REST Web Service
 *
 * @author boonghim
 */
@Path("Feedback")
public class FeedbackResource {

    FeedbackSessionBeanLocal feedbackSessionBean = lookupFeedbackSessionBeanLocal();
    
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FeedbackResource
     */
    public FeedbackResource() {
    }


    /**
     * PUT method for updating or creating an instance of FeedbackResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewFeedback(CreateNewFeedbackReq createNewFeedbackReq) {
        if(createNewFeedbackReq != null ) {
            try {
                Long newFeedbackId = feedbackSessionBean.createNewFeedback(createNewFeedbackReq.getNewFeedback());
                CreateNewFeedbackRsp createNewFeedbackRsp = new CreateNewFeedbackRsp(newFeedbackId);
                return Response.status(Response.Status.OK).entity(createNewFeedbackRsp).build();
            } catch (CreateNewFeedbackException ex) {
                 ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new feedback request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }

    
    }

    private FeedbackSessionBeanLocal lookupFeedbackSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (FeedbackSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/FeedbackSessionBean!ejb.session.stateless.FeedbackSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
