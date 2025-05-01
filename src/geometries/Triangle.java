package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;
import primitives.Vector;
/*
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
    /**
     * Constructor for Triangle with a list of points
     * @param points list of points of the triangle
     */
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = plane.findIntersections(ray);
        if (intersections == null || intersections.size() == 0) {
            return null; // no intersection with the plane
        }
        Point intersection = intersections.getFirst();
        if (intersection.equals(vertices.get(0)) || intersection.equals(vertices.get(1)) || intersection.equals(vertices.get(2))) {
            return null; // intersection is a vertex of the triangle
        }
        // Check if the intersection point is inside the triangle
        Vector v1 = vertices.get(1).subtract(vertices.get(0));
        Vector v2 = vertices.get(2).subtract(vertices.get(0));
        Vector v3 = intersection.subtract(vertices.get(0));
        double dot00 = v1.dotProduct(v1);
        double dot01 = v1.dotProduct(v2);
        double dot02 = v1.dotProduct(v3);
        double dot11 = v2.dotProduct(v2);
        double dot12 = v2.dotProduct(v3);
        //long explanation:
        /*
         * The barycentric coordinates of the intersection point are calculated using the
         * dot products of the vectors. The barycentric coordinates are used to check if
         * the intersection point is inside the triangle.
         * The barycentric coordinates are calculated as follows:
            * u = (dot11 * dot02 - dot01 * dot12) / (dot00 * dot11 - dot01 * dot01)
            * v = (dot00 * dot12 - dot01 * dot02) / (dot00 * dot11 - dot01 * dot01)
            * If u >= 0 and v >= 0 and u + v <= 1, then the intersection point is inside the
            * triangle.
            * If the intersection point is inside the triangle, return the intersection
            * point. Otherwise, return null.
            * The method returns a list of intersection points, which can be empty if there
            * are no intersection points, or contain one point if there is an intersection
            * point.
         */
        double invdet = 1.0 / (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invdet;
        double v = (dot00 * dot12 - dot01 * dot02) * invdet;
        double w = 1 - u - v;
        if (u >= 0 && v >= 0 && w >= 0 && u + v <= 1) {
            return List.of(intersection); // intersection is inside the triangle
        }
        return null; // intersection is outside the triangle
    }
}