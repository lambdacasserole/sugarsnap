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

    /**
     * Initialises a new instance of an operator token for use with the {@link ShuntingYardParser}.
     *
     * @param value             the underlying token value
     * @param precedence        the operator precedence
     * @param leftAssociative   whether the operator is left associative (operator is right associative if false)
     */
    public ShuntingYardOperator(T value, int precedence, boolean leftAssociative) {
        super(value, ShuntingYardSymbolType.OPERATOR);
        this.precedence = precedence;
        this.leftAssociative = leftAssociative;
    }

    /**
     * Gets the precedence of this operator.
     *
     * @return  the precedence
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * Gets whether or not the operator is left associative (operator is right associative if false).
     *
     * @return  true if the operator is left associative, false if it is right associative
     */
    public boolean isLeftAssociative() {
        return leftAssociative;
    }
}
