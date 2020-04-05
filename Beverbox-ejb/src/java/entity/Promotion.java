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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author User
 */
@Entity
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promoId;

    @Column(nullable = false,length = 32)
    @NotNull
    @Size(max = 32)
    private String promoName;
    
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String promoType;
    
    @Column(nullable = false)
    private Integer promoPercentage;
    
    @Column(unique = true, nullable = false)
    private String promoCode;
    
    @Column(nullable = false)
    private boolean active = true;
    
    @OneToMany
    private List<Transaction> transactions = new ArrayList<>();

    public Promotion(){
        
    }
    public Promotion(String promoName, String promoType, Integer promoPercentage, String promoCode) {
        this.promoName = promoName;
        this.promoType = promoType;
        this.promoPercentage = promoPercentage;
        this.promoCode = promoCode;
        this.active = true;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

    public Integer getPromoPercentage() {
        return promoPercentage;
    }

    public void setPromoPercentage(Integer promoPercentage) {
        this.promoPercentage = promoPercentage;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promoId != null ? promoId.hashCode() : 0);
        return hash;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the promoId fields are not set
        if (!(object instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) object;
        if ((this.promoId == null && other.promoId != null) || (this.promoId != null && !this.promoId.equals(other.promoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Promotion[ id=" + promoId + " ]";
    }
    
}
