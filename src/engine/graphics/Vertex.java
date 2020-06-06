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
    private Vector3f position;

    /**
     * 
     * @param position 
     */
    public Vertex(Vector3f position) {
        this.position = position;
    }

    /**
     * 
     * @return 
     */
    public Vector3f getPosition() {
        return position;
    }
    
}
