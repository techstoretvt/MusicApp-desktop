/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import model.BaiHat;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import services.MyMusicPlayer;

/**
 *
 * @author tranv
 */
public class PhatKeTiepPanel extends javax.swing.JPanel {

    /**
     * Creates new form PhatKeTiepPanel
     */
    public static boolean isUpdate = true;
    private ArrayList<ItemPhatKeTiep> listItemBH;

    public PhatKeTiepPanel(boolean showCurent) {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        loadData();
        setBorder(null);
        PanelCurrent.setVisible(false);
    }

    public PhatKeTiepPanel() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        loadData();
    }

    public void loadData() {
        if (MyMusicPlayer.dsBaiHat != null) {

            new Thread(() -> {
                try {
                    PanelListMusic.removeAll();
                    ArrayList<BaiHat> dsBaiHat = MyMusicPlayer.dsBaiHat;
                    int sizeDS = dsBaiHat.size();
                    BaiHat currentBH = dsBaiHat.get(MyMusicPlayer.position);

                    // load current bai hat
                    JPanel currentPhatKeTiep = new ItemPhatKeTiep(currentBH, MyMusicPlayer.position, true, false);
                    PanelCurrent.removeAll();
                    PanelCurrent.add(currentPhatKeTiep, BorderLayout.CENTER);
                    PanelCurrent.revalidate();
                    PanelCurrent.repaint();

                    listItemBH = new ArrayList<>();
                    // load list bai hat
                    for (int i = 0; i < sizeDS; i++) {
                        boolean active = i == MyMusicPlayer.position;
                        ItemPhatKeTiep bh = new ItemPhatKeTiep(dsBaiHat.get(i), i, active, true);
                        bh.initBtnUpDownClick(this);

                        if (i == MyMusicPlayer.position) {
                            currentPhatKeTiep = bh;
                            PanelListMusic.add(currentPhatKeTiep);
                            listItemBH.add((ItemPhatKeTiep) currentPhatKeTiep);
                            continue;
                        }

                        listItemBH.add((ItemPhatKeTiep) bh);

                        PanelListMusic.add(bh);
                    }

                    Thread.sleep(500);

                    Rectangle rect = new Rectangle(0, 220, 10, 10);
                    currentPhatKeTiep.scrollRectToVisible(rect);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PhatKeTiepPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();

        }
    }

    public void updateIndex() {
        int size = listItemBH.size();
        for (int i = 0; i < size; i++) {
            ItemPhatKeTiep item = listItemBH.get(i);
            item.setActive(i == MyMusicPlayer.position);
        }
        // load current bai hat
        ArrayList<BaiHat> dsBaiHat = MyMusicPlayer.dsBaiHat;
        BaiHat currentBH = dsBaiHat.get(MyMusicPlayer.position);
        JPanel currentPhatKeTiep = new ItemPhatKeTiep(currentBH, MyMusicPlayer.position, true, false);
        PanelCurrent.removeAll();
        PanelCurrent.add(currentPhatKeTiep, BorderLayout.CENTER);
        PanelCurrent.revalidate();
        PanelCurrent.repaint();
    }

    public void swapItem(int indexOld, int indexNew) {

        ItemPhatKeTiep item1 = listItemBH.get(indexOld);
        ItemPhatKeTiep item2 = listItemBH.get(indexNew);

        listItemBH.set(indexOld, item2);
        listItemBH.set(indexNew, item1);

        item1.setIndex(indexNew);
        item2.setIndex(indexOld);

        PanelListMusic.setComponentZOrder(item1, indexNew);
        PanelListMusic.setComponentZOrder(item2, indexOld);

        PanelListMusic.revalidate();
        PanelListMusic.repaint();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelCurrent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PanelListMusic = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bài hát đang phát", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        setMinimumSize(new java.awt.Dimension(10, 200));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));

        PanelCurrent.setBackground(new java.awt.Color(0, 0, 102));
        PanelCurrent.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        PanelListMusic.setBackground(new java.awt.Color(0, 0, 102));
        PanelListMusic.setLayout(new javax.swing.BoxLayout(PanelListMusic, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(PanelListMusic);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tiếp theo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelCurrent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(PanelCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCurrent;
    private javax.swing.JPanel PanelListMusic;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
