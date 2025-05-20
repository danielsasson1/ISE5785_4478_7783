package renderer;

import scene.Scene;
import primitives.Color;
import primitives.Ray;

public abstract class RayTracerBase {
    /**
     * @param scene The scene to be rendered
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
