class Solution {
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int currSum = 0;
        for(int i: nums)
        {
            if(currSum < 0) currSum = 0;
            currSum += i;
            ans = Math.max(ans, currSum);
        }
        return ans;
    }
}