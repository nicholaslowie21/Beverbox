/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Beverage;
import entity.Customer;
import entity.Transaction;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.PromoCodeNotFoundException;
import util.exception.TransactionNotFoundException;
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

    public long createBevTransaction(Beverage bev, String promoCode, Integer qty, boolean useCashBack, Customer cust) throws PromoCodeNotFoundException;

    public Transaction retrieveTransactionByTransactionId(long id) throws TransactionNotFoundException;
    
}
