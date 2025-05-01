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
    private final Point center;
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

    /**
     * Getter for the center of the sphere
     * @return the center of the sphere
     */
    //@Override
    public Vector getNormal(Point point) {
        // Calculate the normal vector at the given point on the sphere
        return point.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getPoint();
        Vector v = ray.getVector();
        if (center.equals(p0)) {
            return List.of(p0.add(v.scale(radius)));
        }
        Vector u = center.subtract(p0);
        double tm = u.dotProduct(v);
        double d = Math.sqrt(Util.alignZero(u.lengthSquared() - tm * tm));
        if (d > radius) {
            return null; // No intersection
        }
        double th = Math.sqrt(radius * radius - d * d);
        double t1 = Util.alignZero(tm - th);
        double t2 = Util.alignZero(tm + th);
        if (t1 > 0 && t2 > 0 && !(p0.add(v.scale(t1)).equals(p0.add(v.scale(t2))))){
            return List.of(p0.add(v.scale(t1)), p0.add(v.scale(t2)));
        }
        if (t1 > 0) {
            return List.of(p0.add(v.scale(t1)));
        }
        if (t2 > 0) {
            return List.of(p0.add(v.scale(t2)));
        }
        return null; // No intersection
        /*
        long explanation about the calculation of this method:
        1. The method first checks if the center of the sphere is equal to the point of the ray. If they are equal, it returns a list containing the point of the ray plus the radius in the direction of the ray.
        2. It then calculates the vector from the point of the ray to the center of the sphere (u).
        3. It calculates the projection of u onto the direction of the ray (tm).
        4. It calculates the distance from the center of the sphere to the ray (d) using the Pythagorean theorem.
        5. If d is greater than or equal to the radius of the sphere, there is no intersection, and the method returns null.
        6. It calculates the distance from the projection point to the intersection points (th) using the Pythagorean theorem.
        7. It calculates the two intersection points (t1 and t2) using the projection point and the distance to the intersection points.
        8. If both t1 and t2 are greater than 0, it returns a list containing both intersection points.
        9. If only t1 is greater than 0, it returns a list containing only the first intersection point.
        10. If only t2 is greater than 0, it returns a list containing only the second intersection point.
        11. If none of the above conditions are met, it returns null, indicating no intersection.
        12. The method uses the Util.alignZero method to handle floating-point precision issues.
        13. The method returns a list of intersection points, which can be either one or two points depending on the ray's position relative to the sphere.
         */
    }
}