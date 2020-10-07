package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board implements WorldState {
    private static final int BLANK = 0;
    private int[][] tiles;
    private int blankrow;
    private int blankcol;

    public Board(int[][] t) {
        this.tiles = new int[t.length][t.length];
        copuytiles(t);
        findBlank();
    }

    private void copuytiles(int[][] t) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                this.tiles[i][j] = t[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (!valid(i, j)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    private void findBlank() {
        int N = size();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (tileAt(r, c) == BLANK) {
                    blankrow = r;
                    blankcol = c;
                    return;
                }
            }
        }
    }

    public int size() {
        return tiles.length;
    }

    private boolean valid(int r, int c) {
        if (r < 0 || r >= size()) {
            return false;
        }
        return c >= 0 && c < size();
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int N = size();

        int[][] template = new int[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                template[r][c] = tileAt(r, c);
            }
        }

        int[][] ways = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < ways.length; i++) {
            int r = blankrow + ways[i][0];
            int c = blankcol + ways[i][1];
            if (valid(r, c)) {
                template[blankrow][blankcol] = template[r][c];
                template[r][c] = BLANK;
                Board neighbor = new Board(template);
                neighbors.enqueue(neighbor);
                template[r][c] = template[blankrow][blankcol];
                template[blankrow][blankcol] = BLANK;
            }
        }
        return neighbors;
    }

    public int hamming() {
        int hamming = 0;
        int N = size();
        int start = 1;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (start == N * N) {
                    break;
                }
                if (tiles[r][c] != start) {
                    hamming++;
                }
                start++;
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        int N = size();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int correct = r * N + c + 1;
                if (tiles[r][c] != BLANK && tiles[r][c] != correct) {
                    int correctR = (tiles[r][c] - 1) / N;
                    int correctC = (tiles[r][c] - 1) % N;
                    manhattan += Math.abs(r - correctR) + Math.abs(c - correctC);
                }
            }
        }
        return manhattan;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board b = (Board) y;
        if (this.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] != b.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
