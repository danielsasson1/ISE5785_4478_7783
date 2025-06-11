package unittests.geometries;

import geometries.Polygon;
import org.junit.jupiter.api.Test;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {
    @Test
    void GetNormalTest() { //uses the same method of the superclass
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the getNormal method with a triangle defined by three points
        Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Vector normal = triangle.getNormal(new Point(0.5, 0.5, 0));
        assertEquals(new Vector(0, 0, 1), normal, "getNormal should return the correct normal vector for the triangle");
    }

    @Test
    void testFindIntersections() {
        Triangle mesh = new Triangle(new Point(3, 0, 0), new Point(0, 3, 0), new Point(0,0,3));
        // ============ Equivalence Partitions Tests ==============
        // TC01: the intersection point is inside the Polygon
        assertEquals(1, mesh.findIntersections(new Ray(new Point(0 ,1 ,0), new Vector(0.5, 1, 0.5))).size(), "Failed to find the intersection point when the intersection point is inside the Polygon");
        assertEquals(new Point(0.5,2,0.5), mesh.findIntersections(new Ray(new Point(0 ,1 ,0), new Vector(0.5, 1, 0.5))).get(0),"Failed to find the intersection point when the intersection point is inside the Polygon");
        // TC02: the intersection point is outside the Polygon
        assertNull(mesh.findIntersections(new Ray(new Point(3, 3, 3), new Vector(0, 0, 1))), "Failed to find the intersection point when the intersection point is outside the Polygon");
        // ============ Boundary Values Tests ==============
        // TC03 : the intersection point is on the vertex of the Polygon
        assertNull(mesh.findIntersections(new Ray(new Point(0 ,0 ,0), new Vector(0, 1, 0))), "Failed to find the intersection point when the intersection point is on the edge of the Polygon");
        // TC04: the intersection point is on an edge of the Polygon
        assertNull(mesh.findIntersections(new Ray(new Point(0 ,0 ,0), new Vector(1, 1, 0))), "Failed to find the intersection point when the intersection point is on the edge of the Polygon");
        // TC05: the intersection point is outside the Polygon but inside the plane of it
        assertNull(mesh.findIntersections(new Ray(new Point(-2 ,3.5 ,3.5), new Vector(-1, 1, 1))), "Failed to find the intersection point when the intersection point is on a vertex of the Polygon");
        // TC06: the intersection point is parallel to the Polygon
        assertNull(mesh.findIntersections(new Ray(new Point(0 ,0 ,0), new Vector(-1, 1, 0))), "Failed to find the intersection point when the intersection point is parallel to the Polygon");
        // TC07: the intersection point is parallel to the Polygon but in the same plane
        assertNull(mesh.findIntersections(new Ray(new Point(1 ,1 ,1), new Vector(-1, 1, 0))), "Failed to find the intersection point when the intersection point is parallel to the Polygon but in the same plane");
    }
}