package hw4.puzzle;

import java.util.Comparator;

public class SearchNodeComparator implements Comparator<SearchNode> {
    @Override
    public int compare(SearchNode w1, SearchNode w2) {
        return w1.getDistance() - w2.getDistance();
    }
}
