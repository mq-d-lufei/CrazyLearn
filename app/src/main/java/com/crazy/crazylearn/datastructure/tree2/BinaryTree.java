package com.crazy.crazylearn.datastructure.tree2;

import java.util.Stack;

public class BinaryTree<AnyType extends Comparable<? super AnyType>> {

    //规模
    protected int size;
    //根结点
    protected BinaryNode<AnyType> root;

    //更新结点node的高度
    //算法总体运行时间：O(depth(node) + 1),depth(node)为node结点深度
    protected int updateHeight(BinaryNode<AnyType> node) {
        return node.height = 1 + Math.max(BinaryUtils.stature(node.left), BinaryUtils.stature(node.right));
    }

    //更新结点node及其祖先的高度
    protected void updateHeightAbove(BinaryNode<AnyType> node) {
        while (node != null) {  //从node出发，覆盖历代祖先可优化
            updateHeight(node);
            node = node.parent;
        }
    }

    public BinaryTree() {
        size = 0;
        root = null;
    }

    //规模
    public int size() {
        return size;
    }

    public boolean empty() {
        return root == null;
    }

    public BinaryNode<AnyType> root() {
        return root;
    }

    //插入
    public BinaryNode<AnyType> insertAsRoot(AnyType e) {
        size = 1;
        return root = new BinaryNode<>(e);
    }

    /**
     * 插入 、 删除 、 遍历
     */

    public BinaryNode<AnyType> insertAsLeftChile(BinaryNode<AnyType> node, AnyType e) {
        size++;
        node.insertAsLeftChile(e);
        updateHeightAbove(node);
        return node.left;
    }

    public BinaryNode<AnyType> insertAsRightChile(BinaryNode<AnyType> node, AnyType e) {
        size++;
        node.insertAsRightChile(e);
        updateHeightAbove(node);
        return node.right;
    }

    //接入左子树
    public BinaryNode<AnyType> attachAsLeftChile(BinaryNode<AnyType> node, BinaryTree<AnyType> tree) {
        if (null != (node.left = tree.root)) {
            node.left.parent = node;
            size += tree.size;
            ///更新全树觃模不x所有祖先癿高度
            updateHeightAbove(node);
            //tree本身置空
            tree.root = null;
            tree.size = 0;
            tree = null;
        }
        return node;
    }

    //接入右子树
    public BinaryNode<AnyType> attachAsRightChile(BinaryNode<AnyType> node, BinaryTree<AnyType> tree) {
        if (null != (node.right = tree.root)) {
            node.right.parent = node;
            size += tree.size;
            ///更新全树觃模不x所有祖先癿高度
            updateHeightAbove(node);
            //tree本身置空
            tree.root = null;
            tree.size = 0;
            tree = null;
        }
        return node;
    }

    public void perOrder(BinaryNode<AnyType> node) {

        Stack<BinaryNode<AnyType>> stack = new Stack<>();

        BinaryNode<AnyType> visitNode = node;

        boolean isOver = false;

        while (!isOver) {

            visitAlongLeftBranch(visitNode, stack);

            if (stack.empty()) isOver = true;

            visitNode = stack.pop();

        }

    }

    private void visitAlongLeftBranch(BinaryNode<AnyType> node, Stack<BinaryNode<AnyType>> stack) {

        BinaryNode<AnyType> nodeTemp = node;

        while (null != nodeTemp) {
            visit(nodeTemp);
            stack.push(nodeTemp.right);
            nodeTemp = nodeTemp.left;
        }
    }

    private void visit(BinaryNode<AnyType> node) {
        System.out.print(node.element + " ");
    }

    public void middleOrder(BinaryNode<AnyType> node) {

        BinaryNode<AnyType> visitNode = node;

        Stack<BinaryNode<AnyType>> stack = new Stack<>();

        while (null != visitNode || !stack.empty()) {

            while (null != visitNode) {
                stack.push(visitNode);
                visitNode = visitNode.left;
            }

            visitNode = stack.pop();

            visit(visitNode);

            visitNode = visitNode.right;
        }
    }

    //中序后继遍历法
    public void middleOrder2(BinaryNode<AnyType> node) {

        BinaryNode<AnyType> visitNode = node.findLeftMost();

        while (null != visitNode) {
            visit(visitNode);
            //得到后继
            visitNode = visitNode.succ();
        }
    }

}
