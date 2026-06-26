/*
300. Longest Increasing Subsequence
Link: https://leetcode.com/problems/longest-increasing-subsequence

Given an integer array nums, return the length of the longest strictly increasing
subsequence.

Example 1:
Input: nums = [10,9,2,5,3,7,101,18]
Output: 4

Example 2:
Input: nums = [0,1,0,3,2,3]
Output: 4

Example 3:
Input: nums = [7,7,7,7,7,7,7]
Output: 1
*/

import java.util.Arrays;

class Solution
{

    /*
    Intuition

    At every index, we have two choices:
    1. Take nums[i] in the subsequence, but only if it is greater than the last
       picked element.
    2. Skip nums[i] and move ahead.

    So the state becomes:
    - current index i
    - previous chosen index prevIndex

    This is similar to Longest Common Subsequence:
    - In LCS, state was (i, j)
    - In LIS, state is (i, prevIndex)

    We try both possibilities and take the maximum answer.
    */

    /*
    Recursion
    Time: O(2^n) in the worst case
    Space: O(n) recursion stack
    */
    public int lengthOfLISRecursion(int[] nums) {
        return lisRecursive(nums, 0, -1);
    }

    private int lisRecursive(int[] nums, int i, int prevIndex) {
        if (i == nums.length) {
            return 0;
        }

        int notTake = lisRecursive(nums, i + 1, prevIndex);
        int take = 0;

        if (prevIndex == -1 || nums[i] > nums[prevIndex]) {
            take = 1 + lisRecursive(nums, i + 1, i);
        }

        return Math.max(take, notTake);
    }

    /*
    Memoization
    Time: O(n^2)
    Space: O(n^2) + O(n) recursion stack
    */
    public int lengthOfLISMemoization(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n + 1];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return lisMemo(nums, 0, -1, dp);
    }

    private int lisMemo(int[] nums, int i, int prevIndex, int[][] dp) {
        if (i == nums.length) {
            return 0;
        }

        if (dp[i][prevIndex + 1] != -1) {
            return dp[i][prevIndex + 1];
        }

        int notTake = lisMemo(nums, i + 1, prevIndex, dp);
        int take = 0;

        if (prevIndex == -1 || nums[i] > nums[prevIndex]) {
            take = 1 + lisMemo(nums, i + 1, i, dp);
        }

        return dp[i][prevIndex + 1] = Math.max(take, notTake);
    }

    /*
    Tabulation DP
    Time: O(n^2)
    Space: O(n)
    */
    public int lengthOfLISTabulation(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (nums[prev] < nums[i]) {
                    dp[i] = Math.max(dp[i], 1 + dp[prev]);
                }
            }

            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    /*
    Binary Search + Greedy
    Time: O(n log n)
    Space: O(n)

    This is the method used by default for the LeetCode solution.

    tails[len] stores the minimum possible tail value for an increasing
    subsequence of length len + 1.
    */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] tails = new int[n];
        int size = 0;

        for (int num : nums) {
            int left = 0;
            int right = size;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            tails[left] = num;

            if (left == size) {
                size++;
            }
        }

        return size;
    }
}
