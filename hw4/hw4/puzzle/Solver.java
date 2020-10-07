package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver {
    private int searchitems;
    private Queue<WorldState> q;

    public Solver(WorldState initial) {
        searchitems = 0;

        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        pq.insert(new SearchNode(initial));
        while (!pq.isEmpty()) {
            searchitems++;
            SearchNode search = pq.delMin();
            WorldState current = search.getCurrent();
            if (current.isGoal()) {
                q = search.getParents();
                q.enqueue(current);
                break;
            }
            Queue<WorldState> parents = search.getParents();
            for (WorldState worldState : current.neighbors()) {
                if (!isParents(parents, worldState)) {
                    SearchNode node = new SearchNode(worldState, search);
                    pq.insert(node);
                }
            }
        }


    }

    private boolean isParents(Queue<WorldState> parents, WorldState w) {
        for (WorldState worldState : parents) {
            if (w.equals(worldState)) {
                return true;
            }
        }
        return false;
    }


    public int moves() {
        return q.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return q;
    }

    public int getSearchitems() {
        return searchitems;
    }
}
