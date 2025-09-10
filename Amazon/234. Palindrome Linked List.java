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
    public boolean isPalindrome(ListNode head) {
        ListNode fast, slow;
        fast = head;
        slow = head;
        
        while(fast != null && fast.next != null)
        {
            fast = fast.next.next;
            slow = slow.next;
        }
        ListNode secondHalf = reverseLinkedList(slow);
        ListNode p1 = head;
        while(secondHalf != null)
        {
            if(p1.val != secondHalf.val)
            {
                return false;
            }
            p1 = p1.next;
            secondHalf = secondHalf.next;
        }
        return true;
    }

    public static ListNode reverseLinkedList(ListNode head)
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

//other approaches
// recursive - Time: O(n), Space: O(n) (due to recursion stack).
class Solution {
    private ListNode front;

    public boolean isPalindrome(ListNode head) {
        front = head;
        return checkRecursively(head);
    }

    private boolean checkRecursively(ListNode current) {
        if (current == null) return true;
        if (!checkRecursively(current.next)) return false;
        if (front.val != current.val) return false;
        front = front.next;
        return true;
    }
}

// stack to store 2nd half of list -  Time: O(n), Space: O(n/2)
public boolean isPalindrome(ListNode head) {
    Stack<Integer> stack = new Stack<>();
    ListNode slow = head, fast = head;

    // Push first half into stack
    while (fast != null && fast.next != null) {
        stack.push(slow.val);
        slow = slow.next;
        fast = fast.next.next;
    }

    // Skip middle node for odd length
    if (fast != null) slow = slow.next;

    // Compare second half with stack
    while (slow != null) {
        if (stack.pop() != slow.val) return false;
        slow = slow.next;
    }
    return true;
}
