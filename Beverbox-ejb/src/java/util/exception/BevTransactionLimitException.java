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
public class BevTransactionLimitException extends Exception {

    /**
     * Creates a new instance of <code>BevTransactionLimitException</code>
     * without detail message.
     */
    public BevTransactionLimitException() {
    }

    /**
     * Constructs an instance of <code>BevTransactionLimitException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public BevTransactionLimitException(String msg) {
        super(msg);
    }
}
