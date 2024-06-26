/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import Interface.UpdateListMusic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import component.CustomScrollBarUI;
import component.FilterListMusic;
import component.ItemMusic;
import model.BaiHat;
import java.util.ArrayList;
import javax.swing.JPanel;
import helpers.LocalData;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BaiHat_CaSi;
import model.Casi;
import services.MySql;

/**
 *
 * @author tranv
 */
public class DaTai extends javax.swing.JPanel implements UpdateListMusic {

    public static ArrayList<BaiHat> dsBaiHat;

    @Override
    public void updateListMusic() {
        int size = dsBaiHat.size();

        PanelListMusic.removeAll();
        for (int i = 0; i < size; i++) {
            BaiHat bh = dsBaiHat.get(i);
            JPanel pn = new ItemMusic(bh, i + 1, "DaTai");
            PanelListMusic.add(pn);
        }
        PanelListMusic.revalidate();
        PanelListMusic.repaint();
    }

    public DaTai() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        getListMusic();
//        getListMusicV2();
    }

    public void getListMusic() {
        dsBaiHat = LocalData.getListDownload();
        int size = dsBaiHat.size();

        for (int i = 0; i < size; i++) {
            BaiHat bh = dsBaiHat.get(i);
            JPanel pn = new ItemMusic(bh, i + 1, "DaTai");
            PanelListMusic.add(pn);
        }
        PanelListMusic.revalidate();
        PanelListMusic.repaint();

        pnBoLoc.add(new FilterListMusic(dsBaiHat, this));
        pnBoLoc.revalidate();
        pnBoLoc.repaint();
    }

    public void getListMusicV2() {

        try {
            String sql = String.format("select * from download");
            ResultSet kq = MySql.queryData(sql);
            dsBaiHat = new ArrayList<>();
            while (kq != null && kq.next()) {
                String idBaiHat = kq.getString("idBaiHat");
                System.out.println("idBaiHat: "+idBaiHat);
                String TenBaiHat = kq.getString("TenBaiHat");
                String anhBia = kq.getString("anhBia");
                String linkBaiHat = kq.getString("linkBaiHat");
                String linkMV = kq.getString("linkMV");
                String tenNhacSi = kq.getString("tenNhacSi");
                String theLoai = kq.getString("theLoai");
                String ngayPhatHanh = kq.getString("ngayPhatHanh");
                String nhaCungCap = kq.getString("nhaCungCap");
                double thoiGian = kq.getDouble("thoiGian");
                String listCaSi = kq.getString("listCaSi");

               
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Casi>>() {
                }.getType();
                ArrayList<Casi> listCS = gson.fromJson(listCaSi, type);
                ArrayList<BaiHat_CaSi> baiHat_CaSis = new ArrayList<>();
                for (Casi cs : listCS) {
                    BaiHat_CaSi baiHat_CaSi = new BaiHat_CaSi("", "", "", cs);
                    baiHat_CaSis.add(baiHat_CaSi);
                }

                BaiHat bh = new BaiHat(idBaiHat, TenBaiHat, "", anhBia, linkBaiHat, baiHat_CaSis);

                dsBaiHat.add(bh);
            }

            int size = dsBaiHat.size();

            for (int i = 0; i < size; i++) {
                BaiHat bh = dsBaiHat.get(i);
                JPanel pn = new ItemMusic(bh, i + 1, "DaTai");
                PanelListMusic.add(pn);
            }
            PanelListMusic.revalidate();
            PanelListMusic.repaint();

            pnBoLoc.add(new FilterListMusic(dsBaiHat, this));
            pnBoLoc.revalidate();
            pnBoLoc.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(DaTai.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanelListMusic = new javax.swing.JPanel();
        pnBoLoc = new javax.swing.JPanel();

        jScrollPane1.setBackground(new java.awt.Color(23, 15, 35));
        jScrollPane1.setBorder(null);

        jPanel1.setBackground(new java.awt.Color(23, 15, 35));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bài hát đã tải");

        PanelListMusic.setBackground(new java.awt.Color(23, 15, 35));
        PanelListMusic.setLayout(new javax.swing.BoxLayout(PanelListMusic, javax.swing.BoxLayout.PAGE_AXIS));

        pnBoLoc.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelListMusic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(0, 171, Short.MAX_VALUE))
            .addComponent(pnBoLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnBoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelListMusic, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelListMusic;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnBoLoc;
    // End of variables declaration//GEN-END:variables

}
