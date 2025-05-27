package primitives;

/**
 * Point class represents a point in 3D space.
 * It is defined by its coordinates (x, y, z).
 */
public class Point {

    /**
     * The coordinates of the point in 3D space.
     * The coordinates are represented by a Double3 object.
     */
    public final Double3 point; //The point is represented by a Double3 object
    /**
     * A static point at (0,0,0).
     * This point is used as a reference point in various calculations.
     */
    public static final Point ZERO = new Point(Double3.ZERO);// A static point at (0,0,0)

    // Constructors

    /**
     * Constructs a point with the given coordinates.
     * @param D1 is the x-coordinate
     * @param D2 is the y-coordinate
     * @param D3 is the z-coordinate
     */
    public Point(double D1, double D2, double D3) {
        point = new Double3(D1,D2,D3);
    }// constructor

    /**
     * Constructs a point with the given Double3 object.
     * @param D1 is the Double3 object representing the point
     */
    public Point(Double3 D1) {
        point = D1; // constructor
    }

    /**
     * Subtracts a vector from the point.
     * @param p1 is the point to be subtracted
     * @return a new vector representing the difference between the point and the given point
     */
    public Vector subtract(Point p1){
        return new Vector(point.subtract(p1.point));
    } // subtracting a point from another point


    /**
     * Adds a vector to the point.
     * @param v1 is the vector to be added
     * @return a new point representing the sum of the point and the given vector
     */
    public Point add(Vector v1){
        return new Point(this.point.add(v1.point));
    } // adding a vector to a point

    /**
     * Calculates the squared distance between the point and another point.
     * @param p1 is the point to calculate the distance to
     * @return the squared distance between the two points
     */
    public double distanceSquared(Point p1) {
        return ((p1.point.d1() - point.d1()) * (p1.point.d1() - point.d1())) +
                ((p1.point.d2() - point.d2()) * (p1.point.d2() - point.d2())) +
                ((p1.point.d3() - point.d3()) * (p1.point.d3() - point.d3()));
    } // calculating the squared distance between two points

    /**
     * Calculates the distance between the point and another point.
     * @param p1 is the point to calculate the distance to
     * @return the distance between the two points
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    } // calculating the distance between two points


    //@Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // checking if the object is the same
        return obj instanceof Point other && this.point.equals(other.point); // checking if the point is equal to another point
    } //overriding the equals method


    //@Override
    public String toString() {
        return "Point" + point;
    }// overriding the toString method
}