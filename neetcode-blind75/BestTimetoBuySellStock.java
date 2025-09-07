//  DP
class Solution {
    public int maxProfit(int[] prices) {
        int ans = 0;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < prices.length; i++)
        {
            ans = Math.max(ans, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        return ans;
    }
}

// 2 pointer
public class Solution {
    public int maxProfit(int[] prices) {
        int l = 0, r = 1;
        int maxP = 0;

        while (r < prices.length) {
            if (prices[l] < prices[r]) {
                int profit = prices[r] - prices[l];
                maxP = Math.max(maxP, profit);
            } else {
                l = r;
            }
            r++;
        }
        return maxP;
    }
}