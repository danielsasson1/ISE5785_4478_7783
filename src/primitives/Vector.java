package primitives;
import static primitives.Double3.ZERO;

public class Vector extends Point {

    // Constructors
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3); // calling the constructor of the parent class
        if (d1 == 0 && d2 == 0 && d3 == 0) throw new IllegalArgumentException("The zero vector is not allowed"); // checking if the vector is zero
    } // constructor

    public Vector(Double3 D1) {
        super(D1); // calling the constructor of the parent class
        if (D1.equals(Double3.ZERO)) throw new IllegalArgumentException("The zero vector is not allowed");
    } // constructor

    public Vector add(Vector v1) {
        return new Vector(this.point.add(v1.point)); // adding two vectors
    }

    public Vector scale(double d1) {
        return new Vector(this.point.scale(d1));
    }// scaling a vector by a scalar

    public double dotProduct(Vector v1) {
        return this.point.d1() * v1.point.d1() + this.point.d2() * v1.point.d2() + this.point.d3() * v1.point.d3();
    }// calculating the dot product of two vectors

    public Vector crossProduct(Vector v1) {
        return new Vector(this.point.d2() * v1.point.d3() - this.point.d3() * v1.point.d2(), this.point.d3() * v1.point.d1() - this.point.d1() * v1.point.d3(), this.point.d1() * v1.point.d2() - this.point.d2() * v1.point.d1());// calculating the cross product
    }// cross product of two vectors

    public double lengthSquared() {
        return this.dotProduct(this); // calculating the length squared
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    } // calculating the length of the vector

    public Vector normalize() {
        return new Vector(this.point.reduce(length()));
    }// normalizing the vector

    //@Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // checking if the object is the same
        return obj instanceof Vector other && point.equals(other.point); // checking if the vector is equal to another vector
    }

    //@Override
    public String toString() {
        return "Vector" + point;
    }

    public static final Vector AXIS_X = new Vector(1,0,0); // A static vector at (1,0,0)
    public static final Vector AXIS_Y = new Vector(0,1,0); // A static vector at (0,1,0)
    public static final Vector AXIS_Z = new Vector(0,0,1); // A static vector at (0,0,1)
}