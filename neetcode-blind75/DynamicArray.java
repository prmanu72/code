class DynamicArray {
    int size;
    int capacity;
    int[] arr;
    public DynamicArray(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.arr = new int[capacity];
    }

    public int get(int i) {
        return arr[i];
    }

    public void set(int i, int n) {
        arr[i] = n;
    }

    public void pushback(int n) {
        if(this.size == this.capacity)
        {
            this.capacity = 2 * this.capacity;
            arr = Arrays.copyOf(arr, this.capacity);
        }
        arr[this.size] = n;
        this.size++;
    }

    public int popback() {
        this.size--;
        return arr[this.size];
    }

    private void resize() {
        this.capacity = 2 * this.capacity;
        arr = Arrays.copyOf(arr, this.capacity);
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return this.capacity;
    }
}
