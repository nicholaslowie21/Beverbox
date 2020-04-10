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
public class QuantityLimitException extends Exception {

    /**
     * Creates a new instance of <code>QuantityLimitException</code> without
     * detail message.
     */
    public QuantityLimitException() {
    }

    /**
     * Constructs an instance of <code>QuantityLimitException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public QuantityLimitException(String msg) {
        super(msg);
    }
}
