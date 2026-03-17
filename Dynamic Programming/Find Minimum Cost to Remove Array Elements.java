/*
3469. Find Minimum Cost to Remove Array Elements
Solved
Medium
Topics
premium lock icon
Companies
Hint
You are given an integer array nums. Your task is to remove all elements from the array by performing one of the following operations at each step until nums is empty:

Choose any two elements from the first three elements of nums and remove them. The cost of this operation is the maximum of the two elements removed.
If fewer than three elements remain in nums, remove all the remaining elements in a single operation. The cost of this operation is the maximum of the remaining elements.
Return the minimum cost required to remove all the elements.

 

Example 1:

Input: nums = [6,2,8,4]

Output: 12

Explanation:

Initially, nums = [6, 2, 8, 4].

In the first operation, remove nums[0] = 6 and nums[2] = 8 with a cost of max(6, 8) = 8. Now, nums = [2, 4].
In the second operation, remove the remaining elements with a cost of max(2, 4) = 4.
The cost to remove all elements is 8 + 4 = 12. This is the minimum cost to remove all elements in nums. Hence, the output is 12.

Example 2:

Input: nums = [2,1,3,3]

Output: 5

Explanation:

Initially, nums = [2, 1, 3, 3].

In the first operation, remove nums[0] = 2 and nums[1] = 1 with a cost of max(2, 1) = 2. Now, nums = [3, 3].
In the second operation remove the remaining elements with a cost of max(3, 3) = 3.
The cost to remove all elements is 2 + 3 = 5. This is the minimum cost to remove all elements in nums. Hence, the output is 5.

 

Constraints:

1 <= nums.length <= 1000
1 <= nums[i] <= 106
*/

/*Intuition

At every step, you are allowed to pick any 2 elements from the first 3 and remove them, paying a cost equal to their maximum.

🔑 Core Observation

From any 3 elements:

[a, b, c]

You:

Remove 2 elements

Keep 1 element (this becomes the carry for the next step)

So the real decision is:

❗ Which element should we keep for future?

⚖️ Why Greedy Fails

Removing the smallest pair is not always optimal

Removing the largest early might reduce future costs

Decisions have future impact due to carry

👉 So:

Local optimal ≠ Global optimal
💡 DP Insight

We define a state:

dp(idx, carry)

Meaning:

idx → next index to process

carry → leftover element from previous step

🔄 Transition Logic

At each step, we have:

carry = a
nums[idx] = b
nums[idx+1] = c

Three choices:

Remove (b, c), keep a

Remove (a, c), keep b

Remove (a, b), keep c

Each choice:

Adds cost = max(removed pair)

Passes new carry forward

🧱 Base Cases

If no elements left → return carry

If one element left → return max(carry, nums[idx])

🚀 Why Memoization

Same (idx, carry) state repeats

Without memo → exponential

With memo → polynomial

🎯 Final Idea in One Line

At every step, try all ways of removing 2 out of 3 elements, carry forward the remaining one, and use DP to minimize total cost.
*/
class Solution {

    static class Pair 
    {
        int a,b;

        Pair(int a,int b){
            this.a=a;
            this.b=b;
        }

        public boolean equals(Object o){
            Pair k=(Pair)o;
            return a==k.a && b==k.b;
        }

        public int hashCode(){
            return Objects.hash(a,b);
        }
    }

    Map<Pair, Integer> memo = new HashMap<>();

    public int minCost(int[] nums) 
    {
        this.memo = new HashMap<>();
        Map<Pair, Integer> memo = new HashMap<>();
        return dp(nums, nums.length, 1, nums[0]);
    }

    int dp(int[] nums, int n, int idx, int carry)
    {
        Pair pair = new Pair(idx, carry);
        if(this.memo.containsKey(pair))
         return this.memo.get(pair);
        
        if(idx >= n) return carry;
        if(idx == n - 1) return Math.max(carry, nums[idx]);
        int a = carry;
        int b = nums[idx];
        int c = nums[idx+1];

        int t1 = Math.max(b, c) + dp(nums, n, idx+2, a);
        int t2 = Math.max(c,a) + dp(nums, n, idx+2, b);
        int t3 = Math.max(a, b) + dp(nums, n, idx+2, c);

        int ans = Math.min(t1, Math.min(t2, t3));
        
        this.memo.put(pair, ans);

        return ans;
    }
}