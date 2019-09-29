package com.crazy.crazylearn.datastructure.tree;

public interface IBinaryTree<Node, Data> {

    void init(Node node);

    //增加左结点时，若不存在，则加入，若存在，则加入新结点，并且老节点作为新结点的左孩子，
    void addLeft(Node oldNode, Node newNode);

    //增加右结点时，若不存在，则加入，若存在，则加入新结点，并且老节点作为新结点的右孩子，
    void addRight(Node oldNode, Node newNode);

    //删除该节点及其孩子
    int remove(Node node);

    int searchPos(Node node);

    Node searchNode(int pos);

    void printTree();
}
