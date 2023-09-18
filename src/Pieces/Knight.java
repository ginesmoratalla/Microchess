package Pieces;

import Pieces.Piece;
import main.Board;
import main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Knight extends Piece {                                                                               // Child class from Piece
    public Knight(Board board, int row, int col, boolean isWhite) {                                               // Create constructor with color and location
        super(board);                                                                                             // Extend from parent class methods
        this.pieceNum = 3;                                                                                        // Identify the piece with a number
        this.row = row;
        this.col = col;
        this.relativeposX = 20;                                                                                   // Position to center the knight image in a space in the board
        this.relativeposY = 20;

        this.xCord = col * board.pieceSize + relativeposX;                                                        // Center the image with the relative pos
        this.yCord = row * board.pieceSize + relativeposY;                                                        // Center the image with the relative pos

        this.isWhite = isWhite;
        String location = isWhite ? "/resources/white_knight.png" : "/resources/black_knight.png";                // Image source depending on the color

        // Get image and set it to the piece
        try {
            BufferedImage image = ImageIO.read(Bishop.class.getResourceAsStream(location));
            this.pieceIcon = image;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Valid move following the logic from Knight (L movements)
    public boolean isValidMove(int col, int row) {
        int LknightCol = Math.abs(col - this.col);
        int LknightRow = Math.abs(row - this.row);

        return LknightCol * LknightRow == 2;

    }

    // Knight can jump through pieces
}
