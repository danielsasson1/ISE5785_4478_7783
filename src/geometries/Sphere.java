package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * Sphere class represents a sphere in 3D space
 * It extends the RadialGeometry class
 */
public class Sphere extends RadialGeometry {
    /**
     * The center of the sphere
     */
    private final Point center;// the center of the sphere
    /**
     * Getter for the center of the sphere
     * @return the center of the sphere
     */
    /**
     * Constructor for Sphere
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        // Calculate the normal vector at the given point on the sphere
        if (point.equals(center)) return null; // Normal is undefined at the center of the sphere
        return point.subtract(center).normalize();
    }

    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        Point p0 = ray.getPoint(0);
        Vector v = ray.getVector();
        if (p0.equals(center)) {
            // If the ray starts at the center of the sphere, return the normal vector
            return List.of(new Intersection(this, center.add(v.scale(radius))));
        }
        Vector L = p0.subtract(center);
        double b = 2 * L.dotProduct(v);
        double c = L.lengthSquared() - radiusSquared;
        double discriminant = b * b - 4 * c;
        if (discriminant < 0) return null;
        if (discriminant == 0) {
            if(-b/2 < 0) return null; // The ray is outside the sphere
            return List.of(new Intersection(this, p0.add(v.scale(-b / 2))));
        }
        double t1 = (-b + Math.sqrt(discriminant)) / 2;
        double t2 = (-b - Math.sqrt(discriminant)) / 2;
        if (t1 <= 0 && t2 <= 0) return null;// Both intersections are behind or on the ray's origin
        if (t1 <= 0) return List.of(new Intersection(this, p0.add(v.scale(t2))));// t1 is behind or on the ray's origin, return only t2
        if (t2 <= 0) return List.of(new Intersection(this, p0.add(v.scale(t1))));// t2 is behind or on the ray's origin, return only t1
        return List.of(
            new Intersection(this, p0.add(v.scale((t1)))),
            new Intersection(this, p0.add(v.scale((t2))))
        );
    }
}