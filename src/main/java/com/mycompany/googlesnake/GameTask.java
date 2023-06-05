/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.googlesnake;
/**
 *
 * @author samue
 */
public class GameTask {
    State currentState; 
    private static Move currentMove = null;
    static boolean isFirstMove = true; 
    int score; 
    private static Move oldMove = null; 
    boolean appleOnMap; 
    public enum State{
        INITIAL_SPAWN, SPAWN_APPLE, GAME_OVER, CHASING_APPLE
    }
    public enum Move{
        LEFT, RIGHT, UP, DOWN
    }
    public void run(Snake snake){
        // System.out.println(oldMove == null);
        if(oldMove != null && currentMove != null){
            snake.move(currentMove);
        }
        if(oldMove != currentMove && oldMove != null){
            System.out.println("ROTATING from OLDMOVE: " + oldMove + " newMOVe: " + currentMove); 
            snake.rotate(currentMove, oldMove);
            oldMove = currentMove; 
            
        }
        oldMove = currentMove; 

        
    //logic:
    //if(apple not on map) - > call snake.spawnApple(), in snake class, it 
    // 
    // unless player is in beginning state(still waiting for a move:
    //          call snake.move(currentMOve); - logic for handling whether to rotate or not in Snake class
    //          
    // if player is still waiting for a move
    //          if(currentMove != null) -> snake.move(currentMove), otherwise do nothing 
//        
//        switch(currentState){
//            case INITIAL_SPAWN:
//                
//                break; 
//                
//            case CHASING_APPLE:
//                break; 
//        
//        }
    }
    public static void setCurrentMove(Move move){
        //check if the move works (ie right to left, up to down doesn't work)
        Boolean moveWorks = Snake.moveWorks(move, currentMove);
        System.out.println(isFirstMove);
        // if(isFirstMove){
        //     if(move != Move.LEFT){
        //         oldMove = Move.RIGHT; 
        //         currentMove = move; 
        //         isFirstMove = false; 
        //     }
        // }
        if((move != currentMove && moveWorks || currentMove == null)){
            currentMove = move; 
            if(oldMove == null){
                oldMove = move; 
            }
        }
        
    }
    //oldMove == nu

}
//start -> oldMove is right 