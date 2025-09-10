/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) 
    {
        if(head == null || head.next == null) return head;
        ListNode p, q;
        p = null;
        q = head;

        while(q != null)
        {
           ListNode next = q.next;
            q.next = p;
            p = q;
            q = next;
        }
        return p;
    }
}

// recursive
class Solution {
    public ListNode reverseList(ListNode head) 
    {
        if(head == null || head.next == null) return head;
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}