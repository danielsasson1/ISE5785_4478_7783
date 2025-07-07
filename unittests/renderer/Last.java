package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import lighting.*;
import scene.Scene;
import java.util.List;

/**
 * Last class is a placeholder for the last part of the code.
 * It currently does not contain any functionality or properties.
 */
public class Last {
    @Test
    public void testLast() {
        Color wallColor = new Color(130, 130, 130);
        Color floorColor = new Color(100, 100, 100);
        Material material = new Material().setKA(0.2).setKD(0.6).setKS(0.2).setShininess(20).setKR(0.01);
        Geometry window = new Polygon_without(new Polygon(new Point(100,100,100), new Point(-100,100,100), new Point(-100,-100,100), new Point(100,-100,100)),
                new Polygon(new Point(75,75,100), new Point(-75,75,100), new Point(-75,-75,100), new Point(75,-75,100))).setMaterial(material).setEmission(wallColor);
        Geometry plane = new Plane(new Point(0,-50,0), Vector.AXIS_Y).setMaterial(material).setEmission(new Color(10,150,10));
        DirectionalLight sun = new DirectionalLight(new Color(255, 255, 170), new Vector(1, -1, -0.2));
        Scene scene = new Scene("WHAT A SCENE");
        scene.setBackground(new Color(10, 75, 160));
        scene.setAmbientLight(new AmbientLight(new Color(25,25,25)));
        scene.geometries.add(window, plane);
        scene.lights.add(sun);
        Camera camera = new Camera.Builder().setLocation(new Point(0,0,0)).setDirection(new Point(0, 0, 1), Vector.AXIS_Y)
                .setResolution(800,600).setVpSize(200,200).setVpDistance(100).setRayTracer(scene, RayTracerType.SIMPLE).build();
        camera.renderImage()
                .writeToImage("LastTest.png");
    }
}
