package lab11.graphs;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    // to store information of edgeTo, for not drawing out all the edges
    private int[] parent;
    private boolean cycle;

    public MazeCycles(Maze m) {
        super(m);
        parent = new int[marked.length];
    }

    @Override
    public void solve() {
        //start from 0, 0
        parent[0] = 0;
        dfs(0);
        announce();
    }

    // Helper methods go here
    private void dfs(int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            // avoid searching for another node, after returning from call stack
            if (cycle) {
                return;
            }
            if (marked[w]) {
                if (w != parent[v]) {
                    parent[w] = v; // set the edges become cycle
                    drawCycle(w);
                    cycle = true;
                    return;
                }
            } else {
                parent[w] = v;
                dfs(w);
            }
        }
    }

    //draw the cycle
    private void drawCycle(int w) {
        int v = parent[w];
        edgeTo[v] = parent[v];
        while (v != w) {
            v = parent[v];
            edgeTo[v] = parent[v];
        }
    }

}

