package com.wqzeng.springbtgradle.structure;

public class BTreeNode<T> {
    public T element;
    public BTreeNode<T> left;
    public BTreeNode<T> right;

    public BTreeNode(T element) {
        this.element = element;
    }
}
