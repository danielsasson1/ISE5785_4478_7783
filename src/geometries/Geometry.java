// Interface for all geometries
package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * Abstract class representing a geometric shape in 3D space.
 * This class serves as a base for all specific geometric shapes.
 */
public abstract class Geometry {

    /**
     * Calculates the normal vector to the geometry at a given point
     * @param point the point on the geometry
     * @return the normal vector
     */
    public abstract Vector getNormal(Point point);
}