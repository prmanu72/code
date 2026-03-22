# Problem
- Title: Minimum Subset Sum Difference
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/dsa/partition-a-set-into-two-subsets-such-that-the-difference-of-subset-sums-is-minimum/)
- Description:
  Given an array `arr[]` containing non-negative integers, divide it into two sets `set1` and `set2` such that the absolute difference between their sums is minimum.

- Example:
  Input:
  ```text
  arr = [1, 6, 11, 5]
  ```
  Output:
  ```text
  1
  ```
  Explanation:
  `set1 = {1, 5, 6}`, sum = `12`
  `set2 = {11}`, sum = `11`
  Minimum difference = `1`

- Constraints:
  - `1 <= arr.size() * |sum of array elements| <= 10^5`
  - `1 <= arr[i] <= 10^5`

---

# Intuition
This is another variation of **Subset Sum**, which itself comes from **0/1 Knapsack**.

If one subset has sum `s1`, then the other subset has sum:

```text
s2 = totalSum - s1
```

So the difference is:

```text
|s2 - s1| = |totalSum - 2 * s1|
```

That means we do not need to explicitly build both subsets.
We only need to know which subset sums are possible, then choose the achievable `s1` that makes `|totalSum - 2 * s1|` as small as possible.

---

# Approach

## Step 1: Compute total sum
Add all elements to get:

```java
sum = arr[0] + arr[1] + ...
```

## Step 2: Build subset sum DP
Let:

```java
dp[i][j]
```

mean:
- using the first `i` elements
- is it possible to form subset sum `j`?

This is the standard Subset Sum DP table.

## Step 3: Use only valid subset sums
After filling the table, `dp[n][j] == true` means:
- some subset has sum `j`

Then the other subset must have sum:

```java
sum - j
```

So the difference becomes:

```java
(sum - j) - j = sum - 2 * j
```

We only need to check until `sum / 2`, because after that the values are symmetric.

## Why this works
If `j` goes beyond `sum / 2`, then:
- `j` becomes the larger subset sum
- `sum - j` becomes the smaller one

That produces the same difference values we have already seen on the left half.

So we only search the achievable subset sums in:

```text
0 to sum / 2
```

## DP table construction for an example
Take:

```text
arr = [1, 4]
```

Then:

```text
sum = 5
```

We build `dp[i][j]` where:
- `i` = first `i` elements considered
- `j` = target subset sum

So the table size is:

```text
3 x 6
```

because:
- `n + 1 = 2 + 1 = 3`
- `sum + 1 = 5 + 1 = 6`

### Initial table
- `dp[i][0] = true` for all `i`
- `dp[0][j] = false` for all `j > 0`

```text
        j ->   0     1     2     3     4     5
i = 0        true  false false false false false
i = 1        true  false false false false false
i = 2        true  false false false false false
```

### Row 1: use first element `1`
Current element is:

```text
arr[0] = 1
```

- `j = 1`
  we can take `1`, so:
  `dp[1][1] = dp[0][0] || dp[0][1] = true`

- `j = 2, 3, 4, 5`
  not possible, because with only element `1`, we cannot make those sums

Table now:

```text
        j ->   0     1     2     3     4     5
i = 0        true  false false false false false
i = 1        true  true  false false false false
i = 2        true  false false false false false
```

### Row 2: use first two elements `1, 4`
Current element is:

```text
arr[1] = 4
```

- `j = 1`
  cannot take `4`, so carry previous answer:
  `dp[2][1] = dp[1][1] = true`

- `j = 2`
  cannot take `4`, so:
  `dp[2][2] = dp[1][2] = false`

- `j = 3`
  cannot take `4`, so:
  `dp[2][3] = dp[1][3] = false`

- `j = 4`
  we can take `4`, so:
  `dp[2][4] = dp[1][0] || dp[1][4] = true`

- `j = 5`
  we can take `4`, so:
  `dp[2][5] = dp[1][1] || dp[1][5] = true`
  This means subset `{1, 4}` makes sum `5`.

Final table:

```text
        j ->   0     1     2     3     4     5
i = 0        true  false false false false false
i = 1        true  true  false false false false
i = 2        true  true  false false true  true
```

### Reading the answer
Look at the last row:

```text
dp[2][j] = true for j = 0, 1, 4, 5
```

These are the achievable subset sums.

We only need to check until:

```text
sum / 2 = 5 / 2 = 2
```

So only `j = 0, 1, 2` matter.

Among them, achievable sums are:
- `0`
- `1`

Now compute:

```text
difference = sum - 2 * j
```

- for `j = 0`:
  `5 - 2*0 = 5`

- for `j = 1`:
  `5 - 2*1 = 3`

Minimum difference is:

```text
3
```

which matches:

```text
{1} and {4}
```

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

    public int minDifference(int arr[])
    {
        int n = arr.length;
        int sum = 0;
        for (int i : arr) sum += i;

        boolean[][] dp = new boolean[n + 1][sum + 1];

        isSubsetSum(arr, sum, dp);

        int ans = Integer.MAX_VALUE;

        for (int j = 0; j <= sum; j++)
        {
            int s1 = j;
            int s2 = sum - j;

            if (s1 > s2) break;

            if (dp[n][j])
            {
                ans = Math.min(ans, s2 - s1);
            }
        }
        return ans;
    }

    Boolean isSubsetSum(int arr[], int sum, boolean[][] dp)
    {
        int n = arr.length;

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
Minimum Subset Sum Difference is solved by finding all achievable subset sums and choosing the one closest to `totalSum / 2`.
