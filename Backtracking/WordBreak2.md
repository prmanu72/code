# Problem
- Title: Word Break - 2
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/word-break-part-23249/1)
- Description:
  Given a string `s` and a dictionary `dict[]` of valid words, return all possible ways to break `s` into a sentence such that every word in the sentence belongs to the dictionary. A dictionary word may be used multiple times.

- Example:
  Input: `s = "likegfg"`, `dict = ["lik", "like", "egfg", "gfg"]`
  Output:
  - `"lik egfg"`
  - `"like gfg"`
  Explanation: Both sentences use only words present in the dictionary.

- Constraints:
  - `1 <= dict.size() <= 20`
  - `1 <= dict[i].length() <= 15`
  - `1 <= s.length() <= 500`

---

# Intuition
We need to generate **all possible valid sentences**, not just check whether one split is possible.

That makes this a backtracking problem:
- choose one valid word from the current position
- add it to the current sentence
- recursively solve the remaining suffix
- then undo that choice and try the next word

## Main idea
At any index `start`, try every substring starting there:
- `s[start...start]`
- `s[start...start+1]`
- `s[start...start+2]`
- and so on

If a substring is present in the dictionary, then it is a valid word choice.

Once we choose that word:
- append it to the current sentence
- recurse from the next index

If we finally reach the end of the string, then the current sentence is one complete answer.

## Why converting the dictionary into a `HashSet` helps
We need to repeatedly ask:
- is this substring a valid dictionary word?

If the dictionary stays as a list, every lookup is linear.
Using a `HashSet` makes membership checking fast on average.

So:
- `List.contains(word)` would be slower
- `HashSet.contains(word)` is the right choice here

## How the recursion works on `"likegfg"`
Dictionary:
```text
["lik", "like", "egfg", "gfg"]
```

Start from index `0`.

Possible prefixes are:
- `"l"` -> not in dictionary
- `"li"` -> not in dictionary
- `"lik"` -> valid
- `"like"` -> valid

So two recursive branches start.

### Branch 1: choose `"lik"`
Now we are at the substring:
```text
"egfg"
```

Possible prefixes:
- `"e"` -> invalid
- `"eg"` -> invalid
- `"egf"` -> invalid
- `"egfg"` -> valid

So we get:
```text
"lik egfg"
```

### Branch 2: choose `"like"`
Now we are at:
```text
"gfg"
```

Possible prefixes:
- `"g"` -> invalid
- `"gf"` -> invalid
- `"gfg"` -> valid

So we get:
```text
"like gfg"
```

These are the two valid answers.

## What the `start` index means
`start` tells us from where the remaining string is still not broken.

So:
- everything before `start` is already part of the current sentence
- we now need to find the next valid word beginning exactly at `start`

If `start == s.length()`, it means:
- the whole string has been consumed
- the current sentence is complete

So we save it in the result.

## Why backtracking is needed
Suppose current sentence is:
```text
"like "
```

Then we choose:
```text
"gfg "
```

Now one complete answer is formed.

After storing it, we must remove `"gfg "` so that we can return to the earlier state and try other possible words from that level.

That undo step is what backtracking means here.

## Recursion tree intuition
For `"likegfg"`:
```text
start = 0
|- "lik"
|  `- "egfg"   -> complete: "lik egfg"
`- "like"
   `- "gfg"    -> complete: "like gfg"
```

Each valid root-to-leaf path forms one sentence.

## Important note about spaces
The current logic appends:
```text
word + " "
```

at every step.

That means the built string may end with a trailing space when it is added to the answer.  
In practice, many solutions trim the sentence before storing it, or they add spaces only between words.

The recursive idea remains the same either way.

---

# Approach
1. Convert the dictionary array into a `HashSet` for fast word lookup.
2. Start backtracking from index `0` with an empty sentence builder.
3. At each recursion call, try every substring starting from the current index.
4. For each ending index `end`:
   - form `s[start...end]`
   - check whether it exists in the dictionary set
5. If it is not a valid word, skip it.
6. If it is a valid word:
   - append it to the current sentence
   - recurse from `end + 1`
   - remove the appended part after recursion
7. If `start` reaches the end of the string, store the built sentence as one answer.

---

# Complexity

- Time complexity:
  Exponential in the worst case

At each position, multiple valid word choices may exist, so many sentence combinations may need to be explored.

- Space complexity:

`O(n)` auxiliary recursion space, excluding the output

The recursion depth can go up to the number of chosen words, and the current sentence builder also grows with the constructed sentence.  
The final output can be very large because all valid sentences are stored.

---

# Code
```java
class Solution {
    static String[] wordBreak(String[] dict, String s) 
    {
        List<String> res = new ArrayList<>();

        Set<String> set = new HashSet<>(Arrays.asList(dict));
        
        backTrack(s, 0, new StringBuilder(), res, set);
        
        return res.toArray(new String[0]);
    }
    
    static void backTrack(String s, int start, StringBuilder cur, List<String> res, Set<String> set)
    {
        if(start == s.length())
        {
            res.add(cur.toString().trim());
            return;
        }
        
        for(int end = start; end < s.length(); end++)
        {
            String subStr = s.substring(start, end + 1);
            if(set.contains(subStr))
            {
                int len = cur.length();
                cur.append(subStr).append(" ");
                
                backTrack(s, end + 1, cur, res, set);
                
                cur.setLength(len);
            }
        }
    }
}
```

---

# One-line takeaway
Try every dictionary word starting from the current index, build the sentence one word at a time, and backtrack after each choice to generate all valid sentence splits.
