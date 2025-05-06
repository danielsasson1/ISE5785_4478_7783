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
        // Test invalid radius
        //TC01
        assertDoesNotThrow( () -> new Tube(new Ray(new Point(1, 2, 3), new Vector(1, 0, 0)), 5),"Failed to create a tube with valid parameters");
    }
    @Test
    void testGetNormal() {
        Tube t1 = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)),1);
        Point p1=new Point(3,3,1);
        Point p2=new Point(0,0,-1);
        Vector normalP1 =new Vector(0,0,1);
        Vector normalP2 =new Vector(0,0,-1);

        // ============ Equivalence Partitions Tests ==============
        //TC01: Testing the getNormal with random Tube and random point
        assertEquals(normalP1,t1.getNormal(p1),"ERROR: getNormal of tube doesn't work properly (TC01)");

        // =============== Boundary Values Tests ==================
        //TC02: Testing the getNormal with a point that is the closest to the head of the ray
        assertEquals(normalP2,t1.getNormal(p2),"ERROR: getNormal of tube doesn't work properly (TC02)");
    }
}