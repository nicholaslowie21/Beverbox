/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Option;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewOptionException;
import util.exception.DeleteOptionException;
import util.exception.OptionNotFoundException;

/**
 *
 * @author Venny
 */
@Local
public interface OptionSessionBeanLocal {

    public Long createNewOption(Option newOption) throws CreateNewOptionException;

    public List<Option> retrieveAllOptions();

    public Option retrieveOptionByOptionId(Long optionId) throws OptionNotFoundException;

    public List<Option> searchOptionsByName(String searchString);

    public void deleteOption(Long optionId) throws OptionNotFoundException, DeleteOptionException;

    public void updateOption(Option option) throws OptionNotFoundException;
    
}
