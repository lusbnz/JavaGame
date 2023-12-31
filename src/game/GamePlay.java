package game;

/*
    Team 5.14: Game hỗ trợ học tập
 */
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class GamePlay extends javax.swing.JFrame {

    private Clip correctGuessSound;
    private Clip incorrectGuessSound;
    private Clip defaultSound;
    String folderPath = "src/images";
    File folder = new File(folderPath);
    File[] files = folder.listFiles();

    private final String[][] danhSachHinhAnh = new String[files.length][];

    private final int soLuongHinhAnh;
    private int luaChonHinhAnh;
    private String[] tuCanDoan;
    private int doDaiTu;

    private char[] kyTuDoan = new char[doDaiTu];
    private boolean daDoanHet = false;
    private int soLanDoanSai = 0;
    private final int soLanDoanToiDa = 10;

    public GamePlay() {
        initComponents();

        // init variable and list image array
        int i = 0;
        for (File file : files) {
            String[] nameOfImage = file.getName().split("\\.")[0].split("");
            danhSachHinhAnh[i] = nameOfImage;
            i++;
        }
        soLuongHinhAnh = danhSachHinhAnh.length;
        luaChonHinhAnh = (int) (Math.random() * soLuongHinhAnh);
        tuCanDoan = danhSachHinhAnh[luaChonHinhAnh];
        doDaiTu = tuCanDoan.length;
        kyTuDoan = new char[doDaiTu];

        ImageIcon icon = getResizedImageIcon(tuCanDoan);
        Picture.setIcon(icon);

        loadSounds();

        setupActionListeners();

    }

    private void setupActionListeners() {
        WordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleGuess();
                updateAttemptsLabel();
            }
        });
        WordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleGuess();
                    updateAttemptsLabel();
                }
            }
        });
        SubmitButton.addActionListener(e -> {
            handleGuess();
            updateAttemptsLabel();
        });
        NextButton.addActionListener(e -> resetGame());
        SoundButton.addActionListener(e -> onOffSound());
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
                Image resizedImage = originalImage.getScaledInstance(260, 300, Image.SCALE_SMOOTH);
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
        Picture.setIcon(newIcon);

        WordField.setText("");
        WordField.setEnabled(true);
        SubmitButton.setEnabled(true);
        DisplayArea.setText("");

        WordField.requestFocusInWindow();
    }

    private void handleGuess() {
        if (!daDoanHet && soLanDoanSai < soLanDoanToiDa) {
            DisplayArea.setText("");
            String doan = WordField.getText().toUpperCase();

            if (doan.length() == doDaiTu || doan.length() > 1) {
                if (doan.equals(String.join("", tuCanDoan))) {
                    kyTuDoan = doan.toCharArray();
                    daDoanHet = true;
                    playCorrectGuessSound();
                } else {
                    soLanDoanSai++;
                    playIncorrectGuessSound();
                }
            } else if (doan.length() == 1) {
                boolean coChu = false;
                for (int i = 0; i < doDaiTu; i++) {
                    if (tuCanDoan[i].equals(doan) && kyTuDoan[i] == 0) {
                        kyTuDoan[i] = doan.charAt(0);
                        coChu = true;
                    }
                }

                if (!coChu) {
                    soLanDoanSai++;
                    playIncorrectGuessSound();
                }
            }

            if (String.valueOf(kyTuDoan).equals(String.join("", tuCanDoan))) {
                daDoanHet = true;
                playCorrectGuessSound();
            }

            StringBuilder displayText = new StringBuilder();
            for (char c : kyTuDoan) {
                if (c == 0) {
                    displayText.append("_");
                } else {
                    displayText.append(c);
                }
            }

            DisplayArea.append("\nHãy đoán từ có " + doDaiTu + " chữ cái:\n");
            DisplayArea.append(displayText.toString() + "\n");
        }

        if (daDoanHet || soLanDoanSai == soLanDoanToiDa) {
            if (daDoanHet) {
                DisplayArea.setText("Chúc mừng! Bạn đã đoán đúng từ \"" + String.join("", tuCanDoan) + "\".");
            } else {
                DisplayArea.setText("Bạn đã hết số lần đoán. Từ cần đoán là \"" + String.join("", tuCanDoan) + "\".");
            }

            WordField.setEnabled(false);
            SubmitButton.setEnabled(false);
            WordField.setText("");
        }

        WordField.setText("");
        WordField.requestFocus();
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
                new GamePlay().setVisible(true);
                WordField.requestFocusInWindow();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        PlayBackGround = new javax.swing.JPanel();
        SubmitButton = new javax.swing.JButton();
        NextButton = new javax.swing.JButton();
        attemptsLabel = new javax.swing.JLabel();
        SoundButton = new javax.swing.JButton();
        Picture = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DisplayArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PlayBackGround.setBackground(new java.awt.Color(51, 51, 51));

        WordField.setBackground(new java.awt.Color(0, 0, 0));
        WordField.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        WordField.setForeground(new java.awt.Color(255, 255, 255));
        WordField.setAutoscrolls(false);
        WordField.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SubmitButton.setBackground(new java.awt.Color(255, 153, 153));
        SubmitButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 2, 24)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(255, 255, 255));
        SubmitButton.setText("Submit");
        SubmitButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        NextButton.setBackground(new java.awt.Color(113, 226, 153));
        NextButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 2, 24)); // NOI18N
        NextButton.setForeground(new java.awt.Color(255, 255, 255));
        NextButton.setText("Next");
        NextButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        attemptsLabel.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        attemptsLabel.setForeground(new java.awt.Color(255, 51, 51));
        attemptsLabel.setText("Số mạng còn lại: 10");

        SoundButton.setBackground(new java.awt.Color(0, 0, 0));
        SoundButton.setFont(new java.awt.Font("Vladimir Script", 3, 18)); // NOI18N
        SoundButton.setForeground(new java.awt.Color(102, 204, 255));
        SoundButton.setText("<//>");
        SoundButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        SoundButton.setFocusable(false);
        SoundButton.setPreferredSize(new java.awt.Dimension(50, 50));
        SoundButton.setRequestFocusEnabled(false);

        DisplayArea.setColumns(20);
        DisplayArea.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DisplayArea.setForeground(new java.awt.Color(204, 102, 0));
        DisplayArea.setRows(3);
        DisplayArea.setAutoscrolls(false);
        DisplayArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DisplayArea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DisplayArea.setDisabledTextColor(new java.awt.Color(255, 153, 0));
        DisplayArea.setEnabled(false);
        DisplayArea.setFocusable(false);
        DisplayArea.setSelectionColor(new java.awt.Color(255, 153, 0));
        jScrollPane1.setViewportView(DisplayArea);

        javax.swing.GroupLayout PlayBackGroundLayout = new javax.swing.GroupLayout(PlayBackGround);
        PlayBackGround.setLayout(PlayBackGroundLayout);
        PlayBackGroundLayout.setHorizontalGroup(
                PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PlayBackGroundLayout.createSequentialGroup()
                                .addContainerGap(188, Short.MAX_VALUE)
                                .addGroup(PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PlayBackGroundLayout.createSequentialGroup()
                                                .addComponent(Picture, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(113, 113, 113))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PlayBackGroundLayout.createSequentialGroup()
                                                .addComponent(WordField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(165, 165, 165))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PlayBackGroundLayout.createSequentialGroup()
                                                .addGroup(PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(PlayBackGroundLayout.createSequentialGroup()
                                                                .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(NextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                                        .addComponent(attemptsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(72, 72, 72)))
                                .addComponent(SoundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60))
        );
        PlayBackGroundLayout.setVerticalGroup(
                PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PlayBackGroundLayout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addGroup(PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(SoundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(PlayBackGroundLayout.createSequentialGroup()
                                                .addComponent(Picture, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(WordField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(attemptsLabel)
                                                .addGap(2, 2, 2)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(NextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(72, Short.MAX_VALUE))
        );

        SoundButton.getAccessibleContext().setAccessibleParent(SoundButton);

        getContentPane().add(PlayBackGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 730, 630));

        pack();
    }// </editor-fold>                        

    public static void main(String args[]) throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Game_GUI gg = new Game_GUI();
        gg.display();

    }

    // Variables declaration - do not modify                     
    private javax.swing.JTextArea DisplayArea;
    private javax.swing.JButton NextButton;
    private javax.swing.JLabel Picture;
    private javax.swing.JPanel PlayBackGround;
    private javax.swing.JButton SoundButton;
    private javax.swing.JButton SubmitButton;
    final javax.swing.JTextField WordField = new javax.swing.JTextField();
    private javax.swing.JLabel attemptsLabel;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}
