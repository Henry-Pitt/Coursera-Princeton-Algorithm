/* *****************************************************************************
 *  Name: Henry
 *  Date: 07/24/2019
 *  Description: Create a data type that models an n-by-n board with sliding
 *               tiles. Implement an immutable data type Board with the
 *               following API.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private int n;
    private int[][] boardArray;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col).
    public Board(int[][] tiles) {
        n = tiles.length;
        boardArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boardArray[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(" " + boardArray[i][j]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int numberHam = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardArray[i][j] != 0 && boardArray[i][j] != i * n + j + 1) {
                    numberHam++;
                }
            }
        }

        return numberHam;

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int numberMan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardArray[i][j] != 0) {
                    int goalRow = (boardArray[i][j] - 1) / n;
                    int goalCol = (boardArray[i][j] - 1) % n;
                    numberMan += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }

        return numberMan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardArray[i][j] > 0 && boardArray[i][j] != n * i + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.n != that.n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.boardArray[i][j] != that.boardArray[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // return the blank square location
    private int[] blankLocation() {
        int[] location = new int[2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardArray[i][j] == 0) {
                    location[0] = i;
                    location[1] = j;
                    return location;
                }
            }
        }
        return location;
    }

    // copy BoardArray
    private int[][] copyBoardArray() {
        int[][] copyArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copyArray[i][j] = boardArray[i][j];
            }
        }
        return copyArray;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborsArrayList = new ArrayList<Board>();

        int blankRow = this.blankLocation()[0];
        int blankCol = this.blankLocation()[1];
        // neighbor 1 : up
        if (blankRow > 0) {
            int[][] neighborUp = copyBoardArray();
            neighborUp[blankRow][blankCol] = neighborUp[blankRow - 1][blankCol];
            neighborUp[blankRow - 1][blankCol] = 0;
            Board neighborBoardUp = new Board(neighborUp);
            neighborsArrayList.add(neighborBoardUp);
        }
        // neighbor 2 : left
        if (blankCol > 0) {
            int[][] neighborLeft = copyBoardArray();
            neighborLeft[blankRow][blankCol] = neighborLeft[blankRow][blankCol - 1];
            neighborLeft[blankRow][blankCol - 1] = 0;
            Board neighborBoardLeft = new Board(neighborLeft);
            neighborsArrayList.add(neighborBoardLeft);
        }
        // neighbor 3 : down
        if (blankRow < n - 1) {
            int[][] neighborDown = copyBoardArray();
            neighborDown[blankRow][blankCol] = neighborDown[blankRow + 1][blankCol];
            neighborDown[blankRow + 1][blankCol] = 0;
            Board neighborBoardDown = new Board(neighborDown);
            neighborsArrayList.add(neighborBoardDown);
        }
        // neighbor 4 : right
        if (blankCol < n - 1) {
            int[][] neighborRight = copyBoardArray();
            neighborRight[blankRow][blankCol] = neighborRight[blankRow][blankCol + 1];
            neighborRight[blankRow][blankCol + 1] = 0;
            Board neighborBoardRight = new Board(neighborRight);
            neighborsArrayList.add(neighborBoardRight);
        }
        return neighborsArrayList;
    }

    // a board that is obtained by exchange any pair of tiles
    public Board twin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (this.boardArray[i][j] * this.boardArray[i][j + 1] != 0) {
                    int[][] twinArray = swap(i, j, i, j + 1);
                    return new Board(twinArray);
                }
            }
        }
        return null;
    }

    // exchange two element in a array
    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] twinArray = copyBoardArray();
        int tmp = twinArray[row1][col1];
        twinArray[row1][col1] = twinArray[row2][col2];
        twinArray[row2][col2] = tmp;
        return twinArray;
    }


    // unit testing
    public static void main(String[] args) {
        // Create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        StdOut.println(initial);

        StdOut.println("dimension: " + initial.dimension());
        StdOut.println("hamming: " + initial.hamming());
        StdOut.println("manhattan: " + initial.manhattan());
        StdOut.println("isGoal: " + initial.isGoal());
        Board twinInitial = initial.twin();
        StdOut.println("twin: " + twinInitial);
        StdOut.println("is equals: " + initial.equals(initial));
        StdOut.println("is equals: " + initial.equals(twinInitial));
        for (Board neighbor : initial.neighbors()) {
            StdOut.println("neighbors: " + "\n" + neighbor);
        }
    }
}
