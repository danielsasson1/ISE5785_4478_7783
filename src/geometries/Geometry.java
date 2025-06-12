// Interface for all geometries
package geometries;
import primitives.*;
import java.util.List;

/**
 * Abstract class representing a geometric shape in 3D space.
 * This class serves as a base for all specific geometric shapes.
 */
public abstract class Geometry extends Intersectable {

    /**
     * The color of the geometry.
     */
    protected Color emission = Color.BLACK; // default emission color
    /** The material of the geometry */
    private Material material = new Material(); // default material

    /**
     * Gets the material of the geometry.
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of the geometry.
     * @param material the new material
     * @return this.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Gets the emission color of the geometry.
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     * @param emission the new emission color
     * @return this.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Calculates the normal vector to the geometry at a given point
     * @param point the point on the geometry
     * @return the normal vector
     */
    public abstract Vector getNormal(Point point);

    @Override
    abstract protected List<Intersection> calculateIntersectionsHelper(Ray ray);
}