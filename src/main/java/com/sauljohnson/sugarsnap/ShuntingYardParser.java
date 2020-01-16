package com.sauljohnson.sugarsnap;

import java.util.*;

/**
 * An implementation of the shunting yard algorithm that can operate on generic tokens.
 *
 * @since 01/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardParser<T> {

    /**
     * Converts the given list of symbols from reverse Polish notation (RPN) to a parse tree.
     *
     * @param shunted                       the list of symbols to convert
     * @return                              the resulting parse tree
     * @throws ShuntingYardParserException  in case of parse error
     */
    private ParseTreeNode<T> generateParseTree(List<ShuntingYardSymbol<T>> shunted) throws ShuntingYardParserException {
        Stack<ParseTreeNode<T>> stack = new Stack<ParseTreeNode<T>>();
        for (ShuntingYardSymbol<T> symbol : shunted) {
            switch(symbol.getType()){
                case NUMBER:
                    // Push numbers (terminal nodes) straight on to stack.
                    stack.push(new ParseTreeNode<T>(symbol.getValue()));
                    break;
                case OPERATOR:
                    // Infix operator.
                    stack.push(new ParseTreeNode<T>(symbol.getValue(), Arrays.asList(stack.pop(), stack.pop())));
                    break;
                case FUNCTION:
                    // Function call.
                    ShuntingYardFunction<T> function = (ShuntingYardFunction<T>) symbol;
                    List<ParseTreeNode<T>> children = new LinkedList<ParseTreeNode<T>>();
                    for (int i = 0; i < function.getArity(); i++) {
                        if (stack.isEmpty()) {
                            throw new ShuntingYardParserException("Argument error.");
                        }
                        children.add(stack.pop());
                    }
                    stack.push(new ParseTreeNode<T>(symbol.getValue(), children));
                    break;
                default:
                    throw new ShuntingYardParserException("Don't know how to handle symbol with type " +
                            symbol.getType() + " here, make sure each symbol has a valid type.");
            }
        }
        // Result is left on stack.
        return stack.pop();
    }

    /**
     * Converts the given list of symbols from infix notation to reverse Polish notation (RPN) using the shunting yard
     * algorithm.
     *
     * @param symbols                       the list of symbols to parse in infix notation
     * @return                              the parsed list of symbols in reverse Polish notation (RPN)
     * @throws ShuntingYardParserException  in case of parse error
     */
    public List<ShuntingYardSymbol<T>> shunt(List<ShuntingYardSymbol<T>> symbols) throws ShuntingYardParserException {
        // Queue up symbols to read.
        Queue<ShuntingYardSymbol<T>> input = new LinkedList<ShuntingYardSymbol<T>>(symbols);

        // Prepare both stacks.
        Queue<ShuntingYardSymbol<T>> output = new LinkedList<ShuntingYardSymbol<T>>();
        Stack<ShuntingYardOperator<T>> operators = new Stack<ShuntingYardOperator<T>>();

        // Shunting yard algorithm.
        while(!input.isEmpty()) {
            ShuntingYardSymbol<T> buffer = input.remove();
            switch (buffer.getType()) {
                case NUMBER:
                    output.add(buffer);
                    break;
                case FUNCTION:
                case LEFT_PAREN:
                    operators.push((ShuntingYardOperator<T>) buffer);
                    break;
                case OPERATOR:
                    ShuntingYardOperator<T> bufferOperator = (ShuntingYardOperator<T>) buffer;
                    if (!operators.isEmpty()) {
                        while (!operators.isEmpty()
                                && (operators.peek().getType() == ShuntingYardSymbolType.FUNCTION
                                    || operators.peek().getPrecedence() > bufferOperator.getPrecedence()
                                    || (operators.peek().getPrecedence() == bufferOperator.getPrecedence()
                                        && operators.peek().isLeftAssociative()))
                                && operators.peek().getType() != ShuntingYardSymbolType.LEFT_PAREN) {
                            output.add(operators.pop());
                        }
                    }
                    operators.push(bufferOperator);
                    break;
                case RIGHT_PAREN:
                    while (!operators.isEmpty() && operators.peek().getType() != ShuntingYardSymbolType.LEFT_PAREN) {
                        output.add(operators.pop());
                    }
                    // If we emptied the stack and did not encounter a left parenthesis, mismatched bracket present.
                    if (operators.isEmpty()) {
                        throw new ShuntingYardParserException("Mismatched parentheses present in expression.");
                    }
                    operators.pop();
                    break;
                case IGNORABLE:
                    // Symbols that fall into here are considered ignorable and won't end up in output.
                    break;
                default:
                    throw new ShuntingYardParserException("Don't know how to handle symbol with type " +
                            buffer.getType() + " here, make sure each symbol has a valid type.");
            }
        }
        // Is there a paren on top of the stack? Then there are mismatched brackets.
        while (!operators.isEmpty()) {
            // If we encounter any more parentheses, they're mismatched.
            if (operators.peek().getType() == ShuntingYardSymbolType.LEFT_PAREN
                    || operators.peek().getType() == ShuntingYardSymbolType.RIGHT_PAREN) {
                throw new ShuntingYardParserException("Mismatched parentheses present in expression.");
            }
            output.add(operators.pop());
        }
        // Pass back output.
        return new LinkedList<ShuntingYardSymbol<T>>(output);
    }
}
