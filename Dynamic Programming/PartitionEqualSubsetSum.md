# Problem
- Title: Partition Equal Subset Sum
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/subset-sum-problem2014/1)
- Description:
  Given an array `arr[]`, determine whether it can be partitioned into two subsets such that the sum of both subsets is equal.

  Each element must belong to exactly one subset.

- Example:
  Input:
  ```text
  arr = [1, 5, 11, 5]
  ```
  Output:
  ```text
  true
  ```
  Explanation:
  One partition is `[1, 5, 5]` and the other is `[11]`.

- Constraints:
  - `1 <= arr.size() <= 100`
  - `1 <= arr[i] <= 200`

---

# Intuition
This problem is a direct variation of **Subset Sum**, which itself is a variation of **0/1 Knapsack**.

If the array can be split into two equal parts, then:

```text
sum(part1) = sum(part2)
```

So:

```text
sum(part1) + sum(part2) = totalSum
```

Since both are equal:

```text
2 * sum(part1) = totalSum
```

Therefore:

```text
sum(part1) = totalSum / 2
```

So the whole problem becomes:

- is there a subset whose sum is `totalSum / 2`?

If yes, the remaining elements automatically form the other half.

---

# Approach

## Step 1: Compute total sum
Add all elements of the array.

## Step 2: Check parity
If `totalSum` is odd, equal partition is impossible.

Why?
Because two equal integers cannot add up to an odd number.

So:

```java
if (totalSum % 2 != 0) return false;
```

## Step 3: Reduce to subset sum
Now the target becomes:

```java
totalSum / 2
```

So we call the standard subset sum DP.

## DP meaning
Let:

```java
dp[i][j]
```

mean:
- using the first `i` elements
- can we form sum `j`?

This is exactly the same DP table used in Subset Sum.

## Transition
For current element `arr[i - 1]`:

If it fits:

```java
dp[i][j] = dp[i - 1][j - arr[i - 1]] || dp[i - 1][j]
```

If it does not fit:

```java
dp[i][j] = dp[i - 1][j]
```

---

# Why this is related to 0/1 Knapsack
The structure is the same:
- for every element, choose take or skip
- each element can be used at most once
- state depends on index and target sum

The only difference is what we store:
- Knapsack stores maximum value
- Subset Sum stores possible/not possible
- Partition Equal Subset Sum uses Subset Sum with target `totalSum / 2`

So this is:

```text
0/1 Knapsack -> Subset Sum -> Partition Equal Subset Sum
```

---

# Complexity

- Time complexity:
  `O(n * target)` where `target = totalSum / 2`

- Space complexity:
  `O(n * target)`

---

# Code
```java
class Solution {
    static boolean equalPartition(int arr[]) {
        int totalSum = 0;

        for (int i : arr) totalSum += i;

        if (totalSum % 2 != 0) return false;

        return isSubsetSum(arr, totalSum / 2);
    }

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
Partition Equal Subset Sum is solved by reducing it to Subset Sum with target `totalSum / 2`.
