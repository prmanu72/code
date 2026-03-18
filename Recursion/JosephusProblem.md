# Intuition
The Josephus problem involves n people in a circle, where every kth person is eliminated until one remains. We need to find the position of the last remaining person.

Two approaches:
1. **Simulation**: Use a list to simulate the elimination process recursively
2. **Mathematical Formula**: Use the recurrence relation f(n,k) = (f(n-1,k) + k) % n

---

# Approach
### Approach 1: Recursive Simulation
1. Create a list with numbers 1 to n
2. Recursively eliminate every kth person by calculating the index
3. Continue until one person remains

### Approach 2: Mathematical Formula
1. Use iterative formula: Start with 0, for each i from 2 to n: result = (result + k) % i
2. Convert 0-based result to 1-based

---

# Complexity

### Approach 1: Simulation
- Time complexity: O(n²) - Each removal takes O(n) time
- Space complexity: O(n) - List storage

### Approach 2: Formula
- Time complexity: O(n) - Single loop
- Space complexity: O(1) - Constant space

---

# Code

### Approach 1: Recursive Simulation
```java
class Solution {
    int ans;
    public int findTheWinner(int n, int k) {
        ans = -1;
        List<Integer> a = new ArrayList<>();
        for(int i = 1; i <= n; i++)
            a.add(i);

        solve(n, k, 0, a);
        return ans;    
    }

    void solve(int n, int k, int i, List<Integer> a) {
        if(a.size() == 1) {
            ans = a.get(0);
            return;
        }

        int idx = (i + k - 1) % a.size();
        a.remove(idx);

        solve(n, k, idx, a);
    }
}
```

### Approach 2: Mathematical Formula (Optimized)
```java
class Solution {
    public int findTheWinner(int n, int k) {
        int result = 0;  // 0-based indexing
        
        for(int i = 2; i <= n; i++) {
            result = (result + k) % i;
        }
        
        return result + 1;  // Convert to 1-based indexing
    }
}
```

---

# One-line takeaway
Josephus problem: Simulate elimination or use formula f(n,k) = (f(n-1,k) + k) % n to find the last remaining person efficiently.
