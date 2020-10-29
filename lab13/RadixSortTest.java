import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class RadixSortTest {
    @Test
    public void testRadixSort() {
        String[] test = new String[]{"bac", "abc", "cda"};
        String[] actual = RadixSort.sort(test);
        String[] expect = new String[]{"abc", "bac", "cda"};
        assertArrayEquals(expect, actual);
    }
}
