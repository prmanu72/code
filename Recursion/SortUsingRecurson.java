class Solution {
    void sortArr(int[] arr) 
    {
        List<Integer> a = new ArrayList<>();
        for(int i : arr) a.add(i);

        sort(a);
        for(int i = 0; i < arr.length; i++) arr[i] = a.get(i);
    }
    
    void sort(List<Integer> a)
    {
        if(a.size() <= 1) return;
        
        int t = a.get(a.size() - 1);
        a.removeLast();
        
        sort(a);
        
        insert(a, t);
    }
    
    void insert(List<Integer> a, int k)
    {
        if(a.size() == 0 || a.get(a.size() - 1) <= k)
        {
            a.add(k);
            return;
        }
        
        int t = a.get(a.size() - 1);
        a.removeLast();
        
        insert(a,k);
        
        a.add(t);
    }
}