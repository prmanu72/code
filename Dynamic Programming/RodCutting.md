# Problem
- Title: Rod Cutting
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/rod-cutting0840/1)
- Description:
  Given a rod of length `n` and an array `price[]`, where `price[i]` denotes the value of a piece of length `i + 1`, determine the maximum value obtainable by cutting the rod and selling the pieces.

  The rod may be cut into any number of pieces, as long as the total length used is exactly `n`.

- Example:
  Input:
  ```text
  price = [1, 5, 8, 9, 10, 17, 17, 20]
  ```
  Output:
  ```text
  22
  ```
  Explanation:
  Cut the rod into lengths `2` and `6`.
  Profit = `5 + 17 = 22`.

- Constraints:
  - `1 <= price.size() <= 10^3`
  - `1 <= price[i] <= 10^6`

---

# Intuition
This problem looks different from knapsack at first, but the core decision is the same:

- either use a piece of a certain length
- or skip that piece length and try smaller choices

The key difference from **0/1 Knapsack** is:
- in `0/1 Knapsack`, each item can be taken at most once
- in **Rod Cutting**, a piece length can be used multiple times

For example, if rod length is `8`, we are allowed to use:
- length `2` four times
- length `1` eight times
- length `3` two times and length `2` once
- any valid combination

So this is actually an **Unbounded Knapsack** problem.

## Why unbounded?
Suppose we are considering a cut of length `2`.

If we use one piece of length `2`, we still may use another piece of length `2` again later.

That is exactly why the transition uses:

```java
dp[i][j - i]
```

instead of:

```java
dp[i - 1][j - i]
```

We stay in the same row because the same cut length is still available.

---

# Approach

## Step 1: Define available piece lengths
If `price.length = n`, then the possible piece lengths are:

```text
1, 2, 3, ..., n
```

and:

```text
price[i - 1]
```

is the profit of choosing a piece of length `i`.

## Step 2: Define DP state
Let:

```java
dp[i][j]
```

mean:
- maximum profit we can make
- using piece lengths from `1` to `i`
- to build/cut a rod of total length `j`

- i = current piece length being considered
- j = current rod length we want to cut

So:
- rows represent available cut lengths
- columns represent current rod length to be formed

## Step 3: Transition
At state `dp[i][j]`, we have two choices.

### Choice 1: take a piece of length `i`
This is possible only if:

```java
j >= i
```

If we take it:
- we gain `price[i - 1]`
- remaining rod length becomes `j - i`
- and since this is unbounded, we may still take length `i` again

So:

```java
price[i - 1] + dp[i][j - i]
```

### Choice 2: skip this piece length
Then we move to smaller lengths:

```java
dp[i - 1][j]
```

### Final recurrence
If `j >= i`:

```java
dp[i][j] = Math.max(price[i - 1] + dp[i][j - i], dp[i - 1][j]);
```

Otherwise:

```java
dp[i][j] = dp[i - 1][j];
```

---

# DP Table Meaning With Example
Take:

```text
price = [1, 5, 8, 9]
```

This means:
- length `1` -> profit `1`
- length `2` -> profit `5`
- length `3` -> profit `8`
- length `4` -> profit `9`

We want the best profit for rod length `4`.

### `dp[1][4]`
Using only length `1`, the only way is:

```text
1 + 1 + 1 + 1
```

profit:

```text
4
```

### `dp[2][4]`
Now length `2` is also allowed.

We can do:
- four pieces of length `1` -> profit `4`
- two pieces of length `2` -> profit `10`
- one piece of length `2` and two pieces of length `1` -> profit `7`

Best is:

```text
10
```

So `dp[2][4] = 10`.

This shows why staying in the same row matters:

```java
price[1] + dp[2][2]
```

Then `dp[2][2]` can again use length `2`.

---

# Complexity

- Time complexity:
  `O(n^2)`

- Space complexity:
  `O(n^2)`

---

# Code
```java
class Solution {
    public int cutRod(int[] price)
    {
        int n = price.length;
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= n; j++)
            {
                if (j >= i) // current piece of length should not be greater than current rod length
                    dp[i][j] = Math.max(price[i - 1] + dp[i][j - i], dp[i - 1][j]);
                else
                    dp[i][j] = dp[i - 1][j];
            }
        }

        return dp[n][n];
    }
}
```

---

# One-line takeaway
Rod Cutting is an Unbounded Knapsack problem: for each cut length, either use it again or skip it, and maximize total profit.
