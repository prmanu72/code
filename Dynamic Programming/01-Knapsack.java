class Solution {
    public int knapsack(int C, int val[], int W[]) {
        int n = val.length;
        int[][] memo = new int[n + 1][C + 1];

        for (int idx = 0; idx <= n; idx++) {
            memo[idx][0] = 0;
        }

        for (int wt = 0; wt <= C; wt++) {
            memo[0][wt] = 0;
        }

        for (int idx = 1; idx <= n; idx++) {
            for (int wt = 1; wt <= C; wt++) {
                if (wt >= W[idx - 1]) {
                    memo[idx][wt] = Math.max(
                        val[idx - 1] + memo[idx - 1][wt - W[idx - 1]],
                        memo[idx - 1][wt]
                    );
                } else {
                    memo[idx][wt] = memo[idx - 1][wt];
                }
            }
        }

        return memo[n][C];
    }
}
