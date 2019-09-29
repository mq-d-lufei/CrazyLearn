package com.crazy.crazylearn.datastructure;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Stack;

public class MyLinkedList<E> {

    private Node<E> first;

    private Node<E> last;

    public MyLinkedList() {

    }

    public MyLinkedList(Node<E> first, Node<E> last) {
        this.first = first;
        this.last = last;
    }

    public void printList() {
        Node<E> temp = first;
        while (null != temp) {
            System.out.print("-" + temp.item);
            temp = temp.next;
        }
    }

    public void addNode(E item) {
        if (null == first) {
            first = new Node<>(item, null);
            last = first;
        } else {
            last.next = new Node<>(item, null);
            last = last.next;
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    private static class Node2<E> {
        E item;
        Node2<E> next;
        Node2<E> prev;

        public Node2(E item, Node2<E> next, Node2<E> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    /***
     *
     * https://www.cnblogs.com/tojian/p/10055036.html
     *
     * 1.链表的倒数第K个结点
     *
     * 双指针法，指针间距K
     *
     * 倒数： 思路：1  2  3  4  5  6
     *   p1： 0  1  2  3  4  5   快指针循环次数
     *
     */
    //倒数第一从1开始
    Node findNodeReverse(MyLinkedList myLinkedList, int reverseIndexK) {

        if (reverseIndexK < 1)
            throw new IllegalStateException();

        Node next = myLinkedList.first;

        Node p1 = next, p2 = next;

        //p1先走reverseIndexK - 1步，p2再走
        for (int i = 0; i < reverseIndexK - 1; i++) {
            if (null == p1) {
                return null;
            }
            p1 = p1.next;
        }

        while (null != p1) {
            p1 = p1.next;
            p2 = p2.next;
        }

        return p2;
    }

    /***
     *
     * 2.从尾到头打印链表（递归和非递归）
     *
     * 思路：1、辅助栈
     *       2、Java方法栈（递归）
     */

    void printNodeReverse(MyLinkedList<E> myLinkedList) {

        Node<E> head = myLinkedList.first;

        Stack<Node<E>> stack = new Stack<>();

        while (null != head) {
            stack.push(head);
            head = head.next;
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop().item);
        }
    }

    void printNodeReverse2(Node<E> head) {

        if (null != head) {
            if (null != head.next) {
                printNodeReverse2(head.next);
            }
            System.out.println(head.item);
        }
    }

    /***
     *
     * 3.如何判断一个链表有环
     *
     * 思路：1、快慢指针
     *       2、
     */
    Node<E> haveRing(MyLinkedList<E> myLinkedList) {
        Node<E> head = myLinkedList.first;

        Node<E> slowPointer = head, fastPointer = head;

        while (null != fastPointer && null != fastPointer.next) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;

            if (slowPointer.equals(fastPointer)) {
                return slowPointer;
            }
        }

        return null;
    }

    /***
     *
     * 4.链表中环的大小
     *
     */
    public int ringCount(MyLinkedList myLinkedList) {
        Node ringNode = haveRing(myLinkedList);
        int count = 0;
        if (null == ringNode)
            return count;
        Node<E> fastPoint = ringNode.next;
        count++;
        while (ringNode != fastPoint) {
            fastPoint = fastPoint.next;
            count++;
        }
        return count;
    }

    /***
     *
     * 6.单链表在时间复杂度为O(1)删除链表结点
     */

    /***
     *
     * 7.输入两个单链表，找出他们的第一个公共结点。
     */
    public Node<E> findFirstCommonNode(MyLinkedList linkedList1, MyLinkedList linkedList2) {

        HashSet<Node<E>> set = new HashSet<>();

        Node<E> p1 = linkedList1.first;
        Node<E> p2 = linkedList2.first;

        while (null != p1 && null != p2) {
            set.add(p1);
            if (!set.add(p2)) {
                return p2;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        return null;
    }

    /***
     *
     * 8.合并两个排序的链表（输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则）
     *  如 1 3 5 7 , 2 4 6 8  -> 1 2 3 4 5 6
     *
     */
    public MyLinkedList<Integer> mergeSortList(MyLinkedList<Integer> sortList1, MyLinkedList<Integer> sortList2) {

        MyLinkedList sortList = new MyLinkedList();

        Node<Integer> head1 = sortList1.first;
        Node<Integer> head2 = sortList2.first;

        int item = head1.item < head2.item ? head1.item : head2.item;
        Node<Integer> sortFirstNode = new Node<>(item, null);
        Node<Integer> sortLastNode = sortFirstNode;

        Node<Integer> p1 = head1.next;
        Node<Integer> p2 = head2.next;

        while (null != p1 && null != p2) {
            if (p1.item < p2.item) {
                sortLastNode.next = p1;
                sortLastNode = p1;
                p1 = p1.next;
            } else if (p1.item > p2.item) {
                sortLastNode.next = p2;
                sortLastNode = p2;
                p2 = p2.next;
            } else {
                sortLastNode.next = p1;
                sortLastNode = p1;
                p1 = p1.next;

                sortLastNode.next = p2;
                sortLastNode = p2;
                p2 = p2.next;
            }
        }

        if (null != p1)
            sortLastNode.next = p1;
        if (null != p2)
            sortLastNode.next = p2;

        return new MyLinkedList<>(sortFirstNode.next, null);
    }

    public static MyLinkedList<Integer> mergeSortList2(MyLinkedList<Integer> sortList1, MyLinkedList<Integer> sortList2) {

        Node<Integer> sortFirstNode = new Node<>(-1, null);
        Node<Integer> sortLastNode = sortFirstNode;

        Node<Integer> p1 = sortList1.first;
        Node<Integer> p2 = sortList2.first;

        while (null != p1 && null != p2) {
            if (p1.item <= p2.item) {
                sortLastNode.next = p1;
                p1 = p1.next;
            } else {
                sortLastNode.next = p2;
                p2 = p2.next;
            }
            sortLastNode = sortLastNode.next;
        }

        if (null != p1)
            sortLastNode.next = p1;
        if (null != p2)
            sortLastNode.next = p2;

        return new MyLinkedList<>(sortFirstNode.next, null);
    }

    /***
     *
     * 10.反转链表
     * 定义一个函数，输入一个链表的头结点，反转该链表并输出反转后链表的头结点。
     *
     *  小于两结点不反转
     *  h 1   2 3 4 5 6
     *  h 2 1   3 4 5 6
     *  h 3 2 1   4 5 6
     *  h 4 3 2 1   5 6
     *  h 5 4 3 2 1   6
     *  h 6 5 4 3 2 1
     *
     *  分为待反转链表和已反转链表
     *
     */
    public MyLinkedList<E> reverseList(MyLinkedList<E> linkedList) {

        Node<E> head = linkedList.first;

        //小于两结点不反转
        if (null == head || null == head.next)
            return linkedList;

        Node<E> temp = new Node<E>(null, head);

        //头结点向后移动一位
        head = head.next;

        temp.next.next = null;
        while (null != head) {

            Node<E> t = head;
            //向后移动
            head = t.next;

            if (null == head)
                linkedList.last = t;

            t.next = null;

            t.next = temp.next;

            temp.next = t;
        }

        linkedList.first = temp.next;

        return linkedList;
    }

    /**
     * 11、回文链表
     * 1-2-3-2-1
     * 1-2-3-3-2-1
     * <p>
     * 1、快慢指针，找出中间位置
     * 2、反转
     * 3、比较
     */
    public boolean isPalindromeLinkedList(Node<E> head) {
        if (null == head || null == head.next)
            return false;
        Node<E> slow = head, fast = head;
        while (null != fast.next && null != fast.next.next) {
            fast = fast.next.next;
            slow = slow.next;
        }
        Node<E> reverseList = null;
        //reverseList = reverseList(slow.next);
        while (null != head && null != reverseList) {
            if (head.item != reverseList.item)
                return false;
            head = head.next;
            reverseList = reverseList.next;
        }
        return true;
    }

    public static void main(String[] args) {

        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        for (int i = 0; i < 10; i++) {
            myLinkedList.addNode(i);
        }
        myLinkedList.printList();

        System.out.println(" -1-");

        MyLinkedList<Integer> integerMyLinkedList = myLinkedList.reverseList(myLinkedList);
        integerMyLinkedList.printList();

        System.out.println(" -2-");

        MyLinkedList<Integer> myLinkedListSort1 = new MyLinkedList<>();
        for (int i = 0; i < 15; i = i + 2) {
            myLinkedListSort1.addNode(i);
        }
        myLinkedListSort1.printList();
        System.out.println(" -3-");
        MyLinkedList<Integer> myLinkedListSort2 = new MyLinkedList<>();
        for (int i = 0; i < 10; i = i + 1) {
            myLinkedListSort2.addNode(i);
        }
        myLinkedListSort2.printList();

        System.out.println(" -4-");
        MyLinkedList<Integer> integerMyLinkedListSort = MyLinkedList.mergeSortList2(myLinkedListSort1, myLinkedListSort2);
        integerMyLinkedListSort.printList();


        System.out.println(" -5-");
        /* test cache linked hash map  */

        LinkedHashMap<Integer, Integer> cache = new CacheLinkedHashMap<>();
        for (int i = 0; i < 30; i++) {
            cache.put(i, i);
        }
        System.out.print("Cache size: " + cache);

        LinkedHashMap<Integer, Integer> lruCache = new LruCacheLinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            lruCache.put(i, i);
        }
        System.out.println(" -6-");
        System.out.print("Cache size: " + lruCache);
        lruCache.get(2);
        lruCache.get(5);
        lruCache.get(6);

        System.out.println(" -7-");
        System.out.print("Cache size: " + lruCache);

        for (int i = 10; i < 30; i++) {
            lruCache.put(i, i);
        }

        System.out.println(" -8-");
        System.out.print("Cache size: " + lruCache);

    }

    /**
     * LinkedHashMap通过设置accssOrder来判断是使用查询顺序进行迭代还是访问顺序进行迭代
     */
    public static class CacheLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

        private static final int MAX_ENTRIES = 10;

        public CacheLinkedHashMap() {
            super();
        }

        public CacheLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
            super(initialCapacity, loadFactor, accessOrder);
        }

        @Override
        protected boolean removeEldestEntry(Entry<K, V> eldest) {

            return size() > MAX_ENTRIES;
        }
    }

    public static class LruCacheLinkedHashMap<K, V> extends CacheLinkedHashMap<K, V> {

        public LruCacheLinkedHashMap() {
            this(16, 0.75f);
        }

        public LruCacheLinkedHashMap(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor, true);
        }

    }

}
