package Pieces;
import main.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Piece {                                                                                // Piece class parent for every type of piece

    // Instance variables for a piece
    public int col;                                                                                 // Position in the board
    public int row;                                                                                 // Position in the board
    public int xCord;                                                                               // Coordinates for a piece (position in the board*space size)
    public int yCord;                                                                               // Coordinates for a piece (position in the board*space size)
    public int relativeposX;                                                                        // Relative position for each image, to center it into the board
    public int relativeposY;                                                                        // Relative position for each image, to center it into the board

    public boolean isWhite;                                                                         // Color for the current piece
    public int pieceNum;                                                                            // Piece identifier with a number
    Board board;                                                                                    // Instance of a board
    Image pieceIcon;                                                                                // Image for every piece
    public Piece(Board board) {                                                                     // Constructor for Piece with the current board
        this.board = board;
    }
    public boolean isValidMove(int col, int row) {                                                  // Valid move for the logic of each piece (Changes on each piece)
        return true;
    }
    public boolean cantJumpPiece(int col, int row) {                                               // If pieces have a piece cutting the movement to one of the sides
        return false;
    }
    public void paint(Graphics2D graphics2D) {                                                      // Graphics 2D method to print the image on the coordinates in the board
        graphics2D.drawImage(pieceIcon, xCord, yCord, null);
    }



}
