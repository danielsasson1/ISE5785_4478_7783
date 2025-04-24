package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointTests {
    @Test
    void subtract() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        Vector v1 = new Vector(1, 2, 3);
        /*
        TC01
         * Test the subtraction of two points
         * Check if the result is equal to the expected vector
         */
        assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");
        /*
        TC02
         * Test the subtraction of two points
         * Check if the result is equal to the zero vector
         */
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "ERROR: (point - itself) does not work correctly");
    }// subtracting two points

    @Test
    void add() {
        Point p1 = new Point(1, 2, 3);
        Vector v1 = new Vector(1, 2, 3);
        Point p2 = new Point(2, 4, 6);

        /*
        TC03
         * Test the addition of a vector to a point
         * Check if the result is equal to the expected point
         */

        assertEquals(p2, p1.add(v1), "ERROR: (point + vector) = other point does not work correctly");
        /*
        TC04
         * Test the addition of a vector to a point
         * Check if the result is equal to the zero point
         */
        assertEquals(Point.ZERO, p1.add(v1.scale(-1)), "ERROR: (point + vector) = zero point does not work correctly");
    }// adding a vector to a point

    @Test
    void distanceSquared() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        /*
        TC05
         * Test the squared distance between two points
         * Check if the result is equal to the expected value
         */
        assertEquals(14, p1.distanceSquared(p2), "ERROR: point squared distance to itself is not zero");
        /*
        TC06
         * Test the squared distance between two points
         * Check if the result is equal to the expected value
         */
        assertEquals(0, p1.distanceSquared(p1), "ERROR: point squared distance to itself is not zero");
    }// calculating the squared distance between two points

    @Test
    void distance() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        /*
        TC07
         * Test the distance between two points
         * Check if the result is equal to the expected value
         */
        assertEquals(Math.sqrt(14), p1.distance(p2), "ERROR: point distance to itself is not zero");
        /*
        TC08
         * Test the distance between two points
         * Check if the result is equal to the expected value
         */
        assertEquals(0, p1.distance(p1), "ERROR: point distance to itself is not zero");
    }// calculating the distance between two points
}
