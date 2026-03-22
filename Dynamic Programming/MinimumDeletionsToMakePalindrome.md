# Problem
- Title: Minimum number of deletions
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/minimum-number-of-deletions4610/1)
- [Leetcode:1312. Minimum Insertion Steps to Make a String Palindrome](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/)
- Description:
  Given a string `s`, remove the minimum number of characters so that the remaining string becomes a palindrome. The relative order of the remaining characters must stay the same.

- Example:
  Input: `s = "aebcbda"`
  Output: `2`
  Explanation: Remove `'e'` and `'d'` to get `"abcba"`.

- Constraints:
  - `1 <= s.size() <= 10^3`

---

# Intuition
To make the string a palindrome with minimum deletions, we want to keep the longest part of the string that is already palindromic as a subsequence.

That is exactly the Longest Palindromic Subsequence (LPS).

So:
- keep the LPS
- delete everything else

If the string length is `n` and the LPS length is `lps`, then:

```text
minimum deletions = n - lps
```

We can find LPS by computing the LCS of:
- the original string
- its reverse

---

# Approach
1. Reverse the string.
2. Build the LCS table between `s` and `reverse(s)`.
3. The value `dp[n][n]` gives the length of the longest palindromic subsequence.
4. Return `n - dp[n][n]`.

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
    public int minDeletions(String s) {
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

        int lps = dp[n][n];
        return n - lps;
    }
}
```

---

# One-line takeaway
Minimum deletions to make a string palindrome equals string length minus the longest palindromic subsequence length.
