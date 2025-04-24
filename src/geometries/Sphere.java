package geometries;
import primitives.Point;
import primitives.Vector;

public abstract class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * Constructor for Sphere
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Getter for the center of the sphere
     * @return the center of the sphere
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}