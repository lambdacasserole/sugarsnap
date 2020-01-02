package com.sauljohnson.sugarsnap;

/**
 * Represents a right parenthesis token for use with the {@link ShuntingYardParser}.
 *
 * @since 01/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardRightParen<T> extends ShuntingYardOperator<T> {

    /**
     * Initialises a new instance of a right parenthesis token for use with the {@link ShuntingYardParser}.
     *
     * @param value the underlying token value
     */
    protected ShuntingYardRightParen(T value) {
        super(value, Integer.MAX_VALUE, false);
        type = ShuntingYardSymbolType.RIGHT_PAREN;
    }
}
