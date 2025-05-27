package renderer;

import scene.Scene;
import primitives.Color;
import primitives.Ray;

/**
 * RayTracerBase class is an abstract base class for ray tracing algorithms.
 * It provides a common interface for all ray tracers and holds a reference to the scene being rendered.
 */
public abstract class RayTracerBase {
    /**
     * //@param scene The scene to be rendered
     */
    protected final Scene scene;
    /**
     * Constructor for RayTracerBase
     * @param scene The scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
    /**
     * Trace a ray through the scene
     * @param ray The ray to be traced
     * @return The color of the pixel
     */
    abstract public Color traceRay(Ray ray);




}
