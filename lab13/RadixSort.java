/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] sorted = new String[asciis.length];
        int max = 0;
        for (int i = 0; i < asciis.length; i++) {
            int len = asciis[i].length();
            if (len > max) {
                max = len;
            }
            sorted[i] = asciis[i];
        }
        sortHelperLSD(sorted, 0, max);
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index, int max) {
        // Optional LSD helper method for required LSD radix sort
        if (index >= max) {
            return;
        }
        int[] counts = new int[257]; // extra space for empty char
        for (String s : asciis) {
            int c = 0;
            if (index <= s.length()) {
                c = (int) s.charAt(s.length() - 1 - index) + 1;
            }
            counts[c]++;
        }
        int[] start = new int[257];
        int pos = 0;
        for (int i = 0; i < counts.length; i++) {
            start[i] = pos;
            pos += counts[i];
        }


        String[] sort = new String[asciis.length];
        for (String s : asciis) {
            int c = 0;
            if (index <= s.length()) {
                c = (int) s.charAt(s.length() - 1 - index) + 1;
            }
            sort[start[c]] = s;
            start[c]++;
        }
        System.arraycopy(sort, 0, asciis, 0, sort.length);
        index++;
        sortHelperLSD(asciis, index, max);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
