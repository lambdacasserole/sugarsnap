public interface Symbol {
    boolean isNumber();

    boolean isOperator();

    boolean isFunction();

    int getPrecedence();

    boolean isLeftAssociative();

    boolean isLeftParenthesis();

    boolean isRightParenthesis();
}
