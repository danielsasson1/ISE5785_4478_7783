package renderer;

import lighting.DirectionalLight;
import lighting.LightSource;
import scene.Scene;
import primitives.*;
import primitives.Util.*;
import geometries.Intersectable.Intersection;
import java.util.List;


/**
 * SimpleRayTracer class is a basic implementation of a ray tracer.
 * It extends the RayTracerBase class and provides functionality to trace rays in a scene.
 */
public class SimpleRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;
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
        List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
        if (intersections == null || intersections.isEmpty()) {
            return scene.background;
        }
        Intersection closestPoint = ray.findClosestIntersection(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at a given point in the scene.
     * This method currently returns the ambient light intensity of the scene.
     * @param point The point at which to calculate the color
     * @return The color at the specified point
     */
    public Color calcColor(Intersection point, Ray ray) {
        if (!preprocessIntersection(point, ray.getVector())) return Color.BLACK;

        Color color = scene.ambientLight.getIntensity().scale(point.geometry.getMaterial().KA);
        color = color.add(calcColorLocalEffects(point));
        return color;
    }

    /**
     * Preprocesses the intersection by calculating the normal vector and the dot product with the ray direction.
     * This method also checks if the intersection is valid by ensuring the dot product is not zero.
     * @param intersection The intersection to preprocess
     * @param RayDirection The direction of the ray
     * @return true if the intersection is valid, false otherwise
     */
    public boolean preprocessIntersection (Intersection intersection, Vector RayDirection) {
        intersection.v = RayDirection.normalize();
        intersection.n = intersection.geometry.getNormal(intersection.point);
        intersection.nDotV = intersection.n.dotProduct(RayDirection);
        return !Util.isZero(intersection.nDotV); // Check if the intersection is valid.
    }
    /**
     * Sets the light source for the intersection and calculates the direction vector and dot product with the ray direction.
     * This method also checks if the intersection is valid by ensuring the dot product is not zero.
     * @param intersection The intersection to set the light source for
     * @param light The light source to be set
     * @return true if the intersection is valid, false otherwise
     */
    public boolean setLightSource (Intersection intersection, LightSource light){
        intersection.light = light;
        intersection.l = light.getL(intersection.point);
        intersection.lDotN = intersection.l.dotProduct(intersection.geometry.getNormal(intersection.point));
        return !Util.isZero(intersection.lDotN); // Check if the intersection is valid.
    }

    /**
     * Calculates the color at the intersection point considering local effects such as light sources.
     * It iterates through all light sources in the scene and calculates the contribution of each light source
     * to the color at the intersection point.
     * @param intersection The intersection point to calculate the color for
     * @return The calculated color at the intersection point
     */
    private Color calcColorLocalEffects(Intersection intersection) {
        Color color = intersection.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            // also checks if sign(nl) == sign(nv))
            if (!setLightSource(intersection, lightSource) || Util.alignZero(intersection.lDotN * intersection.nDotV) <= 0 ) continue;
            if (!unshaded(intersection)) continue; // If the point is shaded, skip to the next light source
            Color iL = lightSource.getIntensity(intersection.point);
            color = color.add(
                    iL.scale(
                            calcDiffusive(intersection).add(calcSpecular(intersection))
                    )
            );
        }
        return color;
    }
    /**
     * calcDiffusive calculates the diffusive component of the color at the intersection point.
     * It scales the diffuse color (KD) of the material by the absolute value of the dot product
     * between the light direction (l) and the normal vector (n) at the intersection point.
     * @param intersection The intersection point containing the material and light information
     * @return The calculated diffusive color component
     */
    private Double3 calcDiffusive(Intersection intersection) {
        return intersection.material.KD.scale(Math.abs(intersection.lDotN));
    }
    /**
     * calcSpecular calculates the specular component of the color at the intersection point.
     * It computes the reflection vector (r) based on the light direction (l) and the normal vector (n),
     * then calculates the dot product of the view direction (v) and the reflection vector (r).
     * @param intersection The intersection point containing the material and light information
     * @return The calculated specular color component
     */
    private Double3 calcSpecular(Intersection intersection) {
        Vector r = intersection.l.subtract(intersection.n.scale(2 * intersection.lDotN));
        double vr = -1 * intersection.v.dotProduct(r);

        return intersection.material.KS.scale(Math.pow(Math.max(0, vr), intersection.material.Nsh));
    }
    /**
     * Checks if the point at the intersection is unshaded by casting a ray from the intersection point
     * @param intersection
     * @return
     */
    private boolean unshaded(Intersection intersection) {
        Point pointDelta = intersection.point.add(intersection.n.scale(intersection.lDotN > 0 ? -DELTA : DELTA));
        Ray check = new Ray(pointDelta, intersection.l.scale(-1));
        List<Intersection> intersections = scene.geometries.calculateIntersections(check);
        if (intersections == null) {
            return true; // No intersections, the point is unshaded
        }
        Intersection closeIntersection = check.findClosestIntersection(intersections);
        if (intersection.light instanceof DirectionalLight) return false;//Specific case for directional light, always shaded since there is an intersection, it was made to calculate faster.
        if (closeIntersection.point.distance(pointDelta) > intersection.light.getDistance(intersection.point)) return true; // If the closest intersection is farther than the light source, the point is unshaded
        return false; // If there is an intersection, the point is shaded
    }
}
