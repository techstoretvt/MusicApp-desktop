/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import model.GetTaiKhoan;
import model.KeywordGoogle;
import model.TaiKhoan;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import screen.ChiTietCaSiPanel;
import screen.ChiTietMVPanel;
import screen.ChiTietPlaylistPanel;
import screen.DaTaiPanel;
import component.KaraokePanel;
import component.ModelHenGio;
import component.OptionBaiHat;
import screen.KhamPhaPanel;
import screen.ListLivePanel;
import screen.LivePanel;
import component.PhatKeTiepPanel;
import screen.SettingGiaoDien;
import screen.SettingPanel;
import component.ThemVaoPlaylist;
import component.ThongTinBaiHat;
import java.awt.FontMetrics;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import screen.ThuVienPanel;
import screen.TimKiemPanel;
import screen.YeuThichPanel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import api.ApiServiceV1;
import helpers.AppConstants;
import services.LocalData;
import component.MyCustomDialog;
import services.MyMusicPlayer;
import services.MySocketClient;
import helpers.utils;
import screen.MiniGame;
import screen.RadioPanel;

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
    public static String keyRemoteControl;

    private Thread threadTimKiem;
    private String textChuChay;

    /**
     * Creates new form HomeJFrame
     */
    public MainJFrame() {
//        setUndecorated(true);

        initComponents();
        setTitle("Music App - Nghe nhạc trực tuyến");
        ImageIcon ic = new ImageIcon(getClass().getResource("/icon/anh-bia-am-nhac2.jpg"));
        setIconImage(ic.getImage());

        initPanelContent();

        PanelFooter.setVisible(false);
        // test
//        LocalData.removeData("accessToken");
        utils.CheckLogin();

        getUserLogin();

        initSocketDkBangDienThoai();

        loadBackGroundColor();

        loadChuChay();

    }

    public void loadChuChay() {

        textChuChay = "Với ứng dụng nghe nhạc trực tuyến trên desktop này, bạn sẽ 'đắm chìm' trong không gian âm nhạc cực kỳ hài hước và sống động! ";

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    textChuChay = textChuChay.substring(1) + textChuChay.charAt(0);
                    tfChuChay.setText(textChuChay);
                } catch (InterruptedException ex) {

                }
            }
        }).start();
    }

    public void loadBackGroundColor() {
        String colorTabBar = LocalData.getData("ColorTabBar");
        String colorHeader = LocalData.getData("ColorHeader");
        String colorFooter = LocalData.getData("ColorFooter");

        if (colorTabBar != null && !colorTabBar.isEmpty()) {
            setBackgroundTabbar(new Color(Integer.parseInt(colorTabBar)));
        }
        if (colorHeader != null && !colorHeader.isEmpty()) {
            setBackgroundHeader(new Color(Integer.parseInt(colorHeader)));
        }
        if (colorFooter != null && !colorFooter.isEmpty()) {
            setBackgroundFooter(new Color(Integer.parseInt(colorFooter)));
        }

    }

    public void initSocketDkBangDienThoai() {
        Socket mSocket = MySocketClient.getSocket();

        keyRemoteControl = utils.randomKeyRemoteControl();

        mSocket.on("next-music-mobile--" + keyRemoteControl, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                // Xử lý dữ liệu từ máy chủ ở đây
                MyMusicPlayer.nextBaiHat();

            }
        });

        mSocket.on("prev-music-mobile--" + keyRemoteControl, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                // Xử lý dữ liệu từ máy chủ ở đây
                MyMusicPlayer.preBaiHat();

            }
        });

        mSocket.on("pause-music-mobile--" + keyRemoteControl, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                // Xử lý dữ liệu từ máy chủ ở đây
                MyMusicPlayer.pause();

            }
        });

        mSocket.on("resume-music-mobile--" + keyRemoteControl, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                // Xử lý dữ liệu từ máy chủ ở đây
                MyMusicPlayer.resume();

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
            btnBack.setEnabled(true);
        } else {
            ImageIcon iconBack = new ImageIcon(MainJFrame.class.getResource("/icon/icon-arrow-back-disable.png"));
            btnBack.setIcon(iconBack);
            btnBack.setEnabled(false);
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
            case "Radio":
                newPN = new RadioPanel();
                break;
            case "SettingGiaoDien":
                newPN = new SettingGiaoDien();
                break;
            case "MiniGame":
                newPN = new MiniGame();
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
            return;
        }

        if (oldPanel == null) {
            return;
        }

        cardLayout.show(cardPanel, oldPanel);

        if (historyPanel.size() > 1) {
            ImageIcon iconBack = new ImageIcon(MainJFrame.class.getResource("/icon/icon-arrow-back-active.png"));
            btnBack.setIcon(iconBack);
            btnBack.setEnabled(true);
        } else {
            ImageIcon iconBack = new ImageIcon(MainJFrame.class.getResource("/icon/icon-arrow-back-disable.png"));
            btnBack.setIcon(iconBack);
            btnBack.setEnabled(false);
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
        btnDieuKhoanVaDichVu = new javax.swing.JButton();
        btnGioiThieu = new javax.swing.JButton();
        btnLienHe = new javax.swing.JButton();
        btnMiniGame = new javax.swing.JButton();
        btnRadio = new javax.swing.JButton();
        PanelFooter = new javax.swing.JPanel();
        PanelFooterChill = new javax.swing.JPanel();
        PanelFooterLeft = new javax.swing.JPanel();
        imageBaiHat = new javax.swing.JLabel();
        lbTenBaiHat = new javax.swing.JLabel();
        lbTenCaSi = new javax.swing.JLabel();
        btnMore = new javax.swing.JButton();
        PanelFooterCenter = new javax.swing.JPanel();
        lbThoiGianHienTai = new javax.swing.JLabel();
        lbTongThoiGian = new javax.swing.JLabel();
        progessTimeBaiHat = new javax.swing.JProgressBar();
        btnRandom = new javax.swing.JButton();
        btnLoop = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnPrevBaiHat = new javax.swing.JButton();
        btnPlayPause = new javax.swing.JButton();
        btnNextMusic = new javax.swing.JButton();
        PanelFooterRight = new javax.swing.JPanel();
        progessVolume = new javax.swing.JProgressBar();
        jButton13 = new javax.swing.JButton();
        btnMenuPhatKeTiep = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnKaraoke = new javax.swing.JButton();
        btnOption = new javax.swing.JButton();
        PannelContainer = new javax.swing.JPanel();
        PanelHeader = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        btnAvatar = new javax.swing.JButton();
        btnSetting = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        ipTimKiem = new javax.swing.JTextField();
        btnMiniApp = new javax.swing.JButton();
        lblHenGio = new javax.swing.JLabel();
        tfChuChay = new javax.swing.JTextField();
        PanelWrap = new javax.swing.JPanel();
        PanelContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1600, 800));

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        PanelTabBar.setBackground(new java.awt.Color(51, 0, 51));

        jLabel1.setFont(new java.awt.Font("Viner Hand ITC", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/anh-bia-am-nhac2.jpg"))); // NOI18N
        jLabel1.setText("  Music App");

        btnKhamPha.setBackground(new java.awt.Color(102, 102, 102));
        btnKhamPha.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnKhamPha.setForeground(new java.awt.Color(255, 255, 255));
        btnKhamPha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-discover-20.png"))); // NOI18N
        btnKhamPha.setText("Khám phá");
        btnKhamPha.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnKhamPha.setBorderPainted(false);
        btnKhamPha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKhamPha.setFocusPainted(false);
        btnKhamPha.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnKhamPha.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnKhamPhaMouseMoved(evt);
            }
        });
        btnKhamPha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnKhamPhaMouseExited(evt);
            }
        });
        btnKhamPha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhamPhaActionPerformed(evt);
            }
        });

        btnThuVien.setBackground(null);
        btnThuVien.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnThuVien.setForeground(new java.awt.Color(255, 255, 255));
        btnThuVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-thuvien.png"))); // NOI18N
        btnThuVien.setText("Thư viện");
        btnThuVien.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnThuVien.setBorderPainted(false);
        btnThuVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThuVien.setFocusPainted(false);
        btnThuVien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThuVien.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnThuVienMouseMoved(evt);
            }
        });
        btnThuVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThuVienMouseExited(evt);
            }
        });
        btnThuVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuVienActionPerformed(evt);
            }
        });

        btnLive.setBackground(null);
        btnLive.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnLive.setForeground(new java.awt.Color(255, 255, 255));
        btnLive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-live-20.png"))); // NOI18N
        btnLive.setText("Live");
        btnLive.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnLive.setBorderPainted(false);
        btnLive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLive.setFocusPainted(false);
        btnLive.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLive.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnLiveMouseMoved(evt);
            }
        });
        btnLive.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLiveMouseExited(evt);
            }
        });
        btnLive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiveActionPerformed(evt);
            }
        });

        btnDaTai.setBackground(null);
        btnDaTai.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnDaTai.setForeground(new java.awt.Color(255, 255, 255));
        btnDaTai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-download-20.png"))); // NOI18N
        btnDaTai.setText("Nhạc đã tải");
        btnDaTai.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnDaTai.setBorderPainted(false);
        btnDaTai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDaTai.setFocusPainted(false);
        btnDaTai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDaTai.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnDaTaiMouseMoved(evt);
            }
        });
        btnDaTai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDaTaiMouseExited(evt);
            }
        });
        btnDaTai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaTaiActionPerformed(evt);
            }
        });

        btnYeuThich.setBackground(null);
        btnYeuThich.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnYeuThich.setForeground(new java.awt.Color(255, 255, 255));
        btnYeuThich.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-favorite-20.png"))); // NOI18N
        btnYeuThich.setText("Yêu  thích");
        btnYeuThich.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnYeuThich.setBorderPainted(false);
        btnYeuThich.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnYeuThich.setFocusPainted(false);
        btnYeuThich.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnYeuThich.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnYeuThichMouseMoved(evt);
            }
        });
        btnYeuThich.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnYeuThichMouseExited(evt);
            }
        });
        btnYeuThich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYeuThichActionPerformed(evt);
            }
        });

        btnDieuKhoanVaDichVu.setBackground(null);
        btnDieuKhoanVaDichVu.setFont(new java.awt.Font("Segoe UI Black", 0, 10)); // NOI18N
        btnDieuKhoanVaDichVu.setForeground(new java.awt.Color(153, 153, 153));
        btnDieuKhoanVaDichVu.setText("Điều khoản & dịch vụ");
        btnDieuKhoanVaDichVu.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnDieuKhoanVaDichVu.setBorderPainted(false);
        btnDieuKhoanVaDichVu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDieuKhoanVaDichVu.setFocusPainted(false);
        btnDieuKhoanVaDichVu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDieuKhoanVaDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDieuKhoanVaDichVuActionPerformed(evt);
            }
        });

        btnGioiThieu.setBackground(null);
        btnGioiThieu.setFont(new java.awt.Font("Segoe UI Black", 0, 10)); // NOI18N
        btnGioiThieu.setForeground(new java.awt.Color(153, 153, 153));
        btnGioiThieu.setText("Giới thiệu");
        btnGioiThieu.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnGioiThieu.setBorderPainted(false);
        btnGioiThieu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGioiThieu.setFocusPainted(false);
        btnGioiThieu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGioiThieuActionPerformed(evt);
            }
        });

        btnLienHe.setBackground(null);
        btnLienHe.setFont(new java.awt.Font("Segoe UI Black", 0, 10)); // NOI18N
        btnLienHe.setForeground(new java.awt.Color(153, 153, 153));
        btnLienHe.setText("Liên hệ");
        btnLienHe.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnLienHe.setBorderPainted(false);
        btnLienHe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLienHe.setFocusPainted(false);
        btnLienHe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLienHe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLienHeActionPerformed(evt);
            }
        });

        btnMiniGame.setBackground(null);
        btnMiniGame.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnMiniGame.setForeground(new java.awt.Color(255, 255, 255));
        btnMiniGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-game-20.png"))); // NOI18N
        btnMiniGame.setText("Mini game");
        btnMiniGame.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnMiniGame.setBorderPainted(false);
        btnMiniGame.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMiniGame.setFocusPainted(false);
        btnMiniGame.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMiniGame.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnMiniGameMouseMoved(evt);
            }
        });
        btnMiniGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMiniGameMouseExited(evt);
            }
        });
        btnMiniGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiniGameActionPerformed(evt);
            }
        });

        btnRadio.setBackground(null);
        btnRadio.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnRadio.setForeground(new java.awt.Color(255, 255, 255));
        btnRadio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-radio-20.png"))); // NOI18N
        btnRadio.setText("Radio");
        btnRadio.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnRadio.setBorderPainted(false);
        btnRadio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRadio.setFocusPainted(false);
        btnRadio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRadio.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnRadioMouseMoved(evt);
            }
        });
        btnRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRadioMouseExited(evt);
            }
        });
        btnRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelTabBarLayout = new javax.swing.GroupLayout(PanelTabBar);
        PanelTabBar.setLayout(PanelTabBarLayout);
        PanelTabBarLayout.setHorizontalGroup(
            PanelTabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTabBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnKhamPha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThuVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDaTai, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(btnYeuThich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDieuKhoanVaDichVu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGioiThieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelTabBarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnLienHe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMiniGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRadio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(btnRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDaTai, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnYeuThich, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMiniGame, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(btnGioiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDieuKhoanVaDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLienHe, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jPanel1.add(PanelTabBar, java.awt.BorderLayout.LINE_START);

        PanelFooter.setBackground(new java.awt.Color(0, 0, 0));

        PanelFooterChill.setBackground(new java.awt.Color(0, 0, 0));
        PanelFooterChill.setLayout(new java.awt.GridLayout(1, 0));

        PanelFooterLeft.setBackground(new java.awt.Color(0, 0, 0));

        imageBaiHat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imageBaiHat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageBaiHatMouseClicked(evt);
            }
        });

        lbTenBaiHat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTenBaiHat.setForeground(new java.awt.Color(255, 255, 255));
        lbTenBaiHat.setText("Đi về nhàĐi về nhàĐi về nhà");
        lbTenBaiHat.setMaximumSize(new java.awt.Dimension(200, 20));
        lbTenBaiHat.setMinimumSize(new java.awt.Dimension(150, 22));

        lbTenCaSi.setForeground(new java.awt.Color(204, 204, 204));
        lbTenCaSi.setText("Đen Vâu");
        lbTenCaSi.setMaximumSize(new java.awt.Dimension(450, 16));

        btnMore.setBackground(new java.awt.Color(0, 0, 0));
        btnMore.setBorder(null);
        btnMore.setFocusPainted(false);
        btnMore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelFooterLeftLayout = new javax.swing.GroupLayout(PanelFooterLeft);
        PanelFooterLeft.setLayout(PanelFooterLeftLayout);
        PanelFooterLeftLayout.setHorizontalGroup(
            PanelFooterLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFooterLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(PanelFooterLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbTenBaiHat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTenCaSi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMore)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelFooterLeftLayout.setVerticalGroup(
            PanelFooterLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFooterLeftLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(PanelFooterLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelFooterLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnMore)
                        .addGroup(PanelFooterLeftLayout.createSequentialGroup()
                            .addComponent(lbTenBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbTenCaSi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        PanelFooterChill.add(PanelFooterLeft);

        PanelFooterCenter.setBackground(new java.awt.Color(0, 0, 0));

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

        btnRandom.setBackground(null);
        btnRandom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-random.png"))); // NOI18N
        btnRandom.setBorder(null);
        btnRandom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRandom.setFocusPainted(false);
        btnRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomActionPerformed(evt);
            }
        });

        btnLoop.setBackground(null);
        btnLoop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-loop.png"))); // NOI18N
        btnLoop.setBorder(null);
        btnLoop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLoop.setFocusPainted(false);
        btnLoop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoopActionPerformed(evt);
            }
        });

        jPanel6.setBackground(null);

        btnPrevBaiHat.setBackground(null);
        btnPrevBaiHat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/prev.png"))); // NOI18N
        btnPrevBaiHat.setBorder(null);
        btnPrevBaiHat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrevBaiHat.setFocusPainted(false);
        btnPrevBaiHat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevBaiHatActionPerformed(evt);
            }
        });
        jPanel6.add(btnPrevBaiHat);

        btnPlayPause.setBackground(null);
        btnPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-pause.png"))); // NOI18N
        btnPlayPause.setBorder(null);
        btnPlayPause.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlayPause.setFocusPainted(false);
        btnPlayPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseActionPerformed(evt);
            }
        });
        jPanel6.add(btnPlayPause);

        btnNextMusic.setBackground(null);
        btnNextMusic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-next-30.png"))); // NOI18N
        btnNextMusic.setBorder(null);
        btnNextMusic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNextMusic.setFocusPainted(false);
        btnNextMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextMusicActionPerformed(evt);
            }
        });
        jPanel6.add(btnNextMusic);

        javax.swing.GroupLayout PanelFooterCenterLayout = new javax.swing.GroupLayout(PanelFooterCenter);
        PanelFooterCenter.setLayout(PanelFooterCenterLayout);
        PanelFooterCenterLayout.setHorizontalGroup(
            PanelFooterCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFooterCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelFooterCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFooterCenterLayout.createSequentialGroup()
                        .addComponent(btnRandom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoop))
                    .addGroup(PanelFooterCenterLayout.createSequentialGroup()
                        .addComponent(lbThoiGianHienTai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progessTimeBaiHat, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTongThoiGian)))
                .addContainerGap())
        );
        PanelFooterCenterLayout.setVerticalGroup(
            PanelFooterCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFooterCenterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelFooterCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRandom, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLoop, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PanelFooterCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFooterCenterLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(progessTimeBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelFooterCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbThoiGianHienTai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbTongThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        PanelFooterChill.add(PanelFooterCenter);

        PanelFooterRight.setBackground(new java.awt.Color(0, 0, 0));

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
        jButton13.setBorder(null);
        jButton13.setFocusPainted(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        btnMenuPhatKeTiep.setBackground(null);
        btnMenuPhatKeTiep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/menu-music.png"))); // NOI18N
        btnMenuPhatKeTiep.setToolTipText("Danh sách nhạc");
        btnMenuPhatKeTiep.setBorder(null);
        btnMenuPhatKeTiep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMenuPhatKeTiep.setFocusPainted(false);
        btnMenuPhatKeTiep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuPhatKeTiepActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnKaraoke.setBackground(null);
        btnKaraoke.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/micro.png"))); // NOI18N
        btnKaraoke.setToolTipText("Karaoke");
        btnKaraoke.setBorder(null);
        btnKaraoke.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKaraoke.setFocusPainted(false);
        btnKaraoke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKaraokeActionPerformed(evt);
            }
        });

        btnOption.setBackground(null);
        btnOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-more-20.png"))); // NOI18N
        btnOption.setToolTipText("Karaoke");
        btnOption.setBorder(null);
        btnOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOption.setFocusPainted(false);
        btnOption.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnOption.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelFooterRightLayout = new javax.swing.GroupLayout(PanelFooterRight);
        PanelFooterRight.setLayout(PanelFooterRightLayout);
        PanelFooterRightLayout.setHorizontalGroup(
            PanelFooterRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFooterRightLayout.createSequentialGroup()
                .addContainerGap(203, Short.MAX_VALUE)
                .addGroup(PanelFooterRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFooterRightLayout.createSequentialGroup()
                        .addComponent(btnOption)
                        .addGap(12, 12, 12)
                        .addComponent(btnKaraoke)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progessVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMenuPhatKeTiep)
                .addGap(12, 12, 12))
        );
        PanelFooterRightLayout.setVerticalGroup(
            PanelFooterRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFooterRightLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(PanelFooterRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuPhatKeTiep)
                    .addComponent(jButton13)
                    .addGroup(PanelFooterRightLayout.createSequentialGroup()
                        .addGroup(PanelFooterRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(progessVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnKaraoke)
                            .addComponent(btnOption, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        PanelFooterChill.add(PanelFooterRight);

        javax.swing.GroupLayout PanelFooterLayout = new javax.swing.GroupLayout(PanelFooter);
        PanelFooter.setLayout(PanelFooterLayout);
        PanelFooterLayout.setHorizontalGroup(
            PanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFooterChill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelFooterLayout.setVerticalGroup(
            PanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFooterLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(PanelFooterChill, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(PanelFooter, java.awt.BorderLayout.PAGE_END);

        PannelContainer.setBackground(new java.awt.Color(23, 15, 35));

        PanelHeader.setBackground(new java.awt.Color(23, 15, 35));

        btnBack.setBackground(null);
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-arrow-back-disable.png"))); // NOI18N
        btnBack.setBorder(null);
        btnBack.setEnabled(false);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tìm kiếm");

        btnTimKiem.setBackground(new java.awt.Color(255, 102, 102));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiem.setText("Tìm");
        btnTimKiem.setBorder(null);
        btnTimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnAvatar.setBackground(null);
        btnAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-user.png"))); // NOI18N
        btnAvatar.setBorder(null);
        btnAvatar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAvatar.setFocusPainted(false);
        btnAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvatarActionPerformed(evt);
            }
        });

        btnSetting.setBackground(null);
        btnSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-setting-30.png"))); // NOI18N
        btnSetting.setToolTipText("Cài đặt");
        btnSetting.setBorder(null);
        btnSetting.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        ipTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ipTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ipTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ipTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        btnMiniApp.setBackground(null);
        btnMiniApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-device-30.png"))); // NOI18N
        btnMiniApp.setToolTipText("Cài đặt");
        btnMiniApp.setBorder(null);
        btnMiniApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMiniApp.setFocusPainted(false);
        btnMiniApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiniAppActionPerformed(evt);
            }
        });

        lblHenGio.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHenGio.setForeground(new java.awt.Color(255, 255, 255));
        lblHenGio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHenGioMouseClicked(evt);
            }
        });

        tfChuChay.setEditable(false);
        tfChuChay.setBackground(null);
        tfChuChay.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        tfChuChay.setForeground(new java.awt.Color(102, 0, 102));
        tfChuChay.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfChuChay.setText("ssss");
        tfChuChay.setBorder(null);
        tfChuChay.setEnabled(false);
        tfChuChay.setFocusable(false);

        javax.swing.GroupLayout PanelHeaderLayout = new javax.swing.GroupLayout(PanelHeader);
        PanelHeader.setLayout(PanelHeaderLayout);
        PanelHeaderLayout.setHorizontalGroup(
            PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHeaderLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnBack)
                .addGap(47, 47, 47)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfChuChay, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(lblHenGio)
                .addGap(18, 18, 18)
                .addComponent(btnMiniApp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelHeaderLayout.setVerticalGroup(
            PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHeaderLayout.createSequentialGroup()
                .addGroup(PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelHeaderLayout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(btnBack))
                        .addGroup(PanelHeaderLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHenGio)
                                    .addComponent(tfChuChay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(PanelHeaderLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(btnSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMiniApp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
            .addComponent(PanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelWrap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PannelContainerLayout.setVerticalGroup(
            PannelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PannelContainerLayout.createSequentialGroup()
                .addComponent(PanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
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
//        System.out.println("Width" + String.valueOf(progressBarWidth));
//        System.out.println("vi tri: " + String.valueOf(mouseX));
//        System.out.println("data: " + String.valueOf(percentage));

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
        new Thread(() -> {
            MyMusicPlayer.nextBaiHat();
        }).start();

    }//GEN-LAST:event_btnNextMusicActionPerformed

    private void btnPrevBaiHatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevBaiHatActionPerformed
        // TODO add your handling code here:
        new Thread(() -> {
            MyMusicPlayer.preBaiHat();
        }).start();

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
            if (threadTimKiem != null && threadTimKiem.isAlive()) {
                threadTimKiem.interrupt();
            }
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

    private void btnDieuKhoanVaDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDieuKhoanVaDichVuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDieuKhoanVaDichVuActionPerformed

    private void btnGioiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGioiThieuActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnGioiThieuActionPerformed

    private void ipTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ipTimKiemKeyReleased
        // TODO add your handling code here:

        if (evt.getKeyCode() == 10) {
            return;
        }

        if (threadTimKiem != null && threadTimKiem.isAlive()) {
            threadTimKiem.interrupt();
        }

        threadTimKiem = new Thread(() -> {
            try {
                Thread.sleep(500);

                getGoiYTimKiem();

            } catch (InterruptedException ex) {

            }
        });

        threadTimKiem.start();


    }//GEN-LAST:event_ipTimKiemKeyReleased

    private void imageBaiHatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageBaiHatMouseClicked
        // TODO add your handling code here:
        XemThongTinBaiHat();
    }//GEN-LAST:event_imageBaiHatMouseClicked

    public void XemThongTinBaiHat() {
        MyCustomDialog customDialog = new MyCustomDialog(this, "", new ThongTinBaiHat(MyMusicPlayer.dsBaiHat.get(MyMusicPlayer.position)));
        customDialog.setResizable(false);
        customDialog.show(true);
    }

    private void btnMiniAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiniAppActionPerformed
        // TODO add your handling code here:
        Main.rootFrame.setVisible(false);

        JFrame miniFrame = new MiniAppFrame();
        miniFrame.setLocationRelativeTo(null);
        MiniAppFrame.isOpen = true;
        miniFrame.setVisible(true);

    }//GEN-LAST:event_btnMiniAppActionPerformed

    private void btnOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionActionPerformed
        // TODO add your handling code here:
        OptionBaiHat optionBaiHat = new OptionBaiHat(MyMusicPlayer.dsBaiHat.get(MyMusicPlayer.position), "Main", btnOption);
        optionBaiHat.show();
    }//GEN-LAST:event_btnOptionActionPerformed

    private void btnLienHeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLienHeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLienHeActionPerformed

    private void lblHenGioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHenGioMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            if (ModelHenGio.threadHenGio != null && ModelHenGio.threadHenGio.isAlive()) {
                int xacnhan = JOptionPane.showConfirmDialog(null, "Hủy hẹn giờ hả?");
                if (xacnhan == JOptionPane.OK_OPTION) {
                    ModelHenGio.threadHenGio.interrupt();
                    lblHenGio.setText("");
                }
            }
        }
    }//GEN-LAST:event_lblHenGioMouseClicked

    private void btnMiniGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiniGameActionPerformed
        // TODO add your handling code here:
        ShowPanel("MiniGame", new MiniGame());
        resetTabBarColor();
        btnMiniGame.setBackground(new Color(102, 102, 102));
    }//GEN-LAST:event_btnMiniGameActionPerformed

    private void btnKhamPhaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhamPhaMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnKhamPha);
    }//GEN-LAST:event_btnKhamPhaMouseMoved

    private void btnKhamPhaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhamPhaMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnKhamPha);
    }//GEN-LAST:event_btnKhamPhaMouseExited

    private void btnThuVienMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThuVienMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnThuVien);
    }//GEN-LAST:event_btnThuVienMouseMoved

    private void btnThuVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThuVienMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnThuVien);
    }//GEN-LAST:event_btnThuVienMouseExited

    private void btnLiveMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLiveMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnLive);
    }//GEN-LAST:event_btnLiveMouseMoved

    private void btnLiveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLiveMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnLive);
    }//GEN-LAST:event_btnLiveMouseExited

    private void btnDaTaiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDaTaiMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnDaTai);
    }//GEN-LAST:event_btnDaTaiMouseMoved

    private void btnDaTaiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDaTaiMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnDaTai);
    }//GEN-LAST:event_btnDaTaiMouseExited

    private void btnYeuThichMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnYeuThichMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnYeuThich);
    }//GEN-LAST:event_btnYeuThichMouseMoved

    private void btnYeuThichMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnYeuThichMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnYeuThich);
    }//GEN-LAST:event_btnYeuThichMouseExited

    private void btnMiniGameMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMiniGameMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnMiniGame);
    }//GEN-LAST:event_btnMiniGameMouseMoved

    private void btnMiniGameMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMiniGameMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnMiniGame);
    }//GEN-LAST:event_btnMiniGameMouseExited

    private void btnRadioMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRadioMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnRadio);
    }//GEN-LAST:event_btnRadioMouseMoved

    private void btnRadioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRadioMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnRadio);
    }//GEN-LAST:event_btnRadioMouseExited

    private void btnRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadioActionPerformed
        // TODO add your handling code here:
        ShowPanel("Radio", new RadioPanel());
        resetTabBarColor();
        btnRadio.setBackground(new Color(102, 102, 102));
    }//GEN-LAST:event_btnRadioActionPerformed

    public void onHoverMenu(JButton btn) {
        btn.setForeground(Color.red);
    }

    public void endHoverMenu(JButton btn) {
        btn.setForeground(Color.WHITE);
    }

    public void getGoiYTimKiem() {

        ApiServiceV1.apiServiceV1.getGoiYTuKhoa(ipTimKiem.getText()).enqueue(new Callback<KeywordGoogle>() {
            @Override
            public void onResponse(Call<KeywordGoogle> call, Response<KeywordGoogle> rspns) {
                KeywordGoogle res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    System.out.println("co data");
                    ArrayList<String> data = res.getData();
                    JPopupMenu popupMenu = new JPopupMenu();
                    for (String keyword : data) {
                        JMenuItem option1 = new JMenuItem(keyword);
                        option1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ipTimKiem.setText(option1.getText());

                                String keyword = ipTimKiem.getText();
                                if (!keyword.isEmpty()) {
                                    ShowPanel("TimKiem", new TimKiemPanel(keyword));
                                }
                            }
                        });
                        popupMenu.add(option1);
                    }
                    popupMenu.show(ipTimKiem, -12, popupMenu.getHeight() + 30);
                }
            }

            @Override
            public void onFailure(Call<KeywordGoogle> call, Throwable thrwbl) {

            }
        });

    }

    public void resetTabBarColor() {
        btnKhamPha.setBackground(null);
        btnThuVien.setBackground(null);
        btnLive.setBackground(null);
        btnYeuThich.setBackground(null);
        btnDaTai.setBackground(null);
        btnRadio.setBackground(null);
        btnMiniGame.setBackground(null);
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

    public void setBackgroundTabbar(Color color) {
        PanelTabBar.setBackground(color);
    }

    public void setBackgroundHeader(Color color) {
        PanelHeader.setBackground(color);
    }

    public void setBackgroundFooter(Color color) {
        PanelFooterLeft.setBackground(color);
        PanelFooterCenter.setBackground(color);
        PanelFooterRight.setBackground(color);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContent;
    public static javax.swing.JPanel PanelFooter;
    private javax.swing.JPanel PanelFooterCenter;
    private javax.swing.JPanel PanelFooterChill;
    private javax.swing.JPanel PanelFooterLeft;
    private javax.swing.JPanel PanelFooterRight;
    private javax.swing.JPanel PanelHeader;
    private javax.swing.JPanel PanelTabBar;
    public static javax.swing.JPanel PanelWrap;
    private javax.swing.JPanel PannelContainer;
    public static javax.swing.JButton btnAvatar;
    public static javax.swing.JButton btnBack;
    private javax.swing.JButton btnDaTai;
    private javax.swing.JButton btnDieuKhoanVaDichVu;
    private javax.swing.JButton btnGioiThieu;
    public static javax.swing.JButton btnKaraoke;
    private javax.swing.JButton btnKhamPha;
    private javax.swing.JButton btnLienHe;
    private javax.swing.JButton btnLive;
    private javax.swing.JButton btnLoop;
    private javax.swing.JButton btnMenuPhatKeTiep;
    private javax.swing.JButton btnMiniApp;
    private javax.swing.JButton btnMiniGame;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnNextMusic;
    public static javax.swing.JButton btnOption;
    public static javax.swing.JButton btnPlayPause;
    private javax.swing.JButton btnPrevBaiHat;
    private javax.swing.JButton btnRadio;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JLabel lbTenBaiHat;
    public static javax.swing.JLabel lbTenCaSi;
    public static javax.swing.JLabel lbThoiGianHienTai;
    public static javax.swing.JLabel lbTongThoiGian;
    public static javax.swing.JLabel lblHenGio;
    public static javax.swing.JProgressBar progessTimeBaiHat;
    public static javax.swing.JProgressBar progessVolume;
    private javax.swing.JTextField tfChuChay;
    // End of variables declaration//GEN-END:variables
}
