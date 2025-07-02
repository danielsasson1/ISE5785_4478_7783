package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;
import lighting.LightSource;


/**
 * Intersectable interface represents a geometric object that can be intersected by a ray.
 * It provides methods to find intersection points and calculate the normal vector at a given point.
 */
public abstract class Intersectable {
    /** The bounding box of the intersectable geometry */
    protected BoundingBox box;
    /**
     * Intersection class represents an intersection point between a ray and a geometry.
     * It contains the geometry at the intersection point and the intersection point itself.
     */
    public static class Intersection {
        /** The geometry at the intersection point */
        public final Geometry geometry;
        /** The intersection point */
        public final Point point;
        /** The material at the intersection point */
        public final Material material;
        /** The direction of the intersecting ray */
        public Vector v;
        /** The normal vector at the intersection point */
        public Vector n;
        /** The scalar product of the normal vector and the intersecting ray direction */
        public double nDotV;
        /** The light in the intersection point */
        public LightSource light;
        /** The direction from the light to the intersection point */
        public Vector l;
        /** The scalar product of the light vector and the normal */
        public double lDotN;


        /**
         * Constructor for Intersection
         * @param geometry the geometry at the intersection point
         * @param point    the intersection point
         */
        public Intersection(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
            if (geometry != null) this.material = geometry.getMaterial();
            else this.material = null;
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
    protected abstract List<Intersection> calculateIntersectionsHelper(Ray ray);

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
        if (box != null && !box.intersects(ray)) {
            return null; // skip intersection if ray misses box
        }

        List<Intersection> intersections = calculateIntersectionsHelper(ray);
        if (intersections == null || intersections.isEmpty()) {
            return null; // No intersections found
        }
        List<Point> points = new LinkedList<>();
        for (int i = 0; i < intersections.size(); i++) {
            points.add(intersections.get(i).point);
        }
        return points;
    }

    /**
     * Gets the normal vector at a given point on the geometry.
     * @return the normal vector at the intersection point
     */
    public BoundingBox getBoundingBox(){
        return this.box;
    }
}
