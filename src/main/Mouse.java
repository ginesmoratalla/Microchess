package main;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Pieces.Piece;

import javax.swing.*;

// MOUSE CLASS USES THREE METHODS: PRESS, DRAG, RELEASE
public class Mouse extends MouseAdapter {
    Board board;                                                                                        // Create instance of the board for the methods bellow
    public Mouse(Board board) {
        this.board = board;
    }                                                                                                   // Create constructor of the mouse for the board class

    @Override
    public void mouseDragged(MouseEvent e) {                                                            // Method to drag the piece (no point and click)
        // Doesn't need NullpointerException (You can't drag a null piece)
        if(board.chosenPiece != null) {
            board.chosenPiece.xCord = e.getX() - (board.pieceSize/2) + board.chosenPiece.relativeposX;  // Get the dragged piece and move it (relative pos relates to the individual size of each piece image)
            board.chosenPiece.yCord = e.getY() - (board.pieceSize/2) + board.chosenPiece.relativeposY;  // Get the dragged piece and move it (relative pos relates to the individual size of each piece image)
            board.repaint();                                                                            // Repaint (Graphics 2D) to keep showing where you drag the piece

        }
    }


    @Override
    public void mousePressed(MouseEvent e) {                                                             // Method for when the mouse pressed or clicked
        // Catch NullpointerException when you click an empty space
        try {
            // Get clicked space form the board
            int col = e.getX()/board.spaceSize;
            int row = e.getY()/board.spaceSize;
            // Check if space clicked has a piece
            Piece pieceThere = board.getPiece(col, row);
            if(pieceThere != null) {
                board.chosenPiece = pieceThere;
            }
            // If no piece was found, don't do anything with the exception (else would get an error)
        } catch (Exception ee) {
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {

        // Checks when the mouse is released (Check for NullPointerException again)
        try {
            // Drop dragged piece in selected position
            int col = e.getX()/board.pieceSize;                                                           // Get col and row where piece is dropped
            int row = e.getY()/board.pieceSize;                                                           // Get col and row where piece is dropped
            Position movement = new Position(board, board.chosenPiece, col, row);                         // Make a move with your piece to that space (if valid)
            int posX = board.chosenPiece.col * board.pieceSize + board.chosenPiece.relativeposX;          // If move was made (valid) put piece in that space (with relative pos for the image)
            int posY = board.chosenPiece.row * board.pieceSize + board.chosenPiece.relativeposY;          // If move was made (valid) put piece in that space (with relative pos for the image)

            // If the peice chosen is not null, perform the following methods
            if(board.chosenPiece != null) {

                // Existing piece and valid Move
                if(board.validMove(movement)) {

                    // 1. TURNS
                    // If it is not your turn, you cant move the piece (WARNING MESSAGE)
                    if(movement.piece.isWhite != board.isTurnWhite) {
                        JOptionPane.showMessageDialog(null, "Not your turn, please, wait!", "Turn", JOptionPane.OK_OPTION);

                        // Put piece back to where it was (now invalid move)
                        board.chosenPiece.xCord = posX;
                        board.chosenPiece.yCord = posY;
                    }
                    // 2. If the move puts your own king in check, put piece back (not valid)
                    else if (board.isTurnWhite && board.isKingCheckedAfterMove(true, movement) || !board.isTurnWhite && board.isKingCheckedAfterMove(false, movement) ) {

                        // Put piece back to where it was (now invalid move)
                        board.chosenPiece.xCord = posX;
                        board.chosenPiece.yCord = posY;
                    }

                    // 2. CONFIRM CHECKS AFTER MAKING THE MOVE
                    else {

                        // Make Movement (none of the conditions above was met, valid move)
                        board.makeMove(movement);


                        // WHITE KING CHECK AND CHECKMATE
                        if(board.isWhiteKingChecked(board) && board.isTurnWhite) {         // White king in check and white turn

                            if(board.isKingCheckMated(board.chosenPiece.isWhite)) {        // check for checkmate in Board class
                                JOptionPane.showMessageDialog(null, "Checkmate!\nBlack wins!!", "Checkmate", JOptionPane.OK_OPTION);
                            }
                            // If not checkmate, check
                            else {JOptionPane.showMessageDialog(null, "White king is now in check!", "Check", JOptionPane.OK_OPTION); }

                            // Move the piece to said position and change turn
                            board.makeMove(movement);
                            board.isTurnWhite = !board.isTurnWhite;

                        }

                        // BLACK KING CHECK AND CHECKMATE
                        if(board.isBlackKingChecked(board) && !board.isTurnWhite) {         // Black king in check and white turn

                            if(board.isKingCheckMated(board.chosenPiece.isWhite)) {         // check for checkmate in Board class
                                JOptionPane.showMessageDialog(null, "Checkmate!\nWhite wins!!", "Checkmate", JOptionPane.OK_OPTION);
                            }
                            // If not checkmate, check
                            else {JOptionPane.showMessageDialog(null, "Black king is now in check!", "Check", JOptionPane.OK_OPTION); }

                            // Move the piece to said position
                            board.makeMove(movement);
                            board.isTurnWhite = !board.isTurnWhite;
                        }
                    }
                } else {
                    // NOT A VALID MOVE (RESTORE PIECE LOCATION)
                    board.chosenPiece.xCord = posX;
                    board.chosenPiece.yCord = posY;
                }
            }
            // Dont do anything with the exception (to not get error again)
        } catch (Exception ex) {
        }

        // Reset the piece and reprint the board (Graphics 2d)
        board.chosenPiece = null;
        board.repaint();

    }
}
