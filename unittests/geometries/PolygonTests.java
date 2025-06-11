package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import geometries.Triangle;
import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import primitives.*;

/**
 * Testing Polygons
 * @author Dan
 */
class PolygonTests {
   /**
    * Delta value for accuracy when comparing the numbers of type 'double' in
    * assertEquals
    */
   private static final double DELTA = 0.000001;

   /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
   @Test
   void testConstructor() {
      // ============ Equivalence Partitions Tests ==============

      // TC01: Correct concave quadrangular with vertices in correct order
      assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                                           new Point(1, 0, 0),
                                           new Point(0, 1, 0),
                                           new Point(-1, 1, 1)),
                         "Failed constructing a correct polygon");

      // TC02: Wrong vertices order
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                   "Constructed a polygon with wrong order of vertices");

      // TC03: Not in the same plane
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                   "Constructed a polygon with vertices that are not in the same plane");

      // TC04: Concave quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0.5, 0.25, 0.5)), //
                   "Constructed a concave polygon");

      // =============== Boundary Values Tests ==================

      // TC10: Vertex on a side of a quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0, 0.5, 0.5)),
                   "Constructed a polygon with vertix on a side");

      // TC11: Last point = first point
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                   "Constructed a polygon with vertice on a side");

      // TC12: Co-located points
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                   "Constructed a polygon with vertice on a side");

   }

   /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
   @Test
   void testGetNormal() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: There is a simple single test here - using a quad
      Point[] pts =
         { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
      Polygon pol = new Polygon(pts);
      // ensure there are no exceptions
      assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
      // generate the test result
      Vector result = pol.getNormal(new Point(0, 0, 1));
      // ensure |result| = 1
      assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
      // ensure the result is orthogonal to all the edges
      for (int i = 0; i < 3; ++i)
         assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                      "Polygon's normal is not orthogonal to one of the edges");
   }


    @Test
    void testFindIntersections() {
        Polygon mesh = new Polygon(new Point(3, 0, 0), new Point(0, 3, 0), new Point(0,0,3));
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
