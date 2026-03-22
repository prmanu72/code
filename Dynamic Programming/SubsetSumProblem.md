# Problem
- Title: Subset Sum Problem
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1)
- Description:
  Given an array of positive integers `arr[]` and a value `sum`, determine whether there exists a subset of `arr[]` whose total is exactly equal to `sum`.

- Example:
  Input:
  ```text
  arr = [3, 34, 4, 12, 5, 2], sum = 9
  ```
  Output:
  ```text
  true
  ```
  Explanation:
  We can choose `4 + 3 + 2 = 9`.

- Constraints:
  - `1 <= arr.size() <= 200`
  - `1 <= arr[i] <= 200`
  - `1 <= sum <= 10^4`

---

# Intuition
This problem is a direct variation of **0/1 Knapsack**.

In 0/1 Knapsack:
- each item can be taken at most once
- we choose whether to take or skip an item
- we maximize total value under a capacity limit

In Subset Sum:
- each number can also be taken at most once
- we again choose whether to take or skip it
- but instead of maximizing value, we only ask:
  can we make the target sum or not?

So this is the boolean version of 0/1 Knapsack.

---

# Approach

## State meaning
Let:

```java
dp[i][j]
```

mean:
- using the first `i` elements
- is it possible to form sum `j`?

So the table stores `true` or `false`, not maximum value.

## Base cases
- `dp[i][0] = true`
  because sum `0` is always possible by taking an empty subset

- `dp[0][j] = false` for `j > 0`
  because with `0` elements, we cannot form any positive sum

## Transition
For the current element `arr[i - 1]`:

If it fits into the current target `j`, then we have two choices:
- take it:
  `dp[i - 1][j - arr[i - 1]]`
- skip it:
  `dp[i - 1][j]`

So:

```java
dp[i][j] = dp[i - 1][j - arr[i - 1]] || dp[i - 1][j]
```

If it does not fit:

```java
dp[i][j] = dp[i - 1][j]
```

## Why this is a variation of 0/1 Knapsack
The structure is exactly the same:
- same "take or skip" choice
- same `i` and `j` DP table
- same transition shape

The only difference is what the table stores:
- Knapsack stores the **best value**
- Subset Sum stores **whether a sum is possible**

---

# Complexity

- Time complexity:
  `O(n * sum)`

- Space complexity:
  `O(n * sum)`

---

# Code
```java
class Solution {

    static Boolean isSubsetSum(int arr[], int sum) {
        int n = arr.length;
        boolean[][] dp = new boolean[n + 1][sum + 1];

        for (int i = 0; i <= n; i++)
            dp[i][0] = true;

        for (int j = 1; j <= sum; j++)
            dp[0][j] = false;

        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= sum; j++)
            {
                if (j >= arr[i - 1])
                {
                    dp[i][j] = dp[i - 1][j - arr[i - 1]] || dp[i - 1][j];
                }
                else
                {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][sum];
    }
}
```

---

# One-line takeaway
Subset Sum is the boolean form of 0/1 Knapsack: instead of maximizing value, we only check whether the target sum can be formed.
