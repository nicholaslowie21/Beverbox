/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Beverage;
import java.util.List;
import javax.ejb.Local;
import util.exception.BeverageNotFoundException;
import util.exception.CreateNewBeverageException;
import util.exception.DeleteBeverageException;
import util.exception.InputDataValidationException;

/**
 *
 * @author boonghim
 */
@Local
public interface BeverageSessionBeanLocal {

    public Long createNewBeverage(Beverage newBeverage) throws CreateNewBeverageException, InputDataValidationException;

    public List<Beverage> retrieveAllBeverages();

    public List<Beverage> searchBeverageByName(String beverageName);

    public Beverage retrieveBeverageByBeverageId(Long beverageId) throws BeverageNotFoundException;

    public void updateBeverage(Beverage beverage) throws BeverageNotFoundException;

    public void deleteBeverage(Long beverageId) throws BeverageNotFoundException, DeleteBeverageException;

    public List<Beverage> searchBeverageByCountry(String country);

    public List<Beverage> retrieveAllActive();

    public List<Beverage> retrieveAllLimited();
    
}
