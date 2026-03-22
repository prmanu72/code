# Problem
- Title: Minimum number of deletions and insertions
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/minimum-number-of-deletions-and-insertions0209/1)
- Description:
  Given two strings `s1` and `s2`, find the minimum number of deletions from and insertions into `s1` required to transform it into `s2`.

- Example:
  Input: `s1 = "heap", s2 = "pea"`
  Output: `3`
  Explanation: Delete `'h'` and `'p'` from `"heap"`, then insert `'p'` at the beginning.

- Constraints:
  - `1 <= s1.size(), s2.size() <= 1000`
  - All characters are lowercase English alphabets.

---

# Intuition
This problem allows only two operations:
- delete a character from `s1`
- insert a character into `s1`

There are two standard ways to solve it:

1. LCS-based approach:
   Keep the longest common subsequence unchanged. Delete everything else from `s1`, and insert the missing characters needed to build `s2`.

2. Direct DP approach:
   Let `dp[i][j]` be the minimum operations needed to convert `s1[0...i-1]` into `s2[0...j-1]` using only insert and delete.

---

# Approach
1. Compute the LCS length of `s1` and `s2`.
2. Characters not in the LCS must be deleted from `s1`.
3. Characters missing from the LCS must be inserted to form `s2`.
4. So the answer is:

```text
(s1.length() - lcs) + (s2.length() - lcs)
```

Another valid DP directly models the transformation:
- If characters match, no new operation is needed.
- Otherwise either delete from `s1` or insert into `s1`.

Why this is different from Edit Distance:
- This problem allows only insert and delete.
- Edit Distance allows insert, delete, and replace.
- Because replace is not allowed here, a mismatch may need 2 operations here but only 1 in Edit Distance.

Example:

```text
s1 = "cat", s2 = "cut"
```

- In this problem: delete `'a'`, insert `'u'` -> `2`
- In Edit Distance: replace `'a'` with `'u'` -> `1`

---

# Complexity

- Time complexity:
  - LCS approach: `O(n * m)`
  - Direct DP approach: `O(n * m)`

- Space complexity:
  - LCS approach: `O(n * m)`
  - Direct DP approach: `O(n * m)`

---

# Code
```java
class Solution {

    // Solution using LCS
    public int minOperationsUsingLCS(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int lcs = dp[n][m];
        return (n - lcs) + (m - lcs);
    }

    // Solution without LCS
    public int minOperations(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int j = 1; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][m];
    }
}
```

---

# One-line takeaway
If only insert and delete are allowed, either preserve the LCS and count the rest, or use direct DP without the replace transition used in Edit Distance.
