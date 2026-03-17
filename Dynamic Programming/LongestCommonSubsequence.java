/*
Given two strings A and B. Find the longest common sequence ( A sequence which does not need to be contiguous), which is common in both the strings.

You need to return the length of such longest common subsequence.

Problem Constraints
1 <= |A|, |B| <= 1005

Input Format
First argument is an string A.

Second argument is an string B.

Output Format
Return the length of such longest common subsequence between string A and string B.

Example Input
Input 1:

 A = "abbcdgf"
 B = "bbadcgf"

Example Output
Output 1:

 5

Example Explanation
Explanation 1:

 The longest common subsequence is "bbcgf", which has a length of 5

*/

// T - O(n*m) S - O(n*m)

import java.util.*;

public class Solution {
    int[][] dp;

    public int solve(String A, String B) 
    {
        if (A.length() == 0 || B.length() == 0) return 0;

        dp = new int[A.length()][B.length()];
        for (int[] row : dp) Arrays.fill(row, -1);

        return lcs(A, B, 0, 0);
    }

    int lcs(String A, String B, int i, int j)
    {
        if (i == A.length() || j == B.length()) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        if (A.charAt(i) == B.charAt(j)) {
            return dp[i][j] = 1 + lcs(A, B, i + 1, j + 1);
        }

        return dp[i][j] = Math.max(
            lcs(A, B, i + 1, j),
            lcs(A, B, i, j + 1)
        );
    }
}

class Solution {
    public int longestCommonSubsequence(String A, String B) {
        int n = A.length(), m = B.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                        dp[i - 1][j],
                        dp[i][j - 1]
                    );
                }
            }
        }

        return dp[n][m];
    }
}

// space optimised

class Solution {
    public int longestCommonSubsequence(String A, String B) {
        int n = A.length(), m = B.length();

        int[] prev = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];

            for (int j = 1; j <= m; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
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

