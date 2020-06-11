/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

/**
 *
 * @author User
 */
public class Mesh {
    /**
     * Array with the points and their coordinates of of the mesh
     */
    private final Vertex[] vertices;
    /**
     * Pointers towards the points of the mesh. These pointers are accessed
     * according to the drawing method.
     * eg:
     *     a triad of pointers is used when the drawing shapes are triangles etc.
     */
    private final int[] indeces;
    /**
     * VAO as Vertex Array Object
     * 
     * The VAO contains all the VBOs (Vector Buffer Objects)
     */
    private int vao;
    /**
     * PBO as Position Buffer Object
     * 
     * Is the VBO (Vector Buffer Object) for the positions
     */
    private int pbo;
    /**
     * IBO as Indexes Buffer Object
     * 
     * Is the VBO (Vector Buffer Object) for the indexes
     */
    private int ibo;
    /**
     * CBO as Color Buffer Object
     * 
     * Is the VBO (Vector Buffer Object) for the colors
     */
    private int cbo;

    /**
     * 
     * @param vertices
     * @param indeces 
     */
    public Mesh(Vertex[] vertices, int[] indeces) {
        this.vertices = vertices;
        this.indeces = indeces;
    }
    
    /**
     * Creates the VAO
     */
    public void create() {
        // Create the VAO in OpenGL
        this.vao = GL30.glGenVertexArrays();
        
        // Bind the OpenGl VAO with the current one
        GL30.glBindVertexArray(this.vao);
        
        // Create a buffer and an array for the data of the PBO
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(this.vertices.length * 3);
        float[] positionData = new float[this.vertices.length * 3];
        
        // Fill the array with the data of the PBO
        for (int i = 0; i < this.vertices.length; i++) {
            positionData[i * 3] = vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
        }
        
        // Put the array with the data of the PBO to the buffer
        positionBuffer.put(positionData).flip();
        
//        // Create a VBO for the PBO in OpenGL
//        this.pbo = GL15.glGenBuffers();
//        
//        // Bind the OpenGL VBO with the PBO
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.pbo);
//        
//        // Pass the buffer to the OpenGL PBO
//        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
//        
//        // Specify the location in the VAO for the rendering
//        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
//        
//        // Unbind the PBO
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Call the function to initialize the pbo
        pbo = this.storeData(positionBuffer, 0, 3);

        // Create a buffer and an array for the data of the Colors
        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(this.vertices.length * 3);
        float[] colorData = new float[this.vertices.length * 3];
        
        // Fill the array with the colors
        for (int i = 0; i < this.vertices.length; i++) {
            colorData[i * 3] = vertices[i].getColor().getX();
            colorData[i * 3 + 1] = vertices[i].getColor().getY();
            colorData[i * 3 + 2] = vertices[i].getColor().getZ();
        }
        
        // Put the array with the data of the PBO to the buffer
        colorBuffer.put(colorData).flip();
        
        // Call the function to initialize the pbo
        cbo = this.storeData(colorBuffer, 1, 3);
        
        // Create buffer for the data of the IBO
        IntBuffer indecesBuffer = MemoryUtil.memAllocInt(this.indeces.length);
        indecesBuffer.put(this.indeces).flip();
        
        // Create a VBO for the IBO in OpenGL
        this.ibo = GL15.glGenBuffers();
        
        // Bind the OpenGL VBO with the IBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        
        // Pass the buffer to the OpenGL IBO
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indecesBuffer, GL15.GL_STATIC_DRAW);
        
        // Specify the location in the VAO for the rendering
        // Probably this is retundant due to floats and integers having the same size !?
//        GL20.glVertexAttribPointer(1, 3, GL11.GL_INT, false, 0, 0);
        
        // Unbind the IBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }
    
    /**
     * Used to store a vbo
     * 
     * @param buffer
     * @param index
     * @param size
     * @return 
     */
    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID;
        
        // Create a VBO in OpenGL
        bufferID = GL15.glGenBuffers();
        
        // Bind the OpenGL VBO with the PBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        
        // Pass the buffer to the OpenGL PBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        
        // Specify the location in the VAO for the rendering
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        
        // Unbind the PBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        return bufferID;
    }

    /**
     * Destroys all the data of the mesh
     */
    public void destroy() {
        // Delete all the buffers
        GL15.glDeleteBuffers(this.pbo);
        GL15.glDeleteBuffers(this.cbo);
        GL15.glDeleteBuffers(this.ibo);
        
        // Delete the array
        GL30.glDeleteVertexArrays(this.vao);
    }
    
    /**
     * 
     * @return 
     */
    public Vertex[] getVertices() {
        return this.vertices;
    }

    /**
     * 
     * @return 
     */
    public int[] getIndeces() {
        return this.indeces;
    }

    /**
     * 
     * @return 
     */
    public int getVAO() {
        return this.vao;
    }

    /**
     * 
     * @return 
     */
    public int getPBO() {
        return this.pbo;
    }

    /**
     * 
     * @return 
     */
    public int getCBO() {
        return this.cbo;
    }

    /**
     * 
     * @return 
     */
    public int getIBO() {
        return this.ibo;
    }
}
