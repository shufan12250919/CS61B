package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;


    private class Vertex implements Comparable {
        private int index;
        private int estimatedDistance;

        private Vertex(int v, int dist) {
            index = v;
            estimatedDistance = h(v) + dist;
        }

        @Override
        public int compareTo(Object o) {
            Vertex n = (Vertex) o;
            return this.estimatedDistance - n.estimatedDistance;
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /**
     * Finds vertex estimated to be closest to target.
     */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /**
     * Performs an A star search from vertex s.
     */
    private void astar(int s) {
        MinPQ<Vertex> priorityQueue = new MinPQ<>();
        priorityQueue.insert(new Vertex(s, distTo[s]));
        while (!priorityQueue.isEmpty()) {
            Vertex next = priorityQueue.delMin();
            marked[next.index] = true;
            announce();
            if (next.index == t) {
                return;
            }
            for (int w : maze.adj(next.index)) {
                //check the currnt next->w distance is a shorter than old distance to w
                if (distTo[w] > distTo[next.index] + 1) {
                    edgeTo[w] = next.index;
                    distTo[w] = distTo[next.index] + 1;
                }
                // marked means already checked the best way
                if (!marked[w]) {
                    priorityQueue.insert(new Vertex(w, distTo[w]));
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

