package scene;
import lighting.AmbientLight;
import primitives.Color;
import geometries.Geometries;

/**
 * The Scene class represents a 3D scene with a name, background color, ambient light, and geometries.
 * It provides methods to set the background color, ambient light, and geometries of the scene.
 */
public class Scene {
    /** The name of the scene. */
    public String name;
    /** The background color of the scene. Default is black. */
    public Color background = Color.BLACK;
    /** The ambient light of the scene. Default is no ambient light. */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /** The geometries in the scene. Default is an empty set of geometries. */
    public Geometries geometries = new Geometries();

    /**
     * Constructor for Scene
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    //setters
    /**
        * Set the name of the scene
        * @param background The name of the scene.
        * @return The scene object.
    */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
        * Set the ambient light of the scene
        * @param ambientLight The ambient light of the scene.
        * @return The scene object.
    */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
        * Set the geometries of the scene
        * @param geometries The geometries of the scene.
        * @return The scene object.
    */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }


}
