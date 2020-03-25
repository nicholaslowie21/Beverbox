/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Promotion;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.PromoCodeNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author User
 */
@Local
public interface PromotionSessionBeanLocal {

    public long createNewPromotion(Promotion newPromo) throws UnknownPersistenceException, InputDataValidationException;

    public long updatePromotion(Promotion updatedPromo) throws InputDataValidationException;

    public Promotion retrievePromotionByPromoCode(String promoCode) throws PromoCodeNotFoundException;

    public long deletePromotion(Promotion promo);

    public List<Promotion> retrieveAllPromotions();
    
}
