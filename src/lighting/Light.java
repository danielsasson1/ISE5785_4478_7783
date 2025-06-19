package lighting;
import primitives.*;

/**
 * SpotLight class represents a spotlight in a 3D scene.
 * It extends PointLight to include direction and angle of the spotlight.
 */
public abstract class Light {
    /**
     * The color of the light
     */
    protected final Color intensity;
    /** for soft shadows - the radius of the circle */
    protected double radius = 0.0; //no soft shadows by default
    /** for soft shadows - the number of rays to sample */
    protected int numOfRays = 1; //no soft shadows by default

    /**
     * Constructor for the light
     * @param intensity the color of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Get the color of the light
     * @return the color of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
