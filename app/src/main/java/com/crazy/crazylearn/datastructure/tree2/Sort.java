package com.crazy.crazylearn.datastructure.tree2;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Random;

public class Sort<AnyType extends Comparable<? super AnyType>> {

    /***
     * 排序数据结构
     */
    public static class SortList<AnyType extends Comparable<? super AnyType>> {
        int length;
        AnyType[] sortArray;

        public SortList(@NonNull AnyType[] sortArray) {
            this.sortArray = sortArray;
            this.length = sortArray.length;
        }

        public AnyType get(int pos) {
            return sortArray[pos];
        }
    }

    /**
     * 排序列表，from与to交换位置
     */
    public void swap(SortList<AnyType> sortList, int from, int to) {
        AnyType temp = sortList.sortArray[from];
        sortList.sortArray[from] = sortList.sortArray[to];
        sortList.sortArray[to] = temp;
    }

    public static void main(String[] args) {

        Sort<Integer> sort = new Sort<>();

       // Integer[] sortInt = {-1, 1, 11, 3, 18, 2, 9, 5, 8, 0};
        Integer[] sortInt = sort.buildSortList();

        SortList<Integer> sortList = new SortList<Integer>(sortInt);

        //sort.insertSort(sortList);

        long startTime = System.currentTimeMillis();

        sort.insertSort1(sortList);

        long endTime = System.currentTimeMillis();

        System.out.println("-----insertSort-------- " + (endTime - startTime));

        System.out.println("-------------");

        long startTime1 = System.currentTimeMillis();
        sort.shellSort(sortList);
        long endTime1 = System.currentTimeMillis();
        System.out.println("-----shellSort-------- " + (endTime1 - startTime1));

       // System.out.println(Arrays.toString(sortInt));

    }

    public Integer[] buildSortList() {

        int count = 10000 *100;

        Integer[] sortArray = new Integer[count];

        Random random = new Random();

        for (int i = 0; i < sortArray.length; i++) {
            sortArray[i] = random.nextInt(count) - 1;
        }

        return sortArray;
    }

    /***
     * 从小到大
     */
    /**
     * 稳定性：待排序的序列中存在相等的元素，经过排序后，相等的元素之间依然保持原有的先后顺序
     */

    /***
     * 冒泡排序（BubbleSort）
     *
     * 是一种交换排序，思想:两两比较相邻元素的关键字，如果反序则交换，直到没有反序为止
     *
     *  其思想是通过无序区中相邻记录关键字间的比较和位置的交换,使关键字最小的记录如气泡一般逐渐往上“漂浮”直至“水面”。
     *
     *  冒泡排序初级版 （i与其后所有位置都比较过后，i递增一位，继续重复，直至结束）
     *  排序好的位置对后续关键字的排序没有帮助
     *
     *
     *  1、时间复杂度
     *     最好：O(n)
     *     最坏：O(n^2)
     *  2、空间复杂度：
     *      O(1)
     *  3、稳定性：
     *     是稳定的排序算法，相邻元素若相等，不需要交换位置
     *
     *  效率：（i - 1） * (j - 1)! + 3 ==> O(n * n)
     *
     *
     *
     */

    public void bubbleSort0(SortList<AnyType> sortList) {
        for (int i = 0; i < sortList.length - 1; i++) {
            for (int j = i + 1; j < sortList.length; j++) { //j在剩余元素中排序（自前往后走）
                if (sortList.get(i).compareTo(sortList.get(j)) > 0) {//
                    swap(sortList, i, j);
                }
            }
        }
    }

    /***
     * 正宗冒泡排序
     *
     * 較小的元素会如同气泡慢慢浮到上面（相较于bubbleSort0，每次排序，较小元素都会上浮，因此由于bubbleSort0）
     *
     * 效率：（i） * (j!)==> O(n * n)
     */
    public void bubbleSort1(SortList<AnyType> sortList) {
        for (int i = 0; i < sortList.length - 1; i++) { //i相当于排序完成的位置，所以j>=i,j要在剩余元素中排序
            for (int j = sortList.length - 1 - 1; j >= i; j--) { //j从后往前循环
                if (sortList.get(j).compareTo(sortList.get(j + 1)) > 0) {//前一位比后一位大舅交换
                    swap(sortList, i, j);
                }
            }
        }
    }

    /***
     * 冒泡排序優化
     * <p>
     * 由于每次都需要从最底部（倒数第一与倒数第二位）开始冒泡，当下一轮冒泡开始后直到i的位置没有一次交换位置，
     * 说明除了0-i以外的位置都是有序的了，所以无需再做交换，直接退出就好
     */
    public void bubbleSort2(SortList<AnyType> sortList) {

        boolean status = true;//

        for (int i = 0; i < sortList.length - 1 && status; i++) { //i相当于排序完成的位置，所以j>=i,j要在剩余元素中排序

            status = false; //从底部到顶部的一次冒泡过程中，是否交换過位置

            for (int j = sortList.length - 1 - 1; j >= i; j--) { //j从后往前循环
                if (sortList.get(j).compareTo(sortList.get(j + 1)) > 0) {//前一位比后一位大舅交换
                    swap(sortList, i, j);
                    status = true;
                }
            }
        }
    }


    /***
     **************************************选择排序***********************************************
     */

    /***
     * 选择排序
     *
     *  选择出最佳方案（如找出最小值）后方才交换位置
     *
     *  基本思路：每一趟在n-i+1(i=1.2.3...n-1)个记录中选取关键字最小的记录，作为有序序列的第i个记录
     *
     *  在未排序表中从前往后找到最小值后交换位置
     */

    /***
     * 简单选择排序
     * <p>
     * 特点：交换移动数据次数比较少（节约了时间）
     *
     * 复杂度：
     *      交换而言：最好O(0),最坏O(n - 1)
     *      比较次数而言：最好最坏一样 O(n(n-1)/2)
     *
     *      最终总的时间复杂度 = O(n^2) => 比较与交换的综总和
     *
     * 性能略优于冒泡排序
     */
    public void selectSort(SortList<AnyType> sortList) {

        int i, j, min;

        //i为等待交换的（存放最小元素）位置
        for (i = 0; i < sortList.length - 1; i++) {
            min = i;
            //从j到length的过程中找出最小元素的位置
            for (j = i + 1; j < sortList.length; j++) {
                if (sortList.get(min).compareTo(sortList.get(j)) > 0) {
                    min = j;
                }
            }
            //一趟比较执行完毕后（j到length完毕后）找到最小位置,然后与i交换
            if (sortList.get(i).compareTo(sortList.get(min)) > 0) {
                swap(sortList, i, min);
            }
        }
    }


    /***
     *************************************************************************************
     */

    /****
     * 直接插入排序（Straight Insertion Sort）
     *
     * 基本操作是：将一个记录，插入到已经排好序的有序表中，从而得到一个新的，记录数增加1的有序表
     *
     * sortList[0] = 0 位置为预留位置，作为哨兵
     *
     * 从前往后遇到较小值就插入
     *
     * 时间复杂度：O(n)
     *    最好情况：没有移动，o（n）
     *    最坏情况：（n+2）*(n-1)/2
     *
     *
     *
     *
     */
    public void insertSort(SortList<AnyType> sortList) {

        if (sortList.length <= 1) return;
        int i, j;

        //第一个位置为空，作为哨兵，之后从第二个位置开始
        for (i = 2; i < sortList.length; i++) {
            if (sortList.get(i - 1).compareTo(sortList.get(i)) > 0) { //前一项大于后一项
                sortList.sortArray[0] = sortList.get(i);//将i出的值插入0处，空出位置i
                for (j = i - 1; sortList.get(j).compareTo(sortList.get(0)) > 0; j--) {//i的前一个位置若大于0处，
                    sortList.sortArray[j + 1] = sortList.get(j); //将j所在位置的值后移一位
                    System.out.println(Arrays.toString(sortList.sortArray));
                }
                sortList.sortArray[j + 1] = sortList.get(0);
                System.out.println(Arrays.toString(sortList.sortArray));
            }
        }
    }

    public void insertSort1(SortList<AnyType> sortList) {
        AnyType temp;
        int before, current;

        //第一个位置为空，作为哨兵，之后从第二个位置开始
        for (current = 1; current < sortList.length; current++) {
            before = current - 1;
            if (sortList.get(current).compareTo(sortList.get(before)) < 0) { //当前元素大于前一项
                temp = sortList.get(current);//保存当前元素
                for (; before >= 0 && sortList.get(before).compareTo(temp) > 0; before--) {//i的前一个位置若大于0处，
                    sortList.sortArray[before + 1] = sortList.get(before);
                  //  System.out.println(Arrays.toString(sortList.sortArray));
                }
                sortList.sortArray[before + 1] = temp;
               // System.out.println(Arrays.toString(sortList.sortArray));
            }
        }
    }

    public void insertSort2(int[] sort) {

        int i, j, temp;

        for (i = 1; i < sort.length; i++) {
            temp = i;
            for (j = i; j >= 0 && sort[j - 1] > temp; j--) {//在已排序序列中将比当前temp大的树后移
                sort[j] = sort[j - 1];
            }
            sort[j] = temp;//将temp插入空位
        }
    }

    /***
     *
     *  希尔排序 （Shell Sort）
     *
     *  1、对直接插入排序改进增加效率
     *
     *
     */
    public void shellSort(SortList<AnyType> sortList) {

        int j;

        for (int gap = sortList.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < sortList.length; i++) {
                AnyType temp = sortList.sortArray[i];

                for (j = i; j >= gap && temp.compareTo(sortList.sortArray[j - gap]) < 0; j -= gap) {
                    sortList.sortArray[j] = sortList.sortArray[j - gap];
                }
                sortList.sortArray[j] = temp;
            }
        }
    }


    /***
     *
     * 快速排序
     *  0、分而治之
     *  1、分支策略
     *  2、轴点
     *
     *  L <= pivot <= G ;U = [lo,hi];[lo]和[hi]交替空闲
     *
     */


}
