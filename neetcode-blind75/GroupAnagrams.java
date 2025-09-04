 public class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> res = new HashMap<>();
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            String key = Arrays.toString(count);
            res.putIfAbsent(key, new ArrayList<>());
            res.get(key).add(s);
        }
        return new ArrayList<>(res.values());
    }
}

 class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String s: strs)
        {
            String sorted = sortString(s);
            map.computeIfAbsent(sorted, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    private String sortString(String s)
    {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}


 class Solution {
    public boolean isAnagram(String s, String t) {
        int arr[] = new int[26];
        int sn = s.length(), tn = t.length();
        if(sn != tn) return false;
        for(int i = 0; i < sn; i++)
        {
            arr[s.charAt(i)-'a'] += 1;
        }
        for(int i = 0; i < tn; i++)
        {
            arr[t.charAt(i)-'a'] -= 1;
            if(arr[t.charAt(i)-'a'] < 0) return false;
        }
        return IntStream.of(arr).sum() == 0;        
    }
    public List<List<String>> groupAnagrams(String[] strs) {
        int n = strs.length;
        List<List<String>> result = new ArrayList();
        boolean visited[] = new boolean[n];

        for(int i = 0; i < n; i++)
        {
            if(visited[i] == true) continue;

            List<String> temp = new ArrayList();
            temp.add(strs[i]);
            visited[i] = true;

            for(int j = i+1; j < n; j++)
            {
                if(isAnagram(strs[i], strs[j]))
                {
                    temp.add(strs[j]);
                    visited[j] = true;
                }
            }

            result.add(temp);
        }
        return result;

    }
}
