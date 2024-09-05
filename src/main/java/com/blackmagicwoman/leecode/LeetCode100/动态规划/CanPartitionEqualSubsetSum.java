package com.blackmagicwoman.leecode.LeetCode100.动态规划;

import java.util.Arrays;

/**
 * @program: mybatisTest
 * @description:给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * 示例 1：
 * 输入：nums = [1,5,11,5]
 * 输出：true
 * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
 * 示例 2：
 * 输入：nums = [1,2,3,5]
 * 输出：false
 * 解释：数组不能分割成两个元素和相等的子集。
 * @author: HeiSe
 * @create: 2024-03-22 20:17
 **/
public class CanPartitionEqualSubsetSum {

    public static void main(String[] args) {

    }

    public static boolean canPartition(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        sum /= 2;
        int value = 0;
        for (int num : nums) {
            value += num;
            if (value == sum) return true;
        }
        return false;
    }
}
