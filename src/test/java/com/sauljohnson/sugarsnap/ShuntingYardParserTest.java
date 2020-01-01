package com.sauljohnson.sugarsnap;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShuntingYardParserTest {

    @org.junit.jupiter.api.Test
    void parse() {
        // Test case: 3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3
        List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
                new ShuntingYardSymbol<String>("3", ShuntingYardSymbolType.NUMBER),
                new ShuntingYardOperator<String>("+", 2, true),
                new ShuntingYardSymbol<String>("4", ShuntingYardSymbolType.NUMBER),
                new ShuntingYardOperator<String>("*", 3, true),
                new ShuntingYardSymbol<String>("2", ShuntingYardSymbolType.NUMBER),
                new ShuntingYardOperator<String>("/", 3, true),
                new ShuntingYardLeftParen<String>("("),
                new ShuntingYardSymbol<String>("1", ShuntingYardSymbolType.NUMBER),
                new ShuntingYardOperator<String>("-", 2, true),
                new ShuntingYardSymbol<String>("5", ShuntingYardSymbolType.NUMBER),
                new ShuntingYardRightParen<String>(")"),
                new ShuntingYardOperator<String>("^", 4, false),
                new ShuntingYardSymbol<String>("2", ShuntingYardSymbolType.NUMBER),
                new ShuntingYardOperator<String>("^", 4, false),
                new ShuntingYardSymbol<String>("3", ShuntingYardSymbolType.NUMBER)));

        // Expected output: 3 4 2 * 1 5 - 2 3 ^ ^ / +
        ShuntingYardParser<String> parser = new ShuntingYardParser<String>(input);
        List<ShuntingYardSymbol<String>> output = parser.parse();
        StringBuilder sb = new StringBuilder();
        for (ShuntingYardSymbol<String> stringShuntingYardSymbol : output) {
            sb.append(stringShuntingYardSymbol.getValue());
        }
        assertEquals("342*15-23^^/+", sb.toString());
    }
}