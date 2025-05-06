package unittests.geometries;
import geometries.Plane;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

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

    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the findIntersections method with a ray that does not intersect the plane
        Plane plane = new Plane(new Point(3, 0, 0), new Point(0, 0, 3), new Point(0, 3, 0));
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(-1, -1, 1));
        List<Point> intersections = plane.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that does not intersect the plane");
        // TC02: Test the findIntersections method with a ray that intersects the plane
        ray = new Ray(new Point(0, 0, 0), new Vector(0, 1, 1));
        intersections = plane.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the plane");
        assertEquals(new Point(0, 1.5, 1.5), intersections.get(0), "findIntersections should return the correct intersection point");
        //============ Boundary Values Tests ==================
        // TC03: Test the findIntersections method with a ray that is parallel to the plane
        ray = new Ray(new Point(3, 0, 0), new Vector(1, 0, 0));
        intersections = plane.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is parallel to the plane");
        // TC04: Test the findIntersections method with a ray that is on the plane
        ray = new Ray(new Point(3, 0, 0), new Vector(1, 1, 1));
        intersections = plane.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is on the plane");
        // TC05: Test the findIntersections method with a ray that is orthogonal to the plane
        ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        intersections = plane.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that is orthogonal to the plane");
        assertEquals(new Point(1,1,1), intersections.get(0), "findIntersections should return the correct intersection point");
        // TC06: Test the findIntersections method with a ray that is orthogonal to the plane and starts on the plane
        ray = new Ray(new Point(3, 0, 0), new Vector(1, 1, 1));
        intersections = plane.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is orthogonal to the plane and starts on the plane");
        // TC07: Test the findIntersections method with a ray that is orthogonal to the plane and negative to the plane
        ray = new Ray(new Point(0, 0, 0), new Vector(-1, -1, -1));
        intersections = plane.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is orthogonal to the plane and negative to the plane");
    }
}