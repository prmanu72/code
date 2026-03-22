# Problem
- Title: Print Longest Common Subsequence
- Source link: [Levis can be adapted from standard LCS](https://leetcode.com/problems/longest-common-subsequence)
- Description:
  Given two strings `text1` and `text2`, return one actual longest common subsequence string.

- Example:
  Input: `text1 = "abcde", text2 = "ace"`
  Output: `"ace"`
  Explanation: `"ace"` is a longest common subsequence of both strings.

- Constraints:
  - `1 <= text1.length, text2.length <= 1000`
  - Strings can be processed using the standard LCS DP table.

---

# Intuition
The standard LCS table gives the length of the best subsequence for every prefix pair. But that table alone does not directly tell us the subsequence string in scan order.

To print the LCS, first compute the length table, then start from `dp[n][m]` and backtrack:
- If characters match, that character is part of one valid LCS.
- Otherwise move to the neighbor with the larger value.

This reconstructs one consistent path through the table.

---

# Approach
1. Build the normal LCS DP table where `dp[i][j]` stores the LCS length of `text1[0...i-1]` and `text2[0...j-1]`.
2. Start from `(n, m)` and reconstruct the answer.
3. If `text1.charAt(i - 1) == text2.charAt(j - 1)`, append that character and move diagonally to `(i - 1, j - 1)`.
4. Otherwise move to `(i - 1, j)` or `(i, j - 1)` depending on which has the larger DP value.
5. Reverse the built string at the end because backtracking collects characters from back to front.

Why we cannot use a string while building the table:
While filling the DP table, a match at cell `(i, j)` only means that character can contribute to some LCS for that state. It does not mean this character belongs to the final subsequence we will print.

If we append during table construction like this:

```java
if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
    ans.append(text1.charAt(i - 1));
    dp[i][j] = 1 + dp[i - 1][j - 1];
}
```

then we mix characters from many different DP states. Those states may belong to different possible subsequences, so the collected string can become longer than the real LCS or even stop being a valid subsequence. The correct place to append characters is during backtracking, after the full DP table is ready.

---

# Complexity

- Time complexity:
  `O(n * m)`

- Space complexity:
  `O(n * m)`

---

# Code
```java
class Solution {
    public String printLongestCommonSubsequence(String text1, String text2) {
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

        StringBuilder ans = new StringBuilder();
        int i = n;
        int j = m;

        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                ans.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return ans.reverse().toString();
    }
}
```

---

# One-line takeaway
For printing LCS, build the length table first and append characters only during backtracking, not while filling the DP table.
