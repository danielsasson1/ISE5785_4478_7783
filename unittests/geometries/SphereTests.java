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
        // ============ Equivalence Partitions Tests ==============
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1);
        //TC01 regular case
        assertEquals(new Vector(1,0,0),sphere.getNormal(new Point(2, 0, 0)), "getNormal failed");
        //TC02 point inside the sphere
        assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(0.5, 0, 0)), "getNormal failed");
        // ============ Boundary Values Tests ==================
        //TC03 point on the edge of the sphere
        assertEquals(new Vector(0, 1, 0), sphere.getNormal(new Point(0, 1, 0)), "getNormal failed");
        //TC04 point at the center of the sphere
        assertNull(sphere.getNormal(new Point(0, 0, 0)), "getNormal failed");
    }
    @Test
    void findIntersections() {
        // Test the findIntersections method of the Sphere class with a ray that intersects the sphere
        Sphere sphere = new Sphere(new Point(0, 0, 0), Math.sqrt(3));
        //============ Equivalence Partitions Tests ==============
        //TC01
        // Create a ray that intersects the sphere
        Ray ray = new Ray(new Point(3, 0, 0), new Vector(-3, 0, 0));
        List<Point> intersections = sphere.findIntersections(ray);
        assertEquals(2, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(-Math.sqrt(3),0,0), intersections.get(0), "findIntersections should return the correct intersection point");
        assertEquals(new Point(Math.sqrt(3),0,0), intersections.get(1), "findIntersections should return the correct intersection point");
        //TC02
        // Test the findIntersections method with a ray that does not intersect the sphere
        ray = new Ray(new Point(2, 2, 2), new Vector(1, 1, 1));
        intersections = sphere.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that does not intersect the sphere");
        //============ Boundary Values Tests ==================
        //TC03
        // a ray that starts in the center of the sphere
        ray = new Ray(new Point(0,0,0), new Vector(0, 1, 0));
        intersections = sphere.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(0, Math.sqrt(3), 0), intersections.get(0), "findIntersections should return the correct intersection point");
        //TC04
        //a ray that the discriminant is lower than 0
        ray = new Ray(new Point(3, 0, 0), new Vector(0, 1, 1));
        intersections = sphere.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that does not intersect the sphere");
        //TC05
        //a ray that the discriminant is equals to 0 and intersects the sphere at one point
        ray = new Ray(new Point(-5, Math.sqrt(3), 0), new Vector(1, 0, 0));
        intersections = sphere.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(0, Math.sqrt(3), 0), intersections.get(0), "findIntersections should return the correct intersection point");
        //TC06
        //a ray that the discriminant is equals to 0 and doesn't intersect the sphere at one point
        ray = new Ray(new Point(5, Math.sqrt(3), 0), new Vector(1, 0, 0));
        intersections = sphere.findIntersections(ray);
        assertNull(intersections, "findIntersections should return null for a ray that does not intersect the sphere");
        //TC07
        //a ray that starts inside the sphere and intersects it at one positive point
        ray = new Ray(new Point(0.1, 0.1, 0), new Vector(1, 1, 0));
        intersections = sphere.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(1.224744871391589,1.224744871391589,0.0), intersections.get(0), "findIntersections should return the correct intersection point");
        //TC08
        //a ray that starts inside the sphere and intersects it at one negative point
        ray = new Ray(new Point(0.1, 0.1, 0), new Vector(-1, -1, 0));
        intersections = sphere.findIntersections(ray);
        assertEquals(1, intersections.size(), "findIntersections should return one intersection point for a ray that intersects the sphere");
        assertEquals(new Point(-1.224744871391589,-1.224744871391589,0.0), intersections.get(0), "findIntersections should return the correct intersection point");
    }
}