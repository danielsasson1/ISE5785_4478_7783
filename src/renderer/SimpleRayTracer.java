package renderer;

import scene.Scene;
import primitives.*;
import renderer.RayTracerBase;

import java.util.List;

public class SimpleRayTracer extends RayTracerBase {
    /**
     * Constructor for SimpleRayTracer
     * @param scene The scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }
    public Color traceRay(Ray ray){
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null || intersections.isEmpty()) {
            return scene.background;
        }
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }
    public Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
