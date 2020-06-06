/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.maths;

/**
 *
 * @author Nikolas Kostakis
 */
public class Vector3f {
    private float x, y, z;

    /**
     * 
     * @param x 
     * @param y
     * @param z 
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * 
     * @param x
     * @param y
     * @param z 
     */
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * 
     * @return 
     */
    public float getX() {
        return x;
    }

    /**
     * 
     * @param x 
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * 
     * @return 
     */
    public float getY() {
        return y;
    }

    /**
     * 
     * @param y 
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * 
     * @return 
     */
    public float getZ() {
        return z;
    }

    /**
     * 
     * @param z 
     */
    public void setZ(float z) {
        this.z = z;
    }
    
}
