import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestArrayDequeGoldOnlyOneMethod {

    @Test
    public void testSad() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ans = new ArrayDequeSolution<>();

        String output = "";

        int count = 0;
        while (true) {
            double randNum = StdRandom.uniform();

            if (randNum < 0.25) {
                sad.addFirst(count);
                ans.addFirst(count);
                output += "\naddFirst(" + count + ")";

            } else if (randNum < 0.5) {
                sad.addLast(count);
                ans.addLast(count);
                output += "\naddLast(" + count + ")";

            } else if (randNum < 0.75) {
                Integer actual = sad.removeFirst();
                Integer expected = ans.removeFirst();
                if (actual == null || expected == null) {
                    continue;
                }
                output += "\nremoveFirst()";
                assertEquals(output, expected, actual);

            } else {
                Integer actual = sad.removeLast();
                Integer expected = ans.removeLast();
                if (actual == null || expected == null) {
                    continue;
                }
                output += "\nremoveLast()";
                assertEquals(output, expected, actual);
            }

            count += 1;
        }

    }

}
