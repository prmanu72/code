# Problem
- Title: Target Sum
- Source link: [LeetCode](https://leetcode.com/problems/target-sum/)
- Description:
  Given an integer array `nums` and an integer `target`, assign either `'+'` or `'-'` before every element and count how many different expressions evaluate to `target`.

- Example:
  Input:
  ```text
  nums = [1, 1, 1, 1, 1], target = 3
  ```
  Output:
  ```text
  5
  ```
  Explanation:
  There are 5 ways to assign signs so that the final expression evaluates to `3`.

- Constraints:
  - `1 <= nums.length <= 20`
  - `0 <= nums[i] <= 1000`
  - `0 <= sum(nums[i]) <= 1000`
  - `-1000 <= target <= 1000`

---

# Intuition
This problem is another variation of **Count of Subsets With Given Difference**.

Suppose:
- numbers assigned `'+'` go into subset `S1`
- numbers assigned `'-'` go into subset `S2`

Then the expression value becomes:

```text
sum(S1) - sum(S2) = target
```

Also:

```text
sum(S1) + sum(S2) = totalSum
```

Adding both equations:

```text
2 * sum(S1) = totalSum + target
```

So:

```text
sum(S1) = (totalSum + target) / 2
```

That means:
- Count of subsets with given difference.
- count the number of subsets whose sum is `(totalSum + target) / 2`

So this is a direct reduction to counting subsets with a given sum.

---

# Approach

## Step 1: Convert sign assignment into subset partition
Each number must receive either:
- `'+'`
- `'-'`

So the array is effectively partitioned into two groups:
- positive-sign group
- negative-sign group

Their difference must equal `target`.

## Step 2: Reduce to subset count
Using:

```text
s1 - s2 = target
s1 + s2 = totalSum
```

we get:

```text
s1 = (totalSum + target) / 2
```

So we only need to count subsets with this target sum.

## Step 3: Handle invalid cases
If:

```text
totalSum + target
```

is odd, then the target subset sum is not an integer, so answer is `0`.

Also, your solution converts negative target to positive:

```java
if (target < 0) target *= -1;
```

That works because the number of ways to make `target` and `-target` is the same:
- flipping all signs converts one valid expression into the other

## Step 4: Use counting subset-sum DP
Let:

```java
dp[i][j]
```

mean:
- using the first `i` elements
- how many subsets have sum exactly `j`

Since `0` can appear in the array, we must use the zero-safe counting version:
- `dp[0][0] = 1`
- fill `j` from `0`

This ensures zero elements correctly double the count when appropriate.

---

# Complexity

- Time complexity:
  `O(n * requiredSum)`

- Space complexity:
  `O(n * requiredSum)`

---

# Code
```java
class Solution {
    public int findTargetSumWays(int[] nums, int target)
    {
        if (target < 0) target *= -1;
        return countOfSubSetWithGivenDiff(nums, target);
    }

    public int countOfSubSetWithGivenDiff(int[] arr, int diff)
    {
        int n = arr.length;

        int sum = 0;
        for (int i : arr) sum += i;

        if ((sum + diff) % 2 != 0) return 0;

        int target = (sum + diff) / 2;

        return countOfSubsetSum(arr, target);
    }

    int countOfSubsetSum(int[] arr, int k)
    {
        int n = arr.length;
        int[][] dp = new int[n + 1][k + 1];

        dp[0][0] = 1;

        for (int j = 1; j <= k; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                if (j >= arr[i - 1]) {
                    dp[i][j] = dp[i - 1][j - arr[i - 1]] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][k];
    }
}
```

---

# One-line takeaway
Target Sum reduces to counting subsets with sum `(totalSum + target) / 2`, so it is the same DP pattern as Count of Subsets With Given Difference.
