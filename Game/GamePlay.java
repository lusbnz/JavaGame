package Game;

public class GamePlay extends javax.swing.JFrame {

    public GamePlay() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PlayBackGround = new javax.swing.JPanel();
        Picture = new javax.swing.JPanel();
        WordField = new javax.swing.JTextField();
        SubmitButton = new javax.swing.JButton();
        NextButton = new javax.swing.JButton();
        AnswerReact = new javax.swing.JTextField();
        SoundOfPicture = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PlayBackGround.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout PictureLayout = new javax.swing.GroupLayout(Picture);
        Picture.setLayout(PictureLayout);
        PictureLayout.setHorizontalGroup(
            PictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );
        PictureLayout.setVerticalGroup(
            PictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 301, Short.MAX_VALUE)
        );

        WordField.setBackground(new java.awt.Color(0, 0, 0));
        WordField.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        WordField.setForeground(new java.awt.Color(255, 255, 255));
        WordField.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        WordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WordFieldActionPerformed(evt);
            }
        });

        SubmitButton.setBackground(new java.awt.Color(255, 153, 153));
        SubmitButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 2, 24)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(255, 255, 255));
        SubmitButton.setText("Submit");
        SubmitButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        NextButton.setBackground(new java.awt.Color(0, 204, 204));
        NextButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 2, 24)); // NOI18N
        NextButton.setForeground(new java.awt.Color(255, 255, 255));
        NextButton.setText("Next");
        NextButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        AnswerReact.setBackground(new java.awt.Color(255, 204, 102));
        AnswerReact.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        AnswerReact.setForeground(new java.awt.Color(255, 255, 255));
        AnswerReact.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        SoundOfPicture.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout PlayBackGroundLayout = new javax.swing.GroupLayout(PlayBackGround);
        PlayBackGround.setLayout(PlayBackGroundLayout);
        PlayBackGroundLayout.setHorizontalGroup(
            PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PlayBackGroundLayout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(SoundOfPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PlayBackGroundLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PlayBackGroundLayout.createSequentialGroup()
                                .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(116, 116, 116)
                                .addComponent(NextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PlayBackGroundLayout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(WordField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(AnswerReact, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PlayBackGroundLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(Picture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(61, 61, 61))
        );
        PlayBackGroundLayout.setVerticalGroup(
            PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PlayBackGroundLayout.createSequentialGroup()
                .addGroup(PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PlayBackGroundLayout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(SoundOfPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PlayBackGroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Picture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(WordField, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(AnswerReact))
                .addGap(30, 30, 30)
                .addGroup(PlayBackGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        getContentPane().add(PlayBackGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 730, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void WordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WordFieldActionPerformed
        WordField.setText("");
    }//GEN-LAST:event_WordFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GamePlay().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AnswerReact;
    private javax.swing.JButton NextButton;
    private javax.swing.JPanel Picture;
    private javax.swing.JPanel PlayBackGround;
    private javax.swing.JButton SoundOfPicture;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JTextField WordField;
    // End of variables declaration//GEN-END:variables
}
