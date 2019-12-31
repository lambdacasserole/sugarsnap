package com.sauljohnson.sugarsnap;

public class TestShuntingYardOperator extends ShuntingYardOperator<String> {

    private int precedence;

    private boolean isLeftAssociative;

    private String value;

    public TestShuntingYardOperator(int precedence, boolean isLeftAssociative, String value) {
        this.precedence = precedence;
        this.isLeftAssociative = isLeftAssociative;
        this.value = value;
    }

    int getPrecedence() {
        return precedence;
    }

    boolean isLeftAssociative() {
        return isLeftAssociative;
    }

    public String getValue() {
        return value;
    }
}
