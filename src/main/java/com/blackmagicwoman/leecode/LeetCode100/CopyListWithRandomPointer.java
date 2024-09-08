package com.blackmagicwoman.leecode.LeetCode100;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @program: mybatisTest
 * @description: 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
 * <p>
 * 构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。
 * <p>
 * 例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。
 * <p>
 * 返回复制链表的头节点。
 * <p>
 * 用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
 * <p>
 * val：一个表示 Node.val 的整数。
 * random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
 * 你的代码 只 接受原链表的头节点 head 作为传入参数。
 * <p>
 * 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * 输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
 * <p>
 * 输入：head = [[1,1],[2,1]]
 * 输出：[[1,1],[2,1]]
 * <p>
 * 输入：head = [[3,null],[3,0],[3,null]]
 * 输出：[[3,null],[3,0],[3,null]]
 * @author: HeiSe
 * @create: 2024-03-06 20:25
 **/
public class CopyListWithRandomPointer {

    public static void main(String[] args) {
        Node listNode = new Node(1);
        Node listNode2 = new Node(2);
        listNode.next = listNode2;
        Node listNode3 = new Node(3);
        Node listNode4 = new Node(4);
        Node listNode5 = new Node(5);
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode.random= listNode5;
        listNode3.random = listNode;
        listNode4.random = listNode2;
        listNode2.random = listNode4;
        listNode5.random = listNode3;
        copyRandomList(listNode);
    }

    public static Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        HashMap<Integer, Node> hashMap = new HashMap<>();
        HashMap<Node, Integer> reVerashMap = new HashMap<>();
        HashMap<Integer, Node> somap = new HashMap<>();
        Node dm = new Node(0);
        Node move = dm;
        Integer i = 0;
        while (head != null) {
            hashMap.put(i, head);
            reVerashMap.put(head, i);
            Node node = new Node(head.val);
            move.next = node;
            somap.put(i, node);
            head = head.next;
            move = move.next;
            i++;
        }

        for (Map.Entry<Integer, Node> entry : somap.entrySet()) {
            Integer key = entry.getKey();
            Node node = hashMap.get(key);
            Node random = node.random;
            Integer i1 = reVerashMap.get(random);
            Node node1 = somap.get(i1);
            entry.getValue().random = node1;
        }
        return dm.next;
    }

    private static class Node {
        int val;
        Node next;

        Node random;

        Node() {
        }

        Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }

        Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
}
