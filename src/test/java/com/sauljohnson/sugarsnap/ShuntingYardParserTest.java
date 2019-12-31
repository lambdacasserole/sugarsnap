package com.sauljohnson.sugarsnap;

import static org.junit.jupiter.api.Assertions.*;

class ShuntingYardParserTest {

    @org.junit.jupiter.api.Test
    void parse() {
        // Test case: 3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3
        ShuntingYardSymbol<String>[] input = new ShuntingYardSymbol[] {
                new TestShuntingYardNumber("3"),
                new TestShuntingYardOperator(2, true, "+"),
                new TestShuntingYardNumber("4"),
                new TestShuntingYardOperator(3, true, "*"),
                new TestShuntingYardNumber("2"),
                new TestShuntingYardOperator(3, true, "/"),
                new TestShuntingYardLeftParen(),
                new TestShuntingYardNumber("1"),
                new TestShuntingYardOperator(2, true, "-"),
                new TestShuntingYardNumber("5"),
                new TestShuntingYardRightParen(),
                new TestShuntingYardOperator(4, false, "^"),
                new TestShuntingYardNumber("2"),
                new TestShuntingYardOperator(4, false, "^"),
                new TestShuntingYardNumber("3"),
        };

        ShuntingYardParser<String> parser = new ShuntingYardParser<String>(input);
        ShuntingYardSymbol<String>[] output = parser.parse();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < output.length; i++) {
            sb.append(output[i].getValue());
        }
        String g = sb.toString();
    }
}