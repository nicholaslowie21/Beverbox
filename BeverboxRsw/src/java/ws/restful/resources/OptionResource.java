package ws.restful.resources;

import ejb.session.stateless.OptionSessionBeanLocal;
import entity.OptionEntity;
import entity.Subscription;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.OptionNotFoundException;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllOptionsRsp;
import ws.restful.model.RetrieveOptionRsp;

@Path("Option")
public class OptionResource {

    @Context
    private UriInfo context;
    
    OptionSessionBeanLocal optionSessionBean = lookupOptionSessionBeanLocal();

    public OptionResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllOptions() {
        try {
            List<OptionEntity> options = optionSessionBean.retrieveAllActiveOptions();
          
            for(OptionEntity option: options) {
//                for(Subscription subscription: option.getSubscriptions()) {
//                    subscription.setOption(null);
//                }
                
                option.getSubscriptions().clear();
            }
            return Response.status(Response.Status.OK).entity(new RetrieveAllOptionsRsp(options)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    @Path("retrieveOption/{optionId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAOptionByOptionId(@PathParam("optionId") Long optionId) {
        try {
            OptionEntity option = optionSessionBean.retrieveOptionByOptionId(optionId);
            
            option.getSubscriptions().clear();
            return Response.status(Response.Status.OK).entity(new RetrieveOptionRsp(option)).build();
        } catch (OptionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
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
