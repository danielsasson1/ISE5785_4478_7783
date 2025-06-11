package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;
import primitives.Util;

/**
 * Plane class represents a geometric plane in 3D space.
 * It is defined by a point on the plane and a normal vector.
 */
public class Plane extends Geometry {
    /**
     * The point on the plane
     */
    private final Point point;// a point on the plane
    /**
     * The normal vector to the plane
     */
    private final Vector normal;// a normal vector to the plane

    /**
     * Constructor for Plane class
     * @param point a point on the plane
     * @param normal a normal vector to the plane
     */
    public Plane(Point point, Vector normal) {
        this.point = point;
        this.normal = normal.normalize();
    }

    /**
     * Constructor for Plane class using three points
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane (Point p1, Point p2, Point p3) {
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
            throw new IllegalArgumentException("Points must not be the same");
        }
        this.point = p1;
        this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize(); // Calculate the normal vector using the cross product of two vectors in the plane
    }
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
        Vector rayDirection = ray.getVector();
        Point rayOrigin = ray.getPoint(0);
        double denominator = normal.dotProduct(rayDirection);
        if (Util.isZero(denominator)) {
            return null; // Ray is parallel to the plane, no intersection
        }
        if (rayOrigin.equals(point)) {
            return null; // Ray origin is on the plane, no intersection
        }
        double t = (-(normal.dotProduct(rayOrigin.subtract(point))) / denominator);
        if (t <= 0) {
            return null; // Intersection is behind the ray's origin
        }
        Intersection intersection = new Intersection(this, ray.getPoint(t));
        return List.of(intersection); // Return a list with the intersection point
    }
}