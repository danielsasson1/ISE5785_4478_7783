package renderer;

import scene.Scene;
import primitives.*;
import renderer.RayTracerBase;

import java.util.List;

/**
 * SimpleRayTracer class is a basic implementation of a ray tracer.
 * It extends the RayTracerBase class and provides functionality to trace rays in a scene.
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Constructor for SimpleRayTracer
     * @param scene The scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray through the scene and returns the color at the intersection point.
     * If there are no intersections, it returns the background color of the scene.
     * @param ray The ray to be traced
     * @return The color at the intersection point or the background color
     */
    public Color traceRay(Ray ray){
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null || intersections.isEmpty()) {
            return scene.background;
        }
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color at a given point in the scene.
     * This method currently returns the ambient light intensity of the scene.
     * @param point The point at which to calculate the color
     * @return The color at the specified point
     */
    public Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
