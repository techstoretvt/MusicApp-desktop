/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import component.CustomScrollBarUI;
import component.ItemMusic;
import model.BaiHat;
import model.GetListBaiHat;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static screen.ThuVien.listDaNghe;
import api.ApiServiceV1;
import helpers.LocalData;

/**
 *
 * @author tranv
 */
public class KhamPha extends javax.swing.JPanel {

    public static ArrayList<BaiHat> dsBaiHat;
    public int maxCountDaNghe = 3;

    public KhamPha() {
        initComponents();

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        loadTop10Music();

//        ImageIcon anhKhamPha = utils.getImageBaiHat(getClass().getResource("/icon/anh-kham-pha.png").toString(),
//                600, 300);
//        lbAnhKhamPha.setIcon(anhKhamPha);
        getListDaNghe();

    }

    public void getListDaNghe() {
        listDaNghe = LocalData.getListDaNghe();
        if (listDaNghe != null && !listDaNghe.isEmpty()) {
            pnDaNghe.removeAll();
            int height = 0;
            int size = listDaNghe.size();
            for (int i = 0; i < size && i < maxCountDaNghe; i++) {
                ItemMusic itemBH = new ItemMusic(listDaNghe.get(i), (i + 1), "DaNghe", false);
                pnDaNghe.add(itemBH);
                height += 71;
            }
            pnDaNghe.setPreferredSize(new Dimension(200, height));

            pnDaNghe.revalidate();
            pnDaNghe.repaint();
        }

    }

    public void loadTop10Music() {

        JLabel lbLoading = new JLabel("Đang tải...");
        lbLoading.setForeground(Color.GRAY);
        lbLoading.setMinimumSize(new Dimension(100, 30));
        lbLoading.setMaximumSize(new Dimension(100, 30));
        lbLoading.setHorizontalAlignment(SwingConstants.RIGHT);
        panelListMusic.add(lbLoading);

        ApiServiceV1.apiServiceV1.getTop10MusicKhamPha(15).enqueue(new Callback<GetListBaiHat>() {
            @Override
            public void onResponse(Call<GetListBaiHat> call, Response<GetListBaiHat> rspns) {
                GetListBaiHat res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    panelListMusic.removeAll();
                    dsBaiHat = res.getData();
                    int sizeDs = dsBaiHat.size();
                    for (int i = 0; i < sizeDs; i++) {
                        JPanel pn = new ItemMusic(res.getData().get(i), i + 1, "khampha");
                        panelListMusic.add(pn);
                    }
                    panelListMusic.revalidate();
                    panelListMusic.repaint();
                } else {
                    System.out.println("Loi ds bai hat 1");
                }
            }

            @Override
            public void onFailure(Call<GetListBaiHat> call, Throwable thrwbl) {
                System.out.println("Loi ds bai hat 2");
            }
        });

    }

    public void loadTop100Music() {

        ApiServiceV1.apiServiceV1.getTop10MusicKhamPha(100).enqueue(new Callback<GetListBaiHat>() {
            @Override
            public void onResponse(Call<GetListBaiHat> call, Response<GetListBaiHat> rspns) {
                GetListBaiHat res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    panelListMusic.removeAll();
                    dsBaiHat = res.getData();
                    int sizeDs = dsBaiHat.size();
                    for (int i = 0; i < sizeDs; i++) {
                        JPanel pn = new ItemMusic(res.getData().get(i), i + 1, "khampha");
                        panelListMusic.add(pn);
                    }
                    panelListMusic.revalidate();
                    panelListMusic.repaint();
                } else {
                    System.out.println("Loi ds bai hat 1");
                }
            }

            @Override
            public void onFailure(Call<GetListBaiHat> call, Throwable thrwbl) {
                System.out.println("Loi ds bai hat 2");
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
        panelContainer = new javax.swing.JPanel();
        panelListMusic = new javax.swing.JPanel();
        pnDaNghe = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnTop100 = new javax.swing.JButton();
        btnMore = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(23, 15, 35));
        setMinimumSize(new java.awt.Dimension(100, 100));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(507, 800));
        jScrollPane1.setViewportView(panelContainer);

        panelContainer.setBackground(new java.awt.Color(23, 15, 35));

        panelListMusic.setBackground(new java.awt.Color(23, 15, 35));
        panelListMusic.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 204)), "PHỔ BIẾN NHẤT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(204, 0, 204))); // NOI18N
        panelListMusic.setLayout(new javax.swing.BoxLayout(panelListMusic, javax.swing.BoxLayout.Y_AXIS));

        pnDaNghe.setBackground(new java.awt.Color(23, 15, 35));
        pnDaNghe.setPreferredSize(new java.awt.Dimension(0, 0));
        pnDaNghe.setLayout(new javax.swing.BoxLayout(pnDaNghe, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NGHE GẤN ĐÂY");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        btnTop100.setBackground(new java.awt.Color(204, 0, 204));
        btnTop100.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTop100.setForeground(new java.awt.Color(255, 255, 255));
        btnTop100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-100-20.png"))); // NOI18N
        btnTop100.setText("Xem top 100");
        btnTop100.setFocusPainted(false);
        btnTop100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTop100ActionPerformed(evt);
            }
        });

        btnMore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-down.png"))); // NOI18N
        btnMore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMoreMouseClicked(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout panelContainerLayout = new javax.swing.GroupLayout(panelContainer);
        panelContainer.setLayout(panelContainerLayout);
        panelContainerLayout.setHorizontalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDaNghe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelListMusic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMore)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(btnTop100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelContainerLayout.setVerticalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(btnMore))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnDaNghe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelListMusic, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTop100)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(panelContainer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTop100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTop100ActionPerformed
        // TODO add your handling code here:
        loadTop100Music();
        btnTop100.setVisible(false);

    }//GEN-LAST:event_btnTop100ActionPerformed

    private void btnMoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoreMouseClicked
        // TODO add your handling code here:
        toggleShowMoreDaNghe();
    }//GEN-LAST:event_btnMoreMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        toggleShowMoreDaNghe();
    }//GEN-LAST:event_jLabel3MouseClicked

    public void toggleShowMoreDaNghe() {
        if (maxCountDaNghe == 3) {
            maxCountDaNghe = 10;
            getListDaNghe();
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icons-up.png"));
            btnMore.setIcon(icon);
        } else {
            maxCountDaNghe = 3;
            getListDaNghe();
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icons-down.png"));
            btnMore.setIcon(icon);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnMore;
    private javax.swing.JButton btnTop100;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JPanel panelListMusic;
    private javax.swing.JPanel pnDaNghe;
    // End of variables declaration//GEN-END:variables
}
