// using PriorityQueue
class Solution {
    public int[] topKFrequent(int[] nums, int k) 
    {
        Map<Integer, Integer> freq = new HashMap<>();
        for(int i : nums)
        {
            freq.put(i, freq.getOrDefault(i, 0) + 1);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);

        for(Map.Entry<Integer, Integer> entry: freq.entrySet())
        {
            pq.offer(new int[]{entry.getValue(), entry.getKey()});
            if(pq.size() > k)
            {
                pq.poll();
            }
        }

        int[] res = new int[k];

        for(int i = 0; i < k; i++)
        {
            int[] ar = pq.poll();
            res[i] = ar[1];
        }
        return res;
    }
}
// using bucket sort
class Solution {
    public int[] topKFrequent(int[] nums, int k) 
    {
        Map<Integer, Integer> freq = new HashMap<>();
        for(int i : nums)
        {
            freq.put(i, freq.getOrDefault(i, 0) + 1);
        }

        List<Integer>[] buckets = new List[nums.length + 1];
        for(int i =0; i< buckets.length; i++)
        {
            buckets[i] = new ArrayList<Integer>();
        }

        for(Map.Entry<Integer, Integer> entry: freq.entrySet())
        {
            buckets[entry.getValue()].add(entry.getKey());
        }

        int[] res = new int[k];
        int index = 0;
        for(int i = buckets.length-1; i > 0 && index < k; i--)
        {
            for(int it: buckets[i])
            {
                res[index++] = it;
            }
        }
        return res;
    }
}
