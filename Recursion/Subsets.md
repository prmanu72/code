# Intuition
To generate all subsets in lexicographic order:
1. For each element, we have two choices: **include it** or **exclude it**
2. This creates 2^n possible subsets (all combinations)
3. Sort the result by lexicographic order (compare element-by-element)

The key insight: Use backtracking to explore all possibilities, then sort at the end to ensure lexicographic order.

---

# Approach
1. **Sort input array** to ensure elements are in order
2. **solve() - Backtracking function:**
   - Base case: If index reaches array length, add current subset to result
   - Recursive case:
     - Create a new list with current element included
     - Recursively solve with included element
     - Recursively solve with excluded element (original curList)
3. **Sort result** using custom comparator:
   - Compare element-by-element up to min length
   - If one subset is a prefix of another, shorter comes first

**Example: [1, 2, 3]**
```
Generated (unsorted): [[1,2,3], [1,2], [1,3], [1], [2,3], [2], [3], []]
After sorting: [[], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]]
```

---

# Complexity

- Time complexity:
  O(n·2^n)

Generate 2^n subsets + O(2^n log 2^n) for sorting = O(n·2^n + 2^n·n) = O(n·2^n)

- Space complexity:

O(2^n)

Storing all subsets requires O(2^n) space. Recursion depth is O(n).

---

# Code
```java
public class Solution {
    public ArrayList<ArrayList<Integer>> subsets(ArrayList<Integer> A) 
    {
        Collections.sort(A);
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        solve(A, 0, new ArrayList<>(), res);
        
        Collections.sort(res, (a, b) -> {
           int minLen = Math.min(a.size(), b.size());
           
           for(int i = 0; i < minLen; i++) 
           {
               if(a.get(i) != b.get(i)) 
               {
                   return a.get(i) - b.get(i);
               }
           }
    
           return a.size() - b.size();
        });
       
        return res;
    }
    
    void solve(ArrayList<Integer> A, int i, ArrayList<Integer> curList, ArrayList<ArrayList<Integer>> res)
    {
        if(i == A.size())
        {
            res.add(new ArrayList<>(curList));
            return;
        }
        
        // Include current element
        ArrayList<Integer> list = new ArrayList<>(curList);
        list.add(A.get(i));
        solve(A, i + 1, list, res);
        
        // Exclude current element
        solve(A, i + 1, new ArrayList<>(curList), res);

        /*
        OR
        curList.add(A.get(i));
        solve(A, i + 1, curList, res);

        curList.remove(curList.size() - 1);
        solve(A, i + 1, curList, res);
        */
    }
}
```

---

# One-line takeaway
Generate all 2^n subsets using backtracking (include/exclude each element), then sort lexicographically by comparing element-by-element.
