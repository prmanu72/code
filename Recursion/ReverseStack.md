# Intuition
To reverse a stack using recursion:
1. Pop the top element from the stack
2. Recursively reverse the remaining stack
3. Insert the popped element at the **bottom** of the now-reversed stack

This approach leverages recursion to handle the reversal process and a helper function to insert elements at the bottom.

---

# Approach
1. **reverseStack()**: Entry point that initiates the recursive reversal.
2. **reverse()**: 
   - Base case: If stack has 0 or 1 element, it's already "reversed"
   - Recursive case:
     - Pop the top element `t`
     - Recursively reverse the remaining stack
     - Insert `t` at the bottom of the reversed stack
3. **insertAtBottom()**: 
   - Base case: If stack is empty, push the element
   - Recursive case:
     - Pop the top element `t`
     - Recursively find the bottom (insert continues down)
     - Push `t` back (shifts elements up on backtrack)

---

# Complexity

- Time complexity:
  O(n²)

For each of the n elements, insertAtBottom takes O(n) to reach the bottom and reinsert. Total: n × O(n) = O(n²).

- Space complexity:

O(n)

Recursion call stack depth is O(n). The reverse() function creates O(n) stack frames, and insertAtBottom() creates additional O(n) frames.

---

# Code
```java
class Solution {
    public static void reverseStack(Stack<Integer> st) 
    {
        reverse(st);
    }
    
    static void reverse(Stack<Integer> st)
    {
        if(st.size()<=1) return;
        
        int t = st.pop();
        
        reverse(st);
        
        insertAtBottom(st, t);
    }
    
    static void insertAtBottom(Stack<Integer> st, int k)
    {
        if(st.isEmpty())
        {
            st.push(k);
            return;
        }
        
        int t = st.pop();
        
        insertAtBottom(st, k);
        
        st.push(t);
    }
}
```

---

# One-line takeaway
Reverse a stack by recursively popping elements and reinserting them at the bottom to achieve complete reversal.
