/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component;

import frame.MainJFrame;
import gson.BaiHat;
import gson.ResponseDefault;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import screen.ChiTietMVPanel;
import screen.ChiTietPlaylistPanel;
import services.ApiServiceV1;
import services.LocalData;
import services.MyCustomDialog;
import services.MyMusicPlayer;
import services.utils;

/**
 *
 * @author tranv
 */
public class OptionBaiHat extends JPopupMenu {

    BaiHat bh;
    boolean isYeuThich = false;
    String from_to;
    Component c;

    public OptionBaiHat(BaiHat bh, String from_to, Component c) {
        this.bh = bh;
        this.from_to = from_to;
        this.c = c;
    }

    public void show() {

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
                    showOptions();
                } else {
                    isYeuThich = false;
                    showOptions();
                    System.out.println("err check yeu thich: " + res.getErrMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

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
            ImageIcon icon5 = new ImageIcon(getClass().getResource("/icon/icons-music-video-20.png"));
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

        popupMenu.show(c, -90, -popupMenu.getHeight() - sizeY);
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
}
