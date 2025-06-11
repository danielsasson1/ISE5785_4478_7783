package lighting;

import primitives.Color;

/**
 * AmbientLight class represents the ambient light in a scene.
 * It is a type of light that is present in the scene and illuminates all objects equally.
 * The intensity of the ambient light can be adjusted.
 */
public class AmbientLight {

    /**
     * The intensity of the ambient light.
     * It is represented as a Color object.
     */
    private final Color intensity; // The intensity of the ambient light

    /**
     * A static constant representing no ambient light.
     * It is initialized to black color.
     */
    public final static AmbientLight NONE = new AmbientLight(Color.BLACK); // A static constant representing no ambient light

    /**
     * Constructor for AmbientLight
     * @param intensity The intensity of the ambient light.
     */
    public AmbientLight(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getIntensity
     * @return The intensity of the ambient light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
