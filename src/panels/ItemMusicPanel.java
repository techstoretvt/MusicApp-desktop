/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package panels;

import gson.BaiHat;
import gson.BaiHat_CaSi;
import gson.Casi;
import gson.ResponseDefault;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import org.apache.commons.io.FileUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.ApiServiceV1;
import services.LocalData;
import services.MyCustomDialog;
import services.MyMusicPlayer;
import services.utils;
import view.MainJFrame;

/**
 *
 * @author tranv
 */
public class ItemMusicPanel extends javax.swing.JPanel {

    private BaiHat bh = null;
    private String from_to = ""; //khampha,...
    private int stt = 1;
    private boolean isYeuThich = false;
    private boolean isCheckYeuThich = false;

    public ItemMusicPanel(BaiHat bh, int stt, String from_to) {
        initComponents();

        this.bh = bh;
        this.from_to = from_to;
        this.stt = stt;

        lbStt.setText(String.valueOf(stt));
        lbTenBaiHat.setText(bh.getTenBaiHat());
        lbTenCaSi.setText(getStringTenCaSi());
        lbThoiGian.setText(utils.getThoiGianBaiHat((int) bh.getThoiGian() / 1000));

        if (from_to.equals("DaTai")) {
            new Thread(() -> {
                String urlAnh2 = "";
                while (true) {
                    urlAnh2 = utils.getAnhBHDownload(bh.getId());
                    if (urlAnh2 != null) {
                        break;
                    }
                }
                ImageIcon icon = utils.getImageBaiHat(urlAnh2, 40, 40);
                imageBaiHat.setIcon(icon);
            }).start();

        } else {
            new Thread(() -> {
                String urlAnh = bh.getAnhBia();
                ImageIcon icon = utils.getImageBaiHat(urlAnh, 40, 40);
                imageBaiHat.setIcon(icon);
            }).start();

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

        lbStt = new javax.swing.JLabel();
        imageBaiHat = new javax.swing.JLabel();
        lbTenBaiHat = new javax.swing.JLabel();
        lbTenCaSi = new javax.swing.JLabel();
        lbThoiGian = new javax.swing.JLabel();
        btnOption = new javax.swing.JButton();

        setBackground(new java.awt.Color(23, 15, 35));
        setMaximumSize(new java.awt.Dimension(32767, 70));

        lbStt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbStt.setForeground(new java.awt.Color(0, 102, 255));
        lbStt.setText("10");
        lbStt.setMaximumSize(new java.awt.Dimension(40, 32));

        imageBaiHat.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\anh-bia-am-nhac2.jpg")); // NOI18N
        imageBaiHat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imageBaiHat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageBaiHatMouseClicked(evt);
            }
        });

        lbTenBaiHat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTenBaiHat.setForeground(new java.awt.Color(255, 255, 255));
        lbTenBaiHat.setText("Ten bai hat");
        lbTenBaiHat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbTenBaiHat.setMaximumSize(new java.awt.Dimension(600, 16));
        lbTenBaiHat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTenBaiHatMouseClicked(evt);
            }
        });

        lbTenCaSi.setForeground(new java.awt.Color(153, 153, 153));
        lbTenCaSi.setText("Ten ca si, tac gia");
        lbTenCaSi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbTenCaSi.setMaximumSize(new java.awt.Dimension(600, 16));
        lbTenCaSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTenCaSiMouseClicked(evt);
            }
        });

        lbThoiGian.setForeground(new java.awt.Color(255, 255, 255));
        lbThoiGian.setText("5:33");

        btnOption.setBackground(new java.awt.Color(23, 15, 35));
        btnOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-more-20.png"))); // NOI18N
        btnOption.setBorder(null);
        btnOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOption.setFocusable(false);
        btnOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(lbStt, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTenCaSi, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(lbTenBaiHat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                        .addComponent(lbTenBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(lbTenCaSi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lbStt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                ChiTietCaSiPanel.idCaSi = listcs.get(0).getCasi().getId();
                MainJFrame.resetPanel();

            } else {
                MainJFrame.ShowPanel("ChiTietCaSi", new ChiTietCaSiPanel(listcs.get(0).getCasi().getId()));
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
                            ChiTietCaSiPanel.idCaSi = cs.getId();
                            MainJFrame.resetPanel();

                        } else {
                            MainJFrame.ShowPanel("ChiTietCaSi", new ChiTietCaSiPanel(cs.getId()));
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
        if (!isCheckYeuThich) {
            String header = utils.getHeader();

            ApiServiceV1.apiServiceV1.kiemTraYeuThichBaiHat(bh.getId(), header).enqueue(new Callback<ResponseDefault>() {
                @Override
                public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> rspns) {
                    ResponseDefault res = rspns.body();
                    if (res != null && res.getErrCode() == 0) {
                        if (res.getErrMessage().equals("like")) {
                            isYeuThich = true;
                        } else {
                            isYeuThich = false;
                        }
                        isCheckYeuThich = true;
                        showOptions();
                    } else {
                        isYeuThich = false;
                        isCheckYeuThich = true;
                        showOptions();
                        System.out.println("err check yeu thich: " + res.getErrMessage());
//                        JOptionPane.showMessageDialog(null, res.getErrMessage(),
//                                "Thông báo",JOptionPane.WARNING_MESSAGE);
                    }

                }

                @Override
                public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            });
        } else {
            showOptions();
        }

    }//GEN-LAST:event_btnOptionActionPerformed

    public void showOptions() {
        int sizeY = 120;

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem option1 = new JMenuItem("Thêm vào playlist");
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/icon/icon-add-playlist-black.png"));
        option1.setIcon(icon1);
        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!utils.getIsLogin()) {
                    return;
                }
                MyCustomDialog customDialog = new MyCustomDialog(null, "Thêm vào playlist",
                        new ThemVaoPlaylist(bh.getId()));
                customDialog.setVisible(true);
            }
        });

        JMenuItem option2 = new JMenuItem("Tải xuống");
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/icon/icon-download.png"));
        option2.setIcon(icon2);
        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDownload();
            }
        });

        JMenuItem option3 = new JMenuItem("Phát tiếp theo");
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/icon/icon-add-play.png"));
        option3.setIcon(icon3);
        option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyMusicPlayer.themBaiHat(bh);
            }

        });

        JMenuItem option4 = new JMenuItem("Sao chép link");
        ImageIcon icon4 = new ImageIcon(getClass().getResource("/icon/icon-copy-link.png"));
        option4.setIcon(icon4);
        option4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String link = bh.getLinkBaiHat();
                StringSelection stringSelection = new StringSelection(link);

                // Lấy đối tượng Clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

                // Đặt dữ liệu vào clipboard
                clipboard.setContents(stringSelection, null);

                JOptionPane.showMessageDialog(null, "Đã copy liên kết",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        if (isYeuThich) {
            JMenuItem optBoYeuThich = new JMenuItem("Bỏ yêu thích");
            ImageIcon iconBoYT = new ImageIcon(getClass().getResource("/icon/icon-heart.png"));
            optBoYeuThich.setIcon(iconBoYT);
            optBoYeuThich.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toggleYeuThich();
                }
            });

            popupMenu.add(optBoYeuThich);
        } else {

            JMenuItem optYeuThich = new JMenuItem("Yêu thích");
            ImageIcon iconBoYT = new ImageIcon(getClass().getResource("/icon/icon-no-heart.png"));
            optYeuThich.setIcon(iconBoYT);
            optYeuThich.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (utils.getIsLogin()) {
                        toggleYeuThich();
                    }

                }
            });

            popupMenu.add(optYeuThich);

        }

        popupMenu.add(option1);

        if (!kiemTraTonTai()) {
            popupMenu.add(option2);
        }

        if (from_to.equals("DaTai")) {
            JMenuItem optXoaDaTai = new JMenuItem("Xóa khỏi playlist");
            ImageIcon iconRemove = new ImageIcon(getClass().getResource("/icon/icon-delete-black.png"));
            optXoaDaTai.setIcon(iconRemove);

            optXoaDaTai.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleXoaKhoiDaTai();
                }

            });

            popupMenu.add(optXoaDaTai);
            sizeY += 20;
        }
        popupMenu.add(option3);

        String linkMV = bh.getLinkMV();
        if (!linkMV.equals("false")) {
            JMenuItem option5 = new JMenuItem("Xem MV");
            ImageIcon icon5 = new ImageIcon(getClass().getResource("/icon/icon-copy-link.png"));
            option5.setIcon(icon5);
            option5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainJFrame.ShowPanel("ChiTietMV", new ChiTietMVPanel(bh.getId()));
                }

            });

            popupMenu.add(option5);
        }

        popupMenu.add(option4);

        if (from_to.equals("Playlist")) {
            JMenuItem optDeletePlaylist = new JMenuItem("Xóa khỏi playlist");
            ImageIcon iconRemove = new ImageIcon(getClass().getResource("/icon/icon-delete-black.png"));
            optDeletePlaylist.setIcon(iconRemove);

            optDeletePlaylist.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleXoaBHKhoiPlaylist();
                }

            });

            popupMenu.add(optDeletePlaylist);
            sizeY += 20;
        }

        popupMenu.show(btnOption, -90, -popupMenu.getHeight() - sizeY);
    }

    public void handleDownload() {
        try {
            if (kiemTraTonTai()) {
                JOptionPane.showMessageDialog(this, "Bài hát đã tồn tại.", "Download thất bại",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String urlBH = bh.getLinkBaiHat();

            URL songURL = new URL(urlBH);
            URL songURLImage = new URL(bh.getAnhBia());

            String newName = bh.getId() + ".mp3";

            String file_path = System.getProperty("user.dir")
                    + File.separator + "src"
                    + File.separator + "download" + File.separator + newName;

            String file_path_image = System.getProperty("user.dir")
                    + File.separator + "src"
                    + File.separator + "download" + File.separator + bh.getId() + ".jpg";

            String filePathLocal = LocalData.getData("file_path_download");
            if (filePathLocal != null) {
                file_path = filePathLocal;
            }

            File destination = new File(file_path);
            File destination_image = new File(file_path_image);
            new Thread(() -> {
                JPanel pnLoading = new LoadingTaiBaiHat();
                MyCustomDialog customDialog = new MyCustomDialog(null, "Tải bài hát", pnLoading);

                try {
                    new Thread(() -> {
                        customDialog.show(true);
                    }).start();

                    FileUtils.copyURLToFile(songURL, destination);
                    FileUtils.copyURLToFile(songURLImage, destination_image);

                    ArrayList<BaiHat> listBH = LocalData.getListDownload();
                    listBH.add(bh);
                    LocalData.saveListDownLoad(listBH);

                    customDialog.show(false);
                    LoadingTaiBaiHat.closeThread();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Download thất bại", "Lỗi rồi",
                            JOptionPane.ERROR_MESSAGE);
                    customDialog.show(false);
                    LoadingTaiBaiHat.closeThread();
                    Logger.getLogger(ItemMusicPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }).start();

        } catch (MalformedURLException ex) {
            Logger.getLogger(ItemMusicPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean kiemTraTonTai() {
        ArrayList<BaiHat> listBH = LocalData.getListDownload();
        boolean check = false;
        for (BaiHat i : listBH) {
            if (i.getLinkBaiHat().equals(bh.getLinkBaiHat())) {
                check = true;
            }
        }

        return check;
    }

    public void handleXoaKhoiDaTai() {
        ArrayList<BaiHat> listBH = LocalData.getListDownload();

        ArrayList<BaiHat> newListBH = new ArrayList<>();
        for (BaiHat i : listBH) {
            if (!i.getLinkBaiHat().equals(bh.getLinkBaiHat())) {
                newListBH.add(i);
            }
        }

        LocalData.saveListDownLoad(newListBH);
        MainJFrame.resetPanel();
    }

    public void toggleYeuThich() {
        String header = utils.getHeader();

        ApiServiceV1.apiServiceV1.toggleLikeBaiHat(bh.getId(), header).enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> rspns) {
                ResponseDefault res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    handleCheckYeuThich();
                } else {
                    System.out.println("Warning: " + res.getErrMessage());
                    JOptionPane.showMessageDialog(null, res.getErrMessage(),
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public void handleCheckYeuThich() {
        String header = utils.getHeader();

        ApiServiceV1.apiServiceV1.kiemTraYeuThichBaiHat(bh.getId(), header).enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> rspns) {
                ResponseDefault res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    if (res.getErrMessage().equals("like")) {
                        isYeuThich = true;
                    } else {
                        isYeuThich = false;
                    }
                } else {
                    System.out.println("check yeu thich: " + res.getErrMessage());
//                        JOptionPane.showMessageDialog(null, res.getErrMessage(),
//                                "Thông báo",JOptionPane.WARNING_MESSAGE);
                }

            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });

    }

    public void handleXoaBHKhoiPlaylist() {
        String header = utils.getHeader();

        ApiServiceV1.apiServiceV1.xoaBaiHatKhoiDS(ChiTietPlaylistPanel.idPlaylist, bh.getId(), header).enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> rspns) {
                ResponseDefault res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    MainJFrame.resetPanel();
                } else {
                    System.out.println("Warning: " + res.getErrMessage());
                    JOptionPane.showMessageDialog(null, res.getErrMessage(),
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public void handlePlayBaiHat() {

        if (from_to.equals("khampha")) {
            MyMusicPlayer.initMusicPlayer(KhamPhaPanel.dsBaiHat, stt - 1);

        } else if (from_to.equals("timKiem")) {
            MyMusicPlayer.initMusicPlayer(BaiHatSearchPanel.dsBaiHat, stt - 1);

        } else if (from_to.equals("caSi")) {
            MyMusicPlayer.initMusicPlayer(ChiTietCaSiPanel.dsBaiHat, stt - 1);
        } else if (from_to.equals("Playlist")) {
            MyMusicPlayer.initMusicPlayer(ChiTietPlaylistPanel.dsBaiHat, stt - 1);
        } else if (from_to.equals("YeuThich")) {
            MyMusicPlayer.initMusicPlayer(YeuThichPanel.dsBaiHat, stt - 1);
        } else if (from_to.equals("DaTai")) {
            System.out.println("phat nhac offline");
            ArrayList<BaiHat> listBH = LocalData.getListDownload();

            MyMusicPlayer.initMusicPlayer(listBH, stt - 1, "off");
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOption;
    private javax.swing.JLabel imageBaiHat;
    private javax.swing.JLabel lbStt;
    private javax.swing.JLabel lbTenBaiHat;
    private javax.swing.JLabel lbTenCaSi;
    private javax.swing.JLabel lbThoiGian;
    // End of variables declaration//GEN-END:variables
}
