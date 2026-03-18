# Intuition
The depth of a binary tree is the number of nodes along the longest path from the root to a leaf.

At every node:
- The depth depends on the **maximum depth of its left and right subtree**
- So we recursively compute both and take the maximum.

---

# Approach
1. Use **recursion (DFS)** to compute height.
2. For each node:
   - Recursively compute left subtree height
   - Recursively compute right subtree height
   - Return:
     ```
     1 + max(left, right)
     ```
3. Base case:
   - If node is `null`, return `0`.

---

# Complexity

- Time complexity:
  O(n)

Each node is visited once.

- Space complexity:

O(h)

where `h` is the height of the tree (recursion stack).
- Worst case: `O(n)` (skewed tree)
- Best case: `O(log n)` (balanced tree)

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
  public int maxDepth(TreeNode root) 
  {
      return height(root);    
  }

  int height(TreeNode root)
  {
      if(root == null) return 0;

      int left = height(root.left);
      int right = height(root.right);

      return 1 + Math.max(left, right);
  }
}
```

# One-line takeaway
Tree depth = 1 + max(depth of left subtree, depth of right subtree)

  
