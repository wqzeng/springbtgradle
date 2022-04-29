package com.wqzeng.offer;

import org.junit.Assert;
import org.junit.Test;

//单链表
public class ListNode {
    @Test
    public void testReverse() {
        Node head = new Node(5);
        head.append(4);
        head.append(3);
        head.append(2);
        head.append(1);
        Node reverse = reverse(head);
        Assert.assertEquals(1, reverse.value);
        Assert.assertEquals(2, reverse.next.value);
    }

    class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }

        public boolean hasNext() {
            return next != null;
        }

        public void append(int value) {
            Node newNode = new Node(value);
            Node next = this;
            while (next.hasNext()) {
                next = next.next;
            }
            next.next = newNode;
        }
    }

    /**
     * 反转单链表
     *
     * @param node
     * @return
     */
    private Node reverse(Node node) {
        Node prev = null;
        Node cur = node;
        while (cur != null) {
            Node next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    private Node reverse2(Node node) {
        Node prev = null;
        Node cur = node;
        while (cur != null) {
            Node next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
