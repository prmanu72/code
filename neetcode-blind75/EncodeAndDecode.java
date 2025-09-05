class Solution {

    public String encode(List<String> strs) 
    {
        int n = strs.size();
        if(n == 0)
        {
            return "";
        }
        int[] sizeArr = new int[n];
        for(int i = 0; i < n; i++)
        {
            sizeArr[i] = strs.get(i).length();
        }
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < n-1; i++)
        {
            result.append(sizeArr[i]);
            result.append(",");
        }
        result.append(sizeArr[n-1]);

        result.append("#");
        for(String s: strs)
        {
            result.append(s);
        }
        return result.toString();
    }

    public List<String> decode(String str) 
    {
        int index = str.indexOf("#");
        if(index == -1)
        {
            return new ArrayList<>();
        }
        String numeric = str.substring(0, index);
        String words = str.substring(index + 1);

        List<Integer> sizes = Arrays.stream(numeric.split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
        
        int n = sizes.size();
        List<String> res= new ArrayList<>();
        
        for(int i = 0, start = 0; i < n; i++)
        {
            res.add(words.substring(start, start + sizes.get(i)));
            start = start + sizes.get(i);
        }
        return res;
    }
}
