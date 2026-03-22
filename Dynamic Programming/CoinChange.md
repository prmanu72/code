# Problem
- Title: Coin Change - Min number of coins
- Source link: [LeetCode](https://leetcode.com/problems/coin-change/)
- Description:
  Given an integer array `coins` representing coin denominations and an integer `amount`, return the fewest number of coins needed to make up that amount.

  If the amount cannot be formed, return `-1`.

  You have an infinite supply of each coin.

- Example:
  Input:
  ```text
  coins = [1, 2, 5], amount = 11
  ```
  Output:
  ```text
  3
  ```
  Explanation:
  `11 = 5 + 5 + 1`

- Constraints:
  - `1 <= coins.length <= 12`
  - `1 <= coins[i] <= 2^31 - 1`
  - `0 <= amount <= 10^4`

---

# Intuition
This is an **Unbounded Knapsack** minimization problem.

For each coin, we have two choices:
- take the coin
- skip the coin

The important difference from `Coin Change II` is:
- there we counted the number of combinations
- here we want the minimum number of coins

So instead of:
- boolean DP
- counting DP

we now use:
- minimum-value DP

---

# Approach

## State meaning
Let:

```java
dp[i][j]
```

mean:
- minimum number of coins needed to make amount `j`
- using the first `i` coin types

## Base cases
- `dp[i][0] = 0`
  because amount `0` needs `0` coins

- `dp[0][j] = INF` for `j > 0`
  because with zero coin types, no positive amount can be formed

## Why use `Integer.MAX_VALUE - 1`
We need a large value to represent:

```text
impossible
```

But we cannot safely use:

```java
Integer.MAX_VALUE
```

because the transition does:

```java
1 + dp[i][j - coins[i - 1]]
```

If that value were `Integer.MAX_VALUE`, adding `1` would overflow.

So we use:

```java
Integer.MAX_VALUE - 1
```

This still behaves like infinity for `Math.min(...)`, but avoids overflow when `1` is added.

## Transition
If current coin fits:

```java
dp[i][j] = Math.min(1 + dp[i][j - coins[i - 1]], dp[i - 1][j]);
```

Meaning:
- take current coin:
  use one coin, then solve the remaining amount with the same row because coins are unlimited

- skip current coin:
  move to the previous row

If coin does not fit:

```java
dp[i][j] = dp[i - 1][j];
```

## Why same row when taking?
Because coins can be reused unlimited times.

So after taking one coin of value `coins[i - 1]`, we are still allowed to take it again.

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
    public int coinChange(int[] coins, int amount)
    {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];

        for (int j = 1; j <= amount; j++) dp[0][j] = Integer.MAX_VALUE - 1;

        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= amount; j++)
            {
                if (j >= coins[i - 1])
                {
                    dp[i][j] = Math.min(1 + dp[i][j - coins[i - 1]], dp[i - 1][j]);
                }
                else
                {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][amount] == Integer.MAX_VALUE - 1 ? -1 : dp[n][amount];
    }
}
```

---

# One-line takeaway
Coin Change is an unbounded-knapsack minimization problem: for each coin, take it or skip it, and use a large sentinel value to represent impossible states safely.
