package primitives;

/**
 * Material class represents the properties of a material in a 3D rendering context.
 * It includes coefficients for ambient reflection, diffuse reflection, and specular reflection.
 */
public class Material {
    /**
     * Diffuse reflection coefficient
     * Represents the amount of light reflected diffusely by the material.
     */
    public Double3 KA = Double3.ONE; // Ambient reflection coefficient
    /**
     * Specular reflection coefficient
     * Represents the amount of light reflected specularly by the material.
     */
    public Double3 KS = Double3.ZERO; // Specular reflection coefficient
    /**
     * Diffuse reflection coefficient
     * Represents the amount of light reflected diffusely by the material.
     */
    public Double3 KD = Double3.ZERO; // Diffuse reflection coefficient
    /**
     * Shininess coefficient
     * Represents the shininess of the material, affecting the size of specular highlights.
     */
    public int Nsh = 0; // Shininess coefficient
    /**
     * KT - Transparency coefficient
     */
    public Double3 KT = Double3.ZERO;
    /**
     * KR - Reflection coefficient
     */
    public Double3 KR = Double3.ZERO;

    /**
     * setter for Ka
     * @param KA the ambient reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKA(Double3 KA) {
        this.KA = KA;
        return this;
    }
    /**
     * setter for Ka uses a double value
     * @param KA the ambient reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKA(double KA) {
        this.KA = new Double3(KA);
        return this;
    }
    /**
     * setter for Ks
     * @param KS the specular reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKS(Double3 KS) {
        this.KS = KS;
        return this;
    }
    /**
     * setter for Ks uses a double value
     * @param KS the specular reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKS(double KS) {
        this.KS = new Double3(KS);
        return this;
    }
    /**
     * setter for Kd
     * @param KD The diffuse reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKD(Double3 KD) {
        this.KD = KD;
        return this;
    }
    /**
     * setter for Kd uses a double value
     * @param KD The diffuse reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKD(double KD) {
        this.KD = new Double3(KD);
        return this;
    }
    /**
     * setter for Nsh
     * @param nSh the shininess coefficient
     * @return this Material object for method chaining
     */
    public Material setShininess(int nSh) {
        this.Nsh = nSh;
        return this;
    }
    /**
     * setter for KT
     * @param KT The diffuse reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKT(Double3 KT) {
        this.KT = KT;
        return this;
    }
    /**
     * setter for Kd uses a double value
     * @param KT The diffuse reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKT(double KT) {
        this.KT = new Double3(KT);
        return this;
    }
    /**
     * setter for KR
     * @param KR The diffuse reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKR(Double3 KR) {
        this.KR = KR;
        return this;
    }
    /**
     * setter for Kd uses a double value
     * @param KR The diffuse reflection coefficient
     * @return this Material object for method chaining
     */
    public Material setKR(double KR) {
        this.KR = new Double3(KR);
        return this;
    }
    
    /**
     * default constructor
     */
    public Material () {
        // Default constructor
    }
}
