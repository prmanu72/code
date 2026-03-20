# Problem
- Title: Largest Number in K Swaps
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/largest-number-in-k-swaps-1587115620/1)
- Description:
  Given a number `k` and string `s` of digits denoting a positive integer, build the largest number possible by performing swap operations on the digits of `s` at most `k` times.

- Example:
  Input: `s = "1234567", k = 4`
  Output: `7654321`
  Explanation: Three swaps can transform `1234567` into `7654321` by swapping `1` with `7`, `2` with `6`, and `3` with `5`.

- Constraints:
  - `1 <= s.size() <= 15`
  - `1 <= k <= 7`

---

# Intuition
We want the number to be as large as possible from left to right.

That means for every index `idx`, we should try to place the maximum possible digit at that position. But this is not a pure greedy problem, because if the maximum digit appears multiple times in the suffix, swapping with different occurrences can leave different remaining suffixes for future swaps.

So the right strategy is:
- process the string from left to right
- at each index, find the maximum digit available in the remaining suffix
- if the current digit is already that maximum, move ahead without using a swap
- otherwise, swap with every occurrence of that maximum digit, recurse, and backtrack

This gives a backtracking solution with pruning:
- we never try useless swaps with smaller digits
- we only explore branches that can improve the current position

## Why pure greedy does not work
Consider:

```text
s = "342431636"
```
```text
k = 3
```

A greedy strategy that commits to one locally best swap sequence can end up with:

```text
66441233
```

But the correct maximum is:

```text
66441332
```

The reason is that after placing a large digit at an early position, there can still be multiple equally promising swap choices for later positions. Choosing one of them greedily may damage the suffix arrangement and block a better final number.

So the key point is:
- greedy can make the current prefix look optimal
- but different swap choices can leave different suffixes
- therefore we must explore all useful maximum-digit swap branches using backtracking

### Recursion tree for `"42431636", k = 3`
Below is the same recursion, drawn in a more tree-like shape.

Notation:
- `E` = explored branch
- `S` = skipped/pruned branch

```text
                                "42431636", k=3, idx=0, max='6'
                     /S(0,1)      /S(0,3)      /E(0,5)             \E(0,7)
                    /            /            /                     \
          "22431646"      "32441636"   "62431436", k=2       "62431634", k=2
                                            |                      |
                                 max at idx=1 is '6'     max at idx=1 is '6'
                                            |                      |
                              S(1,2..6), E(1,7)          S(1,2,3,4,6,7), E(1,5)
                                            |                      |
                                    "66431432", k=1         "66431234", k=1
                                            |                      |
                                   idx=2, max='4'           idx=2, max='4'
                                            |                      |
                                      E(no swap)               E(no swap)
                                            |                      |
                                    "66431432", idx=3       "66431234", idx=3
                                            |                      |
                                   max at idx=3 is '4'      max at idx=3 is '4'
                                   /S(3,4)   \E(3,5)        /S(3,4,5,6)   \E(3,7)
                                  /           \             /               \
                           "66134432"     "66441332"   pruned            "66441233"
                                            answer                         smaller
```

This tree shows the important pruning idea:
- at `idx = 0`, only the two swaps with digit `'6'` are explored
- all swaps with `'2'`, `'3'`, `'1'`, or `'4'` are skipped immediately
- later, both explored branches still need to be checked because they produce different suffix layouts

That is exactly why a single greedy choice is unsafe here:
- one branch gives `66441233`
- another gives `66441332`
- the second one is larger, so both maximum-digit branches must be explored

---

# Approach
1. Convert the input string into a character array so swaps are easy.
2. Keep a global best answer in another character array `ans`.
3. Start recursion from index `0`.
4. For the current index `idx`, find the maximum digit from `idx` to the end.
5. If that maximum digit is equal to `s[idx]`, no swap is needed here. Recurse for `idx + 1` with the same `k`.
6. Otherwise, for every position `i > idx` such that `s[i]` equals that maximum digit:
7. Swap `s[idx]` and `s[i]`.
8. Recurse for `idx + 1` with `k - 1`.
9. Swap back to restore the original array state.
10. Whenever we reach a base case, compare the current number with `ans` and update `ans` if needed.

**Example: `"3435335", k = 3"`**
```text
idx = 0, current = '3'
maximum in suffix = '5'

Try placing '5' at index 0:
  swap with one occurrence of '5'
  recurse for the next index

At the next level, again try to place the best possible digit at that position.
Backtracking explores all useful ways to use the remaining swaps.
```

---

# Complexity

- Time complexity:
  Exponential in the worst case due to backtracking.

More precisely, the branching factor is limited because we only recurse on positions containing the maximum suffix digit, and `k <= 7`, so this works well within the given constraints.

- Space complexity:
  `O(n + k)`

`O(n)` is used by the character arrays, and the recursion depth is at most `O(n)` with at most `k` swap-consuming levels.

---

# Code
```java
class Solution {
    public String findMaximumNum(String s, int k) {
        char[] ans = s.toCharArray();
        char[] arr = s.toCharArray();

        backtrack(arr, k, 0, ans);
        return new String(ans);
    }

    private void backtrack(char[] arr, int k, int idx, char[] ans) {
        if (isBigger(ans, arr)) {
            copy(arr, ans);
        }

        if (k == 0 || idx == arr.length) {
            return;
        }

        char max = arr[idx];
        for (int i = idx + 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        if (max == arr[idx]) {
            backtrack(arr, k, idx + 1, ans);
            return;
        }

        for (int i = arr.length - 1; i > idx; i--) {
            if (arr[i] == max) {
                swap(arr, idx, i);
                backtrack(arr, k - 1, idx + 1, ans);
                swap(arr, idx, i);
            }
        }
    }

    private boolean isBigger(char[] older, char[] newer) {
        for (int i = 0; i < older.length; i++) {
            if (newer[i] > older[i]) return true;
            if (newer[i] < older[i]) return false;
        }
        return false;
    }

    private void copy(char[] from, char[] to) {
        for (int i = 0; i < from.length; i++) {
            to[i] = from[i];
        }
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

---

# One-line takeaway
Use backtracking with pruning: at each index, only try swaps that place the maximum possible suffix digit at the current position.
