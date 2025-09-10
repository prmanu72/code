class Solution {
    public int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for(Character ch : s.toCharArray())
        {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        boolean isOdd = false;
        int ans = 0;
       
        for(Map.Entry<Character, Integer> entry: map.entrySet())
        {
            int val =  entry.getValue();
            if(val %2 == 1)
            {
                ans += (val-1);
                isOdd = true;
            }
            else
            ans += val;
        }
        if(isOdd) return ans+1;
        return ans;
    }
}