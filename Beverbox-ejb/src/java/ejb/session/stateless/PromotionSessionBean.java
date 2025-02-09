/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Promotion;
import entity.Subscription;
import entity.Transaction;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.PromoCodeNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author User
 */
@Stateless
public class PromotionSessionBean implements PromotionSessionBeanLocal {
    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PromotionSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public long createNewPromotion(Promotion newPromo) throws UnknownPersistenceException, InputDataValidationException{
        try
        {
            Set<ConstraintViolation<Promotion>>constraintViolations = validator.validate(newPromo);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newPromo);
                em.flush();

                return newPromo.getPromoId();
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(javax.persistence.PersistenceException ex)
        {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
    
    @Override
    public long updatePromotion(Promotion updatedPromo) throws InputDataValidationException{
        Set<ConstraintViolation<Promotion>>constraintViolations = validator.validate(updatedPromo);
        
        if(constraintViolations.isEmpty()){
            em.merge(updatedPromo);
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
        
        return updatedPromo.getPromoId();
    }
    
    @Override
    public Promotion retrievePromotionByPromoCode(String promoCode) throws PromoCodeNotFoundException{
        Query query = em.createQuery("SELECT p FROM Promotion p WHERE p.promoCode LIKE :inPromo AND p.active = true")
                .setParameter("inPromo", promoCode);
        
        Promotion temp;
        try{
            temp = (Promotion) query.getSingleResult();
            if(temp!=null) {
                if(!temp.isActive()){
                    throw new PromoCodeNotFoundException("Promo Code is invalid!");
                }
                    
            }
        }catch(NoResultException ex){
            throw new PromoCodeNotFoundException("No such promo code found!");
        }
        
        return temp;
    }
    
    @Override
    public long deletePromotion(Promotion promo){
        promo.setActive(false);
        return promo.getPromoId();
    }
    
    @Override
    public long restorePromotion(Promotion promo){
        promo.setActive(true);
        return promo.getPromoId();
    }
    
    @Override
    public List<Promotion> retrieveAllPromotions(){
        Query query = em.createQuery("SELECT p FROM Promotion p");
        
        return query.getResultList();
    }
    
    public List<Promotion> retrieveAllActivePromotions(){
        Query query = em.createQuery("SELECT p FROM Promotion p WHERE p.active = true");
        
        return query.getResultList();
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Promotion>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
}
