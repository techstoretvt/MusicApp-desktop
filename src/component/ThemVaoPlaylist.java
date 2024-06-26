/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import bodyapi.BodyThemBHVaoDS;
import bodyapi.BodyThemDSPhat;
import frame.MainJFrame;
import model.DanhSachPhat;
import model.GetListPlaylist;
import model.ThemBHVaoDS;
import model.ThemDSPhat;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import screen.ChiTietPlaylist;
import api.ApiServiceV1;
import helpers.Utils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranv
 */
public class ThemVaoPlaylist extends javax.swing.JPanel {

    private String idBaiHat = "";

    /**
     * Creates new form ThemVaoPlaylist
     */
    public ThemVaoPlaylist(String idBaiHat) {
        initComponents();
        this.idBaiHat = idBaiHat;
        loadListPlaylist();
    }

    public void loadListPlaylist() {
        String header = Utils.getHeader();
        PanelDSPlaylist.removeAll();

        ApiServiceV1.apiServiceV1.getDanhSachPhat(header).enqueue(new Callback<GetListPlaylist>() {
            @Override
            public void onResponse(Call<GetListPlaylist> call, Response<GetListPlaylist> rspns) {
                GetListPlaylist res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    ArrayList<DanhSachPhat> listPlaylist = res.getData();
                    int size = listPlaylist.size();

                    for (int i = 0; i < size; i++) {

                        DanhSachPhat playlist = listPlaylist.get(i);

                        JButton btn = new JButton();
                        btn.setText(playlist.getTenDanhSach());
                        btn.setBackground(new Color(0, 0, 51));
                        btn.setForeground(Color.WHITE);
                        btn.setFocusPainted(false);
                        btn.setBorder(null);

                        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-playlist.png"));
                        btn.setIcon(icon);

                        btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                handleThemBHVaoPlaylist(idBaiHat, playlist.getId());
                            }

                        });

                        PanelDSPlaylist.add(btn);
                    }

                    PanelDSPlaylist.revalidate();
                    PanelDSPlaylist.repaint();

                } else {
                    System.out.println("ko tim thay danh sach phat");
                }

            }

            @Override
            public void onFailure(Call<GetListPlaylist> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public void handleThemBHVaoPlaylist(String idBH, String idDS) {
        String header = Utils.getHeader();
        BodyThemBHVaoDS body = new BodyThemBHVaoDS(idBH, idDS);

        ApiServiceV1.apiServiceV1.themBaiHatVaoDS(body, header).enqueue(new Callback<ThemBHVaoDS>() {
            @Override
            public void onResponse(Call<ThemBHVaoDS> call, Response<ThemBHVaoDS> rspns) {
                ThemBHVaoDS res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    lblMessage.setText("Thêm thành công");
                    
                    if(MainJFrame.historyPanel.peek().equals("ChiTietPlaylist")) {
                        if (ChiTietPlaylist.idPlaylist.equals(idDS)) {
                            MainJFrame.resetPanel();
                        }
                    }
                    
                } else {
                    lblMessage.setText(res.getErrMessage());
                }
                
                new Thread(()-> {
                    try {
                        Thread.sleep(2000);
                         lblMessage.setText("");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ThemVaoPlaylist.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<ThemBHVaoDS> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAddPlaylist = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PanelDSPlaylist = new javax.swing.JPanel();
        lblMessage = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));

        btnAddPlaylist.setBackground(new java.awt.Color(153, 0, 153));
        btnAddPlaylist.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddPlaylist.setForeground(new java.awt.Color(255, 255, 255));
        btnAddPlaylist.setText("+");
        btnAddPlaylist.setFocusPainted(false);
        btnAddPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPlaylistActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tạo mới playlist");

        jScrollPane1.setBorder(null);

        PanelDSPlaylist.setBackground(new java.awt.Color(0, 0, 51));
        PanelDSPlaylist.setLayout(new javax.swing.BoxLayout(PanelDSPlaylist, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(PanelDSPlaylist);

        lblMessage.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(255, 255, 0));
        lblMessage.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addComponent(btnAddPlaylist)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(lblMessage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPlaylist)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPlaylistActionPerformed
        // TODO add your handling code here:
        String namePlaylist = JOptionPane.showInputDialog(null, "Nhập tên danh sách:", "Thêm danh sách phát",
                JOptionPane.INFORMATION_MESSAGE);
        if (namePlaylist != null && !namePlaylist.isEmpty()) {
            String header = Utils.getHeader();

            BodyThemDSPhat body = new BodyThemDSPhat(namePlaylist);
            ApiServiceV1.apiServiceV1.themDanhSachPhat(body, header).enqueue(new Callback<ThemDSPhat>() {
                @Override
                public void onResponse(Call<ThemDSPhat> call, Response<ThemDSPhat> rspns) {
                    ThemDSPhat res = rspns.body();
                    if (res != null && res.getErrCode() == 0) {
                        loadListPlaylist();
                    } else {
                        System.out.println("Warning: " + res.getErrMessage());
                        JOptionPane.showMessageDialog(null, res.getErrMessage(),
                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }
                }

                @Override
                public void onFailure(Call<ThemDSPhat> call, Throwable thrwbl) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            });

        } else {
            System.out.println("Người dùng đã hủy bỏ hộp thoại.");
        }
    }//GEN-LAST:event_btnAddPlaylistActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDSPlaylist;
    private javax.swing.JButton btnAddPlaylist;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMessage;
    // End of variables declaration//GEN-END:variables
}
