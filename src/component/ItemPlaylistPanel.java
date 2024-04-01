/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import gson.ChiTietDanhSachPhat;
import gson.DanhSachPhat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import services.utils;
import frame.MainJFrame;
import screen.ChiTietPlaylistPanel;

/**
 *
 * @author tranv
 */
public class ItemPlaylistPanel extends javax.swing.JPanel {

    private DanhSachPhat playlist;

    /**
     * Creates new form ItemPlaylistPanel
     */
    public ItemPlaylistPanel(DanhSachPhat playlist) {
        initComponents();
        
        this.playlist = playlist;

        lbTenPlaylist.setText(playlist.getTenDanhSach());

        ArrayList<ChiTietDanhSachPhat> chiTietDanhSachPhats = playlist.getChiTietDanhSachPhats();

        if (chiTietDanhSachPhats.size() == 0) {
            ImageIcon icon = utils.getImageBaiHat(getClass().getResource("/icon/playlist-empty.png").toString(),
                    200, 200);
            imgPlaylist.setIcon(icon);
        } else {
            ImageIcon icon = utils.getImageBaiHat(chiTietDanhSachPhats.get(0).getBaihat().getAnhBia(),
                    200, 200);
            imgPlaylist.setIcon(icon);
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

        jToggleButton1 = new javax.swing.JToggleButton();
        imgPlaylist = new javax.swing.JLabel();
        lbTenPlaylist = new javax.swing.JLabel();

        jToggleButton1.setText("jToggleButton1");

        setBackground(new java.awt.Color(23, 15, 35));

        imgPlaylist.setForeground(new java.awt.Color(255, 255, 255));
        imgPlaylist.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgPlaylist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loadingBH.gif"))); // NOI18N
        imgPlaylist.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgPlaylistMouseClicked(evt);
            }
        });

        lbTenPlaylist.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTenPlaylist.setForeground(new java.awt.Color(255, 255, 255));
        lbTenPlaylist.setText("Ten danh sach");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgPlaylist)
                    .addComponent(lbTenPlaylist))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgPlaylist)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTenPlaylist)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void imgPlaylistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgPlaylistMouseClicked
        // TODO add your handling code here:
        System.out.println("id: "+playlist.getId());
        MainJFrame.ShowPanel("ChiTietPlaylist", new ChiTietPlaylistPanel(playlist.getId()));
    }//GEN-LAST:event_imgPlaylistMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imgPlaylist;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lbTenPlaylist;
    // End of variables declaration//GEN-END:variables
}
