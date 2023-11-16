
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;

public class Game {

    private JFrame frame;
    private JTextField inputField;
    private JButton submitButton;
    private JTextArea displayArea;
    private JLabel imageLabel;
    private JButton nextButton;

    private String[][] danhSachHinhAnh = {
        //{"C", "A", "T"},
        {"D", "O", "G"},
        {"H", "O", "U", "S", "E"},
        {"T", "R", "E", "E"}
    };

    private int soLuongHinhAnh = danhSachHinhAnh.length;
    private int luaChonHinhAnh = (int) (Math.random() * soLuongHinhAnh);
    private String[] tuCanDoan = danhSachHinhAnh[luaChonHinhAnh];
    private int doDaiTu = tuCanDoan.length;
    private char[] kyTuDoan = new char[doDaiTu];
    private boolean daDoanHet = false;
    private int soLanDoanSai = 0;
    private int soLanDoanToiDa = 5;

    public Game() {
        frame = new JFrame("Game Nhìn hình đoán chữ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon icon = getImage(tuCanDoan);
        imageLabel.setIcon(icon);

        inputField = new JTextField(10);
        submitButton = new JButton("Submit");
        nextButton = new JButton("Next");

        displayArea = new JTextArea(5, 5);
        displayArea.setEditable(false);

        imageLabel.setPreferredSize(new Dimension(1000, 500));
        displayArea.setPreferredSize(new Dimension(1000, 100));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        southPanel.add(inputField);
        southPanel.add(submitButton);
        southPanel.add(nextButton);

        frame.add(imageLabel, BorderLayout.NORTH);
        frame.add(displayArea, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleGuess();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
    }

    private ImageIcon getImage(String[] word) {
        String wordString = String.join("", word);
        String originalImagePath = "src/" + wordString + ".jpg";

        try {
            BufferedImage originalImage = ImageIO.read(new File(originalImagePath));

            if (originalImage != null) {
                Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            } else {
                System.err.println("Error: Unable to load image for word: " + wordString);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void resetGame() {
        luaChonHinhAnh = (int) (Math.random() * soLuongHinhAnh);
        tuCanDoan = danhSachHinhAnh[luaChonHinhAnh];
        doDaiTu = tuCanDoan.length;

        kyTuDoan = new char[doDaiTu];
        daDoanHet = false;
        soLanDoanSai = 0;

        ImageIcon newIcon = getImage(tuCanDoan);
        imageLabel.setIcon(newIcon);

        inputField.setText("");
        inputField.setEnabled(true);
        submitButton.setEnabled(true);
        displayArea.setText("");

        inputField.requestFocusInWindow();
    }

    private void handleGuess() {
        if (!daDoanHet && soLanDoanSai < soLanDoanToiDa) {
            String doan = inputField.getText().toUpperCase();

            if (doan.matches("[A-Z]+")) {
                for (char letter : doan.toCharArray()) {
                    boolean coChu = false;
                    for (int i = 0; i < doDaiTu; i++) {
                        if (tuCanDoan[i].equals(String.valueOf(letter)) && kyTuDoan[i] == 0) {
                            kyTuDoan[i] = letter;
                            coChu = true;
                        }
                    }

                    if (!coChu) {
                        soLanDoanSai++;
                        displayArea.setText("Sai rồi! Bạn đã sai " + soLanDoanSai + " lần.");
                    }
                }
            } else {
                displayArea.setText("Vui lòng chỉ đoán các chữ cái từ A đến Z.");
            }

            StringBuilder displayText = new StringBuilder();
            for (char c : kyTuDoan) {
                if (c == 0) {
                    displayText.append("_");
                } else {
                    displayText.append(c);
                }
            }

            displayArea.append("\nHãy đoán từ có " + doDaiTu + " chữ cái:\n");
            displayArea.append(displayText.toString() + "\n");

            if (String.valueOf(kyTuDoan).equals(String.join("", tuCanDoan))) {
                daDoanHet = true;
            }
        }

        if (daDoanHet || soLanDoanSai == soLanDoanToiDa) {
            if (daDoanHet) {
                displayArea.setText("Chúc mừng! Bạn đã đoán đúng từ \"" + String.join("", tuCanDoan) + "\".");
            } else {
                displayArea.setText("Bạn đã hết số lần đoán. Từ cần đoán là \"" + String.join("", tuCanDoan) + "\".");
            }

            inputField.setEnabled(false);
            submitButton.setEnabled(false);
            inputField.setText("");

        }
        inputField.setText("");
        inputField.requestFocus();
    }

    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
                inputField.requestFocusInWindow();
            }
        });
    }

    public static void main(String[] args) {
        Game gameGUI = new Game();
        gameGUI.display();
        gameGUI.inputField.requestFocusInWindow();
    }
}
