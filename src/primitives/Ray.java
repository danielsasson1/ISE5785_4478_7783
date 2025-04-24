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

    public Point getPoint() {
        return point;
    } // getter for the point

    public Vector getVector() {
        return vector;
    } // getter for the vector
}