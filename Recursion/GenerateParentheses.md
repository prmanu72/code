# Intuition
To generate all valid parentheses combinations:
1. We need to place n opening "(" and n closing ")" parentheses
2. A valid combination never has more ")" than "(" at any point
3. Use recursion to track remaining left and right parentheses
4. Only add ")" when it doesn't exceed the count of "("

For n=3, we have 5 valid combinations (Catalan number).

---

# Approach
1. **generateParenthesis()**: 
   - Initialize with n left and n right parentheses available
   - Call recursive gen function
2. **gen(l, r, cur, res)**:
   - **Pruning:** If `r < l`, this path is invalid (more rights than lefts), return
   - **Base case:** If both l and r are 0, add current string to result
   - **Recursive case:**
     - If l > 0: Add "(" and recurse
     - If r > 0: Add ")" and recurse

**Example for n=2:**
```
gen(2, 2):
  Add "(": gen(1, 2)
    Add "(": gen(0, 2)
      l==0, append "))" → "(())"  ✓
    Add ")": gen(1, 1)
      Add "(": gen(0, 1)
        append ")" → "()()"  ✓
  Add ")": gen(2, 1)
    r < l → return (invalid)
```

---

# Complexity

- Time complexity:
  O(4^n / √n)

This is the nth Catalan number. We generate all valid combinations without exploring invalid branches.

- Space complexity:

O(n)

Recursion stack depth is O(n) where n = number of pairs. StringBuilder stores at most O(2n) characters.

---

# Code
```java
public class Solution {
    public ArrayList<String> generateParenthesis(int A) 
    {
        ArrayList<String> res = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        
        gen(A, A, cur, res);
        
        return res;
    }
    
    void gen(int l, int r, StringBuilder cur, ArrayList<String> res)
    {
        if(r < l) return;
        if(l == 0 && r == 0)
        {
            res.add(cur.toString());
            return;
        }
        
        int len = cur.length();
        if(l > 0)
        {
            cur.append("(");
            gen(l-1, r, cur, res);
            cur.setLength(len);
        }
        
        if(r > 0)
        {
            cur.append(")");
            gen(l,r-1, cur, res);
            cur.setLength(len);
        }
    }
}
```

---

# Key Insights

1. **Pruning with `if(r < l) return`**: Eliminates invalid states early (more closing than opening at any point)
2. **Simple conditionals**: `if(l > 0)` and `if(r > 0)` make it clear when we can place each type of parenthesis
3. **No unnecessary optimization**: Clean, readable code without special case handling
4. **Binary branching**: At each step, we conditionally choose to place "(" and/or ")"
5. **Catalan numbers**: The count of valid combinations is the nth Catalan number: C(n) = (2n)! / ((n+1)! × n!)

---

# One-line takeaway
Generate valid parentheses by pruning invalid states (r < l) and conditionally placing "(" and ")" based on remaining counts.
