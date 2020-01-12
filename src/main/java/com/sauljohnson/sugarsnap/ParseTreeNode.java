package com.sauljohnson.sugarsnap;

import java.util.LinkedList;
import java.util.List;

public class ParseTreeNode<T> {

    private T value;

    private List<ParseTreeNode<T>> children;

    public ParseTreeNode(T value, List<ParseTreeNode<T>> children) {
        this.value = value;
        this.children = children;
    }

    public ParseTreeNode(T value) {
        this(value, new LinkedList<ParseTreeNode<T>>());
    }

    public T getValue() {
        return value;
    }

    public List<ParseTreeNode<T>> getChildren() {
        return children;
    }

    public void addChild(ParseTreeNode<T> child) {
        children.add(child);
    }
}
