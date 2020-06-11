/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.graphics;

import engine.maths.Vector3f;

/**
 *
 * @author User
 */
public class Vertex {
    private Vector3f position, color;

    /**
     * 
     * @param position 
     */
    public Vertex(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    /**
     * 
     * @return 
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * 
     * @return 
     */
    public Vector3f getColor() {
        return this.color;
    }
    
}
