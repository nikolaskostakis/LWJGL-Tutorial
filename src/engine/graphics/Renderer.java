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
    private Shader shader;
    
    /**
     * 
     * @param shader 
     */
    public Renderer(Shader shader) {
        this.shader = shader;
    }
    
    /**
     * 
     * @param mesh the mesh to be rendered
     */
    public void renderMesh(Mesh mesh) {
        // Bind the VAO for the given mesh
        GL30.glBindVertexArray(mesh.getVAO());
        
        // Enable the first index of the VAO, for the pbo
        GL30.glEnableVertexAttribArray(0);

        // Enable the second index of the VAO, for the cbo
        GL30.glEnableVertexAttribArray(1);
        
        // Bind the IBO of the given mesh
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        
        // Bind the shader
        this.shader.bind();
        
        // Draw/Render
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndeces().length, GL11.GL_UNSIGNED_INT, 0);
        
        //Unbind the shader
        this.shader.unbind();
        
        // Unbind the IBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        
        // Disable the first index of the VAO
        GL30.glDisableVertexAttribArray(0);
        
        // Disable the second index of the VAO
        GL30.glDisableVertexAttribArray(1);
        
        // Unbind the VAO
        GL30.glBindVertexArray(0);
    }
}
