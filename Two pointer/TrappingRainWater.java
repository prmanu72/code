/*
42. Trapping Rain Water

Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how
 much water it can trap after raining.

Example 1:
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
Example 2:

Input: height = [4,2,0,3,2,5]
Output: 9 

Constraints:

n == height.length
1 <= n <= 2 * 104
0 <= height[i] <= 105
*/

/*
Intuition

Water at any index = min(leftMax, rightMax) − height[i]

Key Observation
- We don’t need both sides always

The smaller side determines the water level

The bigger side is irrelevant at that moment

🎯 Decision Rule

👉 Compare ends:

If height[left] < height[right]  // do not depend on rightMax, because water contained depends on smaller wall
→ process left
→ water = leftMax − height[left]

Else
→ process right
→ water = rightMax − height[right]

🔥 Why this works

👉 When left is smaller:

Right side is guaranteed taller

So:

min(leftMax, rightMax) = leftMax

👉 Symmetrically for right side

🧩 Mental Model

👉 Always process the weaker (shorter) side
👉 Because that side limits the water

🚀 One-line Memory Trick

“Move the smaller pointer, because it decides the water.”
*/

class Solution {
    public int trap(int[] height) {
        int n = height.length;

        int left = 0, right = n - 1;
        int leftMax = 0, rightMax = 0;
        int water = 0;

        while (left < right) 
        {

            if (height[left] < height[right]) 
            {

                if (height[left] >= leftMax) 
                {
                    leftMax = height[left];
                }
                else 
                {
                    water += leftMax - height[left];
                }

                left++;

            } 
            else 
            {

                if (height[right] >= rightMax) 
                {
                    rightMax = height[right];
                } 
                else 
                {
                    water += rightMax - height[right];
                }

                right--;
            }
        }

        return water;
    }
}