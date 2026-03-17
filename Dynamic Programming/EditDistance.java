/*
🧠 Intuition (Very Important)

You want to convert:

A → B

Allowed operations:

Insert

Delete

Replace

🔑 Key Idea

At every (i, j):

A[i-1], B[j-1]
✅ Case 1: Characters match
A[i-1] == B[j-1]

👉 No operation needed

dp[i][j] = dp[i-1][j-1]
❌ Case 2: Characters differ

We try all 3 operations:

1. Insert
Insert B[j-1] into A
→ match B[j-1]
→ move j
dp[i][j] = 1 + dp[i][j-1]
2. Delete
Delete A[i-1]
→ move i
dp[i][j] = 1 + dp[i-1][j]
3. Replace
Replace A[i-1] → B[j-1]
dp[i][j] = 1 + dp[i-1][j-1]
🎯 Final Transition
dp[i][j] = 1 + min(
    dp[i-1][j],     // delete
    dp[i][j-1],     // insert
    dp[i-1][j-1]    // replace
)

*/

public class Solution {
    public int minDistance(String A, String B) 
    {
        int n1 = A.length(), n2 = B.length();
        int[][] dp = new int[n1+1][n2+1];
        
        for(int i = 0; i <= n1; i++) dp[i][0] = i;
        for(int j = 0; j <= n2; j++) dp[0][j] = j;
        
        for(int i = 1; i <= n1; i++)
        {
            for(int j = 1; j <= n2; j++)
            {
                if(A.charAt(i-1) == B.charAt(j-1))
                {
                    dp[i][j] = dp[i-1][j-1];
                }
                else
                {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i-1][j], dp[i][j-1]),
                                        dp[i-1][j-1]);
                }
            }
        }
        
        
        return dp[n1][n2];
    }
}
