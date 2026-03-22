# Problem
- Title: Count of Subsets With Given Difference
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/partitions-with-given-difference/1)
- Description:
  Given an array `arr[]` and an integer `diff`, count the number of ways to partition the array into two subsets such that the difference between their sums is exactly `diff`.

  A partition means:
  - every element must belong to exactly one subset
  - the union of both subsets must form the original array

- Example:
  Input:
  ```text
  arr = [5, 2, 6, 4], diff = 3
  ```
  Output:
  ```text
  1
  ```
  Explanation:
  The only valid partition is `[6, 4]` and `[5, 2]`.

- Constraints:
  - `1 <= arr.size() <= 50`
  - `0 <= diff <= 50`
  - `0 <= arr[i] <= 6`

---

# Intuition
This problem is a direct variation of **Count of Subsets With Given Sum**.

Suppose:
- subset `S1` has sum `s1`
- subset `S2` has sum `s2`
- total array sum is `sum`

Then:

```text
s1 - s2 = diff
```

Also:

```text
s1 + s2 = sum
```

Add the two equations:

```text
2 * s1 = sum + diff
```

So:

```text
s1 = (sum + diff) / 2
```

That means the problem becomes:

- count how many subsets have sum equal to `(sum + diff) / 2`

So this is not a brand new DP problem.
It is just a reduction to counting subsets with a target sum.

---

# Approach

## Step 1: Compute total sum
Add all elements of the array.

## Step 2: Derive target subset sum
From:

```text
s1 - s2 = diff
s1 + s2 = sum
```

we get:

```text
s1 = (sum + diff) / 2
```

So:

```java
target = (sum + diff) / 2
```

## Step 3: Check if target is valid
If `sum + diff` is odd, then `(sum + diff) / 2` is not an integer.

In that case, no valid partition exists.

So:

```java
if ((sum + diff) % 2 != 0) return 0;
```

## Step 4: Count subsets with this target
Now use the standard counting DP:

```java
dp[i][j]
```

means:
- using the first `i` elements
- how many subsets have sum exactly `j`

Since `0` can appear in the array, we must use the zero-safe initialization:

```java
dp[0][0] = 1
```

and fill from:

```java
j = 0
```

so zero-valued elements are counted correctly.

---

# Why zero needs care here too
If `arr[i - 1] == 0`, then:

```java
dp[i][j] = dp[i - 1][j - 0] + dp[i - 1][j]
         = dp[i - 1][j] + dp[i - 1][j]
```

So every existing valid subset can:
- skip the zero
- take the zero

That doubles the count.

This is why the inner loop must start from `j = 0`.

---

# Complexity

- Time complexity:
  `O(n * target)`

- Space complexity:
  `O(n * target)`

---

# Code
```java
class Solution {
    public int countPartitions(int[] arr, int diff)
    {
        int n = arr.length;

        int sum = 0;
        for (int i : arr) sum += i;

        // s1 - s2 = diff
        // s1 + s2 = sum
        // 2 * s1 = sum + diff
        // s1 = (sum + diff) / 2

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
Count of Subsets With Given Difference reduces to counting subsets with target sum `(sum + diff) / 2`.
