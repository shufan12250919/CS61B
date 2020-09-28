package lab9tester;

import lab9.BSTMap;

public class BSTMapTest {
    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        printKeys(bstmap);
        System.out.println("Remove: " + bstmap.remove("cat"));
        printKeys(bstmap);
        System.out.println("Remove: " + bstmap.remove("fish"));
        printKeys(bstmap);
        System.out.println("Remove: " + bstmap.remove("hello"));
        printKeys(bstmap);
    }

    public static void printKeys(BSTMap<String, Integer> map) {
        for (String s : map) {
            System.out.print(s + " ");
        }

        System.out.println();
    }
}
