/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 *
 * @author Nikolas Kostakis
 */
public class Input {
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;
    
    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseButtons;
    
    /**
     * 
     */
    public Input(){
        this.keyboard = new GLFWKeyCallback() {
            // Add invoke methode
            // Runs this function every time a ket is pressed on the keyboard !?
            public void invoke(long window, int key, int scancode, int action, int mods){
                // Store the action of the keyboard key
                Input.keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };
        
        this.mouseButtons = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods){
                // Store the action of the keyboard key
                Input.buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };
        
        this.mouseMove = new GLFWCursorPosCallback() {
            public void invoke(long window, double xPos, double yPos){
                // Store the action of the keyboard key
                Input.mouseX = xPos;
                Input.mouseY = yPos;
            }
        };
    }
    
    /**
     * 
     * @param key keyboard key
     * @return whether or not the specified keyboard key is pressed
     */
    public static boolean isKeyDown(int key) {
        return Input.keys[key];
    }
    
    /**
     * 
     * @param button mouse button
     * @return whether or not the specified mouse button is pressed
     */
    public static boolean isButtonDown(int button) {
        return Input.buttons[button];
    }
    
    public void destroy(){
        this.keyboard.free();
        this.mouseMove.free();
        this.mouseButtons.free();
    }

    /**
     * 
     * @return the X position of the cursor
     */
    public static double getMouseX() {
        return Input.mouseX;
    }

    /**
     * 
     * @return the Y position of the cursor
     */
    public static double getMouseY() {
        return Input.mouseY;
    }

    /**
     * 
     * @return 
     */
    public GLFWKeyCallback getKeyboardCallback() {
        return this.keyboard;
    }

    /**
     * 
     * @return 
     */
    public GLFWCursorPosCallback getMouseMoveCllback() {
        return this.mouseMove;
    }

    /**
     * 
     * @return 
     */
    public GLFWMouseButtonCallback getMouseButtonsCallback() {
        return this.mouseButtons;
    }
    
}
