/* *****************************************************************************
 *  Name: Henry
 *  Date: 07/15/2019
 *  Description: Write a program BruteCollinearPoints.java that examines 4 points
 *               at a time and checks whether they all lie on  the same line
 *               segment.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containning 4 points

        if (points == null) {
            throw new java.lang.IllegalArgumentException(
                    "The argument to the constructor is null.");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException("One point in the array is null.");
            }
        }

        Point[] copyOfPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copyOfPoints);
        for (int i = 0; i < copyOfPoints.length - 1; i++) {
            if (copyOfPoints[i].compareTo(copyOfPoints[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException(
                        "The argument to the constructor contains a repeated point.");
            }
        }

        for (int i = 0; i < copyOfPoints.length - 3; i++) {
            for (int j = i + 1; j < copyOfPoints.length - 2; j++) {
                for (int k = j + 1; k < copyOfPoints.length - 1; k++) {
                    for (int m = k + 1; m < copyOfPoints.length; m++) {
                        if (copyOfPoints[i].slopeTo(copyOfPoints[j]) == copyOfPoints[i]
                                .slopeTo(copyOfPoints[k]) &&
                                copyOfPoints[i].slopeTo(copyOfPoints[j]) == copyOfPoints[i]
                                        .slopeTo(copyOfPoints[m])) {
                            segments.add(new LineSegment(copyOfPoints[i], copyOfPoints[m]));
                        }
                    }
                }

            }
        }

    }

    public int numberOfSegments() {
        // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] segmentsArray = new LineSegment[segments.size()];
        return segments.toArray(segmentsArray);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            StdDraw.setPenColor(StdDraw.BLUE);
            segment.draw();
        }
        StdDraw.show();

    }
}
