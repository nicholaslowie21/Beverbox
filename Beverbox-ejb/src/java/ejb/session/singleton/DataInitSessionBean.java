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
        Promotion promo = new Promotion("WelcomePromo", "NEW MEMBER", 10, "HiBevey");
        em.persist(promo);
        em.flush();
        
        promo = new Promotion("Chinese New Year", "GENERAL", 10, "CNY2020");
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
        String article = "<h3>The best alcoholic beverages from Korea, now available on Beverbox! Introducing our new box, Klassic <span style=\"color: rgb(230, 0, 0);\">Korea</span>!</h3><p>Baekseju, Insamju, Hite, Taedonggang, Munbaeju, Beopju. Each of them are unique alcoholic beverages made from different ingredients.</p><p><strong>Baekseju</strong>(15%) is a Korean glutinous rice-based fermented alcoholic beverage flavored with a variety of herbs. <strong>Insamju</strong>(19%), also called ginseng liquor or ginseng wine, is an alcoholic beverage made of ginseng. It is often referred to as medicinal wine. <strong>Hite</strong>(4.3%) is a lager beer brewed from rice in addition to barley which results in a lighter-bodied, slightly sweeter beer that has less of a head than other beers. Hite is also the most popular beer brand in South Korea. <strong>Taedonggang</strong>(5%) is a brand of North Korean beer, named after the river that runs through Pyongyang dividing the city between east and west. <strong>Munbaeju</strong>(40%) is a Korean traditional distilled liquor produced in South Korea and is considered to be one of the finest Korean spirits. Munbae means <em>wild pear</em> in Korean, because it has a fruity scent to it, although no pear is used in its production. <strong>Beopju</strong>(16%-18%) is a type of clear rice wine. The name literally means 'law liquor', as it is made following a fixed procedure. On 1 November 1986, a variety called Gyodong-beopju was designated by the government of South Korea as Intangible Cultural Property.</p>";  
        try {
            articleSessionBeanLocal.createNewArticle(new Article("[NEW] 'Klassic Korea' Box of the Month", article, new Date(120,03,17,10,0,0)));
            
            article = "<h3>Authentic Taste of Thailand, Terrific Thailand!</h3><p><strong>Pomegranate juice</strong> is popular in Thailand. It has a cleansing and brightening effect on the skin, as well as a fresh, citrusy fragrance. This gives our products a refreshing feel on the skin. It does has astringent and antioxidant qualities, too. <strong>Chai Yen</strong> is the famous Thai Milk Tea. Made from strongly-brewed black tea, often spiced with ingredients such as star anise, crushed tamarind, cardamom, and occasionally others as well (often making this beverage a favorite among masala chai tea fans). This brew is then sweetened with sugar and sweetened condensed milk, and served over ice. <strong>Est Cola</strong> is a cola soft drink from Thailand, manufactured by Sermsuk Public Company Limited. It was launched on 2 November 2012. It was created following the termination of the company's contract with PepsiCo, for whom it had bottled and distributed Pepsi in Thailand since 1952.<strong>Green Spot</strong> is a non-carbonated non-caffeinated orange-based soft drink and has been around since 1954. <strong>Manao </strong>is a carbonated lime soda. The Coca-Cola Co. has launched Schweppes Manao Soda, a carbonated lime soda, as its first beverage especially created for the Thai market. Schweppes Manao Soda, an industrial adaptation of a local favorite soft drink, is also the first ever ready-to-drink lime soda. <strong>M-150</strong>, Thailand's most popular energy drink, is a non-carbonated energy drink marketed by the Osotspa Company Limited. In Thailand it is sold in 150 mL glass bottles, hence the name.</p>";
            articleSessionBeanLocal.createNewArticle(new Article("Terrific Thailand is here! From thai milk tea to M-150!", article, new Date(120,03,17,11,0,0)));
            
            article = "<h3>Chai Tea</h3><p>In many parts of the world, “<em>chai</em>” is simply the word for tea. However, in the Western world, the word chai has become synonymous with a type of fragrant, spicy Indian tea more accurately referred to as masala chai. What’s more, this beverage may have benefits for heart health, digestion, controlling blood sugar levels and more. This article explains what you need to know about chai tea and its potential benefits.</p><p>Chai tea is a sweet and spicy tea renowned for its fragrant aroma. Depending on where you come from, you may recognize it as masala chai. However, for the purpose of clarity, this article will use the term “chai tea” throughout. Chai tea is made from a combination of black tea, gingerand other spices. The most popular spices include cardamom, cinnamon, fennel, black pepper and cloves, although star anise, coriander seeds and peppercorns are other well-liked options. Unlike regular tea, which is brewed with water, chai tea is traditionally brewed using both warm water and warm milk. It also tends to be sweetened to varying degrees. Benefits of Chai tea include:</p><ol><li>It May Help Improve Heart Health</li><li>Chai Tea May Reduce Blood Sugar Levels</li><li>It May Reduce Nausea and Improve Digestion</li><li>It May Help You Lose Weight</li></ol><p>Chai tea is a fragrant, spicy tea that may help boost heart health, reduce blood sugar levels, aid digestion and help with weight loss.</p>";
            articleSessionBeanLocal.createNewArticle(new Article("What is Chai Tea and Its Benefits?", article, new Date(120,03,17,12,0,0)));
        } catch (CreateNewArticleException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Date addMonths(Date date, int numMonths){
        date.setMonth((date.getMonth() + numMonths));
        return date;
    }
}
