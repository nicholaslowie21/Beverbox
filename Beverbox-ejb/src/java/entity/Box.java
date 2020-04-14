/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
public class Box implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boxId;
    @Column(nullable = false, length = 64)
    @Size(max = 64)
    private String boxName;
    @Column(nullable = true, length = 32)
    @Size(max = 32)
    private String boxOrigin;
    @Column(nullable = true, length = 32)
    @Size(max = 1000)
    private String boxDesc;
    @Column(nullable = false)
    private Boolean active = true;

    @ManyToMany(mappedBy = "boxes")
    private List<Beverage> beverages;
    
    @OneToMany(mappedBy="box")
    private List<Review> reviews; 
    
    public Box() {
    }

    public Box(String boxName, String boxOrigin, String boxDesc) {
        this.boxName = boxName;
        this.boxOrigin = boxOrigin;
        this.boxDesc = boxDesc;
        this.active = true;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public String getBoxOrigin() {
        return boxOrigin;
    }

    public void setBoxOrigin(String boxOrigin) {
        this.boxOrigin = boxOrigin;
    }

    public String getBoxDesc() {
        return boxDesc;
    }

    public void setBoxDesc(String boxDesc) {
        this.boxDesc = boxDesc;
    }
    
    

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<Beverage> beverages) {
        this.beverages = beverages;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (boxId != null ? boxId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the boxId fields are not set
        if (!(object instanceof Box)) {
            return false;
        }
        Box other = (Box) object;
        if ((this.boxId == null && other.boxId != null) || (this.boxId != null && !this.boxId.equals(other.boxId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Box[ id=" + boxId + " ]";
    }
    
}
