package com.sauljohnson.sugarsnap;

/**
 * Represents a number token for use with the {@link ShuntingYardParser}.
 *
 * @since 02/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardNumber<T> extends ShuntingYardSymbol<T> {

    /**
     * Initialises a new instance of a number token for use with the {@link ShuntingYardParser}.
     *
     * @param value the underlying token value
     */
    public ShuntingYardNumber(T value) {
        super(value, ShuntingYardSymbolType.NUMBER);
    }
}
