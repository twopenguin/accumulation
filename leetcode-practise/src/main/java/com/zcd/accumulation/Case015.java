package com.zcd.accumulation;

public class Case015 {
    /**
     * 141. 环形链表
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode fast = head.next;
        ListNode low = head;
        while(fast!=low){
            if (fast == null) return false;
            ListNode temp = fast.next;
            if (temp == null) return false;
            fast = temp.next;
            low = low.next;
        }
        return true;
    }

    /**
     * 142. 环形链表 II
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode part1 = head;
        ListNode fast = head.next;
        ListNode low = head;
        while(fast!=low){
            if (fast == null) return null;
            ListNode temp = fast.next;
            if (temp == null) return null;
            fast = temp.next;
            low = low.next;
        }
        //low
        while (low != part1){
            low = low.next;
            part1 = part1.next;
        }
        return part1;
    }


}

/**
 * 141. 环形链表(Dependency)
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}
