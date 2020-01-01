package com.sauljohnson.sugarsnap;

/**
 * Represents a symbol token for use with the {@link ShuntingYardParser}.
 *
 * @since 01/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardSymbol<T> {

    private T value;

    protected ShuntingYardSymbolType type;

    protected ShuntingYardSymbol(T value, ShuntingYardSymbolType type) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public ShuntingYardSymbolType getType() {
        return type;
    }
}
