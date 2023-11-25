package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;

public class Game {

    private final JFrame frame;
    private final JLabel imageLabel;
    private final JTextArea displayArea;
    final JTextField inputField;
    private final JButton submitButton;
    private final JButton nextButton;
    private final JButton soundButton;
    private final JLabel attemptsLabel;
    private Clip correctGuessSound;
    private Clip incorrectGuessSound;
    private Clip defaultSound;

    private final String[][] danhSachHinhAnh = {
        {"D", "O", "G"},
        {"H", "O", "U", "S", "E"},
        {"T", "R", "E", "E"}
    };

    private final int soLuongHinhAnh = danhSachHinhAnh.length;
    private int luaChonHinhAnh = (int) (Math.random() * soLuongHinhAnh);
    private String[] tuCanDoan = danhSachHinhAnh[luaChonHinhAnh];
    private int doDaiTu = tuCanDoan.length;

    private char[] kyTuDoan = new char[doDaiTu];
    private boolean daDoanHet = false;
    private int soLanDoanSai = 0;
    private final int soLanDoanToiDa = 5;

    public Game() {
        frame = new JFrame("Game Nhìn hình đoán chữ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        inputField = new JTextField(10);
        submitButton = new JButton("Submit");
        nextButton = new JButton("Next");
        soundButton = new JButton("Sound");

        attemptsLabel = new JLabel("Remaining Attempts: " + (soLanDoanToiDa - soLanDoanSai));
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        attemptsLabel.setHorizontalAlignment(JLabel.CENTER);

        displayArea = new JTextArea(5, 5);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 20));

        imageLabel.setPreferredSize(new Dimension(1000, 500));
        displayArea.setPreferredSize(new Dimension(1000, 100));

        ImageIcon icon = getResizedImageIcon(tuCanDoan);
        imageLabel.setIcon(icon);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(inputField);
        buttonPanel.add(submitButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(soundButton);

        JPanel remainingLivesPanel = new JPanel();
        remainingLivesPanel.setLayout(new FlowLayout());
        remainingLivesPanel.add(attemptsLabel);

        southPanel.add(remainingLivesPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(imageLabel, BorderLayout.NORTH);
        frame.add(displayArea, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        loadSounds();

        setupActionListeners();
    }

    private void setupActionListeners() {
        inputField.addActionListener(e -> {
            handleGuess();
            updateAttemptsLabel();
        });
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleGuess();
                    updateAttemptsLabel();
                }
            }
        });
        submitButton.addActionListener(e -> {
            handleGuess();
            updateAttemptsLabel();
        });
        nextButton.addActionListener(e -> resetGame());
        soundButton.addActionListener(e -> onOffSound());
    }

    private void loadSounds() {
        try {
            correctGuessSound = AudioSystem.getClip();
            AudioInputStream correctGuessStream = AudioSystem.getAudioInputStream(new File("src/audios/true.wav"));
            correctGuessSound.open(correctGuessStream);

            incorrectGuessSound = AudioSystem.getClip();
            AudioInputStream incorrectGuessStream = AudioSystem.getAudioInputStream(new File("src/audios/false.wav"));
            incorrectGuessSound.open(incorrectGuessStream);

            defaultSound = AudioSystem.getClip();
            AudioInputStream defaultSoundStream = AudioSystem.getAudioInputStream(new File("src/audios/defaultSound.wav"));
            defaultSound.open(defaultSoundStream);
            defaultSound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
        }
    }

    private ImageIcon getResizedImageIcon(String[] word) {
        String wordString = String.join("", word);
        String originalImagePath = "src/images/" + wordString + ".jpg";

        try {
            BufferedImage originalImage = ImageIO.read(new File(originalImagePath));
            if (originalImage != null) {
                Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            } else {
                System.err.println("Error: Unable to load image for word: " + wordString);
            }
        } catch (IOException e) {
        }
        return null;
    }

    private void updateAttemptsLabel() {
        int remainingAttempts = soLanDoanToiDa - soLanDoanSai;
        attemptsLabel.setText("Số mạng còn lại: " + remainingAttempts);
    }

    private void resetGame() {
        soLanDoanSai = 0;
        updateAttemptsLabel();
        int anhCu = luaChonHinhAnh;
        while (anhCu == luaChonHinhAnh) {
            luaChonHinhAnh = (int) (Math.random() * soLuongHinhAnh);
        }

        tuCanDoan = danhSachHinhAnh[luaChonHinhAnh];
        doDaiTu = tuCanDoan.length;

        kyTuDoan = new char[doDaiTu];
        daDoanHet = false;

        ImageIcon newIcon = getResizedImageIcon(tuCanDoan);
        imageLabel.setIcon(newIcon);

        inputField.setText("");
        inputField.setEnabled(true);
        submitButton.setEnabled(true);
        displayArea.setText("");

        inputField.requestFocusInWindow();
    }

    private void handleGuess() {
        if (!daDoanHet && soLanDoanSai < soLanDoanToiDa) {
            displayArea.setText("");
            String doan = inputField.getText().toUpperCase();
            int soChuCaiDoan = doan.length();
            if (soChuCaiDoan > 1) {
                if (doan.equals(String.join("", tuCanDoan))) {
                    daDoanHet = true;
                    playCorrectGuessSound();
                } else {
                    soLanDoanSai++;
                    playIncorrectGuessSound();
                }
            } else {
                if (doan.matches("[A-Z]+")) {
                    for (char letter : doan.toCharArray()) {
                        boolean coChu = false;
                        for (int i = 0; i < doDaiTu; i++) {
                            if (tuCanDoan[i].equals(String.valueOf(letter)) && kyTuDoan[i] == 0) {
                                kyTuDoan[i] = letter;
                                coChu = true;
                                break;
                            }
                        }

                        if (!coChu) {
                            soLanDoanSai++;
                            playIncorrectGuessSound();
                        }
                    }
                } else {
                    displayArea.setText("Vui lòng chỉ đoán các chữ cái từ A đến Z.");
                }
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
                playCorrectGuessSound();
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

    private void playCorrectGuessSound() {
        if (correctGuessSound != null && !correctGuessSound.isRunning()) {
            correctGuessSound.setFramePosition(0);
            correctGuessSound.start();
        }
    }

    private void playIncorrectGuessSound() {
        if (incorrectGuessSound != null && !incorrectGuessSound.isRunning()) {
            incorrectGuessSound.setFramePosition(0);
            incorrectGuessSound.start();
        }
    }

    private void onOffSound() {
        if (defaultSound != null && defaultSound.isRunning()) {
            defaultSound.stop();
        } else if (defaultSound != null && !defaultSound.isRunning()) {
            defaultSound.start();
        }
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
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.display();
    }
}
