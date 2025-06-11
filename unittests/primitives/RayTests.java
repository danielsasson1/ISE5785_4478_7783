package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;

class RayTests {
    @Test
    void getPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: t is a negative number
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));
        assertEquals(new Point(-1, 2, 3), ray.getPoint(-2), "Bad getPoint with negative t");

        // TC02: t is a positive number
        assertEquals(new Point(3, 2, 3), ray.getPoint(2), "Bad getPoint with positive t");

        // =============== Boundary Values Tests ==================
        // TC03: t is zero
        assertEquals(new Point(1, 2, 3), ray.getPoint(0), "Bad getPoint with t=0");
    }
    @Test
    void findClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: The closest point is the point in the middle of the list
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        List<Point> points = List.of(new Point(2, 2, 3), new Point(0, 0, 1), new Point(4, 2, 3));
        assertEquals(new Point(0, 0, 1), ray.findClosestPoint(points), "Bad findClosestPoint with point in the middle of the list");
        // =============== Boundary Values Tests ==================
        // TC02: The list is empty
        assertNull(ray.findClosestPoint(null), "Bad findClosestPoint with empty list");
        // TC03: The closest point is the first point in the list
        points = List.of(new Point(0, 0, 1), new Point(2, 2, 3), new Point(4, 2, 3));
        assertEquals(new Point(0, 0, 1), ray.findClosestPoint(points), "Bad findClosestPoint with first point in the list");
        // TC04: The closest point is the last point in the list
        points = List.of(new Point(2, 2, 3), new Point(4, 2, 3), new Point(0, 0, 1));
        assertEquals(new Point(0, 0, 1), ray.findClosestPoint(points), "Bad findClosestPoint with last point in the list");
    }
}