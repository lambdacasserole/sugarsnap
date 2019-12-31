package com.sauljohnson.sugarsnap;

public class TestShuntingYardRightParen extends ShuntingYardOperator<String> {

    public String getValue() {
        return ")";
    }

    public ShuntingYardSymbolType getType() {
        return ShuntingYardSymbolType.RIGHT_PAREN;
    }

    int getPrecedence() {
        return Integer.MIN_VALUE;
    }

    boolean isLeftAssociative() {
        return false;
    }
}

