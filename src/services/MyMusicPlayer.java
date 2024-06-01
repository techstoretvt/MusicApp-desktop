/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import helpers.LocalData;
import api.ApiServiceV1;
import model.BaiHat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.io.FileUtils;
import frame.MainJFrame;
import model.GetListBaiHat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.math.BigDecimal;
import javax.swing.JPanel;
import component.ItemMusic;
import component.KaraokePanel;
import component.PhatKeTiepPanel;
import helpers.Utils;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import observer.PhatKeTiepObserver;
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
    public static Thread threadPlay;
    public static Thread threadCheckPlay;

    public static double currentTime = 0;
    public static int totalTime = 10000;
    public static int currentPositionScroll = 0;
    public static boolean isLoop = false;
    public static boolean isRandom = false;
    public static boolean loadingPlay = false;

    public static BigDecimal timeCurrentLoiBaiHat = new BigDecimal("0.0");

    public static String typeMusic = "on";

    public static void initMusicPlayer(ArrayList<BaiHat> list, int pos) {
        String type = "on";
        initMusicPlayer(list, pos, type);
    }

    public static void initMusicPlayer(ArrayList<BaiHat> list, int pos, String type) {
        loadingPlay = true;
        dsBaiHat = list;
        position = pos;
        currentTime = 0;
        typeMusic = type;
        if (myThread != null && myThread.isAlive()) {
            System.out.println("Ngung");
            myThread.interrupt();
        }
        if (threadCheckPlay != null && threadCheckPlay.isAlive()) {
            threadCheckPlay.interrupt();
        }
        if (threadPlay != null && threadPlay.isAlive()) {
            threadPlay.interrupt();
        }
        // reset player
        if (player != null) {
            player.close();
            pauseLocation = 0;
            songTotalLength = 0;
        }
        BaiHat currentBH = dsBaiHat.get(position);
        totalTime = (int) (currentBH.getThoiGian() / 1000);
        updateUI(currentBH, type);

        //play
        threadPlay = new Thread(() -> {
            try {
                if (type.equals("on")) {
                    // download
                    URL songURL = new URL(currentBH.getLinkBaiHat());

                    File destination = new File(file_path_music);
                    FileUtils.copyURLToFile(songURL, destination);
                }

                String file_path = file_path_music;
                if (type.equals("off")) {
                    file_path = Utils.getUrlBHDownload(currentBH.getId());
                }

                fileInputStream = new FileInputStream(file_path);
                player = new Player(fileInputStream);
                songTotalLength = fileInputStream.available();

                fileInputStream.skip(0);

                Thread.sleep(500);

                MyMusicPlayer.isPlay = true;
                addDaNghe(currentBH);

                player.play();

            } catch (JavaLayerException e) {
                System.out.println("Loi Play" + e.getMessage());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MyMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MyMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {

            }
        });
        threadPlay.start();

        threadCheckPlay = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    if (MyMusicPlayer.player != null && MyMusicPlayer.player.getPosition() != 0) {
                        loadingPlay = false;
                        initThreadPlay();
                        break;
                    }

                } catch (InterruptedException ex) {
                    break;
                }
            }

        });
        threadCheckPlay.start();
    }

    public static void initThreadPlay() {
        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    // su kien phat xong
                    if (totalTime == currentTime) {
                        myThread.interrupt();
                        isPlay = false;
                        MainJFrame.subjectBtnMusic.notifyObservers();
                        if (player != null) {
                            player.close();
                        }

                        // next nhac
                        nextBaiHat();

                        break;
                    }

                    // set progess
                    if (MyMusicPlayer.isPlay) {
                        currentTime += 0.1;
                        currentTime = Math.round(currentTime * 10.0) / 10.0;
                        int tgHienTai = (int) Math.floor(currentTime);

                        if (MainJFrame.progessTimeBaiHat != null) {
                            MainJFrame.progessTimeBaiHat.setValue(tgHienTai);
                        }

                        if (MainJFrame.lbThoiGianHienTai != null) {
                            String tgHienTaiStr = Utils.getThoiGianBaiHat(tgHienTai);
                            MainJFrame.lbThoiGianHienTai.setText(tgHienTaiStr);
                        }

                        if (MainJFrame.isKaraoke && KaraokePanel.dsItemLoiBH != null
                                && KaraokePanel.dsItemLoiBH.size() != 0
                                && KaraokePanel.listIndexLoiBaiHat != null) {

                            int heightPan = MainJFrame.heightPanelContent / 2;

                            Integer indexLoiBH = KaraokePanel.listIndexLoiBaiHat.get(String.valueOf(currentTime));
                            Integer indexLoiBHNext = KaraokePanel.listIndexLoiBaiHat.get(String.valueOf(currentTime + 0.5));
                            
                            if (indexLoiBHNext != null) {
                                 new Thread(() -> {
                                    int i = 0;
                                    while (i < 100) {
                                        try {
                                            if (i >= 75 && i <= 85) {
                                                Thread.sleep(10);
                                            } else if (i >= 85) {
                                                Thread.sleep(40);
                                            }

                                            i++;

                                            Rectangle rect = new Rectangle(0, heightPan / 100 * i, 0, 0);

                                            KaraokePanel.dsItemLoiBH.get(indexLoiBHNext).scrollRectToVisible(rect);

                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(MyMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
                                            break;
                                        }
                                    }

                                }).start();
                            }
                            
                            if (indexLoiBH != null) {

                                int lastIndex = KaraokePanel.currentIndexLoiBH;

//                                new Thread(() -> {
//                                    int i = 0;
//                                    while (i < 100) {
//                                        try {
//                                            if (i >= 75 && i <= 85) {
//                                                Thread.sleep(10);
//                                            } else if (i >= 85) {
//                                                Thread.sleep(40);
//                                            }
//
//                                            i++;
//
//                                            Rectangle rect = new Rectangle(0, heightPan / 100 * i, 0, 0);
//
//                                            KaraokePanel.dsItemLoiBH.get(indexLoiBH).scrollRectToVisible(rect);
//
//                                        } catch (InterruptedException ex) {
//                                            Logger.getLogger(MyMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
//                                            break;
//                                        }
//                                    }
//
//                                }).start();

                                KaraokePanel.dsItemLoiBH.get(lastIndex).setForeground(Color.WHITE);

                                KaraokePanel.dsItemLoiBH.get(indexLoiBH).setForeground(Color.YELLOW);

                                KaraokePanel.currentIndexLoiBH = indexLoiBH;

                            }

                        }
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        break;
                    }

                }
            }
        });
        myThread.start();
    }

    public static void addDaNghe(BaiHat currentBH) {
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
                if (!bh.getId().equals(currentBH.getId())) {
                    newListDaNghe.add(bh);
                }
            }

            newListDaNghe.addFirst(currentBH);
            if (newListDaNghe.size() >= 10) {
                newListDaNghe.removeLast();
            }
            LocalData.saveListDaNghe(newListDaNghe);

        }).start();
    }

    public static void updateUI(BaiHat currentBH, String type) {

        new Thread(() -> {

            if (MainJFrame.lbThoiGianHienTai != null) {
                MainJFrame.lbThoiGianHienTai.setText("0:00");
            }

            if (MainJFrame.progessTimeBaiHat != null) {
                MainJFrame.progessTimeBaiHat.setValue(0);
            }

            if (ItemMusic.anhNhac != null) {
                String urlAnh = currentBH.getAnhBia();
                ImageIcon anhBH = Utils.getImageBaiHat(urlAnh, 50, 50);
                ItemMusic.anhNhac.setIcon(anhBH);

//                ItemMusic.anhNhac = null;
            }

            // get current time
            if (MainJFrame.progessTimeBaiHat != null) {
                MainJFrame.progessTimeBaiHat.setMaximum((int) (currentBH.getThoiGian() / 1000));
            }

            MainJFrame.subject.notifyObservers();

        }).start();

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
        if (loadingPlay) {
            return;
        }
        loadingPlay = false;
        if (player != null) {
            try {
                pauseLocation = fileInputStream.available();

                player.close();
                isPlay = false;

                MainJFrame.subjectBtnMusic.notifyObservers();

            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
            }
        }
    }

    public static void resume() {
        if (loadingPlay) {
            return;
        }
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
            if (player == null || loadingPlay) {
                return;
            }
            player.close();

            BaiHat currentBH = dsBaiHat.get(position);
            String file_path = file_path_music;
            if (typeMusic.equals("off")) {
                file_path = Utils.getUrlBHDownload(currentBH.getId());
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

            MainJFrame.subjectBtnMusic.notifyObservers();

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

        PhatKeTiepPanel.isUpdate = false;
        initMusicPlayer(dsBaiHat, position, typeMusic);
    }

    public static void preBaiHat() {
        if (position == 0 - 1 && isLoop == false) {
            return;
        }
        currentTime = 0;

        position = (position - 1 + dsBaiHat.size()) % dsBaiHat.size();
        PhatKeTiepPanel.isUpdate = false;
        initMusicPlayer(dsBaiHat, position, typeMusic);
    }

    public static void getListRandom(PhatKeTiepObserver phatKeTiepObserver) {

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

                    if (phatKeTiepObserver != null) {
                        phatKeTiepObserver.update();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetListBaiHat> call, Throwable thrwbl) {
                loadingPlay = false;
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
