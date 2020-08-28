package synthesizer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(2);
        arb.enqueue(3);
        int first = arb.dequeue();
        Assert.assertEquals(2, first);
        int second = arb.peek();
        Assert.assertEquals(3, second);
        Assert.assertFalse(arb.isEmpty());
        arb.enqueue(6);
        Assert.assertEquals(2, arb.fillCount());
    }

    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}
