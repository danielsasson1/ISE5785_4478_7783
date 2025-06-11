package unittests.geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import geometries.Tube;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

class TubeTests {

    @Test
    void constructor() {
        //TC01
        assertDoesNotThrow( () -> new Tube(new Ray(new Point(1, 2, 3), new Vector(1, 0, 0)), 5),"Failed to create a tube with valid parameters");
        //TC02 invalid parameters
        assertThrows(IllegalArgumentException.class, () -> new Tube(new Ray(new Point(1, 2, 3), new Vector(1, 0, 0)), -5), "Failed to throw exception for negative radius");
    }

    @Test
    void testGetNormal() {
        // ============= Equivalence Partitioning Tests =============
        Tube t1 = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), Math.sqrt(3));
        //TC01 point on the side surface of the tube
        assertEquals(new Vector(0, 1, 0), t1.getNormal(new Point(5, 5, 0)), "Failed to get normal vector for point on the side surface of the tube");
        //TC02 point on the downside of the tube
        assertEquals(new Vector(-1, 0, 0), t1.getNormal(new Point(-1, 15, 0)), "Failed to get normal vector for point on the downside of the tube");
        //=============== Boundary Values Tests ==================
        //TC03 point is the center of the tube
        assertNull(t1.getNormal(new Point(0, 0, 0)), "Failed to get normal vector for point at the center of the tube");
        //TC04 point on the axis of the tube
        assertNull(t1.getNormal(new Point(5, 0, 0)), "Failed to get normal vector for point on the axis of the tube");
    }
}