package com.crazy.crazylearn.datastructure.tree2;

import java.util.Arrays;

public class KMP {

    public static int search(char[] p, char[] t) {

        int m = p.length;
        int n = t.length;

        int i = 0, j = 0;

        while (i < n && j < m) {

            if (p[j] == t[i]) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }

        return i - j;
    }

    public static int kmp_search(char[] p, char[] t) {

        int[] next = buildNext(p);

        int m = p.length;
        int n = t.length;

        int i = 0, j = 0;

        while (i < n && j < m) {

            if (j < 0 || p[j] == t[i]) {
                i++;
                j++;
            } else {
                //  i = i - j + 1;
                //  j = 0;
                //文本串t不用回退，模式串右移到j
                j = next[j];
            }
        }

        return i - j;
    }


    /**
     *  0 1 2 3 4 5 6
     *
     *  a b c d f e g
     *  a a a b b c c
     * -1 0 1 2 0 0 0
     *
     * -1
     *
     * @param p
     * @return
     */


    public static int[] buildNext(char[] p) {

        int m = p.length;
        int j = 0;

        int[] next = new int[m];
        int t = next[0] = -1;

        while (j < m - 1) {

            if (0 > t || p[j] == p[t]) {
                j++;
                t++;
               // next[j] = t;
                next[j] = (p[j] != p[t]) ? t : next[t];
            } else {
                t = next[t];
            }
        }
        return next;
    }

    public static void main(String[] args) {

        String p = "aaabbcc";

        int[] next = buildNext(p.toCharArray());

        System.out.println("next = " + (Arrays.toString(next)));

    }
}
