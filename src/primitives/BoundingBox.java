package primitives;

/**
 * BoundingBox represents a rectangular area defined by two points: the minimum and maximum corners.
 * It is used to define a 2D or 3D space in which objects can exist.
 */
public class BoundingBox {
    /**
     * The minimum corner of the bounding box.
     */
    public final Point min;
    /**
     * The maximum corner of the bounding box.
     */
    public final Point max;

    /**
     * Constructs a BoundingBox with the specified minimum and maximum points.
     * @param min The minimum corner of the bounding box.
     * @param max The maximum corner of the bounding box.
     */
    public BoundingBox(Point min, Point max) {
        if (min.point.d1() > max.point.d1() ||
                min.point.d2() > max.point.d2() ||
                min.point.d3() > max.point.d3()) {
            throw new IllegalArgumentException("BoundingBox: min point must have smaller coordinates than max point");
        }
        this.min = min;
        this.max = max;
    }
    public BoundingBox union(BoundingBox other) {
        Point newMin = new Point(
                Math.min(this.min.point.d1(), other.min.point.d1()),
                Math.min(this.min.point.d2(), other.min.point.d2()),
                Math.min(this.min.point.d3(), other.min.point.d3())
        );

        Point newMax = new Point(
                Math.max(this.max.point.d1(), other.max.point.d1()),
                Math.max(this.max.point.d2(), other.max.point.d2()),
                Math.max(this.max.point.d3(), other.max.point.d3())
        );

        return new BoundingBox(newMin, newMax);
    }
    /**
     * Returns the center coordinate along a given axis.
     * @param axis 0 for X, 1 for Y, 2 for Z
     * @return the center value along the selected axis
     */
    public double getCenter(int axis) {
        switch (axis) {
            case 0: return (min.point.d1() + max.point.d1()) / 2.0;// X-axis
            case 1: return (min.point.d2() + max.point.d2()) / 2.0;// Y-axis
            case 2: return (min.point.d3() + max.point.d3()) / 2.0;// Z-axis
            default: throw new IllegalArgumentException("Invalid axis: " + axis);// Must be 0, 1, or 2
        }
    }
    /**
     * Checks whether the given ray intersects this axis-aligned bounding box (AABB).
     * @param ray the ray to test
     * @return true if the ray intersects the box, false otherwise
     */
    public boolean intersects(Ray ray) {
        double tMin = Double.NEGATIVE_INFINITY;
        double tMax = Double.POSITIVE_INFINITY;
        double[] origin = { ray.getPoint(0).point.d1(), ray.getPoint(0).point.d2(), ray.getPoint(0).point.d3() };
        double[] direction = { ray.getVector().point.d1(), ray.getVector().point.d2(), ray.getVector().point.d3()};
        double[] boxMin = { min.point.d1(), min.point.d2(), min.point.d3() };
        double[] boxMax = { max.point.d1(), max.point.d2(), max.point.d3() };

        for (int i = 0; i < 3; i++) {
            double dir = direction[i];
            if (Util.isZero(dir)) {
                // Ray is parallel to slab — check if origin is inside slab
                if (origin[i] < boxMin[i] || origin[i] > boxMax[i]) return false;// Outside the box
            } else {
                double t1 = (boxMin[i] - origin[i]) / dir;// Calculate entry point
                double t2 = (boxMax[i] - origin[i]) / dir;// Calculate exit point

                if (t1 > t2) {
                    double temp = t1;
                    t1 = t2;
                    t2 = temp;
                }

                tMin = Math.max(tMin, t1);
                tMax = Math.min(tMax, t2);

                if (tMin > tMax) return false; // Missed the box
            }
        }

        return true; // Ray intersects box
    }

}
