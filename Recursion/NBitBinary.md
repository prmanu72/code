# Intuition
To generate n-bit binary numbers where each prefix has more (or equal) 1's than 0's:
1. Start with "1" (must begin with 1 for this constraint)
2. At each position, decide: add "1" or add "0"
3. Only add "0" if we still maintain the invariant: `ones >= zeroes`
4. Generate in decreasing order by trying "1" before "0" at each step

This is a constrained permutation problem where the constraint prunes invalid branches early.

---

# Approach
1. **NBitBinary()**: 
   - Initialize with "1" (first digit must be 1)
   - Start with `ones=1, zeroes=0`, and `n-1` remaining positions
   - Call recursive solve function
2. **solve(n, ones, zeroes, cur, res)**:
   - **Pruning:** If `zeroes > ones`, this path violates the constraint, return
   - **Base case:** If n reaches 0, all positions filled, add current string to result
   - **Recursive case (explore in decreasing order):**
     - **Option 1:** Add "1" (always valid if we have positions left)
     - Recurse with `ones + 1`
     - **Option 2:** Add "0" (after trying "1")
     - Recurse with `zeroes + 1`

**Example for n=3:**
```
Start: "1", ones=1, zeroes=0, n=2
  Add "1": "11", ones=2, zeroes=0, n=1
    Add "1": "111" ✓ (ones=3, zeroes=0)
    Add "0": "110" ✓ (ones=2, zeroes=1)
  Add "0": "10", ones=1, zeroes=1, n=1
    Add "1": "101" ✓ (ones=2, zeroes=1)
    Add "0": "100" ✗ (zeroes > ones, pruned)

Result: ["111", "110", "101"]
```

---

# Complexity

- Time complexity:
  O(2^n)

Generate all valid n-bit binary strings (at most 2^n). The pruning eliminates invalid branches, so actual count is much less.

- Space complexity:

O(2^n)

Store all valid strings. Recursion stack depth is O(n).

---

# Code
```java
class Solution {
    ArrayList<String> NBitBinary(int n) 
    {
        ArrayList<String> res = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        cur.append("1");
        
        solve(n - 1, 1, 0, cur, res);
        
        return res;
    }
    
    void solve(int n, int ones, int zeroes, StringBuilder cur, ArrayList<String> res)
    {
        // Pruning: more zeroes than ones violates constraint
        if(zeroes > ones) return;
        
        // Base case: all n positions filled
        if(n == 0)
        {
            res.add(cur.toString());
            return;
        }
        
        int len = cur.length();
        
        // Option 1: add "1" (try this first for decreasing order)
        cur.append("1");
        solve(n - 1, ones + 1, zeroes, cur, res);
        cur.setLength(len);
        
        // Option 2: add "0" (after trying "1")
        cur.append("0");
        solve(n - 1, ones, zeroes + 1, cur, res);
        cur.setLength(len);
    }
}
```

---

# Key Insights

1. **Start with "1"**: The first digit must be 1 to satisfy the constraint at the first prefix
2. **Early pruning**: `if(zeroes > ones) return;` eliminates invalid paths before exploring further
3. **Implicit decreasing order**: By trying "1" before "0" at each step, the recursion naturally generates strings in decreasing magnitude (111 > 110 > 101 > ...)
4. **Constraint tracking**: Maintain count of ones and zeroes to validate the prefix condition
5. **Efficient backtracking**: `StringBuilder.setLength()` efficiently resets state without creating new strings

---

# One-line takeaway
Generate constrained n-bit binary numbers by starting with "1" and recursively adding "1" or "0" while maintaining the invariant that at any prefix, ones ≥ zeroes.
