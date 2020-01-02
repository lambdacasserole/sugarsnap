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

    /**
     * Initialises a new instance of a symbol token for use with the {@link ShuntingYardParser}.
     *
     * @param value the underlying token value
     * @param type  the type of symbol to be represented
     */
    protected ShuntingYardSymbol(T value, ShuntingYardSymbolType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Gets the value that underlies this symbol.
     *
     * @return  the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Gets the type of symbol represented.
     *
     * @return  the symbol type
     */
    public ShuntingYardSymbolType getType() {
        return type;
    }
}
