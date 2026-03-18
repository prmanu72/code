# Intuition
To generate all possible strings by placing spaces between characters:
1. Start with the first character (no space before it)
2. For each remaining character, decide: **place a space before it or not**
3. This creates 2^(n-1) possible combinations

Think of it as a binary choice at each position: space (1) or no space (0).

---

# Approach
1. **permutation()**: 
   - Initialize `StringBuilder` with first character
   - Call recursive solve function
   - Sort results lexicographically
2. **solve()**: 
   - Base case: If all characters processed, add current string to result
   - Recursive case (for each remaining character):
     - **Option 1:** Add space before character
     - Recursively solve
     - **Backtrack:** Remove space and character
     - **Option 2:** Add character without space
     - Recursively solve
     - **Backtrack:** Remove character

**Example: "ABC"**
```
Start with "A"
At 'B':
  Add space: "A B"
    At 'C':
      Add space: "A B C" ✓
      No space: "A BC" ✓
  No space: "AB"
    At 'C':
      Add space: "AB C" ✓
      No space: "ABC" ✓
```

---

# Complexity

- Time complexity:
  O(n·2^n)

Generate 2^(n-1) combinations + O(2^(n-1)·log(2^(n-1))) for sorting ≈ O(n·2^n)

- Space complexity:

O(n)

Recursion stack depth is O(n). StringBuilder stores at most O(n + n-1) = O(n) characters.

---

# Code
```java
import java.util.*;

class Solution {

    ArrayList<String> permutation(String s) {
        ArrayList<String> res = new ArrayList<>();

        StringBuilder curr = new StringBuilder();
        curr.append(s.charAt(0));

        solve(s, 1, curr, res);

        Collections.sort(res);
        return res;
    }

    void solve(String s, int idx, StringBuilder curr, ArrayList<String> res) {
        if (idx == s.length()) {
            res.add(curr.toString());
            return;
        }

        int len = curr.length();

        // Option 1: add space before character
        curr.append(" ").append(s.charAt(idx));
        solve(s, idx + 1, curr, res);

        // backtrack
        curr.setLength(len);

        // Option 2: no space before character
        curr.append(s.charAt(idx));
        solve(s, idx + 1, curr, res);

        // backtrack
        curr.setLength(len);
    }
}
```

---

# Key Insights

1. **StringBuilder efficiency:** Using `curr.setLength(len)` is more efficient than string concatenation
2. **No space before first character:** Loop starts at index 1
3. **Binary choices:** At each of n-1 positions, 2 choices (space or no space) = 2^(n-1) results
4. **Backtracking:** Reset StringBuilder length to restore state before trying alternative

---

# One-line takeaway
Generate all space-separated string combinations by recursively deciding whether to add a space before each character (2^(n-1) combinations total).
