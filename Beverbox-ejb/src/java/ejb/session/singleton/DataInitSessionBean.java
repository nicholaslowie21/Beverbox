/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.PromotionSessionBeanLocal;
import entity.Promotion;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Venny
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;

    
    
    @PostConstruct
    public void PostConstruct(){
        
        List<Promotion> promos = promotionSessionBean.retrieveAllPromotions();
        if(promos.size()==0){
            initializePromo();
        }
    
    }
    
    public void initializePromo(){
        Promotion promo = new Promotion("promoName 1", "NEW MEMBER", 10, "promoCode 1");
        em.persist(promo);
        em.flush();
        
        promo = new Promotion("promoName 2", "GENERAL", 10, "promoCode 2");
        em.persist(promo);
        em.flush();
    }
}
