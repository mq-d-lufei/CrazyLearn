package com.crazy.crazylearn.datastructure.tree;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayBinaryTree implements IBinaryTree<String, String> {

    public ArrayList<String> nodes = new ArrayList<>();

    @Override
    public void init(String s) {

    }

    @Override
    public void addLeft(String oldNode, String newNode) {

    }

    @Override
    public void addRight(String oldNode, String newNode) {

    }

    @Override
    public int remove(String s) {

        int pos = nodes.indexOf(s);

        nodes.set(pos, null);

        return pos;
    }

    @Override
    public int searchPos(String s) {
        return nodes.indexOf(s);
    }

    @Override
    public String searchNode(int pos) {
        return nodes.get(pos);
    }

    @Override
    public void printTree() {

        int currentRow = 1;

        for (int i = 0; i < nodes.size(); i++) {
            if (!changeRow(currentRow, i)) {

                System.out.print("\n");
                currentRow++;

                String result = (null == nodes.get(i) || nodes.get(i).equals("")) ? "_" : nodes.get(i);
                System.out.print(result + " ");

            } else {
                String result = (null == nodes.get(i) || nodes.get(i).equals("")) ? "_" : nodes.get(i);
                System.out.print(result + " ");
            }
        }
    }

    //pos从1開始
    public boolean changeRow(int currentRow, int pos) {
        //1 3，7，15
        int x = (int) (Math.pow(2, currentRow) - 1);
        //  System.out.println("x = " + x);
        return pos < x;
    }

    public void init() {

        nodes.addAll(Arrays.asList("1", "", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
    }

    public static void main(String[] args) {

        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree();
        arrayBinaryTree.init();
        arrayBinaryTree.printTree();
    }
}
