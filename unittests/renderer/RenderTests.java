package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.*;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import primitives.*;
import scene.Scene;

/**
 * Test rendering a basic image
 * @author Dan
 */
class RenderTests {
   /**
    * Default constructor to satisfy JavaDoc generator
    */
   RenderTests() { /* to satisfy JavaDoc generator */ }

   /**
    * Camera builder of the tests
    */
   private final Camera.Builder camera = Camera.getBuilder() //
           .setLocation(Point.ZERO).setDirection(new Point(0, 0, -1), Vector.AXIS_Y) //
           .setVpDistance(100) //
           .setVpSize(500, 500);

   /**
    * Produce a scene with basic 3D model and render it into a png image with a
    * grid
    */
   @Test
   void renderTwoColorTest() {
      Scene scene = new Scene("Two color").setBackground(new Color(75, 127, 90))
              .setAmbientLight(new AmbientLight(new Color(255, 191, 191)));
      scene.geometries //
              .add(// center
                      new Sphere(new Point(0, 0, -100), 50d),
                      // up left
                      new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)),
                      // down left
                      new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)),
                      // down right
                      new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)));

      camera //
              .setRayTracer(scene, RayTracerType.SIMPLE) //
              .setResolution(1000, 1000) //
              .build() //
              .renderImage() //
              .printGrid(100, new Color(YELLOW)) //
              .writeToImage("Two color render test");
   }

   // For stage 6 - please disregard in stage 5

   /**
    * Produce a scene with basic 3D model - including individual lights of the
    * bodies and render it into a png image with a grid
    */
   @Test
   void renderMultiColorTest() {
      Scene scene = new Scene("Multi color").setAmbientLight(new AmbientLight(new Color(51, 51, 51)));
      scene.geometries //
              .add(// center
                      new Sphere(new Point(0, 0, -100), 50),
                      // up left
                      new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)) //
                              .setEmission(new Color(GREEN)),
                      // down left
                      new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)) //
                              .setEmission(new Color(RED)),
                      // down right
                      new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)) //
                              .setEmission(new Color(BLUE)));

      camera //
              .setRayTracer(scene, RayTracerType.SIMPLE) //
              .setResolution(1000, 1000) //
              .build() //
              .renderImage() //
              .printGrid(100, new Color(WHITE)) //
              .writeToImage("color render test");
   }

   /**
    * Test for XML based scene - for bonus
    */
   @Test
   void basicRenderXml() {
      Scene scene = new Scene("Using XML");
      // enter XML file name and parse from XML file into scene object instead of the
      // new Scene above,
      // Use the code you added in appropriate packages
      // ...
      // NB: unit tests is not the correct place to put XML parsing code

      camera //
              .setRayTracer(scene, RayTracerType.SIMPLE) //
              .setResolution(1000, 1000) //
              .build() //
              .renderImage() //
              .printGrid(100, new Color(YELLOW)) //
              .writeToImage("xml render test");
   }

   /**
    * Test for JSON based scene - for bonus
    */
   @Test
   void basicRenderJson() {
      Scene scene = new Scene("Using Json");
      // enter XML file name and parse from JSON file into scene object instead of the
      // new Scene above,
      // Use the code you added in appropriate packages
      // ...
      // NB: unit tests is not the correct place to put XML parsing code

      camera //
              .setRayTracer(scene, RayTracerType.SIMPLE) //
              .setResolution(1000, 1000) //
              .build() //
              .renderImage() //
              .printGrid(100, new Color(YELLOW)) //
              .writeToImage("xml render test");
   }

   @Test
   void MultyColor2() {
      Scene scene = new Scene("Multi color").setAmbientLight(new AmbientLight(new Color(white)));
      scene.geometries //
              .add(// center
                      new Sphere(new Point(0, 0, -100), 50).setMaterial(new Material().setKA(new Double3(0.4))),
                      // up left
                      new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)) //
                              .setMaterial(new Material().setKA(new Double3(0,0.8,0))), //
                      // down left
                      new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)) //
                              .setMaterial(new Material().setKA(new Double3(0.8,0,0))),
                      // down right
                      new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)) //
                              .setMaterial(new Material().setKA(new Double3(0,0,0.8))));

      camera //
              .setRayTracer(scene, RayTracerType.SIMPLE) //
              .setResolution(1000, 1000) //
              .build() //
              .renderImage() //
              .printGrid(100, new Color(WHITE)) //
              .writeToImage("Bomb Test Render");
   }

   @Test
   void My_own_test() {
      PointLight pointlight1 = new PointLight(new Color(0,400,200), new Point(5,5,5)).setKl(0.2).setKq(0.002);
      pointlight1.setSoftShadow(0.5,60);
      Scene scene = new Scene("My own test").setAmbientLight(new AmbientLight(new Color(5,5,0)));
      scene.geometries.add(new Cylinder(new Ray(new Point(0,0,0), Vector.AXIS_Y), 1, 3).setMaterial(new Material().setKA(1).setKD(0.5).setKS(0.5).setShininess(1)).setEmission(new Color(5,5,100)),
              new Plane(new Point(0,-1,0), Vector.AXIS_Y.scale(-1)).setMaterial(new Material().setKA(1).setKD(0.8).setKS(0.2).setShininess(1)).setEmission(new Color(60,60,54)));
      scene.setBackground(new Color (40, 0, 0)).
              lights.add(pointlight1);
      //scene.lights.add(new DirectionalLight(new Color(138,43,226),new Vector(1,-1,-1)));
      Camera camera1 = camera.setRayTracer(scene, RayTracerType.SIMPLE).setLocation(new Point (0,5,10)).setResolution(500, 500).setDirection(new Point (0,0,0), Vector.AXIS_Y.scale(1))
              .setVpDistance(3).setVpSize(2, 2).build();
      camera1.renderImage()
              //.printGrid(100, new Color(WHITE))
              .writeToImage("My_own_test");
   }

   @Test
   void A_cool_scene() {
      Material wallMaterial = new Material().setKA(0.2).setKD(0.6).setKS(0.2).setShininess(20).setKR(0.01),
              mirrorMaterial = new Material().setKA(0.1).setKD(0.1).setKS(0.9).setShininess(300).setKR(0.90);
      Material floorMaterial = new Material().setKA(0.2).setKD(0.6).setKS(0.2).setShininess(20).setKR(0.3);
      Color wallColor = new Color(130, 130, 130);
      Color floorColor = new Color(100, 100, 100);
      Geometry floor = new Polygon(new Point(-100, 0, 100), new Point(100, 0, 100), new Point(100, 0, 300), new Point(-100, 0, 300))
              .setMaterial(floorMaterial).setEmission(floorColor);
      Geometry ceiling = new Polygon(new Point(-100, 200, 100), new Point(-100, 200, 300), new Point(100, 200, 300), new Point(100, 200, 100))
              .setMaterial(wallMaterial).setEmission(floorColor);
      Geometry backWall = new Polygon(new Point(-100, 200, 300), new Point(-100, 0, 300), new Point(100, 0, 300), new Point(100, 200, 300))
              .setMaterial(wallMaterial).setEmission(wallColor);
      Geometry leftWall = new Polygon(new Point(100, 0, 300),new Point(100, 0, 100),new Point(100, 200, 100),new Point(100, 200, 300))
              .setMaterial(wallMaterial).setEmission(wallColor);
      Polygon rightWall = new Polygon(new Point(-100, 0, 100),new Point(-100, 0, 300),new Point(-100, 200, 300), new Point(-100, 200, 100));
      Geometry rightWallWithHole = new Polygon_without(rightWall,
              new Polygon(new Point(-100, 50, 150), new Point(-100, 50, 250), new Point(-100, 150, 250), new Point(-100, 150, 150)))
              .setMaterial(wallMaterial).setEmission(wallColor);
      Geometry lightbulb = new Sphere(new Point(0, 170, 200), 20)
              .setMaterial(new Material().setKA(0.1).setKD(0.1).setKS(0.9).setShininess(300).setKT(0.9))
              .setEmission(new Color(0,0,0));
      Geometry lightbulbholder = new Cylinder(new Ray(new Point(0, 190, 200), Vector.AXIS_Y), 10, 10)
              .setMaterial(new Material().setKA(0.1).setKD(0.1).setKS(0.9).setShininess(300))
              .setEmission(new Color(50,50,50));
      //Mirror on the left wall:
      Geometry mirror = new Polygon(new Point(99.9, 100, 220), new Point(99.9, 150, 220), new Point(99.9, 150, 120), new Point(99.9, 100, 120))
              .setMaterial(mirrorMaterial).setEmission(new Color(0,3,0));
      //Flashlight on the ceiling, top left corner:
      Geometry flashlightHolder = new Cylinder(new Ray(new Point(90, 200, 180), new Vector(0,-1,0)), 5, 30)
              .setMaterial(new Material().setKA(0.1).setKD(0.1).setKS(0.9).setShininess(300))
              .setEmission(new Color(50,50,50));
      //The actual flashlight:
      Geometry flashlightGeo = new Cylinder(new Ray(new Point(90, 170, 180), new Vector(0,-1,0)), 10, 30)
              .setMaterial(new Material().setKA(0.1).setKD(0.1).setKS(0.9).setShininess(300).setKT(0.9))
              .setEmission(new Color(0,0,0));
      Geometry sphere = new Sphere(new Point(0, 20, 180), 20)
              .setMaterial(new Material().setKA(0.1).setKD(0.6).setKS(0.3).setShininess(300))
              .setEmission(new Color(10,10,100));
      //Directional light sun from window:
      DirectionalLight sun = new DirectionalLight(new Color(255 / ((double)255/120), 255 / ((double)255/120), 170 / ((double)255/120)), new Vector(1, -1, -0.2));
      //Point light from a bulb on the ceiling:
      PointLight bulb = new PointLight(new Color(255,255,51), new Point(0, 170, 200))
              .setKl(0.001).setKq(0.0001);
      bulb.setSoftShadow(10, 10);
      //Spotlight from a flashlight:
      SpotLight flashlight = new SpotLight(new Color(150,20,150), new Point(90, 169, 180), new Vector(0, -1, 0))
              .setKl(0.0001).setKq(0.00001).setNarrowBeam(10);
      flashlight.setSoftShadow(5, 10);

      Scene scene = new Scene("My Scene").setAmbientLight(new AmbientLight(new Color(255, 255, 255))).setBackground(new Color(204, 255, 255));
      scene.geometries.add(floor, ceiling, backWall, leftWall, rightWallWithHole, lightbulb, lightbulbholder, mirror, flashlightHolder, flashlightGeo, sphere);
      scene.lights.add(sun);
      scene.lights.add(bulb);
      scene.lights.add(flashlight);
      Camera camera = new Camera.Builder().setLocation(new Point(0,100,0)).setDirection(new Point(0, 100, 200), Vector.AXIS_Y)
              .setResolution(800,600).setVpSize(200,200).setVpDistance(100).setRayTracer(scene, RayTracerType.SIMPLE).build();
      camera.renderImage()
                .writeToImage("My_Pool_Scene");
   }
}
