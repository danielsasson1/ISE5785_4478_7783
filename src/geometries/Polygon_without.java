package geometries;
import geometries.*;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * this class makes a polygon with a polygon hole in it.
 */
public class Polygon_without extends Geometry{
    /** Out is the polygon that is the outer boundary of the polygon without */
    private final Polygon out;
    /** In is the polygon that is the inner boundary of the polygon without */
    private final Polygon in;

    /**
     * Constructor for Polygon_without
     * @param out the outer polygon
     * @param in the inner polygon
     */
    public Polygon_without(Polygon out, Polygon in) {
        this.out = out;
        this.in = in;
        this.box = out.getBoundingBox();
    }

    @Override
    public Vector getNormal(Point point) {
        // The normal is the same as the outer polygon's normal
        return out.getNormal(point);
    }
    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
        List<Intersection> outIntersections = out.calculateIntersectionsHelper(ray);
        List<Intersection> inIntersections = in.calculateIntersectionsHelper(ray);
        List<Intersection> intersections = new LinkedList<>();
        if (inIntersections == null && outIntersections != null) {
            intersections.add(new Intersection(this, outIntersections.getFirst().point));
            return intersections; // Only outer polygon has intersections
        }
        return null; // If both polygons have intersections, we return null to indicate that the ray is inside the hole
    }
}
