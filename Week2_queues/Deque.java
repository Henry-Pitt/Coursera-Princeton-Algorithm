/* *****************************************************************************
 *  Name: Henry
 *  Date: 07/03/2019
 *  Description: A double-ended queue or deque is a generalization of a stack
 *               and a queue that supports adding and removing items from
 *               either the front or the back of the data structure.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first; // link to least recently added node
    private Node last;  // link to most recently added node
    private int N;      // number of items on the deque

    private class Node {
        // nested class to define class
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can not add a null.");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.previous = null;
        if (isEmpty()) {
            last = first;
        }
        else oldfirst.previous = first;
        N++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can not add a null.");
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.previous = oldlast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else oldlast.next = last;
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        N--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        Item item = last.item;
        last = last.previous;
        if (isEmpty()) {
            first = null;
        }
        N--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque1 = new Deque<Integer>();
        StdOut.println("isEmpty: " + deque1.isEmpty() + "  Expected: true");
        StdOut.println("Size: " + deque1.size() + "  Expected: 0");
        deque1.addFirst(1); // deque1: 1
        deque1.addLast(2);  // deque1: 1 2
        deque1.addFirst(3);  // deque1: 3 1 2
        deque1.addLast(4);  // deque1: 3 1 2 4
        deque1.addLast(5);  // deque1: 3 1 2 4 5
        deque1.addFirst(6); // deque1: 6 3 1 2 4 5
        StdOut.println("isEmpty: " + deque1.isEmpty() + "  Expected: false");
        StdOut.println("Size: " + deque1.size() + "  Expected: 6");
        for (int a : deque1) {
            StdOut.print(a + " ");
        }
        StdOut.println("  Expected: 6 3 1 2 4 5");

        StdOut.println("removeFirst: " + deque1.removeFirst() + "  Expected: 6");
        // deque1: 3 1 2 4 5
        StdOut.println("removeLast: " + deque1.removeLast() + "  Expected: 5");
        // deque1: 3 1 2 4
        StdOut.println("removeLast: " + deque1.removeLast() + "  Expected: 4");
        // deque1: 3 1 2
        StdOut.println("removeFirst: " + deque1.removeFirst() + "  Expected: 3");
        // deque1: 1 2
        StdOut.println("isEmpty: " + deque1.isEmpty() + "  Expected: false");
        StdOut.println("Size: " + deque1.size() + "  Expected: 2");

        deque1.removeLast();
        deque1.removeFirst();
        StdOut.println("isEmpty: " + deque1.isEmpty() + "  Expected: true");
        StdOut.println("Size: " + deque1.size() + "  Expected: 0");

    }
}
