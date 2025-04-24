package geometries;
import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * Constructor for Sphere
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.center = center;
    }

    /**
     * Getter for the center of the sphere
     * @return the center of the sphere
     */
    //@Override
    public Vector getNormal(Point point) {
        // Calculate the normal vector at the given point on the sphere
        return point.subtract(center).normalize();
    }
}