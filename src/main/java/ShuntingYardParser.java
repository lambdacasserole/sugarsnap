import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ShuntingYardParser {

    private Symbol[] symbols;

    public ShuntingYardParser(Symbol[] symbols) {
        this.symbols = symbols;
    }

    public void parse() {
        // Queue up symbols to read.
        Queue<Symbol> input = new LinkedList<Symbol>();
        input.addAll(Arrays.asList(symbols));

        // Prepare both stacks.
        Queue<Symbol> output = new LinkedList<Symbol>();
        Stack<Symbol> operators = new Stack<Symbol>();

        // Shunting yard algorithm.
        while(!input.isEmpty()) {
            Symbol buffer = input.remove();
            if (buffer.isNumber()) {
                output.add(buffer);
            } else if (buffer.isFunction()) {
                operators.push(buffer);
            } else if (buffer.isOperator()) {
                Symbol operator = operators.peek();
                while ((operator.isFunction()
                        || operator.getPrecedence() > buffer.getPrecedence()
                        || (operator.getPrecedence() == buffer.getPrecedence() && operator.isLeftAssociative()))
                        && !operator.isLeftParenthesis()) {
                    while (!operators.isEmpty()) {
                        output.add(operators.pop());
                    }
                }
                operators.push(buffer);
            } else if (buffer.isLeftParenthesis()) {
                operators.push(buffer);
            } else if (buffer.isRightParenthesis()) {
                while (!operators.peek().isLeftParenthesis()) {
                    output.add(operators.pop());
                    // Run out? Then mismatched brackets.
                }
                operators.pop();
            }
        }
//        /* if the operator token on the top of the stack is a paren, then there are mismatched parentheses. */
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }
    }


//    if the token is an operator, then:
//            while ((there is a function at the top of the operator stack)
//    or (there is an operator at the top of the operator stack with greater precedence)
//    or (the operator at the top of the operator stack has equal precedence and is left associative))
//    and (the operator at the top of the operator stack is not a left parenthesis):
//    pop operators from the operator stack onto the output queue.
//    push it onto the operator stack.
//            if the token is a left paren (i.e. "("), then:
//    push it onto the operator stack.
//            if the token is a right paren (i.e. ")"), then:
//            while the operator at the top of the operator stack is not a left paren:
//    pop the operator from the operator stack onto the output queue.
//    /* if the stack runs out without finding a left paren, then there are mismatched parentheses. */
//        if there is a left paren at the top of the operator stack, then:
//    pop the operator from the operator stack and discard it
//    /* After while loop, if operator stack not null, pop everything to output queue */
//if there are no more tokens to read then:
//            while there are still operator tokens on the stack:
//    /* if the operator token on the top of the stack is a paren, then there are mismatched parentheses. */
//    pop the operator from the operator stack onto the output queue.
//            exit.
//
}
