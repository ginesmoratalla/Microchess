package Pieces;

import Pieces.Piece;
import main.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Pawn extends Piece {                                                                                   // Child class from Piece
    public Pawn(Board board, int row, int col, boolean isWhite) {                                                   // Create constructor with color and location
        super(board);                                                                                               // Extend from parent class methods
        this.pieceNum = 1;                                                                                          // Identify the piece with a number
        this.row = row;
        this.col = col;
        this.relativeposX = 40;                                                                                     // Position to center the pawn image in a space in the board
        this.relativeposY = 20;

        this.xCord = col * board.pieceSize + relativeposX;                                                          // Center the image with the relative pos
        this.yCord = row * board.pieceSize + relativeposY;                                                          // Center the image with the relative pos

        this.isWhite = isWhite;
        String location = isWhite ? "/resources/white_pawn.png" : "/resources/black_pawn.png";                      // Image source depending on the color

        // Get image and set it to the piece
        try {
            BufferedImage image = ImageIO.read(Bishop.class.getResourceAsStream(location));
            this.pieceIcon = image;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Valid move following the logic from Pawn (Diagonal to eat, or 1 move up)
    public boolean isValidMove(int col, int row) {
        // Pawn cannot jump to the same row
        int checkColor = isWhite ? 1 : -1;

        // Pawn going straight move
        if(this.col == col && row == this.row - checkColor && board.getPiece(col, row) == null) {
            return true;
        }

        // Capturing left
        if(col == this.col - 1 && row == this.row - checkColor && board.getPiece(col, row) != null) {
            return true;
        }
        // Capturing right
        if(col == this.col + 1 && row == this.row - checkColor && board.getPiece(col, row) != null) {
            return true;
        }

        return false;
    }

    // Pawn only makes jumps 1 space long
}
