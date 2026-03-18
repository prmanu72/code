# Intuition
To generate all case permutations of a string:
1. For each **letter**, we have 2 choices: **lowercase or uppercase**
2. For each **digit**, we have 1 choice: **keep it as is**
3. This creates 2^k possible combinations (where k = number of letters)

Think of it as exploring both paths at each letter, while skipping digits.

---

# Approach
1. **letterCasePermutation()**: 
   - Call recursive solve function with index 0
   - Returns all generated permutations
2. **solve()**: 
   - Base case: If index reaches string length, add current string to result
   - Recursive case at each character:
     - **If digit:** Append it and continue (only one choice)
     - **If letter:** 
       - **Option 1:** Convert to lowercase, recurse, and backtrack
       - **Option 2:** Convert to uppercase, recurse, and backtrack

**Example: "a1b2"**
```
At 'a':
  Lowercase 'a': "a"
    At '1': "a1"
      At 'b':
        Lowercase 'b': "a1b"
          At '2': "a1b2" ✓
        Uppercase 'B': "a1B"
          At '2': "a1B2" ✓
  Uppercase 'A': "A"
    At '1': "A1"
      At 'b':
        Lowercase 'b': "A1b"
          At '2': "A1b2" ✓
        Uppercase 'B': "A1B"
          At '2': "A1B2" ✓
```

---

# Complexity

- Time complexity:
  O(2^k · n)

where k = number of letters, n = string length. Generate 2^k permutations, each takes O(n) to copy.

- Space complexity:

O(n)

Recursion stack depth is O(n). StringBuilder stores at most O(n) characters.

---

# Code
```java
class Solution {
    public List<String> letterCasePermutation(String s) 
    {
        ArrayList<String> res = new ArrayList<>();
        solve(s, 0, new StringBuilder(), res);
        return res;    
    }
    
    void solve(String s, int idx, StringBuilder cur, ArrayList<String> res)
    {
        if(idx == s.length())
        {
            res.add(cur.toString());
            return;
        }
        
        Character ch = s.charAt(idx);
        
        if(Character.isDigit(ch))
        {
            // Digit: only one choice
            cur.append(ch);
            solve(s, idx + 1, cur, res);
        }
        else
        {
            // Letter: two choices (lowercase and uppercase)
            int len = cur.length();
            
            // Option 1: lowercase
            cur.append(Character.toLowerCase(ch));
            solve(s, idx + 1, cur, res);
            cur.setLength(len);

            // Option 2: uppercase
            cur.append(Character.toUpperCase(ch));
            solve(s, idx + 1, cur, res);
            cur.setLength(len);  // not necessay, but good to have
        }
    }
}
```

---

# Key Insights

1. **Digit handling:** Skip generating alternatives for digits (only one path)
2. **Letter handling:** Two recursive paths (lowercase and uppercase)
3. **Backtracking:** `cur.setLength(len)` efficiently resets StringBuilder to previous state
4. **Binary branching:** Only letters create binary decision trees
5. **Total combinations:** 2^(number of letters) permutations

---

# One-line takeaway
Generate all case permutations by exploring both lowercase and uppercase options for each letter while keeping digits unchanged.
