package scene;
import lighting.AmbientLight;
import primitives.Color;
import geometries.Geometries;

public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
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
