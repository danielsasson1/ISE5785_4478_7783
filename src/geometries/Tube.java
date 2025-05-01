package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;
import java.util.List;
/*
 * Tube class represents a tube geometry in 3D space.
 * It extends the RadialGeometry class and provides methods to calculate the normal vector at a given point on the tube's surface.
 */
public class Tube extends RadialGeometry{
    protected final Ray axis;
    /**
     * Constructor for Tube
     * @param axis the axis of the tube
     */

    /**
     * Constructor for Tube
     * @param axis the axis of the tube
     * @param radius the radius of the tube
     */
    public Tube (Ray axis, double radius) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Getter for the axis of the tube
     * @return the axis of the tube
     */
    //@Override
    public Vector getNormal(Point point) {
        // Calculate the vector from the axis head to the given point
        Vector headToPoint = point.subtract(axis.getPoint());
        // Calculate the projection of the head-to-point vector onto the axis direction
        double t = headToPoint.dotProduct(axis.getVector());
        if (t != 0) {
            // Calculate the point on the axis that is closest to the given point
            Point o = axis.getPoint().add(axis.getVector().scale(t));
            // Calculate the vector from the closest point on the axis to the given point and normalize it
            return point.subtract(o).normalize();
        } else {
            //if t=0 we don't need to create the point o, we already have the closest point on the axis to the given point
            return point.subtract(axis.getPoint());
        }
    }

    public List<Point> findIntersections(Ray ray) {
        return null;
    } // finding the intersection points
}
