public class Solution {
    public boolean isPalindrome(String s) {
        StringBuilder newStr = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                newStr.append(Character.toLowerCase(c));
            }
        }
        return newStr.toString().equals(newStr.reverse().toString());
    }
}

class Solution 
{
    public int longestConsecutive(int[] nums) 
    {
        int n = nums.length;
        if(n <2)
        {
            return n;
        }
        Set<Integer> set = new HashSet<>();
        for(int i :nums)
        {
            set.add(i);
        }
        int ans = 1;
        for(int i=0;i<n; i++)
        {
            if(!set.contains(nums[i]-1))
            {
                int count = 0,k =nums[i];
                while(set.contains(k))
                {
                    k++;
                    count++;
                }
                ans = Math.max(ans,count);
            }
        }
        return ans;
    }
}
