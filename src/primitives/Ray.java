package primitives;

import java.util.List;
import geometries.Intersectable.*;
import primitives.Point;

/**
 * Ray class represents a ray in 3D space.
 * It is defined by a starting point and a direction vector.
 */
public class Ray {
    /** * DELTA is a small value used to avoid self-intersection issues in ray tracing.
     * It is used to offset the intersection point slightly along the normal vector.
     */
    private static final double DELTA = 0.1;
    /**
     * The ray is represented by a point and a vector.
     */
    protected final Point point; // The starting point of the ray
    /**
     * The direction vector of the ray.
     * The vector is normalized to ensure it has a length of 1.
     */
    protected final Vector vector; // The direction vector of the ray

    /**
     * Constructs a ray with the given point and vector.
     * @param p1 is the starting point of the ray
     * @param v1 is the direction vector of the ray
     */
    public Ray (Point p1, Vector v1) {
        point = p1;
        vector = v1.normalize();
    } // constructor

    /**
     * Constructs a ray with the given point, vector, and normal vector.
     * This constructor is used to avoid self-intersection issues by offsetting the point slightly along the normal vector.
     * @param p1 is the starting point of the ray
     * @param v1 is the direction vector of the ray
     * @param n is the normal vector to offset the point slightly
     */
    public Ray(Point p1, Vector v1, Vector n) {
        // constructor with a normal vector to avoid self-intersection issues
        double VDotN = v1.dotProduct(n); // calculate the dot product of the vector and the normal vector
        point = p1.add(n.scale(VDotN > 0 ? DELTA : -DELTA)); // offset the point slightly along the normal vector
        vector = v1.normalize(); // normalize the vector to ensure it has a length of 1
    } // constructor with a normal vector

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Ray other && point.equals(other.point) && vector.equals(other.vector);
    } // checking if the object is the same

    @Override
    public String toString() {
        return "Ray" + point + vector;
    } // overriding the toString method

    /**
     * Getter for the point on the ray at a certain distance from the head.
     * @param t is the distance from the head of the ray
     * @return the point on the ray at the distance t from the head
     */
    public Point getPoint(double t) {
        if (Util.isZero(t))
            return point;
        return point.add(vector.scale(t));
    } // getter for the point at a certain distance from the head

    /**
     * Getter for the vector of the ray.
     * @return the vector of the ray
     */
    public Vector getVector() {
        return vector;
    } // getter for the vector

    /**
     * Finds the closest point on a list of points to the ray.
     * @param points is the list of points to check
     * @return the closest point on the ray to the list of points
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null ? null
                : findClosestIntersection(points.stream().map(p -> new Intersection(null, p)).toList()).point;
    }

    /**
     * Finds the closest intersection point of the ray with a list of points.
     * @param points is the list of points to check
     * @return the closest intersection point of the ray with the list of points
     */
    public Intersection findClosestIntersection(List<Intersection> points) {
        if (points == null || points.isEmpty()) return null; // check if the list is empty
        int TheFinal = 0; // set the first point as the closest point
        double minDistance = point.distance(points.get(0).point); // set the minimum distance to the distance from the first point
        for (int i = 1; i < points.size(); i++) { // iterate over the points
            double distance = point.distance(points.get(i).point); // get the distance from the point to the ray
            if (distance < minDistance) { // check if the distance is smaller than the minimum distance
                minDistance = distance; // set the minimum distance to the new distance
                TheFinal = i; // set the closest point to the new point
            }
        }
        return points.get(TheFinal); // return the closest intersection point
    }
}