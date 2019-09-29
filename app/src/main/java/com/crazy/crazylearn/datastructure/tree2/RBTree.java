package com.crazy.crazylearn.datastructure.tree2;

public class RBTree<AnyType extends Comparable<? super AnyType>> extends BST<AnyType> {


    @Override
    public BinaryNode<AnyType> search(AnyType e) {
        return null;
    }


    /***
     *
     * 接下来会发生什么取决于附近其他节点的颜色。有几种红黑树插入的情况：
     *
     * 1、N 是根节点，即红黑树的第一个节点
     * 2、N 的父母（P）是黑人
     * 3、P 是红色的（所以它不能是树的根）而N的叔叔（U）是红色的
     * 4、P 为红色，U为黑色
     *
     */

    @Override
    public BinaryNode<AnyType> insert(AnyType e) {
        return null;
    }

    @Override
    protected BinaryNode<AnyType> findMin(BinaryNode<AnyType> right) {
        return null;
    }

    @Override
    public boolean remove(AnyType e) {
        return false;
    }

    /***
     * Returns the successor of the specified Entry, or null if no such.
     * 1、如果有右孩子，则后继为右孩子中最小(最左)的值
     * 2、如果没有右孩子，在t的父节点中查找
     *    a、t为左孩子，则后继为父节点
     *    b、t为右节点，则遍历t的祖先，直到祖先为第一个左子树的根结点或为空时
     */
    public BinaryNode<AnyType> successor(BinaryNode<AnyType> t) {
        if (t == null)
            return null;
        if (t.right != null) {
            BinaryNode<AnyType> succ = t.right;
            while (succ.left != null) {
                succ = succ.left;
            }
            return succ;
        } else {
            BinaryNode<AnyType> p = t.parent;
            BinaryNode<AnyType> ch = t;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    /**
     * 删除
     *
     * 情况1：N is the new root. In this case, we are done.
     *          We removed one black node from every path, and the new root is black,
     *          so the properties are preserved.
     *
     * 情况1：
     *
     */

    /**
     * Delete node p, and then rebalance the tree.
     */
    private void deleteNode(BinaryNode<AnyType> p) {

        // If strictly internal, copy successor's element to p and then make p
        // point to successor.
        if (p.left != null && p.right != null) {
            BinaryNode<AnyType> succ = successor(p);
            p.element = succ.element;
            p = succ;
        } // p has 2 children,succ not null

        // Start fixup at replacement node, if it exists.
        BinaryNode<AnyType> replacement = p.left != null ? p.left : p.right;

        if (null == replacement && null == p.parent) {// no children and is root
            root = null;
        } else if (null == replacement) {//  No children. Use self as phantom replacement and unlink.

            //该删除节点为叶子结点，调整结构后删除
            if (p.rbColor == BinaryNode.RBColor.RB_BLACK) {
                fixAfterDeletion(p);
            }

            //情况1：刪除结点X为紅色结点
            if (null != p.parent) {
                if (p == p.parent.left)
                    p.parent.left = null;
                else
                    p.parent.right = null;
                p.parent = null;
            }

        } else { // only one child (p.left | p.right | replacement.right)

            replacement.parent = p.parent;

            if (null == p.parent) {
                root = replacement;
            } else if (p == p.parent.left) {
                p.parent.left = replacement;
            } else if (p == p.parent.right) {
                p.parent.right = replacement;
            }

            p.left = p.right = p.parent = null;

            //如果刪除结点为红色，不会影响黑高度（此时删除结点P有左子树或右子树、或在替换节点replacement处，可能有右子树），
            //所以，删除结点为红色，且仅有一个孩子时，可直接删除，
            //删除结点为黑色，且仅有一个孩子时，需调整树结构
            if (p.rbColor == BinaryNode.RBColor.RB_BLACK)
                fixAfterDeletion(replacement);
        }

    }

    private BinaryNode.RBColor colorOf(BinaryNode<AnyType> p) {
        return (p == null ? BinaryNode.RBColor.RB_BLACK : p.rbColor);
    }

    protected BinaryNode<AnyType> rotateWithRightChild(BinaryNode<AnyType> node) {

        BinaryNode<AnyType> r = null;

        if (null != node) {
            r = node.right;

            //1、先处理右孩子（转轴）的左孩子
            node.right = r.left;
            if (r.left != null) {
                r.left.parent = node;
            }

            //2、处理有孩子（转轴）
            r.parent = node.parent;
            if (node.parent == null) {
                root = r;
            } else if (node == node.parent.left) {
                node.parent.left = r;
            } else {
                node.parent.right = r;
            }

            //3、
            r.left = node;
            node.parent = r;
        }

        return r;
    }

    protected BinaryNode<AnyType> rotateWithLeftChild(BinaryNode<AnyType> node) {

        BinaryNode<AnyType> l = null;

        if (null != node) {
            l = node.left;

            //1、先处理右孩子（转轴）的左孩子
            node.left = l.right;
            if (l.right != null) {
                l.right.parent = node;
            }

            //2、处理有孩子（转轴）
            l.parent = node.parent;
            if (node.parent == null) {
                root = l;
            } else if (node == node.parent.right) {
                node.parent.right = l;
            } else {
                node.parent.left = l;
            }

            //3、
            l.right = node;
            node.parent = l;
        }

        return l;
    }

    private void fixAfterDeletion(BinaryNode<AnyType> x) {

        while (x != root && x.rbColor == BinaryNode.RBColor.RB_BLACK) {

            if (x == x.parent.left) {

                BinaryNode<AnyType> sib = x.parent.right;

                //情況c:黑色X的兄弟节点S为红色，S的父节点必然为黑色，S的孩子必为黑色（且至少有一个），因为保证删除前的黑平衡
                if (sib.rbColor == BinaryNode.RBColor.RB_RED) {

                    sib.rbColor = BinaryNode.RBColor.RB_BLACK;
                    sib.parent.rbColor = BinaryNode.RBColor.RB_RED;

                    rotateWithRightChild(x.parent);

                    //TODO: 转化为情况b 或 情况a
                    sib = x.parent.right;
                }

                //情况b：X的兄弟节点S为黑色，S的两个孩子都为黑色，
                if (colorOf(sib.left) == BinaryNode.RBColor.RB_BLACK &&
                        colorOf(sib.right) == BinaryNode.RBColor.RB_BLACK) {//黑黑黑

                    //TODO: 转化为情况c
                    sib.rbColor = BinaryNode.RBColor.RB_RED;
                    x = x.parent;
                }
                //情況a:X的兄弟节点S为黑色，S的孩子中至少有一个是红色（包括两红）
                else {//黑紅、紅黑、紅紅

                    if (sib.right.rbColor == BinaryNode.RBColor.RB_BLACK) {

                        sib.left.rbColor = BinaryNode.RBColor.RB_BLACK;
                        sib.rbColor = BinaryNode.RBColor.RB_RED;

                        rotateWithLeftChild(sib);

                        //TODO: 转化为下面的情況
                        sib = x.parent.right;
                    }

                    //
                    sib.rbColor = x.parent.rbColor;
                    x.parent.rbColor = BinaryNode.RBColor.RB_BLACK;
                    sib.right.rbColor = BinaryNode.RBColor.RB_BLACK;

                    rotateWithRightChild(x.parent);

                    x = root;
                }

            } else {

                BinaryNode<AnyType> sib = x.parent.left;

                //情況c:黑色X的兄弟节点S为红色，S的父节点必然为黑色，S的孩子必为黑色（且至少有一个），因为保证删除前的黑平衡
                if (sib.rbColor == BinaryNode.RBColor.RB_RED) {

                    sib.rbColor = BinaryNode.RBColor.RB_BLACK;
                    sib.parent.rbColor = BinaryNode.RBColor.RB_RED;

                    rotateWithLeftChild(x.parent);

                    //TODO: 转化为情况b 或 情况a
                    sib = x.parent.left;
                }

                //情况b：X的兄弟节点S为黑色，S的两个孩子都为黑色，
                if (colorOf(sib.right) == BinaryNode.RBColor.RB_BLACK &&
                        colorOf(sib.left) == BinaryNode.RBColor.RB_BLACK) {//黑黑黑

                    //TODO: 转化为情况c
                    sib.rbColor = BinaryNode.RBColor.RB_RED;
                    x = x.parent;
                }
                //情況a:X的兄弟节点S为黑色，S的孩子中至少有一个是红色（包括两红）
                else {//黑紅、紅黑、紅紅

                    if (sib.left.rbColor == BinaryNode.RBColor.RB_BLACK) {

                        sib.right.rbColor = BinaryNode.RBColor.RB_BLACK;
                        sib.rbColor = BinaryNode.RBColor.RB_RED;

                        rotateWithRightChild(sib);

                        //TODO: 转化为下面的情況
                        sib = x.parent.left;
                    }

                    //
                    sib.rbColor = x.parent.rbColor;
                    x.parent.rbColor = BinaryNode.RBColor.RB_BLACK;
                    sib.left.rbColor = BinaryNode.RBColor.RB_BLACK;

                    rotateWithLeftChild(x.parent);

                    x = root;
                }

            }

        }

        x.rbColor = BinaryNode.RBColor.RB_BLACK;

    }


}
