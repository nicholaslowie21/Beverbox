/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Transaction;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author User
 */
@Local
public interface TransactionSessionBeanLocal {

    public long createNewTransaction(Transaction newTransaction) throws UnknownPersistenceException, InputDataValidationException;

    public List<Transaction> retrieveAllTransaction();

    public List<Transaction> retrieveCustBeverageTrans(Customer customer);
    
}
