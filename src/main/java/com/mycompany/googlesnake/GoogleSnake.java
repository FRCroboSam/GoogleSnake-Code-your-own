/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.googlesnake;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
        String name = Utils.input("Enter a name for the score: ");
        snake.scoreBoard.put(name, snake.score);

        //add score to leaderboard 
        // Serialization
        try {
 
            // Saving of object in a file
            String fileName = "leaderboard.txt";
            FileOutputStream file = new FileOutputStream
                                           (fileName);
            ObjectOutputStream out = new ObjectOutputStream
                                           (file);
 
            // Method for serialization of object
            out.writeObject(snake.scoreBoard);
 
            out.close();
            file.close();
 
            System.out.println("Object has been serialized\n"
                              + "Data before Deserialization.");
 
            // value of static variable changed
        }
 
        catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }
}
