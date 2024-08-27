package com.blackmagicwoman.leecode.LeetCode100;

import com.github.pagehelper.PageHelper;

import java.util.HashMap;
import java.util.List;

/**
 * @program: mybatisTest
 * @description: 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * <p>
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * <p>
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 * 示例 2：
 * <p>
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 * 示例 3：
 * <p>
 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * 输出：[8,9,9,9,0,0,0,1]
 * @author: HeiSe
 * @create: 2024-03-02 17:20
 **/
public class AddTwoNumbers {

    public static void main(String[] args) {
//        ListNode listNode = new ListNode(2);
//        ListNode listNode2 = new ListNode(4);
//        listNode.next = listNode2;
//        ListNode listNode3 = new ListNode(3);
//        listNode2.next = listNode3;
//        ListNode listNodea = new ListNode(5);
//        ListNode listNodea2 = new ListNode(6);
//        listNodea.next = listNodea2;
//        ListNode listNodea3 = new ListNode(4);
//        listNodea2.next = listNodea3;
//        addTwoNumbers(listNode, listNodea);

        ListNode listNode = new ListNode(9);
        ListNode listNode2 = new ListNode(9);
        listNode.next = listNode2;
        ListNode listNodea = new ListNode(9);
        ListNode listNodea2 = new ListNode(9);
        listNodea.next = listNodea2;
        ListNode listNodea3 = new ListNode(9);
        ListNode listNodea4 = new ListNode(9);
        listNodea2.next = listNodea3;
        listNodea3.next = listNodea4;
        addTwoNumbers(listNode, listNodea);

//        ListNode listNode = new ListNode(0);
//        ListNode listNodea = new ListNode(0);
//        addTwoNumbers(listNode, listNodea);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        int count = 0;
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        while (l1 != null || l2 != null) {
            int i1 = 0;
            if (l1 != null) {
                i1 = l1.val;
                l1 = l1.next;
            }
            int i2 = 0;
            if (l2 != null) {
                i2 = l2.val;
                l2 = l2.next;
            }
            if (hashMap.containsKey(count)) {
                i2 += hashMap.get(count);
            }
            //进位
            int i = (i1 + i2) / 10;
            //当前位
            int ii = (i1 + i2) % 10;
            hashMap.put(count, ii);
            if (i > 0) {
                hashMap.put(count + 1, i);
            }
            count++;
        }
        int i = 0;
        ListNode listNode = new ListNode(hashMap.get(i));
        ListNode head = new ListNode();
        i++;
        head.next = listNode;
        while (true) {
            if (!hashMap.containsKey(i)) {
                break;
            }
            ListNode listNode1 = new ListNode(hashMap.get(i));
            listNode.next = listNode1;
            listNode = listNode.next;
            i++;
        }
        return head.next;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
