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
            sb.append(stringShuntingYardSymbol.getValue());
        }
        return sb.toString();
    }

    @org.junit.jupiter.api.Test
    void testParenthesisParsing() {
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
            List<ShuntingYardSymbol<String>> output = parser.parse(input);
            assertEquals("342*15-23^^/+", concatStringSymbols(output));
        } catch (ShuntingYardParserException e) {
            e.printStackTrace();
            fail("Exception encountered while parsing valid expression.");
        }
    }

    @org.junit.jupiter.api.Test
    void testFunctionParsing() {
        // Test case: sin ( max ( 2 , 3 ) / 3 * pi )
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardFunction<String>("sin"),
                new ShuntingYardLeftParen<String>("("),
                new ShuntingYardFunction<String>("max"),
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
            List<ShuntingYardSymbol<String>> output = parser.parse(input);
            assertEquals("23max3/pi*sin", concatStringSymbols(output));
        } catch (ShuntingYardParserException e) {
            e.printStackTrace();
            fail("Exception encountered while parsing valid expression.");
        }
    }

    @org.junit.jupiter.api.Test
    void testMismatchedLeftParen() {
        // Test case: sin ( max ( 2 , 3 ) / 3 * pi
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardFunction<String>("sin"),
                new ShuntingYardLeftParen<String>("("),
                new ShuntingYardFunction<String>("max"),
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
            List<ShuntingYardSymbol<String>> output = parser.parse(input);
            fail("Exception was not thrown for mismatched left parenthesis.");
        } catch (ShuntingYardParserException e) {
            assertTrue(e.getMessage().toLowerCase().contains("parentheses"));
        }
    }

    @org.junit.jupiter.api.Test
    void testMismatchedRightParen() {
        // Test case: sin ( max ( 2 , 3 ) / 3 * pi
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardFunction<String>("sin"),
                new ShuntingYardFunction<String>("max"),
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
            List<ShuntingYardSymbol<String>> output = parser.parse(input);
            fail("Exception was not thrown for mismatched right parenthesis.");
        } catch (ShuntingYardParserException e) {
            assertTrue(e.getMessage().toLowerCase().contains("parentheses"));
        }
    }
}