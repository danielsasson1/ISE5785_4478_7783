package primitives;

import java.util.List;

/**
 * Ray class represents a ray in 3D space.
 * It is defined by a starting point and a direction vector.
 */
public class Ray {
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
        if (Util.isZero(t)) return point;
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
    public Point findClosestPoint (List<Point> points) {
        if (points == null || points.isEmpty()) return null; // check if the list is empty
        Point closestPoint = points.get(0); // set the first point as the closest point
        double minDistance = point.distance(closestPoint); // set the minimum distance to the distance from the first point
        for (int i = 1; i < points.size(); i++) { // iterate over the points
            double distance = point.distance(points.get(i)); // get the distance from the point to the ray
            if (distance < minDistance) { // check if the distance is smaller than the minimum distance
                minDistance = distance; // set the minimum distance to the new distance
                closestPoint = points.get(i); // set the closest point to the new point
            }
        }
        return closestPoint; // return the closest point
    } // find the closest point on a list of points
}