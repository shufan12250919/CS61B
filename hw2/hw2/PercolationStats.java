package hw2;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] exp;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("N and T need to be positive!");
        }
        exp = new double[T];
        int area = N * N;
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            int time = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(N) + 1;
                int col = StdRandom.uniform(N) + 1;
                p.open(row, col);
                time++;
            }
            exp[i] = time * 1.0 / area;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0.0;
        for (double x : exp) {
            sum += x;
        }
        return sum / exp.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = mean();
        double sum = 0.0;
        for (double x : exp) {
            sum += ((x - mean) * (x - mean));
        }
        double dev = sum / (exp.length - 1);
        return Math.sqrt(dev);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double mean = mean();
        double dev = stddev();
        return mean - 1.96 * dev / Math.sqrt(exp.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double mean = mean();
        double dev = stddev();
        return mean + 1.96 * dev / Math.sqrt(exp.length);
    }
}
