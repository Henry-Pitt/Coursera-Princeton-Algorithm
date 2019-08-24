import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openSites;
    private int N;    // create N-by-N grid
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufConnectTop;
    private int top;
    private int bottom;
    private int numberOfOpenSites;

    public Percolation(int n) {    // Create n-by-n grid, with all sites blocked
        if (n <= 1) {
            throw new IllegalArgumentException("N must be bigger than 0.");
        }
        N = n;
        top = 0;
        bottom = N * N + 1;
        openSites = new boolean[N * N + 2];
        for (int i = 1; i <= N * N; i++) {
            openSites[i] = false;    // Each site is blocked
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufConnectTop = new WeightedQuickUnionUF(N * N + 1);
        numberOfOpenSites = 0;

    }

    public void open(int row, int col) {    // open site(row, col) if it is not open already
        int index = encode2DTo1D(row, col);
        openSites[index] = true;
        if (row == 1) {
            uf.union(index, top);
            ufConnectTop.union(index, top);
        }
        if (row == N) {
            uf.union(index, bottom);
        }
        if (col > 1 && openSites[index - 1]) {
            uf.union(index, index - 1);
            ufConnectTop.union(index, index - 1);
        }
        if (col < N && openSites[index + 1]) {
            uf.union(index, index + 1);
            ufConnectTop.union(index, index + 1);
        }
        if (row > 1 && openSites[index - N]) {
            uf.union(index, index - N);
            ufConnectTop.union(index, index - N);
        }
        if (row < N && openSites[index + N]) {
            uf.union(index, index + N);
            ufConnectTop.union(index, index + N);
        }

        numberOfOpenSites++;
    }

    private void verifyRowColIndex(int row, int col) {
        // By convertion, the row and column indices are integers between 1 and n
        if (!(row >= 1 && row <= N && col >= 1 && col <= N)) {
            throw
                    new IllegalArgumentException(
                            "row and column indices are integers between 1 and n.");
        }
    }

    private int encode2DTo1D(int row, int col) {
        verifyRowColIndex(row, col);
        return (row - 1) * N + col;
    }

    public boolean isOpen(int row, int col) {   // is site(row, col) open?
        int index = encode2DTo1D(row, col);
        return openSites[index];
    }

    public boolean isFull(int row, int col) {   // is site(row, col) full?
        int index = encode2DTo1D(row, col);
        return ufConnectTop.connected(top, index);
    }

    public int numberOfOpenSites() {    // number of open sites
        return numberOfOpenSites;
    }

    public boolean percolates() {   // does the system percolate?
        return uf.connected(top, bottom);
    }

    public static void main(String[] args) {    // test client(optional)
        Percolation perc = new Percolation(10);
        perc.open(1, 1);
        StdOut.println(perc.percolates());
    }

}
