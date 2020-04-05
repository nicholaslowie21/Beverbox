/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import java.util.List;

/**
 *
 * @author User
 */
public class RetrieveSubTransactions {
    List<SubTransaction> subTransactions;

    public RetrieveSubTransactions() {
    }

    public RetrieveSubTransactions(List<SubTransaction> subTransactions) {
        this.subTransactions = subTransactions;
    }

    public List<SubTransaction> getSubTransactions() {
        return subTransactions;
    }

    public void setSubTransactions(List<SubTransaction> subTransactions) {
        this.subTransactions = subTransactions;
    }
    
    
}
