package com.blackmagicwoman.sort;

/**
 * @program: mybatisTest
 * @description: 希尔排序
 * @author: heise
 * @create: 2022-09-08 22:36
 **/
public class ShellSort {
    /**
     * 希尔排序
     * @param array
     * @return
     */
    public static int[] shellSort(int[] array){
        int len=array.length;
        int temp,gap=len/2;
        while (gap>0){
            for (int i = gap; i <len ; i++) {
                temp=array[i];
                int preIndex=i-gap;
                while (preIndex>=0&&array[preIndex]>temp){
                    array[preIndex+gap]=array[preIndex];
                    preIndex-=gap;
                }
                array[preIndex+gap]=temp;
            }
            gap/=2;
        }
        return array;
    }

    public static void main(String[] args) {
       int[] a= new int[]{1, 9, 2, 4, 5, 6, 7,3,8,0};
        int[] ints = shellSort(a);
        System.out.println(ints);

    }
}
