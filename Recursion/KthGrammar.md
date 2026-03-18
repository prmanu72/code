`779. K-th Symbol in Grammar`
# Intuition
The Kth Grammar problem builds a sequence recursively:
- **Row 1:** `0`
- **Row 2:** `01` (0 stays 0, then 1 is the inverted 0)
- **Row 3:** `0110` (0 stays 0, 1 stays 1, then invert: 1, 0)
- **Row 4:** `01101001` (0, 1, 1, 0, then invert: 1, 0, 0, 1)

Pattern: Each row = previous row + inverted(previous row)

The key insight: Instead of building the entire string, we can recursively narrow down to find the kth element without constructing everything.

---

# Approach
1. **Base case (n=1):** Return "0"
2. **Base case (n=2):** Return "0" if k=1, else "1"
3. **Recursive case:**
   - Calculate length of current row: `len = 2^(n-1)` (or `1 << (n-1)`)
   - If `k <= len/2`: Element is in the first half (same as previous row)
     - Recursively find in row n-1 at position k
   - Else: Element is in the second half (inverted portion)
     - Recursively find in row n-1 at adjusted position `(k - len/2)`
     - Invert the result (flip 0↔1)

**Optimization:** We avoid building the full string by recursively reducing the problem.

---

# Complexity

- Time complexity:
  O(n)

Each recursive call reduces n by 1. At most n recursive calls.

- Space complexity:

O(n)

Recursion call stack depth is O(n).

---

# Code
```java
class Solution {
    public int kthGrammar(int n, int k) 
    {
        return Integer.parseInt(solve(n, k));
    }

    String solve(int n, int k)
    {
        if(n == 1) return "0";
        if(n == 2)
        {
            return k == 1 ? "0" : "1"; 
        } 

        int len = 1 << (n-1);

        if(k <= len/2)
        {
            return solve(n-1, k);
        }
        else
        {
            String result = solve(n-1, k - len/2);
            return result.equals("0") ? "1" : "0";
        }
    }
}
```

**Note:** Fixed string comparison from `==` to `.equals()` for proper Java string comparison.

---

# One-line takeaway
Find the kth element in the nth grammar row by recursively narrowing to the first half (same value) or second half (inverted value).
