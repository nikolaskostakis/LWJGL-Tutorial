/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author User
 */
public class Main implements Runnable {

    public Thread game;
    
    public void start() {
        this.game = new Thread(this, "game");
        this.game.start();
    }
    
    public static void init() {
        System.out.println("Initializing Game!");
    }
    
    public void run() {
        Main.init();
        while (true) {
            update();
            render();
        }
    }
    
    private void update(){
        System.out.println("Updating Game!");
    }
    
    private void render(){
        System.out.println("Rendering Game!");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().start();
    }
    
}
