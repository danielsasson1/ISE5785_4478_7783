package geometries;

import java.util.List;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;

/**
 * Intersectable interface represents a geometric object that can be intersected by a ray.
 * It provides methods to find intersection points and calculate the normal vector at a given point.
 */
public interface Intersectable {
    public List<Point> findIntersections(Ray ray);
    /**
     * Finds the intersection points of a ray with the geometry.
     * @param ray the ray to check for intersections
     * @return a list of intersection points
     */
}
