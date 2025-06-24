package geometries;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
/**
 * Tube class represents a tube geometry in 3D space.
 * It extends the RadialGeometry class and provides methods to calculate the normal vector at a given point on the tube's surface.
 */
public class Tube extends RadialGeometry{
    /**
     * The axis of the tube
     */
    protected final Ray axis;// the axis of the tube

    /**
     * Constructor for Tube
     * @param axis the axis of the tube
     * @param radius the radius of the tube
     */
    public Tube (Ray axis, double radius) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point point) {
        if (point.equals(axis.getPoint(0))) return null; // Normal is undefined at the axis point
        Vector CtoP = point.subtract(axis.getPoint(0));
        double t = CtoP.dotProduct(axis.getVector());
        if (Util.isZero(t)) {
            return point.subtract(axis.getPoint(0)).normalize(); // Normal at the point perpendicular to the axis
        }
        Point q = axis.getPoint(t);
        if (point.equals(q)) return null; // Normal is undefined at the point on the axis
        return point.subtract(q).normalize(); // Normal at the point on the tube surface
    }

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
        if (ray.getVector().equals(axis.getVector())) {
            return null; // Ray is along the axis, no intersections
        }
        Vector d_proj, deltaP_proj;
        if (Util.isZero(ray.getVector().dotProduct(axis.getVector()))) d_proj = ray.getVector();
        else if (ray.getVector().equals(axis.getVector().scale(ray.getVector().dotProduct(axis.getVector())))) return null;
        else d_proj = ray.getVector().subtract(axis.getVector().scale(ray.getVector().dotProduct(axis.getVector())));
        if (ray.getPoint(0).equals(axis.getPoint(0))) {
            if (ray.getVector().equals(axis.getVector())) {
                return null; // Ray is along the axis, no intersections
            }
            double t =  radiusSquared / d_proj.lengthSquared();
            return List.of(new Intersection(this, ray.getPoint(0).add(ray.getVector().scale(t))));
        }
        Vector deltaP = ray.getPoint(0).subtract(axis.getPoint(0));
        if (Util.isZero(deltaP.dotProduct(axis.getVector()))) deltaP_proj = deltaP;
        else deltaP_proj = deltaP.subtract(axis.getVector().scale(deltaP.dotProduct(axis.getVector())));
        double a = d_proj.lengthSquared();
        double b = 2 * d_proj.dotProduct(deltaP_proj);
        double c = deltaP_proj.lengthSquared() - radiusSquared;
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return null; // No intersection points
        }
        if (discriminant == 0) {
            double t = -b / (2 * a);
            if (t < 0) {
                return null; // Intersection point is behind the ray's origin
            }
            Point intersectionPoint = ray.getPoint(0).add(ray.getVector().scale(t));
            return List.of(new Intersection(this, intersectionPoint));
        }
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double t1 = (-b + sqrtDiscriminant) / (2 * a);
        double t2 = (-b - sqrtDiscriminant) / (2 * a);
        Point intersectionPoint1, intersectionPoint2;
        intersectionPoint1 = ray.getPoint(t1);
        intersectionPoint2 = ray.getPoint(t2);
        if (t1 < 0 && t2 < 0) {
            return null; // Both intersection points are behind the ray's origin
        }
        if (t1 < 0){
            return List.of(new Intersection(this, intersectionPoint1));
        }
        if (t2 < 0){
            return List.of(new Intersection(this, intersectionPoint2));
        }
        return List.of(new Intersection(this, intersectionPoint1), new Intersection(this, intersectionPoint2));
    } // finding the intersection points
}
