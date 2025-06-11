package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;
import primitives.Vector;

/**
 * Triangle class represents a triangle in 3D space.
 * It extends the Polygon class and is defined by three points.
 */
public class Triangle extends Polygon {
    /**
     * Constructor for Triangle
     * @param p1 first point of the triangle
     * @param p2 second point of the triangle
     * @param p3 third point of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public Vector getNormal(Point point) {
        // Call the getNormal method from the Polygon class
        return super.getNormal(point);
    }

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
        // Call the findIntersections method from the Polygon class
        return super.calculateIntersectionsHelper(ray);
    }
}