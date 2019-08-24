/* *****************************************************************************
 *  Name: Henry
 *  Date: 08/19/2019
 *  Description: Write a mutable data type KdTree.java that uses a 2d-tree to
 *               implement the same API(but replace PointSET with KdTree).
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node root;      // root of KdTree
    private int count;      // size of tree
    private final RectHV CANVAS = new RectHV(0, 0, 1, 1);

    private static class Node {
        private Point2D p;
        private Node left, right;       // links to subtrees
        private boolean xCoordinate;    // divide by x coordinate
        private RectHV rect;            // the axis-aligned rectangle corresponding to this node

        Node(Point2D p, boolean xCoordinate, RectHV rect) {
            this.p = p;
            this.xCoordinate = xCoordinate;
            this.rect = rect;
        }

        public int compareTo(Point2D that) {
            // use the xCoordinate of parent node
            if (xCoordinate) {
                if (this.p.x() < that.x()) {
                    return 1;
                }
                else if (this.p.x() > that.x()) {
                    return -1;
                }
            }
            else {
                if (this.p.y() < that.y()) {
                    return 1;
                }
                else if (this.p.y() > that.y()) {
                    return -1;
                }
            }
            return 0;
        }
    }

    public KdTree() {
        // construct an empty set of points
        this.root = null;
        this.count = 0;
    }

    public boolean isEmpty() {
        // is the set empty?
        return size() == 0;
    }

    public int size() {
        // number of points in the set
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        else return count;
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        if (contains(p)) {
            return;
        }
        root = insert(root, p, true, CANVAS);
    }

    private Node insert(Node x, Point2D p, boolean xCoordinate, RectHV rect) {
        if (x == null) {
            count++;
            return new Node(p, xCoordinate, rect);
        }
        int cmp = x.compareTo(p);
        if (cmp < 0) {
            x.left = insert(x.left, p, !xCoordinate, leftChildRect(x));
        }
        if (cmp >= 0) {
            x.right = insert(x.right, p, !xCoordinate, rightChildRect(x));
        }
        return x;
    }

    private RectHV leftChildRect(Node x) {
        RectHV orginalRect = x.rect;
        RectHV childRect;
        double xmin = orginalRect.xmin();
        double ymin = orginalRect.ymin();
        double xmax = orginalRect.xmax();
        double ymax = orginalRect.ymax();

        if (x.xCoordinate) {
            childRect = new RectHV(xmin, ymin, x.p.x(), ymax);
        }
        else {
            childRect = new RectHV(xmin, ymin, xmax, x.p.y());
        }

        return childRect;
    }

    private RectHV rightChildRect(Node x) {
        RectHV orginalRect = x.rect;
        RectHV childRect;
        double xmin = orginalRect.xmin();
        double ymin = orginalRect.ymin();
        double xmax = orginalRect.xmax();
        double ymax = orginalRect.ymax();

        if (x.xCoordinate) {
            childRect = new RectHV(x.p.x(), ymin, xmax, ymax);
        }
        else {
            childRect = new RectHV(xmin, x.p.y(), xmax, ymax);
        }

        return childRect;
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) {
            return false;
        }
        if (x.p.equals(p)) {
            return true;
        }
        int cmp = x.compareTo(p);
        if (cmp < 0) {
            return contains(x.left, p);
        }
        else {
            return contains(x.right, p);
        }
    }

    public void draw() {
        // draw all points to standard draw
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
        if (x.xCoordinate) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        draw(x.left);
        draw(x.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null) {
            throw new IllegalArgumentException("Argument is null.");
        }

        Queue<Point2D> insideRectQueue = new Queue<Point2D>();
        range(root, rect, insideRectQueue);

        return insideRectQueue;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null) return;
        boolean isLeft = leftChildRect(x).intersects(rect);
        boolean isRight = rightChildRect(x).intersects(rect);

        if (isLeft) {
            range(x.left, rect, queue);
        }
        if (rect.contains(x.p)) {
            queue.enqueue(x.p);
        }
        if (isRight) {
            range(x.right, rect, queue);
        }
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new IllegalArgumentException("Argument is null.");
        }

        if (this.isEmpty()) {
            throw new NullPointerException("The KdTree is empty.");
        }

        Point2D nearestPoint = nearest(p, root, root.p);

        return nearestPoint;
    }

    private Point2D nearest(Point2D p, Node x, Point2D nearestPoint) {
        if (x == null || x.rect.distanceSquaredTo(p) > nearestPoint.distanceSquaredTo(p)) {
            return nearestPoint;
        }
        double minDist = p.distanceSquaredTo(nearestPoint);
        double currrentNodeDist = x.p.distanceSquaredTo(p);
        if (currrentNodeDist < minDist) {
            nearestPoint = x.p;
        }
        double leftDist = leftChildRect(x).distanceSquaredTo(p);
        double rightDist = rightChildRect(x).distanceSquaredTo(p);

        if (leftDist < rightDist) {
            nearestPoint = nearest(p, x.left, nearestPoint);
            nearestPoint = nearest(p, x.right, nearestPoint);
        }
        else {
            nearestPoint = nearest(p, x.right, nearestPoint);
            nearestPoint = nearest(p, x.left, nearestPoint);
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        Point2D point2D = new Point2D(0.7, 0.2);
        kdtree.insert(point2D);
        kdtree.insert(new Point2D(0.5, 0.4));
        kdtree.insert(new Point2D(0.2, 0.3));
        kdtree.insert(new Point2D(0.4, 0.7));
        StdOut.println("size: " + kdtree.size());
        kdtree.insert(new Point2D(0.9, 0.6));
        kdtree.insert(new Point2D(0.72, 0.52));
        StdOut.println("size: " + kdtree.size());
        kdtree.draw();
    }
}
