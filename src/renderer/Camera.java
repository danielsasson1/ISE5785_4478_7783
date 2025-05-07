package renderer;
import primitives.*;
import java.util.MissingResourceException;

public class Camera implements Cloneable {
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
        }

        /*
         * Set the width of the camera
         * @param p0 the position of the camera
         * @param vTo the direction the camera is looking at
         * @param vUp the up direction of the camera
         * @param vRight the right direction of the camera
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /*
         * Set the height of the camera
         * @param camera the camera to set
         */
        public Builder() {
            this.camera = new Camera();
        }

        /*
         * set the camera to a new camera
         */
        public Builder setLocation(Point p0) {
            camera.p0 = p0;
            return this;
        }

        /*
         * set p0 to a new point
         * @param p0 the point to set
         * @return the camera
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            // calculate the right direction of the camera
            return this;
        }

        /*
         * set the To direction of the camera
         * @param vTo the up direction of the camera
         * @param vUp the right direction of the camera
         * @return the camera
         */
        public Builder setDirection(Point Point_Direction, Vector vUp) {
            camera.vUp = vUp.normalize();
            camera.vTo = Point_Direction.subtract(camera.p0).normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
            // calculate the right direction of the camera
            return this;
        }

        /*
         * set the Direction of the camera
         * @param vTo the distance of the camera
         * @param vUp the right direction of the camera
         * @param vRight the right direction of the camera
         * @return this
         */
        public Builder setDirection(Point Point_Direction) {
            camera.vTo = Point_Direction.subtract(camera.p0).normalize();
            camera.vUp = Vector.AXIS_Y;
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
            return this;
        }
        /*
         * set the direction of the camera
         * @param vTo the direction of the camera
         * @param vUp the up direction of the camera
         * @param vRight the right direction of the camera
         * @return this
         */

        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;
            return this;
        }
        /*
         * set the size of the view plane
         * @param width the width of the camera
         * @param height the height of the camera
         * @return this
         */

        public Builder setVpDistance (double distance) {
            camera.distance = distance;
            return this;
        }
        /*
         * set the distance of the camera
         * @param distance the distance of the camera
         * @return this
         */

        public Builder setResolution(int nX, int nY) {
            return null;
        }
        /*
         * set the resolution of the camera
         * @param nX the number of pixels in the x direction
         * @param nY the number of pixels in the y direction
         * @return this
         */

        public Camera build() {
            final String className = "Camera";
            final String description = "Values are not set";
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            if (camera.p0 == null) {
                throw new MissingResourceException(description, className, "p0");
            }
            if (camera.vTo == null) {
                throw new MissingResourceException(description, className, "vTo");
            }
            if (camera.vUp == null) {
                throw new MissingResourceException(description, className, "vUp");
            }
            if (camera.vRight == null) {
                throw new MissingResourceException(description, className, "vRight");
            }
            if (camera.width == 0) {
                throw new MissingResourceException(description, className, "width");
            }
            if (camera.height == 0) {
                throw new MissingResourceException(description, className, "height");
            }
            if (camera.distance == 0) {
                throw new MissingResourceException(description, className, "distance");
            }
            if (!Util.isZero(camera.vTo.dotProduct(camera.vRight)) ||
                    !Util.isZero(camera.vTo.dotProduct(camera.vUp)) ||
                    !Util.isZero(camera.vRight.dotProduct(camera.vUp)))
                throw new IllegalArgumentException("vTo, vUp and vRight must be orthogonal");

            if (!Util.isZero(camera.vTo.length() - 1) ||
                    !Util.isZero(camera.vUp.length() - 1) ||
                    !Util.isZero(camera.vRight.length() - 1))
                throw new IllegalArgumentException("vTo, vUp and vRight must be normalized");

            if (camera.width <= 0 || camera.height <= 0)
                throw new IllegalArgumentException("width and height must be positive");

            if (camera.distance <= 0)
                throw new IllegalArgumentException("distance from camera to view must be positive");
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Point getP0() {
        return p0;
    }
    /*
     * get the position of the camera
     * @return the position of the camera
     */

    public Vector getVTo() {
        return vTo;
    }
    /*
     * get the direction the camera is looking at
     * @return the direction the camera is looking at
     */

    public Vector getVUp() {
        return vUp;
    }
    /*
     * get the up direction of the camera
     * @return the up direction of the camera
     */

    public Vector getVRight() {
        return vRight;
    }
    /*
     * get the right direction of the camera
     * @return the right direction of the camera
     */

    public double getWidth() {
        return width;
    }
    /*
     * get the width of the camera
     * @return the width of the camera
     */

    public double getHeight() {
        return height;
    }
    /*
     * get the height of the camera
     * @return the height of the camera
     */

    public double getDistance() {
        return distance;
    }
    /*
     * get the distance of the camera
     * @return the distance of the camera
     */

    public static Builder getBuilder() {
        return new Builder();
    }
    /*
     * get the builder of the camera
     * @return the builder of the camera
     */

    public Ray constructRay(int nX, int nY, int j, int i) {
        Point Pij = p0; // the point on the view plane
        Pij = Pij.add(vTo.scale(distance)); // add the direction of the camera
        double Yi = -(i - (nY - 1) / 2d) * (height / nY); // the y coordinate of the point on the view plane
        double Xj = (j - (nX - 1) / 2d) * (width / nX); // the x coordinate of the point on the view plane
        if(!Util.isZero(Xj)) Pij = Pij.add(vRight.scale(Xj)); // if the x coordinate is not zero, add the right direction of the camera
        if(!Util.isZero(Yi)) Pij = Pij.add(vUp.scale(Yi)); // if the y coordinate is not zero, add the up direction of the camera
        return new Ray(p0, Pij.subtract(p0)); // return the ray from the camera to the point on the view plane
    }
    /*
     * construct a ray from the camera to the point on the view plane
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j the x coordinate of the pixel
     * @param i the y coordinate of the pixel
     * @return the ray from the camera to the point on the view plane
     */
}
