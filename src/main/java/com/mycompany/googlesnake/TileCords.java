/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.googlesnake;

import com.mycompany.googlesnake.GameTask.Move;
import java.awt.Point;

/**
 *
 * @author samue
 */
//this class calculates the pixel coordinates of each (row, col) tile location on the board 
public class TileCords {
    public static Point calculatePixelCords(int row, int col){
        int x = GameParams.TOP_LEFT_BOARD_POINT.x + col * GameParams.TILE_WIDTH; 
        int y= GameParams.TOP_LEFT_BOARD_POINT.y + row * GameParams.TILE_WIDTH; 
        return new Point(x, y);
    }

    public static Point calculateRowCol(int x, int y){
        int row = (y - GameParams.TOP_LEFT_BOARD_POINT.y) / GameParams.TILE_WIDTH; 
        int col = (x - GameParams.TOP_LEFT_BOARD_POINT.y) / GameParams.TILE_WIDTH; 
        return new Point(row, col); 
    }
    

    public static Point calculateSnakeHeadRowColumn(int x, int y){
        System.out.println("Y IS: " + y);
        int col = Math.abs(x - GameParams.TOP_LEFT_BOARD_POINT.x) / GameParams.TILE_WIDTH;
        int row = Math.abs(y - GameParams.TOP_LEFT_BOARD_POINT.y) / GameParams.TILE_WIDTH;
        return new Point(row, col);
    }

    public static Point calculateSnakeHeadCords(int row, int col, int imageWidth, int imageHeight, Move move){
        Point topLeft = calculatePixelCords(row, col);
        //calculate the middle of line where the neck intersects the tile 
        Point middlePoint; 
        Point newPoint; 
        switch(move){
        }
        if(move == Move.RIGHT){
            //center point is on the left side of the tile
            middlePoint = new Point(topLeft.x, topLeft.y + GameParams.TILE_WIDTH / 2);
            newPoint = new Point(middlePoint.x, middlePoint.y - imageHeight/2 ); 
            return newPoint; 
        }
        else if(move == Move.LEFT){
            //center point is on the right side of the tileq
            middlePoint = new Point(topLeft.x + GameParams.TILE_WIDTH, topLeft.y + GameParams.TILE_WIDTH / 2);
            newPoint = new Point(middlePoint.x - imageWidth, middlePoint.y - imageHeight/2);
            return newPoint; 
        }
        else if(move == Move.UP){
            //center is on the bottom side of the tile
            middlePoint = new Point(topLeft.x + GameParams.TILE_WIDTH / 2, topLeft.y + GameParams.TILE_WIDTH); 
            newPoint = new Point(middlePoint.x - imageWidth / 2, middlePoint.y - imageHeight); 
            return newPoint; 
        }
        else if(move == Move.DOWN){
            middlePoint = new Point(topLeft.x + GameParams.TILE_WIDTH / 2, topLeft.y); 
            newPoint = new Point(middlePoint.x - imageWidth / 2, middlePoint.y );
            return newPoint; 
        }
       
        
            
        return null; 
    }
   
}
