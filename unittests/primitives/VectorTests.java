package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static primitives.Util.isZero;

class VectorTests {

    @Test
    void add() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1, -2, -3);
        // ============== Equivalence Partitions Tests ==============
        // Test the addition of two vectors and the addition of a vector to itself
        //TC01
        assertEquals(v2, v1.add(v1), "ERROR: (vector1 + vector2) does not work correctly");
        // ============== Boundary Values Tests ==============
        // Test the addition of a vector to itself
        //TC02
        assertThrows(IllegalArgumentException.class, () -> v1.add(v3), "ERROR: ZERO VECTOR!");
    }// add

    @Test
    void subtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1, -2, -3);
        // ============== Equivalence Partitions Tests ==============
        // Test the subtraction of two vectors and the subtraction of a vector from itself
        //TC03
        assertEquals(v3, v1.subtract(v2), "ERROR: (vector1 - vector2) does not work correctly");
        // ============== Boundary Values Tests ==============
        // Test the subtraction of a vector from itself
        //TC04
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "ERROR: ZERO VECTOR!");
    }// subtract

    @Test
    void scale() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1, -2, -3);
        // ============== Equivalence Partitions Tests ==============
        // Test the scaling of a vector by a scalar
        //TC05
        assertEquals(v2, v1.scale(2), "ERROR: (vector * scalar) does not work correctly");
        // Test the scaling of a vector by a negative scalar
        //TC06
        assertEquals(v3, v1.scale(-1), "ERROR: (vector * scalar) does not work correctly");
        // ============== Boundary Values Tests ==============
        // Test the scaling of a vector by zero
        //TC07
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "ERROR: ZERO VECTOR!");
    }// scale

    @Test
    void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1,-1,1);
        // ============== Equivalence Partitions Tests ==============
        // Test the dot product of two vectors
        //TC08
        assertEquals(28, v1.dotProduct(v2), "ERROR: (vector1 * vector2) does not work correctly");
        // ============== Boundary Values Tests ==============
        // Test the dot product of a vector with itself
        //TC09
        assertEquals(14, v1.dotProduct(v1), "ERROR: (vector * vector) does not work correctly");
        // Test the dot product of a vector with a zero vector
        //TC10
        assertEquals(0, v1.dotProduct(v3), "Can't calculate the dot product with a zero vector");
        // Test the dot product of a vector with a unit vector
        //TC11
        Vector v4 = v1.normalize();
        assertEquals(1, v4.dotProduct(v4), "ERROR: (vector * vector) does not work correctly");
    }// dotProduct

    @Test
    void crossProduct() {
        // TC01
        // Test the cross product of two vectors
        //============= Equivalence Partitions Tests ==============
        assertEquals(new Vector(1, 0, 0), new Vector(0, 1, 0).crossProduct(new Vector(0, 0, 1)), "Wrong result of cross product");
        // Test the cross product of a vector with itself
        // ============== Boundary Values Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 1, 1);
        Vector vr = v1.crossProduct(v2);

        // TC02: the vectors are parallel
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).crossProduct(new Vector(2, 4, 6)), "did not constructed a zero vector");

        // TC03: the vector are equal
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).crossProduct(new Vector(1, 2, 3)), "did not constructed a zero vector");

        // TC04: the cross product is not orthogonal to its operands
        assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v2)), "Cross product is not orthogonal to its operands");

        // TC05: the vectors are the same length but not parallel
        assertEquals(new Vector(1,7,-5), new Vector(1, 2, 3).crossProduct(new Vector(3, 1, 2)), "Wrong result of cross product");

    }// crossProduct

    @Test
    void lengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1,-2,-3);

        // Test the length squared of a vector
        //TC01
        //============== Equivalence Partitions Tests ==============
        assertEquals(14, v1.lengthSquared(), "ERROR: (vector * vector) does not work correctly");
        // Test Positive and Negative Vector
        //TC02
        //============== Boundary Values Tests ==============
        assertEquals( 14, v3.lengthSquared(), "ERROR: (vector * vector) does not work correctly");
    }// lengthSquared

    @Test
    void length() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1,-2,-3);
        //============== Equivalence Partitions Tests ==============
        // Test the length of a vector
        //TC01
        assertEquals(Math.sqrt(14), v1.length(), "ERROR: (vector * vector) does not work correctly");
        // Test Positive and Negative Vector
        //TC02
        assertEquals(Math.sqrt(14), v3.length(), "ERROR: (vector * vector) does not work correctly");
        //============== Boundary Values Tests ==============
        // Test the length of a unit vector
        //TC03
        Vector v4 = v1.normalize();
        assertEquals(1, v4.length(), "ERROR: (vector * vector) does not work correctly");
    }// length

    @Test
    void normalize() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1,-2,-3);
        //============== Equivalence Partitions Tests ==============
        // Test the normalization of a vector
        //TC01
        assertEquals(new Vector(1/Math.sqrt(14), 2/Math.sqrt(14), 3/Math.sqrt(14)), v1.normalize(), "ERROR: (vector * vector) does not work correctly");
        // Test correct vector normalized is the same vector
        //TC02
        assertEquals(new Vector(-1/Math.sqrt(14), -2/Math.sqrt(14), -3/Math.sqrt(14)), v3.normalize(), "ERROR: (vector * vector) does not work correctly");
        //=============== Boundary Values Tests ==============
        //TC03: vector normalized * vector is bigger than 0
        assertTrue(v1.normalize().dotProduct(v1) > 0, "ERROR: (vector * vector) does not work correctly");
        //TC04: vector normalized = normalized vector
        Vector v4 = v1.normalize();
        assertEquals(v4, v1.normalize(), "ERROR: (vector * vector) does not work correctly");
    }
}