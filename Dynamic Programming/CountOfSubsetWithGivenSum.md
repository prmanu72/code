# Problem
- Title: Count of Subsets With Given Sum
- Source links:
  - [GeeksforGeeks - Perfect Sum Problem](https://www.geeksforgeeks.org/problems/perfect-sum-problem5633/1)
- Description:
  Given an array `arr[]` and a target `sum`, count how many subsets of the array have sum exactly equal to `sum`.

---

# Intuition
This is another variation of **Subset Sum** and **0/1 Knapsack**.

The difference is:
- Subset Sum asks: is the target possible?
- This problem asks: how many ways is the target possible?

So instead of storing `true / false`, the DP table stores counts.

Let:

```java
dp[i][j]
```

mean:
- using the first `i` elements
- how many subsets can make sum `j`

The transition stays almost the same as Subset Sum:
- take the current element
- skip the current element

But instead of `OR`, we use `+`.

---

# Variant 1: Zero Is Not Present In The Array

## State meaning

```java
dp[i][j]
```

means:
- number of subsets from the first `i` elements
- whose sum is exactly `j`

## Base cases
- `dp[i][0] = 1`
  because there is exactly one way to make sum `0`:
  take the empty subset

- `dp[0][j] = 0` for `j > 0`
  because with zero elements, no positive sum can be formed

## Transition
If current element `arr[i - 1]` fits:

```java
dp[i][j] = dp[i - 1][j - arr[i - 1]] + dp[i - 1][j]
```

Meaning:
- take current element
- skip current element

If it does not fit:

```java
dp[i][j] = dp[i - 1][j]
```

## Code
```java
class Solution {
    public int countSubsets(int[] arr, int sum) {
        int n = arr.length;
        int[][] dp = new int[n + 1][sum + 1];

        for (int i = 0; i <= n; i++)
            dp[i][0] = 1;

        for (int j = 1; j <= sum; j++)
            dp[0][j] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (j >= arr[i - 1]) {
                    dp[i][j] = dp[i - 1][j - arr[i - 1]] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][sum];
    }
}
```

## Important note
This version works correctly only when the array does **not** contain `0`.

Why?
Because if `0` is present, then for any sum `j`:
- take `0`
- skip `0`

Both give the same sum, so the count must double.

The usual initialization `dp[i][0] = 1` for all rows prevents that doubling from being captured.

---

# Variant 2: Zero Is Included In The Array

This is the version used in the GeeksforGeeks Perfect Sum Problem.

## What changes?
The transition remains the same, but we must allow `j = 0` to be updated dynamically.

That means:
- initialize only:
  ```java
  dp[0][0] = 1
  ```
- and fill the table from:
  ```java
  j = 0
  ```
  not from `j = 1`

## Why this is necessary
Suppose:

```text
arr = [0, 0], sum = 0
```

Subsets with sum `0` are:
- `{}`
- `{first 0}`
- `{second 0}`
- `{first 0, second 0}`

Answer is:

```text
4
```

If we force `dp[i][0] = 1` for every row, we lose this doubling effect.

But if we compute `dp[i][0]` normally:

```java
dp[i][0] = dp[i - 1][0] + dp[i - 1][0]
```

when `arr[i - 1] == 0`, the count doubles correctly.

## Code
```java
class Solution {
    public int perfectSum(int[] arr, int sum) {
        int n = arr.length;
        int[][] dp = new int[n + 1][sum + 1];

        dp[0][0] = 1;

        for (int j = 1; j <= sum; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= sum; j++) {
                if (j >= arr[i - 1]) {
                    dp[i][j] = dp[i - 1][j - arr[i - 1]] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][sum];
    }
}
```

## If modulo is required
Some versions of the problem require:

```java
mod = 1000000007
```

Then update as:

```java
dp[i][j] = (dp[i - 1][j - arr[i - 1]] + dp[i - 1][j]) % mod;
```

---

# Comparison

## When zero is not present
- `dp[i][0] = 1` for all `i`
- inner loop can start from `j = 1`

## When zero is present
- set only `dp[0][0] = 1`
- inner loop must start from `j = 0`
- this lets sum `0` counts grow correctly

---

# Complexity

- Time complexity:
  `O(n * sum)`

- Space complexity:
  `O(n * sum)`

---

# One-line takeaway
Count of Subsets With Given Sum is the counting version of Subset Sum: replace boolean DP with integer DP, and handle zeros carefully because they double the number of valid subsets.
