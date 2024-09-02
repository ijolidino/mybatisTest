package com.blackmagicwoman.leecode.LeetCode100.backdate;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybatisTest
 * @description: 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
 * <p>
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
 * <p>
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：candidates = [2,3,6,7], target = 7
 * 输出：[[2,2,3],[7]]
 * 解释：
 * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
 * 7 也是一个候选， 7 = 7 。
 * 仅有这两种组合。
 * 示例 2：
 * <p>
 * 输入: candidates = [2,3,5], target = 8
 * 输出: [[2,2,2,2],[2,3,3],[3,5]]
 * 示例 3：
 * <p>
 * 输入: candidates = [2], target = 1
 * 输出: []
 * @author: HeiSe
 * @create: 2024-03-13 20:05
 **/
public class CanCombinationSum {

    public static void main(String[] args) {
        combinationSum(new int[]{2, 3, 5}, 8);
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        int begin = 0;
        generator(candidates, target, begin, integers, result);
        return result;
    }

    private static void generator(int[] candidates, int target, int begin, List<Integer> integers, List<List<Integer>> result) {
        for (int i = begin; i < candidates.length; i++) {
            if (target - candidates[i] < 0) continue;
            integers.add(candidates[i]);
            if (target - candidates[i] == 0) {
                result.add(new ArrayList<>(integers));
            }
            generator(candidates, target - candidates[i], i, integers, result);
            integers.remove(integers.size() - 1);
        }
    }
}
