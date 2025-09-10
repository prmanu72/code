class LRUCache {
    int capacity;
    Node left;
    Node right;
    Map<Integer, Node> map;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        left = new Node(0, 0);
        right = new Node(0, 0);
        left.next = right;
        right.prev = left;
        map = new HashMap<>(); 
    }
    
    public int get(int key) {
        if(map.containsKey(key))
        {
            Node node = map.get(key);
            this.remove(node);
            this.insert(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key))
        {
            this.remove(map.get(key));
        }
        Node newNode = new Node(key, value);
        map.put(key, newNode);
        this.insert(newNode);
         if(map.size() > this.capacity)
        {
            Node lru = left.next;
            this.remove(lru);
            map.remove(lru.key);
        }
    }
    
    public void insert(Node node)
    {
        node.prev = right.prev;
        right.prev.next = node;
        node.next = right;
        right.prev = node;
    }

    public void remove(Node node)
    {
        Node prev = node.prev;
        Node next= node.next;
        prev.next = next;
        next.prev = prev;
    }
}
public class Node
    {
        int key, val;
        Node prev, next;
        Node(int k, int v)
        {
            this.key = k;
            this.val = v;
        }
    }
