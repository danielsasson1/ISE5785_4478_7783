package geometries;
import geometries.Geometry;
public abstract class RadialGeometry extends Geometry {
    protected final double radius;
    protected final double radiusSquared;

    /**
     * Constructor for RadialGeometry
     * @param radius the radius of the geometry
     */
    public RadialGeometry (double radius) {
        this.radius = radius;
        radiusSquared = radius * radius;
    }
    /**
     * Getter for radius
     * @return the radius of the geometry
     */

}
