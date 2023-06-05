/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.googlesnake;

/**
 *
 * @author samue
 */
public class GoogleSnake {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");
        GameFrame frame = new GameFrame();
        Snake snake = new Snake(); 
        frame.setSnake(snake);
        frame.setName("Game Panel 1");
        frame.run(); 
        //spawnApple for beginning of the 
        snake.spawnApple(); 
        GameTask task = new GameTask();
        while(true && !snake.checkGameOver()){
            task.run(snake);
            Thread.sleep(500);
        }
    }
}
