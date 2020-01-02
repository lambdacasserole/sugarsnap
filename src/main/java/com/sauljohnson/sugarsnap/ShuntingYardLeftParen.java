package com.sauljohnson.sugarsnap;

/**
 * Represents a left parenthesis token for use with the {@link ShuntingYardParser}.
 *
 * @since 01/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardLeftParen<T> extends ShuntingYardOperator<T> {

    /**
     * Initialises a new instance of a left parenthesis token for use with the {@link ShuntingYardParser}.
     *
     * @param value the underlying token value
     */
    public ShuntingYardLeftParen(T value) {
        super(value, Integer.MAX_VALUE, false);
        type = ShuntingYardSymbolType.LEFT_PAREN;
    }
}
