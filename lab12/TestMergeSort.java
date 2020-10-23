import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMergeSort {

    @Test
    public void testMakeSingleItemQueues() {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        Queue<Queue<String>> singleQueue = MergeSort.makeSingleItemQueues(students);
        for (Queue<String> q : singleQueue) {
            assertEquals(q.dequeue(), students.dequeue());
        }
    }

    @Test
    public void testMergeSortedQueues() {
        Queue<String> students1 = new Queue<String>();
        students1.enqueue("Alice");
        students1.enqueue("Vanessa");
        Queue<String> students2 = new Queue<String>();
        students2.enqueue("Ethan");
        Queue<String> q = MergeSort.mergeSortedQueues(students1, students2);
        String expect = "Alice Ethan Vanessa ";
        String actual = q.toString();
        assertEquals(expect, actual);
    }

    @Test
    public void testMergeSort() {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        String expect = "Alice Ethan Vanessa ";
        Queue<String> sortedstudents = MergeSort.mergeSort(students);
        String actual = sortedstudents.toString();
        assertEquals(expect, actual);
    }
}
