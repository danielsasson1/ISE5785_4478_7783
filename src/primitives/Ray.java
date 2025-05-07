package primitives;

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
}