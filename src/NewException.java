/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author d706397
 */
public class NewException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of <code>NewException</code> without detail message.
     */
    public NewException() {
    }

    /**
     * Constructs an instance of <code>NewException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NewException(String msg) {
        super(msg);
    }
}
