package com.company;

import java.util.Arrays;

public class YourSort implements SortInterface {
    int count; //3 main operations - move 1st pointer, move 2nd pointer, and swap elements
    long start;
    long end;

    //taken from https://www.geeksforgeeks.org/bubble-sort/
    public int[] iterativeSort(int[] list) throws UnsortedException {
        start();
        int n = list.length;
        for (int i = 0; i < n - 1; i++){
            count++; //move 1st pointer
            for (int j = 0; j < n - i - 1; j++){
                count++; //move 2nd pointer
                if (list[j] > list[j + 1]) {
                    // swap arr[j+1] and arr[j]
                    count++;
                    int temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
        if(!isValid(list))
            throw new UnsortedException("Not sorted");
        return list;
    }
    public int[] recursiveSort(int[] list) throws UnsortedException {
        start();
        list = recurse(list);
        if(!isValid(list))
            throw new UnsortedException("Not sorted");
        return list;
    }
    private int[] recurse(int[] list){
        count++; //move 1st pointer
        if(list.length < 2){
            return list;
        }
        list = recurse(list, 0);

        count++; //copy array
        return recurse(Arrays.copyOfRange(list, 1, list.length));
    }
    int[] recurse(int[] list, int i){
        count++; //move 2nd pointer
        if(i == list.length - 1){
            return list;
        }
        if (list[i] > list[i + 1]) {
            count++; //swap elements
            int temp = list[i];
            list[i] = list[i + 1];
            list[i + 1] = temp;
        }
        return recurse(list, i+1);
    }

    public int getCount() {
        return count;
    }

    public long getTime() {
        return end - start;
    }
    private void start(){
        count = 0;
        start = System.nanoTime();
    }
    private boolean isValid(int[] list){
        end = System.nanoTime();
        return check(list);
    }

    private boolean check(int[] list) {
        if (list.length < 2) {
            return true;
        }
        int current = 1;
        while (current < list.length) {
            if (list[current - 1] > list[current]) {
                return false;
            }
            current++;
        }
        return true;
    }
}
