package com.sauljohnson.sugarsnap;

/**
 * Represents an exception thrown by an instance of {@link ShuntingYardParser} due to a parse error.
 *
 * @since 02/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardParserException extends Exception {

    /**
     * Initialises a new instance of an exception thrown by an instance of {@link ShuntingYardParser} due to a parse
     * error.
     *
     * @param message   a message describing the exception
     */
    public ShuntingYardParserException(String message) {
        super(message);
    }
}
