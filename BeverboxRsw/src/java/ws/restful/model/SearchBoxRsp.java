/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Box;
import java.util.List;

/**
 *
 * @author boonghim
 */
public class SearchBoxRsp {
    private List<Box> boxes;

    public SearchBoxRsp() {
    }

    public SearchBoxRsp(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
    
    
    
}