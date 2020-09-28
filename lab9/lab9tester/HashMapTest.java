package lab9tester;

import lab9.MyHashMap;

public class HashMapTest {
    public static void main(String[] args) {
        MyHashMap<String, Integer> hmap = new MyHashMap<>();
        hmap.put("hello", 5);
        hmap.put("cat", 10);
        hmap.put("fish", 22);
        hmap.put("zebra", 90);
        printKeys(hmap);
        System.out.println("Remove: " + hmap.remove("cat")); // remove successfully
        printKeys(hmap);
        System.out.println("Remove: " + hmap.remove("fish"));
        printKeys(hmap);
        System.out.println("Remove: " + hmap.remove("hello"));
        printKeys(hmap);
    }

    public static void printKeys(MyHashMap<String, Integer> map) {
        for (String s : map) {
            System.out.print(s + " ");
        }

        System.out.println();
    }
}
