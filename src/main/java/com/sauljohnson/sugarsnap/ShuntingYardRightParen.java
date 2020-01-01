package com.sauljohnson.sugarsnap;

public class ShuntingYardRightParen<T> extends ShuntingYardOperator<T> {

    protected ShuntingYardRightParen(T value) {
        super(value, Integer.MAX_VALUE, false);
        type = ShuntingYardSymbolType.RIGHT_PAREN;
    }
}
