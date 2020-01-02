package com.sauljohnson.sugarsnap;

/**
 * An enumeration of different types of symbol for use with the {@link ShuntingYardParser}.
 *
 * @since 01/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public enum ShuntingYardSymbolType {
    NUMBER,
    OPERATOR,
    FUNCTION,
    LEFT_PAREN,
    RIGHT_PAREN,
    IGNORABLE
}
