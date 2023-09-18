package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Main class from where the code starts running
public class Main {
    public static void main(String[] args) {

        // Create an instance of Board (from Board class)
        Board board = new Board();
        // Create a frame with the method bello (Main class)
        JFrame frame = getFrame();
        // Favicon Image
        ImageIcon imagefavicon = getimageIcon();
        Image image = imagefavicon.getImage();
        frame.setIconImage(image);
        // Options Menu from Menu class
        JPanel panel = new JPanel();
        Menu menu = new Menu();
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    menu.GetMenu(board);                                // Get panel everytime I press 'Space'
                }
            }
        });
        panel.setFocusable(true);                                       // Enable keyboard focus for the panel
        panel.requestFocusInWindow();                                   // Request keyboard focus for the panel
        frame.add(panel);                                               // Add panel to the frame
        frame.add(board);                                               // Add board to play
        frame.setResizable(false);                                      // Non resizable frame/board
        frame.setVisible(true);                                         // Make it visible
    }
    public static JFrame getFrame() {                                   // Method to get the frame to put the board in
        JFrame frame = new JFrame();
        frame.setTitle("MicroChess (Gines Moratalla)");
        // Size and Layout
        frame.setSize(610, 785);                            // Size of the frame
        frame.setLayout(new BorderLayout());                            // Layout
        frame.setLocationRelativeTo(null);                              // Frame appears at the middle of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }
    public static ImageIcon getimageIcon() {                            // Get favicon image by BufferedImage
        try {
            BufferedImage image = ImageIO.read(Main.class.getResourceAsStream("/resources/0_favicon.png"));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}