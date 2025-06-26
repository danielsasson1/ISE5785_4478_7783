package geometries;
import primitives.BoundingBox;
import java.util.List;

/**
 * A node in the Bounding Volume Hierarchy (BVH) tree.
 * Each node can either be a leaf node containing a list of geometries,
 * or an internal node containing two child BVH nodes.
 */
public class BVHNode {
    /** The bounding box that encloses the contents of this node */
    private final BoundingBox box;
    /** List of geometries (used only if this is a leaf node) */
    private final List<Geometry> geometries;
    /** Left child node (used only if this is an internal node) */
    private final BVHNode left;
    /** Right child node (used only if this is an internal node) */
    private final BVHNode right;
    /**
     * Constructs a leaf node that holds a list of geometries.
     * @param box        The bounding box surrounding the geometries.
     * @param geometries The list of geometries in this node.
     */
    public BVHNode(BoundingBox box, List<Geometry> geometries) {
        this.box = box;
        this.geometries = geometries;
        this.left = null;
        this.right = null;
    }
    /**
     * Constructs an internal node with two child BVH nodes.
     * @param box  The bounding box surrounding both children.
     * @param left The left child BVH node.
     * @param right The right child BVH node.
     */
    public BVHNode(BoundingBox box, BVHNode left, BVHNode right) {
        this.box = box;
        this.left = left;
        this.right = right;
        this.geometries = null;
    }
    /**
     * Returns true if this node is a leaf node (contains geometries).
     * @return true if leaf node, false if internal node
     */
    public boolean isLeaf() {
        return geometries != null;
    }
    /**
     * Gets the bounding box of this node.
     * @return The bounding box.
     */
    public BoundingBox getBox() {
        return box;
    }
    /**
     * Gets the list of geometries (for leaf nodes).
     * @return List of geometries, or null if this is not a leaf.
     */
    public List<Geometry> getGeometries() {
        return geometries;
    }
    /**
     * Gets the left child node (for internal nodes).
     * @return Left child BVH node, or null if this is a leaf.
     */
    public BVHNode getLeft() {
        return left;
    }
    /**
     * Gets the right child node (for internal nodes).
     * @return Right child BVH node, or null if this is a leaf.
     */
    public BVHNode getRight() {
        return right;
    }
}

