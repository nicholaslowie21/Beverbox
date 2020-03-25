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
public class PromoCodeNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>PromoCodeNotFoundException</code> without
     * detail message.
     */
    public PromoCodeNotFoundException() {
    }

    /**
     * Constructs an instance of <code>PromoCodeNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PromoCodeNotFoundException(String msg) {
        super(msg);
    }
}
