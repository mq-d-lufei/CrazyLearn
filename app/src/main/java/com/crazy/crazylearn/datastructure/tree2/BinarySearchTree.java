package com.crazy.crazylearn.datastructure.tree2;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 二叉搜索树
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {

    //根结点
    private BinaryNode<AnyType> root;

    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * 树结点
     */
    private static class BinaryNode<AnyType> {

        //结点数据
        AnyType element;
        //左右孩子
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

    /**
     * 插入
     */
    public void insert(AnyType x) {
        root = insert(x, root);
    }

    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> root) {

        if (null == root) {
            return new BinaryNode<>(x);
        }

        int compareResult = x.compareTo(root.element);

        if (compareResult < 0) {
            root.left = insert(x, root.left);
        } else if (compareResult > 0) {
            root.right = insert(x, root.right);
        } else {
            //ignore
        }

        return root;
    }


    /**
     * 刪除
     */
    public void remove(AnyType x) {
        root = remove(x, root);
    }

    //刪除後返回新树
    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> root) {
        if (null == root) {
            return null;
        }

        int compareResult = x.compareTo(root.element);

        if (compareResult < 0) {
            root.left = remove(x, root.left);
        } else if (compareResult > 0) {
            root.right = remove(x, root.right);
        } else {
            if (null != root.left && null != root.right) {

                root.element = findMin(root.right).element;
                root.right = remove(root.element, root.right);

            } else {
                root = null != root.left ? root.left : root.right;
            }
        }

        return root;
    }

    public boolean isEmpty() {
        return null == root;
    }

    public void makeEmpty() {
        root = null;
    }

    public AnyType findMin() {
        if (isEmpty()) return null;
        return findMin(root).element;
    }

    public AnyType findMax() {
        if (isEmpty()) return null;
        return findMax(root).element;
    }

    /**
     * 查找最小值
     */
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> root) {
        if (null == root)
            return null;
        if (null == root.left)
            return root;
        return findMin(root.left);
    }

    //查找最大值
    public BinaryNode<AnyType> findMax(BinaryNode<AnyType> root) {
        if (null == root)
            return null;

        BinaryNode<AnyType> rootTemp = root;

        while (null != rootTemp.right) {

            rootTemp = rootTemp.right;
        }

        return rootTemp;
    }

    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    private boolean contains(AnyType x, BinaryNode<AnyType> root) {
        if (null == root) return false;

        int compareResult = x.compareTo(root.element);

        if (compareResult < 0) {
            return contains(x, root.left);
        } else if (compareResult > 0) {
            return contains(x, root.right);
        } else {
            return true;
        }
    }

    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }


    /**
     * 终须递归输出
     */
    public void printTree(BinaryNode<AnyType> root) {

        if (null != root) {
            printTree(root.left);
            System.out.print(root.element + " - ");
            printTree(root.right);
        }
    }

    public void preOrder(BinaryNode<AnyType> root) {

        if (null == root) {
            //System.out.println("Empty Tree");
            return;
        }

        System.out.print(root.element + " - ");
        preOrder(root.left);
        preOrder(root.right);
    }

    public void afterOrder(BinaryNode<AnyType> root) {

        if (null == root) {
            // System.out.println("Empty Tree");
            return;
        }

        afterOrder(root.left);
        afterOrder(root.right);
        System.out.print(root.element + " - ");
    }

    /**
     * 先访问结点数据,然后将各个左子树结点（根节点）入栈，之后出栈，出栈时，再访问右子树
     */
    public void preOrderTree(BinaryNode<AnyType> root) {

        if (null == root)
            return;

        LinkedList<BinaryNode<AnyType>> rootTempList = new LinkedList<>();

        BinaryNode<AnyType> tempRoot = root;

        Stack<BinaryNode<AnyType>> stack = new Stack<>();

        while (null != tempRoot || !stack.empty()) {

            while (null != tempRoot) {
                System.out.print(tempRoot.element + " - ");
                stack.push(tempRoot);
                // rootTempList.push(tempRoot);
                tempRoot = tempRoot.left;
            }

            if (!stack.empty()) {
                tempRoot = stack.pop();
                // tempRoot =  rootTempList.pop();
                tempRoot = tempRoot.right;
            }
        }
    }

    /**
     * * 将各个结点（根节点）入栈，之后出栈，出栈时，先访问左子树，再访问结点数据，然后访问右子树
     */
    public void middleOrderTree(BinaryNode<AnyType> root) {

        if (null == root)
            return;

        LinkedList<BinaryNode<AnyType>> stack = new LinkedList<>();

        BinaryNode<AnyType> tempRoot = root;

        while (null != tempRoot || !stack.isEmpty()) {

            while (null != tempRoot) {
                stack.push(tempRoot);
                tempRoot = tempRoot.left;
            }

            if (!stack.isEmpty()) {
                tempRoot = stack.pop();
                System.out.print(tempRoot.element + " - ");

                tempRoot = tempRoot.right;
            }
        }
    }

    private class AfterOrderNode {
        BinaryNode<AnyType> root;
        boolean isFirstTop = true;//第一次到栈顶

        public AfterOrderNode(BinaryNode<AnyType> root) {
            this.root = root;
        }
    }

    public void afterOrderTree(BinaryNode<AnyType> root) {

        if (null == root)
            return;

        LinkedList<AfterOrderNode> stack = new LinkedList<>();

        BinaryNode<AnyType> tempRoot = root;

        AfterOrderNode peekNode;

        while (null != tempRoot || !stack.isEmpty()) {

            while (null != tempRoot) {
                stack.push(new AfterOrderNode(tempRoot));
                tempRoot = tempRoot.left;
            }

            if (!stack.isEmpty()) {

                peekNode = stack.pop();

                tempRoot = peekNode.root;

                //第一次出栈后再入栈，访问右子树，最终会第二次出栈，此时，左右子树都访问完毕，方可打印该结点

                if (peekNode.isFirstTop) {
                    peekNode.isFirstTop = false;
                    stack.push(peekNode);
                    tempRoot = peekNode.root.right;
                } else {
                    System.out.print(tempRoot.element + " - ");
                    tempRoot = null;
                    peekNode = null;
                }

                /* if (null != tempRoot.right && peekNode.popCount < 1*//*并且不是第二次出栈*//*) {
                    tempRoot = tempRoot.right;

                } else {
                    peekNode = stack.pop();
                    tempRoot = peekNode.root;

                    if (peekNode.popCount < 1) {
                        *//*并且不是第二次出栈*//*
                        tempRoot = tempRoot.right;
                    } else {
                        System.out.print(tempRoot.element + " - ");
                        tempRoot = null;
                    }
                }

                peekNode.popCount++;*/
            }

        }
    }

    public static void main(String[] args) {

        //Create
        BinarySearchTree<Integer> searchTree = new BinarySearchTree<Integer>();

        int deValue = 0;

        //insert
        for (int i = 0; i < 20; i++) {

            int random = ((int) (Math.random() * 20) + 1);
            System.out.print(random + " - ");

            searchTree.insert(random);

            if (i == 10) {
                deValue = random;
            }
        }

        System.out.println("\n");
        //print
        searchTree.printTree();

        boolean contains = searchTree.contains(deValue);
        System.out.println("\n" + "contains " + contains);

        System.out.println("\n");
        System.out.println("will delete " + deValue);
        System.out.println("\n");

        //delete
        searchTree.remove(deValue);

        System.out.println("\n中序1：\n");
        searchTree.printTree();
        System.out.println("\n中序2：\n");
        searchTree.middleOrderTree(searchTree.root);

        boolean contains1 = searchTree.contains(deValue);
        System.out.println("\n" + "contains " + contains1);

        System.out.println("前序1：\n");
        searchTree.preOrder(searchTree.root);

        System.out.println("\n前序2：\n");
        searchTree.preOrderTree(searchTree.root);

        System.out.println("\n后序1:  ");
        searchTree.afterOrder(searchTree.root);

        System.out.println("\n后序2:  ");
        searchTree.afterOrderTree(searchTree.root);
    }
}
