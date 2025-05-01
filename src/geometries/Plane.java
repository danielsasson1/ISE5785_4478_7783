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
/**
 * The Plane class extends the Geometry class and provides methods to
 * calculate the normal vector at a given point on the plane.
 */
public class Plane extends Geometry {
    private final Point point;
    private final Vector normal;
    /**
     * Constructor for Plane class
     * @param point a point on the plane
     * @param normal a normal vector to the plane
     */
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
        this.point = p1;
        this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
    }

    /**
     * Returns the point on the plane
     * @return the point on the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector rayDirection = ray.getVector();
        Point rayOrigin = ray.getPoint(0);
        if (Util.isZero(rayDirection.dotProduct(normal)) || point.equals(rayOrigin)) {
            return null; // The ray is parallel to the plane - infinite intersections. or the ray starts on the plane - illegal.
        }
        // Calculate the distance from the ray origin to the plane
        double Temp = normal.dotProduct(point.subtract(rayOrigin)) / normal.dotProduct(rayDirection);
        return Temp > 0 ? List.of(rayOrigin.add(rayDirection.scale(Temp))) : null; // The ray intersects the plane

    }
    /**
     * Returns the normal vector to the plane at a given point
     * @param point the point on the plane
     * @return the normal vector
     */
}
/**
 * The Plane class represents a geometric plane in 3D space.
 * It is defined by a point on the plane and a normal vector.
 */