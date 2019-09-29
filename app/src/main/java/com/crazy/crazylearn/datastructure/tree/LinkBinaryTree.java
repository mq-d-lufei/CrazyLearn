package com.crazy.crazylearn.datastructure.tree;

/**
 * 二叉樹链表结构表示法
 */
public class LinkBinaryTree<Data> {

    //数据
    private Data data;

    //左右结点
    private LinkBinaryTree left;
    private LinkBinaryTree right;

    public LinkBinaryTree(Data data) {
        this.data = data;
    }

    public LinkBinaryTree(Data data, LinkBinaryTree left, LinkBinaryTree right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public Data getData() {
        return data;
    }

    public LinkBinaryTree getLeft() {
        return left;
    }

    public LinkBinaryTree getRight() {
        return right;
    }
}
