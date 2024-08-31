package com.blackmagicwoman.leecode.LeetCode100.tree;

/**
 * @program: mybatisTest
 * @description: 二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
 * <p>
 * 路径和 是路径中各节点值的总和。
 * <p>
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 * @author: HeiSe
 * @create: 2024-03-09 13:11
 **/
public class BinaryTreeMaximumPathSum {

    public static void main(String[] args) {

    }

    private int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        getMax(root);
        return max;
    }

    private int getMax(TreeNode root) {
        if (root == null) return 0;
        //获取左边最大的贡献值
        int leftSum = Math.max(getMax(root.left), 0);
        //获取右边最大的贡献值
        int rightSum = Math.max(getMax(root.right), 0);
        //得到当前节点的最大的贡献值
        int currSum = root.val + leftSum + rightSum;
        max = Math.max(currSum, max);
        return root.val + Math.max(leftSum, rightSum);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
