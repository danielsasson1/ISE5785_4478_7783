package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.*;

class RayTests {
    @Test
    void getPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: t is a negative number
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));
        assertEquals(new Point(-1, 2, 3), ray.getPoint(-2), "Bad getPoint with negative t");

        // TC02: t is a positive number
        assertEquals(new Point(3, 2, 3), ray.getPoint(2), "Bad getPoint with positive t");

        // =============== Boundary Values Tests ==================
        // TC03: t is zero
        assertEquals(new Point(1, 2, 3), ray.getPoint(0), "Bad getPoint with t=0");
    }
}