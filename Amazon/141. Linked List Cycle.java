/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode fast, slow;
        if(head == null || head.next == null) return false;
        slow = head;
        fast = head.next;
        while(fast != null && fast.next != null)
        {
            if(slow == fast) return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }
}

public class Solution {
    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) return false;
        Set<ListNode> set = new HashSet<>();
        while(head != null)
        {
            if(set.contains(head)) return true;
            set.add(head);
            head = head.next;
        }
        return false;
    }
}