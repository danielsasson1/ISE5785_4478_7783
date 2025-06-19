package lighting;
import primitives.*;
import java.util.List;

/**
 * SpotLight class represents a spotlight in a 3D scene.
 * It extends PointLight to include direction and angle of the spotlight.
 */
public interface LightSource {
    /**
     * Get the intensity of the light at a specific point.
     * @param p the point in space where the intensity is calculated
     * @return the color intensity of the light at the point
     */
    Color getIntensity(Point p);
    /**
     * Get the direction vector from the light source to a specific point.
     * @param p the point in space where the direction is calculated
     * @return the direction vector from the light source to the point
     */
    Vector getL(Point p);
    /**
     * Get the distance from the light source to a specific point.
     * @param point the point in space where the distance is calculated
     * @return the distance from the light source to the point
     */
    double getDistance(Point point);
    /**
     * Set the soft shadow parameters.
     * @param radius the radius of the circle for soft shadows
     * @param numOfRays the number of rays to sample for soft shadows
     */
    public void setSoftShadow(double radius, int numOfRays);
    /**
     * Get the number of rays for soft shadows.
     * @return the number of rays to sample for soft shadows
     */
    public int getNumOfRays();
    /**
     * Calculates all the Vectors for soft shadows
     * @param point the point in space where the vectors are calculated
     * @return a List of vectors representing the directions for soft shadows
     */
    public List<Vector> generateSampleVectors(Point point);
}
