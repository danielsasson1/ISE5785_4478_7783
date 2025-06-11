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
    /**
     * The height of the cylinder
     */
    private final double height;

    /**
     * Constructor for Cylinder
     * @param axis - the axis of the cylinder
     * @param radius - the radius of the cylinder
     * @param height - the height of the cylinder
     */
    public Cylinder (Ray axis, double radius, double height) {
        super(axis, radius);
        if (height <= 0) {
            throw new IllegalArgumentException("Radius must be greater than zero");
        }
        this.height = height;
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
        if (t > height) {
            return axisDirection; // Normal at the top base
        }
        Point o = C.add(axisDirection.scale(t));
        if (point.equals(o)) return null; // Normal is undefined at the point on the axis
        return point.subtract(o).normalize();
    }

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {return null;}
}
