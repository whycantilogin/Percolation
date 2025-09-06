import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int openSites; // number of open sites
    private double[] fractions; // holds percolation probabilities
    private int trials; // number of trials

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid inputs.");
        }
        this.trials = trials;
        fractions = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int randomRow = StdRandom.uniformInt(0, n);
                int randomCol = StdRandom.uniformInt(0, n);

                // random site must be blocked (closed)
                // so keep generating random site until grid[random_site]==false
                // while (p.grid[random_row * n + random_col]) {
                while (p.isOpen(randomRow,randomCol)) {
                    randomRow = StdRandom.uniformInt(0, n);
                    randomCol = StdRandom.uniformInt(0, n);
                }
                // open the site
                p.open(randomRow, randomCol);
            }

            // StdOut.printf("percolated");
            openSites = p.numberOfOpenSites();
            // StdOut.printf("# of open sites: %d", openSites);

            // fraction of sites that are opened when system percolates
            // is an estimate of the percolation threshold p*
            fractions[i] = (double) openSites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    // z score for 95% confidence is 1.96
    public double confidenceLow() {
        return mean() - 1.96 * (stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    // z score for 95% confidence is 1.96
    public double confidenceHigh() {
        return mean() + 1.96 * (stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        Stopwatch stopwatch=new Stopwatch();
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.printf("%-17s= %f\n", "mean()", ps.mean());
        StdOut.printf("%-17s= %f\n", "stddev()", ps.stddev());
        StdOut.printf("%-17s= %f\n", "confidenceLow()", ps.confidenceLow());
        StdOut.printf("%-17s= %f\n", "confidenceHigh()", ps.confidenceHigh());
//        StdOut.printf("%-17s= %f\n", "elapsed time", ps.total_runtime/T);
         StdOut.printf("%-17s= %f\n", "elapsed time", stopwatch.elapsedTime());
    }
}
