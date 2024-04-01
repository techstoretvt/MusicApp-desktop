/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import component.ItemPlaylistPanel;
import component.ItemNgheSiPanel;
import bodyapi.BodyThemDSPhat;
import component.CustomScrollBarUI;
import component.ItemMusicPanel;
import gson.BaiHat;
import gson.DanhSachPhat;
import gson.GetListCSQuanTam;
import gson.GetListPlaylist;
import gson.QuanTamCS;
import gson.ThemDSPhat;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.ApiServiceV1;
import services.LocalData;
import services.utils;

/**
 *
 * @author tranv
 */
public class ThuVienPanel extends javax.swing.JPanel {

    public static ArrayList<BaiHat> listDaNghe;
    /**
     * Creates new form ThuVienPanel
     */
    public ThuVienPanel() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());
//        jScrollPane2.getVerticalScrollBar().setUnitIncrement(10);
        getListCaSi();
        getPlaylist();

        getListDaNghe();
        //test
    }

    public void getListDaNghe() {
        listDaNghe = LocalData.getListDaNghe();
        if (listDaNghe != null && listDaNghe.size() != 0) {
            int size = listDaNghe.size();
            for (int i = 0; i < size; i++) {
                ItemMusicPanel itemBH = new ItemMusicPanel(listDaNghe.get(i), (i + 1), "DaNghe");
                pnNgheGanDay.add(itemBH);
            }
            pnNgheGanDay.revalidate();
            pnNgheGanDay.repaint();
        }

    }

    public void getListCaSi() {
        String header = utils.getHeader();

        ApiServiceV1.apiServiceV1.getListCaSiQuanTam(header).enqueue(new Callback<GetListCSQuanTam>() {
            @Override
            public void onResponse(Call<GetListCSQuanTam> call, Response<GetListCSQuanTam> rspns) {
                GetListCSQuanTam res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    PanelListCS.removeAll();
                    ArrayList<QuanTamCS> data = res.getData();
                    int size = data.size();
                    for (int i = 0; i < size; i++) {
                        if (i > 2) {
                            break;
                        }
                        QuanTamCS cs = data.get(i);
                        JPanel pn = new ItemNgheSiPanel(cs.getCasi());
                        PanelListCS.add(pn);
                    }

                    if (size > 2 || true) {
                        JButton btn = new JButton();
                        btn.setText("Xem thêm");
                        btn.setPreferredSize(new Dimension(100, 40));
                        btn.setFocusPainted(false);
                        btn.setBackground(new Color(23, 15, 35));
                        btn.setForeground(Color.WHITE);
                        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                        PanelListCS.add(btn);
                    }

                    PanelListCS.revalidate();
                    PanelListCS.repaint();

                } else {
                    System.out.println("ko tim thay ca si");
                }
            }

            @Override
            public void onFailure(Call<GetListCSQuanTam> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public void getPlaylist() {
        String header = utils.getHeader();

        ApiServiceV1.apiServiceV1.getDanhSachPhat(header).enqueue(new Callback<GetListPlaylist>() {
            @Override
            public void onResponse(Call<GetListPlaylist> call, Response<GetListPlaylist> rspns) {
                GetListPlaylist res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    PanelListPlaylist.removeAll();
                    ArrayList<DanhSachPhat> listPlaylist = res.getData();
                    int size = listPlaylist.size();

                    for (int i = 0; i < size; i++) {
                        if (i > 2) {
                            break;
                        }
                        DanhSachPhat playlist = listPlaylist.get(i);
                        JPanel pn = new ItemPlaylistPanel(playlist);
                        PanelListPlaylist.add(pn);
                    }
                    if (size > 2 || true) {
                        JButton btn = new JButton();
                        btn.setText("Xem thêm");
                        btn.setPreferredSize(new Dimension(100, 40));
                        btn.setFocusPainted(false);
                        btn.setBackground(new Color(23, 15, 35));
                        btn.setForeground(Color.WHITE);
                        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                        PanelListPlaylist.add(btn);
                    }
                    PanelListPlaylist.revalidate();
                    PanelListPlaylist.repaint();

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PanelListCS = new javax.swing.JPanel();
        lblLoadingCS = new javax.swing.JLabel();
        PanelListPlaylist = new javax.swing.JPanel();
        lblLoadingCS1 = new javax.swing.JLabel();
        btnAddPlaylist = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        pnNgheGanDay = new javax.swing.JPanel();

        setBackground(new java.awt.Color(23, 15, 35));
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        jPanel2.setBackground(new java.awt.Color(23, 15, 35));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Thư viện");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PLAYLIST");

        PanelListCS.setBackground(new java.awt.Color(23, 15, 35));
        PanelListCS.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblLoadingCS.setForeground(new java.awt.Color(255, 255, 255));
        lblLoadingCS.setText("Đang tải...");
        PanelListCS.add(lblLoadingCS);

        PanelListPlaylist.setBackground(new java.awt.Color(23, 15, 35));
        PanelListPlaylist.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblLoadingCS1.setForeground(new java.awt.Color(255, 255, 255));
        lblLoadingCS1.setText("Đang tải...");
        PanelListPlaylist.add(lblLoadingCS1);

        btnAddPlaylist.setBackground(new java.awt.Color(23, 15, 35));
        btnAddPlaylist.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAddPlaylist.setForeground(new java.awt.Color(255, 255, 255));
        btnAddPlaylist.setText("+");
        btnAddPlaylist.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddPlaylist.setFocusPainted(false);
        btnAddPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPlaylistActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nghe gần đây");

        pnNgheGanDay.setBackground(null);
        pnNgheGanDay.setLayout(new javax.swing.BoxLayout(pnNgheGanDay, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelListCS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelListPlaylist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(pnNgheGanDay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(btnAddPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(0, 297, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelListCS, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddPlaylist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelListPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnNgheGanDay, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel2);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPlaylistActionPerformed
        // TODO add your handling code here:
        String namePlaylist = JOptionPane.showInputDialog(null, "Nhập tên danh sách:", "Thêm danh sách phát",
                JOptionPane.INFORMATION_MESSAGE);
        if (namePlaylist != null && !namePlaylist.isEmpty()) {
            System.out.println("Dữ liệu nhập vào: " + namePlaylist);
            String header = utils.getHeader();

            BodyThemDSPhat body = new BodyThemDSPhat(namePlaylist);
            ApiServiceV1.apiServiceV1.themDanhSachPhat(body, header).enqueue(new Callback<ThemDSPhat>() {
                @Override
                public void onResponse(Call<ThemDSPhat> call, Response<ThemDSPhat> rspns) {
                    ThemDSPhat res = rspns.body();
                    if (res != null && res.getErrCode() == 0) {
                        getPlaylist();
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
    private javax.swing.JPanel PanelListCS;
    private javax.swing.JPanel PanelListPlaylist;
    private javax.swing.JButton btnAddPlaylist;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLoadingCS;
    private javax.swing.JLabel lblLoadingCS1;
    private javax.swing.JPanel pnNgheGanDay;
    // End of variables declaration//GEN-END:variables
}
