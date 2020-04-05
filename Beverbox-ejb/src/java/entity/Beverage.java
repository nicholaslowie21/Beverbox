/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 *
 * @author boonghim
 */
@Entity
public class Beverage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beverageId;
    @Column(nullable = false, length = 64)
    @Size(max = 64)
    private String beverageName;
    @Column(length = 64)
    @Size(max = 64)
    private String beverageDesc;
    @Column(length = 64)
    @Size(max = 64)
    private String country;
    private String type;
    @Column(nullable = false)
    private Boolean active;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer quantityOnHand;
    
    @ManyToMany
    private List<Box> boxes;
    
    @OneToMany(mappedBy = "beverage")
    private List<Transaction> transactions = new ArrayList<>();
    
    public Beverage() {
    }

    public Beverage(String beverageName, String beverageDesc, String country, String type, Double price, Integer quantityOnHand) {
        this.beverageName = beverageName;
        this.beverageDesc = beverageDesc;
        this.country = country;
        this.type = type;
        this.price = price;
        this.quantityOnHand = quantityOnHand;
        this.active = true;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public void setBeverageName(String beverageName) {
        this.beverageName = beverageName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBeverageDesc() {
        return beverageDesc;
    }

    public void setBeverageDesc(String beverageDesc) {
        this.beverageDesc = beverageDesc;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
    
    public Long getBeverageId() {
        return beverageId;
    }

    public void setBeverageId(Long beverageId) {
        this.beverageId = beverageId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (beverageId != null ? beverageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the beverageId fields are not set
        if (!(object instanceof Beverage)) {
            return false;
        }
        Beverage other = (Beverage) object;
        if ((this.beverageId == null && other.beverageId != null) || (this.beverageId != null && !this.beverageId.equals(other.beverageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Beverage[ id=" + beverageId + " ]";
    }
    
}
