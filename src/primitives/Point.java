package primitives;

public class Point {
    public final Double3 point; //The point is represented by a Double3 object

    public static final Point ZERO = new Point(Double3.ZERO);// A static point at (0,0,0)

    public Point(double D1, double D2, double D3) {
        point = new Double3(D1,D2,D3);
    }// constructor

    public Point(Double3 D1) {
        point = D1; // constructor
    }

    public Vector subtract(Point p1){
        return new Vector(point.subtract(p1.point));
    } // subtracting a point from another point

    public Point add(Vector v1){
        return new Point(this.point.add(v1.point));
    } // adding a vector to a point

    public double distanceSquared(Point p1) {
        return ((p1.point.d1() - point.d1()) * (p1.point.d1() - point.d1())) +
                ((p1.point.d2() - point.d2()) * (p1.point.d2() - point.d2())) +
                ((p1.point.d3() - point.d3()) * (p1.point.d3() - point.d3()));
    } // calculating the squared distance between two points

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