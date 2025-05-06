package renderer;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.Locale;

public class Camera implements Cloneable{
    private Point p0; // the position of the camera
    private Vector vTo; // the direction the camera is looking at
    private Vector vUp; // the up direction of the camera
    private Vector vRight; // the right direction of the camera
    private double width = 0; // the width of the camera, default is 0
    private double height = 0; // the height of the camera
    private double distance = 0; // the distance of the camera from the view plane

    /**
     * Constructor for Camera
     * the position of the camera
     * the direction the camera is looking at
     * the up direction of the camera
     */
    private Camera() {
    } // constructor

    public static class Builder {
        private final Camera camera;

        public Builder(Point p0, Vector vTo, Vector vUp) {
            camera = new Camera();
            camera.p0 = p0;
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            camera.vRight = vTo.crossProduct(vUp).normalize();
        }

        public Builder setWidth(double width) {
            camera.width = width;
            return this;
        }

        public Builder setHeight(double height) {
            camera.height = height;
            return this;
        }

        public Builder setDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        public Camera build() {
            return camera;
        }
    }
}
