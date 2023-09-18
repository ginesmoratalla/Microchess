package Pieces;

import main.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Queen extends Piece {                                                                                  // Child class from Piece
    public Queen(Board board, int row, int col, boolean isWhite) {                                                  // Create constructor with color and location
        super(board);                                                                                               // Extend from parent class methods
        this.pieceNum = 6;                                                                                          // Identify the piece with a number
        this.row = row;
        this.col = col;
        this.relativeposX = 20;                                                                                     // Position to center the queen image in a space in the board
        this.relativeposY = 15;

        this.xCord = col * board.pieceSize + relativeposX;                                                          // Center the image with the relative pos
        this.yCord = row * board.pieceSize + relativeposY;                                                          // Center the image with the relative pos


        this.isWhite = isWhite;
        String location = isWhite ? "/resources/white_queen.png" : "/resources/black_queen.png";                    // Image source depending on the color

        // Get image and set it to the piece
        try {
            BufferedImage image = ImageIO.read(Bishop.class.getResourceAsStream(location));
            this.pieceIcon = image;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Valid move following the logic from Pawn (Combination of Rook and Bishop moves)
    public boolean isValidMove(int col, int row) {
        // Rook moves on a straight line and Bishop moves diagonally
        int bishopCol = Math.abs(col - this.col);
        int bishopRow = Math.abs(row - this.row);
        return ((row == this.row && col != this.col) || (row != this.row && col == this.col)) || (bishopCol == bishopRow && bishopCol != 0);
    }
    public boolean cantJumpPiece(int col, int row) {
        // Check if there is a piece in the queens direction
        // Check Right
        if (this.col < col) {
            for(int i = this.col + 1; i < col; i++) {
                if(board.getPiece(i, this.row) != null) {
                    return true;
                }
            }
        }
        // Rook Moves

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

        // Bishop Moves
        // Check up Right
        if (this.col < col && this.row > row) {
            for(int i = 1; i < Math.abs(col - this.col); i++) {
                if(board.getPiece(this.col + i, this.row - i) != null) {
                    return true;
                }
            }
        }
        // Check up Left
        if (this.col > col && this.row > row) {
            for(int i = 1; i < Math.abs(col - this.col); i++) {
                if(board.getPiece(this.col - i, this.row - i) != null) {
                    return true;
                }
            }
        }
        // Check down Right
        if (this.col < col && this.row < row) {
            for(int i = 1; i < Math.abs(col - this.col); i++) {
                if(board.getPiece(this.col + i, this.row + i) != null) {
                    return true;
                }
            }
        }
        // Check down Left
        if (this.col > col && this.row < row) {
            for(int i = 1; i < Math.abs(col - this.col); i++) {
                if(board.getPiece(this.col - i, this.row + i) != null) {
                    return true;
                }
            }
        }
        return false;
    }

}
