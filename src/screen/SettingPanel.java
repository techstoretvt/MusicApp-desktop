/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import component.CustomScrollBarUI;
import component.ModelHenGio;
import javax.swing.ImageIcon;
import services.LocalData;
import frame.MainJFrame;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import component.MyCustomDialog;

/**
 *
 * @author tranv
 */
public class SettingPanel extends javax.swing.JPanel {

    /**
     * Creates new form SettingPanel
     */
    public SettingPanel() {
        initComponents();
        jScrollPane3.getVerticalScrollBar().setUnitIncrement(10);
        jScrollPane3.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        loadBtn();

        remoteId.setText(MainJFrame.keyRemoteControl);

    }

    public void loadBtn() {
        String isDKGiongNoi = LocalData.getData("isDKGiongNoi");
        if (isDKGiongNoi == null || isDKGiongNoi.equals("false")) {
            btnDKGiongNoi.setSelected(false);
            btnDKGiongNoi.setLabel("Tắt");
        } else {
            btnDKGiongNoi.setSelected(true);
            btnDKGiongNoi.setLabel("Bật");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnDKGiongNoi = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        remoteId = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnGiaoDien = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnHenGio = new javax.swing.JLabel();

        jScrollPane3.setBorder(null);

        jPanel1.setBackground(new java.awt.Color(23, 15, 35));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Setting");

        jPanel2.setBackground(new java.awt.Color(23, 15, 35));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Điều khiển bằng giọng nói");

        btnDKGiongNoi.setText("Bật");
        btnDKGiongNoi.setFocusPainted(false);
        btnDKGiongNoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDKGiongNoiActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Remote id");

        remoteId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        remoteId.setForeground(new java.awt.Color(255, 255, 255));
        remoteId.setText("id");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Giao diện");

        btnGiaoDien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-right-20.png"))); // NOI18N
        btnGiaoDien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGiaoDien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGiaoDienMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Hẹn giờ");

        btnHenGio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-right-20.png"))); // NOI18N
        btnHenGio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHenGio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHenGioMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDKGiongNoi))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(remoteId))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGiaoDien))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnHenGio)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnDKGiongNoi))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(remoteId))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnGiaoDien))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHenGio)
                    .addComponent(jLabel5))
                .addContainerGap(385, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDKGiongNoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDKGiongNoiActionPerformed
        // TODO add your handling code here:
        String isDKGiongNoi = LocalData.getData("isDKGiongNoi");
        if (isDKGiongNoi == null || isDKGiongNoi.equals("false")) {
            System.out.println("false");
            LocalData.setData("isDKGiongNoi", "true");
            BatDKGiongNoi();
        } else {
            System.out.println("true");
            LocalData.setData("isDKGiongNoi", "false");
            TatDKGiongNoi();
        }
        loadBtn();
    }//GEN-LAST:event_btnDKGiongNoiActionPerformed

    private void btnGiaoDienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGiaoDienMouseClicked
        // TODO add your handling code here:
        MainJFrame.ShowPanel("SettingGiaoDien", new SettingGiaoDien());


    }//GEN-LAST:event_btnGiaoDienMouseClicked

    private void btnHenGioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHenGioMouseClicked
        // TODO add your handling code here:
        MyCustomDialog customDialog = new MyCustomDialog(null, "", new ModelHenGio());
        customDialog.show(true);
    }//GEN-LAST:event_btnHenGioMouseClicked

    public void BatDKGiongNoi() {
        //        MainJFrame.initDKGiongNoi();
//        String url = "http://localhost:3000/public/music/control?key=asdfd";
//        try {
//            Desktop.getDesktop().browse(new URI(url));
//        } catch (IOException | URISyntaxException ex) {
//            ex.printStackTrace();
//        }

    }

    public void TatDKGiongNoi() {
        MainJFrame.deleteDKGiongNoi();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnDKGiongNoi;
    private javax.swing.JLabel btnGiaoDien;
    private javax.swing.JLabel btnHenGio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel remoteId;
    // End of variables declaration//GEN-END:variables
}
