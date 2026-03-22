# Problem
- Title: 1143. Longest Common Subsequence
- Source link: [LeetCode](https://leetcode.com/problems/longest-common-subsequence)
- Description:
  Given two strings `text1` and `text2`, return the length of their longest common subsequence. If there is no common subsequence, return `0`.

- Example:
  Input: `text1 = "abcde", text2 = "ace"`
  Output: `3`
  Explanation: The longest common subsequence is `"ace"`.

- Constraints:
  - `1 <= text1.length, text2.length <= 1000`
  - `text1` and `text2` consist of lowercase English characters.

---

# Intuition
If the current characters match, they must contribute `1` to the answer and we move both pointers forward. If they do not match, we have two choices: skip one character from `text1` or skip one character from `text2`, and take the better result.

This naturally gives a recursive solution. Since the same `(i, j)` states repeat many times, memoization reduces it to `O(n * m)`. The same recurrence can then be converted into bottom-up DP, and finally optimized to use only two rows.

---

# Approach
1. Define `lcs(i, j)` as the LCS length of suffixes `text1[i...]` and `text2[j...]`.
2. Base case: if either string is exhausted, return `0`.
3. If `text1.charAt(i) == text2.charAt(j)`, then `lcs(i, j) = 1 + lcs(i + 1, j + 1)`.
4. Otherwise, `lcs(i, j) = max(lcs(i + 1, j), lcs(i, j + 1))`.
5. Memoization stores each `(i, j)` result to avoid recomputation.
6. Tabulation builds the same answer iteratively.
7. Space optimization keeps only the previous and current rows because each DP state depends only on the left cell, upper cell, and upper-left cell.

---

# Complexity

- Time complexity:
  - Recursion: `O(2^(n + m))` in the worst case
  - Memoization: `O(n * m)`
  - Tabulation: `O(n * m)`
  - Space optimized DP: `O(n * m)`

- Space complexity:
  - Recursion: `O(n + m)` recursion stack
  - Memoization: `O(n * m)` plus recursion stack
  - Tabulation: `O(n * m)`
  - Space optimized DP: `O(m)`

---

# Code
```java
import java.util.Arrays;

class Solution {

    public int longestCommonSubsequenceRecursion(String text1, String text2) {
        return lcsRecursive(text1, text2, 0, 0);
    }

    // Recursive
    private int lcsRecursive(String text1, String text2, int i, int j) {
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }

        if (text1.charAt(i) == text2.charAt(j)) {
            return 1 + lcsRecursive(text1, text2, i + 1, j + 1);
        }

        return Math.max(
            lcsRecursive(text1, text2, i + 1, j),
            lcsRecursive(text1, text2, i, j + 1)
        );
    }

    // Memoization
    public int longestCommonSubsequenceMemoization(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[][] dp = new int[n][m];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return lcsMemo(text1, text2, 0, 0, dp);
    }

    private int lcsMemo(String text1, String text2, int i, int j, int[][] dp) {
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        if (text1.charAt(i) == text2.charAt(j)) {
            return dp[i][j] = 1 + lcsMemo(text1, text2, i + 1, j + 1, dp);
        }

        return dp[i][j] = Math.max(
            lcsMemo(text1, text2, i + 1, j, dp),
            lcsMemo(text1, text2, i, j + 1, dp)
        );
    }

    // Bottom-up tabulation
    public int longestCommonSubsequenceTabulation(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][m];
    }

    // Space optimized DP
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[] prev = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];

            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }

            prev = curr;
        }

        return prev[m];
    }
}
```

---

# One-line takeaway
Compare characters from both strings, and when they do not match, try skipping one side while caching or tabulating overlapping subproblems.
