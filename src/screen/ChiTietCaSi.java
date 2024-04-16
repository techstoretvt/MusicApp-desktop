/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import component.ItemMusic;
import component.CircleImage;
import component.CustomScrollBarUI;
import model.BaiHat;
import model.Casi;
import model.GetCaSiByID;
import model.GetListBaiHat;
import model.ResponseDefault;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import api.ApiServiceV1;
import services.MyMusicPlayer;
import helpers.Utils;

/**
 *
 * @author tranv
 */
public class ChiTietCaSi extends javax.swing.JPanel {

    public static ArrayList<BaiHat> dsBaiHat;
    public static String idCaSi = "";

    /**
     * Creates new form ChiTietCaSiPanel
     * @param idCaSi
     */
    public ChiTietCaSi(String idCaSi) {
        initComponents();

        ChiTietCaSi.idCaSi = idCaSi;

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        jScrollPane1.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

        handleGetCaSi(idCaSi);
        handleLayBaiHatCaSi(idCaSi);
        checkQuamTamCS();
    }

    public final void checkQuamTamCS() {
        String header = Utils.getHeader();

        ApiServiceV1.apiServiceV1.kiemTraQuanTamCaSi(idCaSi, header).enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> rspns) {
                ResponseDefault res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    System.out.println(res.getErrMessage());
                    if (res.getErrMessage().equals("yes")) {
                        btnQuanTam.setText("Bỏ quan tâm");
                    } else {
                        btnQuanTam.setText("Quan tâm");
                    }
                } else {
                    System.out.println("Loi kiem tra quan tam ca si");
                }
            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public final void handleGetCaSi(String id) {

        ApiServiceV1.apiServiceV1.layCaSiById(id).enqueue(new Callback<GetCaSiByID>() {
            @Override
            public void onResponse(Call<GetCaSiByID> call, Response<GetCaSiByID> rspns) {
                GetCaSiByID res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    Casi cs = res.getData();
                    JPanel pnAnh = new CircleImage(cs.getAnh(), 150, 150);
                    PanelAnhCS.setLayout(new BorderLayout());
                    PanelAnhCS.add(pnAnh);

                    lbTenCaSi.setText(cs.getTenCaSi());
                    lbQuanTam.setText(String.valueOf(res.getCountQuanTam()));
                    
                    tfMoTa.setText(cs.getMoTa());

                } else {
                    System.out.println("khong tim thay ca si");
                }

            }

            @Override
            public void onFailure(Call<GetCaSiByID> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public final void handleLayBaiHatCaSi(String id) {

        ApiServiceV1.apiServiceV1.layBaiHatCuaCaSi(id).enqueue(new Callback<GetListBaiHat>() {
            @Override
            public void onResponse(Call<GetListBaiHat> call, Response<GetListBaiHat> rspns) {
                GetListBaiHat res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    dsBaiHat = res.getData();
                    int size = dsBaiHat.size();
                    for (int i = 0; i < size; i++) {
                        BaiHat currentBH = dsBaiHat.get(i);
                        JPanel bh = new ItemMusic(currentBH, i + 1, "caSi");
                        PanelBaiHatCaSi.add(bh);
                    }
                    PanelBaiHatCaSi.revalidate();
                    PanelBaiHatCaSi.repaint();

                } else {
                    System.out.println("ko tim thay ds bai hat cua ca si");
                }
            }

            @Override
            public void onFailure(Call<GetListBaiHat> call, Throwable thrwbl) {
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
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        PanelAnhCS = new javax.swing.JPanel();
        lbTenCaSi = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        lbQuanTam = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnQuanTam = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tfMoTa = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        PanelBaiHatCaSi = new javax.swing.JPanel();

        jScrollPane1.setBorder(null);

        jPanel6.setBackground(new java.awt.Color(23, 15, 35));

        jPanel7.setBackground(new java.awt.Color(23, 15, 35));

        javax.swing.GroupLayout PanelAnhCSLayout = new javax.swing.GroupLayout(PanelAnhCS);
        PanelAnhCS.setLayout(PanelAnhCSLayout);
        PanelAnhCSLayout.setHorizontalGroup(
            PanelAnhCSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        PanelAnhCSLayout.setVerticalGroup(
            PanelAnhCSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        lbTenCaSi.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        lbTenCaSi.setForeground(new java.awt.Color(255, 255, 255));
        lbTenCaSi.setText("Đang tải...");

        btnPlay.setBackground(new java.awt.Color(23, 15, 35));
        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-play-big.png"))); // NOI18N
        btnPlay.setBorder(null);
        btnPlay.setFocusPainted(false);
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        lbQuanTam.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("người quan tâm");

        btnQuanTam.setBackground(new java.awt.Color(23, 15, 35));
        btnQuanTam.setForeground(new java.awt.Color(255, 255, 255));
        btnQuanTam.setText("...");
        btnQuanTam.setFocusPainted(false);
        btnQuanTam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanTamActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(51, 51, 51));
        jSeparator1.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelAnhCS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lbTenCaSi)
                        .addGap(18, 18, 18)
                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lbQuanTam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnQuanTam)
                            .addComponent(jLabel1))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTenCaSi)
                            .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbQuanTam)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuanTam))
                    .addComponent(PanelAnhCS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(23, 15, 35));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(23, 15, 35));
        jPanel2.setMinimumSize(new java.awt.Dimension(50, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Thông tin");

        jScrollPane2.setBorder(null);

        tfMoTa.setBackground(new java.awt.Color(23, 15, 35));
        tfMoTa.setColumns(20);
        tfMoTa.setForeground(new java.awt.Color(255, 255, 255));
        tfMoTa.setLineWrap(true);
        tfMoTa.setRows(5);
        tfMoTa.setText("Loading...");
        tfMoTa.setWrapStyleWord(true);
        jScrollPane2.setViewportView(tfMoTa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(23, 15, 35));
        jPanel3.setMinimumSize(new java.awt.Dimension(50, 50));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Bài hát nổi bật");

        PanelBaiHatCaSi.setBackground(new java.awt.Color(23, 15, 35));
        PanelBaiHatCaSi.setLayout(new javax.swing.BoxLayout(PanelBaiHatCaSi, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelBaiHatCaSi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelBaiHatCaSi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuanTamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanTamActionPerformed
        // TODO add your handling code here:
        if (!Utils.getIsLogin()) {
            return;
        }
        toggleQuanTamCaSi();

    }//GEN-LAST:event_btnQuanTamActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        // TODO add your handling code here:
        MyMusicPlayer.initMusicPlayer(dsBaiHat, 0);
    }//GEN-LAST:event_btnPlayActionPerformed

    public void toggleQuanTamCaSi() {
        String header = Utils.getHeader();

        ApiServiceV1.apiServiceV1.toggleQuanTamCasi(idCaSi, header).enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> rspns) {
                ResponseDefault res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    if (res.getErrMessage().equals("yes")) {
                        btnQuanTam.setText("Bỏ quan tâm");
                    } else {
                        btnQuanTam.setText("Quan tâm");
                    }
                    handleGetCaSi(idCaSi);
                } else {
                    System.out.println("Loi toggle quan tam ca si");
                }
            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAnhCS;
    private javax.swing.JPanel PanelBaiHatCaSi;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnQuanTam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbQuanTam;
    private javax.swing.JLabel lbTenCaSi;
    private javax.swing.JTextArea tfMoTa;
    // End of variables declaration//GEN-END:variables
}
