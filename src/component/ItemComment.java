/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import model.NewComentLive;
import javax.swing.ImageIcon;
import helpers.Utils;

/**
 *
 * @author tranv
 */
public class ItemComment extends javax.swing.JPanel {

    /**
     * Creates new form ItemCommentPanel
     */
    public ItemComment(NewComentLive cmt) {
        initComponents();
        
        ImageIcon icon = Utils.getImageBaiHat(cmt.getAvatar(), 35, 35);
        imgUser.setIcon(icon);
        
        nameUser.setText(cmt.getNameUser());
        content.setText(cmt.getNoiDung());
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imgUser = new javax.swing.JLabel();
        nameUser = new javax.swing.JLabel();
        content = new javax.swing.JLabel();

        setBackground(new java.awt.Color(23, 15, 35));
        setMaximumSize(new java.awt.Dimension(32767, 48));

        nameUser.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        nameUser.setForeground(new java.awt.Color(204, 204, 204));
        nameUser.setText("Name user");

        content.setForeground(new java.awt.Color(255, 255, 255));
        content.setText("Noi dung comment");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameUser)
                    .addComponent(content))
                .addContainerGap(243, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(content))
                    .addComponent(imgUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel content;
    private javax.swing.JLabel imgUser;
    private javax.swing.JLabel nameUser;
    // End of variables declaration//GEN-END:variables
}
