/*
239. Sliding Window Maximum
Solved
Hard
Topics
premium lock icon
Companies
Hint
You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

Return the max sliding window.

 

Example 1:

Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation: 
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
Example 2:

Input: nums = [1], k = 1
Output: [1]
 

Constraints:

1 <= nums.length <= 105
-104 <= nums[i] <= 104
1 <= k <= nums.length
*/
class Solution {
    public int[] maxSlidingWindow(int[] a, int k) 
    {
        Deque<Integer> q = new ArrayDeque<>();
        int n = a.length;

        int[] res = new int[n-k+1];
        int i = 0;

        int l = 0, r = 0;

        while(r < n)
        {
            while(!q.isEmpty() && q.peekLast() < a[r])
                q.pollLast();

            q.offerLast(a[r]);
            if(r-l+1 < k) r++;
            else
            {
                res[i++] = q.peekFirst();

                if(a[l] == q.peekFirst()) q.pollFirst();
                l++;

                r++;
            }
        }
        return res;
    }
}