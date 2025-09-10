/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

 // Time: O(m + n)
// Space: O(1)
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
       ListNode l1 = headA,l2 = headB;
       while(l1 != l2)
       {
        l1 = (l1 == null) ? headB: l1.next;
        l2 = (l2 == null) ? headA: l2.next;
       }
        return l1;
    }
}


 // Time: O(m + n)
// Space: O(m + n)
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Stack<ListNode> stackA = new Stack<>();
        Stack<ListNode> stackB = new Stack<>();
        ListNode pa = headA, pb = headB;
        while(pa!=null) 
        {
            stackA.push(pa);
            pa = pa.next;
        }
        while(pb!=null) 
        {
            stackB.push(pb);
            pb = pb.next;
        }

        ListNode ans = null;

        while(!stackA.isEmpty() && !stackB.isEmpty())
        {
            ListNode ta = stackA.pop();
            ListNode tb = stackB.pop();
            if(ta == tb)
            {
                ans = ta;
            }
            else
            {
                return ans;
            }
        }
        return ans;
    }
}