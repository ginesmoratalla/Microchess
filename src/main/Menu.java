package main;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu {
    public Menu() {                                                                   // Constructor for the menu
    }
    public void GetMenu(Board board) {                                                // Get menu method for the main class

        // Get image
        String location = "/resources/pause.jpg";
        BufferedImage image = null;
        try {
            image = ImageIO.read(Board.class.getResourceAsStream(location));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        ImageIcon pauseImage = new ImageIcon(image);

        // Options for the pause menu (parameters)
        Object[] options = {"New Game", "Resume", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an option",
                "Paused Game Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                pauseImage,
                options,
                options[0]
        );

        if (choice == 0) {                                                              // Restart game (resetboard method in Board class resets the board)
            board.resetBoard();

        } else if (choice == 1) {                                                       // Resume game (button only closes the panel)

        } else if (choice == 2) {                                                       // Exit button closes the game and stops the program
            System.exit(0);
        }
    }
}


