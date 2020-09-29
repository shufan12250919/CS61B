package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF set;
    private boolean[][] grid;
    private int open;
    private int len;
    private int top; //top point for the set
    private int bot; //bottom point for the set

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        len = N;
        set = new WeightedQuickUnionUF(N * N + 2);
        top = N * N;
        bot = N * N + 1;
        grid = new boolean[N][N];
        open = 0;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > len || col < 0 || col > len) {
            throw new java.lang.IndexOutOfBoundsException("Out of range!!!");
        }
        if (grid[row][col]) {
            return; // if the grid is already opened
        }
        open++;
        grid[row][col] = true;
        //connect to top
        if (row == 0) {
            set.union(col, top);
        }

        connect(row, col);

        //if percolate then connect to bot
        if (row == len - 1 && set.connected(convert(row, col), bot)) {
            set.union(convert(row, col), bot);
        }
    }

    // convert 2d position to 1d for set
    private int convert(int row, int col) {
        return row * len + col;
    }

    //connect the surrounding sites
    private void connect(int row, int col) {
        int pos = convert(row, col);
        if (row - 1 > -1) {
            if (isOpen(row - 1, col)) {
                int up = convert(row - 1, col);
                set.union(pos, up);
            }
        }
        if (row + 1 < len) {
            if (isOpen(row + 1, col)) {
                int down = convert(row + 1, col);
                set.union(pos, down);
            }
        }
        if (col - 1 > -1) {
            if (isOpen(row, col - 1)) {
                int left = convert(row, col - 1);
                set.union(pos, left);
            }
        }
        if (col + 1 < len) {
            if (isOpen(row, col + 1)) {
                int right = convert(row, col + 1);
                set.union(pos, right);
            }
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > len || col < 0 || col > len) {
            throw new java.lang.IndexOutOfBoundsException("Out of range!!!");
        }
        return grid[row][col];
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > len || col < 0 || col > len) {
            throw new java.lang.IndexOutOfBoundsException("Out of range!!!");
        }
        int pos = convert(row, col);
        return set.connected(pos, top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return set.connected(top, bot);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
