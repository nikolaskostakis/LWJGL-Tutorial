/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Nikolas Kostakis
 */
public class Window {
    private int width, height; // Width & Height of the window 
    private final String title; // Title of the window
    private long windowVar;
    private int frames;
    private static long time;
    private Input input;
    // Background colors
    private float backgroundR, backgroundG, backgroundB;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullscreen;
    private int[] windowPosX, windowPosY;
    
    /**
     * Constructor 
     * @param width the width of the window
     * @param height the height of the window
     * @param title the title of the window
     */
    public Window(int width, int height, String title) {
        this.windowPosX = new int[1];
        this.windowPosY = new int[1];
        this.width = width;
        this.height = height;
        this.title = title;
    }
    
    /**
     * Creates the window
     */
    public void create() {
        // Initialization
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR: GLFW was not initialized!");
            return;
        }
        
        // Create object for inputs
        this.input = new Input();
        
        // Create the window
        this.windowVar = GLFW.glfwCreateWindow(this.width, this.height, 
                this.title, (this.isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0), 0);
        
        // Check if the window was created
        if (this.windowVar == 0){
            System.err.println("ERROR: Window was not created!");
            return;
        }
        
        // Set postion of window to the center of the primary monitor
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        this.windowPosX[0] = (videoMode.width() - this.width) / 2;
        this.windowPosY[0] = (videoMode.height() - this.height) / 2;
        GLFW.glfwSetWindowPos(this.windowVar, this.windowPosX[0], this.windowPosY[0]);
        
        // Make the window the one GLFW changes are made
        GLFW.glfwMakeContextCurrent(this.windowVar);
        
        // Make th window rendable
        GL.createCapabilities();
        
        // Depth test
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        // Add callbacks to the window
        this.createCallbacks();
        
        // Show the window
        GLFW.glfwShowWindow(this.windowVar);
        
        // Determine the frame rate to 60FPS !?
        // TODO check for different frame rates
        GLFW.glfwSwapInterval(1);
        
        // Get the current time
        Window.time = System.currentTimeMillis();
    }
    
    /**
     * Creates all the needed callbacks
     */
    private void createCallbacks() {
        // Set the windows new width and height when resized
        this.sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int newWidth, int newHeight) {
                width = newWidth;
                height = newHeight;
                isResized = true;
            }
        };
        
        GLFW.glfwSetKeyCallback(this.windowVar, this.input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(this.windowVar, this.input.getMouseMoveCllback());
        GLFW.glfwSetMouseButtonCallback(this.windowVar, this.input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(this.windowVar, this.input.getMouseScrollCallback());
        GLFW.glfwSetWindowSizeCallback(this.windowVar, this.sizeCallback);
    }
    
    /**
     * Updates the window
     */
    public void update() {
        // Check if the window is resized
        if (this.isResized) {
            // Set the viewport in the new width and height
            GL11.glViewport(0, 0, this.width, this.height);
            this.isResized = false;
        }

        // Set the clear colors
        GL11.glClearColor(this.backgroundR, this.backgroundG, this.backgroundB, 1.0f);

        // Clear
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
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
    
    /**
     * 
     * @return whether or not the window should be closed
     */
    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(this.windowVar);
    }
    
    /**
     * Destroys the window
     */
    public void destroy() {
        this.input.destroy();
        this.sizeCallback.free();
        GLFW.glfwWindowShouldClose(this.windowVar);
        GLFW.glfwDestroyWindow(this.windowVar);
        GLFW.glfwTerminate();
    }
    
    /**
     * Sets the color of the background to the given RGB one
     * 
     * @param red the amount of red in the color
     * @param green the amount of green in the color
     * @param blue the amount of blue in the color
     */
    public void setBackgroundColor(float red, float green, float blue) {
        this.backgroundR = red;
        this.backgroundG = green;
        this.backgroundB = blue;
    }

    /**
     * Sets the window to either fullscreen or not
     * 
     * @param isFullscreen whether or not the window should go in fullscreen or not
     */
    public void setIsFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        this.isResized = true;
        
        // If the window is to be in fullscreen
        if (this.isFullscreen) {
            // Get the current window position
            GLFW.glfwGetWindowPos(this.windowVar, this.windowPosX, this.windowPosY);
            
            // Set fullscreen into the window in the primary monitor
            GLFW.glfwSetWindowMonitor(this.windowVar, GLFW.glfwGetPrimaryMonitor(), 
                    0, 0, this.width, this.height, 0);
        } else {
            // Undo the fullscreen
            GLFW.glfwSetWindowMonitor(this.windowVar, 0, this.windowPosX[0], 
                    this.windowPosY[0], this.width, this.height, 0);
        }
    }

    /**
     * 
     * @return whether the window is in fullscreen or not
     */
    public boolean getIsFullscreen() {
        return isFullscreen;
    }

    /**
     * 
     * @return the width of the window
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * 
     * @return the height of the window
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * 
     * @return the title of the window
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 
     * @return 
     */
    public long getWindowVar() {
        return this.windowVar;
    }
}
