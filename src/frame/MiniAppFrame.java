/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import model.BaiHat;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import component.PhatKeTiepPanel;
import services.MyMusicPlayer;
import helpers.Utils;
import observer.MiniAppObserver;
import observer.PhatKeTiepObserver;

/**
 *
 * @author tranv
 */
public class MiniAppFrame extends javax.swing.JFrame {

    public static boolean isOpen = false;
    private boolean isShowPhatKeTiep = false;
    private MiniAppObserver miniAppObserver;
    private PhatKeTiepPanel phatKeTiepPanel;
    private PhatKeTiepObserver phatKeTiepObserver;
    private boolean isZoom = true;

    /**
     * Creates new form MiniAppFrame
     */
    public MiniAppFrame() {
//        setUndecorated(true);
        initComponents();
        setAlwaysOnTop(true);
        setTitle("Đang phát");
        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-app.jpg"));
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loadGiaoDienBaiHat();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isOpen = false;
            }

        });
        miniAppObserver = new MiniAppObserver(this);
        MainJFrame.subject.attach(miniAppObserver);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                MainJFrame.subject.detach(miniAppObserver);
            }

        });

        handeZoom();

        pack();

    }

    public void loadGiaoDienBaiHat() {
        if (MyMusicPlayer.dsBaiHat != null && MyMusicPlayer.dsBaiHat.size() != 0) {
            BaiHat bh = MyMusicPlayer.dsBaiHat.get(MyMusicPlayer.position);

            new Thread(() -> {
                ImageIcon anh = Utils.getImageBaiHat(bh.getAnhBia(), 50, 50);
                imgBaiHat.setIcon(anh);
            }).start();

            lblTenCaSi.setText(Utils.getTenCaSi(bh));
            lblTenBaiHat.setText(bh.getTenBaiHat());

            if (MyMusicPlayer.isPlay) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-pause.png"));
                btnPause.setIcon(icon);
            } else {
                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-play.png"));
                btnPause.setIcon(icon);
            }

            if (isShowPhatKeTiep) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/menu-music-active.png"));
                btnPhatKeTiep.setIcon(icon);

                updatePhatKeTiep();
            } else {
                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/menu-music.png"));
                btnPhatKeTiep.setIcon(icon);
            }

        }
    }

    public void updatePhatKeTiep() {
        phatKeTiepObserver.update();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        imgBaiHat = new javax.swing.JLabel();
        lblTenBaiHat = new javax.swing.JLabel();
        lblTenCaSi = new javax.swing.JLabel();
        btnZoom = new javax.swing.JButton();
        btnShowMainApp = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnPrev = new javax.swing.JLabel();
        btnPause = new javax.swing.JLabel();
        btnNext = new javax.swing.JLabel();
        btnPhatKeTiep = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(468, 90));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(23, 15, 35));

        lblTenBaiHat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenBaiHat.setForeground(new java.awt.Color(255, 255, 255));
        lblTenBaiHat.setMaximumSize(new java.awt.Dimension(130, 0));
        lblTenBaiHat.setMinimumSize(new java.awt.Dimension(130, 0));
        lblTenBaiHat.setPreferredSize(new java.awt.Dimension(130, 0));

        lblTenCaSi.setForeground(new java.awt.Color(204, 204, 204));
        lblTenCaSi.setMaximumSize(new java.awt.Dimension(130, 0));
        lblTenCaSi.setMinimumSize(new java.awt.Dimension(130, 0));
        lblTenCaSi.setPreferredSize(new java.awt.Dimension(130, 0));

        btnZoom.setBackground(new java.awt.Color(23, 15, 35));
        btnZoom.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnZoom.setForeground(new java.awt.Color(255, 255, 255));
        btnZoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-left-20.png"))); // NOI18N
        btnZoom.setBorder(null);
        btnZoom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnZoom.setFocusPainted(false);
        btnZoom.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnZoom.setMaximumSize(new java.awt.Dimension(40, 23));
        btnZoom.setPreferredSize(new java.awt.Dimension(40, 23));
        btnZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomActionPerformed(evt);
            }
        });

        btnShowMainApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-device-20.png"))); // NOI18N
        btnShowMainApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnShowMainApp.setMaximumSize(new java.awt.Dimension(40, 20));
        btnShowMainApp.setPreferredSize(new java.awt.Dimension(40, 20));
        btnShowMainApp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowMainAppMouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(23, 15, 35));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/prev.png"))); // NOI18N
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrevMouseClicked(evt);
            }
        });
        jPanel3.add(btnPrev);

        btnPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-pause.png"))); // NOI18N
        btnPause.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPauseMouseClicked(evt);
            }
        });
        jPanel3.add(btnPause);

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-next-30.png"))); // NOI18N
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNextMouseClicked(evt);
            }
        });
        jPanel3.add(btnNext);

        btnPhatKeTiep.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnPhatKeTiep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-playlist.png"))); // NOI18N
        btnPhatKeTiep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPhatKeTiep.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnPhatKeTiep.setMaximumSize(new java.awt.Dimension(40, 20));
        btnPhatKeTiep.setPreferredSize(new java.awt.Dimension(40, 20));
        btnPhatKeTiep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPhatKeTiepMouseClicked(evt);
            }
        });
        jPanel3.add(btnPhatKeTiep);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(imgBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenBaiHat, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(lblTenCaSi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(btnShowMainApp, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnZoom, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(lblTenBaiHat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lblTenCaSi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(imgBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnZoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnShowMainApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowMainAppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowMainAppMouseClicked
        // TODO add your handling code here:
        MiniAppFrame.isOpen = false;
        dispose();
        Main.rootFrame.setVisible(true);
    }//GEN-LAST:event_btnShowMainAppMouseClicked

    private void btnPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseClicked
        // TODO add your handling code here:
        MyMusicPlayer.preBaiHat();
//        loadGiaoDienBaiHat();
    }//GEN-LAST:event_btnPrevMouseClicked

    private void btnNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseClicked
        // TODO add your handling code here:
        MyMusicPlayer.nextBaiHat();
//        loadGiaoDienBaiHat();
    }//GEN-LAST:event_btnNextMouseClicked

    private void btnPauseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPauseMouseClicked
        // TODO add your handling code here:
        if (MyMusicPlayer.isPlay) {
            MyMusicPlayer.pause();
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-play.png"));
            btnPause.setIcon(icon);
        } else {
            MyMusicPlayer.resume();
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-pause.png"));
            btnPause.setIcon(icon);
        }
    }//GEN-LAST:event_btnPauseMouseClicked

    private void btnPhatKeTiepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPhatKeTiepMouseClicked
        // TODO add your handling code here:
        isShowPhatKeTiep = !isShowPhatKeTiep;
//        loadGiaoDienBaiHat();

        if (isShowPhatKeTiep) {
            setSize(468, 400);
            phatKeTiepPanel = new PhatKeTiepPanel(true);
            phatKeTiepObserver = new PhatKeTiepObserver(phatKeTiepPanel);
            jPanel1.add(phatKeTiepPanel, BorderLayout.CENTER);

            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/menu-music-active.png"));
            btnPhatKeTiep.setIcon(icon);

        } else {

            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/menu-music.png"));
            btnPhatKeTiep.setIcon(icon);
            setSize(468, 90);
            try {
                jPanel1.remove(1);
            } catch (Exception e) {
            }

        }

        jPanel1.repaint();
        jPanel1.revalidate();

    }//GEN-LAST:event_btnPhatKeTiepMouseClicked

    private void btnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomActionPerformed
        // TODO add your handling code here:
        handeZoom();
    }//GEN-LAST:event_btnZoomActionPerformed

    public void handeZoom() {
        if (isZoom) {
            new Thread(() -> {
                setSize(300, 90);
                jPanel3.setVisible(false);

                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icons-right-20.png"));
                btnZoom.setIcon(icon);
            }).start();

        } else {
            int height = 90;
            if (isShowPhatKeTiep) {
                height = 400;
            }
            setSize(468, height);
            jPanel3.setVisible(true);

            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icons-left-20.png"));
            btnZoom.setIcon(icon);
        }
        isZoom = !isZoom;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnNext;
    private javax.swing.JLabel btnPause;
    private javax.swing.JLabel btnPhatKeTiep;
    private javax.swing.JLabel btnPrev;
    private javax.swing.JLabel btnShowMainApp;
    private javax.swing.JButton btnZoom;
    private javax.swing.JLabel imgBaiHat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblTenBaiHat;
    private javax.swing.JLabel lblTenCaSi;
    // End of variables declaration//GEN-END:variables
}
