/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Beverage;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.BeverageNotFoundException;
import util.exception.CreateNewBeverageException;
import util.exception.DeleteBeverageException;
import util.exception.InputDataValidationException;

/**
 *
 * @author boonghim
 */
@Stateless
public class BeverageSessionBean implements BeverageSessionBeanLocal {

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public BeverageSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewBeverage(Beverage newBeverage) throws CreateNewBeverageException, InputDataValidationException
    {
         Set<ConstraintViolation<Beverage>>constraintViolations = validator.validate(newBeverage);
       
           
            
            if(constraintViolations.isEmpty()) {
                try {
                    em.persist(newBeverage);
                    em.flush();
                    
                    return newBeverage.getBeverageId();
                } catch(PersistenceException ex) 
                  {
                    throw new CreateNewBeverageException();
                  }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
          
        
    }
    
    @Override
    public List<Beverage> retrieveAllBeverages()
    {
        Query query = em.createQuery("SELECT be FROM Beverage be ORDER BY be.beverageName ASC");        
        List<Beverage> beverages = query.getResultList();
        
        return beverages;
    }
    
    @Override
    public List<Beverage> searchBeverageByName(String beverageName)
    {
        Query query = em.createQuery("SELECT be FROM Beverage be WHERE be.beverageName LIKE :inBeverageName ORDER BY be.beverageName ASC");
        query.setParameter("inBeverageName", "%" + beverageName + "%");
        List<Beverage> beverages = query.getResultList();
        
        
        return beverages;
    }
    
    @Override
    public List<Beverage> searchBeverageByCountry(String country)
    {
        Query query = em.createQuery("SELECT be FROM Beverage be WHERE be.country LIKE :inCountry ORDER BY be.country ASC");
        query.setParameter("inBeverageName", "%" + country + "%");
        List<Beverage> beverages = query.getResultList();
        
        
        return beverages;
    }
    
    
    @Override
    public Beverage retrieveBeverageByBeverageId(Long beverageId) throws BeverageNotFoundException
    {
        Beverage beverage = em.find(Beverage.class, beverageId);
        if(beverage != null) {
            return beverage;
        }
        else
        {
            throw new BeverageNotFoundException("Beverage ID " + beverageId + " does not exist!");
        }               
    }
    
    @Override
    public List<Beverage> retrieveAllActive() {
         Query query = em.createQuery("SELECT be FROM Beverage be WHERE be.active = true ORDER BY be.beverageName ASC");
         List<Beverage> beverages = query.getResultList();
         return beverages;
    }

    @Override
    public void updateBeverage (Beverage beverage) throws BeverageNotFoundException
    {
        if(beverage != null)
        {
            Beverage beverageToUpdate = retrieveBeverageByBeverageId(beverage.getBeverageId());

            beverageToUpdate.setBeverageName(beverage.getBeverageName());
            beverageToUpdate.setBeverageDesc(beverage.getBeverageDesc());
            beverageToUpdate.setCountry(beverage.getCountry());
            beverageToUpdate.setQuantityOnHand(beverage.getQuantityOnHand());
            beverageToUpdate.setPrice(beverage.getPrice());
            beverageToUpdate.setActive(beverage.getActive());
            beverageToUpdate.setBox(beverage.getBox());
            
        }
        else
        {
            throw new BeverageNotFoundException("Beverage ID: " + beverage.getBeverageId() + " not found!");
        }
    }
    
    
    @Override
    public void deleteBeverage(Long beverageId) throws BeverageNotFoundException, DeleteBeverageException
    {
        Beverage beverageToDelete = retrieveBeverageByBeverageId(beverageId);
        if(beverageToDelete.getBox() == null) {
            
            beverageToDelete.setActive(false);
        }
        else
        {
            throw new DeleteBeverageException("Beverage ID " + beverageId + " is associated with existing box(s) and cannot be deleted!");
        }
}

    public void persist(Object object) {
        em.persist(object);
    }

    public void persist1(Object object) {
        em.persist(object);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Beverage>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }  
    
}
