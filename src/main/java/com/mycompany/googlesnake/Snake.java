/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.googlesnake;

import com.mycompany.googlesnake.GameTask.Move;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point; 
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
/**
 *
 * @author samue
 */
public class Snake {
    ArrayList<Point> curvePointsList; 
    Move direction; 
    SnakeTail tail; 
    SnakeHead head; 
    Apple apple; 
    ArrayList<SnakeLength> lengths; 
    Move currentMove;
    HashMap<String, Integer> scoreBoard; 
    Move newMove; 
    int score = 0; 
    boolean gameOver = false; 

    //when new move is called, set to true when 
    boolean waitingForTurn = false; 
    //snake spawns with length = 1, 1 head and one tail 
    public Snake(){
        //fetch the leaderboard 
        String fileName = "leaderboard.ser"; 
        try
        {  
            File tempFile = new File("leaderboard.ser");
            if (tempFile.createNewFile()) {
                System.out.println("File created: " );
              } else {
                System.out.println("File already exists.");
              }
            FileInputStream file = new FileInputStream
                                         (fileName);
            ObjectInputStream in = new ObjectInputStream(file); 
 
            // Method for deserialization of object
            scoreBoard = (HashMap<String, Integer>)in.readObject();
 
            in.close();
            file.close();
            System.out.println("Object has been deserialized\n"
                                + "Data after Deserialization.");
 
            // System.out.println("z = " + object1.z);
 
        }
        catch (IOException ex) {
            System.out.println("IOException is caught");
        }
 
        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                                " is caught");
        }
        if(scoreBoard == null){
            scoreBoard = new HashMap<>(); 
        }

        currentMove = Move.RIGHT; 
        lengths = new ArrayList<>(); 
        head = new SnakeHead(GameParams.SNAKE_HEAD_POINT.x, GameParams.SNAKE_HEAD_POINT.y); 
        apple = new Apple(3, 10); 
        //head.rotate(currentMove); 
        lengths.add(new SnakeLength(GameParams.SNAKE_HEAD_POINT.x, GameParams.SNAKE_HEAD_POINT.y - 1)); 

        lengths.add(new SnakeLength(GameParams.SNAKE_HEAD_POINT.x, GameParams.SNAKE_HEAD_POINT.y - 2));
        lengths.add(new SnakeLength(GameParams.SNAKE_HEAD_POINT.x, GameParams.SNAKE_HEAD_POINT.y - 3));
        lengths.add(new SnakeLength(GameParams.SNAKE_HEAD_POINT.x, GameParams.SNAKE_HEAD_POINT.y - 4));
        

                
        
    }  
    public void spawnApple() {
        apple.spawn(); 
    }
    public void checkGameOover() {
    }
    public void setCurrentMove(Move move){
        this.currentMove = move; 
    }
    //determines if a move can be done 
    public static boolean moveWorks(Move move, Move oldMove){
        return Rotating.turnDegrees(oldMove, move) != null;
    }
    public void rotate(Move move){
        System.out.println("ROTATING");
        head.rotate(move);
    }
    public void rotate(Move newMove, Move oldMove){
        head.rotate(oldMove, newMove);
    }
    
    private enum Type{
        HEAD, BODY, TAIL
    }
    public void drawSnake(Graphics g){
        g.setColor(Color.black); 
        g.drawString("SCORE IS: "+ score, 10, 10); 
        g.setColor(Color.RED);
        apple.draw(g); 
        head.draw(g);
        g.setColor(Color.BLUE);
        for(SnakeLength length: lengths){
            length.draw(g); 
        }
//        tail.draw();
    }
    
    //basic move (moves one tile at a time) 
    public void move(Move move){
        //generic code that moves the snake head up, down, right, left 
        System.out.println("HEAD ROW: " + head.row + " Head COL: " + head.col);
        System.out.println("HEAD Y:" + head.currentLocation.y);
        
        head.prevLocation = head.currentLocation; 
        int xOffset = 0; 
        int yOffset = 0; 
        lengths.get(0).prevLocation = lengths.get(0).currentLocation; 
        lengths.get(0).currentLocation = TileCords.calculatePixelCords(head.row, head.col); 
         //head.currentLocation; 

        // lengths.get(1).currentLocation =lengths.get(0).prevLocation; 
        for(int i = 1; i<lengths.size(); i++){
            lengths.get(i).prevLocation = lengths.get(i).currentLocation;                
            lengths.get(i).currentLocation = lengths.get(i-1).prevLocation;    
        }
        switch(move){
            case RIGHT:
                head.currentLocation.x += GameParams.TILE_HEIGHT;
                xOffset = GameParams.TILE_WIDTH;
                head.col += 1; 
                // head.row = TileCords.calculateSnakeHeadRowColumn(head.currentLocation.x + 20, head.currentLocation.y + 30).x; 
                // head.col = TileCords.calculateSnakeHeadRowColumn(head.currentLocation.x + 20, head.currentLocation.y + 30).y;  
                break; 
            case LEFT:
                head.col -= 1; 
                head.currentLocation.x -= GameParams.TILE_HEIGHT;
                xOffset = -GameParams.TILE_WIDTH;
                // head.row = TileCords.calculateSnakeHeadRowColumn(head.currentLocation.x + 20, head.currentLocation.y + 30).x; 
                // head.col = TileCords.calculateSnakeHeadRowColumn(head.currentLocation.x + 20, head.currentLocation.y + 30).y;  
                break; 
            case UP:
                head.row -= 1; 
                yOffset = -GameParams.TILE_WIDTH; 
                head.currentLocation.y -= GameParams.TILE_HEIGHT;
                // head.row = TileCords.calculateSnakeHeadRowColumn(head.currentLocation.x + 20, head.currentLocation.y + 30).x; 
                // head.col = TileCords.calculateSnakeHeadRowColumn(head.currentLocation.x + 20, head.currentLocation.y + 30).y; 
                break; 
            case DOWN:
                head.row += 1; 
                yOffset = GameParams.TILE_WIDTH; 
                head.currentLocation.y += GameParams.TILE_HEIGHT;
                // head.row = TileCords.calculateSnakeHeadRowColumn(head.currentLocation.x + 30, head.currentLocation.y + 20).x; 
                // head.col = TileCords.calculateSnakeHeadRowColumn(head.currentLocation.x + 30, head.currentLocation.y + 20).y; 
                break; 
        }

             
        //check if the apple is eaten 
        if(apple.isEaten()){
            lengths.add(new SnakeLength()); 
        }
        if(checkGameOver()){

        }
        //check if game over 

    } 
    //current version, turn head when it is anywhere within the tile
    public void executeTurn(Move move, Move prevMove){
        head.rotate(move);

    }
    public boolean checkGameOver(){
        if(head.row < 0 || head.row == GameParams.NUM_ROWS || head.col < 0 || head.col == GameParams.NUM_COLUMNS){
            gameOver = true; 
            return true; 
        }
        for(SnakeLength length: lengths){
            if(length.currentLocation != null){
                Point lengthCords = TileCords.calculateRowCol(length.currentLocation.x, length.currentLocation.y);
                if(lengthCords.x == head.row && lengthCords.y == head.col){
                    gameOver = true; 
                    return true; 
                }
            }

        }
        return false; 
    }


    public class SnakeLength{
        int row; 
        int col; 
        Point currentLocation; 
        Point prevLocation; 
        public SnakeLength(int row, int col){
            this.currentLocation = TileCords.calculatePixelCords(row, col); 
            this.row = row; 
            this.col = col; 
        }
        //call this to add lengths after eating apple
        public SnakeLength(){
        }
        public void draw(Graphics g){
            if(currentLocation != null){
                g.fillRoundRect(currentLocation.x, currentLocation.y, GameParams.TILE_WIDTH, GameParams.TILE_HEIGHT, 10, 10);
            }
        }
    }
    public class SnakeHead extends SnakeLength{
        BufferedImage image; 
        int currentAngle;
        public enum State{
            ROTATING, NORMAL, GAME_OVER
        }
        public enum Turn{
            CLOCKWISE, COUNTERCLOCK
        }
        public SnakeHead(int row, int col){
            super(row, col);
            this.currentAngle = 0; 
          
            try{
                image = ImageIO.read(new File("SnakeHead.png"));
                // image = ImageIO.read(new File("C:\\Autodesk\\NetBeansProjects\\SnakeGame\\src\\main\\java\\Resources\\snakeHead.png"));
            } catch (IOException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            image = Scalr.resize(image, Scalr.Method.QUALITY, 80, 80); 

            this.currentLocation  = TileCords.calculateSnakeHeadCords(row, col, image.getWidth(), image.getHeight(), currentMove); 

            
        }
        //move is the new direction after the turn 
        public void rotate(Move move){
            System.out.println("Current MOVE: " + currentMove + " New Move:  " + move);
            Scalr.Rotation rotation = Rotating.turnDegrees(currentMove, move);
            if(rotation != null){
                System.out.println("ROTATING " + move + " ACTUALLy"); 
                image = Scalr.rotate(image, rotation, null); 
                prevLocation = currentLocation; 
                System.out.println("ROW: "  + row + "COL: "+ col);
                // currentLocation = TileCords.calculateSnakeHeadCords(row, col, image.getWidth(), image.getHeight(), move);
                currentMove = move; 

            }
        }
        public void rotate(Move oldMove, Move newMove){
            Scalr.Rotation rotation = Rotating.turnDegrees(oldMove, newMove);
            if(rotation != null){
                image = Scalr.rotate(image, rotation, null); 
                prevLocation = currentLocation; 
                System.out.println("ROW: "  + row + "COL: "+ col);
                // currentLocation = TileCords.calculateSnakeHeadCords(row, col, image.getWidth(), image.getHeight(), move);
                currentMove = newMove; 

            }
        }

        @Override
        public void draw(Graphics g){
            Point cords = TileCords.calculateSnakeHeadCords(row, col, image.getWidth(), image.getHeight(), currentMove); 
            // Point location = TileCords.calculatePixelCords(row, col);
            g.setColor(Color.BLACK);
            // currentLocation = cords; 
//            System.out.println(image.getHeight());
            

            g.drawImage(image, cords.x, cords.y , null); 
            // g.fillRect(currentLocation.x + 20 , currentLocation.y + 30, 10, 10);
        }
        
    
    
    //implement this like snake head in the future 

    }
    
    public class SnakeTail extends SnakeLength{
        public SnakeTail(int row, int col){
            super(row, col); 
        }
    }
    private class Rotating{

        static ArrayList<Move> directionOrder = new ArrayList<Move>() {{
           add(Move.UP);
           add(Move.RIGHT);
           add(Move.DOWN);
           add(Move.LEFT);
        }};

        //this version only supports 90 degree turns 
        public static Scalr.Rotation turnDegrees(Move current, Move turn){
            int orderIndex = 0; 
            int gapBetweenMoves = directionOrder.indexOf(current) - directionOrder.indexOf(turn); 
            if(Math.abs(gapBetweenMoves) == 2){
                return null; 
            }
            else if(gapBetweenMoves == 1 || gapBetweenMoves == -3 ){
                System.out.println("CURRENT: " + current);
                System.out.println("TURN: " + turn);
                return Scalr.Rotation.CW_270; 
            }
            else if(gapBetweenMoves == -1 || gapBetweenMoves == 3){

                return Scalr.Rotation.CW_90; 
            }
            return null; 
        }
        public static Scalr.Rotation turnXDegrees(int degrees, BufferedImage image,  Graphics g, int x, int y){


            // Rotation information

            double rotationRequired = Math.toRadians (-15);
            double locationX = image.getWidth() / 2;
            double locationY = image.getHeight() / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            Graphics2D g2d = (Graphics2D)g; 
            // Drawing the rotated image at the required drawing locations
            g2d.drawImage(op.filter(image, null), x, y, null);
            return null; 
        }

    }

    public class Apple{
        int row; 
        int col; 
        boolean show = true; 
        public Apple(int row, int col){
            this.row = row; 
            this.col = col; 
        }

        public void draw(Graphics g){
            if(show){
                Point location = TileCords.calculatePixelCords(row, col);
                g.setColor(Color.red);
                g.fillRoundRect(location.x, location.y, GameParams.TILE_WIDTH, GameParams.TILE_HEIGHT, 20, 20);
            }

        }
        public void spawn(){
            Random random = new Random(); 
            row = random.nextInt(GameParams.NUM_ROWS);
            col = random.nextInt(GameParams.NUM_COLUMNS);
            show = true; 
        }
        public boolean isEaten(){
            if(head.row == row && head.col == col){
                spawn(); 
                score++; 
                return true; 
            }

            return false; 
        }
    }  
           
}
//Logic
/**
 * Player presses arrow key, if it is a valid move (ie right after up ), set a move 
 * set rotating head = true 
 * when head is within 5 pixels of the next tile, 
    * rotate the head 
    * when done rotating,, set head to move like a new
 * 
 * 
 * 
 **/