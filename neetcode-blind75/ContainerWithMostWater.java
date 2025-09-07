class Solution {
    public int maxArea(int[] heights) 
    {
        int l = 0;
        int r = heights.length-1;
        int ans = 0;
        while(l<r)
        {
            int curr = (r-l)*Math.min(heights[l], heights[r]);
            ans = Math.max(ans, curr);
            if(heights[l] <= heights[r])
            {
                l++;
            }
            else 
            r--;
           
        } 
        return ans;   
    }
}
