/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Box;
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
import util.exception.BoxNotFoundException;
import util.exception.CreateNewBoxException;
import util.exception.DeleteBoxException;
import util.exception.InputDataValidationException;

/**
 *
 * @author boonghim
 */
@Stateless
public class BoxSessionBean implements BoxSessionBeanLocal {

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
      public BoxSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewBox(Box newBox) throws CreateNewBoxException, InputDataValidationException
    {
        Set<ConstraintViolation<Box>>constraintViolations = validator.validate(newBox);
            
        if(constraintViolations.isEmpty()) {
            try {
                em.persist(newBox);
                em.flush();
                
                return newBox.getBoxId();
            }
            catch(PersistenceException ex) {
                throw new CreateNewBoxException();
            }
            
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
        
    }
    
    @Override
    public List<Box> retrieveAllBoxes()
    {
        Query query = em.createQuery("SELECT b FROM Box b ORDER BY b.boxName ASC");        
        List<Box> boxes = query.getResultList();
        
        for(Box box:boxes)
        {
            box.getBeverages().size();
        }
        
        return boxes;
    }
    
    @Override
    public List<Box> searchBoxesByName(String boxName)
    {
        Query query = em.createQuery("SELECT b FROM Box b WHERE b.boxName LIKE :inBoxName ORDER BY b.boxName ASC");
        query.setParameter("inBoxName", "%" + boxName + "%");
        List<Box> boxes = query.getResultList();
        
        for(Box box:boxes)
        {
            box.getBeverages().size();
        }
        
        return boxes;
    }
    
    
    @Override
    public Box retrieveBoxByBoxId(Long boxId) throws BoxNotFoundException
    {
        Box box = em.find(Box.class, boxId);
        
        if(box != null)
        {
            box.getBeverages().size();
            
            return box;
        }
        else
        {
            throw new BoxNotFoundException("Box ID " + boxId + " does not exist!");
        }               
    }
    
    @Override
    public List<Box> retrieveAllActive() {
         Query query = em.createQuery("SELECT b FROM Box b WHERE b.active = true ORDER BY b.boxName ASC");
         List<Box> boxes = query.getResultList();
         
         for(Box box:boxes)
        {
            box.getBeverages().size();
        }
         
         return boxes;
    }

    @Override
    public void updateBox (Box box) throws BoxNotFoundException
    {
        if(box != null)
        {
            Box boxToUpdate = retrieveBoxByBoxId(box.getBoxId());

            boxToUpdate.setBoxName(box.getBoxName());
            boxToUpdate.setBoxDesc(box.getBoxDesc());
            boxToUpdate.setBoxOrigin(box.getBoxOrigin());
            
        }
        else
        {
            throw new BoxNotFoundException("Box ID provided for option to be updated");
        }
    }
    
    
    @Override
    public void deleteBox(Long boxId) throws BoxNotFoundException, DeleteBoxException
    {
        Box boxToDelete = retrieveBoxByBoxId(boxId);
        if(boxToDelete == null){
            throw new BoxNotFoundException("Box " + boxId + "does not exist!");
        }
        else {
            boxToDelete.setActive(false);
        }
        
}

    public void persist(Object object) {
        em.persist(object);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Box>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }  

}
