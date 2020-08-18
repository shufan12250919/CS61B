public class LinkedListDeque<T> implements Deque<T> {

    private class Node {
        private T data;
        private Node prev;
        private Node next;

        Node(T d, Node l, Node r) {
            data = d;
            prev = l;
            next = r;
        }

        void setPrev(Node l) {
            prev = l;
        }

        void setNext(Node r) {
            next = r;
        }
    }

    private Node sentinel;
    private int elements;

    public LinkedListDeque() {
        elements = 0;
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T item) {
        Node temp = new Node(item, sentinel, sentinel.next);
        sentinel.next.setPrev(temp);
        sentinel.setNext(temp);
        elements++;
    }

    @Override
    public void addLast(T item) {
        Node temp = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.setNext(temp);
        sentinel.setPrev(temp);
        elements++;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.prev == sentinel && sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return elements;
    }

    @Override
    public void printDeque() {
        Node cur = sentinel.next;
        while (cur != sentinel) {
            System.out.print(cur.data + " ");
            cur = cur.next;
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node temp = sentinel.next;
        sentinel.next = temp.next;
        sentinel.next.prev = sentinel;
        elements--;
        return temp.data;

    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node temp = sentinel.prev;
        sentinel.prev = temp.prev;
        sentinel.prev.next = sentinel;
        elements--;
        return temp.data;
    }

    @Override
    public T get(int index) {
        Node cur = sentinel.next;
        for (int i = 0; i < index; i++) {
            if (cur == sentinel) {
                return null;
            }
            cur = cur.next;
        }
        return cur.data;
    }

    //recursive version of the method get
    public T getRecursive(int index) {
        if (isEmpty()) {
            return null;
        }
        Node target = getNode(index, sentinel.next);
        if (target != null) {
            return target.data;
        }
        return null;
    }

    private Node getNode(int index, Node cur) {
        if (index == 0) {
            return cur;
        }
        if (cur == null) {
            return null;
        }
        index--;
        return getNode(index, cur.next);
    }

}
