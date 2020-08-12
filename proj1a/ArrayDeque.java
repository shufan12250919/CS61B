public class ArrayDeque<T> {
    private T[] arr;
    private int size;
    private int first;
    //always insert first element at index first
    //the real first element will be at arr[first + 1] (need to deal with index out of bound)
    private int last;
    //always insert last element at index last


    public ArrayDeque() {
        arr = (T[]) new Object[8];
        size = 0;
        first = 0;
        last = 0;
    }

    public void addLast(T item) {
        resize();
        if (size == 0) {
            if (first == 0) {
                first = arr.length - 1;
            } else {
                first = last - 1;
            }
        }
        arr[last] = item;

        if (last == arr.length - 1) {
            last = 0;
        } else {
            last++;
        }

        size++;
    }

    private void resize() {
        if (size == arr.length) {
            T[] bigger = (T[]) new Object[arr.length * 2];
            if (first == arr.length - 1) {
                System.arraycopy(arr, 0, bigger, 0, size);
                first = bigger.length - 1;
                last = size;
            } else {
                int index = first + 1;
                int half = arr.length - index;
                System.arraycopy(arr, 0, bigger, 0, size - half);
                System.arraycopy(arr, index, bigger, bigger.length - half, half);
                first = bigger.length - half - 1;
                last = size - half;
            }


            arr = bigger;
        }
    }

    public void addFirst(T item) {
        resize();
        if (size == 0) {
            if (first == arr.length - 1) {
                last = 0;
            } else {
                last = first + 1;
            }
        }
        arr[first] = item;


        if (first == 0) {
            first = arr.length - 1;
        } else {
            first--;
        }


        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int num = 0;
        int index = first + 1;
        if (index > arr.length) {
            index = 0;
        }

        while (num != size) {
            if (index >= arr.length) {
                index = 0;
            }
            System.out.print(arr[index] + " ");
            index++;
            num++;
        }
        System.out.println();
    }


    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        removal();
        size--;
        if (first == arr.length - 1) {
            first = 0;
        } else {
            first++;
        }
        if (size == 0) {
            last = first;
        }
        return arr[first];
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        removal();
        size--;
        if (last != 0) {
            last--;
        } else {
            last = arr.length - 1;
        }
        if (size == 0) {
            first = last;
        }
        return arr[last];
    }

    private void removal() {
        if (arr.length > 8 && size <= arr.length * 1 / 4) {
            T[] smaller = (T[]) new Object[arr.length * 1 / 4];
            int half = arr.length - 1 - first;
            System.arraycopy(arr, first + 1, smaller, 0, half);
            System.arraycopy(arr, 0, smaller, half, size - half);
            first = smaller.length - 1;
            last = size;
            arr = smaller;
        }
    }

    public T get(int index) {
        if (index >= size() || index < 0) {
            return null;
        }

        int cur = first + 1;
        return arr[(cur + index) % arr.length];
    }


}
