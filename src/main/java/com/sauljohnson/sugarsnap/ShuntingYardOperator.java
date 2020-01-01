package com.sauljohnson.sugarsnap;

/**
 * Represents an operator token for use with the {@link ShuntingYardParser}.
 *
 * @since 01/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardOperator<T> extends ShuntingYardSymbol<T> {

    private int precedence;

    private boolean leftAssociative;

    protected ShuntingYardOperator(T value, int precedence, boolean leftAssociative) {
        super(value, ShuntingYardSymbolType.OPERATOR);
        this.precedence = precedence;
        this.leftAssociative = leftAssociative;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isLeftAssociative() {
        return leftAssociative;
    }
}
