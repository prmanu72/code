# Problem
- Title: 516. Longest Palindromic Subsequence
- Source link: [LeetCode](https://leetcode.com/problems/longest-palindromic-subsequence/)
- Description:
  Given a string `s`, return the length of the longest palindromic subsequence in `s`.

- Example:
  Input: `s = "bbbab"`
  Output: `4`
  Explanation: One possible longest palindromic subsequence is `"bbbb"`.

- Constraints:
  - `1 <= s.length <= 1000`
  - `s` consists only of lowercase English letters.

---

# Intuition
A palindrome reads the same forward and backward.

So if we reverse the string, the longest palindromic subsequence in the original string becomes the longest common subsequence between:
- the original string
- the reversed string

That reduces this problem directly to the standard LCS problem.

---

# Approach
1. Create the reversed version of the string.
2. Build an LCS table between `s` and `reverse(s)`.
3. If characters match, extend the subsequence by `1`.
4. Otherwise take the maximum from the top or left cell.
5. The value at `dp[n][n]` gives the length of the longest palindromic subsequence.

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
    public int longestPalindromeSubseq(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == rev.charAt(j - 1)) {
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
Longest palindromic subsequence can be solved as the LCS of the string and its reverse.
