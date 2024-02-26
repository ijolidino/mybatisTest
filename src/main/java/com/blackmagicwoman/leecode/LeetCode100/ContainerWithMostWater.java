package com.blackmagicwoman.leecode.LeetCode100;

/**
 * @program: mybatisTest
 * @description: 包含最多的水
 * @author: HeiSe
 * @create: 2024-02-23 22:48
 **/
public class ContainerWithMostWater {

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));

        System.out.println(maxArea1(new int[]{1, 8, 6, 2, 5, 4, 1000, 1000, 7}));
    }

    public static int maxArea(int[] height) {
        if (height.length == 0 || height.length == 1) {
            return 0;
        }
        if (height.length == 2) {
            return Math.min(height[0], height[1]);
        }
        int max = 0;
        int maxLen = height[0];
        for (int i = 0; i < height.length; i++) {
            if (maxLen>height[i]){
                continue;
            }
            maxLen=height[i];
            int j = i;
            while (++j < height.length) {
                max = Math.max(Math.min(height[i], height[j]) * (j - i), max);
            }
        }

        return max;
    }

    public static int maxArea1(int[] height) {
        int i = 0, j = height.length - 1, res = 0;
        while(i < j) {
            res = height[i] < height[j] ?
                    Math.max(res, (j - i) * height[i++]):
                    Math.max(res, (j - i) * height[j--]);
        }
        return res;
    }
}
