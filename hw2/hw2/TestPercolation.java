package hw2;

public class TestPercolation {
    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats p = new PercolationStats(20, 10, pf);
        System.out.println("Mean: " + p.mean());
        System.out.println("Stddev: " + p.stddev());
        System.out.println("Low conf: " + p.confidenceLow());
        System.out.println("High conf: " + p.confidenceHigh());
    }
}
