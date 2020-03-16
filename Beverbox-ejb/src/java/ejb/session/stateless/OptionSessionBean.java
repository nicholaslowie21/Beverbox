package ejb.session.stateless;

import entity.Option;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CreateNewOptionException;
import util.exception.DeleteOptionException;
import util.exception.OptionNotFoundException;

@Stateless
public class OptionSessionBean implements OptionSessionBeanLocal {

    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;

    
    public OptionSessionBean() {
    }

    @Override
    public Long createNewOption(Option newOption) throws CreateNewOptionException
    {
        try {
            em.persist(newOption);
            em.flush();
            
            return newOption.getOptionId();
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
    public void updateOption (Option option) throws OptionNotFoundException
    {
        if(option != null)
        {
            Option optionToUpdate = retrieveOptionByOptionId(option.getOptionId());

            //unable to update sharing option because it's only either true or false. And it's likely that two of them will exist
            optionToUpdate.setName(option.getName());
            optionToUpdate.setDuration(option.getDuration());
            optionToUpdate.setPrice(option.getPrice());
            optionToUpdate.setDescription(option.getDescription());
        }
        else
        {
            throw new OptionNotFoundException("Option ID not provided for option to be updated");
        }
    }
    
    
    @Override
    public void deleteOption(Long optionId) throws OptionNotFoundException, DeleteOptionException
    {
        Option optionToRemove = retrieveOptionByOptionId(optionId);
        
        //Perhaps also be able to remove only after all the associated Subscriptions are passed the endDate
        if(optionToRemove.getSubscriptions().isEmpty())
        {
            em.remove(optionToRemove);
        }
        else
        {
            throw new DeleteOptionException("Option ID " + optionId + " is associated with existing subscription(s) and cannot be deleted!");
        }
    }

}
