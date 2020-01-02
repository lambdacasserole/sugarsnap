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
