package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

public class Tube extends RadialGeometry{
    private final Ray axis;

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
    public Vector getNormal(Point point) {
        return null;
    }
}
