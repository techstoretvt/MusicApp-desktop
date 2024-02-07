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
import view.MainJFrame;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import gson.GetListBaiHat;
import java.awt.BorderLayout;
import javax.sound.sampled.FloatControl;
import javax.swing.JPanel;
import panels.ItemMusicPanel;
import panels.ItemPhatKeTiep;
import panels.PhatKeTiepPanel;
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

    public static void initMusicPlayer(ArrayList<BaiHat> list, int pos) {
        try {
            dsBaiHat = list;
            position = pos;
            currentTime = 0;

            if (myThread != null) {
                myThread.interrupt();
            }

            BaiHat currentBH = dsBaiHat.get(position);
            totalTime = (int) (currentBH.getThoiGian() / 1000);

            // download
            URL songURL = new URL(currentBH.getLinkBaiHat());

            File destination = new File(file_path_music);
            FileUtils.copyURLToFile(songURL, destination);

            // reset player
            if (player != null) {
                player.close();
                pauseLocation = 0;
                songTotalLength = 0;
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
                ImageIcon anhBH = utils.getImageBaiHat(currentBH.getAnhBia(), 50, 50);
                MainJFrame.imageBaiHat.setIcon(anhBH);
            }
            if (MainJFrame.lbTongThoiGian != null) {
                String tongTG = utils.getThoiGianBaiHat((int) (currentBH.getThoiGian() / 1000));
                MainJFrame.lbTongThoiGian.setText(tongTG);
            }
            if (MainJFrame.PanelFooter != null) {
                MainJFrame.PanelFooter.setVisible(true);
            }

            // add phat ke tiep
            if (MainJFrame.isMenuPhatKeTiep) {

                MainJFrame.PanelWrap.remove(1);
                JPanel phatKeTiep = new PhatKeTiepPanel();
                MainJFrame.PanelWrap.add(phatKeTiep, BorderLayout.EAST);
                MainJFrame.PanelWrap.revalidate();
                MainJFrame.PanelWrap.repaint();

            }

            //play
            try {
                fileInputStream = new FileInputStream(file_path_music);
                player = new Player(fileInputStream);
                songTotalLength = fileInputStream.available();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (JavaLayerException ex) {

            }

            isPlay = true;

            new Thread(() -> {
                try {
                    System.out.println("play ");
                    player.play();
                    System.out.println("Nhạc đã kết thúc!");
                } catch (JavaLayerException e) {
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
                    try {
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

                                System.out.println("phat xong");
                                break;
                            }

                            // set progess
                            if (isPlay) {
                                currentTime += 1;
                                System.out.println("tg Current: " + currentTime);

                                if (MainJFrame.progessTimeBaiHat != null) {
                                    MainJFrame.progessTimeBaiHat.setValue(currentTime);
                                }

                                if (MainJFrame.lbThoiGianHienTai != null) {
                                    String tgHienTai = utils.getThoiGianBaiHat(currentTime);
                                    MainJFrame.lbThoiGianHienTai.setText(tgHienTai);
                                }

                            }

                            Thread.sleep(1000);

                        }
                    } catch (InterruptedException ex) {
                        System.out.println("vao loi");
                        Logger.getLogger(MyMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            myThread.start();

        } catch (MalformedURLException ex) {
            Logger.getLogger(MyMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pause() {
        if (player != null) {
            try {
                pauseLocation = fileInputStream.available();

                //test
                System.out.println("pause: " + String.valueOf(pauseLocation) + " : " + String.valueOf(songTotalLength));
                System.out.println("time: " + String.valueOf(songTotalLength - pauseLocation));

                double tgBaiHat = dsBaiHat.get(position).getThoiGian();
                System.out.println("tg: " + String.valueOf(tgBaiHat));

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

            fileInputStream = new FileInputStream(file_path_music);

            BaiHat currentBH = dsBaiHat.get(position);
            int timeNext = (int) (currentBH.getThoiGian() / 1000 * percent);
            currentTime = timeNext;
            long timeSkip = (long) (timeNext * 15.97 * 1000);

            System.out.println("Tong tg: " + currentBH.getThoiGian() / 1000);
            System.out.println("tg next: " + timeNext);
            System.out.println("tong tg file: " + songTotalLength);
            System.out.println("tg skip: " + timeSkip);

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

        position = (position + 1) % dsBaiHat.size();
        initMusicPlayer(dsBaiHat, position);
    }

    public static void preBaiHat() {
        if (position == 0 - 1 && isLoop == false) {
            return;
        }

        position = (position - 1 + dsBaiHat.size()) % dsBaiHat.size();
        initMusicPlayer(dsBaiHat, position);
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
}
