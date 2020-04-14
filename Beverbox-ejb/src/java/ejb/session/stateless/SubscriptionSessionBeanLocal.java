/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Subscription;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewSubscriptionException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OptionNotFoundException;
import util.exception.SubscriptionNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.InputDataValidationException;
import util.exception.InvalidPromotionException;
import util.exception.PromoCodeNotFoundException;
import util.exception.TransactionNotFoundException;

/**
 *
 * @author Venny
 */
@Local
public interface SubscriptionSessionBeanLocal {

    
    public List<Subscription> retrieveAllSubscriptions();

    public List<Subscription> retrieveAllActiveSubscriptionsByCustomerId(Long customerId) throws CustomerNotFoundException, SubscriptionNotFoundException;

    public Subscription retrieveSubscriptionBySubscriptionId(Long subscriptionId) throws SubscriptionNotFoundException;

    public List<Subscription> retrieveAllSubscriptionsByOptionId(Long optionId) throws OptionNotFoundException, SubscriptionNotFoundException;

    public void deleteSubscription(Subscription subscription) throws SubscriptionNotFoundException;

    public Long createNewSubscription(Subscription newSubscription, Long optionId, Long customerId, String promoCode, Boolean cashback) throws InvalidPromotionException, CreateNewSubscriptionException, OptionNotFoundException, CustomerNotFoundException, InputDataValidationException, UnknownPersistenceException, TransactionNotFoundException, PromoCodeNotFoundException;

    
}
