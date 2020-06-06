/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Nikolas Kostakis
 */
public class Main implements Runnable {

    public Thread game;
    public Window window;
    public final int WIDTH = 1280, HEIGHT = 760;
    
    // A simple mesh with a rectangle rentangle to be rendered
    public Renderer renderer;
    public Mesh mesh = new Mesh(new Vertex[] {
        new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f)),
        new Vertex(new Vector3f(0.5f, 0.5f, 0.0f)),
        new Vertex(new Vector3f(0.5f, -0.5f, 0.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f))
    }, new int[] {
        0, 1, 2,
        0, 3, 2,
    });

    public void start() {
        this.game = new Thread(this, "game");
        this.game.start();
    }
    
    public void init() {
        System.out.println("Initializing Game!");
        
        // Create oblect for window
        this.window = new Window(this.WIDTH, this.HEIGHT, "Game");
        
        // Set the background color for the window
        this.window.setBackgroundColor(1.0f, 0, 0);
                
        // Create the window
        this.window.create();
        
        // Create the rectangle and the renderer
        renderer = new Renderer();
        mesh.create();
    }
    
    public void run() {
        // Run the initialization 
        this.init();
        
        // While the window is open or the ESC key is not pressed
        while (!this.window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            // Update
            this.update();
            
            // Render
            this.render();
        }
        
        // Close the window
        this.window.destroy();
    }
    
    private void update() {
        // System.out.println("Updating Game!");
        this.window.update();
        
        // Check if the left mouse button is pressed
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("X: " + Input.getMouseX() + ", Y:" + Input.getMouseY());
        }
    }
    
    private void render() {
        // Render the mesh
        renderer.renderMesh(mesh);
        
        // System.out.println("Rendering Game!");
        this.window.swapBuffers();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().start();
    }
    
}
