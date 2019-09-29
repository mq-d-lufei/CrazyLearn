package com.crazy.crazylearn.datastructure.tree2;

public class BinaryUtils {


    //结点高度(空树高度为-1)
    public static int stature(BinaryNode node) {
        return node == null ? -1 : node.height;
    }

    //根结点的父节点为空
    public static boolean isRoot(BinaryNode node) {
        return node.parent == null;
    }

    //node不为根结点，且为父节点的左孩子
    public static boolean isLeftChile(BinaryNode node) {
        return !isRoot(node) && node == node.parent.left;
    }

    //node不为根结点，且为父节点的右孩子
    public static boolean isRightChile(BinaryNode node) {
        return !isRoot(node) && node == node.parent.right;
    }

    //有无父节点
    public static boolean hasParent(BinaryNode node) {
        return !isRoot(node);
    }

    //有无左孩子
    public static boolean hasLeftChile(BinaryNode node) {
        return node.left != null;
    }

    //有无右孩子
    public static boolean hasRightChile(BinaryNode node) {
        return node.right != null;
    }

    //至少拥有一个孩子
    public static boolean hasChile(BinaryNode node) {
        return hasLeftChile(node) || hasRightChile(node);
    }

    //同时拥有两个孩子
    public static boolean hasBothChile(BinaryNode node) {
        return hasLeftChile(node) && hasRightChile(node);
    }

    //是否是叶子
    public static boolean isLeaf(BinaryNode node) {
        return !hasChile(node);
    }

    //兄弟
    public static BinaryNode sibling(BinaryNode node) {
        if (isRoot(node))
            return null;
        return isLeftChile(node) ? node.parent.right : node.parent.left;
    }

    //叔叔
    public static BinaryNode uncle(BinaryNode node) {
        if (isRoot(node) || isRoot(node.parent))
            return null;
        return isLeftChile(node) ? node.parent.parent.right : node.parent.parent.left;
    }

}
