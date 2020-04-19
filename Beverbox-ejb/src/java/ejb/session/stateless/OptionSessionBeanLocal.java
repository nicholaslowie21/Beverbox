/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OptionEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewOptionException;
import util.exception.DeleteOptionException;
import util.exception.InputDataValidationException;
import util.exception.OptionNotFoundException;

/**
 *
 * @author Venny
 */
@Local
public interface OptionSessionBeanLocal {

    public Long createNewOption(OptionEntity newOption) throws CreateNewOptionException, InputDataValidationException;
    
    public List<OptionEntity> retrieveAllOptions();
    
    public List<OptionEntity> retrieveAllActiveOptions();

    public OptionEntity retrieveOptionByOptionId(Long optionId) throws OptionNotFoundException;

    public List<OptionEntity> searchOptionsByName(String searchString);

    public void deleteOption(Long optionId) throws OptionNotFoundException, DeleteOptionException;

    public void updateOption (OptionEntity option) throws OptionNotFoundException, InputDataValidationException;

    public List<OptionEntity> retrieveOptionByType(String searchString);

}
