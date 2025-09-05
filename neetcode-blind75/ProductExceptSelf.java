//optimal
public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }

        int postfix = 1;
        for (int i = n - 1; i >= 0; i--) {
            res[i] *= postfix;
            postfix *= nums[i];
        }
        return res;
    }
}

class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        if(n <= 1)
        {
            return nums;
        }
        
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = nums[0];
        for(int i=1;i < n; i++)
        {
            left[i] = left[i-1]*nums[i];
        }
        right[n-1] =  nums[n-1];
        for(int i = n-2;i >= 0; i--)
        {
            right[i] = right[i+1]*nums[i];
        }
        int[] res = new int[n];
        res[0] = right[1];
        res[n-1]  = left[n-2];
        for(int i =1; i < n-1;  i++)
        {
            res[i] = left[i-1]*right[i+1];
        }
        return res;
    }
}  
