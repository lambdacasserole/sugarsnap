package com.sauljohnson.sugarsnap;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

public class ShuntingYardParser<T> {

    private ShuntingYardSymbol<T>[] symbols;

    public ShuntingYardParser(ShuntingYardSymbol<T>[] symbols) {
        this.symbols = symbols;
    }

    public ShuntingYardSymbol<T>[] parse() {
        // Queue up symbols to read.
        Queue<ShuntingYardSymbol<T>> input = new LinkedList<ShuntingYardSymbol<T>>(Arrays.asList(symbols));

        // Prepare both stacks.
        Queue<ShuntingYardSymbol<T>> output = new LinkedList<ShuntingYardSymbol<T>>();
        Stack<ShuntingYardSymbol<T>> operators = new Stack<ShuntingYardSymbol<T>>();

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
                    operators.push(buffer);
                    System.out.println("Pushed function or left paren to operators.");
                    break;
                case OPERATOR:
                    ShuntingYardOperator<T> bufferOperator = (ShuntingYardOperator<T>) buffer;
                    if (!operators.isEmpty()) {
                        ShuntingYardOperator<T> stackOperator = (ShuntingYardOperator<T>) operators.peek();
                        while (!operators.isEmpty()
                                && (stackOperator.getType() == ShuntingYardSymbolType.FUNCTION
                                    || stackOperator.getPrecedence() > bufferOperator.getPrecedence()
                                    || (stackOperator.getPrecedence() == bufferOperator.getPrecedence()
                                        && stackOperator.isLeftAssociative()))
                                && stackOperator.getType() != ShuntingYardSymbolType.LEFT_PAREN) {
                            output.add(operators.pop());
                            System.out.println("Popped from operators to output.");
                        }
                    }
                    operators.push(buffer);
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
            System.out.println("Popped from operators to output.");
        }
        // Pass back output.
        return output.toArray(new ShuntingYardSymbol[] {});
    }
}
