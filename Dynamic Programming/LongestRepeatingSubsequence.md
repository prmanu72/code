# Problem
- Title: Longest Repeating Subsequence
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/longest-repeating-subsequence2004/1)
- Description:
  Given a string `str`, find the length of the longest repeating subsequence such that the same original index is not used in both occurrences.

- Example:
  Input: `str = "axxzxy"`
  Output: `2`
  Explanation: The longest repeating subsequence is `"xx"`.

- Constraints:
  - `1 <= s.size() <= 10^3`

---

# Intuition
This problem is very close to Longest Common Subsequence.

If a subsequence appears twice in the same string, we can think of it as finding the LCS of the string with itself. But there is one important restriction: we cannot match a character with itself at the same index.

So the idea is:
- compare the string with itself
- if characters match and their indices are different, we can include that character
- otherwise we skip one character from either side, exactly like LCS

That is why this becomes standard LCS with one extra condition: `i != j`.

---

# Approach
1. Let `dp[i][j]` represent the length of the longest repeating subsequence using the first `i` characters of the string on one side and the first `j` characters on the other side.
2. If `str.charAt(i - 1) == str.charAt(j - 1)` and `i != j`, then we can extend the answer:

```text
dp[i][j] = 1 + dp[i - 1][j - 1]
```

3. Otherwise, we skip one character from either side:

```text
dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
```

4. The final answer is `dp[n][n]`.

---

# Complexity

- Time complexity:
  `O(n * n)`

- Space complexity:
  `O(n * n)`

---

# Code
```java
class Solution {
    public int LongestRepeatingSubsequence(String str) {
        int n = str.length();
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (str.charAt(i - 1) == str.charAt(j - 1) && i != j) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][n];
    }
}
```

---

# One-line takeaway
Longest Repeating Subsequence is just LCS of the string with itself, with the added rule that the same index cannot be matched with itself.
