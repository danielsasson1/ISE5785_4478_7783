package primitives;

import java.util.List;

public class Ray {
    protected final Point point;
    protected final Vector vector;

    public Ray (Point p1, Vector v1) {
        point = p1;
        vector = v1.normalize();
    } // constructor

    //@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Ray other && point.equals(other.point) && vector.equals(other.vector);
    } // checking if the object is the same

    //@Override
    public String toString() {
        return "Ray" + point + vector;
    } // overriding the toString method

    public Point getPoint(double t) {
        if (Util.isZero(t)) return point;
        return point.add(vector.scale(t));
    } // getter for the point at a certain distance from the head
    /**
     * Getter for the point on the ray at a certain distance from the head
     * @return the point on the ray at the distance t from the head
     */

    public Vector getVector() {
        return vector;
    } // getter for the vector

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