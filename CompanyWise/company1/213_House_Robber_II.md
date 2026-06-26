# Problem
- Title: 213. House Robber II
- Source link: [LeetCode](https://leetcode.com/problems/house-robber-ii/)
- Description:
  You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed.
  All houses are arranged in a circle, so the first house is adjacent to the last house.
  Adjacent houses cannot both be robbed on the same night.
  Return the maximum amount of money you can rob without alerting the police.

- Example:
  Input: `nums = [1,2,3,1]`
  Output: `4`
  Explanation:
  Rob house `0` and house `2`, for a total of `1 + 3 = 4`.

- Constraints:
  - `1 <= nums.length <= 100`
  - `0 <= nums[i] <= 1000`

---

# Intuition
The circular condition only creates one extra restriction compared to the normal House Robber problem: the first and last houses cannot both be chosen.

So every valid answer must belong to one of these cases:
- include house `0`, which forces us to solve only on `[2 ... n - 2]`
- exclude house `0`, which lets us solve on `[1 ... n - 1]`

Then the answer is the maximum of those two cases.

---

# Approach
## Your solution
1. Use recursion with memoization on a subarray `[l ... r]`.
2. At each index `l`, either:
   - rob that house and move to `l + 2`
   - skip that house and move to `l + 1`
3. For the circle, evaluate:
   - `nums[0] + solve(2, n - 2, nums)`
   - `solve(1, n - 1, nums)`
4. Return the maximum of the two.

This directly models the circular split and uses memoization to avoid recomputing overlapping states.

## Optimized solution using 2 DP arrays
1. Keep the same circular split:
   - first branch solves `[2 ... n - 2]`
   - second branch solves `[1 ... n - 1]`
2. Since the right boundary is fixed inside each run, we only need a 1D DP array for each branch.
3. Let `dp[i]` store the best answer from index `i` to the end of that branch.
4. Fill from right to left using:
   - `dp[i] = max(nums[i] + dp[i + 2], dp[i + 1])`
5. Use separate arrays for the two branches because they represent different valid ranges.

---

# Complexity

- Time complexity:
  `O(n)`

Each branch processes the houses once, so the total runtime is linear.

- Space complexity:
  `O(n)`

The optimized version uses two 1D DP arrays. The recursive memoized version also uses extra memo space and recursion stack.

---

# Code
## Your solution
```java
import java.util.Arrays;

class Solution {
    int[][] memo;

    public int rob(int[] nums) {
        int n = nums.length;
        memo = new int[n + 1][n + 1];

        for (int[] row : memo) Arrays.fill(row, -1);

        return Math.max(nums[0] + solve(2, n - 2, nums), solve(1, n - 1, nums));
    }

    int solve(int l, int r, int[] nums) {
        if (l > r) return 0;
        if (memo[l][r] != -1) return memo[l][r];

        memo[l][r] = Math.max(nums[l] + solve(l + 2, r, nums), solve(l + 1, r, nums));
        return memo[l][r];
    }
}
```

## Optimized solution using 2 DP arrays
```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;

        int[] dp1 = new int[n + 2];
        int[] dp2 = new int[n + 2];

        for (int i = n - 2; i >= 2; i--) {
            dp1[i] = Math.max(nums[i] + dp1[i + 2], dp1[i + 1]);
        }

        for (int i = n - 1; i >= 1; i--) {
            dp2[i] = Math.max(nums[i] + dp2[i + 2], dp2[i + 1]);
        }

        return Math.max(nums[0] + dp1[2], dp2[1]);
    }
}
```

---

# One-line takeaway
Break the circle into two linear robbery cases, then solve each case with standard House Robber DP and take the maximum.
