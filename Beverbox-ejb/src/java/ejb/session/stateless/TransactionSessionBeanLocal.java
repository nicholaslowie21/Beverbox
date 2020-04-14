/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Beverage;
import entity.Customer;
import entity.Subscription;
import entity.Transaction;
import java.util.List;
import javax.ejb.Local;
import util.exception.BevTransactionLimitException;
import util.exception.BeverageNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidPromotionException;
import util.exception.PromoCodeNotFoundException;
import util.exception.QuantityLimitException;
import util.exception.QuantityNotEnoughException;
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

    public Transaction retrieveTransactionByTransactionId(long id) throws TransactionNotFoundException;

    public long renewSubscriptionTransaction(Subscription subs);

    public List<Transaction> retrieveCustSubscriptionTrans(Customer customer);

    public long createBevTransaction(long bevId, String promoCode, Integer qty, boolean useCashBack, long custId) throws InvalidPromotionException, BevTransactionLimitException, QuantityLimitException, PromoCodeNotFoundException, QuantityNotEnoughException, BeverageNotFoundException, CustomerNotFoundException;

    public List<Transaction> retrieveCustTransaction(long custId);


}
