package Pieces;
import main.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class King extends Piece {                                                                           // Child class from Piece
    public King(Board board, int row, int col, boolean isWhite) {                                           // Create constructor with color and location
        super(board);                                                                                       // Extend from parent class methods
        this.pieceNum = 4;                                                                                  // Identify the piece with a number
        this.row = row;
        this.col = col;
        this.relativeposX = 23;                                                                             // Position to center the king image in a space in the board
        this.relativeposY = 20;

        this.xCord = col * board.pieceSize + relativeposX;                                                  // Center the image with the relative pos
        this.yCord = row * board.pieceSize + relativeposY;                                                  // Center the image with the relative pos

        this.isWhite = isWhite;
        String location = isWhite ? "/resources/white_king.png" : "/resources/black_king.png";              // Image source depending on the color

        // Get image and set it to the piece
        try {
            BufferedImage image = ImageIO.read(Bishop.class.getResourceAsStream(location));
            this.pieceIcon = image;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Valid move following the logic from King (One step everywhere)
    public boolean isValidMove(int col, int row) {
        // King moves one step at a time, either diagonally or straight
        int kingCol = Math.abs(col - this.col);
        int kingRow = Math.abs(row - this.row);
        return (kingRow <= 1 && kingCol <= 1) && (kingRow != 0 || kingCol != 0);
    }
    // King can't jump other pieces
}