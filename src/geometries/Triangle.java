package geometries;
import primitives.Point;

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
}