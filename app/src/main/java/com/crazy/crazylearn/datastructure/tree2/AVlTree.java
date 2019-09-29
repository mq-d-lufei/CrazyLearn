package com.crazy.crazylearn.datastructure.tree2;

public class AVlTree<AnyType extends Comparable<? super AnyType>> extends BST<AnyType> {

    protected BinaryNode root;

    @Override
    public BinaryNode<AnyType> search(Comparable e) {
        return null;
    }

    @Override
    public BinaryNode<AnyType> insert(Comparable e) {
        return null;
    }

    @Override
    public boolean remove(Comparable e) {
        return false;
    }

    @Override
    protected BinaryNode<AnyType> findMin(BinaryNode right) {
        return null;
    }


    private static final int ALLOWED_IMBALANCE = 1;

    public boolean isBalance(BinaryNode<AnyType> node) {
        return Math.abs(height(node.left) - height(node.right)) <= ALLOWED_IMBALANCE;
    }

    /**
     * 平衡
     */
    private BinaryNode<AnyType> balance(BinaryNode<AnyType> node) {
        if (null == node)
            return null;

        BinaryNode<AnyType> balanceNode = node;

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

}
