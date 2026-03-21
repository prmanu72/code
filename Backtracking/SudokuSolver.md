# Problem
- Title: Sudoku Solver
- Source link: [LeetCode](https://leetcode.com/problems/sudoku-solver/)
- Description:
  Write a program to solve a Sudoku puzzle by filling the empty cells.

  A Sudoku solution must satisfy all of the following rules:
  - each of the digits `1-9` must occur exactly once in each row
  - each of the digits `1-9` must occur exactly once in each column
  - each of the digits `1-9` must occur exactly once in each of the `9` `3 x 3` sub-boxes of the grid

  The `'.'` character indicates empty cells.

- Example:
  Input:
  ```text
  board = [
    ["5","3",".",".","7",".",".",".","."],
    ["6",".",".","1","9","5",".",".","."],
    [".","9","8",".",".",".",".","6","."],
    ["8",".",".",".","6",".",".",".","3"],
    ["4",".",".","8",".","3",".",".","1"],
    ["7",".",".",".","2",".",".",".","6"],
    [".","6",".",".",".",".","2","8","."],
    [".",".",".","4","1","9",".",".","5"],
    [".",".",".",".","8",".",".","7","9"]
  ]
  ```

  Output:
  ```text
  [
    ["5","3","4","6","7","8","9","1","2"],
    ["6","7","2","1","9","5","3","4","8"],
    ["1","9","8","3","4","2","5","6","7"],
    ["8","5","9","7","6","1","4","2","3"],
    ["4","2","6","8","5","3","7","9","1"],
    ["7","1","3","9","2","4","8","5","6"],
    ["9","6","1","5","3","7","2","8","4"],
    ["2","8","7","4","1","9","6","3","5"],
    ["3","4","5","2","8","6","1","7","9"]
  ]
  ```

  Explanation:
  The input board has exactly one valid solution.

- Constraints:
  - `board.length == 9`
  - `board[i].length == 9`
  - `board[i][j]` is a digit or `'.'`
  - it is guaranteed that the input board has only one solution

---

# Intuition
We try to fill the board cell by cell.

For every empty cell:
- try digits from `'1'` to `'9'`
- place a digit only if it does not break Sudoku rules
- recurse to solve the remaining cells
- if that choice fails later, undo it and try the next digit

This is classic backtracking:
- choose
- recurse
- undo

Because the puzzle has only one valid solution, we stop as soon as one complete board is found.

---

# Approach: Backtracking

## Main idea
The recursive function `solve(board, row, col)` means:
- solve the board starting from cell `(row, col)`

At each cell:
- if `row == 9`, the board is completely solved
- if `col == 9`, move to the next row
- if the current cell is already filled, skip it
- if the current cell is empty, try every digit from `'1'` to `'9'`

If placing a digit leads to a valid full solution, return `true` immediately.
If all digits fail, return `false` so the caller can backtrack.

## Why `solve()` returns `boolean`
We return `boolean` because Sudoku needs only one valid solution.

So:
- `true` means a valid solution has been found from this point onward
- `false` means this path does not work

This makes backtracking clean:
- place a digit
- recurse
- if recursion returns `true`, stop immediately
- otherwise undo the digit and try the next one

Without a `boolean` return type:
- recursion cannot naturally tell earlier calls that the puzzle is solved
- we would need extra state like a global flag or a separate answer board

## How `isValid()` works
Before placing a digit `ch` at `(r, c)`, we check:
- the whole column `c`
- the whole row `r`
- the `3 x 3` subgrid containing `(r, c)`

The top-left corner of the subgrid is:

```java
int r0 = r / 3 * 3;
int c0 = c / 3 * 3;
```

Examples:
- if `r = 5`, then `r / 3 = 1`, so `r0 = 3`
- if `c = 7`, then `c / 3 = 2`, so `c0 = 6`

So the box starts at `(3, 6)`.

This `isValid()` method is correct because we call it only when:

```java
board[row][col] == '.'
```

That means the target cell is still empty while we are checking row, column, and box.

---

# Dry Run
Suppose we reach an empty cell `(0, 2)`.

We try:
- `'1'`
- `'2'`
- `'3'`
- ...
- `'9'`

For each digit:
- if it conflicts with the row, reject it
- if it conflicts with the column, reject it
- if it conflicts with the `3 x 3` box, reject it
- otherwise place it and continue solving the next cell

If that later leads to failure:
- remove the digit
- try the next one

This continues until:
- the whole board is filled, or
- all choices fail and recursion backtracks further

---

# Complexity
- Time complexity:
  Exponential in the number of empty cells in the worst case.

- Space complexity:
  `O(E)` recursion depth, where `E` is the number of empty cells, excluding the input board.

In practice, pruning with row, column, and box checks reduces the search significantly.

---

# Code
```java
class Solution {
    public void solveSudoku(char[][] board)
    {
        solve(board, 0, 0);
    }

    boolean solve(char[][] board, int row, int col)
    {
        if(row == 9)
        {
            return true;
        }
        if(col == 9)
        {
            return solve(board, row + 1, 0);
        }

        if(board[row][col] == '.')
        {
            for(char k = '1'; k <= '9'; k++)
            {
                if(isValid(board, row, col, k))
                {
                    board[row][col] = k;
                    boolean isSuccess = solve(board, row, col + 1);
                    if(isSuccess) return true;
                    board[row][col] = '.';
                }
            }
        }
        else
        {
            boolean isSuccess = solve(board, row, col + 1);
            if(isSuccess) return true;
        }
        return false;
    }

    boolean isValid(char[][] board, int r, int c, char ch)
    {
        for(int i = 0; i < 9; i++)
            if(ch == board[i][c]) return false;

        for(int j = 0; j < 9; j++)
            if(ch == board[r][j]) return false;

        int r0 = r / 3 * 3;
        int c0 = c / 3 * 3;

        for(int i = r0; i < r0 + 3; i++)
        {
            for(int j = c0; j < c0 + 3; j++)
            {
                if(board[i][j] == ch)
                    return false;
            }
        }
        return true;
    }
}
```

---

# Summary
This solution uses backtracking to fill the Sudoku board in-place.

The important ideas are:
- process cells from left to right, top to bottom
- try digits only in empty cells
- validate against row, column, and `3 x 3` box
- return `boolean` so recursion can stop immediately after finding the solution
