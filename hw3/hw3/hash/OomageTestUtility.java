package hw3.hash;

import java.util.HashMap;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int N = oomages.size();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            map.put(bucketNum, map.getOrDefault(bucketNum, 0) + 1);
        }
        for (int n : map.keySet()) {
            int ooInBucket = map.get(n);
            if (ooInBucket < (N / 50) || ooInBucket > (N / 2.5)) {
                return false;
            }
        }
        return true;
    }
}
