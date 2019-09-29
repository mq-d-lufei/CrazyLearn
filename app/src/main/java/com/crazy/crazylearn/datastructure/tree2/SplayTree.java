package com.crazy.crazylearn.datastructure.tree2;

public class SplayTree<AnyType extends Comparable<? super AnyType>> {

    public BinaryNode<AnyType> root;

    public BinaryNode<AnyType> header;
    public BinaryNode<AnyType> nullNode;

    public SplayTree() {
        nullNode = new BinaryNode<AnyType>(null);
        nullNode.left = nullNode.right = nullNode;
        root = nullNode;
    }

    public static class BinaryNode<AnyType extends Comparable<? super AnyType>> {
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;

        public BinaryNode(AnyType element) {
            this(element, null, null);
        }

        public BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }
}
