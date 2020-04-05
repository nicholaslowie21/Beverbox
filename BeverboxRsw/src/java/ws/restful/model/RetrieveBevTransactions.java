/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class RetrieveBevTransactions {
    
    List<BevTransaction> bevTransactions;
    
    public RetrieveBevTransactions() {
    }

    public RetrieveBevTransactions(List<BevTransaction> bevTransactions) {
        this.bevTransactions = bevTransactions;
    }

    public List<BevTransaction> getBevTransactions() {
        return bevTransactions;
    }

    public void setBevTransactions(List<BevTransaction> bevTransactions) {
        this.bevTransactions = bevTransactions;
    }

    
    
    
    
}
