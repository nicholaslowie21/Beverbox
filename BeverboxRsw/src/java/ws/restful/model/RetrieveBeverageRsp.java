/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Beverage;

/**
 *
 * @author boonghim
 */
public class RetrieveBeverageRsp {
    private Beverage beverage;

    public RetrieveBeverageRsp() {
    }

    public RetrieveBeverageRsp(Beverage beverage) {
        this.beverage = beverage;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }
    
    
    
}
