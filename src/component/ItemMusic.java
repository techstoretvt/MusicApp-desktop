/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import model.BaiHat;
import model.BaiHat_CaSi;
import model.Casi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import helpers.LocalData;
import services.MyMusicPlayer;
import helpers.Utils;
import frame.MainJFrame;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import screen.BaiHatSearch;
import screen.ChiTietCaSi;
import screen.ChiTietPlaylist;
import screen.DaTai;
import screen.KhamPha;
import screen.ThuVien;
import screen.YeuThich;

/**
 *
 * @author tranv
 */
public class ItemMusic extends javax.swing.JPanel {

    private BaiHat bh = null;
    private String from_to = ""; //khampha,...
    private int stt = 1;
    private boolean isShowStt;
    private boolean isYeuThich = false;
    private boolean isCheckYeuThich = false;
    public static JLabel anhNhac;

    public ItemMusic(BaiHat bh, int stt, String from_to) {
        initComponents();
        this.bh = bh;
        this.from_to = from_to;
        this.stt = stt;
        this.isShowStt = true;

        setUI();
    }

    public ItemMusic(BaiHat bh, int stt, String from_to, boolean isShowStt) {
        initComponents();

        this.bh = bh;
        this.from_to = from_to;
        this.stt = stt;
        this.isShowStt = isShowStt;

        setUI();

    }

    public void setUI() {
        lbStt.setText(String.valueOf(stt));
        lbTenBaiHat.setText(bh.getTenBaiHat());
        lbTenCaSi.setText(getStringTenCaSi());
        lbThoiGian.setText(Utils.getThoiGianBaiHat((int) bh.getThoiGian() / 1000));
        lblTheLoai.setText(bh.getTheLoai());

//        System.out.println("id: "+bh.getId());
        if (from_to.equals("DaTai")) {
            new Thread(() -> {
                String urlAnh2 = "";
                while (true) {
                    urlAnh2 = Utils.getAnhBHDownload(bh.getId());
                    if (urlAnh2 != null) {
                        break;
                    }
                }
                ImageIcon icon = Utils.getImageBaiHat(urlAnh2, 40, 40);
                imageBaiHat.setIcon(icon);
            }).start();

        } else {
            new Thread(() -> {
                String urlAnh = bh.getAnhBia();
                ImageIcon icon = Utils.getImageBaiHat(urlAnh, 40, 40);
                imageBaiHat.setIcon(icon);
            }).start();

        }

        lbStt.setVisible(isShowStt);
    }

    public String getStringTenCaSi() {
        if (bh == null) {
            return "";
        }

        String tencs = "";
        ArrayList<BaiHat_CaSi> listcs = bh.getBaiHat_caSis();
        for (int i = 0; i < listcs.size(); i++) {
            Casi cs = listcs.get(i).getCasi();
            if (cs != null) {
                tencs += (i == 0 ? "" : ", ") + cs.getTenCaSi();
            }
        }

        return tencs;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbStt = new javax.swing.JLabel();
        imageBaiHat = new javax.swing.JLabel();
        lbTenBaiHat = new javax.swing.JLabel();
        lbTenCaSi = new javax.swing.JLabel();
        lbThoiGian = new javax.swing.JLabel();
        btnOption = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblTheLoai = new javax.swing.JLabel();

        setBackground(new java.awt.Color(23, 15, 35));
        setMaximumSize(new java.awt.Dimension(32767, 70));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        lbStt.setFont(new java.awt.Font("Snap ITC", 1, 18)); // NOI18N
        lbStt.setForeground(new java.awt.Color(0, 102, 255));
        lbStt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbStt.setText("10");
        lbStt.setMaximumSize(new java.awt.Dimension(40, 32));

        imageBaiHat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loadingBH.gif"))); // NOI18N
        imageBaiHat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imageBaiHat.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                imageBaiHatMouseMoved(evt);
            }
        });
        imageBaiHat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageBaiHatMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                imageBaiHatMouseExited(evt);
            }
        });

        lbTenBaiHat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTenBaiHat.setForeground(new java.awt.Color(255, 255, 255));
        lbTenBaiHat.setText("Ten bai hat");
        lbTenBaiHat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbTenBaiHat.setMaximumSize(new java.awt.Dimension(600, 16));
        lbTenBaiHat.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbTenBaiHatMouseMoved(evt);
            }
        });
        lbTenBaiHat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTenBaiHatMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbTenBaiHatMouseExited(evt);
            }
        });

        lbTenCaSi.setForeground(new java.awt.Color(153, 153, 153));
        lbTenCaSi.setText("Ten ca si, tac gia");
        lbTenCaSi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbTenCaSi.setMaximumSize(new java.awt.Dimension(600, 16));
        lbTenCaSi.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbTenCaSiMouseMoved(evt);
            }
        });
        lbTenCaSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTenCaSiMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbTenCaSiMouseExited(evt);
            }
        });

        lbThoiGian.setForeground(new java.awt.Color(255, 255, 255));
        lbThoiGian.setText("5:33");

        btnOption.setBackground(null);
        btnOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-more-20.png"))); // NOI18N
        btnOption.setBorder(null);
        btnOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOption.setFocusable(false);
        btnOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(51, 51, 51));
        jSeparator1.setForeground(new java.awt.Color(51, 51, 51));

        lblTheLoai.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblTheLoai.setForeground(new java.awt.Color(102, 102, 102));
        lblTheLoai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTheLoai.setText("the loai");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbStt, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbTenBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTheLoai, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbTenCaSi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbThoiGian)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTenBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTheLoai))
                        .addGap(8, 8, 8)
                        .addComponent(lbTenCaSi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbStt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnOption, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(lbThoiGian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void imageBaiHatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageBaiHatMouseClicked
        // TODO add your handling code here:

        handlePlayBaiHat();

    }//GEN-LAST:event_imageBaiHatMouseClicked

    private void lbTenBaiHatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenBaiHatMouseClicked
        // TODO add your handling code here:
        handlePlayBaiHat();
    }//GEN-LAST:event_lbTenBaiHatMouseClicked

    private void lbTenCaSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenCaSiMouseClicked
        // TODO add your handling code here:
        ArrayList<BaiHat_CaSi> listcs = bh.getBaiHat_caSis();
        int size = listcs.size();

        if (size == 1) {
            if (MainJFrame.historyPanel.peek().equals("ChiTietCaSi")) {
                ChiTietCaSi.idCaSi = listcs.get(0).getCasi().getId();
                MainJFrame.resetPanel();

            } else {
                MainJFrame.ShowPanel("ChiTietCaSi", new ChiTietCaSi(listcs.get(0).getCasi().getId()));
            }

        } else {
            JPopupMenu popupMenu = new JPopupMenu();

            for (int i = 0; i < size; i++) {
                Casi cs = listcs.get(i).getCasi();
                JMenuItem option = new JMenuItem(cs.getTenCaSi());
                option.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (MainJFrame.historyPanel.peek().equals("ChiTietCaSi")) {
                            ChiTietCaSi.idCaSi = cs.getId();
                            MainJFrame.resetPanel();

                        } else {
                            MainJFrame.ShowPanel("ChiTietCaSi", new ChiTietCaSi(cs.getId()));
                        }

                    }
                });

                popupMenu.add(option);
            }

            popupMenu.show(lbTenCaSi, 0, -popupMenu.getHeight() - 70);
        }


    }//GEN-LAST:event_lbTenCaSiMouseClicked

    private void btnOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionActionPerformed
        // TODO add your handling code here:
        OptionBaiHat optionBaiHat = new OptionBaiHat(bh, from_to, btnOption);
        optionBaiHat.show();


    }//GEN-LAST:event_btnOptionActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        onHover();
    }//GEN-LAST:event_formMouseMoved

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        // TODO add your handling code here:
        endHover();
    }//GEN-LAST:event_formMouseExited

    private void imageBaiHatMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageBaiHatMouseMoved
        // TODO add your handling code here:
        onHover();
    }//GEN-LAST:event_imageBaiHatMouseMoved

    private void imageBaiHatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageBaiHatMouseExited
        // TODO add your handling code here:
        endHover();
    }//GEN-LAST:event_imageBaiHatMouseExited

    private void lbTenBaiHatMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenBaiHatMouseMoved
        // TODO add your handling code here:
        onHover();
    }//GEN-LAST:event_lbTenBaiHatMouseMoved

    private void lbTenBaiHatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenBaiHatMouseExited
        // TODO add your handling code here:
        endHover();
    }//GEN-LAST:event_lbTenBaiHatMouseExited

    private void lbTenCaSiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenCaSiMouseMoved
        // TODO add your handling code here:
        onHover();
        lbTenCaSi.setForeground(new Color(174, 104, 213));
    }//GEN-LAST:event_lbTenCaSiMouseMoved

    private void lbTenCaSiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenCaSiMouseExited
        // TODO add your handling code here:
        endHover();
        lbTenCaSi.setForeground(new Color(153, 153, 153));
    }//GEN-LAST:event_lbTenCaSiMouseExited

    public void onHover() {
        setBackground(new Color(47, 39, 57));
    }

    public void endHover() {
        setBackground(new Color(23, 15, 35));
    }

    public void handlePlayBaiHat() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);

                if (imageBaiHat.getIcon().toString().contains("loadingBH.gif")) {
                    return;
                }

                BaiHat bh = MyMusicPlayer.dsBaiHat.get(MyMusicPlayer.position);
                String urlAnh = bh.getAnhBia();
                ImageIcon anhBH = Utils.getImageBaiHat(urlAnh, 50, 50);
                imageBaiHat.setIcon(anhBH);

            } catch (InterruptedException ex) {
                Logger.getLogger(ItemMusic.class.getName()).log(Level.SEVERE, null, ex);
            }

        }).start();

        if (MyMusicPlayer.loadingPlay) {
            return;
        }

        new Thread(() -> {

            if (!from_to.equals("DaTai")) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/loadingBH.gif"));
                imageBaiHat.setIcon(icon);
//                System.out.println(imageBaiHat.getIcon());

                anhNhac = imageBaiHat;
            }

            if (from_to.equals("khampha")) {
                MyMusicPlayer.initMusicPlayer(KhamPha.dsBaiHat, stt - 1);
            } else if (from_to.equals("timKiem")) {
                MyMusicPlayer.initMusicPlayer(BaiHatSearch.dsBaiHat, stt - 1);

            } else if (from_to.equals("caSi")) {
                MyMusicPlayer.initMusicPlayer(ChiTietCaSi.dsBaiHat, stt - 1);
            } else if (from_to.equals("Playlist")) {
                MyMusicPlayer.initMusicPlayer(ChiTietPlaylist.dsBaiHat, stt - 1);
            } else if (from_to.equals("YeuThich")) {
                MyMusicPlayer.initMusicPlayer(YeuThich.dsBaiHat, stt - 1);
            } else if (from_to.equals("DaNghe")) {
                MyMusicPlayer.initMusicPlayer(ThuVien.listDaNghe, stt - 1);
            } else if (from_to.equals("DaTai")) {
                System.out.println("phat nhac offline");
                ArrayList<BaiHat> listBH = DaTai.dsBaiHat;

                MyMusicPlayer.initMusicPlayer(listBH, stt - 1, "off");
            }
        }).start();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOption;
    private javax.swing.JLabel imageBaiHat;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbStt;
    private javax.swing.JLabel lbTenBaiHat;
    private javax.swing.JLabel lbTenCaSi;
    private javax.swing.JLabel lbThoiGian;
    private javax.swing.JLabel lblTheLoai;
    // End of variables declaration//GEN-END:variables
}
