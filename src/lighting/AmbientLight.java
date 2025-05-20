package lighting;

import primitives.Color;

public class AmbientLight {
    /**
     * @param intensity The intensity of the ambient light.
     * @param NONE A static constant representing no ambient light.
     */
    private final Color intensity;
    public final static AmbientLight NONE = new AmbientLight(Color.BLACK);

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
