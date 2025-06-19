package lighting;
import primitives.*;

import java.util.List;

/**
 * SpotLight class represents a spotlight in a 3D scene.
 * It extends PointLight to include direction and angle of the spotlight.
 */
public class SpotLight extends PointLight {
    /**
     * The direction of the spotlight
     */
    private final Vector direction;
    /** Narrow Beam is a factor that determines how narrow the beam of the spotlight is */
    private double NARROW_BEAM = 1.0;

    /**
     * Constructor for SpotLight
     * @param intensity the color of the light
     * @param position the position of the light in space
     * @param direction the direction of the spotlight
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.pow(Math.max(0, direction.dotProduct(getL(p))), NARROW_BEAM));
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

    /**
     * Sets the narrow beam factor for the spotlight.
     * @param narrowBeam the narrow beam factor
     * @return this SpotLight object for method chaining
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.NARROW_BEAM = narrowBeam;
        return this;
    }

    @Override
    public SpotLight setKc(double Kc) {
        super.setKc(Kc);
        return this;
    }
    @Override
    public SpotLight setKl(double Kl) {
        super.setKl(Kl);
        return this;
    }
    @Override
    public SpotLight setKq(double Kq) {
        super.setKq(Kq);
        return this;
    }

    public void setSoftShadow(double radius, int numOfRays) {
        super.setSoftShadow(radius, numOfRays);
    }
    @Override
    public int getNumOfRays() {
        return numOfRays;
    }
    @Override
    public List<Vector> generateSampleVectors(Point point){
        return super.generateSampleVectors(point);
    }
}
