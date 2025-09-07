class Solution {
    public int findMin(int[] nums) 
    {
        int l = 0, r = nums.length-1;
        Integer ans = Integer.MAX_VALUE;
        int m;
        while(l <= r)
        {
            if(nums[l] <= nums[r])
            {
                ans = Math.min(ans, nums[l]);
                return ans;
            }

            m = (l+r)/2;

            ans = Math.min(ans, nums[m]);
            if(nums[l] <= nums[m])
                l = m+1;
            else
                r = m-1;

        }    
        return ans;
    }
}
