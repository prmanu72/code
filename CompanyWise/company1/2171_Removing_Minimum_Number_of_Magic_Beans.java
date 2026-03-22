import java.util.Arrays;

public class Solution {
    public long minimumRemoval(int[] beans) {
        Arrays.sort(beans);

        long total = 0;
        for (int x : beans) {
            total += x;
        }

        long maxKeep = 0;
        int n = beans.length;

        for (int i = 0; i < n; i++) {
            long keep = 1L * beans[i] * (n - i);
            maxKeep = Math.max(maxKeep, keep);
        }

        return total - maxKeep;
    }
}
