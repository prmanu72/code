/*
Given a word pat and a text txt. Return the count of the occurrences of anagrams of the word in the text.

Example 1:

Input: txt = "forxxorfxdofr", pat = "for"
Output: 3
Explanation: for, orf and ofr appears in the txt, hence answer is 3.
Example 2:

Input: txt = "aabaabaa", pat = "aaba"
Output: 4
Explanation: aaba is present 4 times in txt.
Constraints:
1 <= |pat| <= |txt| <= 105
Both strings contain lowercase English letters.

*/
import java.util.*;

class Solution {

    int search(String pat, String txt) 
    {
        Map<Character, Integer> mp = new HashMap<>();
        for(int i = 0; i < pat.length(); i++)
        {
            char ch= pat.charAt(i);
            mp.put(ch, mp.getOrDefault(ch, 0) + 1);
        }
        
        int l = 0, r = 0, count = mp.size(), n = txt.length();
        int ans = 0;
        int k = pat.length();
        
        while(r < n)
        {
            char ch = txt.charAt(r);
            if(mp.containsKey(ch))
            {
                mp.put(ch, mp.get(ch) - 1);
                if(mp.get(ch) == 0)
                {
                    count--;
                }
            }
            if(r -l + 1 < k) r++;
            else if(r-l+1 == k)
            {
                if(count == 0)
                {
                    ans++;
                }
                char leftChar = txt.charAt(l);
                if(mp.containsKey(leftChar))
                {
                    mp.put(leftChar, mp.get(leftChar) + 1);
                    if(mp.get(leftChar) == 1)
                    {
                        count++;
                    }
                }
                l++;
                r++;
            }
        }
        return ans;
    }
}