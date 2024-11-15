package com.company;

public interface SortInterface {
    public int[] iterativeSort(int[] list) throws UnsortedException;
    public int[] recursiveSort(int[] list) throws UnsortedException;
    public int getCount();
    public long getTime();
}
