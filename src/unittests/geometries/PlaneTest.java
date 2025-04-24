package unittests.geometries;
import geometries.Plane;
import primitives.Point;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void constructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the constructor with a point and a normal vector
        assertDoesNotThrow(() -> new Plane(new Point(1, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0)), "Constructor should not throw an exception"); //checks if the constructor throws an exception
        // TC02: Test for a plane that the points are on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(3, 0, 0)), "Constructor should throw an exception for points on the same line");
        // TC03: Test for a plane that the points are not in the same place
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(1, 0, 0)), "Constructor should throw an exception for points not in the same place");
        //TC04: Test for a plane that two of the points are on the same place
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(2, 0, 0)), "Constructor should throw an exception for points not in the same place");
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(1, 0, 0)), "Constructor should throw an exception for points not in the same place");
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(2, 0, 0)), "Constructor should throw an exception for points not in the same place");
    }
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the getNormal method with a point on the plane
        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0));
        assertEquals(plane.getNormal(new Point(1, 0, 0)), plane.getNormal(new Point(0, 0, 1)), "getNormal should return the same normal vector for any point on the plane");
    }
}