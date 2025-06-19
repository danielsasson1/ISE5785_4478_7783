package lighting;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * PointLight class represents a point light source in a 3D scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class PointLight extends Light implements LightSource {
    /**
     * The position of the light in space
     */
    private final Point position;
    /** The constant attenuation factor*/
    private double Kc = 1.0;
    /** The linear attenuation factor */
    private double Kl = 0.0;
    /** The quadratic attenuation factor */
    private double Kq = 0.0;

    /**
     * set the attenuation factors
     * @param Kc constant attenuation factor
     * @return this PointLight object for method chaining
     */
    public PointLight setKc(double Kc) {
        this.Kc = Kc;
        return this;
    }
    /**
     * set the linear attenuation factor
     * @param Kl linear attenuation factor
     * @return this PointLight object for method chaining
     */
    public PointLight setKl(double Kl) {
        this.Kl = Kl;
        return this;
    }
    /**
     * set the quadratic attenuation factor
     * @param Kq quadratic attenuation factor
     * @return this PointLight object for method chaining
     */
    public PointLight setKq(double Kq) {
        this.Kq = Kq;
        return this;
    }


    /**
     * Constructor for PointLight
     * @param intensity the color of the light
     * @param position the position of the light in space
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        // Calculate intensity based on distance from the point light source
        double distanceSquared = position.distanceSquared(p);
        double attenuation = 1/(Kc + Kl * Math.sqrt(distanceSquared) + Kq * distanceSquared);
        return intensity.scale(attenuation);
    }

    @Override
    public Vector getL(Point p) {
        if (p.equals(position)) {
            return null; // No direction if the point is the same as the light position
        }
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

    public void setSoftShadow(double radius, int numOfRays) {
        this.radius = radius;
        this.numOfRays = numOfRays;
    }
    @Override
    public int getNumOfRays() {
        return numOfRays;
    }
    @Override
    public List<Vector> generateSampleVectors(Point point){
        List<Point> samples = new LinkedList<>();
        double phi = (1 + Math.sqrt(5)) / 2; // golden ratio

        for (int i = 0; i < this.numOfRays; i++) {
            double theta = 2 * Math.PI * i / phi;
            double z = 1 - (2.0 * i + 1) / this.numOfRays;
            double r = Math.sqrt(1 - z * z);
            double x = r * Math.cos(theta);
            double y = r * Math.sin(theta);

            double px = position.point.d1() + radius * x;
            double py = position.point.d2() + radius * y;
            double pz = position.point.d3() + radius * z;

            samples.add(new Point(px, py, pz));
        }
        samples.add(position); // Add the light position as a sample point
        List<Vector> directions = new LinkedList<>();

        for (Point sample : samples) {
            directions.add(sample.subtract(point));
        }
        return directions;
    }
}
