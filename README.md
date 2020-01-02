# Sugarsnap
Super generic and reusable shunting and parsing library.

![Logo](assets/logo-text-h.svg)

## Overview
This library implements Dijkstra's [Shunting-yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm) in Java, with generics to permit the type-safe attachment of arbitrary objects to symbols.

The algorithm functions to convert mathematical expressions written in [infix notation](https://en.wikipedia.org/wiki/Infix_notation) to [reverse Polish notation (RPN)](https://en.wikipedia.org/wiki/Reverse_Polish_notation) for easy evaluation using a stack machine. Sugarsnap currenty supports:
* Functions
* Operator precedence
* Left/right operator associativity
* Parenthesised expressions

There is currently no support for:
* Function composition
* Variadic functions
* Postfix unary operators (e.g. factorial "!")

Mismatched brackets etc. will throw exceptions, which can be caught and handled.

## Usage
The purpose of this library is to convert infix notation to RPN. That is to say, it does not contain a tokeniser. Once you've wrangled your string into a list of tokens, here's a quick demonstration of what you need to do:

```java
// Expression: 3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3
// Expression given as list of symbols with strings attached.
List<ShuntingYardSymbol<String>> input = new LinkedList<ShuntingYardSymbol<String>>(Arrays.asList(
        new ShuntingYardSymbol<String>("3", ShuntingYardSymbolType.NUMBER), // A number.
        // An operator, with precedence 2 and left associativity.
        new ShuntingYardOperator<String>("+", 2, true),
        new ShuntingYardSymbol<String>("4", ShuntingYardSymbolType.NUMBER),
        new ShuntingYardOperator<String>("*", 3, true),
        new ShuntingYardSymbol<String>("2", ShuntingYardSymbolType.NUMBER),
        new ShuntingYardOperator<String>("/", 3, true),
        new ShuntingYardLeftParen<String>("("), // An opening parenthesis.
        new ShuntingYardSymbol<String>("1", ShuntingYardSymbolType.NUMBER),
        new ShuntingYardOperator<String>("-", 2, true),
        new ShuntingYardSymbol<String>("5", ShuntingYardSymbolType.NUMBER),
        new ShuntingYardRightParen<String>(")"), // A closing parenthesis.
        // Precedence 4 exponentiation operator with right associativity.
        new ShuntingYardOperator<String>("^", 4, false), 
        new ShuntingYardSymbol<String>("2", ShuntingYardSymbolType.NUMBER),
        new ShuntingYardOperator<String>("^", 4, false),
        new ShuntingYardSymbol<String>("3", ShuntingYardSymbolType.NUMBER)));
        
// Parse expression.
ShuntingYardParser<String> parser = new ShuntingYardParser<String>();
try {
    List<ShuntingYardSymbol<String>> output = parser.parse(input);
    // Resulting list looks like this (as a string): "342*15-23^^/+"
    // Do what you need to do with result...
} catch (ShuntingYardParserException e) {
    // Exception encountered while parsing expression.
    e.printStackTrace();
} 
```
