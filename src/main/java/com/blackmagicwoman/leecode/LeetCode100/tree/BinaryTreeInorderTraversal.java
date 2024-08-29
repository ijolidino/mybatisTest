package com.blackmagicwoman.leecode.LeetCode100.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybatisTest
 * @description: 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
 * @author: HeiSe
 * @create: 2024-03-07 21:06
 **/
public class BinaryTreeInorderTraversal {

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        TreeNode treeNode1 = new TreeNode(2);
        TreeNode treeNode2 = new TreeNode(3);
        TreeNode treeNode3 = new TreeNode(4);
        TreeNode treeNode4 = new TreeNode(5);
        treeNode.left = treeNode1;
        treeNode1.right = treeNode3;
        treeNode.right = treeNode2;
        treeNode2.left = treeNode4;
        List<Integer> integerList = inorderTraversal(treeNode);
        System.out.println(integerList);
    }

    private static List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        addTree(root.left, result);
        result.add(root.val);
        addTree(root.right, result);
        return result;
    }

    private static void addTree(TreeNode treeNode, List<Integer> result) {
        if (treeNode == null) {
            return;
        }
        addTree(treeNode.left, result);
        result.add(treeNode.val);
        addTree(treeNode.right, result);
    }


    private static class TreeNode {
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
