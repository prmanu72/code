/*
Leetcode 76. Minimum Window Substring

Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".

The testcases will be generated such that the answer is unique.

 

Example 1:

Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
Example 2:

Input: s = "a", t = "a"
Output: "a"
Explanation: The entire string s is the minimum window.
Example 3:

Input: s = "a", t = "aa"
Output: ""
Explanation: Both 'a's from t must be included in the window.
Since the largest window of s only has one 'a', return empty string.
 

Constraints:

m == s.length
n == t.length
1 <= m, n <= 105
s and t consist of uppercase and lowercase English letters.
*/

class Solution {
    public String minWindow(String s, String t) 
    {
        int n = s.length();
        Map<Character, Integer> mp = new HashMap<>();
        int l = 0, r = 0;
        int st = -1, end = -1;

        if(n < t.length()) return "";

        for(Character ch : t.toCharArray())
        {
            mp.put(ch, mp.getOrDefault(ch, 0) + 1);
        }
        int count = mp.size();

        while(r < n)
        {
            char rCh = s.charAt(r);
            if(mp.containsKey(rCh))
            {
                mp.put(rCh, mp.get(rCh) - 1);
                if(mp.get(rCh) == 0)
                    count--;
            }

            if(count > 0) 
                r++;
            else if (count == 0)
            {
                while(count == 0)
                {
                    int sub = r-l+1;
                    if(end == -1 || (sub < end - st + 1))
                    {
                        end = r;
                        st = l;
                    }
                    char lChar = s.charAt(l);
                    if(mp.containsKey(lChar))
                    {
                        mp.put(lChar, mp.get(lChar) + 1);
                        if(mp.get(lChar) == 1) count++;
                    }
                    
                    l++;
                }
                r++;
            }
        }
        if(end != -1)
        {
            return s.substring(st, end+1);
        }
        return "";
    }
}