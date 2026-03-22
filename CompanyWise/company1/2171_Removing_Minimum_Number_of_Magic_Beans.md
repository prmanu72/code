# Problem
- Title: 2171. Removing Minimum Number of Magic Beans
- Source link: [LeetCode](https://leetcode.com/problems/removing-minimum-number-of-magic-beans/)
- Description:
  You are given an array of positive integers `beans`, where each integer represents the number of magic beans in a bag.
  Remove any number of beans from each bag so that every remaining non-empty bag has the same number of beans.
  Return the minimum number of beans that must be removed.

- Example:
  Input: `beans = [4,1,6,5]`
  Output: `4`
  Explanation:
  Remove beans to make the non-empty bags equal to `4`: `[4,0,4,4]`.

- Constraints:
  - `1 <= beans.length <= 10^5`
  - `1 <= beans[i] <= 10^5`

---

# Intuition
If we decide that all non-empty bags should end up with `x` beans, then every bag with fewer than `x` beans must become empty, and every bag with more than `x` beans must be reduced to `x`.

After sorting, if we choose `beans[i]` as the final target value, then every bag from `i` to `n - 1` can keep `beans[i]` beans, while all earlier bags are removed completely. So the problem becomes: maximize how many beans we can keep.

---

# Approach
1. Sort the array.
2. Compute the total number of beans across all bags.
3. For each index `i`, treat `beans[i]` as the final number of beans in each non-empty bag.
4. The number of beans we can keep in that case is `beans[i] * (n - i)`.
5. Track the maximum beans we can keep.
6. The answer is `total - maxKeep`.

---

# Complexity

- Time complexity:
  `O(n log n)`

Sorting dominates the runtime. The final scan is linear.

- Space complexity:
  `O(1)`

Aside from a few variables, no extra space is used beyond the sort implementation.

---

# Code
```java
import java.util.Arrays;

class Solution {
    public long minimumRemoval(int[] beans) {
        Arrays.sort(beans);
        long total = 0;
        for (int x : beans) total += x;

        long maxKeep = 0;
        int n = beans.length;

        for (int i = 0; i < n; i++) {
            long keep = 1L * beans[i] * (n - i);
            maxKeep = Math.max(maxKeep, keep);
        }

        return total - maxKeep;
    }
}
```

---

# One-line takeaway
Sort the bags and try each value as the final non-empty bag size, then subtract the maximum keepable beans from the total.
