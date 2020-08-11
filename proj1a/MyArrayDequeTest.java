public class MyArrayDequeTest {
    public static void main(String[] args) {
        System.out.println("Test started!");
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < 9; i++) {
            deque.addFirst(i);
        }
        deque.printDeque();
        System.out.println(deque.get(1));
        System.out.println("Test completed!");
    }
}
