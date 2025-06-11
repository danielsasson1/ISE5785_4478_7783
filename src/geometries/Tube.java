package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;
import java.util.List;
/**
 * Tube class represents a tube geometry in 3D space.
 * It extends the RadialGeometry class and provides methods to calculate the normal vector at a given point on the tube's surface.
 */
public class Tube extends RadialGeometry{
    /**
     * The axis of the tube
     */
    protected final Ray axis;// the axis of the tube

    /**
     * Constructor for Tube
     * @param axis the axis of the tube
     * @param radius the radius of the tube
     */
    public Tube (Ray axis, double radius) {
        super(radius);
        this.axis = axis;
    }
    @Override
    public Vector getNormal(Point point) {
        Vector axisDirection = axis.getVector();
        Point C = axis.getPoint(0);
        if (C.equals(point)) return null; // Normal is undefined at the bottom base center
        double t = point.subtract(C).dotProduct(axisDirection);
        if (t < 0) {
            return axisDirection.scale(-1); // Normal atthe bottom base
        }
        Point o = C.add(axisDirection.scale(t));
        if (point.equals(o)) return null; // Normal is undefined at the point on the axis
        return point.subtract(o).normalize();
    }

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {return null;} // finding the intersection points
}
