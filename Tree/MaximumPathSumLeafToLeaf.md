# Problem
- Title: Maximum Path Sum: Leaf to Leaf
- Source link: [GeeksforGeeks](https://www.geeksforgeeks.org/problems/maximum-path-sum/1)
- Description:
  Given a binary tree where each node contains an integer value, find the maximum possible path sum from one special node to another special node.

  A special node is a node connected to exactly one different node.

- Example:
  Input: `root = [3, 4, 5, -10, 4, N, N]`
  Output: `16`
  Explanation: The best special-node-to-special-node path is `4 -> 4 -> 3 -> 5`, with sum `16`.

- Constraints:
  - `2 <= number of nodes <= 10^4`
  - `-10^3 <= node->data <= 10^3`

---

# Intuition
This is not the same as the usual "maximum path sum between any two nodes" problem.

Here the path must start at one special node and end at another special node.

In a binary tree, that means:
- usually the path is leaf to leaf
- but we must be careful with nodes that have only one child, because a valid path through such a node cannot connect two different special nodes through both sides

The key observation is:

1. To build a valid special-node-to-special-node path through a node, that node must have both a left child and a right child.
2. If both children exist, then:
   - the best path coming from the left subtree gives one endpoint
   - the best path coming from the right subtree gives the other endpoint
   - so the path through the current node is:

```text
leftRootToLeafSum + root.data + rightRootToLeafSum
```

3. If a node has only one child, it cannot form a complete special-node-to-special-node path through itself, so we only return the best root-to-special-node sum upward.

So during DFS, each node does two jobs:
- return the best sum from itself down to a special node
- update the global answer only when both children exist

That is the core idea.

---

# Approach
1. Use DFS to compute the best root-to-special-node sum for each node.
2. Base case:
   - if the node is `null`, return `0`
   - if the node is a leaf, return its value
3. Recursively compute left and right sums.
4. If both children exist:
   - update the global answer with `left + right + root.data`
   - return `root.data + max(left, right)` to the parent
5. If only one child exists:
   - do not update the global answer here
   - return the current node value plus the existing child path
6. At the end:
   - if the root has both children, return the global answer
   - otherwise return the best root-to-special-node sum

---

# Complexity

- Time complexity:
  `O(n)`

Each node is visited exactly once.

- Space complexity:
  `O(h)`

`h` is the height of the tree because of recursion stack. In the worst case of a skewed tree, this becomes `O(n)`.

---

# Code
```java
class Solution {
    int res;

    int maxPathSum(Node root) {
        res = Integer.MIN_VALUE;
        int val = solve(root);

        if (root.left == null || root.right == null) {
            return Math.max(res, val);
        }

        return res;
    }

    int solve(Node root) {
        if (root == null) return 0;

        if (root.left == null && root.right == null) {
            return root.data;
        }

        int l = solve(root.left);
        int r = solve(root.right);

        if (root.left != null && root.right != null) {
            res = Math.max(res, l + r + root.data);
            return root.data + Math.max(l, r);
        }

        return root.left == null ? root.data + r : root.data + l;
    }
}
```

---

# One-line takeaway
For leaf-to-leaf maximum path sum, return the best root-to-leaf sum upward, but update the global answer only at nodes that have both children.
