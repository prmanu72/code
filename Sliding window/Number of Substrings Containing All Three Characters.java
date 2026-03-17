/*
1358. Number of Substrings Containing All Three Characters

Given a string s consisting only of characters a, b and c.

Return the number of substrings containing at least one occurrence of all these characters a, b and c.

 

Example 1:

Input: s = "abcabc"
Output: 10
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again). 
Example 2:

Input: s = "aaacb"
Output: 3
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb". 
Example 3:

Input: s = "abc"
Output: 1
 

Constraints:

3 <= s.length <= 5 x 10^4
s only consists of a, b or c characters.

*/

class Solution {
    public int numberOfSubstrings(String s) 
    {
        Map<Character, Integer> mp = new HashMap<>();

        int n = s.length();
        int l = 0, r = 0;
        int ans = 0;

        while(r < n)
        {
            char  ch = s.charAt(r);
            mp.put(ch, mp.getOrDefault(ch, 0) + 1);
            if(mp.size() < 3) r++;
            else 
            {
                while(l < n && mp.size() == 3)
                {
                    ans = ans + (n-r); // if substring r - l + 1 is valid, then all substrings starting from l to n-1 are valid
                    
                    char ch2 = s.charAt(l);
                    mp.put(ch2, mp.getOrDefault(ch2, 0) - 1);

                    if(mp.get(ch2) == 0) mp.remove(ch2);
                    l++;
                }
                r++;
            }
        }

        return ans;
    }
}