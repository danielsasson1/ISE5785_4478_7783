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
    }

    @Override
    public Vector getNormal(Point point) { return plane.getNormal(point); }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Check if the ray is in the same plane as the polygon
        List<Vector> normals = new LinkedList<>();
        final Point p0 = ray.getPoint(0);
        final Vector v = ray.getVector();
        Vector v1 = vertices.getFirst().subtract(p0);
        for (Point vertex : vertices.subList(1, size)) {
            Vector v2 = vertex.subtract(p0);
            normals.add(v1.crossProduct(v2).normalize());
            v1 = v2;
        }
        normals.add(vertices.getLast().subtract(p0).crossProduct(vertices.getFirst().subtract(p0)).normalize());
        // Check if the ray is in the same plane as the polygon
        boolean Positive = v.dotProduct(normals.getFirst()) > 0;
        for (Vector normal : normals) {
            double s = v.dotProduct(normal);
            if (Util.isZero(s) || s > 0 != Positive) {
                return null; // The ray is not in the same plane as the polygon
            }
        }
        Plane plane = new Plane(vertices.getFirst(), vertices.get(1), vertices.get(2));
        // Find the intersection point of the ray and the plane
        return plane.findIntersections(ray);
        /*
        long explanation about this method:
        1. The method first checks if the ray is in the same plane as the polygon by
        calculating the normals of the edges of the polygon and checking if the dot
        product of the ray's direction vector and the normals is positive for all
        edges. If the dot product is zero or negative for any edge, the ray is not in
        the same plane as the polygon and the method returns null.
        2. If the ray is in the same plane as the polygon, the method creates a new
        Plane object using the first three vertices of the polygon and calls the
        findIntersections method of the Plane class to find the intersection point of
        the ray and the plane.
        3. The method returns the list of intersection points found by the Plane
        class.
         */
    }

}