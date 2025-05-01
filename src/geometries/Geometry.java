// Interface for all geometries
package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;

/**
 * Abstract class representing a geometric shape in 3D space.
 * This class serves as a base for all specific geometric shapes.
 */
public abstract class Geometry implements Intersectable {

    /**
     * Calculates the normal vector to the geometry at a given point
     * @param point the point on the geometry
     * @return the normal vector
     */
    public abstract Vector getNormal(Point point);
    //finding the intersection points:
    //@Override
    public abstract List<Point> findIntersections(Ray ray);

}