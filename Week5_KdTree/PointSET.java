/* *****************************************************************************
 *  Name: Henry
 *  Date: 08/17/2019
 *  Description: Wtrite a mutable data type PointSET.java that represents a set
 *               of points in the unit square. Implement the following API by
 *               using a red-black BST.
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> point2DSET;

    public PointSET() {
        // construct an empty set of points
        point2DSET = new SET<Point2D>();

    }

    public boolean isEmpty() {
        // is the set empty?
        return point2DSET.isEmpty();
    }

    public int size() {
        // number of points in the set
        return point2DSET.size();
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new IllegalArgumentException("The argument is null.");
        }

        point2DSET.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) {
            throw new IllegalArgumentException("The argument is null.");
        }

        return point2DSET.contains(p);
    }

    public void draw() {
        // draw all points to standard draw
        for (Point2D p : point2DSET) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null) {
            throw new IllegalArgumentException("The argument is null.");
        }

        Queue<Point2D> insidePoints = new Queue<Point2D>();
        for (Point2D p : point2DSET) {
            if (rect.contains(p)) {
                insidePoints.enqueue(p);
            }
        }
        return insidePoints;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbour in the set to point p; null if the set is empty
        if (p == null) {
            throw new IllegalArgumentException("The argument is null.");
        }

        double minDis = Double.MAX_VALUE;
        Point2D nearestPoint = null;
        for (Point2D q : point2DSET) {
            double distance = q.distanceSquaredTo(p);
            if (distance < minDis) {
                minDis = distance;
                nearestPoint = q;
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
        // unit testing of the methods(optional)
    }
}
