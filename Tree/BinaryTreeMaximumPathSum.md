# Problem
- Title: 124. Binary Tree Maximum Path Sum
- Source link: [LeetCode](https://leetcode.com/problems/binary-tree-maximum-path-sum/)
- Description:
  Given the root of a binary tree, return the maximum path sum of any non-empty path.

  A path is a sequence of nodes where each adjacent pair is connected by an edge. A node can appear at most once in the path, and the path does not need to pass through the root.

- Example:
  Input: `root = [1,2,3]`
  Output: `6`
  Explanation: The best path is `2 -> 1 -> 3`.

- Constraints:
  - The number of nodes in the tree is in the range `[1, 3 * 10^4]`
  - `-1000 <= Node.val <= 1000`

---

# Intuition
For every node, there are two different quantities to think about:

1. The best path sum passing through that node as the highest point of the path.
2. The best downward path sum starting from that node and going to only one side, which is what we can return to the parent.

If a left or right subtree contributes a negative sum, it only makes the answer worse, so we ignore it by treating it as `0`.

That gives:
- path through current node = `root.val + left + right`
- path returned to parent = `root.val + max(left, right)`

We compute both with one DFS traversal.

---

# Approach
1. Use DFS to compute the best downward path sum from each node.
2. Recursively get the left and right contributions.
3. Clamp both contributions to `0` if they are negative.
4. Update the global answer using:

```text
root.val + left + right
```

5. Return to the parent:

```text
root.val + max(left, right)
```

because a parent can only continue one branch upward.

---

# Complexity

- Time complexity:
  `O(n)`

Each node is visited once.

- Space complexity:
  `O(h)`

`h` is the height of the tree due to recursion stack. Worst case is `O(n)` for a skewed tree.

---

# Code
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int res;

    public int maxPathSum(TreeNode root) {
        res = Integer.MIN_VALUE;
        solve(root);
        return res;
    }

    int solve(TreeNode root) {
        if (root == null) return 0;

        int l = Math.max(0, solve(root.left));
        int r = Math.max(0, solve(root.right));

        res = Math.max(res, root.val + l + r);

        return root.val + Math.max(l, r);
    }
}
```

---

# One-line takeaway
At each node, ignore negative branches, update the answer with `root + left + right`, and return `root + max(left, right)` upward.
