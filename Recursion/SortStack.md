# Intuition
Recursive stack sorting works by:
1. Recursively sorting the stack by removing the top element
2. Then inserting that element into its correct sorted position within the stack

This approach leverages recursion to maintain the invariant: "all remaining elements are sorted before inserting the next one."

---

# Approach
1. **sortStack()**: Entry point that calls the recursive sort function.
2. **sort()**: 
   - Base case: If stack has 0 or 1 elements, it's already sorted
   - Recursive case:
     - Pop the top element `k`
     - Recursively sort the remaining stack
     - Insert `k` into its correct position in the sorted stack
3. **insert()**: 
   - Base case: If stack is empty OR top element ≤ k, push k and return
   - Recursive case:
     - Pop the top element `t`
     - Recursively find the correct position for `k`
     - Push `t` back on backtracking (shifts elements up)

---

# Complexity

- Time complexity:
  O(n²)

For each element, we may need to pop multiple elements to find its correct position. In worst case (reverse sorted), this requires O(n) comparisons per element.

- Space complexity:

O(n)

Recursion call stack depth can be O(n) in worst case. The stack itself stores n elements.

---

# Code
```java
class Solution {
    public void sortStack(Stack<Integer> st) 
    {
        sort(st);
    }
    
    void sort(Stack<Integer> st)
    {
        if(st.size() <= 1) return;
        
        int k = st.pop();
        
        sort(st);
        
        insert(st, k);
    }
    
    void insert(Stack<Integer> st, int k)
    {
        if(st.isEmpty() || st.peek() <= k) 
        {
            st.push(k);
            return;
        }
        
        int t = st.pop();
        
        insert(st, k);
        
        st.push(t);
    }
}
```

---

# One-line takeaway
Recursive stack sort: Sort n-1 elements, then recursively insert the popped element at its correct position while maintaining stack order.
