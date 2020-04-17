package ejb.session.stateless;

import entity.OptionEntity;
import entity.Promotion;
import entity.Transaction;
import java.util.ArrayList;
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
import util.exception.CreateNewOptionException;
import util.exception.DeleteOptionException;
import util.exception.InputDataValidationException;
import util.exception.OptionNotFoundException;

@Stateless
public class OptionSessionBean implements OptionSessionBeanLocal {

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public OptionSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewOption(OptionEntity newOption) throws CreateNewOptionException, InputDataValidationException
    {
        try
        {
            Set<ConstraintViolation<OptionEntity>>constraintViolations = validator.validate(newOption);
        
            if(constraintViolations.isEmpty())
                {
                    em.persist(newOption);
                    em.flush();

                    return newOption.getOptionId();
                } else {
                    throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
                }            
        }
        catch(PersistenceException ex) {
            throw new CreateNewOptionException();
        }
    }
    
    @Override
    public List<OptionEntity> retrieveAllOptions()
    {
        Query query = em.createQuery("SELECT o FROM OptionEntity o ORDER BY o.name ASC");        
        List<OptionEntity> options = query.getResultList();
        
        for(OptionEntity option:options)
        {
            option.getSubscriptions().size();
        }
        
        return options;
    }
    
    @Override
    public List<OptionEntity> retrieveAllActiveOptions()
    {   
        List<OptionEntity> options = retrieveAllOptions();
        List<OptionEntity> activeOptions = new ArrayList<>();

        for(OptionEntity option:options)
        {
            if (option.getActive()) {
                activeOptions.add(option);
            }
        }

        return activeOptions;
    }

    
    @Override
    public List<OptionEntity> searchOptionsByName(String searchString)
    {
        Query query = em.createQuery("SELECT o FROM OptionEntity o WHERE o.name LIKE :inSearchString ORDER BY o.name ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<OptionEntity> options = query.getResultList();
        
        for(OptionEntity option:options)
        {
            option.getSubscriptions().size();
        }
        
        return options;
    }
    
    
    @Override
    public OptionEntity retrieveOptionByOptionId(Long optionId) throws OptionNotFoundException
    {
        OptionEntity option = em.find(OptionEntity.class, optionId);
        
        if(option != null)
        {
            option.getSubscriptions().size();
            
            return option;
        }
        else
        {
            throw new OptionNotFoundException("Option ID " + optionId + " does not exist!");
        }               
    }
    
    @Override
    public List<OptionEntity> retrieveOptionByType(String searchString) {
        Query query = em.createQuery("SELECT o FROM OptionEntity o WHERE o.type LIKE :inSearchString AND o.active LIKE true ORDER BY o.name ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<OptionEntity> options = query.getResultList();
        
        for(OptionEntity option:options)
        {
            option.getSubscriptions().size();
        }
        
        return options;
    }

    @Override
    public void updateOption (OptionEntity option) throws OptionNotFoundException, InputDataValidationException
    {
        if(option != null)
        {
            Set<ConstraintViolation<OptionEntity>>constraintViolations = validator.validate(option);
            if(constraintViolations.isEmpty())
            {
                OptionEntity optionToUpdate = retrieveOptionByOptionId(option.getOptionId());

                //unable to update sharing option because it's only either true or false. And it's likely that two of them will exist
                optionToUpdate.setName(option.getName());
                optionToUpdate.setDuration(option.getDuration());
                optionToUpdate.setPrice(option.getPrice());
                optionToUpdate.setDescription(option.getDescription());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new OptionNotFoundException("Option ID not provided for option to be updated");
        }
        
    }
    
    
    @Override
    public void deleteOption(Long optionId) throws OptionNotFoundException, DeleteOptionException
    {
        OptionEntity optionToRemove = retrieveOptionByOptionId(optionId);
        optionToRemove.setActive(false);
        //Perhaps also be able to remove only after all the associated Subscriptions are passed the endDate
//        Right now, I will never delete the option from the database, it's only like disablingcx
//        if(optionToRemove.getSubscriptions().isEmpty())
//        {
//            em.remove(optionToRemove);
//        }
//        else
//        {
//            throw new DeleteOptionException("OptionEntity ID " + optionId + " is associated with existing subscription(s) and cannot be deleted!");
//        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<OptionEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }    
}
