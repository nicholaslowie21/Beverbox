package ejb.session.stateless;

import entity.Option;
import entity.Promotion;
import entity.Transaction;
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
    public Long createNewOption(Option newOption) throws CreateNewOptionException, InputDataValidationException
    {
        try
        {
            Set<ConstraintViolation<Option>>constraintViolations = validator.validate(newOption);
        
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
    public List<Option> retrieveAllOptions()
    {
        Query query = em.createQuery("SELECT o FROM Option o ORDER BY o.name ASC");        
        List<Option> options = query.getResultList();
        
        for(Option option:options)
        {
            option.getSubscriptions().size();
        }
        
        return options;
    }
    
    @Override
    public List<Option> searchOptionsByName(String searchString)
    {
        Query query = em.createQuery("SELECT o FROM Option o WHERE o.name LIKE :inSearchString ORDER BY o.name ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Option> options = query.getResultList();
        
        for(Option option:options)
        {
            option.getSubscriptions().size();
        }
        
        return options;
    }
    
    
    @Override
    public Option retrieveOptionByOptionId(Long optionId) throws OptionNotFoundException
    {
        Option option = em.find(Option.class, optionId);
        
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
    public void updateOption (Option option) throws OptionNotFoundException, InputDataValidationException
    {
        if(option != null)
        {
            Set<ConstraintViolation<Option>>constraintViolations = validator.validate(option);
            if(constraintViolations.isEmpty())
            {
                Option optionToUpdate = retrieveOptionByOptionId(option.getOptionId());

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
        Option optionToRemove = retrieveOptionByOptionId(optionId);
        optionToRemove.setActive(false);
        //Perhaps also be able to remove only after all the associated Subscriptions are passed the endDate
//        Right now, I will never delete the option from the database, it's only like disablingcx
//        if(optionToRemove.getSubscriptions().isEmpty())
//        {
//            em.remove(optionToRemove);
//        }
//        else
//        {
//            throw new DeleteOptionException("Option ID " + optionId + " is associated with existing subscription(s) and cannot be deleted!");
//        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Option>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }    
}
