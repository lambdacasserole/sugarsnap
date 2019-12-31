package com.sauljohnson.sugarsnap;

public interface ShuntingYardSymbol<T> {
    T getValue();

    ShuntingYardSymbolType getType();
}
