package hw4.puzzle;


import edu.princeton.cs.algs4.Queue;

public class SearchNode {
    private WorldState current;
    private Queue<WorldState> parents;
    private int distance;

    public SearchNode(WorldState c) {
        current = c;
        parents = new Queue<>();
        distance = c.estimatedDistanceToGoal();
    }

    public SearchNode(WorldState c, SearchNode p) {
        current = c;
        parents = p.getParents();
        parents.enqueue(p.current);
        distance = c.estimatedDistanceToGoal() + parents.size();
    }

    public int getDistance() {
        return distance;
    }

    public WorldState getCurrent() {
        return current;
    }

    public Queue<WorldState> getParents() {
        Queue<WorldState> newQ = new Queue<>();
        for (WorldState w : parents) {
            newQ.enqueue(w);
        }
        return newQ;
    }

    public int getMove() {
        return parents.size();
    }

    //hashCode and equal is same as the worldState
    @Override
    public int hashCode() {
        return current.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchNode s = (SearchNode) o;
        WorldState w = s.current;

        return current.equals(w);
    }

}
