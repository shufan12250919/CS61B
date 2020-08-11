public class MyLinkedListDequeTest {

    public static void main(String[] args) {
        System.out.println("Test started!");
        LinkedListDeque<Integer> d = new LinkedListDeque<Integer>();
        d.addFirst(0);
        d.isEmpty();
        d.addFirst(2);
        d.addFirst(3);
        d.addFirst(4);
        d.isEmpty();
        System.out.println(d.removeLast());
        d.printDeque();
    }
}
