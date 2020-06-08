/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.graphics;

import engine.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Nikolas Kostakis
 */
public class Shader {
    private String vertexFile, fragmentFile;
    private int vertexID, fragmentID, programID;
    
    /**
     * 
     * @param vertexPath the file path of the vertex shader
     * @param fragmentPath the file path of the fragment shader
     */
    public Shader(String vertexPath, String fragmentPath) {
        this.vertexFile = FileUtils.loadAsString(vertexPath);
        this.fragmentFile = FileUtils.loadAsString(fragmentPath);
    }
    
    /**
     * Creates the shader
     */
    public void create() {
        this.programID = GL20.glCreateProgram();
        this.vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        
        // Set source code of vertex shader
        GL20.glShaderSource(this.vertexID, this.vertexFile);
        
        // Compile the vertex shader
        GL20.glCompileShader(this.vertexID);
        
        // Check if the compilation was success
        if (GL20.glGetShaderi(this.vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Vertex Shader: " 
                    + GL20.glGetShaderInfoLog(this.vertexID));
            return;
        }
        
        // Create the shader
        this.fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        
        // Set source code of fragment shader
        GL20.glShaderSource(this.fragmentID, this.fragmentFile);
        
        // Compile the fragment shader
        GL20.glCompileShader(this.fragmentID);
        
        // Check if the compilation was success
        if (GL20.glGetShaderi(this.fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Fragment Shader: "
                    + GL20.glGetShaderInfoLog(this.fragmentID));
            return;
        }
        
        // Attach shaders
        GL20.glAttachShader(this.programID, this.vertexID);
        GL20.glAttachShader(this.programID, this.fragmentID);
        
        // Link the program
        GL20.glLinkProgram(this.programID);
        
        // Check if the link was successfull
        if (GL20.glGetProgrami(this.programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.err.println("Program Linking: "
                    + GL20.glGetProgramInfoLog(this.programID));
            return;
        }
        
        // Validate the program
        GL20.glValidateProgram(this.programID);
        
        // Check if the validation was successfull
        if (GL20.glGetProgrami(this.programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Program Validation: "
                    + GL20.glGetProgramInfoLog(this.programID));
            return;
        }
        
        // Delete shaders
        GL20.glDeleteShader(this.vertexID);
        GL20.glDeleteShader(this.fragmentID);
    }
    
    /**
     * Binds the shader
     */
    public void bind() {
        GL20.glUseProgram(this.programID);
    }
    
    /**
     * Unbinds the shader
     */
    public void unbind() {
        GL20.glUseProgram(0);
    }
    
    /**
     * Deletes the program
     */
    public void destroy () {
        GL20.glDeleteProgram(this.programID);
    }
}
