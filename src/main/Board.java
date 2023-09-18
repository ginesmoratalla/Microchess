package main;
import Pieces.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Board extends JPanel {
    // Size for the board and for the coordinates of the pieces (same) // It will be the same so it won't be differential
    public int spaceSize = 150;
    public int pieceSize = 150;

    // Chosenpiece from Piece class constructor to check for validation moves
    public Piece chosenPiece;

    // Booleans to check for pawn promotion for the two colors
    public boolean whitePromoted = false;
    public boolean blackPromoted = false;

    // Boolean for turns (white always begins)
    public boolean isTurnWhite = true;

    // List of pieces which will be updated constantly and will be used for check and checkmate (ArrayList to access directly)
    ArrayList<Piece> listOfPieces = new ArrayList<>();
    Mouse mouse = new Mouse(this);                                                                      // Create a mouse instance to move the mouse through the board

    // Create list of pieces and add them at the beginning
    public void addPieces() {

        // These will be the original pieces in the board (accessed again if you restart the game)

        listOfPieces.add(new King(this, 0, 0, false));
        listOfPieces.add(new Knight(this, 0, 1, false));
        listOfPieces.add(new Bishop(this, 0, 2, false));
        listOfPieces.add(new Rook(this, 0, 3, false));
        listOfPieces.add(new Pawn(this, 1, 0, false));

        listOfPieces.add(new King(this, 4, 3, true));
        listOfPieces.add(new Knight(this, 4, 2, true));
        listOfPieces.add(new Bishop(this, 4, 1, true));
        listOfPieces.add(new Rook(this, 4, 0, true));
        listOfPieces.add(new Pawn(this, 3, 3, true));
    }


    // Constructor for the board
    public Board() {

        // Sizes, mouseactionlistener to move the pieces and add them from the list above
        this.setPreferredSize(new Dimension(600, 750));
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        setFocusable(true);
        addPieces();
    }

    // Get piece at current position
    public Piece getPiece(int col, int row) {
        // Iterate though the pieces until you find the piece at row and col, if not found return null
        for(Piece piece : listOfPieces) {
            if(piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    // Make a move in the board
    public void makeMove(Position movement) {
        // Call position from Position class
        movement.piece.col = movement.nextCol;                                              // Set current piece's position to the next position where you dropped the piece (Mouse released in Mouse class)
        movement.piece.row = movement.nextRow;                                              // Set current piece's position to the next position where you dropped the piece (Mouse released in Mouse class)
        movement.piece.xCord = movement.nextCol * spaceSize + chosenPiece.relativeposX;     // Put the piece in the new position (row and col is the theory, and the coords multiply the piece by the piecesize set above)
        movement.piece.yCord = movement.nextRow * spaceSize + chosenPiece.relativeposY;     // Set the coordinates to print the piece in the row*size and col*size

        captureMove(movement);                                                              // Eat piece in the new space from the board (even if null)
        PawnPromotion(movement.piece);                                                      // Check for pawn promotion every move
        isTurnWhite = !isTurnWhite;                                                         // Change turn
    }


    public boolean validMove(Position move) {                                                // A move will be valid if these conditions are false
        // You move a piece to a space where there is a piece of the same team (method in board)
        if(ChecksameTeam(move.piece, move.movement)) {
            return false;
        }
        // If the move for the piece logic itself is not valid (e.g. a rook moving diagonally)
        if(!move.piece.isValidMove(move.nextCol, move.nextRow)) {
            return false;
        }
        // If a piece can't jump another piece explained in the Piece class
        if(move.piece.cantJumpPiece(move.nextCol, move.nextRow)) {
            return false;
        }
        // If you move a piece outside the board, it is not a valid move (added after testing)
        if(move.nextCol < 0 || move.nextCol >= 4 || move.nextRow < 0 || move.nextRow >= 5) {
            return false;
        }
        // If all false, it is a valid move
        return true;
    }
    public void captureMove(Position move) {                                                    // Eats a piece by removing the piece from the list
        listOfPieces.remove(move.movement);
    }

    public boolean ChecksameTeam(Piece piece1, Piece piece2) {                                  // Same team method
        if (piece1 == null || piece2 == null) {                                                 // Takes two pieces, if they are the same color, return true
            return false;
        }
        return piece1.isWhite == piece2.isWhite;                                                // Made to check for a valid jump to a space in the board
    }

    public void PawnPromotion(Piece piece) {                                                    // Pawn promotion for when a piece is moved

        // Image for the panel with the message
        String location = "/resources/pawn_promotion.png";
        BufferedImage image = null;
        try {
            image = ImageIO.read(Board.class.getResourceAsStream(location));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        ImageIcon imagePawnProm = new ImageIcon(image);

        // IF THE PIECE TAKEN AS ARGUMENT IS A PAWN AND IT GETS TO THE LAST ROW OF EACH SIDE, PROMOTE IT TO WHATEVER YOU SELECT IN THE MESSAGE
        // WHITE PAWN PROMOTED
        if (piece.pieceNum == 1 && piece.isWhite && piece.row == 0 && whitePromoted == false) {
            whitePromoted = true;
            // Options to promote the pawn
            Object[] options = { "Rook", "Bishop", "Knight", "Queen"};

            int selection = JOptionPane.showOptionDialog(null, "White pawn promoted! \n Choose a piece to promote it to: ", "Pawn Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, imagePawnProm, options, options[0]);

            // Removes the pawn from the list and adds the piece you select to the same space where the pawn was
            listOfPieces.remove(piece);
            if (selection == 0) {
                listOfPieces.add(new Rook(this, piece.row, piece.col, piece.isWhite));
            } else if (selection == 1) {
                listOfPieces.add(new Bishop(this, piece.row, piece.col, piece.isWhite));
            } else if (selection == 2) {
                listOfPieces.add(new Knight(this, piece.row, piece.col, piece.isWhite));
            } else if (selection == 3) {
            listOfPieces.add(new Queen(this, piece.row, piece.col, piece.isWhite));
            }
        }
        // BLACK PAWN PROMOTED
        if (piece.pieceNum == 1 && !piece.isWhite && piece.row == 4 && blackPromoted == false) {
            blackPromoted = true;
            // Options to promote the pawn
            Object[] options = { "Rook", "Bishop", "Knight", "Queen"};

            int selection = JOptionPane.showOptionDialog(null, "Black pawn promoted! \n Choose a piece to promote it to: ", "Pawn Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, imagePawnProm, options, options[0]);

            // Removes the pawn from the list and adds the piece you select to the same space where the pawn was
            listOfPieces.remove(piece);
            if (selection == 0) {
                listOfPieces.add(new Rook(this, piece.row, piece.col, piece.isWhite));
            } else if (selection == 1) {
                listOfPieces.add(new Bishop(this, piece.row, piece.col, piece.isWhite));
            } else if (selection == 2) {
                listOfPieces.add(new Knight(this, piece.row, piece.col, piece.isWhite));
            } else if (selection == 3) {
                listOfPieces.add(new Queen(this, piece.row, piece.col, piece.isWhite));
            }
        }

    }

    // Method that iterates through the list and finds the king (Given by number 4 in Piece num class which corresponds to the King
    Piece kingIsHere(boolean isWhite) {
        for(Piece piece : listOfPieces) {
            if (isWhite == piece.isWhite && piece.pieceNum == 4) {
                return piece;
            }
        }
        // If it reaches here, theres no king
        return null;
    }

    // Checks if king is checked or can be unchecked by other piece or by the king itself
    public boolean isKingCheckedAfterMove(boolean isWhite, Position move) {

        // Find the king and store it in king
        Piece king = kingIsHere(isWhite);
        if (king == null) {
            return false;
        }
        // Get the piece from the new move and remove it from the list if not null
        Piece capturedPiece = getPiece(move.nextCol, move.nextRow);
        if (capturedPiece != null) {
            listOfPieces.remove(capturedPiece);
        }
        // Create new piece as temporal
        Piece movedPiece = move.piece;
        // Store the piece's last position
        int oldCol = movedPiece.col;
        int oldRow = movedPiece.row;
        // Update it to new position and go for check bellow
        movedPiece.col = move.nextCol;
        movedPiece.row = move.nextRow;

        // check if the move puts the king in check
        for (Piece piece : listOfPieces) {
            // Pieces of color contrary to the king
            if (piece.isWhite != king.isWhite) {

                // If the piece form the list can move to where the king is without jumping over other pieces, it will be a check
                if(piece.isValidMove(king.col, king.row) && !piece.cantJumpPiece(king.col, king.row)) {

                    // Add the removed piece above back to the list (It was only to check for a temporal move)
                    if (capturedPiece != null) {
                        listOfPieces.add(capturedPiece);
                    }
                    // Restore the piece with the previous position
                    movedPiece.col = oldCol;
                    movedPiece.row = oldRow;
                    // Check
                    return true;
                }
            }
        }


        // Add the removed piece above back to the list (It was only to check for a temporal move)
        if (capturedPiece != null) {
            listOfPieces.add(capturedPiece);
        }
        // Restore the piece with the previous position
        movedPiece.col = oldCol;
        movedPiece.row = oldRow;
        // No check
        return false;
    }

    public boolean isKingCheckMated(boolean isWhite) {                                       // Checkmate method
        // Find king first
        Piece king = kingIsHere(!isWhite);
        if (king == null) {
            return false;
        }
        // Create a copy of the current list of pieces (Done after test)
        ArrayList<Piece> CopiedListPieces = new ArrayList<>(listOfPieces);

        for (Piece piece : CopiedListPieces) {                                                // Iterate through the copied list
            if (piece.isWhite == king.isWhite) {
                                                                                              // Go through every space in the board
                for (int i = 0; i < 4; i++) {       // cols
                    for (int j = 0; j < 5; j++) {   // rows
                        // consider all possible moves for this piece
                        // If it is valid, create temp position with this move
                        if (piece.isValidMove(i, j)) {
                            Position tempMove = new Position(this, piece, i, j);
                            Piece capturedPiece = getPiece(i, j);

                            // remove piece from that space (for now)
                            if (capturedPiece != null) {
                                listOfPieces.remove(capturedPiece);                           // Iterate though the copy but remove and add from the original list
                            }
                            // Store the piece's current position
                            int oldCol = piece.col;
                            int oldRow = piece.row;
                            // Make a temporal move to the current space from the nested for loop
                            Piece movedPiece = tempMove.piece;
                            movedPiece.col = i;
                            movedPiece.row = j;
                            // check if this move gets the king out of check
                            // Make temporal move
                            boolean kingStillChecked = isKingCheckedAfterMove(king.isWhite, tempMove);

                            // Restore the piece back to where it was
                            movedPiece.col = oldCol;
                            movedPiece.row = oldRow;
                            piece.col = oldCol;
                            piece.row = oldRow;

                            // Add back the captured piece
                            if (capturedPiece != null) {
                                listOfPieces.add(capturedPiece);                              // Iterate though the copy but remove and add from the original list
                            }
                            // If the move moved the king out of check, return false, else return true bellow
                            if (!kingStillChecked) {
                                // This move gets the king out of check
                                System.out.println("Not Checkmate");
                                return false;
                            }

                        }
                    }
                }
            }
        }
        // Restore the list with the copied list's data (to avoid errors in the original list
        if (!CopiedListPieces.isEmpty()) {
            listOfPieces.addAll(CopiedListPieces);
        }
        // If we reached here, no moves from any of the pieces can remove your king from check, so it is a checkmate
        System.out.println("Checkmate yes!");
        return true;
    }


    // Checks if White king is currently in check
    public boolean isWhiteKingChecked(Board board) {
        // Get king
        Piece white = kingIsHere(true);
        // Iterate through all the pieces and see if the king is on check
        for (Piece piece : board.listOfPieces) {
            if (piece.isWhite != white.isWhite) {
                if(piece.isValidMove(white.col, white.row) && !piece.cantJumpPiece(white.col, white.row)) {
                    return true;
                }
            }
        }
        return false;
    }
    // Checks if black king is currently in check
    public boolean isBlackKingChecked(Board board) {
        Piece black = kingIsHere(false);
        for (Piece piece : board.listOfPieces) {
            if (piece.isWhite != black.isWhite) {
                if(piece.isValidMove(black.col, black.row) && !piece.cantJumpPiece(black.col, black.row)) {
                    return true;
                }
            }
        }
        return false;
    }


    // Method from Graphics 2d to print the board, the spaces where you can jump to (highlighted) and the images
    public void paintComponent(Graphics graph) {
        // Create instance from Graphics2D
        Graphics2D g = (Graphics2D) graph;
        // Board spaces colors
        // Dark spaces
        Color color1 = new Color(119, 56, 35);
        // Light spaces
        Color color2 = new Color(248, 162, 109);
        // Confirmed piece
        Color color3 = new Color(0, 0, 0, 108);
        // Capturable piece
        Color color4 = new Color(0xBEE31008, true);

        // Fill chess board
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 4; j++) {
                g.setColor((j + i) % 2 == 0 ? color1 : color2);
                g.fillRect(j * spaceSize, i * spaceSize, spaceSize, spaceSize);
            }
        }

        for(Piece piece : listOfPieces) {
            // Show small circle wherever we can move said piece (highlight possible moves)
            if(chosenPiece != null) {
                // All the possible spaces in the baord
                for(int i = 0; i < 5; i++) {
                    for(int j = 0; j < 4; j++) {
                        if(validMove(new Position(this, chosenPiece, j, i))) {
                            Color color = color3;
                            if (getPiece(j, i) != null && !ChecksameTeam(chosenPiece, getPiece(j, i))) {
                                color = color4;                                                                                                // If there is an available jump to a space with a piece from the opposite color, change the highlight color to red
                            }
                            // Set size of the circle for available position
                            int squareCenterX = j * spaceSize + spaceSize / 2;
                            int squareCenterY = i * spaceSize + spaceSize / 2;
                            int radius = spaceSize / 5;
                            // Draw the circle
                            g.setColor(color);
                            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                            g.fillOval(squareCenterX - radius, squareCenterY - radius, radius * 2, radius * 2);               // Draw the circle with the data given above
                            g.setComposite(AlphaComposite.SrcOver);                                                                             // Stop coloring the available spaces
                        }
                    }
                }
            }
            // Print the piece from Piece class
            piece.paint(g);
        }
    }

    // Reset the board from the Menu options
    public void resetBoard() {
        // Reset variables to their initial state
        whitePromoted = false;
        blackPromoted = false;
        isTurnWhite = true;
        listOfPieces.clear();
        addPieces();
        // Repaint the board
        repaint();
    }
}

