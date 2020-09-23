public class MyLinkedListDequeTest {

    public static void main(String[] args) {
        System.out.println("Test started!");
        LinkedListDeque<Integer> d = new LinkedListDeque<Integer>();
        d.addFirst(3);
        System.out.println(d.get(0));
        d.printDeque();
    }
}
