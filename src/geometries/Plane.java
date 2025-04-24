package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane extends Geometry {
    private final Point point;
    private final Vector normal;

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
        this.normal = null;
        this.point = p1;
    }

    /**
     * Returns the point on the plane
     * @return the point on the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}