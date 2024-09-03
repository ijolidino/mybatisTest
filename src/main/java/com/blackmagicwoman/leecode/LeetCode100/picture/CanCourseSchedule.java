package com.blackmagicwoman.leecode.LeetCode100.picture;

import java.util.*;

/**
 * @program: mybatisTest
 * @description: 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
 * <p>
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
 * <p>
 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：true
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
 * 示例 2：
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
 * 输出：false
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
 * @author: HeiSe
 * @create: 2024-03-12 20:22
 **/
public class CanCourseSchedule {

    public static void main(String[] args) {
        canFinish(5,new int[][]{{1,4},{2,4},{3,1},{3,2}});
    }
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses == 0 || prerequisites.length == 0) return false;
        Map<Integer, ArrayList<Integer>> map = new HashMap<>(numCourses);
        int[] ints = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            int curr = prerequisites[i][0];
            int pre = prerequisites[i][1];
            ints[curr]++;
            if (map.containsKey(pre)) {
                map.get(pre).add(curr);
            } else {
                ArrayList<Integer> integers = new ArrayList<>();
                integers.add(curr);
                map.put(pre, integers);
            }
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty() && numCourses > 0) {
            Integer poll = queue.remove();
            numCourses--;
            ArrayList<Integer> list = map.get(poll);
            if (Objects.isNull(list)) continue;
            for (int i = 0; i < list.size(); i++) {
                ints[list.get(i)]--;
                if (ints[i] == 0) {
                    queue.offer(list.get(i));
                }
            }
        }
        return numCourses == 0;
    }
}
