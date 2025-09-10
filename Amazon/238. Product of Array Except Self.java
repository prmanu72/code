class Solution {
    public int[] productExceptSelf(int[] nums) 
    {
        int n = nums.length;
        int[] leftP = new int[n];
        int[] res = new int[n];
        leftP[0] = nums[0];

        for(int i = 1; i < n; i++)
        {
            leftP[i] = leftP[i-1] * nums[i];
        }

        int rightP = 1;
        for(int i = n-1; i >= 1; i--)
        {
            res[i] = rightP*leftP[i-1];
            rightP *= nums[i];
        }
        res[0] = rightP;
        return res;
    }
}