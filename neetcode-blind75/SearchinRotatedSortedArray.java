class Solution {
    public int search(int[] nums, int target) {
        int ans = -1;
        int l = 0, r=nums.length-1;
        while(l<=r)
        {
            int m =(l+r)/2;
            
            if(nums[m] == target)
            {
                return m;
            }
            if(nums[l] <= nums[m])
            {
                if(nums[m] >= target && nums[l] <= target)
                 r = m-1;
                else
                l = m +1;
            }
            else
            {
                 if(nums[r] >= target && nums[m] <= target)
                    l = m+1;
                else
                  r = m-1;
            }
        }
        return ans;
    }
}

