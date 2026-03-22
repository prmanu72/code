# Problem
- Title: 392. Is Subsequence
- Source link: [LeetCode](https://leetcode.com/problems/is-subsequence)
- Description:
  Given two strings `s` and `t`, return `true` if `s` is a subsequence of `t`, otherwise return `false`.

- Example:
  Input: `s = "abc", t = "ahbgdc"`
  Output: `true`
  Explanation: We can delete `'h'`, `'g'`, and `'d'` from `t` to get `"abc"` in order.

- Constraints:
  - `0 <= s.length <= 100`
  - `0 <= t.length <= 10^4`
  - `s` and `t` consist only of lowercase English letters.

---

# Intuition
The most direct way is to scan `t` once and try to match characters of `s` in order. If all characters of `s` are matched, then `s` is a subsequence of `t`.

This can also be viewed through LCS:
- if `LCS(s, t) == s.length()`, then every character of `s` appears in `t` in the same order
- so `s` is a subsequence of `t`

The two-pointer solution is optimal here, while the LCS solution is useful for understanding the subsequence relation.

---

# Approach
1. Two-pointer approach:
   Move through `t` using one pointer and through `s` using another pointer.
   Whenever characters match, advance the pointer in `s`.
   At the end, if all characters of `s` are matched, return `true`.

2. LCS approach:
   Build the standard LCS DP table for `s` and `t`.
   If the LCS length equals `s.length()`, then `s` is a subsequence of `t`.

---

# Complexity

- Time complexity:
  - Two pointers: `O(t.length())`
  - LCS: `O(s.length() * t.length())`

- Space complexity:
  - Two pointers: `O(1)`
  - LCS: `O(s.length() * t.length())`

---

# Code
```java
class Solution {
    //2 pointer
    public boolean isSubsequence(String s, String t) {
        int i = 0;
        int j = 0;

        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }

        return i == s.length();
    }

    // Longest common subsequence
    public boolean isSubsequenceUsingLCS(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][m] == n;
    }
}
```

---

# One-line takeaway
To check if `s` is a subsequence of `t`, the optimal method is two pointers, while LCS gives the same answer more expensively.
