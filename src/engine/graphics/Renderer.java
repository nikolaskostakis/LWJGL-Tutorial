/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author User
 */
public class Renderer {
    public void renderMesh(Mesh mesh) {
        // Bind the VAO for the given mesh
        GL30.glBindVertexArray(mesh.getVAO());
        
        // Enable the first index of the VAO
        GL30.glEnableVertexAttribArray(0);
        
        // Bind the IBO of the given mesh
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        
        // Draw/Render
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndeces().length, GL11.GL_UNSIGNED_INT, 0);
        
        // Unbind it
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        
        // Disable the first index of the VAO
        GL30.glDisableVertexAttribArray(0);
        
        // Unbind the VAO
        GL30.glBindVertexArray(0);
    }
}
