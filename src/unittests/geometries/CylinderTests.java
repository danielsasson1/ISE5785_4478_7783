package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import geometries.Cylinder;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

class CylinderTests {
    @Test
    void constructor() {
        // Test invalid radius
        //TC01
        assertDoesNotThrow(() -> new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 0, 0)), 5, 10), "Failed to create a cylinder with valid parameters");
    }
    @Test
    void getNormal () {
        Cylinder c1 = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)), 1, 5);
        Point p1 = new Point(3, 3, 1);
        Point p2 = new Point(0, 0, -1);
        Vector normalP1 = new Vector(0, 0, 1);
        Vector normalP2 = new Vector(0, 0, -1);

        // ============ Equivalence Partitions Tests ==============
        //TC01: Testing the getNormal with random Cylinder and random point
        assertEquals(normalP1, c1.getNormal(p1), "ERROR: getNormal of cylinder doesn't work properly (TC01)");

        // =============== Boundary Values Tests =================
        //TC02 the point is on the side outer surface of the cylinder
        assertEquals(normalP1, c1.getNormal(new Point(3, 3, 1)), "ERROR: getNormal of cylinder doesn't work properly in the side outer surface (TC02)");
        //TC03 the point is on the side inner surface of the cylinder
        assertEquals(normalP1, c1.getNormal(new Point(3, 3, 1)), "ERROR: getNormal of cylinder doesn't work properly side inner surface (TC03)");
        //TC04 the point is on the top base of the cylinder
        assertEquals(normalP1, c1.getNormal(new Point(3, 3, 5)), "ERROR: getNormal of cylinder doesn't work properly on the top base (TC04)");
        //TC05 the point is on the bottom base of the cylinder
        assertEquals(normalP2, c1.getNormal(new Point(3, 3, -1)), "ERROR: getNormal of cylinder doesn't work properly on the bottom base (TC05)");
    }
}