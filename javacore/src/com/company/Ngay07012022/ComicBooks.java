package com.company.Ngay07012022;

import java.awt.print.Book;
import java.util.*;

public class ComicBooks extends baseBook {
    public static <E> void main(String[] args) {
        int[] array = new int[]{1,2,3,4,5,6,};
        ArrayList<Integer> arrayListInteger = new ArrayList<>();
        Iterator<Integer> iterator = arrayListInteger.iterator();
        BitSet bits1 = new BitSet(10);
        for (int i = 0 ;i<array.length;i++) {
            System.out.println(array[i]);
        }
        Stack<E> stack = new Stack<E>();
        Map mapA = new HashMap();
        HashMap<Integer, Float> hashMap1 = new HashMap<>();
    }
    static void stack_push(Stack<Integer> stack)
    {
        for(int i = 0; i < 5; i++)
        {
            stack.push(i);
        }
    }
}
