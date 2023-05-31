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
    int score; 
    boolean appleOnMap; 
    public enum State{
        INITIAL_SPAWN, SPAWN_APPLE, GAME_OVER, CHASING_APPLE
    }
    public enum Move{
        LEFT, RIGHT, UP, DOWN
    }
    public void run(Snake snake){

        if(currentMove != null){
            snake.move(currentMove);
        }
        
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
        if(move != currentMove){
            currentMove = move; 
        }
    }

}
