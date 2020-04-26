package ejb.session.singleton;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.ArticleSessionBeanLocal;
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
import ejb.session.stateless.SubscriptionSessionBeanLocal;
import ejb.session.stateless.TransactionSessionBean;
import ejb.session.stateless.TransactionSessionBeanLocal;
import entity.Admin;
import entity.Article;
import entity.OptionEntity;
import entity.Promotion;
import entity.Review;
import entity.Subscription;
import entity.Transaction;
import java.util.ArrayList;
import java.util.Date;
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
import util.exception.CreateNewAdminException;
import util.exception.CreateNewArticleException;
import util.exception.CreateNewBeverageException;
import util.exception.CreateNewBoxException;
import util.exception.CreateNewCustomerException;
import util.exception.CreateNewReviewException;
import util.exception.CustomerNotFoundException;
import util.exception.CreateNewOptionException;
import util.exception.CreateNewSubscriptionException;

import util.exception.InputDataValidationException;
import util.exception.InvalidPromotionException;
import util.exception.OptionNotFoundException;
import util.exception.PromoCodeNotFoundException;
import util.exception.TransactionNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Venny
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    @EJB
    private SubscriptionSessionBeanLocal subscriptionSessionBeanLocal;

    @EJB
    private TransactionSessionBeanLocal transactionSessionBeanLocal;

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
    
    @EJB
    private ArticleSessionBeanLocal articleSessionBeanLocal;
    
    
    
    @PersistenceContext(unitName = "Beverbox-ejbPU")
    private EntityManager em;

    private List<Beverage> beverages1;
    private List<Beverage> beverages2;
    private List<Beverage> beverages3;

    public DataInitSessionBean() {
    }

    @PostConstruct
    public void PostConstruct(){
        List<Admin> admins = adminSessionBeanLocal.retrieveAllAdmins();
        List<Customer> customers = customerSessionBeanLocal.retrieveAllCustomers();
        List<Beverage> beverages = beverageSessionBeanLocal.retrieveAllBeverages();
        List<Box> boxes = boxSessionBeanLocal.retrieveAllBoxes();
        List<Promotion> promos = promotionSessionBean.retrieveAllPromotions();
        List<OptionEntity> options = optionSessionBeanLocal.retrieveAllOptions();
        
        List<Review> reviews = reviewSessionBeanLocal.retrieveAllReviews();
        List<Article> articles = articleSessionBeanLocal.retrieveAllArticles();
        List<Transaction> transactions = transactionSessionBeanLocal.retrieveAllTransaction();
        List<Subscription> subscriptions = subscriptionSessionBeanLocal.retrieveAllSubscriptions();
        
        if (admins.isEmpty()) {
            initializeAdmin();
        }
        
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
        if(reviews.isEmpty()) {
            initializeReview();
        }
        if(articles.isEmpty()) {
            initializeArticle();
        }
       
        if(options.size() == 0) {
            initializeOption();
            em.flush();
        }
        
        if(subscriptions.isEmpty()) {
            initializeSubscription();
            em.flush();
        }
        
        if(transactions.isEmpty()){
            initializeTransaction();
       
        }
        em.flush();
    }
    
    public void initializeAdmin() {
        try {
            adminSessionBeanLocal.createNewAdmin(new Admin("manager", "manager@gmail.com", "password"));
            adminSessionBeanLocal.createNewAdmin(new Admin("Alice", "alice@gmail.com", "admin1"));
            adminSessionBeanLocal.createNewAdmin(new Admin("Ben", "ben@gmail.com", "admin2"));
            adminSessionBeanLocal.createNewAdmin(new Admin("Charlie", "charlie@gmail.com", "admin3"));
        } catch (CreateNewAdminException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
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
            customerSessionBeanLocal.createNewCustomer(new Customer("Bob Tan", "abc@gmail.com", "password", "1234 5678 9101 1213", 113, "abc road"));
            customerSessionBeanLocal.createNewCustomer(new Customer("Jane Tan", "def@gmail.com", "password", "1235 5679 9131 1213", 123, "def road"));
            customerSessionBeanLocal.createNewCustomer(new Customer("Po Tato", "ghi@gmail.com", "password", "1231 5678 1101 1223", 133, "ghhi road"));
        } catch (CreateNewCustomerException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void initializeBeverages() {
        try {
            beverages1 = new ArrayList();
            beverages2 = new ArrayList();
            beverages3 = new ArrayList();
            Long bevId1 = beverageSessionBeanLocal.createNewBeverage(new Beverage("Irn Bru", "Orange Drink", "Scotland", "Non-Alcoholic", 10.00, 10, false, 5));
            Beverage bev1 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId1);
            Long bevId2 = beverageSessionBeanLocal.createNewBeverage(new Beverage("Bouvrage", "Cranberry Drink", "Brechin", "Non-Alcoholic", 8.00, 10, false, 5));
            Beverage bev2 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId2);
            Long bevId3 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Baekseju", "Glutinous Rice Drink", "South Korea", "Alcoholic", 12.00, 10, false, 5));
            Beverage bev3 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId3);
            Long bevId4 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Insamju", "Medicinal Wine", "South Korea", "Alcoholic", 12.00, 10, false, 5));
            Beverage bev4 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId4);
            Long bevId6 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Hite", "Rice Lager beer", "South Korea", "Alcoholic", 12.00, 10, false, 5));
            Beverage bev6 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId6);
            Long bevId7 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Taedonggang", "Brown ale", "North Korea", "Alcoholic", 12.00, 10, false, 5));
            Beverage bev7 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId7);
            Long bevId5 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Vimto", "Watermelon Tonic Drink", "London", "Non-Alcoholic", 6.00, 10, false, 5));
            Beverage bev5 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId5);
            Long bevId8 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Evowca Cola", "Cola-flavoured Drink", "Wales", "Non-Alcoholic", 8.00, 10, false, 5));
            Beverage bev8 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId8);
            Long bevId9 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Munbaeju", "Pear Scented Alcohol", "South Korea", "Alcoholic", 15.00, 10, false, 5));
            Beverage bev9 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId9);
            Long bevId10 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Barr", "Lemonade", "Scotland", "Non-Alcoholic", 7.00, 10, false, 5));
            Beverage bev10 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId10);
            Long bevId11 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Curry Leaf Tea", "Tea Drink", "London", "Non-Alcoholic", 7.00, 10, false, 5));
            Beverage bev11 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId11);
            Long bevId12 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Beopju", "Clear Rice Wine", "South Korea", "Alcoholic", 7.00, 10, false, 5));
            Beverage bev12 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId12);
            Long bevId13 = beverageSessionBeanLocal.createNewBeverage(new Beverage("Bombastic Bombay", "Bombay Chai tea Latte", "India", "Non-Alcoholic", 10.00, 10, true, 5));
            Beverage bev13 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId13);
            Long bevId14 = beverageSessionBeanLocal.createNewBeverage(new Beverage("Sizzling Scotland", "Fizzy Drink", "England", "Alcoholic", 10.00, 10, false, 5));
            Beverage bev14 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId14);
            Long bevId15 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Potato Mix", "Potato Drink", "Zambia", "Alcoholic", 15.00, 10, false, 5));
            Beverage bev15 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId15);
            Long bevId16 =beverageSessionBeanLocal.createNewBeverage(new Beverage("PomPom", "Pomegranate Juice", "Thailand", "Non-Alcoholic", 15.00, 10, false, 5));
            Beverage bev16 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId16);
            Long bevId17 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Thai Terrific", "Baileys Milk tea Drink", "Thailand", "Alcoholic", 7.00, 10, false, 5));
            Beverage bev17 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId17);
            Long bevId18 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Chai Yen", "Thai Milk Tea Drink", "Thailand", "Non-Alcoholic", 7.00, 10, false, 5));
            Beverage bev18 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId18);
            Long bevId19 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Potato Mix", "Potato Drink", "Zambia", "Alcoholic", 15.00, 10, false, 5));
            Beverage bev19 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId19);
            Long bevId20 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Est Cola", "Cola Drink", "Thailand", "Non-Alcoholic", 15.00, 10, false, 5));
            Beverage bev20 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId20);
            Long bevId21 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Soju Madness", "Soju Mix Drink", "South Korea", "Alcoholic", 12.00, 10, false, 5));
            Beverage bev21 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId21);
            Long bevId22 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Green Spot", "Non-carbonated Orange Soft Drink", "Thailand", "Non-Alcoholic", 15.00, 10, false, 5));
            Beverage bev22 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId22);
            Long bevId23 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Sake Swish", "Sake Drink", "Japan", "Alcoholic", 12.00, 10, false, 5));
            Beverage bev23 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId23);
            Long bevId24 =beverageSessionBeanLocal.createNewBeverage(new Beverage("M-150", "Energy Drink", "Thailand", "Non-Alcoholic", 15.00, 10, false, 5));
            Beverage bev24 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId24);
            Long bevId25 =beverageSessionBeanLocal.createNewBeverage(new Beverage("Manao", "Lime juice soda Drink", "Thailand", "Non-Alcoholic", 15.00, 10, false, 5));
            Beverage bev25 = beverageSessionBeanLocal.retrieveBeverageByBeverageId(bevId25);
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
            
            beverages3.add(bev16);
            beverages3.add(bev18);
            beverages3.add(bev20);
            beverages3.add(bev22);
            beverages3.add(bev24);
            beverages3.add(bev25);
            
            
        } catch (CreateNewBeverageException | InputDataValidationException | BeverageNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void initializeBox() {
        try {
            Long boxId1 = boxSessionBeanLocal.createNewBox(new Box("Exotic England", "England", "Regular"), beverages1);
            Box box1 = boxSessionBeanLocal.retrieveBoxByBoxId(boxId1);
            
            for(Beverage b: beverages1) {
                b.getBoxes().add(box1);
            }
            
            Long boxId2 = boxSessionBeanLocal.createNewBox(new Box("Klassic Korea", "Korea", "Alcohol"), beverages2);
            Box box2 = boxSessionBeanLocal.retrieveBoxByBoxId(boxId2);
            
            for(Beverage b: beverages2) {
                b.getBoxes().add(box2);
            }
            Long boxId3 = boxSessionBeanLocal.createNewBox(new Box("Terrific Thailand", "Thailand", "Regular"), beverages3);
            Box box3 = boxSessionBeanLocal.retrieveBoxByBoxId(boxId3);
            for(Beverage b: beverages3) {
                b.getBoxes().add(box3);
            }
            
        } catch (CreateNewBoxException | InputDataValidationException | BoxNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void initializeReview(){
        Review r = new Review("This box is amazing and it changed my life!", 5, new Date(120,0,10,10,0,0));
        try {
            reviewSessionBeanLocal.createNewReview(r, 1L, 1L);
            r = new Review("This box is not interesting", 1, new Date(120,0,18,12,0,0));
            reviewSessionBeanLocal.createNewReview(r, 1L, 2L);
            r = new Review("This box is interesting", 3, new Date(120,1,18,14,0,0));
            reviewSessionBeanLocal.createNewReview(r, 1L, 1L);
            r = new Review("This box is not interesting", 1, new Date(120,1,31,20,0,0));
            reviewSessionBeanLocal.createNewReview(r, 1L, 1L);
            r = new Review("This box is fantastic and it is life!", 5, new Date(120,2,23,9,0,0));
            reviewSessionBeanLocal.createNewReview(r, 1L, 2L);
        } catch (CustomerNotFoundException | BoxNotFoundException | CreateNewReviewException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    private void initializeOption() {
        try {
            optionSessionBeanLocal.createNewOption(new OptionEntity("Really Regular [03 months]", 3, false, "Regular Option suitable as a starter pack", 16.90, "REGULAR"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Really Regular [03 months]", 3, true, "Regular Option suitable as a starter pack", 22.90, "REGULAR"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Happy Healthy [06 months]", 6, false, "Healthy and exotic flavors to satisfy your cravings", 48.90, "HEALTHY"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Happy Healthy [06 months]", 6, true, "Healthy and exotic flavors to satisfy your cravings", 54.90, "HEALTHY"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Aloha Alcohol [12 months]", 12, false, "For those who savor life's high all year round", 108.90, "ALCOHOL"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Aloha Alcohol [12 months]", 12, true, "For those who savor life's high all year round", 114.90, "ALCOHOL"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Really Regular [06 months]", 6, false, "A semester-long joyride", 38.90, "REGULAR"));
            optionSessionBeanLocal.createNewOption(new OptionEntity("Really Regular [09 months]", 9, false, "To give you that baby-like vitality", 72.90, "REGULAR"));
            
        } catch (CreateNewOptionException | InputDataValidationException ex) {
            ex.printStackTrace();
        }
        
    }
    
    
    public void initializeTransaction() {
        try {
            
            Transaction t = new Transaction("1234", 10.0, 123, new Date());
            t.setBeverage(em.find(Beverage.class,1l));
            t.setBevNumber(1);
            Customer c = em.find(Customer.class,1l);
            t.setCustomer(c);
            transactionSessionBeanLocal.createNewTransaction(t);
            
            t = new Transaction("1234",15.0,321,new Date());
            t.setBeverage(em.find(Beverage.class,2l));
            t.setBevNumber(2);
            t.setCustomer(c);
            transactionSessionBeanLocal.createNewTransaction(t);
            
            t = new Transaction("1234",15.0,321,new Date());
            c = em.find(Customer.class,2l);
            t.setBeverage(em.find(Beverage.class,2l));
            t.setBevNumber(2);
            t.setCustomer(c);
            transactionSessionBeanLocal.createNewTransaction(t);
            
        } catch (UnknownPersistenceException | InputDataValidationException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initializeSubscription() {
        try {
            Subscription s = new Subscription(addMonths(new Date(), -6), new Date());
            s.setActive(true);
            subscriptionSessionBeanLocal.createNewSubscription(s, 3l, 1l,"",false);      
            
            s = new Subscription(new Date(), addMonths(new Date(), 3));
            s.setActive(true);
            subscriptionSessionBeanLocal.createNewSubscription(s, 1l, 1l,"",false);
            
            s = new Subscription(new Date(), addMonths(new Date(), 6));
            s.setActive(true);
            subscriptionSessionBeanLocal.createNewSubscription(s, 3l, 2l,"",false);
            
            s = new Subscription(new Date(), addMonths(new Date(), 12));
            s.setActive(true);
            subscriptionSessionBeanLocal.createNewSubscription(s, 6l, 2l,"",false);
            
            em.flush();
        } catch (InvalidPromotionException | CreateNewSubscriptionException | OptionNotFoundException | CustomerNotFoundException | 
                TransactionNotFoundException | InputDataValidationException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownPersistenceException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PromoCodeNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void initializeArticle() {
        try {
            articleSessionBeanLocal.createNewArticle(new Article("Title 1","Article 1", new Date(120,03,17,10,0,0)));
            articleSessionBeanLocal.createNewArticle(new Article("Title 2","Article 2", new Date(120,03,17,11,0,0)));
            articleSessionBeanLocal.createNewArticle(new Article("Title 3","Article 3", new Date(120,03,17,12,0,0)));
        } catch (CreateNewArticleException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Date addMonths(Date date, int numMonths){
        date.setMonth((date.getMonth() + numMonths));
        return date;
    }
}
