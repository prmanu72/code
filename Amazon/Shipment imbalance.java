import java.util.*;

public class Solution {

    public static long countPossibleSegments(int k, int[] weights) {
        int n = weights.length;
        long count = 0;

        // Deque for min and max
        Deque<Integer> minDeque = new ArrayDeque<>();
        Deque<Integer> maxDeque = new ArrayDeque<>();

        int left = 0;

        for (int right = 0; right < n; right++) {
            // Maintain minDeque (increasing order)
            while (!minDeque.isEmpty() && weights[minDeque.peekLast()] >= weights[right]) {
                minDeque.pollLast();
            }
            minDeque.offerLast(right);

            // Maintain maxDeque (decreasing order)
            while (!maxDeque.isEmpty() && weights[maxDeque.peekLast()] <= weights[right]) {
                maxDeque.pollLast();
            }
            maxDeque.offerLast(right);

            // Shrink window if condition violated
            while (weights[maxDeque.peekFirst()] - weights[minDeque.peekFirst()] > k) {
                if (minDeque.peekFirst() == left) minDeque.pollFirst();
                if (maxDeque.peekFirst() == left) maxDeque.pollFirst();
                left++;
            }

            // All subarrays ending at `right` with valid window
            count += (right - left + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int k = 3;
        int[] weights = {1, 3, 6};
        System.out.println(countPossibleSegments(k, weights)); // Output: 5
    }
}
