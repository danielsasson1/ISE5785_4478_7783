package geometries;
import primitives.BoundingBox;
import primitives.Ray;
import primitives.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a collection of intersectable geometries.
 * It implements the Intersectable interface and provides methods to add geometries and find intersections with a ray.
 */
public class Geometries extends Intersectable{
    /**
     * List of intersectable geometries
     */
    private final List<Intersectable> geometries = new LinkedList<>();// List of intersectable geometries

    /**
     * Constructor for Geometries
     */
    public Geometries() { }

    /**
     * Constructor for Geometries
     * @param geometries the geometries to add
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Add geometries to the list
     * @param geometries1 the geometries to add
     */
    public void add(Intersectable... geometries1) {
        for (Intersectable geo : geometries1) {
            geometries.add(geo);
            if (box == null) {
                box = geo.getBoundingBox();
            } else if (geo.getBoundingBox() != null) {
                box = box.union(geo.getBoundingBox());
            }
        }
    }

    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
        if (box != null && !box.intersects(ray)) return null;

        List<Intersection> result = new LinkedList<>();
        for (Intersectable geo : geometries) {
            List<Intersection> temp = geo.calculateIntersectionsHelper(ray);
            if (temp != null) result.addAll(temp);
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Builds a Bounding Volume Hierarchy (BVH) from the geometries in this collection.
     * This method recursively splits the geometries into smaller groups
     * and builds a tree structure where each node contains a bounding box
     * and either a list of geometries (if it's a leaf node)
     * or references to child nodes (if it's an internal node).
     */
    public void buildBVH() {
        if (geometries.size() <= 2) return; // Already a leaf

        // Compute axis to sort by
        int axis = chooseSplitAxis(); // e.g. axis with largest spread

        // Sort by center along axis
        geometries.sort(Comparator.comparingDouble(
                g -> g.getBoundingBox().getCenter(axis) // getBoundingBox().getCenter(axis
        )); // Sort geometries by center along the chosen axis

        // Split list
        int mid = geometries.size() / 2;
        List<Intersectable> leftList = new ArrayList<>(geometries.subList(0, mid));
        List<Intersectable> rightList = new ArrayList<>(geometries.subList(mid, geometries.size()));

        // Wrap sublists in Geometries
        Geometries left = new Geometries();
        Geometries right = new Geometries();
        left.addAll(new ArrayList<>(leftList));
        right.addAll(new ArrayList<>(rightList));

        // Recursively build tree
        left.buildBVH();
        right.buildBVH();

        // Replace current flat list with two children
        geometries.clear();
        geometries.add(left);
        geometries.add(right);

        // Recompute this group's bounding box
        this.box = left.getBoundingBox().union(right.getBoundingBox());
    }

    /**
     * Adds a list of geometries to this collection.
     * @param geometries the list of geometries to add
     */
    public void addAll(List<Intersectable> geometries) {
        this.add(geometries.toArray(new Intersectable[0]));
    }

    /**
     * Chooses the axis to split the geometries based on their bounding boxes.
     * The axis with the largest spread (difference between max and min) is chosen.
     * @return 0 for X, 1 for Y, 2 for Z
     */
    private int chooseSplitAxis() {
        double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;

        for (Intersectable geo : geometries) {
            BoundingBox b = geo.getBoundingBox();
            if (b == null) continue;

            double cx = b.getCenter(0);
            double cy = b.getCenter(1);
            double cz = b.getCenter(2);

            minX = Math.min(minX, cx); maxX = Math.max(maxX, cx);
            minY = Math.min(minY, cy); maxY = Math.max(maxY, cy);
            minZ = Math.min(minZ, cz); maxZ = Math.max(maxZ, cz);
        }

        double dx = maxX - minX;
        double dy = maxY - minY;
        double dz = maxZ - minZ;

        if (dx >= dy && dx >= dz) return 0;
        if (dy >= dz) return 1;
        return 2;
    }
}
