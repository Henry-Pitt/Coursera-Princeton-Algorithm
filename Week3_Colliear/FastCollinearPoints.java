/* *****************************************************************************
 *  Name:Henry
 *  Date:07/16/2019
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points

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

        // Think of p as the origin. For each other point q, determinethe slope
        // it makes with p.
        int length = copyOfPoints.length;
        for (int i = 0; i < length; i++) {
            Arrays.sort(copyOfPoints);
            Point pointOrigin = copyOfPoints[i];
            Arrays.sort(copyOfPoints, pointOrigin.slopeOrder());
            // when points slopes are equal, the order conforms to Arrays.sort()
            int min = 1;    // copyOfPoints[0] is pointOrigin itself.
            int max = min;

            while (min < length) {
                while (max < length && pointOrigin.slopeTo(copyOfPoints[min]) == pointOrigin
                        .slopeTo(copyOfPoints[max])) {
                    max++;
                }
                if (max - min >= 3) {
                    if (copyOfPoints[min].compareTo(pointOrigin) > 0) {
                        segments.add(new LineSegment(pointOrigin, copyOfPoints[max - 1]));
                    }
                }
                min = max;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            StdDraw.setPenColor(StdDraw.BLUE);
            segment.draw();
        }
        StdDraw.show();
    }
}
