class Solution {
    public List<List<Integer>> threeSum(int[] nums) 
    {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        Arrays.sort(nums);
        for(int i = 0; i < n; i++)
        {
            if(i>0 && nums[i] == nums[i-1])
            {
                continue;
            }

            int l = i + 1, r = n-1;
            while(l < r)
            {
                if(nums[l] + nums[r] + nums[i] == 0)
                {
                   result.add(Arrays.asList(nums[i], nums[l], nums[r]));
                   l++;
                   r--; 
                   while(l<r && nums[l] == nums[l-1]) l++;
                }
                else if(nums[l] + nums[r] + nums[i] < 0)
                {
                    l++;
                }
                else
                {
                    r--;
                }
                
            }
        }
        return result;
    }
}
