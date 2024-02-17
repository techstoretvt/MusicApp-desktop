/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import gson.GetTaiKhoan;
import gson.TaiKhoan;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import panels.ChiTietCaSiPanel;
import panels.ChiTietMVPanel;
import panels.ChiTietPlaylistPanel;
import panels.DaTaiPanel;
import panels.KaraokePanel;
import panels.KhamPhaPanel;
import panels.ListLivePanel;
import panels.LivePanel;
import panels.NoiBatPanel;
import panels.PhatKeTiepPanel;
import panels.SettingPanel;
import panels.ThuVienPanel;
import panels.TimKiemPanel;
import panels.YeuThichPanel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.ApiServiceV1;
import services.AppConstants;
import services.LocalData;
import services.MyMusicPlayer;
import services.MySocketClient;
import services.utils;

/**
 *
 * @author tranv
 */
public class MainJFrame extends javax.swing.JFrame {

    public static JPanel cardPanel;
    public static CardLayout cardLayout;
    public static boolean isMenuPhatKeTiep = false;
    public static boolean isKaraoke = false;
    public static String oldPanel = "";
    public static Stack<String> historyPanel = new Stack<>(); //.pop() .peek() .push()
    public static boolean isLogin = false;
    public static TaiKhoan userInfo;

    public static Browser browser;
    public static Socket mSocket_DKGionNoi;

    /**
     * Creates new form HomeJFrame
     */
    public MainJFrame() {
        initComponents();

        initPanelContent();

//        PanelFooter.setVisible(false);
        // test
//        LocalData.removeData("accessToken");
        utils.CheckLogin();

        getUserLogin();

        initSocketDkBangDienThoai();
    }

    public void initSocketDkBangDienThoai() {
        Socket mSocket = MySocketClient.getSocket();

        String key = "thoaidev";

        mSocket.on("next-music-mobile-" + key, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                // Xử lý dữ liệu từ máy chủ ở đây
                MyMusicPlayer.nextBaiHat();

            }
        });

        mSocket.on("prev-music-mobile-" + key, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                // Xử lý dữ liệu từ máy chủ ở đây
                MyMusicPlayer.preBaiHat();

            }
        });

    }

    public void initPanelContent() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // add panel
        ShowPanel("KhamPha", null);

        // add vao Frame
        PanelContent.add(cardPanel, BorderLayout.CENTER);
    }

    public static void getUserLogin() {
        String header = utils.getHeader();

        ApiServiceV1.apiServiceV1.getTaiKhoan(header).enqueue(new Callback<GetTaiKhoan>() {
            @Override
            public void onResponse(Call<GetTaiKhoan> call, Response<GetTaiKhoan> rspns) {
                GetTaiKhoan res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    TaiKhoan user = res.getData();
                    userInfo = user;
                    String urlAnh = utils.getAnhUser(user);
                    System.out.println("url anh: " + urlAnh);

                    ImageIcon avatar = utils.getImageBaiHat(urlAnh, 40, 40);

                    btnAvatar.setIcon(avatar);

                } else {
                    System.out.println("Khong tim thay tai khoan");
                    ImageIcon avatar = new ImageIcon(getClass().getResource("/icon/icon-user.png"));
                    btnAvatar.setIcon(avatar);
                }
            }

            @Override
            public void onFailure(Call<GetTaiKhoan> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public static void ShowPanel(String name, JPanel pn) {
        if (!historyPanel.empty() && historyPanel.peek().equals(name)) {
            resetPanel();
            return;
        }
        historyPanel.push(name);

        isKaraoke = false;
        ImageIcon iconKaraoke = new ImageIcon(MainJFrame.class.getResource("/icon/micro.png"));
        btnKaraoke.setIcon(iconKaraoke);

        if (historyPanel.size() > 1) {
            ImageIcon iconBack = new ImageIcon(MainJFrame.class.getResource("/icon/icon-arrow-back-active.png"));
            btnBack.setIcon(iconBack);
        } else {
            ImageIcon iconBack = new ImageIcon(MainJFrame.class.getResource("/icon/icon-arrow-back-disable.png"));
            btnBack.setIcon(iconBack);
        }

        JPanel newPN = pn;
        if (pn == null) {
            newPN = getPanel(name);
        }

        cardPanel.add(newPN, name);
        cardLayout.show(cardPanel, name);
    }

    public static void ShowPanelNoHistory(String name, JPanel pn) {
        isKaraoke = false;
        ImageIcon iconKaraoke = new ImageIcon(MainJFrame.class.getResource("/icon/micro.png"));
        btnKaraoke.setIcon(iconKaraoke);

        JPanel newPN = pn;
        if (pn == null) {
            newPN = getPanel(name);
        }

        cardPanel.add(newPN, name);
        cardLayout.show(cardPanel, name);
    }

    public static JPanel getPanel(String name) {
        JPanel newPN = null;
        switch (name) {
            case "KhamPha":
                newPN = new KhamPhaPanel();
                break;
            case "ThuVien":
                newPN = new ThuVienPanel();
                break;
            case "Karaoke":
                newPN = new KaraokePanel();
                break;
            case "NoiBat":
                newPN = new NoiBatPanel();
                break;
            case "TimKiem":
                newPN = new TimKiemPanel(TimKiemPanel.keyword);
                break;
            case "ChiTietCaSi":
                newPN = new ChiTietCaSiPanel(ChiTietCaSiPanel.idCaSi);
                break;
            case "ChiTietMV":
                newPN = new ChiTietMVPanel(ChiTietMVPanel.idMV);
                break;
            case "ChiTietPlaylist":
                newPN = new ChiTietPlaylistPanel(ChiTietPlaylistPanel.idPlaylist);
                break;
            case "YeuThich":
                newPN = new YeuThichPanel();
                break;
            case "DaTai":
                newPN = new DaTaiPanel();
                break;
            case "Setting":
                newPN = new SettingPanel();
                break;
            case "ListLive":
                newPN = new ListLivePanel();
                break;
            default:
                newPN = new KhamPhaPanel();
        }

        return newPN;
    }

    public static void resetPanel() {

        if (historyPanel.empty()) {
            return;
        }
        String namePanel = MainJFrame.historyPanel.peek();

        JPanel pn = getPanel(namePanel);
        cardPanel.add(pn, namePanel);
        cardLayout.show(cardPanel, namePanel);
    }

    public static void goBackPanel() {
      
        String currentPanel;
        String oldPanel = null;
        try {
            currentPanel = historyPanel.pop();
            oldPanel = historyPanel.peek();
        } catch (Exception e) {
            System.out.println("loi go back");
        }
        
        if (oldPanel == null) {
            return;
        }

        cardLayout.show(cardPanel, oldPanel);

        if (historyPanel.size() > 1) {
            ImageIcon iconBack = new ImageIcon(MainJFrame.class.getResource("/icon/icon-arrow-back-active.png"));
            btnBack.setIcon(iconBack);
        } else {
            ImageIcon iconBack = new ImageIcon(MainJFrame.class.getResource("/icon/icon-arrow-back-disable.png"));
            btnBack.setIcon(iconBack);
        }
    }

    public static void ToggleShowKaraoke() {

        if (isKaraoke) {
            ImageIcon icon = new ImageIcon(MainJFrame.class.getResource("/icon/micro.png"));
            btnKaraoke.setIcon(icon);

            String oldPanel = historyPanel.peek();
            cardLayout.show(cardPanel, oldPanel);

        } else {
            ImageIcon icon = new ImageIcon(MainJFrame.class.getResource("/icon/micro-active.png"));
            btnKaraoke.setIcon(icon);

            JPanel pn = new KaraokePanel();

            cardPanel.add(pn, "Karaoke");
            cardLayout.show(cardPanel, "Karaoke");
        }

        isKaraoke = !isKaraoke;

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
        PanelTabBar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnKhamPha = new javax.swing.JButton();
        btnThuVien = new javax.swing.JButton();
        btnLive = new javax.swing.JButton();
        btnDaTai = new javax.swing.JButton();
        btnYeuThich = new javax.swing.JButton();
        PanelFooter = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        imageBaiHat = new javax.swing.JLabel();
        lbTenBaiHat = new javax.swing.JLabel();
        lbTenCaSi = new javax.swing.JLabel();
        btnMore = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lbThoiGianHienTai = new javax.swing.JLabel();
        lbTongThoiGian = new javax.swing.JLabel();
        progessTimeBaiHat = new javax.swing.JProgressBar();
        btnRandom = new javax.swing.JButton();
        btnLoop = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnPrevBaiHat = new javax.swing.JButton();
        btnPlayPause = new javax.swing.JButton();
        btnNextMusic = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        progessVolume = new javax.swing.JProgressBar();
        jButton13 = new javax.swing.JButton();
        btnMenuPhatKeTiep = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnKaraoke = new javax.swing.JButton();
        PannelContainer = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        btnAvatar = new javax.swing.JButton();
        btnSetting = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        ipTimKiem = new javax.swing.JTextField();
        PanelWrap = new javax.swing.JPanel();
        PanelContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1600, 800));

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        PanelTabBar.setBackground(new java.awt.Color(51, 0, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\anh-bia-am-nhac2.jpg")); // NOI18N
        jLabel1.setText("  Music App");

        btnKhamPha.setBackground(new java.awt.Color(102, 102, 102));
        btnKhamPha.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnKhamPha.setForeground(new java.awt.Color(255, 255, 255));
        btnKhamPha.setText("Khám phá");
        btnKhamPha.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnKhamPha.setBorderPainted(false);
        btnKhamPha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKhamPha.setFocusPainted(false);
        btnKhamPha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhamPhaActionPerformed(evt);
            }
        });

        btnThuVien.setBackground(new java.awt.Color(51, 0, 51));
        btnThuVien.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnThuVien.setForeground(new java.awt.Color(255, 255, 255));
        btnThuVien.setText("Thư viện");
        btnThuVien.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnThuVien.setBorderPainted(false);
        btnThuVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThuVien.setFocusPainted(false);
        btnThuVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuVienActionPerformed(evt);
            }
        });

        btnLive.setBackground(new java.awt.Color(51, 0, 51));
        btnLive.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnLive.setForeground(new java.awt.Color(255, 255, 255));
        btnLive.setText("Live");
        btnLive.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnLive.setBorderPainted(false);
        btnLive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLive.setFocusPainted(false);
        btnLive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiveActionPerformed(evt);
            }
        });

        btnDaTai.setBackground(new java.awt.Color(51, 0, 51));
        btnDaTai.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnDaTai.setForeground(new java.awt.Color(255, 255, 255));
        btnDaTai.setText("Nhạc đã tải");
        btnDaTai.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnDaTai.setBorderPainted(false);
        btnDaTai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDaTai.setFocusPainted(false);
        btnDaTai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaTaiActionPerformed(evt);
            }
        });

        btnYeuThich.setBackground(new java.awt.Color(51, 0, 51));
        btnYeuThich.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnYeuThich.setForeground(new java.awt.Color(255, 255, 255));
        btnYeuThich.setText("Yêu  thích");
        btnYeuThich.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnYeuThich.setBorderPainted(false);
        btnYeuThich.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnYeuThich.setFocusPainted(false);
        btnYeuThich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYeuThichActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelTabBarLayout = new javax.swing.GroupLayout(PanelTabBar);
        PanelTabBar.setLayout(PanelTabBarLayout);
        PanelTabBarLayout.setHorizontalGroup(
            PanelTabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTabBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelTabBarLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addComponent(btnKhamPha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThuVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDaTai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnYeuThich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelTabBarLayout.setVerticalGroup(
            PanelTabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTabBarLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnKhamPha, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThuVien, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLive, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDaTai, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnYeuThich, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jPanel1.add(PanelTabBar, java.awt.BorderLayout.LINE_START);

        PanelFooter.setBackground(new java.awt.Color(0, 0, 0));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        imageBaiHat.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\anh-bia-am-nhac2.jpg")); // NOI18N

        lbTenBaiHat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTenBaiHat.setForeground(new java.awt.Color(255, 255, 255));
        lbTenBaiHat.setText("Đi về nhàĐi về nhàĐi về nhà");
        lbTenBaiHat.setMaximumSize(new java.awt.Dimension(200, 20));
        lbTenBaiHat.setMinimumSize(new java.awt.Dimension(150, 22));

        lbTenCaSi.setForeground(new java.awt.Color(204, 204, 204));
        lbTenCaSi.setText("Đen Vâu");
        lbTenCaSi.setMaximumSize(new java.awt.Dimension(450, 16));

        btnMore.setBackground(new java.awt.Color(0, 0, 0));
        btnMore.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\Icons\\icons8-more-30.png")); // NOI18N
        btnMore.setBorder(null);
        btnMore.setFocusPainted(false);
        btnMore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbTenBaiHat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTenCaSi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMore)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnMore)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(lbTenBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbTenCaSi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        lbThoiGianHienTai.setForeground(new java.awt.Color(255, 255, 255));
        lbThoiGianHienTai.setText("0:13");

        lbTongThoiGian.setForeground(new java.awt.Color(255, 255, 255));
        lbTongThoiGian.setText("5:11");

        progessTimeBaiHat.setValue(50);
        progessTimeBaiHat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                progessTimeBaiHatMouseClicked(evt);
            }
        });

        btnRandom.setBackground(new java.awt.Color(0, 0, 0));
        btnRandom.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\Icons\\icons8-random-30.png")); // NOI18N
        btnRandom.setBorder(null);
        btnRandom.setFocusPainted(false);
        btnRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomActionPerformed(evt);
            }
        });

        btnLoop.setBackground(new java.awt.Color(0, 0, 0));
        btnLoop.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\Icons\\icons8-loop-30.png")); // NOI18N
        btnLoop.setBorder(null);
        btnLoop.setFocusPainted(false);
        btnLoop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoopActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));

        btnPrevBaiHat.setBackground(new java.awt.Color(0, 0, 0));
        btnPrevBaiHat.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\Icons\\prev.png")); // NOI18N
        btnPrevBaiHat.setBorder(null);
        btnPrevBaiHat.setFocusPainted(false);
        btnPrevBaiHat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevBaiHatActionPerformed(evt);
            }
        });
        jPanel6.add(btnPrevBaiHat);

        btnPlayPause.setBackground(new java.awt.Color(0, 0, 0));
        btnPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-pause.png"))); // NOI18N
        btnPlayPause.setBorder(null);
        btnPlayPause.setFocusPainted(false);
        btnPlayPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseActionPerformed(evt);
            }
        });
        jPanel6.add(btnPlayPause);

        btnNextMusic.setBackground(new java.awt.Color(0, 0, 0));
        btnNextMusic.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\Icons\\icons8-next-30.png")); // NOI18N
        btnNextMusic.setBorder(null);
        btnNextMusic.setFocusPainted(false);
        btnNextMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextMusicActionPerformed(evt);
            }
        });
        jPanel6.add(btnNextMusic);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnRandom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoop))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lbThoiGianHienTai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progessTimeBaiHat, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTongThoiGian)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRandom, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLoop, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(progessTimeBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbThoiGianHienTai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbTongThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        jPanel3.add(jPanel5);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        progessVolume.setForeground(new java.awt.Color(0, 204, 51));
        progessVolume.setValue(90);
        progessVolume.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                progessVolumeMouseDragged(evt);
            }
        });
        progessVolume.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                progessVolumeMouseClicked(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(0, 0, 0));
        jButton13.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\Icons\\icons8-speaker-30.png")); // NOI18N
        jButton13.setBorder(null);
        jButton13.setFocusPainted(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        btnMenuPhatKeTiep.setBackground(new java.awt.Color(0, 0, 0));
        btnMenuPhatKeTiep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/menu-music.png"))); // NOI18N
        btnMenuPhatKeTiep.setBorder(null);
        btnMenuPhatKeTiep.setFocusPainted(false);
        btnMenuPhatKeTiep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuPhatKeTiepActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnKaraoke.setBackground(new java.awt.Color(0, 0, 0));
        btnKaraoke.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/micro.png"))); // NOI18N
        btnKaraoke.setBorder(null);
        btnKaraoke.setFocusPainted(false);
        btnKaraoke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKaraokeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(122, Short.MAX_VALUE)
                .addComponent(btnKaraoke)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progessVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMenuPhatKeTiep)
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnKaraoke)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuPhatKeTiep)
                    .addComponent(jButton13)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(progessVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel2);

        javax.swing.GroupLayout PanelFooterLayout = new javax.swing.GroupLayout(PanelFooter);
        PanelFooter.setLayout(PanelFooterLayout);
        PanelFooterLayout.setHorizontalGroup(
            PanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelFooterLayout.setVerticalGroup(
            PanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFooterLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(PanelFooter, java.awt.BorderLayout.PAGE_END);

        PannelContainer.setBackground(new java.awt.Color(23, 15, 35));

        jPanel9.setBackground(new java.awt.Color(23, 15, 35));

        btnBack.setBackground(new java.awt.Color(23, 15, 35));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-arrow-back-disable.png"))); // NOI18N
        btnBack.setBorder(null);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tìm kiếm");

        btnTimKiem.setText("Tìm");
        btnTimKiem.setBorder(null);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnAvatar.setBackground(new java.awt.Color(0, 0, 51));
        btnAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-user.png"))); // NOI18N
        btnAvatar.setBorder(null);
        btnAvatar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAvatar.setFocusPainted(false);
        btnAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvatarActionPerformed(evt);
            }
        });

        btnSetting.setBackground(new java.awt.Color(23, 15, 35));
        btnSetting.setIcon(new javax.swing.ImageIcon("D:\\Media\\Image\\Musicapp\\Icons\\icons8-setting-30.png")); // NOI18N
        btnSetting.setBorder(null);
        btnSetting.setFocusPainted(false);
        btnSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        ipTimKiem.setBorder(null);
        ipTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ipTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ipTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnBack)
                .addGap(47, 47, 47)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addComponent(btnSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(btnBack))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        PanelWrap.setBackground(new java.awt.Color(23, 15, 35));
        PanelWrap.setLayout(new java.awt.BorderLayout());

        PanelContent.setBackground(new java.awt.Color(23, 15, 35));
        PanelContent.setLayout(new java.awt.BorderLayout());
        PanelWrap.add(PanelContent, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout PannelContainerLayout = new javax.swing.GroupLayout(PannelContainer);
        PannelContainer.setLayout(PannelContainerLayout);
        PannelContainerLayout.setHorizontalGroup(
            PannelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelWrap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PannelContainerLayout.setVerticalGroup(
            PannelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PannelContainerLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelWrap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PannelContainer, java.awt.BorderLayout.CENTER);

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

    private void progessTimeBaiHatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progessTimeBaiHatMouseClicked
        // TODO add your handling code here:
        int mouseX = evt.getX();
        int progressBarWidth = progessTimeBaiHat.getWidth();
        float percentage = (float) mouseX / progressBarWidth;
        System.out.println("Width" + String.valueOf(progressBarWidth));
        System.out.println("vi tri: " + String.valueOf(mouseX));
        System.out.println("data: " + String.valueOf(percentage));

        MyMusicPlayer.setTimeBaiHat(percentage);


    }//GEN-LAST:event_progessTimeBaiHatMouseClicked

    private void btnKhamPhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhamPhaActionPerformed
        // TODO add your handling code here:
        ShowPanel("KhamPha", new KhamPhaPanel());
        resetTabBarColor();
        btnKhamPha.setBackground(new Color(102, 102, 102));

    }//GEN-LAST:event_btnKhamPhaActionPerformed

    private void btnThuVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuVienActionPerformed
        // TODO add your handling code here:
        if (!utils.getIsLogin()) {
            return;
        }

        ShowPanel("ThuVien", new ThuVienPanel());
        resetTabBarColor();
        btnThuVien.setBackground(new Color(102, 102, 102));

    }//GEN-LAST:event_btnThuVienActionPerformed

    private void btnLiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiveActionPerformed
        // TODO add your handling code here:
        ShowPanel("ListLive", new ListLivePanel());
        resetTabBarColor();
        btnLive.setBackground(new Color(102, 102, 102));

    }//GEN-LAST:event_btnLiveActionPerformed

    private void btnMoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoreActionPerformed
        // TODO add your handling code here:
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem option1 = new JMenuItem("Option 1");
        JMenuItem option2 = new JMenuItem("Option 2");
        JMenuItem option3 = new JMenuItem("Option 3");

        popupMenu.add(option1);
        popupMenu.add(option2);
        popupMenu.add(option3);

        popupMenu.show(btnMore, 0, -popupMenu.getHeight() - 70);
    }//GEN-LAST:event_btnMoreActionPerformed

    private void btnPlayPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseActionPerformed
        // TODO add your handling code here:

        if (MyMusicPlayer.isPlay) {
            MyMusicPlayer.pause();

        } else {
            MyMusicPlayer.resume();
        }

    }//GEN-LAST:event_btnPlayPauseActionPerformed

    private void progessVolumeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progessVolumeMouseClicked
        // TODO add your handling code here:
        int mouseX = evt.getX();
        int progressBarWidth = progessVolume.getWidth();
        float percentage = (float) mouseX / progressBarWidth;

        int newData = (int) (percentage * 100);
        MyMusicPlayer.setVolume(newData);
    }//GEN-LAST:event_progessVolumeMouseClicked

    private void progessVolumeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progessVolumeMouseDragged
        // TODO add your handling code here:
        int mouseX = evt.getX();
        int progressBarWidth = progessVolume.getWidth();
        float percentage = (float) mouseX / progressBarWidth;

        int newData = (int) (percentage * 100);
        MyMusicPlayer.setVolume(newData);
    }//GEN-LAST:event_progessVolumeMouseDragged

    private void btnNextMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextMusicActionPerformed
        // TODO add your handling code here:
        MyMusicPlayer.nextBaiHat();
    }//GEN-LAST:event_btnNextMusicActionPerformed

    private void btnPrevBaiHatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevBaiHatActionPerformed
        // TODO add your handling code here:
        MyMusicPlayer.preBaiHat();
    }//GEN-LAST:event_btnPrevBaiHatActionPerformed

    private void btnLoopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoopActionPerformed
        // TODO add your handling code here:
        if (MyMusicPlayer.isLoop) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-loop.png"));
            btnLoop.setIcon(icon);
        } else {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-loop-active.png"));
            btnLoop.setIcon(icon);
        }
        MyMusicPlayer.isLoop = !MyMusicPlayer.isLoop;


    }//GEN-LAST:event_btnLoopActionPerformed

    private void btnRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomActionPerformed
        // TODO add your handling code here:
        if (MyMusicPlayer.isRandom) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-random.png"));
            btnRandom.setIcon(icon);
        } else {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/icon-random-active.png"));
            btnRandom.setIcon(icon);
            MyMusicPlayer.getListRandom();
        }
        MyMusicPlayer.isRandom = !MyMusicPlayer.isRandom;
    }//GEN-LAST:event_btnRandomActionPerformed


    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String keyword = ipTimKiem.getText();
        if (!keyword.isEmpty()) {
            ShowPanel("TimKiem", new TimKiemPanel(keyword));
        }


    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void btnMenuPhatKeTiepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuPhatKeTiepActionPerformed
        // TODO add your handling code here:
        if (isMenuPhatKeTiep) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/menu-music.png"));
            btnMenuPhatKeTiep.setIcon(icon);

            JPanel phatKeTiep = new PhatKeTiepPanel();
            PanelWrap.remove(1);
            PanelWrap.revalidate();
            PanelWrap.repaint();
        } else {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/menu-music-active.png"));
            btnMenuPhatKeTiep.setIcon(icon);

            JPanel phatKeTiep = new PhatKeTiepPanel();
            PanelWrap.add(phatKeTiep, BorderLayout.EAST);
            PanelWrap.revalidate();
            PanelWrap.repaint();
        }
        isMenuPhatKeTiep = !isMenuPhatKeTiep;

    }//GEN-LAST:event_btnMenuPhatKeTiepActionPerformed

    private void btnKaraokeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKaraokeActionPerformed
        // TODO add your handling code here:
//        if (isKaraoke) {
//            ShowPanel(oldPanel, null);
//            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/micro.png"));
//            btnKaraoke.setIcon(icon);
//            isKaraoke = false;
//
//        } else {
//            ShowPanel("Karaoke", new KaraokePanel());
//            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/micro-active.png"));
//            btnKaraoke.setIcon(icon);
//            isKaraoke = true;
//        }

        ToggleShowKaraoke();

    }//GEN-LAST:event_btnKaraokeActionPerformed

    private void ipTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipTimKiemActionPerformed
        // TODO add your handling code here:
        String keyword = ipTimKiem.getText();
        if (!keyword.isEmpty()) {
            ShowPanel("TimKiem", new TimKiemPanel(keyword));
        }
    }//GEN-LAST:event_ipTimKiemActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        goBackPanel();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvatarActionPerformed
        // TODO add your handling code here:
        if (!utils.getIsLogin()) {
            return;
        }

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem option1 = new JMenuItem("Đăng xuất");

        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalData.removeData("accessToken");
                MainJFrame.isLogin = false;
                getUserLogin();

                MainJFrame.historyPanel.clear();
                MainJFrame.ShowPanel("KhamPha", null);
            }
        });

        popupMenu.add(option1);

        JMenuItem option2 = new JMenuItem("Phát trực tuyến");

        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = userInfo.getFirstName() + " " + userInfo.getLastName();

                MainJFrame.ShowPanel("Live", new LivePanel(name, utils.getAnhUser(userInfo)));
            }
        });

        popupMenu.add(option2);

        popupMenu.show(btnAvatar, -30, popupMenu.getHeight() + 45);

    }//GEN-LAST:event_btnAvatarActionPerformed

    private void btnYeuThichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYeuThichActionPerformed
        // TODO add your handling code here:
        if (!utils.getIsLogin()) {
            return;
        }

        ShowPanel("YeuThich", new YeuThichPanel());
        resetTabBarColor();
        btnYeuThich.setBackground(new Color(102, 102, 102));
    }//GEN-LAST:event_btnYeuThichActionPerformed

    private void btnDaTaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaTaiActionPerformed
        // TODO add your handling code here:
        ShowPanel("DaTai", null);
        resetTabBarColor();
        btnDaTai.setBackground(new Color(102, 102, 102));
    }//GEN-LAST:event_btnDaTaiActionPerformed

    private void btnSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingActionPerformed
        // TODO add your handling code here:
        ShowPanel("Setting", new SettingPanel());
    }//GEN-LAST:event_btnSettingActionPerformed

    public void resetTabBarColor() {
        btnKhamPha.setBackground(new Color(51, 0, 51));
        btnThuVien.setBackground(new Color(51, 0, 51));
        btnLive.setBackground(new Color(51, 0, 51));
        btnYeuThich.setBackground(new Color(51, 0, 51));
        btnDaTai.setBackground(new Color(51, 0, 51));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    public static void initDKGiongNoi() {
        Engine engine = Engine.newInstance(EngineOptions.newBuilder(RenderingMode.OFF_SCREEN)
                .licenseKey("1BNDHFSC1G8RA8PYZSNW76QPH8UUQU4FTUIGJE23Y1HZ5EEYND8BED4IUWACS012LYXTSS").build());
        browser = engine.newBrowser();
        BrowserView view = BrowserView.newInstance(browser);

        String key = utils.randomKeyLogin();
        String url = AppConstants.url_frontend + "/public/music/control?key=" + key;
        browser.navigation().loadUrlAndWait(url);

        // init websocket
        mSocket_DKGionNoi = MySocketClient.getSocket();

//        mSocket_DKGionNoi.on("next-music-desktop-" + key, new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                // Xử lý dữ liệu từ máy chủ ở đây
//                MyMusicPlayer.nextBaiHat();
//            }
//        });
    }

    public static void deleteDKGiongNoi() {
        if (browser != null) {
            browser.close();
        }
        if (mSocket_DKGionNoi != null) {
            mSocket_DKGionNoi.close();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContent;
    public static javax.swing.JPanel PanelFooter;
    private javax.swing.JPanel PanelTabBar;
    public static javax.swing.JPanel PanelWrap;
    private javax.swing.JPanel PannelContainer;
    public static javax.swing.JButton btnAvatar;
    public static javax.swing.JButton btnBack;
    private javax.swing.JButton btnDaTai;
    public static javax.swing.JButton btnKaraoke;
    private javax.swing.JButton btnKhamPha;
    private javax.swing.JButton btnLive;
    private javax.swing.JButton btnLoop;
    private javax.swing.JButton btnMenuPhatKeTiep;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnNextMusic;
    public static javax.swing.JButton btnPlayPause;
    private javax.swing.JButton btnPrevBaiHat;
    private javax.swing.JButton btnRandom;
    private javax.swing.JButton btnSetting;
    private javax.swing.JButton btnThuVien;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnYeuThich;
    public static javax.swing.JLabel imageBaiHat;
    private javax.swing.JTextField ipTimKiem;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JLabel lbTenBaiHat;
    public static javax.swing.JLabel lbTenCaSi;
    public static javax.swing.JLabel lbThoiGianHienTai;
    public static javax.swing.JLabel lbTongThoiGian;
    public static javax.swing.JProgressBar progessTimeBaiHat;
    public static javax.swing.JProgressBar progessVolume;
    // End of variables declaration//GEN-END:variables
}
