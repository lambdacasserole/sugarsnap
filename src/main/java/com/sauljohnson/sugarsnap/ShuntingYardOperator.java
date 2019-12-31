package com.sauljohnson.sugarsnap;

public abstract class ShuntingYardOperator<T> implements ShuntingYardSymbol<T> {
    public ShuntingYardSymbolType getType() {
        return ShuntingYardSymbolType.OPERATOR;
    }

    abstract int getPrecedence();

    abstract boolean isLeftAssociative();
}
