package geometries;
import geometries.Geometry;

/**
 * Abstract class representing a radial geometry.
 * This class serves as a base for geometries that have a radial symmetry.
 */
public abstract class RadialGeometry extends Geometry {
    protected final double radius;
    protected final double radiusSquared;
    /**
     * Getter for radius squared
     * @return the square of the radius
     */
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
