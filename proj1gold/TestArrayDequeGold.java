import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestArrayDequeGold {
    private static String message = "";

    @Test
    public void test() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> answer = new ArrayDequeSolution<>();

        for (int i = 0; i < 1000; i++) {
            double type = StdRandom.uniform();
            if (type < 0.25) {
                removeFirst(student, answer);
            } else if (type < 0.5) {
                removeLast(student, answer);
            } else if (type < 0.75) {
                addFirst(student, answer);
            } else {
                addLast(student, answer);
            }
        }


    }

    private void removeFirst(StudentArrayDeque<Integer> s, ArrayDequeSolution<Integer> a) {
        Integer expected = a.removeFirst();
        Integer actual = s.removeFirst();
        if (expected == null || actual == null) {
            return;
        }
        message = message + "\nremoveFirst()";
        assertEquals(message, expected, actual);

    }

    private void removeLast(StudentArrayDeque<Integer> s, ArrayDequeSolution<Integer> a) {
        Integer expected = a.removeLast();
        Integer actual = s.removeLast();
        if (expected == null || actual == null) {
            return;
        }
        message = message + "\nremoveLast()";
        assertEquals(message, expected, actual);
    }

    private void addFirst(StudentArrayDeque<Integer> s, ArrayDequeSolution<Integer> a) {
        int num = StdRandom.uniform(0, 100);
        s.addFirst(num);
        a.addFirst(num);
        message = message + "\naddFirst(" + num + ")";
    }

    private void addLast(StudentArrayDeque<Integer> s, ArrayDequeSolution<Integer> a) {
        int num = StdRandom.uniform(0, 100);
        s.addLast(num);
        a.addLast(num);
        message = message + "\naddLast(" + num + ")";
    }


}
