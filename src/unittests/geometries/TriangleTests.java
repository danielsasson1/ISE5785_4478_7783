package unittests.geometries;

import org.junit.jupiter.api.Test;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {
    @Test
    void GetNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal of a triangle (not zero)
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Vector normal = triangle.getNormal(new Point(0, 0, 0));
        assertEquals(new Vector(1, 1, 1).normalize(), normal, "ERROR: Triangle getNormal() does not work correctly");
    }
}