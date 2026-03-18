/*
Problem Description

Given two integer arrays A and B of size N each which represent values and weights associated with N items respectively.

Also given an integer C which represents knapsack capacity.

Find out the maximum value subset of A such that sum of the weights of this subset is smaller than or equal to C.

NOTE:

You cannot break an item, either pick the complete item, or don’t pick it (0-1 property).


Problem Constraints
1 <= N <= 103

1 <= C <= 103

1 <= A[i], B[i] <= 103



Input Format
First argument is an integer array A of size N denoting the values on N items.

Second argument is an integer array B of size N denoting the weights on N items.

Third argument is an integer C denoting the knapsack capacity.



Output Format
Return a single integer denoting the maximum value subset of A such that sum of the weights of this subset is smaller than or equal to C.



Example Input
Input 1:

 A = [60, 100, 120]
 B = [10, 20, 30]
 C = 50
Input 2:

 A = [10, 20, 30, 40]
 B = [12, 13, 15, 19]
 C = 10


Example Output
Output 1:

 220
Output 2:

 0


Example Explanation
Explanation 1:

 Taking items with weight 20 and 30 will give us the maximum value i.e 100 + 120 = 220
Explanation 2:

 Knapsack capacity is 10 but each item has weight greater than 10 so no items can be considered in the knapsack therefore answer is 0.

*/

/*
🧠 Intuition (0/1 Knapsack)
At each item:
→ either take it or skip it
🔑 Key Idea

If you take the item:

value + solve(remaining capacity)

If you skip:

move to next item
⚠️ Important
State depends on:
(index, remaining capacity)

👉 Same index with different capacity → different answers

🎯 Goal
Maximize total value without exceeding capacity
🧠 One-line takeaway
Try take vs skip for every item and use DP on (index, capacity)
*/
public class Solution {
    public int solve(ArrayList<Integer> A, ArrayList<Integer> B, int C) 
    {
        int n = A.size();
        int[][] memo = new int[n][C+1];
        for(int[] arr : memo) Arrays.fill(arr, -1);
        
        return dp(A, B, 0, C, memo);
    }
    
    int dp(ArrayList<Integer> A, ArrayList<Integer> B, int idx, int wt, int[][] memo)
    {
        if(idx == A.size() || wt <= 0) return 0;
        if(memo[idx][wt] != -1) return memo[idx][wt];
        int ans = 0;
        if(idx == A.size() - 1)
        {
            ans = wt >= B.get(idx) ? A.get(idx) : 0;
        }
        else
        {
            if( wt - B.get(idx) >= 0)
                 ans = Math.max(ans, A.get(idx) + dp(A, B, idx + 1, wt - B.get(idx), memo));
            ans = Math.max(
                        ans,
                        dp(A, B, idx + 1, wt, memo)
                    );
        }
        memo[idx][wt] = ans;
        return ans;
    }
}
