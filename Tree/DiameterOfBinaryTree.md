# Problem
- Title: 543. Diameter of Binary Tree
- Source link: [LeetCode](https://leetcode.com/problems/diameter-of-binary-tree/description/)
- Description:
  Given the root of a binary tree, return the length of the diameter of the tree.

  The diameter of a binary tree is the length of the longest path between any two nodes in the tree. This path may or may not pass through the root.

- Example:
  Input: `root = [1,2,3,4,5]`
  Output: `3`
  Explanation: One longest path is `[4,2,1,3]`, which has `3` edges.

- Constraints:
  - The number of nodes in the tree is in the range `[1, 10^4]`
  - `-100 <= Node.val <= 100`

---

# Intuition
For every node, the longest path passing through that node is:

```text
height(left subtree) + height(right subtree)
```

because the path can go down to the deepest node in the left subtree and the deepest node in the right subtree.

So while computing height using DFS, we can also update the best diameter seen so far.

This works because:
- height tells us the longest downward path from a node
- diameter at a node is the sum of the left and right heights
- the global answer is the maximum such value across all nodes

---

# Approach
1. Use a recursive DFS function that returns the height of the current subtree.
2. For each node, recursively compute:
   - `l = height(root.left)`
   - `r = height(root.right)`
3. The diameter passing through the current node is `l + r`.
4. Update the global answer with `max(ans, l + r)`.
5. Return the subtree height as `1 + max(l, r)`.

---

# Complexity

- Time complexity:
  `O(n)`

Each node is visited exactly once.

- Space complexity:
  `O(h)`

`h` is the height of the tree due to recursion stack. In the worst case of a skewed tree, this becomes `O(n)`.

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
    int ans;

    public int diameterOfBinaryTree(TreeNode root) {
        ans = 0;
        solve(root);
        return ans;
    }

    int solve(TreeNode root) {
        if (root == null) return 0;

        int l = solve(root.left);
        int r = solve(root.right);

        ans = Math.max(ans, l + r);
        return 1 + Math.max(l, r);
    }
}
```

---

# One-line takeaway
Compute subtree heights with DFS, and at each node update the diameter using `leftHeight + rightHeight`.
