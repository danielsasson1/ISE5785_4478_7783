package geometries;
import primitives.BoundingBox;
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
        double minX = Math.min(p1.point.d1(), Math.min(p2.point.d1(), p3.point.d1()));
        double minY = Math.min(p1.point.d2(), Math.min(p2.point.d2(), p3.point.d2()));
        double minZ = Math.min(p1.point.d3(), Math.min(p2.point.d3(), p3.point.d3()));

        double maxX = Math.max(p1.point.d1(), Math.max(p2.point.d1(), p3.point.d1()));
        double maxY = Math.max(p1.point.d2(), Math.max(p2.point.d2(), p3.point.d2()));
        double maxZ = Math.max(p1.point.d3(), Math.max(p2.point.d3(), p3.point.d3()));

        Point min = new Point(minX, minY, minZ);
        Point max = new Point(maxX, maxY, maxZ);

        this.box = new BoundingBox(min, max);
    }

    @Override
    public Vector getNormal(Point point) {
        // Call the getNormal method from the Polygon class
        return super.getNormal(point);
    }

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
        // Call the findIntersections method from the Polygon class
        if (super.calculateIntersectionsHelper(ray) == null) return null;
        return List.of(new Intersection(this, super.calculateIntersectionsHelper(ray).getFirst().point));
    }


}