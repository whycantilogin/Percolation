import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // private boolean[] grid;
    private Percolation p;
    private static Stopwatch stopwatch;
    private int open_sites;
    private double[] fractions;
    private double total_runtime;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("invalid inputs.");
        this.trials = trials;
        fractions = new double[trials];
        total_runtime = 0.0;
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            stopwatch = new Stopwatch();
            while (!p.percolates()) {
                int random_row = StdRandom.uniformInt(0, n);
                int random_col = StdRandom.uniformInt(0, n);

                // random site must be blocked (closed)
                // so keep generating random site until grid[random_site]==false
                while (p.grid[random_row * n + random_col]) {
                    random_row = StdRandom.uniformInt(0, n);
                    random_col = StdRandom.uniformInt(0, n);
                }
                // open the site
                p.open(random_row, random_col);
            }
            total_runtime += stopwatch.elapsedTime();

            // StdOut.printf("percolated");
            open_sites = p.numberOfOpenSites();
            // StdOut.printf("# of open sites: %d", open_sites);

            // fraction of sites that are opened when system percolates
            // is an estimate of the percolation threshold p*
            fractions[i] = (double) open_sites / (n * n);
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
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, T);
        StdOut.printf("%-17s= %f\n", "mean()", ps.mean());
        StdOut.printf("%-17s= %f\n", "stddev()", ps.stddev());
        StdOut.printf("%-17s= %f\n", "confidenceLow()", ps.confidenceLow());
        StdOut.printf("%-17s= %f\n", "confidenceHigh()", ps.confidenceHigh());
        StdOut.printf("%-17s= %f\n", "elapsed time", ps.total_runtime/T);
//         StdOut.printf("%-17s= %f\n", "elapsed time", ps.total_runtime);
    }
}
