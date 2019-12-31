package com.sauljohnson.sugarsnap;

public class TestShuntingYardNumber implements ShuntingYardSymbol<String> {

    private String value;

    public TestShuntingYardNumber(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public ShuntingYardSymbolType getType() {
        return ShuntingYardSymbolType.NUMBER;
    }
}
