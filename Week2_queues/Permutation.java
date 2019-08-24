/* *****************************************************************************
 *  Name: Henry
 *  Date: 07/05/2019
 *  Description: Write a client program Permuation.java that takes an integer k
 *               as a command-line argument; reads a sequence of strings from
 *               standard input using StdIn.readString(); and prints exactly k
 *               of them, uniformly at random. Print each item from the sequence
 *               at most once.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();


        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
