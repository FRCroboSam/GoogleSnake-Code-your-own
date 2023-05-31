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
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
/**
 *
 * @author samue
 */
public class Snake {
    ArrayList<Point> curvePointsList; 
    Move direction; 
    SnakeTail tail; 
    SnakeHead head; 
    ArrayList<SnakeLength> lengths; 
    Move currentMove; 
    //snake spawns with length = 1, 1 head and one tail 
    public Snake(){
        currentMove = Move.RIGHT; 
        lengths = new ArrayList<>(); 
        head = new SnakeHead(GameParams.SNAKE_HEAD_POINT.x, GameParams.SNAKE_HEAD_POINT.y); 
        //head.rotate(currentMove); 
        lengths.add(new SnakeLength(GameParams.SNAKE_HEAD_POINT.x, GameParams.SNAKE_HEAD_POINT.y - 1)); 

        lengths.add(new SnakeLength(GameParams.SNAKE_HEAD_POINT.x, GameParams.SNAKE_HEAD_POINT.y - 2));
        
                
        
    }  
    public void setCurrentMove(Move move){
        this.currentMove = move; 
    }
    public void rotate(Move move){
        System.out.println("ROTATING");
        head.rotate(move);
    }
    
    private enum Type{
        HEAD, BODY, TAIL
    }
    public void drawSnake(Graphics g){
        head.draw(g);
        for(SnakeLength length: lengths){
            length.draw(g); 
        }
//        tail.draw();
        
    }
    public void move(Move move){
        if(move == Move.RIGHT){
            head.prevLocation = head.currentLocation; 

            head.currentLocation.x += 5; 
            for(int i = 0; i < lengths.size(); i++){                    
                lengths.get(i).prevLocation = lengths.get(i).currentLocation; 

                lengths.get(i).currentLocation.x += 5; 
                
                
            }
            
            
            
            
            
        }       
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
        public void draw(Graphics g){
            g.setColor(Color.BLUE);
            g.fillRect(currentLocation.x, currentLocation.y, GameParams.TILE_WIDTH, GameParams.TILE_HEIGHT);
        }
    }
    public class SnakeHead extends SnakeLength{
        BufferedImage image; 
        int currentAngle;
        public enum State{
            ROTATING, NORMAL, GAME_OVER
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
        public void rotate(Move move){
            Scalr.Rotation rotation = Rotating.turnDegrees(currentMove, move); 
            if(rotation != null){
                System.out.println("ROTATING " + move); 
                image = Scalr.rotate(image, rotation, null); 
                currentMove = move; 

            }
            
        }

        @Override
        public void draw(Graphics g){
            Point cords = TileCords.calculateSnakeHeadCords(row, col, image.getWidth(), image.getHeight(), currentMove); 
            Point location = TileCords.calculatePixelCords(row, col);
            g.setColor(Color.BLACK);
            currentLocation = cords; 
            g.drawRect(location.x, location.y, 10, 10);
//            System.out.println(image.getHeight());
            

            g.drawImage(image, currentLocation.x, currentLocation.y , null); 
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
        
           
}
