/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Beverage;
import java.util.List;

/**
 *
 * @author boonghim
 */
public class RetrieveAllBeveragesRsp {

    private List<Beverage> beverages;

    public RetrieveAllBeveragesRsp() {
    }

    public RetrieveAllBeveragesRsp(List<Beverage> beverages) {
        this.beverages = beverages;
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<Beverage> beverages) {
        this.beverages = beverages;
    }
    
    

    
    

}
