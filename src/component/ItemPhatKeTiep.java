/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import model.BaiHat;
import model.BaiHat_CaSi;
import model.Casi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import services.MyMusicPlayer;
import helpers.Utils;
import frame.MainJFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author tranv
 */
public class ItemPhatKeTiep extends javax.swing.JPanel {

    private BaiHat bh = null;
    private int index = 1;
    boolean active = false;

    public ItemPhatKeTiep(BaiHat bh, int index, boolean active, boolean showArrow) {
        initComponents();

        this.bh = bh;
        this.index = index;
        this.active = active;

        lbTenBaiHat.setText(bh.getTenBaiHat());
        lbTenCaSi.setText(getStringTenCaSi());

        new Thread(() -> {
            String urlAnh = bh.getAnhBia();
            if (MyMusicPlayer.typeMusic.equals("off")) {
                urlAnh = Utils.getAnhBHDownload(bh.getId());
            }
            ImageIcon icon = Utils.getImageBaiHat(urlAnh, 40, 40);
            imageBaiHat.setIcon(icon);
        }).start();

        new Thread(() -> {
            if (active) {
                setBackground(new Color(204, 0, 204));
                lbTenCaSi.setForeground(new Color(200, 200, 200));
                btnDelete.setVisible(false);
            }

            if (!showArrow) {
                btnUp.setVisible(false);
                btnDown.setVisible(false);
            }

            if (index == 0) {
                btnUp.setVisible(false);
            } else if (index == MyMusicPlayer.dsBaiHat.size() - 1) {
                btnDown.setVisible(false);
            }
        }).start();

    }

    public void setActive(boolean active) {
        this.active = active;
        if (active) {
            setBackground(new Color(204, 0, 204));
            lbTenCaSi.setForeground(new Color(200, 200, 200));
            btnDelete.setVisible(false);
        } else {
            setBackground(new Color(0, 0, 102));
            lbTenCaSi.setForeground(new Color(255, 255, 255));
            btnDelete.setVisible(true);
        }
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

        btnUp = new javax.swing.JLabel();
        btnDown = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        imageBaiHat = new javax.swing.JLabel();
        lbTenBaiHat = new javax.swing.JLabel();
        lbTenCaSi = new javax.swing.JLabel();
        btnDelete = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 102));
        setMaximumSize(new java.awt.Dimension(32767, 62));
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

        btnUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-up-20.png"))); // NOI18N
        btnUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUp.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnUpMouseMoved(evt);
            }
        });
        btnUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUpMouseExited(evt);
            }
        });

        btnDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-down-20.png"))); // NOI18N
        btnDown.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDown.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnDownMouseMoved(evt);
            }
        });
        btnDown.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDownMouseExited(evt);
            }
        });

        jPanel1.setBackground(null);
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
        });

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

        lbTenBaiHat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTenBaiHat.setForeground(new java.awt.Color(255, 255, 255));
        lbTenBaiHat.setText("Ten bai hat");
        lbTenBaiHat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbTenBaiHat.setMaximumSize(new java.awt.Dimension(190, 16));
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
        lbTenCaSi.setMaximumSize(new java.awt.Dimension(190, 16));
        lbTenCaSi.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbTenCaSiMouseMoved(evt);
            }
        });
        lbTenCaSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbTenCaSiMouseExited(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-delete-red.png"))); // NOI18N
        btnDelete.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnDeleteMouseMoved(evt);
            }
        });
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDeleteMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTenBaiHat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTenCaSi, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(lbTenBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(8, 8, 8)
                    .addComponent(lbTenCaSi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnDelete))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUp)
                    .addComponent(btnDown))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDown)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        // TODO add your handling code here:
        MyMusicPlayer.dsBaiHat.remove(bh);
        setVisible(false);
    }//GEN-LAST:event_btnDeleteMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        if (SwingUtilities.isLeftMouseButton(evt)) {
            if (!active) {
                handlePlayBaiHat();
            }
        } else if (SwingUtilities.isRightMouseButton(evt)) {
            System.out.println("Right mouse button clicked");
        }
    }//GEN-LAST:event_jPanel1MouseClicked

    public void initBtnUpDownClick(PhatKeTiepPanel phatKeTiepPanel) {
        btnDown.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ArrayList<BaiHat> dsBaiHats = MyMusicPlayer.dsBaiHat;
                BaiHat bh1 = dsBaiHats.get(index);
                BaiHat bh2 = dsBaiHats.get(index + 1);

                dsBaiHats.set(index, bh2);
                dsBaiHats.set(index + 1, bh1);

                MyMusicPlayer.dsBaiHat = dsBaiHats;

                if (index == MyMusicPlayer.position) {
                    MyMusicPlayer.position = MyMusicPlayer.position + 1;
                } else if (index + 1 == MyMusicPlayer.position) {
                    MyMusicPlayer.position = MyMusicPlayer.position - 1;
                }

                phatKeTiepPanel.swapItem(index, index + 1);
            }

        });

        btnUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ArrayList<BaiHat> dsBaiHats = MyMusicPlayer.dsBaiHat;
                BaiHat bh1 = dsBaiHats.get(index - 1);
                BaiHat bh2 = dsBaiHats.get(index);

//                dsBaiHats.remove(bh1);
//                dsBaiHats.remove(bh2);
//
//                dsBaiHats.add(index - 1, bh2);
//                dsBaiHats.add(index, bh1);
                dsBaiHats.set(index - 1, bh2);
                dsBaiHats.set(index, bh1);

                MyMusicPlayer.dsBaiHat = dsBaiHats;

                if (index == MyMusicPlayer.position) {
                    MyMusicPlayer.position = MyMusicPlayer.position - 1;
                } else if (index - 1 == MyMusicPlayer.position) {
                    MyMusicPlayer.position = MyMusicPlayer.position + 1;
                }

                phatKeTiepPanel.swapItem(index, index -1);
            }

        });
    }


    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        onHover();
    }//GEN-LAST:event_formMouseMoved

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        // TODO add your handling code here:
        endHover();
    }//GEN-LAST:event_formMouseExited

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        // TODO add your handling code here:
        onHover();
    }//GEN-LAST:event_jPanel1MouseMoved

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseExited
        // TODO add your handling code here:
        endHover();
    }//GEN-LAST:event_jPanel1MouseExited

    private void btnUpMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpMouseMoved
        // TODO add your handling code here:
        onHover();
    }//GEN-LAST:event_btnUpMouseMoved

    private void btnUpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpMouseExited
        // TODO add your handling code here:
        endHover();
    }//GEN-LAST:event_btnUpMouseExited

    private void btnDownMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDownMouseMoved
        // TODO add your handling code here:
        onHover();
    }//GEN-LAST:event_btnDownMouseMoved

    private void btnDownMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDownMouseExited
        // TODO add your handling code here:
        endHover();
    }//GEN-LAST:event_btnDownMouseExited

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
    }//GEN-LAST:event_lbTenCaSiMouseMoved

    private void lbTenCaSiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenCaSiMouseExited
        // TODO add your handling code here:
        endHover();
    }//GEN-LAST:event_lbTenCaSiMouseExited

    private void btnDeleteMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseMoved
        // TODO add your handling code here:
        onHover();
    }//GEN-LAST:event_btnDeleteMouseMoved

    private void btnDeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseExited
        // TODO add your handling code here:
        endHover();
    }//GEN-LAST:event_btnDeleteMouseExited

    private void imageBaiHatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageBaiHatMouseClicked
        // TODO add your handling code here:
        if (SwingUtilities.isLeftMouseButton(evt)) {
            if (!active) {
                handlePlayBaiHat();
            }
        } else if (SwingUtilities.isRightMouseButton(evt)) {
            System.out.println("Right mouse button clicked");
        }
    }//GEN-LAST:event_imageBaiHatMouseClicked

    private void lbTenBaiHatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenBaiHatMouseClicked
        // TODO add your handling code here:
        if (SwingUtilities.isLeftMouseButton(evt)) {
            if (!active) {
                handlePlayBaiHat();
            }
        } else if (SwingUtilities.isRightMouseButton(evt)) {
            System.out.println("Right mouse button clicked");
        }
    }//GEN-LAST:event_lbTenBaiHatMouseClicked

    public void onHover() {
        if (active) {
            return;
        }
        setBackground(new Color(47, 39, 57));
    }

    public void endHover() {
        if (active) {
            return;
        }
        setBackground(new Color(0, 0, 102));
    }

    public void handlePlayBaiHat() {
        PhatKeTiepPanel.isUpdate = false;
        new Thread(() -> {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/loadingBH.gif"));
            imageBaiHat.setIcon(icon);

            ItemMusic.anhNhac = imageBaiHat;

        }).start();

        new Thread(() -> {
            MyMusicPlayer.initMusicPlayer(MyMusicPlayer.dsBaiHat, index, MyMusicPlayer.typeMusic);
        }).start();

    }

    public void setIndex(int newIndex) {
        this.index = newIndex;
        if (index == 0) {
            btnUp.setVisible(false);
        } else if (index == MyMusicPlayer.dsBaiHat.size() - 1) {
            btnDown.setVisible(false);
        }
        else {
            btnUp.setVisible(true);
            btnDown.setVisible(true);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnDown;
    private javax.swing.JLabel btnUp;
    private javax.swing.JLabel imageBaiHat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbTenBaiHat;
    private javax.swing.JLabel lbTenCaSi;
    // End of variables declaration//GEN-END:variables
}
