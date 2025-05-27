package geometries;
import geometries.Geometry;

/**
 * Abstract class representing a radial geometry.
 * This class serves as a base for geometries that have a radial symmetry.
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * The radius of the geometry
     */
    protected final double radius;// the radius of the geometry
    /**
     * The square of the radius of the geometry
     */
    protected final double radiusSquared;// the square of the radius
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
