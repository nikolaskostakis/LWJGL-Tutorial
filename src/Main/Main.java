/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import engine.io.Window;

/**
 *
 * @author Nikolas Kostakis
 */
public class Main implements Runnable{

    public Thread game;
    public static Window window;
    public static final int WIDTH = 1280, HEIGHT = 760;

    public void start() {
        this.game = new Thread(this, "game");
        this.game.start();
    }
    
    public static void init(){
        System.out.println("Initializing Game!");
        
        // Create the window
        window = new Window(Main.WIDTH, Main.HEIGHT, "Game");
        window.create();
    }
    
    public void run(){
        Main.init();
        while (!window.shouldClose()){
            this.update();
            this.render();
        }
    }
    
    private void update(){
        // System.out.println("Updating Game!");
        window.update();
    }
    
    private void render(){
        // System.out.println("Rendering Game!");
        window.swapBuffers();
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        new Main().start();
    }
    
}
