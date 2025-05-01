package unittests.geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import geometries.Sphere;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;

class SphereTests {
    @Test
    void constructor() {
        // Test the constructor of the Sphere class
        // Create a sphere with a center at (0, 0, 0) and radius 1
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1);
        //TC01
        // Check that the center and radius are set correctly
        assertDoesNotThrow(() -> new Sphere(new Point(0, 0, 0), 1), "Sphere constructor failed");
        //Test for a sphere with a negative radius
        //TC02
        assertThrows(IllegalArgumentException.class, () -> new Sphere(new Point(1, 0, 0), -1), "Sphere constructor failed");
    }
    @Test
    void getNormal() {
        // Test the getNormal method of the Sphere class
        // Create a sphere with a center at (0, 0, 0) and radius 1
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1);
        //TC01
        // Check that the normal vector at point (1, 0, 0) is (1, 0, 0)
        Vector expectedNormal = new Vector(1, 0, 0);
        Vector actualNormal = sphere.getNormal(new Point(1, 0, 0));
        assertEquals(expectedNormal, actualNormal, "getNormal failed");
    }

    @Test
    void findIntersections() {
        // Test the findIntersections method of the Sphere class with a ray that intersects the sphere
        Sphere sphere = new Sphere(new Point(0, 0, 0), Math.sqrt(3));
        //============ Equivalence Partitions Tests ==============
        //TC01
        // Create a ray that intersects the sphere
        Ray ray = new Ray(new Point(-2, -2, -2), new Vector(1, 1, 1));
        List<Point> intersections = sphere.findIntersections(ray);
        assertEquals(2, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(-1, -1, -1), intersections.get(0), "findIntersections should return the correct intersection point");
        assertEquals(new Point(1, 1, 1), intersections.get(1), "findIntersections should return the correct intersection point");
        //TC02'
        // Test the findIntersections method with a ray that does not intersect the sphere
        ray = new Ray(new Point(2, 2, 2), new Vector(1, 1, 1));
        intersections = sphere.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that does not intersect the sphere");
        //============ Boundary Values Tests ==================
        //TC03
        // Test the findIntersections method with a ray that is parallel to the sphere, and intersects the sphere only at one point
        ray = new Ray(new Point(-5, Math.sqrt(3), 0), new Vector(1, 0, 0));
        intersections = sphere.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(0, Math.sqrt(3), 0), intersections.get(0), "findIntersections should return the correct intersection point");
        //TC04
        // Test the findIntersections method with a ray that is on the sphere
        ray = new Ray(new Point(0, Math.sqrt(3), 0), new Vector(1, 0, 0));
        intersections = sphere.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is on the sphere");
        //TC05
        // Test the findIntersections method with a ray that is orthogonal to the sphere, and intersects the sphere
        ray = new Ray(new Point(-5, -5, -5), new Vector(1, 1, 1));
        intersections = sphere.findIntersections(ray);
        assertEquals(2, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(-1, -1, -1), intersections.get(0), "findIntersections should return the correct intersection point");
        assertEquals(new Point(1, 1, 1), intersections.get(1), "findIntersections should return the correct intersection point");
        //TC06
        // Test the findIntersections method with a ray that is orthogonal to the sphere, and does not intersect the sphere
        ray = new Ray(new Point(5, 5, 5), new Vector(1, 1, 1));
        intersections = sphere.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that does not intersect the sphere");
        //TC07
        // Test the findIntersections method with a ray that is inside the sphere
        ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        intersections = sphere.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(1, 1, 1), intersections.get(0), "findIntersections should return the correct intersection point");
        //TC08
        // Test the findIntersections method with a ray that is inside the sphere and orthogonal to the sphere
        ray = new Ray(new Point(0, 0, 0), new Vector(-1, -1, -1));
        intersections = sphere.findIntersections(ray);
        assertEquals( 1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(-1, -1, -1), intersections.get(0), "findIntersections should return the correct intersection point");
        //TC09
        // Test the findIntersections method with a ray that is inside the sphere and negative to the sphere
        ray = new Ray(new Point(0, 0, 0), new Vector(-1, -1, -1));
        intersections = sphere.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(-1, -1, -1), intersections.get(0), "findIntersections should return the correct intersection point");
        //TC10
        // Test the findIntersections method with a ray that is touching the sphere only at one point and starts on the sphere
        ray = new Ray(new Point(0, Math.sqrt(3), 0), new Vector(1, 1, 1));
        intersections = sphere.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that is touching the sphere only at one point and starts on the sphere");
    }
}