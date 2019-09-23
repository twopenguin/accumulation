package com.zcd.accumulation.common.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类中放一些针对集合的操作
 */
public class CollectionOperation {

    /**
     * 判断集合或者Map 是否为null，或者size == 0
     * 使用 SpringFramework 的 CollectionUtils.isEmpty 方法
     *
     * @return
     */
    public void isEmpty() {
        Map<String, Object> map = new HashMap<>();
        CollectionUtils.isEmpty(map);

        List<String> list = new ArrayList<>();
        CollectionUtils.isEmpty(list);
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode remain = head;
        ListNode start = head;
        ListNode end = head;
        int length = 0;
        while (start != null){
            if (length == n){
                start = start.next;
                end = end.next;
            }
            if (length < n){
                start = start.next;
                length++;
            }
            if (start.next == null){
                end.next = end.next.next;
                break;
            }
        }
        return remain;
    }
    
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
    
    
    
    
}
