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

}
