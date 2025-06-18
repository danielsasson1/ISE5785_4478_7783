package renderer;

import lighting.LightSource;
import scene.Scene;
import primitives.*;
import geometries.Intersectable.Intersection;
import java.util.List;


/**
 * SimpleRayTracer class is a basic implementation of a ray tracer.
 * It extends the RayTracerBase class and provides functionality to trace rays in a scene.
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * MAX_CALC_COLOR_LEVEL is the maximum level of recursion for color calculations.
     * It limits the depth of recursive calls when calculating color contributions from reflections and refractions.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * MIN_CALC_COLOR_K is the minimum value for the color calculation coefficient.
     * It is used to determine when to stop further calculations based on the intensity of the color.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * INITIAL_K is the initial value for the color calculation coefficient.
     * It is set to 1.0 for the initial color calculations.
     */
    private static final Double3 INITIAL_K = Double3.ONE;
    /**
     * Constructor for SimpleRayTracer
     * @param scene The scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {super(scene);}
    /**
     * Traces a ray through the scene and returns the color at the intersection point.
     * If there are no intersections, it returns the background color of the scene.
     * @param ray The ray to be traced
     * @return The color at the intersection point or the background color
     */
    public Color traceRay(Ray ray){
        Intersection closestIntersection = findClosestIntersection(ray);
        return closestIntersection == null ? scene.background : calcColor(closestIntersection, ray);
    }
    /**
     * Calculates the color at a given point in the scene.
     * This method currently returns the ambient light intensity of the scene.
     * @param point The point at which to calculate the color
     * @return The color at the specified point
     */
    public Color calcColor(Intersection point, Ray ray) {
        if (!preprocessIntersection(point, ray.getVector()))
            return Color.BLACK;

        Color ambientLightIntensity = scene.ambientLight.getIntensity();
        Double3 attenuationCoefficient = point.geometry.getMaterial().KA;

        Color intensity = ambientLightIntensity.scale(attenuationCoefficient);
        Color color = calcColor(point, MAX_CALC_COLOR_LEVEL, INITIAL_K);
        return intensity.add(color);
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
    private Color calcColorLocalEffects(Intersection intersection, Double3 k) {
        Color color = intersection.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            // also checks if sign(lNormal) == sign(vNormal)) and if the intersection is unshaded
            if (!setLightSource(intersection, lightSource))
                continue;

            Double3 ktr = transparency(intersection);
            if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                Color iL = lightSource.getIntensity(intersection.point).scale(ktr);
                color = color
                        .add(iL.scale(calcDiffusive(intersection)
                                .add(calcSpecular(intersection))));
            }
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
        Ray check = new Ray(intersection.point, intersection.l.scale(-1), intersection.n);
        List<Intersection> intersections = scene.geometries.calculateIntersections(check);
        if (intersections == null) return true; // If there are no intersections, the point is unshaded

            for (Intersection i : intersections) {
                if (intersection.point.distance(i.point) < intersection.light.getDistance(intersection.point)
                        && i.geometry.getMaterial().KT.lowerThan(MIN_CALC_COLOR_K))
                    return false; // If the distance is less than or equal to the bounding box size, the point is shaded
            }
        return true;
    }
    /**
     * Calculates the color at the intersection point considering reflections and refractions.
     * @param intersection The intersection point to calculate the color for
     * @param level the recursion level for reflections/refractions
     * @param k the color coefficient for reflections/refractions
     * @return The calculated color at the intersection point considering reflections and refractions
     */
    private Color calcColor(Intersection intersection, int level, Double3 k) {
        Color color = calcColorLocalEffects(intersection, k);
        if (level == 1)
            return color;
        return color.add(calcGlobalEffect(intersection, level, k));
    }
    /**
     * caculates the ray of transparency at the intersection point.
     * @param intersection The intersection point to calculate the transparency ray for
     * @return The calculated transparency ray at the intersection point
     */
    private Ray calcRefractionRay(Intersection intersection) {
        return new Ray(intersection.point, intersection.v, intersection.n);
    }
    /**
     * Calculates the reflection ray at the intersection point.
     * @param intersection The intersection point to calculate the reflection ray for
     * @return The calculated reflection ray at the intersection point
     */
    private Ray calcReflectionRay(Intersection intersection) {
        Vector r = intersection.v.add((intersection.n.scale(-2 * intersection.nDotV)));
        return new Ray(intersection.point, r, intersection.n);
    }
    /**
     * Calculates the global effect of reflections and refractions at the intersection point.
     * @param intersectionRay The ray that intersects the scene at the intersection point
     * @param level the recursion level for reflections/refractions
     * @param k the color coefficient for reflections/refractions
     * @param KTR the transparency coefficient for reflections/refractions
     * @return The calculated color at the intersection point considering global effects
     */
    private Color calcGlobalEffect(Ray intersectionRay, int level, Double3 k, Double3 KTR){
        Double3 kkx = k.product(KTR);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK; // If the product of k and KTR is below the threshold, return black color
        }
        Intersection intersection = findClosestIntersection(intersectionRay);
        if (intersection == null) {
            return scene.background.scale(KTR); // No intersection, return black color
        }
        if (preprocessIntersection(intersection, intersectionRay.getVector()))
            return calcColor(intersection, level - 1, kkx).scale(KTR);
        return Color.BLACK;
    }
    /**
     * Calculates the global effect of reflections and refractions at the intersection point.
     * @param intersection The intersection point to calculate the global effect for
     * @param level the recursion level for reflections/refractions
     * @param k the color coefficient for reflections/refractions
     * @return The calculated color at the intersection point considering global effects
     */
    private Color calcGlobalEffect(Intersection intersection, int level, Double3 k) {
        Color globalEffectColor1 = calcGlobalEffect(calcRefractionRay(intersection),
                level, k, intersection.material.KT);

        Color globalEffectColor2 = calcGlobalEffect(calcReflectionRay(intersection),
                level, k, intersection.material.KR);

        return globalEffectColor1.add(globalEffectColor2);
    }
    /**
     * Finds the closest intersection point for a given ray in the scene.
     * @param ray The ray to be traced
     * @return The closest intersection point or null if there are no intersections
     */
    private Intersection findClosestIntersection(Ray ray) {
        List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
        return intersections == null ? null : ray.findClosestIntersection(intersections);
    }
    /**
     * Calculates the transparency at the intersection point.
     * @param intersection The intersection point to calculate the transparency for
     * @return The calculated transparency at the intersection point
     */
    private Double3 transparency(Intersection intersection) {
        Ray ray = new Ray(intersection.point, intersection.l.scale(-1), intersection.n); // create a ray from the point to the light source
        var intersections = scene.geometries.calculateIntersections(ray);

        Double3 ktr = Double3.ONE;

        if (intersections == null)
            return ktr;
        else {
            for (Intersection i : intersections) {
                if (intersection.point.distance(i.point) > intersection.light.getDistance(intersection.point)) // skip the intersection with the geometry itself
                    continue;
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
                ktr = ktr.product(i.material.KT);
            }
        }
        return ktr;
    }


}
