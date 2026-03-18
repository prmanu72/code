# Intuition
To delete the middle element of a stack recursively:
1. Calculate which position is the middle element
2. Recursively pop elements until we reach that position
3. Skip popping the middle element (delete it)
4. Push back all the popped elements to restore stack order

The trick is using the recursion stack depth to track which element to skip.

---

# Approach
1. **deleteMid()**: 
   - Calculate middle index as `(n+1)/2` (for 1-indexed position)
   - Call recursive delete function with this index
2. **delete()**: 
   - Base case: When `idx == s.size()`, it means we've reached the middle element
     - Pop it and return (this deletes the middle)
   - Recursive case:
     - Pop current top element `t`
     - Recursively call delete (moves closer to middle)
     - Push `t` back on backtracking (restores stack order)

---

# Complexity

- Time complexity:
  O(n)

We visit and pop each element exactly once, and push back n-1 elements.

- Space complexity:

O(n)

Recursion call stack depth is O(n) in worst case. The stack itself stores elements.

---

# Code
```java
class Solution {
    public void deleteMid(Stack<Integer> s) {
       int n = s.size();
       if(n == 0) return;
       
       delete(s, (n+1)/2);
    }
    
    void delete(Stack<Integer> s, int idx)
    {
        if(idx == s.size())
        {
            s.pop();
            return;
        }
        
        int t = s.pop();
        
        delete(s, idx);
        s.push(t);
    }
}
```

---

# One-line takeaway
Recursively delete the middle element by popping elements until reaching the middle position, then pushing them back to restore order.
