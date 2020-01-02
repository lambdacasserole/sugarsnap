package com.sauljohnson.sugarsnap;

/**
 * Represents an ignorable token for use with the {@link ShuntingYardParser}.
 *
 * @since 02/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardIgnorable<T> extends ShuntingYardSymbol<T> {

    /**
     * Initialises a new instance of an ignorable token for use with the {@link ShuntingYardParser}.
     *
     * @param value the underlying token value
     */
    public ShuntingYardIgnorable(T value) {
        super(value, ShuntingYardSymbolType.IGNORABLE);
    }
}
