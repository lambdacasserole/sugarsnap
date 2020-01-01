package com.sauljohnson.sugarsnap;

import java.util.*;

/**
 * An implementation of the shunting yard algorithm that can operate on generic tokens.
 *
 * @since 01/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ShuntingYardParser<T> {

    private List<ShuntingYardSymbol<T>> symbols;

    public ShuntingYardParser(List<ShuntingYardSymbol<T>> symbols) {
        this.symbols = symbols;
    }

    public List<ShuntingYardSymbol<T>> parse() {
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
                    System.out.println("Pushed number to output.");
                    break;
                case FUNCTION:
                case LEFT_PAREN:
                    operators.push((ShuntingYardOperator<T>) buffer);
                    System.out.println("Pushed function or left paren to operators.");
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
                            System.out.println("Popped from operators to output.");
                        }
                    }
                    operators.push(bufferOperator);
                    System.out.println("Pushed operator to operators.");
                    break;
                case RIGHT_PAREN:
                    while (operators.peek().getType() != ShuntingYardSymbolType.LEFT_PAREN) {
                        output.add(operators.pop());
                        System.out.println("Popped from operators to output.");
                    }
                    operators.pop();
                    System.out.println("Popped and discarded operator.");
                    break;
                default:

            }
        }
        // Is there a paren on top of the stack? Then there are mismatched brackets.
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }
        // Pass back output.
        return new LinkedList<ShuntingYardSymbol<T>>(output);
    }
}
