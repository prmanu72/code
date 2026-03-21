# Problem
- Title: N Digit numbers with digits in increasing order
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/n-digit-numbers-with-digits-in-increasing-order5903/1)
- Description:
  Given an integer `n`, return all `n`-digit numbers whose digits are in strictly increasing order from left to right.

- Example:
  Input: `n = 2`
  Output: `12 13 14 15 16 17 18 19 23 ... 79 89`
  Explanation: Every valid number has digits increasing strictly from left to right, so `12` is valid, `22` is not, and `21` is not.

- Constraints:
  - `1 <= n <= 9`

---

# Intuition
We are not being asked to check every `n`-digit number. That would be wasteful.

For example, if `n = 3`, scanning from `100` to `999` means checking `900` numbers, even though only a small fraction can ever be valid. Most numbers fail immediately because:
- digits repeat, like `122`
- digits decrease somewhere, like `321`
- digits stay equal, like `334`

So instead of generating all numbers and filtering, we should build only valid numbers from the start.

## Core observation
If a number must be strictly increasing from left to right, then:
- every next digit must be greater than the previous digit
- once we place a digit, all future choices are restricted

Example for `n = 3`:
- if we start with `1`, the next digit can only be `2..9`
- if the second digit is `4`, the third digit can only be `5..9`

That means every partial number already tells us exactly what choices remain.

This is a classic backtracking shape:
- choose a digit
- recurse to fill the remaining positions
- only try choices that keep the partial number valid

## Why the recursion parameter `lastDigit` is enough
At any step, we do not need to remember the whole number to decide the next valid digit.

We only need to know:
- how many digits are still left to place
- what the last chosen digit was
- what number has been built so far

If the last chosen digit is `5`, the next digit must be one of `6, 7, 8, 9`.
Nothing smaller can ever work.

So `lastDigit` captures the entire restriction for the next move.

## How the recursion builds numbers
Suppose `n = 2`.

We begin with:
- `num = 0`
- `lastDigit = 0`

Then the loop starts from `lastDigit + 1`, so first choices are:
- `1`
- `2`
- `3`
- ...
- `9`

If we choose `1`, the recursive call tries:
- `12`
- `13`
- `14`
- ...
- `19`

If we choose `2`, the recursive call tries:
- `23`
- `24`
- ...
- `29`

This automatically generates:
```text
12 13 14 15 16 17 18 19 23 24 ... 79 89
```

No invalid number is ever formed.

## Why the output is already in increasing order
This is an important detail.

The recursion always tries smaller possible digits before larger ones:
- first starting digit `1`, then `2`, then `3`, and so on
- inside each branch, again smaller valid next digits are tried before larger ones

So the traversal order itself is lexicographically increasing, which for equal-length numeric strings is also numerically increasing.

That is why we do not need to sort the result afterward.

## Why `n == 1` needs special handling
For a single digit, the expected output includes:
```text
0 1 2 3 4 5 6 7 8 9
```

But the recursive function starts choosing digits from `lastDigit + 1`.
If `lastDigit = 0`, it starts from `1`, so `0` would never be generated.

That is why the solution manually adds `0` when `n == 1`.

After that, the recursive part generates:
```text
1 2 3 4 5 6 7 8 9
```

Together, the final result becomes correct.

## Backtracking view
Even though there is no explicit "reject and undo" step with a boolean check, this is still backtracking because:
- we build the answer digit by digit
- every recursive call explores one valid choice
- after returning, the loop moves to the next possible choice

Since `num` and `lastDigit` are passed by value, Java automatically gives us the restored previous state for the next branch. So the "backtrack" happens naturally through recursion.

## Small recursion tree for `n = 3`
```text
start
|- 1
|  |- 12
|  |  |- 123
|  |  |- 124
|  |  `- ...
|  |- 13
|  |  |- 134
|  |  `- ...
|  `- ...
|- 2
|  |- 23
|  |  |- 234
|  |  `- ...
|  `- ...
`- ...
```

Each level fixes one more digit, and every branch only extends with larger digits, so every leaf is guaranteed to be a valid strictly increasing number.

---

# Approach
1. Create an empty result list `res`.
2. If `n == 1`, add `0` separately because single-digit numbers include zero.
3. Start a recursive helper `solve(remainingDigits, num, lastDigit, res)`.
4. If `remainingDigits == 0`, the current number is complete, so add it to the result.
5. Otherwise, try every digit from `lastDigit + 1` to `9`.
6. Append the chosen digit to the current number using `num * 10 + i`.
7. Recurse with:
   - one less digit to fill
   - updated number
   - updated last chosen digit
8. Since only larger digits are chosen at every step, every constructed number is valid and the final list is already sorted.

---

# Complexity

- Time complexity:
  `O(C(9, n) * n)`

There are exactly `C(9, n)` valid strictly increasing numbers using digits `1..9`, and each recursive construction handles up to `n` levels.  
For the constraint range, this is efficient. It is also commonly described as roughly `O(9^n)` upper-bound style recursion, but the actual number of generated valid states is much smaller.

- Space complexity:

`O(n)`

The auxiliary space comes from the recursion stack depth, which is at most `n`. The returned list is excluded from auxiliary-space counting.

---

# Code
```java
class Solution {
    public static ArrayList<Integer> increasingNumbers(int n) {
        ArrayList<Integer> res = new ArrayList<>();

        if (n == 1) {
            res.add(0);
        }

        solve(n, 0, 0, res);
        return res;
    }

    static void solve(int n, int num, int lastDigit, ArrayList<Integer> res) {
        if (n == 0) {
            res.add(num);
            return;
        }

        for (int i = lastDigit + 1; i <= 9; i++) {
            solve(n - 1, num * 10 + i, i, res);
        }
    }
}
```

---

# One-line takeaway
Build the number digit by digit using backtracking, and at each step only choose digits larger than the previous one so every generated number is automatically valid and sorted.
