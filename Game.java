// import các thư viện cần sử dụng

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

public class Game {

    // khởi tạo các components
    private final JFrame frame;
    private final JLabel imageLabel;
    private final JTextArea displayArea;
    private final JTextField inputField;
    private final JButton submitButton;
    private final JButton nextButton;

    // mảng chứa các từ cần đoán, đặt tên file ảnh .jpg trùng với tên trong mảng viết hoa
    private final String[][] danhSachHinhAnh = {
        {"D", "O", "G"},
        {"H", "O", "U", "S", "E"},
        {"T", "R", "E", "E"}
    };

    // khởi tạo các biến cần đoán
    private final int soLuongHinhAnh = danhSachHinhAnh.length;
    private int luaChonHinhAnh = (int) (Math.random() * soLuongHinhAnh);
    private String[] tuCanDoan = danhSachHinhAnh[luaChonHinhAnh];
    private int doDaiTu = tuCanDoan.length;

    // khởi tạo các biến đoán
    private char[] kyTuDoan = new char[doDaiTu];
    private boolean daDoanHet = false;
    private int soLanDoanSai = 0;
    private final int soLanDoanToiDa = 5;

    // constructor
    public Game() {
        // khung frame cho màn hình game
        frame = new JFrame("Game Nhìn hình đoán chữ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        // hình ảnh cần đoán
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // cho hình ảnh vào chính giữa màn hình

        // các phím chức năng
        inputField = new JTextField(10); // độ dài ban đầu là 10 cột
        submitButton = new JButton("Submit");
        nextButton = new JButton("Next");

        displayArea = new JTextArea(5, 5); // kích thước ban đầu là 5 dòng 5 cột
        displayArea.setEditable(false); // không cho chỉnh sửa vùng hiển thị thông tin
        displayArea.setFont(new Font("Arial", Font.PLAIN, 20)); // font chữ

        // thiết lập kích thước vùng ưu tiên
        imageLabel.setPreferredSize(new Dimension(1000, 500));
        displayArea.setPreferredSize(new Dimension(1000, 100));

        // cho hình ảnh vào panel
        ImageIcon icon = getResizedImageIcon(tuCanDoan);
        imageLabel.setIcon(icon);

        // cho các phím chức năng vào panel
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        southPanel.add(inputField);
        southPanel.add(submitButton);
        southPanel.add(nextButton);

        // cho các panel vào miền
        frame.add(imageLabel, BorderLayout.NORTH);
        frame.add(displayArea, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        // gọi hàm thực thi hành động
        setupActionListeners();
    }

    private void setupActionListeners() {
        inputField.addActionListener(e -> handleGuess());
        // lắng nghe sự kiện Key sử dụng lớp KeyAdapter
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            // khi một phím được nhả sẽ gọi phương thức keyReleased
            public void keyReleased(KeyEvent e) {
                // xảy ra khi bấm nút Enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleGuess();
                }
            }
        });
        submitButton.addActionListener(e -> handleGuess());
        nextButton.addActionListener(e -> resetGame());
    }

    // chỉnh lại kích thước chung của ảnh
    private ImageIcon getResizedImageIcon(String[] word) {
        // lấy path của ảnh
        String wordString = String.join("", word);
        String originalImagePath = "src/" + wordString + ".jpg";

        try {
            // Đọc hình ảnh gốc từ path
            BufferedImage originalImage = ImageIO.read(new File(originalImagePath));
            // Kiểm tra xem hình ảnh đã được đọc thành công hay không
            if (originalImage != null) {
                // Thay đổi kích thước hình ảnh
                Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            } else {
                System.err.println("Error: Unable to load image for word: " + wordString);
            }
        } catch (IOException e) {
        }
        return null;
    }

    // reset khi bấm nút next
    private void resetGame() {
        // kiểm tra trùng lặp ảnh vì sử dụng random
        int anhCu = luaChonHinhAnh;
        while (anhCu == luaChonHinhAnh) {
            luaChonHinhAnh = (int) (Math.random() * soLuongHinhAnh);
        }

        // thiết lập game mới
        tuCanDoan = danhSachHinhAnh[luaChonHinhAnh];
        doDaiTu = tuCanDoan.length;

        kyTuDoan = new char[doDaiTu];
        daDoanHet = false;
        soLanDoanSai = 0;

        ImageIcon newIcon = getResizedImageIcon(tuCanDoan);
        imageLabel.setIcon(newIcon);

        inputField.setText("");
        inputField.setEnabled(true);
        submitButton.setEnabled(true);
        displayArea.setText("");

        // đặt con trỏ vào vị trí ô input
        inputField.requestFocusInWindow();
    }

    // xử lí sự kiện đoán
    private void handleGuess() {
        // kiểm tra điều kiện sự kiện đoán
        if (!daDoanHet && soLanDoanSai < soLanDoanToiDa) {
            String doan = inputField.getText().toUpperCase();

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
                        displayArea.setText("Sai rồi! Bạn đã sai " + soLanDoanSai + " lần.");
                    }
                }
            } else {
                displayArea.setText("Vui lòng chỉ đoán các chữ cái từ A đến Z.");
            }

            StringBuilder displayText = new StringBuilder();
            for (char c : kyTuDoan) {
                if (c == 0) {
                    displayText.append("_"); // nếu có từ trống thì hiển thị _
                } else {
                    displayText.append(c);
                }
            }

            // hiện thị thông tin
            displayArea.append("\nHãy đoán từ có " + doDaiTu + " chữ cái:\n");
            displayArea.append(displayText.toString() + "\n");

            if (String.valueOf(kyTuDoan).equals(String.join("", tuCanDoan))) {
                daDoanHet = true;
            }
        }

        // kết thúc sự kiện đoán
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
        inputField.requestFocus(); // đặt con trỏ vào vị trí ô input
    }

    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
                inputField.requestFocusInWindow(); // đặt con trỏ vào vị trí ô input
            }
        });
    }

    public static void main(String[] args) {
        Game gameGUI = new Game();
        // hiển thị giao diện
        gameGUI.display();
        // đặt con trỏ vào vị trí ô input
        gameGUI.inputField.requestFocusInWindow();
    }
}
