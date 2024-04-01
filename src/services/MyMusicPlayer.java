/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import gson.BaiHat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.io.FileUtils;
import frame.MainJFrame;
import gson.GetListBaiHat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import component.ItemMusicPanel;
import component.KaraokePanel;
import component.PhatKeTiepPanel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author tranv
 */
public class MyMusicPlayer {

    public static FileInputStream fileInputStream;
    public static Player player = null;
    public static long pauseLocation;
    public static long songTotalLength;
    public static String file_path_music = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "resources" + File.separator + "baihat.mp3";
    public static boolean isPlay = false;
    public static ArrayList<BaiHat> dsBaiHat;
    public static int position = 0;

    public static Thread myThread;
    public static int currentTime = 0;
    public static int totalTime = 0;
    public static boolean isLoop = false;
    public static boolean isRandom = false;

    public static BigDecimal timeCurrentLoiBaiHat = new BigDecimal("0.0");

    public static String typeMusic = "on";

    public static void initMusicPlayer(ArrayList<BaiHat> list, int pos) {
        String type = "on";
        initMusicPlayer(list, pos, type);
    }

    public static void initMusicPlayer(ArrayList<BaiHat> list, int pos, String type) {
        try {
            dsBaiHat = list;
            position = pos;
            currentTime = 0;
            typeMusic = type;

            if (myThread != null) {
                myThread.interrupt();
            }

            // reset player
            if (player != null) {
                player.close();
                pauseLocation = 0;
                songTotalLength = 0;
            }

            BaiHat currentBH = dsBaiHat.get(position);
            totalTime = (int) (currentBH.getThoiGian() / 1000);

            if (type.equals("on")) {
                // download
                URL songURL = new URL(currentBH.getLinkBaiHat());

                File destination = new File(file_path_music);
                FileUtils.copyURLToFile(songURL, destination);
            }

            // reset ui
            if (MainJFrame.btnPlayPause != null) {
                ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-pause.png"));
                MainJFrame.btnPlayPause.setIcon(icon);
            }
            if (MainJFrame.lbTenBaiHat != null && MainJFrame.lbTenCaSi != null) {
                MainJFrame.lbTenBaiHat.setText(currentBH.getTenBaiHat());
                MainJFrame.lbTenCaSi.setText(utils.getTenCaSi(currentBH));
            }
            if (MainJFrame.imageBaiHat != null) {
                new Thread(() -> {
                    String urlAnh = currentBH.getAnhBia();
                    if (type.equals("off")) {
                        urlAnh = utils.getAnhBHDownload(currentBH.getId());
                    }
                    ImageIcon anhBH = utils.getImageBaiHat(urlAnh, 50, 50);
                    MainJFrame.imageBaiHat.setIcon(anhBH);
                }).start();

            }
            if (MainJFrame.lbTongThoiGian != null) {
                String tongTG = utils.getThoiGianBaiHat((int) (currentBH.getThoiGian() / 1000));
                MainJFrame.lbTongThoiGian.setText(tongTG);
            }
            if (MainJFrame.PanelFooter != null) {
                MainJFrame.PanelFooter.setVisible(true);
            }
            if (MainJFrame.isKaraoke) {
                MainJFrame.isKaraoke = false;
                MainJFrame.ToggleShowKaraoke();
            }
            if (ItemMusicPanel.anhNhac != null) {
                new Thread(() -> {
                    String urlAnh = currentBH.getAnhBia();
                    ImageIcon anhBH = utils.getImageBaiHat(urlAnh, 50, 50);
                    ItemMusicPanel.anhNhac.setIcon(anhBH);

                    ItemMusicPanel.anhNhac = null;
                }).start();

            }

            // add phat ke tiep
            if (MainJFrame.isMenuPhatKeTiep) {

                MainJFrame.PanelWrap.remove(1);
                JPanel phatKeTiep = new PhatKeTiepPanel();
                MainJFrame.PanelWrap.add(phatKeTiep, BorderLayout.EAST);
                MainJFrame.PanelWrap.revalidate();
                MainJFrame.PanelWrap.repaint();
            }

            //add đã nghe
            new Thread(() -> {
                ArrayList<BaiHat> listDaNghe = null;
                ArrayList<BaiHat> newListDaNghe = new ArrayList<>();
                try {
                    listDaNghe = LocalData.getListDaNghe();
                } catch (Exception e) {

                }

                if (listDaNghe == null) {
                    listDaNghe = new ArrayList<>();
                }
                
                for (BaiHat bh : listDaNghe) {
                    if(!bh.getId().equals(currentBH.getId())){
                        newListDaNghe.add(bh);
                    }
                }

                newListDaNghe.addFirst(currentBH);
                if (newListDaNghe.size() >= 10) {
                    newListDaNghe.removeLast();
                }
                LocalData.saveListDaNghe(newListDaNghe);

            }).start();

            //play
            try {
                String file_path = file_path_music;
                if (type.equals("off")) {
                    System.out.println("vao off");
                    file_path = utils.getUrlBHDownload(currentBH.getId());
                }
//                JOptionPane.showMessageDialog(null ,file_path);

                fileInputStream = new FileInputStream(file_path);
                player = new Player(fileInputStream);
                songTotalLength = fileInputStream.available();
            } catch (java.io.IOException e) {
                System.out.println("vao loi 1");
            } catch (JavaLayerException ex) {
                System.out.println("vao loi 2");
            }

            isPlay = true;
            fileInputStream.skip(0);

            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    System.out.println("Loi Play");
                    e.printStackTrace();
                }
            }).start();

            // get current time
            if (MainJFrame.progessTimeBaiHat != null) {
                MainJFrame.progessTimeBaiHat.setMaximum((int) (currentBH.getThoiGian() / 1000));
            }

            myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                        // su kien phat xong
                        if (totalTime == currentTime) {
                            myThread.interrupt();
                            isPlay = false;
                            if (MainJFrame.btnPlayPause != null) {
                                ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-play.png"));
                                MainJFrame.btnPlayPause.setIcon(icon);
                            }
                            player.close();

                            // next nhac
                            nextBaiHat();

                            break;
                        }

                        // set progess
                        if (isPlay) {
                            currentTime += 1;
//                                System.out.println("tg Current: " + currentTime);

                            if (MainJFrame.progessTimeBaiHat != null) {
                                MainJFrame.progessTimeBaiHat.setValue(currentTime);
                            }

                            if (MainJFrame.lbThoiGianHienTai != null) {
                                String tgHienTai = utils.getThoiGianBaiHat(currentTime);
                                MainJFrame.lbThoiGianHienTai.setText(tgHienTai);
                            }

                            if (MainJFrame.isKaraoke && KaraokePanel.dsItemLoiBH != null
                                    && KaraokePanel.dsItemLoiBH.size() != 0
                                    && KaraokePanel.listIndexLoiBaiHat != null) {

                                Rectangle rect = new Rectangle(0, 200, 10, 10);

                                Integer indexLoiBH = KaraokePanel.listIndexLoiBaiHat.get(String.valueOf(currentTime));
                                if (indexLoiBH != null) {
                                    int lastIndex = KaraokePanel.currentIndexLoiBH;
                                    KaraokePanel.dsItemLoiBH.get(lastIndex).setForeground(Color.WHITE);

                                    KaraokePanel.dsItemLoiBH.get(indexLoiBH).setForeground(Color.YELLOW);

                                    KaraokePanel.dsItemLoiBH.get(indexLoiBH).scrollRectToVisible(rect);

                                    KaraokePanel.currentIndexLoiBH = indexLoiBH;
                                }

                            }
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            break;
                        }

                    }
                }
            });
            myThread.start();

        } catch (MalformedURLException ex) {
            System.out.println("vao loi karaoke 2");
        } catch (IOException ex) {
            System.out.println("vao loi karaoke 3");
        }
    }

    public static void initMusicOffline(ArrayList<BaiHat> listBH, int stt) {
        dsBaiHat = listBH;
        position = stt;

        BaiHat currentBH = listBH.get(stt);

        //play
        new Thread(() -> {
            try {
                String file_path = System.getProperty("user.dir")
                        + File.separator + "src"
                        + File.separator + "download" + File.separator + currentBH.getId() + ".mp3";

                fileInputStream = new FileInputStream(file_path);
                player = new Player(fileInputStream);
                songTotalLength = fileInputStream.available();

                player.play();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (JavaLayerException ex) {

            }
        }).start();

    }

    public static void pause() {
        if (player != null) {
            try {
                pauseLocation = fileInputStream.available();

                //test
//                System.out.println("pause: " + String.valueOf(pauseLocation) + " : " + String.valueOf(songTotalLength));
//                System.out.println("time: " + String.valueOf(songTotalLength - pauseLocation));
//
//                double tgBaiHat = dsBaiHat.get(position).getThoiGian();
//                System.out.println("tg: " + String.valueOf(tgBaiHat));
                //end test
                player.close();
                isPlay = false;

                if (MainJFrame.btnPlayPause != null) {
                    ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-play.png"));
                    MainJFrame.btnPlayPause.setIcon(icon);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void resume() {
        try {
            // check phát lại nếu bài hát trước đã phát hết

            // -----
            if (player == null) {
                return;
            }
            fileInputStream = new FileInputStream(file_path_music);
            fileInputStream.skip(songTotalLength - pauseLocation);
            player = new Player(fileInputStream);
            isPlay = true;

            if (MainJFrame.btnPlayPause != null) {
                ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-pause.png"));
                MainJFrame.btnPlayPause.setIcon(icon);
            }

        } catch (JavaLayerException | java.io.IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void setVolume(int value) {
        if (MainJFrame.progessVolume != null) {
            MainJFrame.progessVolume.setValue(value);
        }

        if (player != null) {
            //set volume

        }
    }

    public static void setTimeBaiHat(float percent) {
        try {
            if (player == null) {
                return;
            }
            player.close();

            BaiHat currentBH = dsBaiHat.get(position);
            String file_path = file_path_music;
            if (typeMusic.equals("off")) {
                file_path = utils.getUrlBHDownload(currentBH.getId());
            }
            fileInputStream = new FileInputStream(file_path);

            int timeNext = (int) (currentBH.getThoiGian() / 1000 * percent);
            currentTime = timeNext;
            long timeSkip = (long) (timeNext * 15.97 * 1000);

//            System.out.println("Tong tg: " + currentBH.getThoiGian() / 1000);
//            System.out.println("tg next: " + timeNext);
//            System.out.println("tong tg file: " + songTotalLength);
//            System.out.println("tg skip: " + timeSkip);
            fileInputStream.skip(timeSkip);

            player = new Player(fileInputStream);
            isPlay = true;

            if (MainJFrame.btnPlayPause != null) {
                ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-pause.png"));
                MainJFrame.btnPlayPause.setIcon(icon);
            }

        } catch (JavaLayerException | java.io.IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void setTimeBaiHat2(double giay) {
        try {
            if (player == null) {
                return;
            }
            player.close();

            fileInputStream = new FileInputStream(file_path_music);

            BaiHat currentBH = dsBaiHat.get(position);
            int timeNext = (int) giay;
            currentTime = timeNext;
            long timeSkip = (long) (timeNext * 15.97 * 1000);

//            System.out.println("Tong tg: " + currentBH.getThoiGian() / 1000);
//            System.out.println("tg next: " + timeNext);
//            System.out.println("tong tg file: " + songTotalLength);
//            System.out.println("tg skip: " + timeSkip);
            fileInputStream.skip(timeSkip);

            player = new Player(fileInputStream);
            isPlay = true;

            if (MainJFrame.btnPlayPause != null) {
                ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-pause.png"));
                MainJFrame.btnPlayPause.setIcon(icon);
            }

        } catch (JavaLayerException | java.io.IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void nextBaiHat() {
        if (position == dsBaiHat.size() - 1 && isLoop == false) {
            return;
        }
        currentTime = 0;

        position = (position + 1) % dsBaiHat.size();

        initMusicPlayer(dsBaiHat, position, typeMusic);
    }

    public static void preBaiHat() {
        if (position == 0 - 1 && isLoop == false) {
            return;
        }
        currentTime = 0;

        position = (position - 1 + dsBaiHat.size()) % dsBaiHat.size();
        initMusicPlayer(dsBaiHat, position, typeMusic);
    }

    public static void getListRandom() {

        String idCurrentBH = "";
        if (dsBaiHat != null && dsBaiHat.size() != 0) {
            idCurrentBH = dsBaiHat.get(position).getId();
        }

        String[] minusId = new String[]{"asdf", idCurrentBH};

        ApiServiceV1.apiServiceV1.getListRandom(20, minusId).enqueue(new Callback<GetListBaiHat>() {
            @Override
            public void onResponse(Call<GetListBaiHat> call, Response<GetListBaiHat> rspns) {
                GetListBaiHat res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    ArrayList<BaiHat> newList = res.getData();
                    newList.addFirst(dsBaiHat.get(position));

                    dsBaiHat = newList;
                    position = 0;

                    // reset phat ke tiep
                    if (MainJFrame.isMenuPhatKeTiep) {

                        MainJFrame.PanelWrap.remove(1);
                        JPanel phatKeTiep = new PhatKeTiepPanel();
                        MainJFrame.PanelWrap.add(phatKeTiep, BorderLayout.EAST);
                        MainJFrame.PanelWrap.revalidate();
                        MainJFrame.PanelWrap.repaint();

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetListBaiHat> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet.");
                // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public static void themBaiHat(BaiHat bh) {
        if (dsBaiHat == null) {
            return;
        }
        dsBaiHat.add(bh);
        if (MainJFrame.isMenuPhatKeTiep) {

            MainJFrame.PanelWrap.remove(1);
            JPanel phatKeTiep = new PhatKeTiepPanel();
            MainJFrame.PanelWrap.add(phatKeTiep, BorderLayout.EAST);
            MainJFrame.PanelWrap.revalidate();
            MainJFrame.PanelWrap.repaint();

        }
    }
}
