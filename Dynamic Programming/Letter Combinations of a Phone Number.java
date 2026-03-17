/*
17. Letter Combinations of a Phone Number

Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.


 

Example 1:

Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
Example 2:

Input: digits = "2"
Output: ["a","b","c"]
 

Constraints:

1 <= digits.length <= 4
digits[i] is a digit in the range ['2', '9'].
*/

import java.util.*;
class Solution {
    
    public List<String> letterCombinations(String s) 
    {
        Map<Character, String> mp = new HashMap<>();
        mp.put('2', "abc");
        mp.put('3', "def");
        mp.put('4', "ghi");
        mp.put('5', "jkl");
        mp.put('6', "mno");
        mp.put('7', "pqrs");
        mp.put('8', "tuv");
        mp.put('9', "wxyz");
        return dp(s, s.length(), 0, mp);
    }

    List<String> dp(String s, int n, int idx, Map<Character, String> mp)
    {
        if(idx == n-1) 
           {
            List<String> list = new ArrayList<>();
            for (char c : mp.get(s.charAt(idx)).toCharArray()) 
            {
              list.add(String.valueOf(c));
            }
            return list;
           }

        List<String> t = dp(s, n, idx+1,mp);
        List<String> ans = new ArrayList<>();
        String dig = mp.get(s.charAt(idx));
        
        for(String st: t)
        {
            for (char c : dig.toCharArray()) 
            {
                ans.add(c + "" + st);
            }
        }
        return ans;
    }

}