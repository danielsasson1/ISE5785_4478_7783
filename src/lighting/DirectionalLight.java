package lighting;
import primitives.*;

import java.util.List;

/**
 * directional light class represents a light source that emits light in a specific direction.
 * It extends the Light class and implements the LightSource interface.
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * The direction of the light
     */
    private final Vector direction;

    /**
     * Constructor for DirectionalLight
     * @param intensity the color of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY; // Directional light is considered to be infinitely far away
    }

    @Override
    public void setSoftShadow(double radius, int numOfRays) {
        radius = 0.0; // Directional light does not support soft shadows in the same way as point lights
        numOfRays = 1; // Directional light does not support soft shadows in the same way as point lights
    }
    @Override
    public int getNumOfRays() {
        return numOfRays;
    }
    @Override
    public List<Vector> generateSampleVectors(Point point) {
        return List.of(direction.scale(-1)); // Directional light only has one direction, so it returns a list with that direction
    }
}
