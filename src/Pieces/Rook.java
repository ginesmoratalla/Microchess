package Pieces;

import main.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Rook extends Piece {                                                                                    // Child class from Piece
    public Rook(Board board, int row, int col, boolean isWhite) {                                                    // Create constructor with color and location
        super(board);                                                                                                // Extend from parent class methods
        this.pieceNum = 2;                                                                                           // Identify the piece with a number
        this.row = row;
        this.col = col;
        this.relativeposX = 35;                                                                                      // Position to center the rook image in a space in the board
        this.relativeposY = 20;

        this.xCord = col * board.pieceSize + relativeposX;                                                           // Center the image with the relative pos
        this.yCord = row * board.pieceSize + relativeposY;                                                           // Center the image with the relative pos

        this.isWhite = isWhite;
        String location = isWhite ? "/resources/white_rook.png" : "/resources/black_rook.png";                       // Image source depending on the color
        try {
            BufferedImage image = ImageIO.read(Bishop.class.getResourceAsStream(location));
            this.pieceIcon = image;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get image and set it to the piece
    public boolean isValidMove(int col, int row) {
        // Rook moves on a straight line
        return (row == this.row && col != this.col) || (row != this.row && col == this.col);
    }
    public boolean cantJumpPiece(int col, int row) {
        // Check there is no pieces in the rook's direction
        // Check Right
        if (this.col < col) {
            for(int i = this.col + 1; i < col; i++) {
                if(board.getPiece(i, this.row) != null) {
                    return true;
                }
            }
        }
        // Check Left
        if (this.col > col) {
            for(int i = this.col - 1; i > col; i--) {
                if(board.getPiece(i, this.row) != null) {
                    return true;
                }
            }
        }
        // Check Up
        if (this.row > row) {
            for(int i = this.row - 1; i > row; i--) {
                if(board.getPiece(this.col, i) != null) {
                    return true;
                }
            }
        }
        // Check Down
        if (this.row < row) {
            for(int i = this.row + 1; i < row; i++) {
                if(board.getPiece(this.col, i) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
