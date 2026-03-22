# Problem
- Title: 0/1 Knapsack
- Source links:
  - [GeeksforGeeks](https://www.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1)
  - [InterviewBit](https://www.interviewbit.com/problems/0-1-knapsack/)
- Description:
  Given two integer arrays `val` and `wt` of size `N`, where `val[i]` is the value of the `i-th` item and `wt[i]` is its weight, and an integer capacity `C`, find the maximum total value we can carry in the knapsack.

  We cannot break an item.
  For every item, we have only two choices:
  - take it completely
  - skip it

- Example:
  Input:
  ```text
  val = [60, 100, 120]
  wt  = [10, 20, 30]
  C   = 50
  ```
  Output:
  ```text
  220
  ```
  Explanation:
  Take items with weights `20` and `30`, giving value `100 + 120 = 220`.

- Constraints:
  - `1 <= N <= 10^3`
  - `1 <= C <= 10^3`
  - `1 <= val[i], wt[i] <= 10^3`

---

# Intuition
At each item, we only have two choices:
- take it
- skip it

That gives the classic recurrence:
- if the item fits, choose the maximum of:
  - taking it
  - skipping it
- if it does not fit, we must skip it

The state depends on two things:
- which items we are allowed to use
- how much capacity is left

That is why knapsack naturally becomes a 2D DP problem.

---

# Approach 1: Top-Down DP (Recursion + Memoization)

## State meaning
Let:

```java
solve(idx, cap)
```

mean:
- maximum value we can get using items from `0` to `idx`
- with remaining capacity `cap`

So the DP state is:

```java
memo[idx][cap]
```

which stores the answer for that exact subproblem.

## Recurrence
If item `idx` fits:

```java
max(
    val[idx] + solve(idx - 1, cap - wt[idx]),
    solve(idx - 1, cap)
)
```

If it does not fit:

```java
solve(idx - 1, cap)
```

## Base case
If:
- `idx < 0`, there are no items left
- `cap == 0`, there is no capacity left

then the answer is `0`.

## Complexity
- Time complexity:
  `O(N * C)`

- Space complexity:
  `O(N * C)` for the memo table, plus recursion stack

## Code
```java
import java.util.Arrays;

class Solution {
    public int knapsack(int C, int val[], int wt[]) {
        int n = val.length;
        int[][] memo = new int[n][C + 1];

        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return solve(n - 1, C, val, wt, memo);
    }

    int solve(int idx, int cap, int[] val, int[] wt, int[][] memo) {
        if (idx < 0 || cap == 0) {
            return 0;
        }

        if (memo[idx][cap] != -1) {
            return memo[idx][cap];
        }

        if (cap >= wt[idx]) {
            memo[idx][cap] = Math.max(
                val[idx] + solve(idx - 1, cap - wt[idx], val, wt, memo),
                solve(idx - 1, cap, val, wt, memo)
            );
        } else {
            memo[idx][cap] = solve(idx - 1, cap, val, wt, memo);
        }

        return memo[idx][cap];
    }
}
```

---

# Approach 2: Bottom-Up DP (Tabulation)

Your provided code is actually a **bottom-up** solution, not top-down.

Here:

```java
memo[idx][wt]
```

means:
- maximum value we can get using the first `idx` items
- with knapsack capacity exactly `wt`

This is different from the top-down version:
- top-down uses recursion and remaining capacity
- bottom-up fills the table iteratively from smaller states to larger states

## What `memo[idx][wt]` signifies
This is the most important part.

In this table:
- `idx` ranges from `0` to `n`
- `wt` ranges from `0` to `C`

So:

```java
memo[idx][wt]
```

stores:
- the best value possible
- if we are allowed to use only the first `idx` items
- and the knapsack capacity available is `wt`

Examples:
- `memo[0][wt] = 0`
  because with `0` items, we cannot take anything

- `memo[idx][0] = 0`
  because with capacity `0`, we cannot carry anything

- `memo[3][50]`
  means:
  using first `3` items, what is the best value possible with capacity `50`?

## Transition
For item number `idx`, the actual array index is `idx - 1`.

Why?
- because DP rows go from `1` to `n`
- but arrays are `0`-indexed

If current item fits:

```java
memo[idx][wt] = max(
    val[idx - 1] + memo[idx - 1][wt - W[idx - 1]],
    memo[idx - 1][wt]
)
```

Meaning:
- take current item:
  add its value and look at the previous row with reduced capacity
- skip current item:
  carry forward the answer from the previous row

If current item does not fit:

```java
memo[idx][wt] = memo[idx - 1][wt]
```

## Complexity
- Time complexity:
  `O(N * C)`

- Space complexity:
  `O(N * C)`

## Code
```java
class Solution {
    public int knapsack(int C, int val[], int W[])
    {
        int n = val.length;
        int[][] memo = new int[n + 1][C + 1];

        for (int idx = 0; idx <= n; idx++) memo[idx][0] = 0;
        for (int wt = 0; wt <= C; wt++) memo[0][wt] = 0;

        for (int idx = 1; idx <= n; idx++)
        {
            for (int wt = 1; wt <= C; wt++)
            {
                if (wt >= W[idx - 1])
                {
                    memo[idx][wt] = Math.max(
                        val[idx - 1] + memo[idx - 1][wt - W[idx - 1]],
                        memo[idx - 1][wt]
                    );
                }
                else
                {
                    memo[idx][wt] = memo[idx - 1][wt];
                }
            }
        }

        return memo[n][C];
    }
}
```

---

# Approach Summary
Two valid DP interpretations are common for knapsack:

- Top-down:
  `memo[idx][cap]` stores the answer for item index `idx` and remaining capacity `cap`

- Bottom-up:
  `memo[idx][wt]` stores the best answer using first `idx` items and total allowed capacity `wt`

Both represent the same recurrence, just in different execution styles.

---

# One-line takeaway
0/1 Knapsack is a 2D DP problem where each state asks: with some set of items and some remaining capacity, what is the maximum value we can build?
