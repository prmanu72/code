/**
152. Maximum Product Subarray
Solved
Medium
Topics
premium lock icon
Companies
Given an integer array nums, find a subarray that has the largest product, and return the product.

The test cases are generated so that the answer will fit in a 32-bit integer.

Note that the product of an array with a single element is the value of that element.

 

Example 1:

Input: nums = [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.
Example 2:

Input: nums = [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 

Constraints:

1 <= nums.length <= 2 * 104
-10 <= nums[i] <= 10
The product of any subarray of nums is guaranteed to fit in a 32-bit integer.

**/

class Solution 
{
    public int maxProduct(int[] a) 
    {
        int n = a.length;
        int p = 1, s = 1; //prefix, suffix
        int ans = Integer.MIN_VALUE;
        
        for(int i = 0; i < n; i ++)
        {
            p *= a[i];
            s *= a[n-1-i];

            ans = Math.max(ans, Math.max(p,s));
            
            if(p == 0) p = 1;
            if(s == 0) s = 1;
        }
        return ans;
    }
}