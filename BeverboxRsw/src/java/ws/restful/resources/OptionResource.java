package ws.restful.resources;

import ejb.session.stateless.OptionSessionBeanLocal;
import entity.OptionEntity;
import entity.Subscription;
import java.util.ArrayList;
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
import ws.restful.model.OptionWrapper;
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
            List<OptionWrapper> optionWrapper = new ArrayList<>();
            
            for(OptionEntity option: options) {
                
                option.getSubscriptions().clear();
                optionWrapper.add(new OptionWrapper(option));
            }
            return Response.status(Response.Status.OK).entity(new RetrieveAllOptionsRsp(optionWrapper)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    @Path("retrieveOption/{optionId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveOptionByOptionId(@PathParam("optionId") Long optionId) {
        try {
            OptionEntity option = optionSessionBean.retrieveOptionByOptionId(optionId);
            OptionWrapper optionWrapper = new OptionWrapper(option);
//            option.getSubscriptions().clear();
            return Response.status(Response.Status.OK).entity(new RetrieveOptionRsp(optionWrapper)).build();
        } catch (OptionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveOptionByType/{optionType}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveOptionByOptionType(@PathParam("optionType") String optionType) {
//        try {
            List<OptionEntity> options = optionSessionBean.retrieveOptionByType(optionType);
            List<OptionWrapper> uniqueOptions = new ArrayList<>();
                          
            int currUniqueIndex = -1;
            String currIndexName = "";
          
            if (!options.isEmpty()) {
                for(int i=0; i< options.size(); i++) {
                    System.out.println("at i =" + i + options.get(i).getName() + " currUniqueIndex =" + currUniqueIndex + " and currIndexName =" + currIndexName );
                    if(!currIndexName.equals(options.get(i).getName())) {
                        uniqueOptions.add(new OptionWrapper(options.get(i)));
                        currUniqueIndex++;
                        currIndexName = options.get(i).getName();
                        System.out.println("I am in if statement" + currIndexName);
                    } else {
                        // When the previously unique name stored is the same as the subsequent option in all options, so we need to store it
                        uniqueOptions.get(currUniqueIndex).setPriceSharing(options.get(i).getPrice());
                        uniqueOptions.get(currUniqueIndex).setSharingOptionId(options.get(i).getOptionId());
                        System.out.println("I am in else" + uniqueOptions.get(currUniqueIndex).getName() + " and sharing price " + uniqueOptions.get(currUniqueIndex).getPriceSharing());
                    }
                };
            }
            
            return Response.status(Response.Status.OK).entity(new RetrieveAllOptionsRsp(uniqueOptions)).build();

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
