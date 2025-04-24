package unittests.geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

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
}