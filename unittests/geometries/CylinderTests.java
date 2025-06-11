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
        // Test valid radius
        //TC01
        assertDoesNotThrow(() -> new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 0, 0)), 5, 10), "Failed to create a cylinder with valid parameters");
        //TC02 Test invalid radius
        assertThrows(IllegalArgumentException.class, () -> new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 0, 0)), -5, 10), "Failed to throw exception for negative radius");
        assertThrows(IllegalArgumentException.class, () -> new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 0, 0)), 5, -10), "Failed to throw exception for negative radius");
    }
    @Test
    void getNormal () {
        // ============= Equivalence Partitioning Tests =============
        //TC01: Test normal at the side of the cylinder
        Cylinder cylinder = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), Math.sqrt(3), 10);
        assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point(5, 1, 0)), "Failed to get normal at the side of the cylinder");
        //TC02: Test normal at the bottom base of the cylinder
        assertEquals(new Vector(-1, 0, 0), cylinder.getNormal(new Point(-4, 5, 0)), "Failed to get normal at the bottom base of the cylinder");
        //TC03: Test normal at the top base of the cylinder
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(11, 5, 10)), "Failed to get normal at the top base of the cylinder");
        // =============== Boundary Values Tests ==================
        //TC04: Test normal at the center of the bottom base
        assertNull(cylinder.getNormal(new Point(0, 0, 0)), "Failed to get normal at the center of the bottom base");
        //TC05: Test normal on the axis of the cylinder
        assertNull(cylinder.getNormal(new Point(5, 0, 0)), "Failed to get normal on the axis of the cylinder");
    }
}