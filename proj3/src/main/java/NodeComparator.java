import java.util.Comparator;
import java.util.HashMap;

public class NodeComparator implements Comparator<Long> {
    private GraphDB g;
    private long t;
    private HashMap<Long, Double> dist;


    public NodeComparator(GraphDB graph, long target, HashMap<Long, Double> best) {
        g = graph;
        t = target;
        dist = best;
    }


    @Override
    public int compare(Long n1, Long n2) {
        double gap = dist.get(n1) + g.distance(n1, t) - dist.get(n2) - g.distance(n2, t);
        return Double.compare(gap, 0.0);
    }
}
