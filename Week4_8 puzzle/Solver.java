/* *****************************************************************************
 *  Name: Henry
 *  Date:07/25/2019
 *  Description: In this part, you will implement A* search to solve n-by-n
 *               slider puzzles. Create an immutable data type Solver with
 *               the following API.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private Board initial;
        private int numMoves = 0;
        private SearchNode previous = null;

        public SearchNode(Board currentBoard) {
            initial = currentBoard;
        }

        public SearchNode(Board currentBoard, SearchNode previousSN) {
            initial = currentBoard;
            numMoves = previousSN.numMoves + 1;
            previous = previousSN;
        }

        public int compareTo(SearchNode that) {
            return (this.initial.manhattan() + this.numMoves) - (that.initial.manhattan()
                    + that.numMoves);
        }
    }

    private SearchNode lastSN;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        MinPQ<SearchNode> pq1 = new MinPQ<SearchNode>();
        SearchNode rootNode = new SearchNode(initial);
        pq1.insert(rootNode);

        MinPQ<SearchNode> pq2 = new MinPQ<SearchNode>();
        Board twinInitial = initial.twin();
        SearchNode twinRootNode = new SearchNode(twinInitial);
        pq2.insert(twinRootNode);

        while (!pq1.min().initial.isGoal() && !pq2.min().initial.isGoal()) {
            slide(pq1);
            slide(pq2);
        }

        lastSN = pq1.min();
    }

    // slide square
    private void slide(MinPQ<SearchNode> pq) {
        if (pq.min().initial.isGoal()) {
            return;
        }

        SearchNode currentSN = pq.delMin();
        for (Board neighborBoard : currentSN.initial.neighbors()) {
            if (currentSN.previous == null) {
                pq.insert(new SearchNode(neighborBoard, currentSN));
            }
            else {
                if (!neighborBoard.equals(currentSN.previous.initial)) {
                    pq.insert(new SearchNode(neighborBoard, currentSN));
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return lastSN.initial.isGoal();
    }

    // min number of moves to solve initial board
    public int moves() {
        if (lastSN.initial.isGoal()) {
            return lastSN.numMoves;
        }
        else return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> path = new Stack<>();
        if (!isSolvable()) {
            return null;
        }

        SearchNode lastSearchNode = lastSN;
        while (lastSearchNode != null) {
            Board currentBoard = lastSearchNode.initial;
            path.push(currentBoard);
            lastSearchNode = lastSearchNode.previous;
        }

        return path;
    }

    // test client
    public static void main(String[] args) {

        // create initial board with file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible.");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }

    }
}
