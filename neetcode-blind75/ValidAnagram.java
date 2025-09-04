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

    public boolean isAnagram2(String s, String t) {
    if (s.length() != t.length()) return false;

    int[] count = new int[26]; // Assuming input contains only lowercase letters

    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }

    for (int c : count) {
        if (c != 0) return false;
    }

    return true;
}
public boolean isAnagram3(String s, String t) {
          Map<Character, Integer> map = new HashMap<>();

        for(int i = 0;i < s.length(); i++)
        {
            Character ch = s.charAt(i);
            int k = map.getOrDefault(ch,  0);
            map.put(ch, k+1);
        }

        for(int i = 0;i < t.length(); i++)
        {
            Character ch = t.charAt(i);
            int k = map.getOrDefault(ch,  0);
            if(k == 0)
            {
                return false;
            }
            map.put(ch, k-1);
        }
        for(Map.Entry<Character, Integer> entry : map.entrySet())
        {
            if(entry.getValue() > 0)
            {
                return false;
            }
        }
        return true;
    }
}