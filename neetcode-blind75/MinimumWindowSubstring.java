class Solution {
    public String minWindow(String s, String t) 
    {
        Map<Character, Integer> refMap = new HashMap<>();
        Map<Character, Integer> currMap = new HashMap<>();
        for(Character c: t.toCharArray())
        {
            refMap.put(c, refMap.getOrDefault(c, 0) + 1);
        }
        int l = 0, r = 0, n = s.length();
        int[] res = new int[2];
        int ansL = Integer.MAX_VALUE;
        int have = 0, need = refMap.size();
        while(l <= r && r < n)
        {
            Character ch = s.charAt(r);
            currMap.put(ch, currMap.getOrDefault(ch, 0) + 1);
            
            if(refMap.containsKey(ch) && currMap.get(ch) == refMap.get(ch))
                {
                    have += 1;
                }

            while( have == need)
            {
                if(r-l+1 < ansL)
                {
                    ansL = r-l+1;
                    res = new int[]{l, r};
                }
                Character leftChar= s.charAt(l);
                currMap.put(leftChar, currMap.getOrDefault(leftChar, 0) - 1);
                
                if(refMap.containsKey(leftChar) && currMap.get(leftChar) < refMap.get(leftChar))
                {
                    have -= 1;
                }
                l++;
            }    
            r++;
        }
        return ansL == Integer.MAX_VALUE ? "" : s.substring(res[0], res[1]+1);
    }
}
