# Problem
- Title: Coin Change II - number of ways
- Source link: [LeetCode](https://leetcode.com/problems/coin-change-ii/)
- Description:
  Given an integer array `coins` representing coin denominations and an integer `amount`, return the number of combinations that make up that amount.

  You have an infinite supply of each coin.

  If no combination can make the amount, return `0`.

- Example:
  Input:
  ```text
  amount = 5, coins = [1, 2, 5]
  ```
  Output:
  ```text
  4
  ```
  Explanation:
  The valid combinations are:
  - `5`
  - `2 + 2 + 1`
  - `2 + 1 + 1 + 1`
  - `1 + 1 + 1 + 1 + 1`

- Constraints:
  - `1 <= coins.length <= 300`
  - `1 <= coins[i] <= 5000`
  - all values in `coins` are unique
  - `0 <= amount <= 5000`

---

# Intuition
This is an **Unbounded Knapsack** counting problem.

For each coin, we have two choices:
- take the coin
- skip the coin

The difference from `Coin Change I` is:
- there we minimized the number of coins
- here we count the number of valid combinations

The difference from permutation-style counting is:
- order does not matter
- `2 + 1 + 1 + 1` and `1 + 2 + 1 + 1` are the same combination

That is why the DP state is based on:
- first `i` coin types
- target amount `j`

so combinations are counted without reordering duplicates.

---

# Approach

## State meaning
Let:

```java
dp[i][j]
```

mean:
- number of ways to make amount `j`
- using the first `i` types of coins

## Base cases
- `dp[i][0] = 1`
  because there is exactly one way to make amount `0`:
  choose no coins

- `dp[0][j] = 0` for `j > 0`
  because with zero coin types, no positive amount can be formed

## Transition
If current coin value is `coins[i - 1]` and it fits:

```java
dp[i][j] = dp[i][j - coins[i - 1]] + dp[i - 1][j]
```

Meaning:
- `dp[i][j - coins[i - 1]]`
  take the current coin once, and since supply is infinite, we stay in the same row

- `dp[i - 1][j]`
  skip the current coin type completely

If the coin does not fit:

```java
dp[i][j] = dp[i - 1][j]
```

## Why same row for take?
Because coins are unlimited.

If we use one coin of value `coins[i - 1]`, we can still use it again later.

That is why:

```java
dp[i][j - coins[i - 1]]
```

is used, not:

```java
dp[i - 1][j - coins[i - 1]]
```

---

# Complexity

- Time complexity:
  `O(n * amount)`

- Space complexity:
  `O(n * amount)`

---

# Code
```java
class Solution {
    public int change(int amount, int[] coins)
    {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];

        for (int i = 0; i <= n; i++) dp[i][0] = 1;

        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= amount; j++)
            {
                if (j >= coins[i - 1])
                {
                    dp[i][j] = dp[i][j - coins[i - 1]] + dp[i - 1][j];
                }
                else
                {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][amount];
    }
}
```

---

# One-line takeaway
Coin Change II is an unbounded-knapsack counting problem: for each coin, count take plus skip, while staying in the same row when you take it.
