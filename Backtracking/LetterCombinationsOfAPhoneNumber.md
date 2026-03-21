# Problem
- Title: Letter Combinations of a Phone Number
- Source link: [LeetCode](https://leetcode.com/problems/letter-combinations-of-a-phone-number)
- Description:
  Given a string of digits from `2` to `9`, return all possible letter combinations that the number could represent, based on the mapping used on a phone keypad.

- Example:
  Input: `digits = "23"`
  Output: `["ad","ae","af","bd","be","bf","cd","ce","cf"]`
  Explanation:
  - digit `2` maps to `"abc"`
  - digit `3` maps to `"def"`
  - every letter from the first digit can pair with every letter from the second digit

- Constraints:
  - `1 <= digits.length <= 4`
  - `digits[i]` is in the range `['2', '9']`

---

# Intuition
Each digit gives us a small group of possible letters.

So for every position, we just need to choose one letter from that digit's group.

For example, if:
```text
"23"
```

then:
- `2 -> abc`
- `3 -> def`

So the final answers are all possible ways to combine:
- one letter from `"abc"`
- with one letter from `"def"`

That gives:
```text
ad ae af bd be bf cd ce cf
```

There are two natural ways to think about this problem:
- backtracking: build one combination step by step
- recursion + DP thinking: solve the suffix first, then prefix the current digit's letters to those smaller answers

Both lead to correct solutions.

---

# Approach 1: Backtracking

## Intuition
At index `idx`, the current digit gives us some choices.

We try each letter for that digit:
- append it to the current partial string
- recurse for the next index
- remove it after recursion finishes

So the combination is built one character at a time.

This is classic backtracking:
- choose
- recurse
- undo

### Example for `"23"`
At index `0`, digit `2` gives:
- `a`
- `b`
- `c`

If we choose `a`, then at index `1`, digit `3` gives:
- `d` -> `ad`
- `e` -> `ae`
- `f` -> `af`

Then we backtrack and try:
- `b` -> `bd`, `be`, `bf`
- `c` -> `cd`, `ce`, `cf`

### What `idx` means
`idx` tells us which digit we are currently processing.

If `idx == s.length()`, it means:
- one letter has been chosen for every digit
- the current string is complete

So we store it in the result.

## Approach
1. Create a map from digits to letters.
2. Start from index `0` with an empty `StringBuilder`.
3. At each step, get the letters of the current digit.
4. Try each letter:
   - append it
   - recurse for the next digit
   - remove it after recursion
5. When `idx` reaches the end, add the built string to the result.

## Complexity

- Time complexity:
  `O(4^n * n)` in the worst case

Each digit can contribute up to `4` letters, so the total number of combinations is exponential.  
Creating the final strings contributes the extra `n` factor.

- Space complexity:

`O(n)` auxiliary recursion space, excluding the output

The recursion depth and the current builder both grow up to the number of digits.

## Code
```java
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

        List<String> res = new ArrayList<>();

        backTrack(s, 0, new StringBuilder(), res, mp);

        return res;
    }

    void backTrack(String s, int idx, StringBuilder cur, List<String> res, Map<Character, String> mp)
    {
        if(idx == s.length()) 
        {
            res.add(cur.toString());
            return;
        }

        for(char ch : mp.get(s.charAt(idx)).toCharArray())
        {
            int len = cur.length();
            cur.append(ch);

            backTrack(s, idx + 1, cur, res, mp);

            cur.setLength(len);
        }
    }
}
```

---

# Approach 2: Recursive DP / Build From Suffix

## Intuition
This is the idea used in [Letter Combinations of a Phone Number.java](E:\code\Dynamic%20Programming\Letter%20Combinations%20of%20a%20Phone%20Number.java).

Instead of building the answer from left to right using one mutable string, we can think like this:

- first find all combinations for the suffix
- then attach the current digit's letters in front of each of those suffix combinations

### Example for `"23"`
Suppose we want all combinations starting from index `0`.

Digit at index `0` is:
```text
2 -> abc
```

Now ask:
```text
what are all combinations for the remaining suffix starting at index 1?
```

That suffix is `"3"`, so its combinations are:
```text
d, e, f
```

Now prepend every letter of `"abc"` to each of those:
- `a + d`, `a + e`, `a + f`
- `b + d`, `b + e`, `b + f`
- `c + d`, `c + e`, `c + f`

That gives the full answer.

So the recursive relation is:
- solve smaller suffix
- combine current digit's letters with all suffix answers

## Why this feels like DP
The function `dp(idx)` means:
- return all combinations that can be formed from digits starting at index `idx`

So:
- `dp(last index)` is easy: just return all letters of that last digit
- `dp(idx)` can be built using `dp(idx + 1)`

That is why this is a recursive DP-style decomposition.

## Base case
If `idx` is at the last digit:
- return a list containing each letter of that digit as a one-character string

Example:
```text
digit = '3'
letters = "def"
result = ["d", "e", "f"]
```

## Recursive step
To compute combinations from index `idx`:
1. Compute all combinations of the suffix starting at `idx + 1`
2. Get the letter string for the current digit
3. For every suffix combination
4. For every current letter
5. Join them as:
```text
currentLetter + suffixString
```

That produces all combinations starting at `idx`.

## Small recursion picture for `"23"`
```text
dp(0)
|- current digit: "abc"
`- dp(1)
   `- returns ["d", "e", "f"]

Combine:
a + d, a + e, a + f
b + d, b + e, b + f
c + d, c + e, c + f
```

## Approach
1. Create the digit-to-letter map.
2. Call a recursive function `dp(s, n, idx, mp)`.
3. If `idx` is the last digit:
   - return a list of one-letter strings for that digit
4. Otherwise:
   - get all combinations of the suffix using `dp(idx + 1)`
   - get the current digit's letters
   - combine every current letter with every suffix string
5. Return the built list.

## Complexity

- Time complexity:
  `O(4^n * n)` in the worst case

The total number of combinations is exponential, and each final string has length up to `n`.

- Space complexity:

`O(4^n * n)` including returned recursive lists

Unlike backtracking, this approach creates and returns new lists at each recursive level, so more intermediate storage is used.

If we talk only about recursion stack depth, it is:
```text
O(n)
```

## Code
```java
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
        if(idx == n - 1) 
        {
            List<String> list = new ArrayList<>();
            for (char c : mp.get(s.charAt(idx)).toCharArray()) 
            {
                list.add(String.valueOf(c));
            }
            return list;
        }

        List<String> t = dp(s, n, idx + 1, mp);
        List<String> ans = new ArrayList<>();
        String dig = mp.get(s.charAt(idx));
        
        for(String st : t)
        {
            for (char c : dig.toCharArray()) 
            {
                ans.add(c + "" + st);
            }
        }
        return ans;
    }
}
```

---

# Which One To Prefer
- Use backtracking when you want the cleanest "build one answer and undo" flow.
- Use the recursive DP-style approach when you like thinking in terms of "answers for the suffix" and then combining them upward.

For this problem, both are valid and both have the same exponential output-driven time complexity.

The backtracking version is usually more space-efficient in practice because it reuses one `StringBuilder` instead of creating many intermediate lists.

---

# One-line takeaway
This problem can be solved either by backtracking one letter at a time or by recursively computing suffix combinations and prefixing the current digit's letters onto them.
