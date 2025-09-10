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
    public void reorderList(ListNode head) {
        if(head == null|| head.next==null)return;
        ListNode fast=head, slow=head;
        ListNode prev =null;
        while(fast!=null && fast.next!=null)
        {
            fast = fast.next.next;
            prev = slow;
            slow = slow.next;
        }
        prev.next =null;
        ListNode rev = reverseList(slow);
        head = mergeTwoLists(head,rev);
         
    }

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

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) 
    {
        ListNode dummy = new ListNode();
        ListNode curr = dummy;
        int i = 0; 
        while(list1!= null && list2!=null)
        {
            if( i%2 ==0)
            {
                curr.next = list1;
                list1 =list1.next;
            }
            else
            {
                curr.next = list2;
                list2 =list2.next;
            }
            curr = curr.next;
            i++;
        }
        if(list1 == null) curr.next = list2;
        if(list2 == null) curr.next = list1;
        return dummy.next;
    }

}
