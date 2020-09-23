public class MyArrayDequeTest {
    public static void main(String[] args) {
        System.out.println("Test started!");
        ArrayDeque<Integer> d = new ArrayDeque<>();
        for (int i = 0; i < 20; i++) {
            d.addLast(i);
        }
        d.printDeque();
        for (int i = 0; i < 100; i++) {
            d.removeFirst();
        }


        d.printDeque();
        d.addLast(1);
        d.printDeque();
        d.addFirst(2);
        d.printDeque();
        System.out.println("Test completed!");
    }
}
