package lighting;
import primitives.*;

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
}
