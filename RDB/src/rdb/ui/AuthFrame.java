package rdb.ui;

import java.awt.Component;
import javax.swing.JOptionPane;
import rdb.delegate.InitializationDelegate;
import rdb.shared.Errors;

public class AuthFrame extends javax.swing.JFrame {
  private static final int MAX_ATTEMPTS = 5;

  private final InitializationDelegate delegate;
  private int attempts = 0;

  public AuthFrame(InitializationDelegate sessionManager) {
    this.delegate = sessionManager;
    initComponents();
  }

  public static void showAboutDialog(Component parent) {
    JOptionPane.showMessageDialog(parent,
        "RDB 0.99 for UBC CPSC304 2020W1;\nNOT FOR PUBLIC DISTRIBUTION.\n\n"
        + "Brought to you by:\n"
        + "- Rohit Bassi\n- David Zhou\n- Brendon Irwan\n"
        + "-- NetBeans's GUI Designer\n\n"
        + "P.S. Our Milestone 2 grade hasn't been fixed.",
        "About RDB", JOptionPane.INFORMATION_MESSAGE);
  }

  // generated method
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    labelMessage = new javax.swing.JLabel();
    labelUser = new javax.swing.JLabel();
    fieldUser = new javax.swing.JTextField();
    labelPass = new javax.swing.JLabel();
    fieldPass = new javax.swing.JPasswordField();
    buttonAuth = new javax.swing.JButton();
    buttonQuit = new javax.swing.JButton();
    labelStatus = new javax.swing.JLabel();
    menu = new javax.swing.JMenuBar();
    menuApplication = new javax.swing.JMenu();
    menuAbout = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("RDB");
    setResizable(false);

    labelMessage.setText("DBMS credentials required to proceed.");

    labelUser.setText("Username:");

    fieldUser.setPreferredSize(new java.awt.Dimension(256, 24));

    labelPass.setText("Password:");

    fieldPass.setToolTipText("");
    fieldPass.setPreferredSize(new java.awt.Dimension(256, 24));

    buttonAuth.setText("Authorize");
    buttonAuth.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonAuthActionPerformed(evt);
      }
    });

    buttonQuit.setText("Quit");
    buttonQuit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonQuitActionPerformed(evt);
      }
    });

    labelStatus.setText("Ready.");

    menuApplication.setText("Application");

    menuAbout.setText("About");
    menuAbout.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuAboutActionPerformed(evt);
      }
    });
    menuApplication.add(menuAbout);

    menu.add(menuApplication);

    setJMenuBar(menu);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(labelMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(buttonAuth)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonQuit))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(labelUser)
              .addComponent(labelPass))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(fieldPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(fieldUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(labelMessage)
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(labelUser)
          .addComponent(fieldUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(labelPass)
          .addComponent(fieldPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(buttonQuit)
          .addComponent(buttonAuth))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(labelStatus)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void buttonAuthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAuthActionPerformed
    buttonAuth.setEnabled(false);
    labelStatus.setText("Opening database...");
    update(getGraphics());
    int errorCode
        = this.delegate.openDatabase(fieldUser.getText(), fieldPass.getText());
    labelStatus.setText(Errors.parseErrorCode(errorCode));
    if (errorCode == Errors.CREDENTIALS && ++this.attempts >= MAX_ATTEMPTS) {
      JOptionPane.showMessageDialog(this,
          "Too many failed attempts. Application will quit.",
          "RDB", JOptionPane.ERROR_MESSAGE);
      dispose();
    } else if (errorCode == Errors.SUCCESS) {
      dispose();
      this.delegate.startMainUi();
      return;
    }
    fieldUser.requestFocus();
    buttonAuth.setEnabled(true);
  }//GEN-LAST:event_buttonAuthActionPerformed

  private void buttonQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuitActionPerformed
    dispose();
  }//GEN-LAST:event_buttonQuitActionPerformed

  private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
    showAboutDialog(this);
  }//GEN-LAST:event_menuAboutActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonAuth;
  private javax.swing.JButton buttonQuit;
  private javax.swing.JPasswordField fieldPass;
  private javax.swing.JTextField fieldUser;
  private javax.swing.JLabel labelMessage;
  private javax.swing.JLabel labelPass;
  private javax.swing.JLabel labelStatus;
  private javax.swing.JLabel labelUser;
  private javax.swing.JMenuBar menu;
  private javax.swing.JMenuItem menuAbout;
  private javax.swing.JMenu menuApplication;
  // End of variables declaration//GEN-END:variables
}
