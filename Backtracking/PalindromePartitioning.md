# Problem
- Title: Palindrome Partitioning
- Source link: [LeetCode](https://leetcode.com/problems/palindrome-partitioning/)
- Description:
  Given a string `s`, partition it such that every substring in the partition is a palindrome. Return all possible palindrome partitionings of the string.

- Example:
  Input: `s = "aab"`
  Output: `[["a","a","b"],["aa","b"]]`
  Explanation:
  - `"a"`, `"a"`, and `"b"` are all palindromes
  - `"aa"` and `"b"` are also palindromes

- Constraints:
  - `1 <= s.length <= 16`
  - `s` contains only lowercase English letters

---

# Intuition
We need to split the string into parts, but every part must be a palindrome.

This is not a problem where we want just one answer. We need **all possible valid partitions**. That is why backtracking is a natural fit.

## Main idea
Start from the current index and try every possible substring starting there.

For each substring:
- if it is a palindrome, we can choose it as one part of the partition
- then we recursively solve the remaining suffix of the string
- after recursion, we remove that part and try the next possible substring

So the process is:
- choose one valid part
- move forward
- undo the choice
- try another part

That is exactly the backtracking pattern used in the other files in this folder.

## Why this works
Suppose the string is:
```text
"aab"
```

Start at index `0`.

Possible substrings starting at `0` are:
- `"a"` -> palindrome
- `"aa"` -> palindrome
- `"aab"` -> not a palindrome

So we only continue with:
- `"a"`
- `"aa"`

### First branch: choose `"a"`
Now we are at index `1`.

Possible substrings starting at `1` are:
- `"a"` -> palindrome
- `"ab"` -> not a palindrome

Choose `"a"`, then move to index `2`.

At index `2`:
- `"b"` -> palindrome

Now we reach the end of the string, so one complete partition is:
```text
["a", "a", "b"]
```

### Second branch: choose `"aa"`
Now we move directly to index `2`.

At index `2`:
- `"b"` -> palindrome

This gives:
```text
["aa", "b"]
```

So the final answer is:
```text
[["a","a","b"], ["aa","b"]]
```

## Important observation
We do not try to create all partitions first and check them later.

Instead, we check immediately:
- is the current substring a palindrome?

If yes, continue recursion.
If not, skip that choice.

This is the pruning step that keeps the recursion focused only on valid branches.

## What `idx` means in the recursion
The variable `idx` tells us:
- from which position we still need to partition the string

If `idx == s.length()`, it means:
- we have used the whole string
- the current list already forms one valid palindrome partition

So we add a copy of it to the final answer.

## Recursion tree for `"aab"`
```text
start at idx = 0
|- "a"
|  |- "a"
|  |  `- "b"   -> complete: ["a", "a", "b"]
|  `- "ab"      -> not palindrome
|- "aa"
|  `- "b"       -> complete: ["aa", "b"]
`- "aab"        -> not palindrome
```

Every root-to-leaf valid path becomes one answer.

## Why backtracking is needed
Suppose current partition is:
```text
["a", "a"]
```

Then we choose `"b"`:
```text
["a", "a", "b"]
```

After saving this answer, we must go back and restore the previous state:
```text
["a", "a"]
```

That is why we remove the last added substring after the recursive call.

Without that undo step, the next branch would start with the wrong partition state.

---

# Approach
1. Create a result list `res` to store all valid partitions.
2. Start recursion from index `0` with an empty current partition list.
3. At each recursion call, try every substring starting from the current index.
4. For each ending index `j`:
   - check if `s[idx...j]` is a palindrome
5. If it is not a palindrome, skip it.
6. If it is a palindrome:
   - add that substring to the current partition
   - recurse from index `j + 1`
   - remove the last substring after recursion finishes
7. If `idx` reaches `s.length()`, add a copy of the current partition to the result.

---

# Complexity

- Time complexity:
  Exponential in the worst case

At each index, we may try many possible cuts, and for strings like `"aaaa..."`, many substrings are palindromes, so many recursive branches are explored.

- Space complexity:

`O(n)` auxiliary recursion space, excluding the output

The recursion depth can go up to `n`, and the current partition list can also hold up to `n` substrings.  
The final output can be much larger because we store all valid partitions.

---

# Code
```java
class Solution {
    public List<List<String>> partition(String s) 
    {
        List<List<String>> res =  new ArrayList<>();
        findPartitions(s, 0, new ArrayList<>(), res);
        return res;
    }

    void findPartitions(String s, int idx, List<String> curList, List<List<String>> res)
    {
        if(idx == s.length())
        {
            res.add(new ArrayList(curList));
            return;
        }

        for(int j = idx; j < s.length(); j++)
        {
            if(isPalindrome(s, idx, j))
            {
                curList.add(s.substring(idx, j + 1));
                findPartitions(s, j + 1, curList, res);
                curList.remove(curList.size() - 1);
            }
        }
    }

    boolean isPalindrome(String s, int l, int r)
    {
        while(l <= r)
        {
            if(s.charAt(l) != s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }
}
```

---

# One-line takeaway
Try every substring starting from the current index, recurse only if it is a palindrome, and backtrack by removing it so all valid palindrome partitions can be generated.
