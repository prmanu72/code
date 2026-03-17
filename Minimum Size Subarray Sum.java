/*
209. Minimum Size Subarray Sum
Solved
Medium
Topics
premium lock icon
Companies
Given an array of positive integers nums and a positive integer target, return the minimal length of a subarray whose sum is greater than or equal to target. If there is no such subarray, return 0 instead.

 

Example 1:

Input: target = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: The subarray [4,3] has the minimal length under the problem constraint.
Example 2:

Input: target = 4, nums = [1,4,4]
Output: 1
Example 3:

Input: target = 11, nums = [1,1,1,1,1,1,1,1]
Output: 0
 

Constraints:

1 <= target <= 109
1 <= nums.length <= 105
1 <= nums[i] <= 104
 
*/

// T-O(n) | Sliding window
class Solution {
    public int minSubArrayLen(int k, int[] a) 
    {
        int ans = Integer.MAX_VALUE; 
        int n = a.length;
        
        int l = 0, r = 0;
        int sum = 0;

        while(r < n)
        {
            sum += a[r]; // calculation

            if(sum < k) r++; 
            else
            {
                while(l < n && sum >= k)
                {
                    ans = Math.min(ans, r-l+1); // calculate ans
                    sum -= a[l];     // slide window
                    l++;
                }

                r++;
            }
        }
        if(ans == Integer.MAX_VALUE) return 0;
        return ans;
    }
}

// T - O(nlogn) | Prefix + Binary Search
/*

Convert array into prefix sum:

prefix[i] = sum of first i elements

For each index r, we want a subarray ending at r with:

prefix[r] - prefix[l] ≥ target
⇒ prefix[l] ≤ prefix[r] - target

Since all numbers are positive,
👉 prefix is sorted (increasing)

So for every r, we can:

Compute required = prefix[r] - target

Use binary search to find the largest l such that prefix[l] ≤ required

This gives the shortest valid subarray ending at r:

length = r - l

*/
import java.util.*;

class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;

        // Step 1: Build prefix sum
        long[] prefix = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
        }

        int ans = Integer.MAX_VALUE;

        // Step 2: Iterate over right pointer
        for (int r = 1; r <= n; r++) {
            long required = prefix[r] - target;

            // Step 3: Binary search
            int l = binarySearch(prefix, 0, r, required);

            if (l != -1) {
                ans = Math.min(ans, r - l);
            }
        }

        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    // Find largest index where prefix[index] <= target
    private int binarySearch(long[] prefix, int left, int right, long target) {
        int res = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (prefix[mid] <= target) {
                res = mid;       // possible answer
                left = mid + 1; // try to go right
            } else {
                right = mid - 1;
            }
        }

        return res;
    }
}

// T - O(nlogn) | prefix + treemap
// use tree map fro finding floorkey instead of bunary search

import java.util.*;

class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        TreeMap<Long, Integer> map = new TreeMap<>();

        long prefix = 0;
        int ans = Integer.MAX_VALUE;

        // Important: handles subarray from index 0
        map.put(0L, -1);

        for (int r = 0; r < nums.length; r++) 
        {
            prefix += nums[r];

            long required = prefix - target;

            // Find largest prefix <= required
            Long key = map.floorKey(required);

            if (key != null) {
                int l = map.get(key);
                ans = Math.min(ans, r - l);
            }

            // Store current prefix
            map.put(prefix, r);
        }

        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}