package unittests.geometries;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: some geometries intersect with the ray
        Geometries geometries = new Geometries(
                new Sphere(new Point(0, 0, 0), Math.sqrt(3)),
                new Plane(new Point(4.5, 0, 0), new Point(0, 4.5, 0), new Point(0, 0, 4.5)),
                new Triangle(new Point(6, 0, 0), new Point(0, 6, 0), new Point(0, 0, 6))
        );
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
        List<Point> intersections = geometries.findIntersections(ray);
        assertEquals(2, intersections.size(), "Expected 3 intersections");
        assertEquals(new Point(1.5,1.5,1.5), intersections.get(0), "First intersection point is incorrect");
        assertEquals(new Point(2, 2, 2), intersections.get(1), "Second intersection point is incorrect");
        // ============ Boundary Values Tests ==============
        // TC02: no geometries intersect with the ray
        ray = new Ray(new Point(10, 10, 10), new Vector(1, 1, 1));
        intersections = geometries.findIntersections(ray);
        assertNull(intersections, "Expected no intersections, but got some");
        // TC03: single geometry intersects with the ray
        ray = new Ray(new Point(0, 0, 0), new Vector(-1, -1, -1));
        intersections = geometries.findIntersections(ray);
        assertEquals(1, intersections.size(), "Expected 1 intersection with single geometry");
        assertEquals(new Point(-1, -1, -1), intersections.get(0), "Intersection point is incorrect");
        // TC04: empty geometries collection
        geometries = new Geometries();
        ray = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
        intersections = geometries.findIntersections(ray);
        assertNull(intersections, "Expected no intersections with empty geometries collection, but got some");
    }
}