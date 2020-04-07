/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Box;

/**
 *
 * @author boonghim
 */
public class RetrieveBoxRsp {
    private Box box;

    public RetrieveBoxRsp() {
    }

    public RetrieveBoxRsp(Box box) {
        this.box = box;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }
    
    
}
