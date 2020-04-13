/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author User
 */
public class InvalidPromotionException extends Exception {

    /**
     * Creates a new instance of <code>InvalidPromotionException</code> without
     * detail message.
     */
    public InvalidPromotionException() {
    }

    /**
     * Constructs an instance of <code>InvalidPromotionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidPromotionException(String msg) {
        super(msg);
    }
}
