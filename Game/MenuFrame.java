package Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame {

    private final JFrame menuFrame;

    public MenuFrame() {
        menuFrame = new JFrame("Game Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(400, 200);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        JPanel panel = new JPanel();
        panel.add(startButton);

        menuFrame.add(panel);
    }

    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                menuFrame.setVisible(true);
            }
        });
    }

    private void startGame() {
        menuFrame.dispose();
        Game gameGUI = new Game();
        gameGUI.display();
        gameGUI.inputField.requestFocusInWindow();
    }

    public static void main(String[] args) {
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.display();
    }
}
