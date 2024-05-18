/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import model.GetLoiBaiHat;
import model.LoiBaiHat;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import api.ApiServiceV1;
import services.MyMusicPlayer;
import helpers.Utils;
import java.awt.Cursor;

/**
 *
 * @author tranv
 */
public class KaraokePanel extends javax.swing.JPanel {

    public static ArrayList<JButton> dsItemLoiBH;
    public static HashMap<String, Integer> listIndexLoiBaiHat;
    public static int currentIndexLoiBH = 0;

    /**
     * Creates new form KaraokePanel
     */
    public KaraokePanel() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        jScrollPane1.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

        getLoiBaiHat();

    }

    public void getLoiBaiHat() {

        if (MyMusicPlayer.dsBaiHat == null) {
            return;
        }

        PanelListLoiBH.removeAll();
        dsItemLoiBH = new ArrayList<>();
        listIndexLoiBaiHat = null;
        currentIndexLoiBH = 0;

        String idBaiHat = MyMusicPlayer.dsBaiHat.get(MyMusicPlayer.position).getId();

        ApiServiceV1.apiServiceV1.getLoiBaiHatById(idBaiHat).enqueue(new Callback<GetLoiBaiHat>() {
            @Override
            public void onResponse(Call<GetLoiBaiHat> call, Response<GetLoiBaiHat> rspns) {
                GetLoiBaiHat res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    listIndexLoiBaiHat = Utils.createListIndexLoiBaiHat(res.getData());
                    int sizeLoiBH = res.getData().size();
                    if (sizeLoiBH != 0) {
                        GridLayout la = (GridLayout) PanelListLoiBH.getLayout();
                        la.setRows(sizeLoiBH + 1);
                        PanelListLoiBH.setLayout(la);

                        // add ui
//                        JButton loiBH1 = new JButton();
//                        loiBH1.setText("hello");
//                        loiBH1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
//                        loiBH1.setForeground(Color.WHITE);
//                        loiBH1.setBackground(new Color(0, 0, 0));
//                        loiBH1.setFocusPainted(false);
//                        loiBH1.setBorder(null);
//                        PanelListLoiBH.add(loiBH1);

                        for (int i = 0; i < sizeLoiBH; i++) {
                            LoiBaiHat currentLoiBH = res.getData().get(i);
                            JButton loiBH = new JButton();
                            loiBH.setText(currentLoiBH.getLoiBaiHat());
                            loiBH.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                            loiBH.setForeground(Color.WHITE);
                            loiBH.setBackground(new Color(0, 0, 0));
                            loiBH.setFocusPainted(false);
                            loiBH.setBorder(null);
                            loiBH.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            

                            int index = i;

                            loiBH.addMouseListener(new java.awt.event.MouseAdapter() {
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    //xu ly khi click
                                    loiBH.setForeground(Color.YELLOW);
                                    Rectangle rect = new Rectangle(0, 200, 10, 10);
                                    loiBH.scrollRectToVisible(rect);

                                    KaraokePanel.dsItemLoiBH.get(KaraokePanel.currentIndexLoiBH).setForeground(Color.WHITE);
                                    double tg = currentLoiBH.getThoiGian() + 1.0;
//                                    if (index == 0) {
//                                        tg -= 1.0;
//                                    }
                                    MyMusicPlayer.setTimeBaiHat2(tg);
                                    System.out.println("thoigian: " + currentLoiBH.getThoiGian());
                                    KaraokePanel.currentIndexLoiBH = index;

                                }
                            });

                            PanelListLoiBH.add(loiBH);

                            dsItemLoiBH.add(loiBH);
                        }

                        PanelListLoiBH.revalidate();
                        PanelListLoiBH.repaint();

                    } else {
                        GridLayout la = (GridLayout) PanelListLoiBH.getLayout();
                        la.setRows(5);
                        PanelListLoiBH.setLayout(la);
                        
                        JButton loiBH = new JButton();
                        loiBH.setText("Chưa cập nhật cho bài hát này!");
                        loiBH.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                        loiBH.setForeground(Color.WHITE);
                        loiBH.setBackground(new Color(0, 0, 0));
                        loiBH.setFocusPainted(false);
                        loiBH.setBorder(null);

                        PanelListLoiBH.add(loiBH);
                        PanelListLoiBH.revalidate();
                        PanelListLoiBH.repaint();

                        dsItemLoiBH = null;
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetLoiBaiHat> call, Throwable thrwbl) {
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
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        PanelListLoiBH = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setBorder(null);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PanelListLoiBH.setBackground(new java.awt.Color(0, 0, 0));
        PanelListLoiBH.setLayout(new java.awt.GridLayout(1, 1, 0, 20));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelListLoiBH, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelListLoiBH, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelListLoiBH;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
