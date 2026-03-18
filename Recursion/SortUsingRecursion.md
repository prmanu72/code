# Intuition
Recursive insertion sort works by:
1. Recursively sorting the array minus the last element
2. Then inserting the last element into its correct position in the sorted portion

This leverages recursion to break down the problem into smaller subproblems.

---

# Approach
1. **sortArr()**: Convert array to List (for easier manipulation), sort it, then copy back to original array.
2. **sort()**: 
   - Base case: If list has 0 or 1 elements, it's already sorted
   - Recursive case: 
     - Remove the last element `t`
     - Recursively sort the remaining elements
     - Insert `t` into its correct position
3. **insert()**: 
   - If list is empty or last element ≤ key, append key
   - Otherwise, remove last element and recursively find correct position
   - Shift elements as we backtrack

---

# Complexity

- Time complexity:
  O(n²)

Insertion sort has quadratic time complexity. For each element, we may need to scan through all previous elements to find the insertion position.

- Space complexity:

O(n)

Due to:
- List conversion: O(n) additional space
- Recursion stack: O(n) in worst case (skewed tree of recursive calls)

---

# Code
```java
class Solution {
    void sortArr(int[] arr) 
    {
        List<Integer> a = new ArrayList<>();
        for(int i : arr) a.add(i);

        sort(a);
        for(int i = 0; i < arr.length; i++) arr[i] = a.get(i);
    }
    
    void sort(List<Integer> a)
    {
        if(a.size() <= 1) return;
        
        int t = a.get(a.size() - 1);
        a.removeLast();
        
        sort(a);
        
        insert(a, t);
    }
    
    void insert(List<Integer> a, int k)
    {
        if(a.size() == 0 || a.get(a.size() - 1) <= k)
        {
            a.add(k);
            return;
        }
        
        int t = a.get(a.size() - 1);
        a.removeLast();
        
        insert(a,k);
        
        a.add(t);
    }
}
```

---

# One-line takeaway
Recursive insertion sort: Sort n-1 elements, then recursively insert the nth element at its correct position in the sorted portion.
