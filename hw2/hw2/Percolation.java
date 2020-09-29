package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final byte BLOCKED = Byte.parseByte("0"); // 00000000
    private static final byte OPEN = Byte.parseByte("1"); // 00000001
    private static final byte TOPCONNECTED = Byte.parseByte("2"); // 00000010
    private static final byte BOTTOMCONNECTED = Byte.parseByte("4"); // 00000100
    private static final byte PERCOLATED = Byte.valueOf("7"); // 00000111
    private WeightedQuickUnionUF set;
    private byte[] grid;
    private int open;
    private int len;
    private boolean percolate;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N need to be positive!!!");
        }
        len = N;
        set = new WeightedQuickUnionUF(N * N);
        grid = new byte[N * N];
        open = 0;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row >= len || col < 0 || col >= len) {
            throw new java.lang.IndexOutOfBoundsException("Out of range!!!");
        }
        int index = convert(row, col);
        if (grid[index] != BLOCKED) {
            return; // if the grid is already opened
        }
        open++;
        grid[index] = OPEN;
        //connect to top
        if (row == 0) {
            grid[index] = (byte) (grid[index] | TOPCONNECTED);
        }
        //connect to bot
        if (row == len - 1) {
            grid[index] = (byte) (grid[index] | BOTTOMCONNECTED);
        }

        connect(row, col);

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
                int rootad = set.find(up);
                grid[pos] = (byte) (grid[pos] | grid[rootad]);
                set.union(pos, up);
            }
        }
        if (row + 1 < len) {
            if (isOpen(row + 1, col)) {
                int down = convert(row + 1, col);
                int rootad = set.find(down);
                grid[pos] = (byte) (grid[pos] | grid[rootad]);
                set.union(pos, down);
            }
        }
        if (col - 1 > -1) {
            if (isOpen(row, col - 1)) {
                int left = convert(row, col - 1);
                int rootad = set.find(left);
                grid[pos] = (byte) (grid[pos] | grid[rootad]);
                set.union(pos, left);
            }
        }
        if (col + 1 < len) {
            if (isOpen(row, col + 1)) {
                int right = convert(row, col + 1);
                int rootad = set.find(right);
                grid[pos] = (byte) (grid[pos] | grid[rootad]);
                set.union(pos, right);
            }
        }

        int root = set.find(pos);
        grid[root] = (byte) (grid[pos] | grid[root]);
        if (grid[root] == PERCOLATED) {
            percolate = true;
        }


    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > len || col < 0 || col > len) {
            throw new java.lang.IndexOutOfBoundsException("Out of range!!!");
        }
        int index = convert(row, col);
        if (grid[index] != BLOCKED) {
            return true;
        }
        return false;
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > len || col < 0 || col > len) {
            throw new java.lang.IndexOutOfBoundsException("Out of range!!!");
        }
        int index = convert(row, col);
        int root = set.find(index);
        if ((byte) (grid[root] & TOPCONNECTED) == TOPCONNECTED) {
            return true;
        }
        return false;

    }

    // number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolate;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
