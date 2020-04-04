package ejb.session.singleton;

import ejb.session.stateless.BeverageSessionBeanLocal;
import ejb.session.stateless.BoxSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Beverage;
import entity.Box;
import entity.Customer;
import ejb.session.stateless.OptionSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import entity.OptionEntity;
import entity.Promotion;
import entity.Review;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.BeverageNotFoundException;
import util.exception.BoxNotFoundException;
import util.exception.CreateNewBeverageException;
import util.exception.CreateNewBoxException;
import util.exception.CreateNewCustomerException;
import util.exception.CreateNewReviewException;
import util.exception.CustomerNotFoundException;
import util.exception.CreateNewOptionException;

import util.exception.InputDataValidationException;

/**
 *
 * @author Venny
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "BoxSessionBeanLocal")
    private BoxSessionBeanLocal boxSessionBeanLocal;

    @EJB(name = "BeverageSessionBeanLocal")
    private BeverageSessionBeanLocal beverageSessionBeanLocal;

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "OptionSessionBeanLocal")
    private OptionSessionBeanLocal optionSessionBeanLocal;

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;
    
    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;

    private List<Beverage> beverages1;
    private List<Beverage> beverages2;

    public DataInitSessionBean() {
    }

    @PostConstruct
    public void PostConstruct(){
        List<Customer> customers = customerSessionBeanLocal.retrieveAllCustomers();
        List<Beverage> beverages = beverageSessionBeanLocal.retrieveAllBeverages();
        List<Box> boxes = boxSessionBeanLocal.retrieveAllBoxes();
        List<Promotion> promos = promotionSessionBean.retrieveAllPromotions();
        if(promos.size()==0){
            initializePromo();
        }

        if(customers.isEmpty()) {
            initializeCust();
        }
        if(beverages.isEmpty()) {
            initializeBeverages();
        }
        if(boxes.isEmpty()) {
            initializeBox();
        }
        initializeReview();
    

        
        List<OptionEntity> options = optionSessionBeanLocal.retrieveAllOptions();
        if(options.size() == 0) {
            initializeOption();
        }
    }
    
    public void initializePromo(){
        Promotion promo = new Promotion("promoName 1", "NEW MEMBER", 10, "promoCode 1");
        em.persist(promo);
        em.flush();
        
        promo = new Promotion("promoName 2", "GENERAL", 10, "promoCode 2");
        em.persist(promo);
        em.flush();
    }
    
    public void initializeCust() {
        
        try {
            customerSessionBeanLocal.createNewCustomer(new Customer("Bob Tan", "abc@gmail.com", "password", "1234 5678 9101 1213", 113));
            customerSessionBeanLocal.createNewCustomer(new Customer("Jane Tan", "def@gmail.com", "password", "1235 5679 9131 1213", 123));
            customerSessionBeanLocal.createNewCustomer(new Customer("Po Tato", "ghi@gmail.com", "password", "1231 5678 1101 1223", 133));
        } catch (CreateNewCustomerException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void initializeBeverages() {
        try {
            beverages1 = new ArrayList();
            beverages2 = new ArrayList();
            Long bevId1 = beverageSessionBeanLocal.createNewBeverage(new Beverage("Exotic Bananas", "Banana Drink", "Mexico", "Non-Alcoholic", 10.00, 10));
            Beverage bev1 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId1);
            Long bevId2 = beverageSessionBeanLocal.createNewBeverage(new Beverage("Crazy Cranberry", "Cranberry Drink", "Bolivia", "Non-Alcoholic", 8.00, 10));
            Beverage bev2 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId2);
            Long bevId3 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Vodka Raspberry", "Vodka and Raspberry Drink", "Russia", "Alcoholic", 12.00, 10));
            Beverage bev3 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId3);
            Long bevId4 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Hawaiian Madness", "Pineapple and Cherry Drink", "Hawaii", "Alcoholic", 12.00, 10));
            Beverage bev4 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId4);
            Long bevId5 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Watermelon Tea", "Watermelon and Tea Drink", "Thailand", "Non-Alcoholic", 6.00, 10));
            Beverage bev5 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId5);
            Long bevId6 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Soju Madness", "Soju Mix Drink", "Korea", "Alcoholic", 12.00, 10));
            Beverage bev6 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId6);
            Long bevId7 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Sake Swish", "Sake Drink", "Japan", "Alcoholic", 12.00, 10));
            Beverage bev7 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId7);
            Long bevId8 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Durian Teh Bing", "Durian Drink", "Thailand", "Non-Alcoholic", 8.00, 10));
            Beverage bev8 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId8);
            Long bevId9 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Potato Mix", "Potato Drink", "Zambia", "Alcoholic", 15.00, 10));
            Beverage bev9 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId9);
            Long bevId10 =beverageSessionBeanLocal.createNewBeverage(new Beverage("American Lazlo", "Vegetable Drink", "America", "Non-Alcoholic", 7.00, 10));
            Beverage bev10 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId10);
            Long bevId11 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Asian madness", "Mixed Fruit Drink", "Myanmar", "Non-Alcoholic", 7.00, 10));
            Beverage bev11 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId11);
            Long bevId12 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Thai Terrific", "Baileys Milk tea Drink", "Thailand", "Alcoholic", 7.00, 10));
            Beverage bev12 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId12);
            beverages1.add(bev1);
            beverages1.add(bev2);
            beverages1.add(bev5);
            beverages1.add(bev8);
            beverages1.add(bev10);
            beverages1.add(bev11);
            
            beverages2.add(bev3);
            beverages2.add(bev4);
            beverages2.add(bev6);
            beverages2.add(bev7);
            beverages2.add(bev9);
            beverages2.add(bev12);
            
            
        } catch (CreateNewBeverageException | InputDataValidationException | BeverageNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void initializeBox() {
        try {
            Long boxId1 = boxSessionBeanLocal.createNewBox(new Box("Exotic Europe", "England", "Regular"), beverages1);
            Box box1 = boxSessionBeanLocal.retrieveBoxByBoxId(boxId1);
            
            for(Beverage b: beverages1) {
                b.getBoxes().add(box1);
            }
            
            Long boxId2 = boxSessionBeanLocal.createNewBox(new Box("Amazing Asia", "Asia", "Alcohol"), beverages2);
            Box box2 = boxSessionBeanLocal.retrieveBoxByBoxId(boxId2);
            
            for(Beverage b: beverages2) {
                b.getBoxes().add(box2);
            }
            Long boxId3 = boxSessionBeanLocal.createNewBox(new Box("Terrific Thailand", "Thailand", "Regular"), beverages1);
            Box box3 = boxSessionBeanLocal.retrieveBoxByBoxId(boxId3);
            
            
        } catch (CreateNewBoxException | InputDataValidationException | BoxNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void initializeReview(){
        try {
            reviewSessionBeanLocal.createNewReview("This box is amazing and it changed my life!", 1L, 1L);
            reviewSessionBeanLocal.createNewReview("This box is not interesting", 1L, 2L);
            reviewSessionBeanLocal.createNewReview("This box is interesting", 1L, 1L);
            reviewSessionBeanLocal.createNewReview("This box is not interesting", 1L, 1L);
            reviewSessionBeanLocal.createNewReview("This box is fantastic and it is life!", 1L, 2L);
        } catch (CustomerNotFoundException | BoxNotFoundException | CreateNewReviewException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    private void initializeOption() {
        try {
            optionSessionBeanLocal.createNewOption(new OptionEntity("Really Regular [3 months]", 3, true, "A testing option", 16.90, "REGULAR"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Really Regular [3 months]", 3, false, "A testing option", 22.90, "REGULAR"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Happy Healthy [6 months]", 6, false, "A testing option", 48.90, "HEALTHY"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Aloha Alcohol [12 months]", 12, true, "A testing option", 108.90, "ALCOHOL"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Aloha Alcohol [12 months]", 12, false, "A testing option", 102.90, "ALCOHOL"));
        } catch (CreateNewOptionException | InputDataValidationException ex) {
            ex.printStackTrace();
        }
        
    }
}
