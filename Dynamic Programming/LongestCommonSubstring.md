# Problem
- Title: Longest Common Substring
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/longest-common-substring1452/1)
- Description:
  Given two strings `s1` and `s2`, find the length of the longest common substring between them.

- Example:
  Input: `s1 = "ABCDGH", s2 = "ACDGHR"`
  Output: `4`
  Explanation: The longest common substring is `"CDGH"`.

- Constraints:
  - `1 <= s1.size(), s2.size() <= 10^3`
  - Both strings may contain upper and lower case alphabets.

---

# Intuition
Unlike longest common subsequence, a substring must be contiguous. That means once characters stop matching, the current substring length becomes `0`.

So for each pair of indices, we only extend the current answer when the characters match. We still need a global maximum because the best substring can end at any position.

---

# Approach
1. Let `common(i, j)` represent the length of the longest common substring ending at `s1[i - 1]` and `s2[j - 1]`.
2. If the current characters match, then `common(i, j) = 1 + common(i - 1, j - 1)`.
3. If they do not match, then `common(i, j) = 0` because the substring must stay contiguous.
4. Track the maximum value seen across all states.
5. The recursive version explores all `(i, j)` pairs and carries the current match length.
6. Memoization stores the length of the common suffix ending at each `(i, j)`.
7. Bottom-up DP fills a table using the same recurrence and updates the answer while iterating.

---

# Complexity

- Time complexity:
  - Recursion: `O(3^(n + m))` in the worst case due to branching
  - Memoization: `O(n * m)`
  - Bottom-up DP: `O(n * m)`

- Space complexity:
  - Recursion: `O(n + m)` recursion stack
  - Memoization: `O(n * m)` plus recursion stack
  - Bottom-up DP: `O(n * m)`

---

# Code
```java
import java.util.Arrays;

class Solution {

    public int longestCommonSubstrRecursive(String s1, String s2) {
        return solveRecursive(s1, s2, s1.length(), s2.length(), 0);
    }

    private int solveRecursive(String s1, String s2, int n, int m, int count) {
        if (n == 0 || m == 0) {
            return count;
        }

        int currentCount = count;
        if (s1.charAt(n - 1) == s2.charAt(m - 1)) {
            currentCount = solveRecursive(s1, s2, n - 1, m - 1, count + 1);
        }

        int skipS1 = solveRecursive(s1, s2, n - 1, m, 0);
        int skipS2 = solveRecursive(s1, s2, n, m - 1, 0);

        return Math.max(currentCount, Math.max(skipS1, skipS2));
    }

    public int longestCommonSubstrMemoization(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        int answer = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                answer = Math.max(answer, commonSuffixMemo(s1, s2, i, j, dp));
            }
        }

        return answer;
    }

    private int commonSuffixMemo(String s1, String s2, int i, int j, int[][] dp) {
        if (i == 0 || j == 0) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return dp[i][j] = 1 + commonSuffixMemo(s1, s2, i - 1, j - 1, dp);
        }

        return dp[i][j] = 0;
    }

    public int longestCommonSubstr(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];
        int answer = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    answer = Math.max(answer, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        return answer;
    }
}
```

---

# One-line takeaway
For longest common substring, matching characters extend the diagonal by `1`, and any mismatch resets the current length to `0`.
