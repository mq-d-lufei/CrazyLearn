package com.crazy.crazylearn.datastructure.tree2;

public class AVlTree1<AnyType extends Comparable<? super AnyType>> {

    protected AvlNode root;

    private static class AvlNode<AnyType> {

        public AvlNode(AnyType element, AvlNode<AnyType> left, AvlNode<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

        AnyType element;
        AvlNode<AnyType> left, right;
        int height;

    }

    private static final int ALLOWED_IMBALANCE = 1;

    public boolean isBalance(AvlNode<AnyType> node) {
        return Math.abs(height(node.left) - height(node.right)) <= ALLOWED_IMBALANCE;
    }

    /**
     * 平衡
     */
    private AvlNode<AnyType> balance(AvlNode<AnyType> node) {
        if (null == node)
            return null;

        AvlNode<AnyType> balanceNode = node;

        if (height(node.left) - height(node.right) > ALLOWED_IMBALANCE) {

            //左撇型
            if (height(node.left.left) >= height(node.left.right)) {
                //以node.left为轴右旋node,达到平衡
                balanceNode = rotateWithLeftChild(node);
            } else {//小于型，以node.left为轴左旋node.left.right,之后变为左撇型，再以node.left为轴右旋node,达到平衡
                balanceNode = doubleRotateWithLeftChild(node);
            }

        } else if (height(node.right) - height(node.left) > ALLOWED_IMBALANCE) {

            //右撇型
            if (height(node.right.right) >= height(node.right.left)) {
                //以node.right为轴左旋node,达到平衡
                balanceNode = rotateWithRightChild(node);
            } else {//大于型,以node.right为轴右旋node.right.left,之后变为右撇型，再以node.right为轴左旋node,达到平衡
                balanceNode = doubleRotateWithRightChild(node);
            }
        }

        balanceNode.height = Math.max(height(balanceNode.left), height(balanceNode.right)) + 1;

        return balanceNode;
    }

    /***
     * 以节点node的左孩子为轴右旋（顺时针）转90度
     *
     * 上升node.left节点，node.left结点的右孩子给node节点，下降node结点，
     *
     * 结构如下：
     *
     *      *      *       *
     *     *      *       *
     *    *        *     * *
     *
     */
    private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> node) {

        AvlNode<AnyType> finalRoot = node.left;
        node.left = finalRoot.right;
        finalRoot.right = node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        finalRoot.height = Math.max(height(finalRoot.left), height(finalRoot.right)) + 1;

        return finalRoot;
    }

    /***
     * 以节点node的有孩子为轴左旋（逆时针）
     *
     * 上升node.right节点，node.right结点的左孩子给node节点，下降node结点，
     *
     * 结构如下：
     *
     *        *      *      *
     *         *      *      *
     *          *    *      * *
     */
    private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> node) {

        AvlNode<AnyType> finalRoot = node.right;
        node.right = finalRoot.left;
        finalRoot.left = node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        finalRoot.height = Math.max(height(finalRoot.left), height(finalRoot.right)) + 1;

        return finalRoot;
    }


    private AvlNode<AnyType> doubleRotateWithLeftChild(AvlNode<AnyType> node) {

        node.left = rotateWithRightChild(node.left);

        return rotateWithLeftChild(node);
    }

    private AvlNode<AnyType> doubleRotateWithRightChild(AvlNode<AnyType> node) {

        node.right = rotateWithLeftChild(node.right);

        return rotateWithRightChild(node);
    }


    private int height(AvlNode<AnyType> node) {
        return node == null ? -1 : node.height;
    }
}
