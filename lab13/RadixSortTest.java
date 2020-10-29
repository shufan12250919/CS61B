import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class RadixSortTest {
    @Test
    public void testRadixSort() {
        String[] test = new String[]{"bac", "abc", "cda"};
        String[] actual = RadixSort.sort(test);
        String[] expect = new String[]{"abc", "bac", "cda"};
        assertArrayEquals(expect, actual);
    }

    @Test
    public void testRadixSort2() {
        String[] test = new String[]{"abc", "z", "cda", "  ", "c"};
        String[] actual = RadixSort.sort(test);
        String[] expect = new String[]{"abc", "z", "cda", "  ", "c"};
        Arrays.sort(expect);
        System.out.println(Arrays.toString(expect));
        assertArrayEquals(expect, actual);
    }


}
