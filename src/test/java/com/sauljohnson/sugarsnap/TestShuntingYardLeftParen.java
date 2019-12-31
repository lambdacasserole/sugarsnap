package com.sauljohnson.sugarsnap;

public class TestShuntingYardLeftParen extends ShuntingYardOperator<String> {

    public String getValue() {
        return "(";
    }

    public ShuntingYardSymbolType getType() {
        return ShuntingYardSymbolType.LEFT_PAREN;
    }

    int getPrecedence() {
        return Integer.MIN_VALUE;
    }

    boolean isLeftAssociative() {
        return false;
    }
}
