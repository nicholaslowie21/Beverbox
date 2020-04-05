/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.PromotionSessionBeanLocal;
import entity.Promotion;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllPromotionsRsp;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("Promotion")
public class PromotionResource {

    PromotionSessionBeanLocal promotionSessionBean = lookupPromotionSessionBeanLocal();

    @Context
    private UriInfo context;

    
    
    /**
     * Creates a new instance of PromotionResource
     */
    public PromotionResource() {
    }

    /**
     * Retrieves representation of an instance of ws.restful.resources.PromotionResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePromotions() {
        try{
            List<Promotion> promotions = promotionSessionBean.retrieveAllActivePromotions();
            
            return Response.status(Response.Status.OK).entity(new RetrieveAllPromotionsRsp(promotions)).build();
        }catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of PromotionResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private PromotionSessionBeanLocal lookupPromotionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PromotionSessionBeanLocal) c.lookup("java:global/Beverbox/Beverbox-ejb/PromotionSessionBean!ejb.session.stateless.PromotionSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
