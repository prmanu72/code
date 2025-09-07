class Solution {
    public int characterReplacement(String s, int k) {
        int ans = 0, n = s.length();
        int[] freq = new int[26];
        int l = 0, r = 0;
        while(r < n && l <= r)
        {
            char c = s.charAt(r);
            freq[c - 'A']++;
            while(r-l+1 - getMax(freq) > k)
            {
                freq[s.charAt(l)-'A']--;
                l++;
            }
            ans = Math.max(ans, r-l+1);
            r++;
        }
        return ans;
    }
    static int getMax(int[] arr)
    {
        return Arrays.stream(arr).max().getAsInt();
    }
}
