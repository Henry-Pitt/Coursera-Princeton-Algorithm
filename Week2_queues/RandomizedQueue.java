/* *****************************************************************************
 *  Name: Henry
 *  Date: 07/04/2019
 *  Description: A randomized queue is similar to a stack or queue, except that
 *               the item removed is chosen uniformly at ramdom among items in
 *               the data structure.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N = 0;  // number of items
    private Item[] a = (Item[]) new Object[1];

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    private void resize(int max) {
        // move queue to a new array of size max
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        // add item to the end of the array
        if (item == null) {
            throw new IllegalArgumentException("Null can not added to the randomized queue.");
        }
        if (N == a.length) {
            resize(2 * a.length);
        }
        a[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The randomized queue is empty.");
        }
        int randomNumber = StdRandom.uniform(N);
        Item item = a[randomNumber];
        a[randomNumber] = a[N - 1];
        a[N - 1] = null;
        if (N == a.length / 4) {
            resize(a.length / 2);
        }
        N--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("The randomized queue is empty.");
        }
        int randomNumber = StdRandom.uniform(N);
        return a[randomNumber];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    // support random iteration
    private class RandomArrayIterator implements Iterator<Item> {
        private int current = 0;
        private Item[] copyItem;

        public RandomArrayIterator() {
            copyItem = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                copyItem[i] = a[i];
            }
            StdRandom.shuffle(copyItem);
        }

        public boolean hasNext() {
            return current < N;
        }

        public Item next() {
            if (current > N) {
                throw new NoSuchElementException();
            }
            return copyItem[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }
        // StdOut.println("Size: " + queue.size());
        for (int a : queue) {
            for (int b : queue) {
                StdOut.print(a + "-" + b + " ");
            }
            StdOut.println();
        }

    }
}
