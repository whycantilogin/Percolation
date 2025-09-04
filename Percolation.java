import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private boolean[] grid;
    private int n;
    private QuickFindUF qf;
    private int opens;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        opens = 0;

        grid = new boolean[n*n + 2]; // one extra for the virtual top (n*nth element)
        // another for the virtual bottom (n*n+1th)
        qf = new QuickFindUF(this.n*this.n + 2);

        /*
        for (int i = 0; i < n * n; i++) grid[i] = -1;
        grid[n * n] = n * n;
        grid[n * n + 1] = n * n + 1;
        */

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return; // don't do anything if site already open

        opens++; // update the number of open sites
        // open the site:
        grid[row * n + col] = true;

        // if top row, connect to virtual top
        if (row == 0) {
            qf.union(n,row*n+col);
            //qf.union(n * n, row * n + col);
        }

        // if bottom row, connect to virtual bottom
        if (row == n - 1) {
            qf.union(n+1,row*n+col);
            //qf.union(n * n + 1, row * n + col);
        }

        // checking if up, down, left, right site are open
        // if so, take the union (combine sets)
        if (validSite(row, col - 1) && isOpen(row, col - 1)) {
            qf.union(row * this.n + col, row * this.n + col - 1);
        }
        else if (validSite(row, col + 1) && isOpen(row, col + 1)) {
            qf.union(row * this.n + col, row * this.n + col + 1);
        }
        else if (validSite(row + 1, col) && isOpen(row + 1, col)) {
            qf.union(row * this.n + col, (row + 1) * this.n + col);
        }
        else if (validSite(row - 1, col) && isOpen(row - 1, col)) {
            qf.union(row * this.n + col, (row - 1) * this.n + col);
        }

    }

    // checks if row and col refer to a valid site on the grid
    public boolean validSite(int row, int col) {
        return (row >= 0 && row < this.n && col >= 0 && col < this.n);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        StdOut.printf("row: %d, col: %d\n",row,col);
        return grid[row * n + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        //StdOut.printf("row: %d, col: %d\n",row,col);
        return qf.connected(n,row*n+col);
        //return qf.connected(n * n, row * n + col);

        /*
        // check if find(site) == find(virtual top)
        if (qf.find(grid[row * side_length + col]) == qf.find(side_length)) {
            // there is a connected path of open sites between top and site
            return true;
        }
        return false;
        */
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opens;
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.connected(n,n+1);
        //return qf.connected(n * n, n * n + 1); // check if virtual top and virtual bottom connect
    }

    // unit testing
    public static void main(String[] args) {
       /* int grid_size = Integer.parseInt(args[0]);
        Percolation p = new Percolation(grid_size);
        for (int i = 1; i < args.length; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            StdOut.printf("row: %d, col: %d \n", row, col);
            p.open(row, col);
            if (p.percolates()) StdOut.print("percolates");
        }*/
    }
}
