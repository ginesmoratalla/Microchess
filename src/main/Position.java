package main;

import Pieces.Piece;                                                                // Class that will track current piece and next piece
                                                                                    // next place is the place where you drop a piece or when you predict a move
public class Position {

    // Declare instance variables to use for position
    int prevCol;
    int prevRow;
    int nextCol;
    int nextRow;

    // Instance from piece and movement (Piece class)
    Piece piece;
    Piece movement;

    // Create piece constructor
    public Position(Board board, Piece piece, int nextCol, int nextRow) {

        this.prevRow = piece.row;                                                   // Current piece's row
        this.prevCol = piece.col;                                                   // Current piece's col
        this.nextRow = nextRow;                                                     // Set nextRow to see if it is a legal move
        this.nextCol = nextCol;                                                     // Set nextCol to see if it is a legal move

        this.piece = piece;                                                         // Current piece to be "studied"
        this.movement = board.getPiece(nextCol, nextRow);                           // get the piece from the position where you want to jump (make movement)

    }
}
