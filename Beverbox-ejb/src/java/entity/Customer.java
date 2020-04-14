package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author zhixuan
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String customerEmail;
    @Column(nullable = false)
    private String customerPassword;
    private Double accumulatedCashback = 0.0;
    @Column(nullable = false)
    private String customerCCNum;
    @Column(nullable = false)
    private Integer customerCVV;
    
    @Column(nullable = false)
    private String address;
    
    @OneToMany(mappedBy = "customer")
    private List<Subscription> subscriptions;
    
    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;
    
    public Customer() {
        subscriptions = new ArrayList<>();
        transactions = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public Customer(String customerName, String customerEmail, String customerPassword, String customerCCNum, Integer customerCVV, String address) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerCCNum = customerCCNum;
        this.customerCVV = customerCVV;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    
    
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the customerId fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + customerId + " ]";
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }
    
    public Double getAccumulatedCashback() {
        return accumulatedCashback;
    }

    public void setAccumulatedCashback(Double accumulatedCashback) {
        this.accumulatedCashback = accumulatedCashback;
    }
    
    public String getCustomerCCNum() {
        return customerCCNum;
    }

    public void setCustomerCCNum(String customerCCNum) {
        this.customerCCNum = customerCCNum;
    }

    public Integer getCustomerCVV() {
        return customerCVV;
    }

    public void setCustomerCVV(Integer customerCVV) {
        this.customerCVV = customerCVV;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
