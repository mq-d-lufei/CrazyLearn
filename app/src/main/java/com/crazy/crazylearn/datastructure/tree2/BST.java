package com.crazy.crazylearn.datastructure.tree2;

import android.support.annotation.NonNull;

public abstract class BST<AnyType extends Comparable<? super AnyType>> extends BinaryTree<AnyType> {


    protected static class BinarySearchResult<AnyType extends Comparable<? super AnyType>> {
        //命中结点或搜索失敗的父亲(搜索结点时，搜索之前置为空)
        public BinaryNode<AnyType> _hot;
        public BinaryNode<AnyType> searchResult;
    }

    //按照3+4结构，连接三个结点以及四棵子树
    protected BinaryNode<AnyType> connect34(
            BinaryNode<AnyType> a, BinaryNode<AnyType> b, BinaryNode<AnyType> c,
            BinaryNode<AnyType> d, BinaryNode<AnyType> e, BinaryNode<AnyType> f, BinaryNode<AnyType> g) {

        return null;
    }

    //对node一起父亲、祖父左统一旋转调整
    protected BinaryNode<AnyType> rotateAt(BinaryNode<AnyType> node) {
        return null;
    }

    /**
     * 所有派生类（BST变种）更具各自规则重写
     */

    //搜索
    public abstract BinaryNode<AnyType> search(AnyType e);

    //插入
    public abstract BinaryNode<AnyType> insert(AnyType e);

    //删除
    public abstract boolean remove(AnyType e);


    /**
     * 在node结点中搜索结点e
     * 返回搜索结果：目标结点或空
     * _hot:命中结点或搜索节点的父节点
     */
    public BinarySearchResult<AnyType> searchIn(AnyType e, BinaryNode<AnyType> node, @NonNull BinarySearchResult<AnyType> bsr) {

        if (null == node)
            return null;

        //命中节点
        bsr._hot = node;

        int compareResult = e.compareTo(node.element);

        if (compareResult < 0)
            bsr = searchIn(e, node.left, bsr);
        else if (compareResult > 0) {
            bsr = searchIn(e, node.right, bsr);
        } else {
            bsr.searchResult = node;
        }

        return bsr;
    }

    public BinaryNode<AnyType> insertIn(AnyType e, BinaryNode<AnyType> node) {

        BinarySearchResult<AnyType> bsr = new BinarySearchResult<>();

        bsr = searchIn(e, node, bsr);

        BinaryNode<AnyType> insertNode = null;

        if (null == bsr.searchResult) {

            insertNode = new BinaryNode<>(e, bsr._hot);

            if ((e.compareTo(bsr._hot.element) < 0)) {
                bsr._hot.left = insertNode;
            } else {
                bsr._hot.right = insertNode;
            }

        } else {
            insertNode = bsr.searchResult;
        }
        return insertNode;
    }

   /* public boolean removeIn(AnyType e, BinaryNode<AnyType> node) {

        BinarySearchResult<AnyType> bsr = new BinarySearchResult<>();

        bsr = searchIn(e, node, bsr);

        if (null == bsr.searchResult) {
            return false;
        }

        BinaryNode<AnyType> searchNode = bsr.searchResult;

        if (null != searchNode.left && null != searchNode.right) {

        } else {
            searchNode = null != searchNode.left ? searchNode.left : searchNode.right;
        }


    }*/

    public boolean removeIn(AnyType e, BinaryNode<AnyType> node) {

        if (null == node) return false;

        boolean isRemove;

        int compareresult = e.compareTo(node.element);

        if (compareresult < 0) {
            isRemove = removeIn(e, node.left);
        } else if (compareresult > 0) {
            isRemove = removeIn(e, node.right);
        } else {

            if (null != node.left && null != node.right) {

                BinaryNode<AnyType> minNode = findMin(node.right);

                swap(node.element, minNode.element);

                removeIn(e, node.right);

            } else {
                node = null != node.left ? node.left : node.right;
            }

            isRemove = true;
        }

        return isRemove;
    }

    private void swap(AnyType data, AnyType data1) {
        AnyType temp = data;
        data = data1;
        data1 = temp;
    }

    protected abstract BinaryNode<AnyType> findMin(BinaryNode<AnyType> right);


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
    protected BinaryNode<AnyType> rotateWithLeftChild(BinaryNode<AnyType> node) {

        BinaryNode<AnyType> finalRoot = node.left;
        node.left = finalRoot.right;
        finalRoot.right = node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        finalRoot.height = Math.max(height(finalRoot.left), height(finalRoot.right)) + 1;

        return finalRoot;
    }

    /***
     * 以节点node的右孩子为轴左旋（逆时针）
     *
     * 上升node.right节点，node.right结点的左孩子给node节点，下降node结点，
     *
     * 结构如下：
     *
     *        *      *      *
     *         *      *      *
     *          *    *      * *
     */
    protected BinaryNode<AnyType> rotateWithRightChild(BinaryNode<AnyType> node) {

        BinaryNode<AnyType> finalRoot = node.right;
        node.right = finalRoot.left;
        finalRoot.left = node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        finalRoot.height = Math.max(height(finalRoot.left), height(finalRoot.right)) + 1;

        return finalRoot;
    }

    /**
     * 双旋转
     */
    protected BinaryNode<AnyType> doubleRotateWithLeftChild(BinaryNode<AnyType> node) {

        node.left = rotateWithRightChild(node.left);

        return rotateWithLeftChild(node);
    }

    protected BinaryNode<AnyType> doubleRotateWithRightChild(BinaryNode<AnyType> node) {

        node.right = rotateWithLeftChild(node.right);

        return rotateWithRightChild(node);
    }


    protected int height(BinaryNode<AnyType> node) {
        return node == null ? -1 : node.height;
    }

}
