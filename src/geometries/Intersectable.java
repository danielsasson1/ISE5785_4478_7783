package geometries;

import java.util.List;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;
import geometries.Geometry;


/**
 * Intersectable interface represents a geometric object that can be intersected by a ray.
 * It provides methods to find intersection points and calculate the normal vector at a given point.
 */
public abstract class Intersectable {
    /**
     * Calculates the normal vector at a given point on the geometry.
     */
    public static class Intersection {
        public final Geometry geometry;
        public final Point point;

        /**
         * Constructor for Intersection
         * @param geometry the geometry at the intersection point
         * @param point    the intersection point
         */
        public Intersection(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Intersection that = (Intersection) obj;
            return geometry == that.geometry && point.equals(that.point);
        }

        @Override
        public String toString() {
            return "Intersection{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Calculates the intersection points with a given ray.
     * This method should be implemented by subclasses to provide specific intersection logic.
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or an empty list if there are no intersections
     */
    abstract protected List<Intersection> calculateIntersectionsHelper(Ray ray);

    /**
     * Calculates the intersection points with a given ray.
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or an empty list if there are no intersections
     */
    public final List<Intersection> calculateIntersections(Ray ray) {
        List<Intersection> intersections = calculateIntersectionsHelper(ray);
        return intersections; // Return the list of intersections
    }

    /**
     * Calculates the intersection points with a given ray.
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or null if there are no intersections
     */
    public final List<Point> findIntersections(Ray ray) {
        var list = calculateIntersections(ray);
        return list == null ? null : list.stream().map(intersection -> intersection.point).toList();
    }
}
