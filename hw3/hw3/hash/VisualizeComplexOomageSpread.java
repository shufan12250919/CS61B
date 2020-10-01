package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class VisualizeComplexOomageSpread {
    public static void main(String[] args) {
        List<Oomage> list = new ArrayList<>();
        int N = 100;
        int M = 10;
        double scale = 1.0;
        HashTableDrawingUtility.setScale(scale);
        for (int i = 0; i < N; i++) {
            list.add(ComplexOomage.randomComplexOomage());
        }
        HashTableVisualizer.visualize(list, M, scale);
    }
}
