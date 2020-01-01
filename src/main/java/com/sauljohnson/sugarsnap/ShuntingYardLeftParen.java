package com.sauljohnson.sugarsnap;

public class ShuntingYardLeftParen<T> extends ShuntingYardOperator<T> {

    protected ShuntingYardLeftParen(T value) {
        super(value, Integer.MAX_VALUE, false);
        type = ShuntingYardSymbolType.LEFT_PAREN;
    }
}
