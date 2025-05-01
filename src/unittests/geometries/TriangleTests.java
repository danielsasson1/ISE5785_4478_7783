package unittests.geometries;

import org.junit.jupiter.api.Test;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;

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
    @Test
    void FindIntersectionsTest() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the findIntersections method with a ray that intersects the triangle
        Triangle triangle = new Triangle(new Point(3, 0, 0), new Point(0, 3, 0), new Point(0, 0, 3));
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        List<Point> intersections = triangle.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the triangle");
        assertEquals(new Point(1, 1, 1), intersections.get(0), "findIntersections should return the correct intersection point");
        // TC02: Test the findIntersections method with a ray that does not intersect the triangle
        ray = new Ray(new Point(0, 0, 0), new Vector(-1, -1, -1));
        intersections = triangle.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that does not intersect the triangle");
        //============ Boundary Values Tests ==================
        // TC03: Test the findIntersections method with a ray that is parallel to the triangle
        ray = new Ray(new Point(4, 0, 0), new Vector(1, 0, 0));
        intersections = triangle.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is parallel to the triangle");
        // TC04: Test the findIntersections method with a ray that is on the triangle
        ray = new Ray(new Point(3, 0, 0), new Vector(1, 1, 1));
        intersections = triangle.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is on the triangle");
        // TC05: Test the findIntersections method with a ray that is orthogonal to the triangle, and intersecting
        ray = new Ray(new Point(0.5, 0.5, 0.5), new Vector(1, 1, 1));
        intersections = triangle.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that is orthogonal to the triangle");
        assertEquals(new Point(1, 1, 1), intersections.get(0), "findIntersections should return the correct intersection point");
        // TC06: Test the findIntersections method with a ray that is orthogonal to the triangle, and not intersecting
        ray = new Ray(new Point(2, 2, 2), new Vector(1, 1, 1));
        intersections = triangle.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is orthogonal to the triangle and not intersecting");
        // TC07: Test the findIntersections method with a ray that is inside the triangle
        ray = new Ray(new Point(3, 3, 3), new Vector(1, 0, 0));
        intersections = triangle.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is inside the triangle");
        // TC08: Test the findIntersections method with a negative Vector that and negative Point
        ray = new Ray(new Point(-1, -1, -1), new Vector(-1, -1, -1));
        intersections = triangle.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is inside the triangle");
    }
}