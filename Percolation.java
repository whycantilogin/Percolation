import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    public boolean[] grid;
    public int n;
//     private QuickFindUF qf;
    private WeightedQuickUnionUF wqu;
    private int opens;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        opens = 0;

        grid = new boolean[n * n + 2]; // one extra for the additional top (n*nth element)
        // another for the virtual bottom (n*n+1th)
//        qf = new QuickFindUF(this.n * this.n + 2);
        wqu = new WeightedQuickUnionUF(this.n * this.n +2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return; // don't do anything if site already open

        opens++; // update the number of open sites
        // open the site:
        grid[row * n + col] = true;

        // if top row, connect to additional top
        if (row == 0) {
            wqu.union(n*n, row*n + col);
//             qf.union(n * n, row * n + col);
        }

        // if bottom row, connect to additional bottom
        if (row == n - 1) {
            wqu.union(n*n+1, row * n + col);
//             qf.union(n * n + 1, row * n + col);
        }

        // checking if up, down, left, right site are open
        // if so, take the union (combine sets)
        if (validSite(row, col - 1) && isOpen(row, col - 1)) {
            wqu.union(row * this.n + col, row * this.n + col - 1);
//             qf.union(row * this.n + col, row * this.n + col - 1);
        }
        if (validSite(row, col + 1) && isOpen(row, col + 1)) {
            wqu.union(row * this.n + col, row * this.n + col + 1);
//             qf.union(row * this.n + col, row * this.n + col + 1);
        }
        if (validSite(row + 1, col) && isOpen(row + 1, col)) {
            wqu.union(row * this.n + col, (row + 1) * this.n + col);
//             qf.union(row * this.n + col, (row + 1) * this.n + col);
        }
        if (validSite(row - 1, col) && isOpen(row - 1, col)) {
            wqu.union(row * this.n + col, (row - 1) * this.n + col);
//             qf.union(row * this.n + col, (row - 1) * this.n + col);
        }

    }

    // checks if row and col refer to a valid site on the grid
    public boolean validSite(int row, int col) {
        return (row >= 0 && row < this.n && col >= 0 && col < this.n);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row * n + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return wqu.connected(n * n, row * n + col);
//         return qf.connected(n * n, row * n + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opens;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqu.connected(n * n, n * n + 1);
//         return qf.connected(n * n, n * n + 1); // check if additional top and additional bottom connect
    }

    // unit testing
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        for (int trials = 0; trials < T; trials++) {
            Percolation p = new Percolation(n);
            boolean bool = p.percolates();
            if (bool) StdOut.print("percolates");
            else StdOut.print("does not percolate");
        }
    }
}
