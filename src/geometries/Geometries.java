package geometries;
import primitives.Ray;
import primitives.Point;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a collection of intersectable geometries.
 * It implements the Intersectable interface and provides methods to add geometries and find intersections with a ray.
 */
public class Geometries implements Intersectable{
    /**
     * List of intersectable geometries
     */
    private final List<Intersectable> geometries = new LinkedList<>();// List of intersectable geometries

    /**
     * Constructor for Geometries
     */
    public Geometries() { }

    /**
     * Constructor for Geometries num2
     * @param geometries the geometries to add
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Add geometries to the list
     * @param geometries the geometries to add
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries)
            this.geometries.add(geometry);
    }
    /**
     * Find intersections with the given ray
     * @param ray the ray to check for intersections
     * @return a list of intersection points
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        for (Intersectable geometry : geometries) {
            List<Point> geometryIntersections = geometry.findIntersections(ray);
            if (geometryIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }
    /**
     * Get the list of geometries
     * @return the list of geometries
     */
}
//javadoc:
/**
 * Geometries class represents a collection of intersectable geometries.
 * It implements the Intersectable interface and provides methods to add geometries and find intersections with a ray.
 */
