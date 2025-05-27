package primitives;
import static primitives.Double3.ZERO;

/**
 * Vector class represents a vector in 3D space.
 * It is defined by its coordinates (x, y, z).
 */
public class Vector extends Point {
    // Constructors

    /**
     * Constructor for a vector with the given coordinates.
     * @param d1 is the x-coordinate
     * @param d2 is the y-coordinate
     * @param d3 is the z-coordinate
     */
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3); // calling the constructor of the parent class
        if (d1 == 0 && d2 == 0 && d3 == 0) throw new IllegalArgumentException("The zero vector is not allowed"); // checking if the vector is zero
    } // constructor

    /**
     * Constructor for a vector with the given Double3 object.
     * @param D1 is the Double3 object representing the vector
     */
    public Vector(Double3 D1) {
        super(D1); // calling the constructor of the parent class
        if (D1.equals(Double3.ZERO)) throw new IllegalArgumentException("The zero vector is not allowed");
    } // constructor

    /**
     * adds a vector to the current vector.
     * @param v1 is the vector to be added
     * @return a new vector representing the sum of the two vectors
     */
    public Vector add(Vector v1) {
        return new Vector(this.point.add(v1.point)); // adding two vectors
    }

    /**
     * scales the vector by a scalar.
     * @param d1 is the scalar to scale the vector by
     * @return a new vector representing the scaled vector
     */
    public Vector scale(double d1) {
        return new Vector(this.point.scale(d1));
    }// scaling a vector by a scalar

    /**
     * dot product of the vector with another vector.
     * @param v1 is the vector to calculate the dot product with
     * @return the dot product of the two vectors
     */
    public double dotProduct(Vector v1) {
        return this.point.d1() * v1.point.d1() + this.point.d2() * v1.point.d2() + this.point.d3() * v1.point.d3();
    }// calculating the dot product of two vectors

    /**
     * cross product of the vector with another vector.
     * @param v1 is the vector to calculate the cross product with
     * @return a new vector representing the cross product of the two vectors
     */
    public Vector crossProduct(Vector v1) {
        return new Vector(this.point.d2() * v1.point.d3() - this.point.d3() * v1.point.d2(), this.point.d3() * v1.point.d1() - this.point.d1() * v1.point.d3(), this.point.d1() * v1.point.d2() - this.point.d2() * v1.point.d1());// calculating the cross product
    }// cross product of two vectors

    /**
     * the length squared of the vector.
     * @return the length squared of the vector
     */
    public double lengthSquared() {
        return this.dotProduct(this); // calculating the length squared
    }

    /**
     * the length of the vector.
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    } // calculating the length of the vector

    /**
     * normalizes the vector.
     * @return a new vector representing the normalized vector
     */
    public Vector normalize() {
        return new Vector(this.point.reduce(length()));
    }// normalizing the vector

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // checking if the object is the same
        return obj instanceof Vector other && point.equals(other.point); // checking if the vector is equal to another vector
    }

    @Override
    public String toString() {
        return "Vector" + point;
    }

    /**
     * A static vector at (1,0,0)
     */
    public static final Vector AXIS_X = new Vector(1,0,0); // A static vector at (1,0,0)
    /**
     * A static vector at (0,1,0)
     */
    public static final Vector AXIS_Y = new Vector(0,1,0); // A static vector at (0,1,0)
    /**
     * A static vector at (0,0,1)
     */
    public static final Vector AXIS_Z = new Vector(0,0,1); // A static vector at (0,0,1)
}