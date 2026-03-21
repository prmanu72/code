# Problem
- Title: N-Queens
- Source link: [LeetCode](https://leetcode.com/problems/n-queens/)
- Description:
  Place `n` queens on an `n x n` chessboard so that no two queens attack each other.
  Return all distinct board configurations.

- Example:
  Input: `n = 4`
  Output:
  ```text
  [
    [".Q..",
    "...Q",
    "Q...",
    "..Q."],
    ["..Q.",
    "Q...",
    "...Q",
    ".Q.."]
  ]
  ```

- Constraints:
  - `1 <= n <= 9`

---

# Intuition
We need to place exactly one queen in each row.

For every row:
- try each column
- place a queen only if that cell is safe
- recurse for the next row
- undo the choice after recursion

This is a standard backtracking problem because:
- we build the board row by row
- each row gives multiple choices
- invalid branches are skipped early

---

# Approach 1: Backtracking With A `blocked` Matrix

## Idea
Your solution maintains:
- `board[r][c]` to build the actual answer
- `blocked[r][c]` to track how many queens are attacking a cell

If `blocked[row][col] == 0`, the cell is currently safe.

When a queen is placed:
- mark the board with `'Q'`
- update all attacked cells by adding `1`
- recurse to the next row

When backtracking:
- remove the queen
- subtract `1` from the same attacked cells

This works well because multiple queens can attack the same cell, and the count tells us whether the cell is still unsafe.

## Why only one row at a time
The recursion processes rows from top to bottom.

That means:
- row `0` decides where the first queen goes
- row `1` decides where the second queen goes
- and so on

If we reach `row == n`, all queens are placed, so the current board is one valid answer.

## Complexity
- Time complexity:
  Exponential in `n`, because we try many placements recursively.

- Space complexity:
  `O(n^2)` for `board` and `blocked`, excluding the output.

## Code
```java
import java.util.*;

class Solution {
    public List<List<String>> solveNQueens(int n)
    {
        char[][] board = new char[n][n];
        int[][] blocked = new int[n][n];

        for (char[] ch : board)
            Arrays.fill(ch, '.');

        List<List<String>> res = new ArrayList<>();

        solve(n, 0, board, blocked, res);
        return res;
    }

    void solve(int n, int row, char[][] board, int[][] blocked, List<List<String>> res)
    {
        if (row == n)
        {
            List<String> temp = new ArrayList<>();
            for (char[] ch : board)
            {
                temp.add(new String(ch));
            }
            res.add(temp);
            return;
        }

        for (int j = 0; j < n; j++)
        {
            if (blocked[row][j] == 0)
            {
                board[row][j] = 'Q';
                restrictBoard(blocked, row, j, n, 1);

                solve(n, row + 1, board, blocked, res);

                board[row][j] = '.';
                restrictBoard(blocked, row, j, n, -1);
            }
        }
    }

    void restrictBoard(int[][] blocked, int r, int c, int n, int val)
    {
        int k = 0;
        // Blocking current and below rows as we are recursing from top
        int[] dr = new int[]{0, 1, 1, 1, 0};
        int[] dc = new int[]{1, 1, 0, -1, -1};

        while (k < n)
        {
            for (int t = 0; t < 5; t++)
            {
                int R = r + k * dr[t];
                int C = c + k * dc[t];
                if (isValid(R, C, n))
                {
                    blocked[R][C] += val;
                }
            }
            k++;
        }
    }

    boolean isValid(int r, int c, int n)
    {
        return r >= 0 && c >= 0 && r < n && c < n;
    }
}
```

## Notes on this version
- The backtracking logic is correct.
- The `blocked` count handles overlapping attacks safely.
- The `Pair` class is unused here and can be removed.
- In `restrictBoard()`, when `k == 0`, the same cell `(r, c)` is updated multiple times. The code still works because backtracking undoes it symmetrically, but it is extra work.

---

# Approach 2: Optimized Using Columns And Diagonals

## Core idea
Instead of marking the whole board as blocked, we only track whether a queen already exists in:
- a column
- a `\` diagonal
- a `/` diagonal

That gives three arrays:
- `col[c]`
- `diag1[row - col + n - 1]`
- `diag2[row + col]`

So before placing a queen at `(row, col)`:
- if that column is used, reject
- if that `\` diagonal is used, reject
- if that `/` diagonal is used, reject

If all three are free, the cell is safe.

## Why this is faster
With the `blocked` matrix solution, placing one queen updates many cells.

With this optimized approach:
- checking safety is `O(1)`
- placing a queen is `O(1)`
- removing a queen is `O(1)`

So the recursion stays much lighter.

## Visual explanation

### 1. Column array: `col[c]`
`col[c]` tells us whether column `c` already has a queen.

For example, if `c = 2`, then this entire vertical line is controlled:

```text
. . X . .
. . X . .
. . X . .
. . X . .
. . X . .
```

So if `col[2] == true`, we cannot place another queen anywhere in column `2`.

---

### 2. Main diagonal array: `diag1[row - col + n - 1]`
Cells on the same `\` diagonal have the same value of:

```text
row - col
```

Example:

```text
(0,0) (1,1) (2,2) (3,3)
```

For all of these:

```text
row - col = 0
```

Another `\` diagonal:

```text
(0,1) (1,2) (2,3)
```

For all of these:

```text
row - col = -1
```

Another:

```text
(1,0) (2,1) (3,2)
```

For all of these:

```text
row - col = 1
```

The problem is that `row - col` can be negative.

For an `n x n` board, the range is:

```text
-(n - 1) to +(n - 1)
```

So we shift it into a valid array index by adding `n - 1`:

```java
diag1[row - col + n - 1]
```

Now the index range becomes:

```text
0 to 2n - 2
```

---

### 3. Anti-diagonal array: `diag2[row + col]`
Cells on the same `/` diagonal have the same value of:

```text
row + col
```

Example:

```text
(0,3) (1,2) (2,1) (3,0)
```

For all of these:

```text
row + col = 3
```

Another:

```text
(0,2) (1,1) (2,0)
```

For all of these:

```text
row + col = 2
```

This value is never negative, so we can directly use:

```java
diag2[row + col]
```

Its range is also:

```text
0 to 2n - 2
```

---

### 4x4 board view
Below, `d1 = row - col` and `d2 = row + col`.

```text
(0,0) d1=0  d2=0    (0,1) d1=-1 d2=1   (0,2) d1=-2 d2=2   (0,3) d1=-3 d2=3
(1,0) d1=1  d2=1    (1,1) d1=0  d2=2   (1,2) d1=-1 d2=3   (1,3) d1=-2 d2=4
(2,0) d1=2  d2=2    (2,1) d1=1  d2=3   (2,2) d1=0  d2=4   (2,3) d1=-1 d2=5
(3,0) d1=3  d2=3    (3,1) d1=2  d2=4   (3,2) d1=1  d2=5   (3,3) d1=0  d2=6
```

You can observe:
- same column -> same `col[c]`
- same `\` diagonal -> same `row - col`
- same `/` diagonal -> same `row + col`

So to check if `(r, c)` is safe:

```java
if (col[c] || diag1[r - c + n - 1] || diag2[r + c]) {
    // cannot place queen
}
```

## Complexity
- Time complexity:
  Still exponential overall because this is backtracking, but each placement check is `O(1)`.

- Space complexity:
  `O(n)` auxiliary tracking arrays, excluding the board and output.

## Code
```java
import java.util.*;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        char[][] board = new char[n][n];

        for (char[] row : board) {
            Arrays.fill(row, '.');
        }

        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1]; // -(n-1) to (n-1) = 2(n-1) + 1 (zero)
        boolean[] diag2 = new boolean[2 * n - 1];

        solve(0, n, board, col, diag1, diag2, res);
        return res;
    }

    void solve(int row, int n, char[][] board, boolean[] col, boolean[] diag1,
               boolean[] diag2, List<List<String>> res) {
        if (row == n) {
            List<String> temp = new ArrayList<>();
            for (char[] chars : board) {
                temp.add(new String(chars));
            }
            res.add(temp);
            return;
        }

        for (int c = 0; c < n; c++) {
            int d1 = row - c + n - 1;
            int d2 = row + c;

            if (col[c] || diag1[d1] || diag2[d2]) {
                continue;
            }

            board[row][c] = 'Q';
            col[c] = true;
            diag1[d1] = true;
            diag2[d2] = true;

            solve(row + 1, n, board, col, diag1, diag2, res);

            board[row][c] = '.';
            col[c] = false;
            diag1[d1] = false;
            diag2[d2] = false;
        }
    }
}
```

---

# Summary
There are two good ways to think about this problem:

- Your `blocked` matrix approach:
  very intuitive because it directly marks attacked cells

- The optimized `col + diag1 + diag2` approach:
  preferred in interviews and LeetCode because the checks are constant time and the code is cleaner

The key diagonal formulas are:
- `col[c]`
- `diag1[row - col + n - 1]`
- `diag2[row + col]`

These three checks are enough because every queen attacks exactly:
- one column
- one `\` diagonal
- one `/` diagonal
