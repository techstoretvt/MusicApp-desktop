/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author tranv
 */
public class PopupMessageLock extends javax.swing.JPanel {

    /**
     * Creates new form PopupMessage
     */
    public PopupMessageLock() {
        initComponents();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/icon/img-hack.jpg"));
        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new java.awt.Label();
        label2 = new java.awt.Label();

        setMaximumSize(new java.awt.Dimension(400, 300));
        setMinimumSize(new java.awt.Dimension(400, 300));

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setBackground(new java.awt.Color(255, 255, 0));
        label1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label1.setText("Tính năng này đã tạm khóa do vấn đề bản quyền");

        label2.setAlignment(java.awt.Label.CENTER);
        label2.setBackground(new java.awt.Color(0, 255, 204));
        label2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label2.setText("Hệ thống sẽ sớm hoạt động trờ lại");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Label label1;
    private java.awt.Label label2;
    // End of variables declaration//GEN-END:variables
}