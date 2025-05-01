package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

/**
 * Cylinder class represents a cylinder in 3D space.
 * It extends the Tube class and adds a height property.
 */
public class Cylinder extends Tube {
    private final double height;
    /**
     * Constructor for Cylinder
     * @param axis - the axis of the cylinder
     * @param radius - the radius of the cylinder
     */
    /**
     * Constructor for Cylinder
     * @param axis - the axis of the cylinder
     * @param radius - the radius of the cylinder
     * @param height - the height of the cylinder
     */
    public Cylinder (Ray axis, double radius, double height) {
        super(axis, radius);
        this.height = height;
        /*
        @param axis - the axis of the cylinder
        @param radius - the radius of the cylinder
        @param height - the height of the cylinder
        The constructor initializes the cylinder with the given axis, radius, and height.
         */
    }

    /**
     * Getter for the height of the cylinder
     * @return the height of the cylinder
     */
    //@Override
    public Vector getNormal(Point point) {
        // If p0 is the head of the axis
        if (point.equals(axis.getPoint(0)))
            return axis.getVector().scale(-1);

        // If p1 is the end of the axis
        if (point.equals(axis.getPoint(0).add(axis.getVector().scale(height))))
            return axis.getVector();

        // If the point is on the top or bottom surface of the cylinder
        if (axis.getPoint(0).subtract(point).dotProduct(axis.getVector()) == 0)
            return axis.getVector().scale(-1);

        if (axis.getPoint(0).add(axis.getVector().scale(height)).subtract(point).dotProduct(axis.getVector()) == 0)
            return axis.getVector();

        // Otherwise, call the superclass method
        return super.getNormal(point);

    }
    //javadoc
    /**
     * Getter for the height of the cylinder
     * @return the height of the cylinder
     */

    public List<Point> findIntersections(Ray ray) {
        return null;
    } // finding the intersection points
}
