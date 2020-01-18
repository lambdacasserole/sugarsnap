package com.sauljohnson.sugarsnap;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a node in an expression parse tree.
 *
 * @since 16/01/2020
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public class ParseTreeNode<T> {

    private T value;

    private List<ParseTreeNode<T>> children;

    /**
     * Initialises a new instance of a node in an expression parse tree.
     *
     * @param value     the value of the node
     * @param children  the children of the node
     */
    public ParseTreeNode(T value, List<ParseTreeNode<T>> children) {
        this.value = value;
        this.children = children;
    }

    /**
     * Initialises a new instance of a terminal node in an expression parse tree.
     *
     * @param value the value of the node
     */
    public ParseTreeNode(T value) {
        this(value, new LinkedList<ParseTreeNode<T>>());
    }

    /**
     * Gets the value of the node
     *
     * @return  the value of the node
     */
    public T getValue() {
        return value;
    }

    /**
     * Gets the children of the node.
     *
     * @return  the children of the node
     */
    public List<ParseTreeNode<T>> getChildren() {
        return children;
    }

    /**
     * Returns true if this node has children, otherwise returns false.
     *
     * @return  true if this node has children, otherwise false
     */
    public boolean hasChildren() {
        return children.size() == 0;
    }
}
