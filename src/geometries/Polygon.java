package geometries;

import static java.lang.Double.*;
import java.util.List;
import static primitives.Util.*;
import primitives.*;
import java.util.LinkedList;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan
 */
public class Polygon extends Geometry {
    /** List of polygon's vertices */
    protected final List<Point> vertices;
    /** Associated plane in which the polygon lays */
    protected final Plane       plane;
    /** The size of the polygon - the amount of the vertices in the polygon */
    private final int           size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * @param  vertices                 list of vertices according to their order by
     *                                  edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size          = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane         = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector  n        = plane.getNormal(vertices[0]);
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector  edge1    = vertices[size - 1].subtract(vertices[size - 2]);
        Vector  edge2    = vertices[0].subtract(vertices[size - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180deg. It is hold by the sign of its dot product
        // with the normal. If all the rest consequent edges will generate the same sign
        // - the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < size; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
        double minX = vertices[0].point.d1();
        double minY = vertices[0].point.d2();
        double minZ = vertices[0].point.d3();
        double maxX = minX;
        double maxY = minY;
        double maxZ = minZ;

        for (int i = 1; i < vertices.length; i++) {
            double x = vertices[i].point.d1();
            double y = vertices[i].point.d2();
            double z = vertices[i].point.d3();

            if (x < minX) minX = x;
            if (y < minY) minY = y;
            if (z < minZ) minZ = z;
            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
            if (z > maxZ) maxZ = z;
        }

        Point min = new Point(minX, minY, minZ);
        Point max = new Point(maxX, maxY, maxZ);

        this.box = new BoundingBox(min, max);
    }

    @Override
    public Vector getNormal(Point point) { return plane.getNormal(point); }

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
        if (plane.calculateIntersectionsHelper(ray) == null) return null; // No intersection with the plane
        List<Intersection> intersections = List.of (new Intersection(this, plane.calculateIntersectionsHelper(ray).getFirst().point));
        //checks if the intersection point is inside the polygon
        Point intersectionPoint = intersections.get(0).point;
        if (intersectionPoint.equals(vertices.get(0))) return null; // The intersection point is a vertex of the polygon
        Vector v1 = vertices.get(1).subtract(vertices.get(0)); // Edge vector from vertex 0 to vertex 1
        Vector v2 = intersectionPoint.subtract(vertices.get(0)); // Vector between vertex 0 and the intersection point
        if (v1.normalize().equals(v2.normalize()) || v1.normalize().equals(v2.normalize().scale(-1))) return null; // The intersection point is on the edge of the polygon
        boolean positive = (v1.crossProduct(v2)).dotProduct(plane.getNormal(intersectionPoint)) > 0; // Determine the direction of the polygon
        for (int i = 1; i < size; i++) {
            if (intersectionPoint.equals(vertices.get(i))) return null; // The intersection point is a vertex of the polygon
            v1 = vertices.get((i + 1) % size).subtract(vertices.get(i)); // Edge vector from vertex i to vertex (i+1)%size
            v2 = intersectionPoint.subtract(vertices.get(i));// Update v2 the vector between the vertex and the intersection point
            if (v1.normalize().equals(v2.normalize()) || v1.normalize().equals(v2.normalize().scale(-1))) return null; // The intersection point is on the edge of the polygon
            if (positive != (v1.crossProduct(v2).dotProduct(plane.getNormal(intersectionPoint)) > 0)) return null; // The intersection point is outside the polygon
        }
        // If we reach here, the intersection point is inside the polygon
        return intersections; // Return the intersection point with the polygon
    }
}