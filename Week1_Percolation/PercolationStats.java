/* *****************************************************************************
 *  Name: Henry
 *  Date: 06/25/2019
 *  Description: To estimate the percolation threshold
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int T;  // Number of independent experiments
    private double[] p; // Percolation thresholds for the T experiments.

    public PercolationStats(int n, int trials) {
        // perform trials independent experiment on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be bigger than 0.");
        }

        T = trials;
        p = new double[trials];

        // repeatedly perform trials
        for (int count = 0; count < trials; count++) {
            Percolation perc = new Percolation(n);
            int threshold = 0;
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    threshold++;
                }

            }
            p[count] = ((double) threshold) / (n * n);

        }

    }

    public double mean() {
        // sample mean of percolation threshold

        return StdStats.mean(p);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold

        return StdStats.stddev(p);
    }

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        return (mean() - 1.96 * stddev() / Math.sqrt(T));
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return (mean() + 1.96 * stddev() / Math.sqrt(T));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, T);

        StdOut.printf("%-25s = %f\n", "mean", percStats.mean());
        StdOut.printf("%-25s = %f\n", "stddev", percStats.stddev());
        StdOut.printf("%-25s = [%f, %f]\n", "95% confidence interval", percStats.confidenceLo(),
                      percStats.confidenceHi());

    }
}
