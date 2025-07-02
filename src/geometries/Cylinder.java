package geometries;
import primitives.*;
import java.util.List;
import java.util.LinkedList;

/**
 * Cylinder class represents a cylinder in 3D space.
 * It extends the Tube class and adds a height property.
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder
     */
    private final double height;

    /**
     * Constructor for Cylinder
     *
     * @param axis   - the axis of the cylinder
     * @param radius - the radius of the cylinder
     * @param height - the height of the cylinder
     */
    public Cylinder(Ray axis, double radius, double height) {
        super(axis, radius);
        if (height <= 0) {
            throw new IllegalArgumentException("Radius must be greater than zero");
        }
        this.height = height;
        Point base = axis.getPoint(0);
        Vector dir = axis.getVector();
        Point top = axis.getPoint(height);

// Get base & top coordinates
        double x1 = base.point.d1();
        double y1 = base.point.d2();
        double z1 = base.point.d3();
        double x2 = top.point.d1();
        double y2 = top.point.d2();
        double z2 = top.point.d3();

// Compute min and max including radius buffer
        double minX = Math.min(x1, x2) - radius;
        double minY = Math.min(y1, y2) - radius;
        double minZ = Math.min(z1, z2) - radius;
        double maxX = Math.max(x1, x2) + radius;
        double maxY = Math.max(y1, y2) + radius;
        double maxZ = Math.max(z1, z2) + radius;
        this.box = new BoundingBox(
                new Point(minX, minY, minZ),
                new Point(maxX, maxY, maxZ)
        );
    }

    @Override
    public Vector getNormal(Point point) {
        if (point.equals(axis.getPoint(0))) return null; // Normal is undefined at the axis point
        Vector CtoP = point.subtract(axis.getPoint(0));
        double t = CtoP.dotProduct(axis.getVector());
        if (t > 0 && t < height) {
            if (Util.isZero(t)) {
                return point.subtract(axis.getPoint(0)).normalize(); // Normal at the point perpendicular to the axis
            }
            Point q = axis.getPoint(t);
            if (point.equals(q)) return null; // Normal is undefined at the point on the axis
            return point.subtract(q).normalize(); // Normal at the point on the tube surface
        }
        if (t <= 0) return axis.getVector().scale(-1);     // bottom cap
        if (t >= height) return axis.getVector();
        return null; // Normal is undefined outside the cylinder's height
    }

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
        List<Intersection> intersections = super.calculateIntersectionsHelper(ray);
        if (intersections == null) {
            return null; // No intersections found in the tube
        }
        List<Intersection> filteredIntersections = new LinkedList<>();
        for (Intersection intersection : intersections) {
            Vector T1 = intersection.point.subtract(axis.getPoint(0));
            double t = T1.dotProduct(axis.getVector()); // Calculate the projection of the intersection point onto the axis
            if (t >= 0 && t <= height) {
                filteredIntersections.add(new Intersection(this, intersection.point)); // Keep only intersections within the cylinder's height
            }
        }
        if (intersections.size() == filteredIntersections.size()) {
            return intersections; // No filtering needed, return original intersections
        }
        List<Intersection> capsIntersections = calculateCapsIntersections(ray);
        if (capsIntersections != null) {
            for (Intersection intersection : capsIntersections) {
                filteredIntersections.add(new Intersection(this, intersection.point)); // Add intersections with the cylinder's caps
            }
        }
        return filteredIntersections.isEmpty() ? null : filteredIntersections; // Return null if no valid intersections found
    }

    /**
     * Calculates intersections with the cylinder's caps.
     *
     * @param ray - the ray to check for intersections
     * @return a list of intersections with the cylinder's caps, or null if no intersections found
     */
    private List<Intersection> calculateCapsIntersections(Ray ray) {
        Plane topcap = new Plane(axis.getPoint(height), axis.getVector());
        List<Intersection> top = topcap.calculateIntersectionsHelper(ray);
        Plane bottomcap = new Plane(axis.getPoint(0), axis.getVector().scale(-1));
        List<Intersection> bottom = bottomcap.calculateIntersectionsHelper(ray);
        List<Intersection> intersections = new LinkedList<>();
        if (top != null && (top.getFirst().point.distance(axis.getPoint(height)) < radius || Util.isZero(top.getFirst().point.distance(axis.getPoint(height)) - radius)))
            intersections.add(new Intersection(this, top.getFirst().point));
        if (bottom != null && (bottom.getFirst().point.distance(axis.getPoint(0)) < radius || Util.isZero(bottom.getFirst().point.distance(axis.getPoint(0)) - radius)))
            intersections.add(new Intersection(this, bottom.getFirst().point));
        return intersections.isEmpty() ? null : intersections; // Return null if no valid intersections found
    }
}
