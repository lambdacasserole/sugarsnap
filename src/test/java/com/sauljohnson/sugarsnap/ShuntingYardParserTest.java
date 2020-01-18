package com.sauljohnson.sugarsnap;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A suite of unit tests for the {@link ShuntingYardParser} class.
 *
 * @since 01/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
class ShuntingYardParserTest {

    /**
     * Concatenates a list of string symbols and returns the result.
     *
     * @param stringSymbols the list of string symbols to concatenate
     * @return              the result of concatenation
     */
    private String concatStringSymbols(List<ShuntingYardSymbol<String>> stringSymbols) {
        StringBuilder sb = new StringBuilder();
        for (ShuntingYardSymbol<String> stringShuntingYardSymbol : stringSymbols) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(stringShuntingYardSymbol.getValue());
        }
        return sb.toString();
    }

    /**
     * Converts a parse tree to a string, for easy comparison.
     *
     * @param tree  the tree to convert
     * @return      the resulting string
     */
    private String stringifyTree(ParseTreeNode<String> tree) {
        StringBuilder output = new StringBuilder(tree.hasChildren() ? "" : "(");
        output.append(tree.getValue());
        for (ParseTreeNode<String> child : tree.getChildren()) {
            output.append(" ").append(stringifyTree(child));
        }
        output.append(tree.hasChildren() ? "" : ")");
        return output.toString();
    }

    @org.junit.jupiter.api.Test
    void testParenthesisShunting() {
        // Test case: 3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardOperator<String>("+", 2, true),
                new ShuntingYardNumber<String>("4"),
                new ShuntingYardOperator<String>("*", 3, true),
                new ShuntingYardNumber<String>("2"),
                new ShuntingYardOperator<String>("/", 3, true),
                new ShuntingYardLeftParen<String>("("),
                new ShuntingYardNumber<String>("1"),
                new ShuntingYardOperator<String>("-", 2, true),
                new ShuntingYardNumber<String>("5"),
                new ShuntingYardRightParen<String>(")"),
                new ShuntingYardOperator<String>("^", 4, false),
                new ShuntingYardNumber<String>("2"),
                new ShuntingYardOperator<String>("^", 4, false),
                new ShuntingYardNumber<String>("3")));

        // Expected output: 3 4 2 * 1 5 - 2 3 ^ ^ / +
        ShuntingYardParser<String> parser = new ShuntingYardParser<String>();
        try {
            List<ShuntingYardSymbol<String>> output = parser.shunt(input);
            assertEquals("3 4 2 * 1 5 - 2 3 ^ ^ / +", concatStringSymbols(output));
        } catch (ShuntingYardParserException e) {
            e.printStackTrace();
            fail("Exception encountered while parsing valid expression.");
        }
    }

    @org.junit.jupiter.api.Test
    void testFunctionShunting() {
        // Test case: sin ( max ( 2 , 3 ) / 3 * pi )
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardFunction<String>("sin", 1),
                new ShuntingYardLeftParen<String>("("),
                new ShuntingYardFunction<String>("max", 2),
                new ShuntingYardLeftParen<String>("("),
                new ShuntingYardNumber<String>("2"),
                new ShuntingYardIgnorable<String>(","),
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardRightParen<String>(")"),
                new ShuntingYardOperator<String>("/", 3, true),
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardOperator<String>("*", 3, true),
                new ShuntingYardNumber<String>("pi"),
                new ShuntingYardRightParen<String>(")")));

        // Expected output: 2 3 max 3 / pi * sin
        ShuntingYardParser<String> parser = new ShuntingYardParser<String>();
        try {
            List<ShuntingYardSymbol<String>> output = parser.shunt(input);
            assertEquals("2 3 max 3 / pi * sin", concatStringSymbols(output));
        } catch (ShuntingYardParserException e) {
            e.printStackTrace();
            fail("Exception encountered while parsing valid expression.");
        }
    }

    @org.junit.jupiter.api.Test
    void testMismatchedLeftParenShunting() {
        // Test case: sin ( max ( 2 , 3 ) / 3 * pi
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardFunction<String>("sin", 1),
                new ShuntingYardLeftParen<String>("("),
                new ShuntingYardFunction<String>("max", 2),
                new ShuntingYardLeftParen<String>("("),
                new ShuntingYardNumber<String>("2"),
                new ShuntingYardIgnorable<String>(","),
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardRightParen<String>(")"),
                new ShuntingYardOperator<String>("/", 3, true),
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardOperator<String>("*", 3, true),
                new ShuntingYardNumber<String>("pi")));

        // Parse failure expected due to mismatched left parenthesis.
        ShuntingYardParser<String> parser = new ShuntingYardParser<String>();
        try {
            parser.shunt(input);
            fail("Exception was not thrown for mismatched left parenthesis.");
        } catch (ShuntingYardParserException e) {
            assertTrue(e.getMessage().toLowerCase().contains("parentheses"));
        }
    }

    @org.junit.jupiter.api.Test
    void testMismatchedRightParenShunting() {
        // Test case: sin ( max ( 2 , 3 ) / 3 * pi
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardFunction<String>("sin", 1),
                new ShuntingYardFunction<String>("max", 2),
                new ShuntingYardLeftParen<String>("("),
                new ShuntingYardNumber<String>("2"),
                new ShuntingYardIgnorable<String>(","),
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardRightParen<String>(")"),
                new ShuntingYardOperator<String>("/", 3, true),
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardOperator<String>("*", 3, true),
                new ShuntingYardNumber<String>("pi"),
                new ShuntingYardRightParen<String>(")")));

        // Parse failure expected due to mismatched right parenthesis.
        ShuntingYardParser<String> parser = new ShuntingYardParser<String>();
        try {
            parser.shunt(input);
            fail("Exception was not thrown for mismatched right parenthesis.");
        } catch (ShuntingYardParserException e) {
            assertTrue(e.getMessage().toLowerCase().contains("parentheses"));
        }
    }

    @org.junit.jupiter.api.Test
    void testParenthesisParsing() {
        // Test case: 3 4 2 * 1 5 - 2 3 ^ ^ / +
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardNumber<String>("4"),
                new ShuntingYardNumber<String>("2"),
                new ShuntingYardOperator<String>("*", 3, true),
                new ShuntingYardNumber<String>("1"),
                new ShuntingYardNumber<String>("5"),
                new ShuntingYardOperator<String>("-", 2, true),
                new ShuntingYardNumber<String>("2"),
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardOperator<String>("^", 4, false),
                new ShuntingYardOperator<String>("^", 4, false),
                new ShuntingYardOperator<String>("/", 3, true),
                new ShuntingYardOperator<String>("+", 2, true)));

        // Expected output: (+ (/ (^ (^ 3 2) (- 5 1)) (* 2 4)) 3)
        ShuntingYardParser<String> parser = new ShuntingYardParser<String>();
        try {
            ParseTreeNode<String> output = parser.generateParseTree(input);
            assertEquals("(+ (/ (^ (^ 3 2) (- 5 1)) (* 2 4)) 3)", stringifyTree(output));
        } catch (ShuntingYardParserException e) {
            e.printStackTrace();
            fail("Exception encountered while generating parse tree for valid expression.");
        }
    }

    @org.junit.jupiter.api.Test
    void testFunctionParsing() {
        // Test case: 2 3 max 3 / pi * sin
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardNumber<String>("2"),
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardFunction<String>("max", 2),
                new ShuntingYardNumber<String>("3"),
                new ShuntingYardOperator<String>("/", 3, true),
                new ShuntingYardNumber<String>("pi"),
                new ShuntingYardOperator<String>("*", 3, true),
                new ShuntingYardFunction<String>("sin", 1)));

        // Expected output: (sin (* pi (/ 3 (max 3 2))))
        ShuntingYardParser<String> parser = new ShuntingYardParser<String>();
        try {
            ParseTreeNode<String> output = parser.generateParseTree(input);
            String tt = stringifyTree(output);
            assertEquals("(sin (* pi (/ 3 (max 3 2))))", stringifyTree(output));
        } catch (ShuntingYardParserException e) {
            e.printStackTrace();
            fail("Exception encountered while generating parse tree for valid expression.");
        }
    }
}
