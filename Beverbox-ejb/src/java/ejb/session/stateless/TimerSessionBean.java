/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Subscription;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
@LocalBean
public class TimerSessionBean {

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;

    
    
    
//    @Schedule(dayOfWeek = "Mon-Sun", month = "*", hour = "1", dayOfMonth = "*", year = "*", minute = "0", second = "0")
    public void disableOldSubscription(){
        
        Query query = em.createQuery("SELECT s FROM Subscription s WHERE s.active = true");
        List<Subscription> subscriptions = query.getResultList();
        
        long timeNow = new Date().getTime();
        long subsDate = 0l;
        long diff = 0l;
        for(Subscription s: subscriptions){
            subsDate = s.getEndDate().getTime();
            diff = timeNow - subsDate;
            if( diff>=0 && diff > (24 * 60 * 60 * 1000)){
                s.setActive(false);
            }
        }
        
        em.flush();
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
