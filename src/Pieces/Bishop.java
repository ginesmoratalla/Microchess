package Pieces;
import main.Board;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Bishop extends Piece {                                                                     // Child class from Piece
    public Bishop(Board board, int row, int col, boolean isWhite) {                                     // Create constructor with color and location
        super(board);                                                                                   // Extend from parent class methods
        this.pieceNum = 5;                                                                              // Identify the piece with a number
        this.row = row;
        this.col = col;
        this.relativeposX = 15;                                                                         // Position to center the bishop image in a space in the board
        this.relativeposY = 15;

        this.xCord = col * board.pieceSize + relativeposX;                                              // Center the image with the relative pos
        this.yCord = row * board.pieceSize + relativeposY;                                              // Center the image with the relative pos
        this.isWhite = isWhite;

        String location = isWhite ? "/resources/white_bishop.png" : "/resources/black_bishop.png";      // Image source depending on the color

        // Get image and set it to the piece
        try {
            BufferedImage image = ImageIO.read(Bishop.class.getResourceAsStream(location));
            this.pieceIcon = image;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Valid move following the logic from Bishop (Diagonal Moves)
    public boolean isValidMove(int col, int row) {
        // Bishop Moves diagonally
        int bishopCol = Math.abs(col - this.col);
        int bishopRow = Math.abs(row - this.row);
        return bishopCol == bishopRow && bishopCol != 0;
    }

    // Method to check that a piece can't jump other pieces
    public boolean cantJumpPiece(int col, int row) {
        // Method that checks if the bishop jumps another piece, therefore, the extention of its movements stop if there is a piece in the way
        // Check for pieces that might be up Right
        if (this.col < col && this.row > row) {
            for(int i = 1; i < Math.abs(col - this.col); i++) {
                if(board.getPiece(this.col + i, this.row - i) != null) {
                    return true;
                }
            }
        }
        // Check for pieces that might be up Left
        if (this.col > col && this.row > row) {
            for(int i = 1; i < Math.abs(col - this.col); i++) {
                if(board.getPiece(this.col - i, this.row - i) != null) {
                    return true;
                }
            }
        }
        // Check for pieces that might be down Right
        if (this.col < col && this.row < row) {
            for(int i = 1; i < Math.abs(col - this.col); i++) {
                if(board.getPiece(this.col + i, this.row + i) != null) {
                    return true;
                }
            }
        }
        // Check for pieces that might be down Left
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
