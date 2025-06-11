package geometries;
import primitives.Ray;
import primitives.Point;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a collection of intersectable geometries.
 * It implements the Intersectable interface and provides methods to add geometries and find intersections with a ray.
 */
public class Geometries extends Intersectable{
    /**
     * List of intersectable geometries
     */
    private final List<Intersectable> geometries = new LinkedList<>();// List of intersectable geometries

    /**
     * Constructor for Geometries
     */
    public Geometries() { }

    /**
     * Constructor for Geometries
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

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
        List<Intersection> intersections = null;
        List<Intersection> geometryIntersections;
        for (Intersectable geometry : geometries) {
            geometryIntersections = geometry.calculateIntersectionsHelper(ray);
            if (geometryIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }
}
