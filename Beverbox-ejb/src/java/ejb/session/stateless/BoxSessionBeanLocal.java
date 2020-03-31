/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Box;
import java.util.List;
import javax.ejb.Local;
import util.exception.BoxNotFoundException;
import util.exception.CreateNewBoxException;
import util.exception.DeleteBoxException;
import util.exception.InputDataValidationException;

/**
 *
 * @author boonghim
 */
@Local
public interface BoxSessionBeanLocal {

    public void deleteBox(Long boxId) throws BoxNotFoundException, DeleteBoxException;

    public void updateBox(Box box) throws BoxNotFoundException;

    public Box retrieveBoxByBoxId(Long boxId) throws BoxNotFoundException;

    public List<Box> searchBoxesByName(String boxName);

    public List<Box> retrieveAllBoxes();

    public Long createNewBox(Box newBox) throws CreateNewBoxException, InputDataValidationException;

    public List<Box> retrieveAllActive();
    
}
