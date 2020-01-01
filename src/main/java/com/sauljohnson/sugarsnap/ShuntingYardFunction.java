package com.sauljohnson.sugarsnap;

/**
 * Represents a function token for use with the {@link ShuntingYardParser}.
 *
 * @since 01/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardFunction<T> extends ShuntingYardOperator<T> {

    protected ShuntingYardFunction(T value) {
        super(value, Integer.MAX_VALUE, false);
        type = ShuntingYardSymbolType.FUNCTION;
    }
}
