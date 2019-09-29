package com.crazy.crazylearn.datastructure.tree2;

public class BinaryNode<AnyType extends Comparable<? super AnyType>> {

    //数据
    public AnyType element;

    //父节点、左右孩子
    public BinaryNode<AnyType> parent;
    public BinaryNode<AnyType> left;
    public BinaryNode<AnyType> right;

    //高度
    public int height = 0;
    //Null Path Length (左式堆)
    public int npl = 1;
    //颜色（红黑树）
    public RBColor rbColor = RBColor.RB_BLACK;

    //红黑树结点颜色
    public enum RBColor {
        RB_RED, RB_BLACK
    }

    /**
     * 构造函数
     */

    public BinaryNode() {
        npl = 1;
        height = 0;
        rbColor = RBColor.RB_RED;

    }

    public BinaryNode(AnyType element) {
        this();
        this.element = element;
    }

    public BinaryNode(AnyType element, BinaryNode<AnyType> parent) {
        this();
        this.element = element;
        this.parent = parent;
    }

    public BinaryNode(AnyType element, BinaryNode<AnyType> parent, BinaryNode<AnyType> left, BinaryNode<AnyType> right, int height, int npl, RBColor rbColor) {
        this.element = element;
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.height = height;
        this.npl = npl;
        this.rbColor = rbColor;
    }

    /**
     * 结点操作接口
     */

    /**
     * 插入孩子结点
     */
    public BinaryNode<AnyType> insertAsLeftChile(AnyType e) {
        return left = new BinaryNode<>(e);
    }

    public BinaryNode<AnyType> insertAsRightChile(AnyType e) {
        return right = new BinaryNode<>(e);
    }

    /**
     * 定位当前结点中序遍历的直接后继
     */
    public BinaryNode<AnyType> succ() {

        BinaryNode<AnyType> succ = this;

        //当前节点有右子树，后继为该子树最左（小）结点
        if (null != right) {
            succ = right;
            while (BinaryUtils.hasLeftChile(succ)) {
                succ = succ.left;
            }
        } else {
            while (BinaryUtils.isRightChile(succ))
                succ = succ.parent;
            succ = succ.parent;
        }

        return succ;
    }

    public BinaryNode<AnyType> findLeftMost() {

        BinaryNode<AnyType> leftMost = this;

        while (BinaryUtils.hasLeftChile(leftMost)) {
            leftMost = leftMost.left;
        }
        return leftMost;
    }


}


