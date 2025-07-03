package renderer;
import geometries.BVHBuilder;
import primitives.*;
import renderer.PixelManager.Pixel;

import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

import scene.Scene;

/**
 * Camera class represents a camera in 3D space.
 * It is used to render a scene from a specific point of view.
 */
public class Camera implements Cloneable {

    /**p0 is the position of the camera in 3D space.*/
    private Point p0; // the position of the camera
    /**vTo is the direction the camera is looking at.*/
    private Vector vTo; // the direction the camera is looking at
    /**vUp is the up direction of the camera.*/
    private Vector vUp; // the up direction of the camera
    /**vRight is the right direction of the camera.*/
    private Vector vRight; // the right direction of the camera
    /**width is the width of the camera's view plane.*/
    private double width = 0; // the width of the camera, default is 0
    /**height is the height of the camera's view plane.*/
    private double height = 0; // the height of the camera
    /**distance is the distance of the camera from the view plane.*/
    private double distance = 0; // the distance of the camera from the view plane
    /**VP_Center is the center of the view plane.*/
    private Point VP_Center; // the center of the view plane

    /**imageWriter is the image writer of the camera.*/
    private ImageWriter imageWriter; // the image writer of the camera
    /**rayTracer is the ray tracer of the camera.*/
    private RayTracerBase rayTracer; // the ray tracer of the camera

    /** nX is the number of pixels in the x direction.*/
    private int nX = 1; // the number of pixels in the x direction
    /** nY is the number of pixels in the y direction.*/
    private int nY = 1; // the number of pixels in the y direction

    /** Amount of threads to use fore rendering image by the camera */
    private int threadsCount = 0;
    /**
     * Amount of threads to spare for Java VM threads:<br>
     * Spare threads if trying to use all the cores
     */
    private static final int SPARE_THREADS = 2;
    /**
     * Debug print interval in seconds (for progress percentage)<br>
     * if it is zero - there is no progress output
     */
    private double printInterval = 0;
    /**
     * Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * </ul>
     */
    private PixelManager pixelManager;

    /**
     * set the thread count for rendering
     * @param threads the number of threads to use for rendering
     * @return this camera
     */
    public Camera setMultithreading(int threads) {
        this.threadsCount = threads;
        return this;
    }
    /**
     * set the interval for printing progress
     * @param interval the interval for printing progress
     * @return this camera
     */
    public Camera setDebugPrint(int interval) {
        this.printInterval = interval;
        return this;
    }

    /**
     * Constructor for Camera
     */
    private Camera() {
    } // constructor

    /**
     * Builder class for Camera
     * It is used to create a camera with specific parameters.
     */
    public static class Builder {
        /**
         *  the camera to build
         */
        private final Camera camera;

        /**
         * Constructor for Builder
         * @param p0 the position of the camera
         * @param vTo the direction the camera is looking at
         * @param vUp the up direction of the camera
         */
        public Builder(Point p0, Vector vTo, Vector vUp) {
            camera = new Camera();
            camera.p0 = p0;
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
        }

        /**
         * Set the width of the camera
         * @param camera the camera to set
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * Default constructor for Builder
         * Initializes a new camera with default values.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Set multi-threading <br>
         * Parameter value meaning:
         * <ul>
         * <li>-2 - number of threads is number of logical processors less 2</li>
         * <li>-1 - stream processing parallelization (implicit multi-threading) is used</li>
         * <li>0 - multi-threading is not activated</li>
         * <li>1 and more - literally number of threads</li>
         * </ul>
         * @param threads number of threads
         * @return builder object itself
         */
        public Builder setMultithreading(int threads) {
            if (threads < -3)
                throw new IllegalArgumentException("Multithreading parameter must be -2 or higher");
            if (threads == -2) {
                int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            } else
                camera.threadsCount = threads;
            return this;
        }
        /**
         * Set debug printing interval. If it's zero - there won't be printing at all
         * @param interval printing interval in %
         * @return builder object itself
         */
        public Builder setDebugPrint(double interval) {
            if (interval < 0) throw new IllegalArgumentException("interval parameter must be non-negative");
            camera.printInterval = interval;
            return this;
        }

        /**
         * set the camera to a new camera
         * @param p0 the position of the camera
         */
        public Builder setLocation(Point p0) {
            camera.p0 = p0;
            return this;
        }

        /**
         * set the direction of the camera
         * @param vTo the direction of the camera
         * @param vUp the up direction of the camera
         * @return the camera
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * set the direction of the camera
         * @param Point_Direction the direction of the camera
         * @param vUp the up direction of the camera
         * @return this
         */
        public Builder setDirection(Point Point_Direction, Vector vUp) {
            camera.vUp = vUp.normalize();
            camera.vTo = Point_Direction.subtract(camera.p0).normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
            // calculate the right direction of the camera
            return this;
        }

        /**
         * set the direction of the camera
         * @param Point_Direction the direction of the camera
         * @return this
         */
        public Builder setDirection(Point Point_Direction) {
            camera.vTo = Point_Direction.subtract(camera.p0).normalize();
            camera.vUp = Vector.AXIS_Y;
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
            return this;
        }

        /**
         * set the size of the view plane
         * @param width the width of the view plane
         * @param height the height of the view plane
         * @return this
         */
        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * set the distance of the camera from the view plane
         * @param distance the distance of the camera from the view plane
         * @return this
         */
        public Builder setVpDistance (double distance) {
            camera.distance = distance;
            return this;
        }

        /**
         * set the resolution of the camera
         * @param nX the number of pixels in the x direction
         * @param nY the number of pixels in the y direction
         * @return this
         */
        public Builder setResolution(int nX, int nY) {
            camera.nX = nX;
            camera.nY = nY;
            return this;
        }


        /**
         * build the camera
         * @return the camera
         */
        public Camera build() {
            final String className = "Camera";
            final String description = "Values are not set";
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            if (camera.nX <= 0 || camera.nY <= 0)
                throw new IllegalArgumentException("resolution must be positive");
            this.camera.imageWriter = new ImageWriter(camera.nX, camera.nY);
            if (camera.rayTracer == null)
                camera.rayTracer = new SimpleRayTracer(new Scene(null));
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
                camera.VP_Center = camera.p0.add(camera.vTo.scale(camera.distance)); // add the direction of the camera

                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * set the ray tracer of the camera
         * @param rayTracerType the ray tracer type of the camera
         * @param scene the scene of the camera
         * @return this
         */
        public Builder setRayTracer (Scene scene ,RayTracerType rayTracerType) {
            switch (rayTracerType) {
                case SIMPLE -> camera.rayTracer = new SimpleRayTracer(scene);
                default -> camera.rayTracer = null;
            }
            return this;
        }

        public Builder enableBVH() {
            camera.rayTracer.scene.geometries.buildBVH();
            return this;
        }
    }

    /**
     * get the position of the camera
     * @return the position of the camera
     */
    public Point getP0() {
        return p0;
    }

    /**
     * get the direction the camera is looking at
     * @return the position of the camera
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * get the up direction of the camera
     * @return the up direction of the camera
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * get the right direction of the camera
     * @return the right direction of the camera
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * get the width of the camera
     * @return the width of the camera
     */
    public double getWidth() {
        return width;
    }

    /**
     * get the height of the camera
     * @return the height of the camera
     */
    public double getHeight() {
        return height;
    }

    /**
     * get the center of the view plane
     * @return the center of the view plane
     */
    public double getDistance() {
        return distance;
    }

    /**
     * get a builder for the camera
     * @return a new builder for the camera
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * construct a ray from the camera to the point on the view plane
     * @param j the x coordinate of the pixel
     * @param i the y coordinate of the pixel
     * @return the ray from the camera to the point on the view plane
     */
    public Ray constructRay(int nX, int nY ,int j, int i) {
        Point Pij = this.VP_Center; // the point on the view plane
        double Yi = -(i - (nY - 1) / 2d) * (height / nY); // the y coordinate of the point on the view plane, negative because the y axis is inverted in the view plane
        double Xj = (j - (nX - 1) / 2d) * (width / nX); // the x coordinate of the point on the view plane
        if(!Util.isZero(Xj)) Pij = Pij.add(vRight.scale(Xj)); // if the x coordinate is not zero, add the right direction of the camera
        if(!Util.isZero(Yi)) Pij = Pij.add(vUp.scale(Yi)); // if the y coordinate is not zero, add the up direction of the camera
        return new Ray(p0, Pij.subtract(p0)); // return the ray from the camera to the point on the view plane
    }

    /**
     * render the image
     * @return the camera
     */
    /** This function renders image's pixel color map from the scene
     * included in the ray tracer object
     * @return the camera object itself
     */
    public Camera renderImage() {
        pixelManager = new PixelManager(nY, nX, printInterval);
        return switch (threadsCount) {
            case 0 -> renderImageNoThreads();
            case -1 -> renderImageStream();
            default -> renderImageRawThreads();
        };
    }


    /**
     * Render image using multi-threading by parallel streaming
     * @return the camera object itself
     */
    private Camera renderImageStream() {
        IntStream.range(0, nY).parallel()
                .forEach(i -> IntStream.range(0, nX).parallel()
                        .forEach(j -> castRay(j, i)));
        return this;
    }
    /**
     * Render image without multi-threading
     * @return the camera object itself
     */
    private Camera renderImageNoThreads() {
        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                castRay(j, i);
        return this;
    }
    /**
     * Render image using multi-threading by creating and running raw threads
     * @return the camera object itself
     */
    private Camera renderImageRawThreads() {
        var threads = new LinkedList<Thread>();
        while (threadsCount-- > 0)
            threads.add(new Thread(() -> {
                Pixel pixel;
                while ((pixel = pixelManager.nextPixel()) != null)
                    castRay(pixel.row(),pixel.col());
            }));
        for (var thread : threads) thread.start();
        try {
            for (var thread : threads) thread.join();
        } catch (InterruptedException ignored) {}
        return this;
    }

    /**
     * print the grid on the image
     * @param interval the interval of the grid
     * @param color the color of the grid
     * @return the camera
     */
    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < nY; i++) { // iterate over the view plane
            for (int j = 0; j < nX; j++) { // iterate over the view plane
                if (i % interval == 0 || j % interval == 0) { // if the pixel is on the grid
                    imageWriter.writePixel(j, i, color); // write the pixel to the image
                }
            }
        }
        return this;
    }

    /**
     * Write the image
     * @param fileName the name of the file to write the image to
     * @return the camera
     */
    public Camera writeToImage(String fileName) {
        imageWriter.writeToImage(fileName);
        return this;
    }

    /**
     * cast a ray from the camera to the point on the view plane
     * @param i the x coordinate of the pixel
     * @param j the y coordinate of the pixel
     */
    private void castRay(int j, int i) {
        imageWriter.writePixel(j, i, rayTracer.traceRay(constructRay(nX ,nY, j, i)));// write the pixel to the image
        pixelManager.pixelDone(); // notify the pixel manager that the pixel is done
    }
}
