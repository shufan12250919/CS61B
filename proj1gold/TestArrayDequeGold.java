import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestArrayDequeGold {
    @Test
    public void Test() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> answer = new ArrayDequeSolution<>();
        String message = "";
        for (int i = 0; i < 100; i++) {
            int type = StdRandom.uniform(1, 5);
            if (type == 1) {
                removeFirst(student, answer, message);
            } else if (type == 2) {
                removeLast(student, answer, message);
            } else if (type == 3) {
                addFirst(student, answer, message);
            } else if (type == 4) {
                addLast(student, answer, message);
            }
        }


    }

    private void removeFirst(StudentArrayDeque<Integer> s, ArrayDequeSolution<Integer> a, String m) {
        if (s.size() >= 1 && a.size() >= 1) {
            int expected = a.removeFirst();
            int actual = s.removeFirst();
            m = m + "removeFirst()\n";
            assertEquals(m, expected, actual);
        }

    }

    private void removeLast(StudentArrayDeque<Integer> s, ArrayDequeSolution<Integer> a, String m) {
        if (s.size() >= 1 && a.size() >= 1) {
            int expected = a.removeLast();
            int actual = s.removeLast();
            m = m + "removeLast()\n";
            assertEquals(m, expected, actual);
        }
    }

    private void addFirst(StudentArrayDeque<Integer> s, ArrayDequeSolution<Integer> a, String m) {
        int num = StdRandom.uniform(0, 100);
        s.addFirst(num);
        a.addFirst(num);
        m = m + "addFirst(" + num + ")\n";
    }

    private void addLast(StudentArrayDeque<Integer> s, ArrayDequeSolution<Integer> a, String m) {
        int num = StdRandom.uniform(0, 100);
        s.addLast(num);
        a.addLast(num);
        m = m + "addLast(" + num + ")\n";
    }


}
