package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

public class Cylinder extends Tube {
    private final double height;

    /**
     * Constructor for Cylinder
     * @param axis - the axis of the cylinder
     * @param radius - the radius of the cylinder
     * @param height - the height of the cylinder
     */
    public Cylinder (Ray axis, double radius, double height) {
        super(axis, radius);
        this.height = height;
    }

    /**
     * Getter for the height of the cylinder
     * @return the height of the cylinder
     */
    public Vector getNormal (Point point) {
        return null;
    }

}
