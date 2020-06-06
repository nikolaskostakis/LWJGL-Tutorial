/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowPosCallback;

/**
 *
 * @author Nikolas Kostakis
 */
public class Window {

    private final int width, height; // Width & Height of the window 
    private final String title; // Title of the window
    private long windowVar;
    private int frames;
    private static long time;
    
    public Input input;
    
    /**
     * Constructor 
     * @param width
     * @param height
     * @param title 
     */
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }
    
    public void create() {
        // Initialization
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR: GLFW was not initialized!");
            return;
        }
        
        // Create object for inputs
        this.input = new Input();
        
        // Create window
        this.windowVar = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0, 0);
        
        // Check if the window was created
        if (this.windowVar == 0){
            System.err.println("ERROR: Window was not created!");
            return;
        }
        
        // Set postion of window to the center of the primary monitor
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(this.windowVar, ((videoMode.width() - this.width) / 2), ((videoMode.height() - this.height) / 2));
        
        // Make the window the one GLFW changes are made
        GLFW.glfwMakeContextCurrent(this.windowVar);
        
        // Add callbacks to the window
        GLFW.glfwSetKeyCallback(this.windowVar, this.input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(this.windowVar, this.input.getMouseMoveCllback());
        GLFW.glfwSetMouseButtonCallback(this.windowVar, this.input.getMouseButtonsCallback());
        
        // Show the window
        GLFW.glfwShowWindow(this.windowVar);
        
        // Determine the frame rate to 60FPS !?
        // TODO check for different frame rates
        GLFW.glfwSwapInterval(1);
        
        // Get the current time
        Window.time = System.currentTimeMillis();
    }
    
    public void update() {
        // Gets all the callbacks connected to this window
        GLFW.glfwPollEvents();
        
        // Update the frame rate
        this.frames++;
        
        // Check if the time has passed
        // It counts in milliseconds
        if (System.currentTimeMillis() > (Window.time + 1000)){
            // Print frames into the title
            GLFW.glfwSetWindowTitle(this.windowVar, (this.title + " | FPS:" + this.frames));
            
            Window.time = System.currentTimeMillis();
            this.frames = 0;
        }
    }
    
    public void swapBuffers() {
        // Swaps buffers of window
        GLFW.glfwSwapBuffers(this.windowVar);
    }
    
    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(this.windowVar);
    }
    
    public void destroy() {
        this.input.destroy();
        GLFW.glfwWindowShouldClose(this.windowVar);
        GLFW.glfwDestroyWindow(this.windowVar);
        GLFW.glfwTerminate();
    }
}
