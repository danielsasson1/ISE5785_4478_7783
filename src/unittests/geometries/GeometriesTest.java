package unittests.geometries;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntersections() {
        // ============ boundary Values Tests ==================
        // TC01: Test the findIntersections method with a ray that intersects the geometries
        // Create a geometries object with multiple shapes
        // Create a ray that intersects the geometries
        // Call the findIntersections method and check the result
        Geometries geometries = new Geometries(
                new Sphere(new Point(0, 0, 0), Math.sqrt(3)), //Points: (-1, -1, -1), (1, 1, 1)
                new Triangle(new Point(1.5, 0, 0), new Point(0, 1.5, 0), new Point(0, 0, 1.5)), //Point: (0.5, 0.5, 0.5)
                new Plane(new Point(6, 0, 0), new Point(0, 6, 0), new Point(0, 0, 6))
        );
        Ray ray = new Ray(new Point(-2, -2, -2), new Vector(1, 1, 1));
        List<Point> intersections = geometries.findIntersections(ray);
        assertEquals(4, intersections.size(), "findIntersections should return two intersection points for a ray that intersects the geometries");
        assertEquals(new Point(-1, -1, -1), intersections.get(0), "findIntersections should return the correct intersection point");
        assertEquals(new Point(1, 1, 1), intersections.get(1), "findIntersections should return the correct intersection point");
        assertEquals(new Point(0.5, 0.5, 0.5), intersections.get(2), "findIntersections should return the correct intersection point");
        assertEquals(new Point(2, 2, 2), intersections.get(3), "findIntersections should return the correct intersection point");
        // TC02: Test the findIntersections method with a ray that intersects only one of the geometries
        // Create a ray that intersects only one of the geometries
        ray = new Ray(new Point(1.5, 1.5, 1.5), new Vector(1, 1, 1));
        intersections = geometries.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects only one of the geometries");
        assertEquals(new Point(2, 2, 2), intersections.get(0), "findIntersections should return the correct intersection point");
        // TC03: Test the findIntersections method with a ray that does not intersect any of the geometries
        // Create a ray that does not intersect any of the geometries
        ray = new Ray(new Point(10, 10, 10), new Vector(1, 1, 1));
        intersections = geometries.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that does not intersect any of the geometries");
        // TC04: Test the findIntersections method with an empty geometries object
        // Create an empty geometries object
        Geometries emptyGeometries = new Geometries();
        // Create a ray
        ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        // Call the findIntersections method and check the result
        intersections = emptyGeometries.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for an empty geometries object");
        // ============ Equivalence Partitions Tests ==============
        // TC05: Test the findIntersections method with a ray that intersects multiple geometries but not all of them
        // Create a ray that intersects multiple geometries but not all of them
        ray = new Ray(new Point(0.5, 0.5, 0.5), new Vector(1, 1, 1));
        intersections = geometries.findIntersections(ray);
        assertEquals(2, intersections.size(), "findIntersections should return two intersection points for a ray that intersects multiple geometries but not all of them");
        assertEquals(new Point(1, 1, 1), intersections.get(0), "findIntersections should return the correct intersection point");
        assertEquals(new Point(2, 2, 2), intersections.get(1), "findIntersections should return the correct intersection point");
    }
}