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
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import screen.ChiTietCaSi;
import screen.ChiTietMV;
import screen.ChiTietPlaylist;
import screen.DaTai;
import component.KaraokePanel;
import component.ModelHenGio;
import component.OptionBaiHat;
import screen.KhamPha;
import screen.ListLive;
import screen.RoomLive;
import component.PhatKeTiepPanel;
import screen.SettingGiaoDien;
import screen.Setting;
import component.ThongTinBaiHat;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import screen.ThuVien;
import screen.TimKiem;
import screen.YeuThich;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import api.ApiServiceV1;
import component.CustomScrollBarUI;
import component.ItemMusic;
import helpers.AppConstants;
import helpers.LocalData;
import component.MyCustomDialog;
import component.PopupMessageLock;
import services.MyMusicPlayer;
import services.MySocketClient;
import helpers.Utils;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BaiHat;
import model.BaiHat_CaSi;
import model.Casi;
import observer.BtnPauseObserver;
import observer.FooterHomeObserver;
import observer.KaraokeObserver;
import observer.PhatKeTiepObserver;
import observer.Subject;
import screen.CommentPanel;
import screen.MVSearch;
import screen.MiniGame;
import screen.Radio;

/**
 *
 * @author tranv
 */
public class MainJFrame extends javax.swing.JFrame {

    public static JPanel cardPanel;
    public static CardLayout cardLayout;
    public static boolean isMenuPhatKeTiep = false;
    public static boolean isKaraoke = false;
    public static boolean isComment = false;

    public static String oldPanel = "";
    public static Stack<String> historyPanel = new Stack<>(); //.pop() .peek() .push()
    public static boolean isLogin = false;
    public static TaiKhoan userInfo;

    public static Browser browser;
    public static Socket mSocket_DKGionNoi;
    public static String keyRemoteControl;

    private Thread threadTimKiem;
    private String textChuChay;

    public static Subject subject;
    public static Subject subjectBtnMusic;
    private KaraokeObserver karaokeObserver;
    private PhatKeTiepObserver phatKeTiepObserver;

    /**
     * Creates new form HomeJFrame
     */
    public MainJFrame() {
//        setUndecorated(true);

        initComponents();
        setTitle("Music App - Nghe nhạc trực tuyến");
        ImageIcon ic = new ImageIcon(getClass().getResource("/icon/anh-bia-am-nhac2.jpg"));
        setIconImage(ic.getImage());
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        initPanelContent();

        PanelFooter.setVisible(false);
        // test
//        LocalData.removeData("accessToken");
        Utils.CheckLogin();

        getUserLogin();

        initSocketDkBangDienThoai();

        loadBackGroundColor();

        loadChuChay();

        subject = new Subject();
        subject.attach(new FooterHomeObserver(this));

        subjectBtnMusic = new Subject();
        subjectBtnMusic.attach(new BtnPauseObserver(btnPlayPause));

        loadBaiHatOld();

        //test
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                    if (MyMusicPlayer.player != null) {
//                        System.out.println("position: " + MyMusicPlayer.player.getPosition());
//                    }
//
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        }).start();
    }

    public void loadBaiHatOld() {
        ArrayList<BaiHat> listDaNghe = LocalData.getListDaNghe();
        if (listDaNghe != null && !listDaNghe.isEmpty()) {
//            BaiHat bhOld = listDaNghe.get(0);
//            MyMusicPlayer.dsBaiHat = listDaNghe;
//            MyMusicPlayer.position = 0;

            MyMusicPlayer.initMusicPlayer(listDaNghe, 0);
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (MyMusicPlayer.isPlay && !MyMusicPlayer.loadingPlay && MyMusicPlayer.player != null) {
                        MyMusicPlayer.pause();
                        break;
                    }
                }

            }).start();

            MyMusicPlayer.getListRandom(phatKeTiepObserver);
//            updateFooterHome(bhOld);

            btnPlayPause.setIcon(new ImageIcon(getClass().getResource("/icon/icon-play.png")));

            PanelFooter.setVisible(true);
        }
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

        keyRemoteControl = Utils.randomKeyRemoteControl();

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
        String header = Utils.getHeader();

        ApiServiceV1.apiServiceV1.getTaiKhoan(header).enqueue(new Callback<GetTaiKhoan>() {
            @Override
            public void onResponse(Call<GetTaiKhoan> call, Response<GetTaiKhoan> rspns) {
                GetTaiKhoan res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    TaiKhoan user = res.getData();
                    userInfo = user;
                    String urlAnh = Utils.getAnhUser(user);

                    ImageIcon avatar = Utils.getImageBaiHat(urlAnh, 40, 40);

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

        isKaraoke = false;
        ImageIcon iconKaraoke = new ImageIcon(MainJFrame.class.getResource("/icon/micro.png"));
        btnKaraoke.setIcon(iconKaraoke);

        if (!historyPanel.empty() && historyPanel.peek().equals(name)) {
            resetPanel();
            return;
        }
        historyPanel.push(name);

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
                newPN = new KhamPha();
                break;
            case "ThuVien":
                newPN = new ThuVien();
                break;
            case "Karaoke":
                newPN = new KaraokePanel();
                break;
            case "TimKiem":
                newPN = new TimKiem(TimKiem.keyword);
                break;
            case "ChiTietCaSi":
                newPN = new ChiTietCaSi(ChiTietCaSi.idCaSi);
                break;
            case "ChiTietMV":
                newPN = new ChiTietMV(ChiTietMV.idMV);
                break;
            case "ChiTietPlaylist":
                newPN = new ChiTietPlaylist(ChiTietPlaylist.idPlaylist);
                break;
            case "YeuThich":
                newPN = new YeuThich();
                break;
            case "DaTai":
                newPN = new DaTai();
                break;
            case "Setting":
                newPN = new Setting();
                break;
            case "ListLive":
                newPN = new ListLive();
                break;
            case "Radio":
                newPN = new Radio();
                break;
            case "SettingGiaoDien":
                newPN = new SettingGiaoDien();
                break;
            case "MiniGame":
                newPN = new MiniGame();
                break;
            case "MV":
                newPN = new MVSearch("a");
                break;
            default:
                newPN = new KhamPha();
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

    public void ToggleShowKaraoke() {

        if (isKaraoke) {
            ImageIcon icon = new ImageIcon(MainJFrame.class.getResource("/icon/micro.png"));
            btnKaraoke.setIcon(icon);

            String oldPanel = historyPanel.peek();
            cardLayout.show(cardPanel, oldPanel);

            subject.detach(karaokeObserver);

        } else {
            ImageIcon icon = new ImageIcon(MainJFrame.class.getResource("/icon/micro-active.png"));
            btnKaraoke.setIcon(icon);

            JPanel pn = new KaraokePanel();

            cardPanel.add(pn, "Karaoke");
            cardLayout.show(cardPanel, "Karaoke");

            karaokeObserver = new KaraokeObserver((KaraokePanel) pn);
            subject.attach(karaokeObserver);
        }

        isKaraoke = !isKaraoke;

    }

    public void ToggleShowComment() {

        if (isKaraoke) {
            ImageIcon icon = new ImageIcon(MainJFrame.class.getResource("/icon/micro.png"));
            btnKaraoke.setIcon(icon);

            String oldPanel = historyPanel.peek();
            cardLayout.show(cardPanel, oldPanel);

            subject.detach(karaokeObserver);

        }

        if (isComment) {
//            ImageIcon icon = new ImageIcon(MainJFrame.class.getResource("/icon/micro.png"));
//            btnKaraoke.setIcon(icon);

            String oldPanel = historyPanel.peek();
            cardLayout.show(cardPanel, oldPanel);

        } else {
//            ImageIcon icon = new ImageIcon(MainJFrame.class.getResource("/icon/micro-active.png"));
//            btnKaraoke.setIcon(icon);

            JPanel pn = new CommentPanel();

            cardPanel.add(pn, "Comment");
            cardLayout.show(cardPanel, "Comment");

        }

        isComment = !isComment;

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
        btnDieuKhoanVaDichVu = new javax.swing.JButton();
        btnGioiThieu = new javax.swing.JButton();
        btnLienHe = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        btnKhamPha = new javax.swing.JButton();
        btnThuVien = new javax.swing.JButton();
        btnMV = new javax.swing.JButton();
        btnNhacNen = new javax.swing.JButton();
        btnLive = new javax.swing.JButton();
        btnRadio = new javax.swing.JButton();
        btnDaTai = new javax.swing.JButton();
        btnYeuThich = new javax.swing.JButton();
        btnMiniGame = new javax.swing.JButton();
        btnEvent = new javax.swing.JButton();
        btnKhoaHoc = new javax.swing.JButton();
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
        btnComment = new javax.swing.JButton();
        PannelContainer = new javax.swing.JPanel();
        PanelHeader = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        btnAvatar = new javax.swing.JButton();
        btnSetting = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        ipTimKiem = new javax.swing.JTextField();
        btnMic = new javax.swing.JLabel();
        btnMiniApp = new javax.swing.JButton();
        lblHenGio = new javax.swing.JLabel();
        tfChuChay = new javax.swing.JTextField();
        btnThongBao = new javax.swing.JButton();
        PanelWrap = new javax.swing.JPanel();
        PanelContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1600, 800));
        setSize(new java.awt.Dimension(1600, 800));

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        PanelTabBar.setBackground(new java.awt.Color(51, 0, 51));

        jLabel1.setFont(new java.awt.Font("Viner Hand ITC", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/anh-bia-am-nhac2.jpg"))); // NOI18N
        jLabel1.setText("  Music App");

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

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel2.setBackground(new java.awt.Color(51, 0, 51));

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

        btnMV.setBackground(null);
        btnMV.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnMV.setForeground(new java.awt.Color(255, 255, 255));
        btnMV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-video-20.png"))); // NOI18N
        btnMV.setText("MV");
        btnMV.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnMV.setBorderPainted(false);
        btnMV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMV.setFocusPainted(false);
        btnMV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMV.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnMVMouseMoved(evt);
            }
        });
        btnMV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMVMouseExited(evt);
            }
        });
        btnMV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMVActionPerformed(evt);
            }
        });

        btnNhacNen.setBackground(null);
        btnNhacNen.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnNhacNen.setForeground(new java.awt.Color(255, 255, 255));
        btnNhacNen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-earbud-headphones-20.png"))); // NOI18N
        btnNhacNen.setText("Nhạc nền - 8D");
        btnNhacNen.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnNhacNen.setBorderPainted(false);
        btnNhacNen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNhacNen.setFocusPainted(false);
        btnNhacNen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNhacNen.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnNhacNenMouseMoved(evt);
            }
        });
        btnNhacNen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNhacNenMouseExited(evt);
            }
        });
        btnNhacNen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhacNenActionPerformed(evt);
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

        btnEvent.setBackground(null);
        btnEvent.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnEvent.setForeground(new java.awt.Color(255, 255, 255));
        btnEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-confetti-20.png"))); // NOI18N
        btnEvent.setText("Event");
        btnEvent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnEvent.setBorderPainted(false);
        btnEvent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEvent.setFocusPainted(false);
        btnEvent.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEvent.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnEventMouseMoved(evt);
            }
        });
        btnEvent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEventMouseExited(evt);
            }
        });
        btnEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventActionPerformed(evt);
            }
        });

        btnKhoaHoc.setBackground(null);
        btnKhoaHoc.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnKhoaHoc.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoaHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-course-20.png"))); // NOI18N
        btnKhoaHoc.setText("Khóa học");
        btnKhoaHoc.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnKhoaHoc.setBorderPainted(false);
        btnKhoaHoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKhoaHoc.setFocusPainted(false);
        btnKhoaHoc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnKhoaHoc.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnKhoaHocMouseMoved(evt);
            }
        });
        btnKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnKhoaHocMouseExited(evt);
            }
        });
        btnKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKhamPha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThuVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNhacNen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
            .addComponent(btnLive, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRadio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDaTai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnYeuThich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMiniGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEvent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKhoaHoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnKhamPha, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThuVien, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMV, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNhacNen, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout PanelTabBarLayout = new javax.swing.GroupLayout(PanelTabBar);
        PanelTabBar.setLayout(PanelTabBarLayout);
        PanelTabBarLayout.setHorizontalGroup(
            PanelTabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTabBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDieuKhoanVaDichVu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGioiThieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLienHe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelTabBarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        PanelTabBarLayout.setVerticalGroup(
            PanelTabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTabBarLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGioiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDieuKhoanVaDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLienHe, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jPanel1.add(PanelTabBar, java.awt.BorderLayout.LINE_START);

        PanelFooter.setBackground(new java.awt.Color(0, 0, 0));
        PanelFooter.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 102)), "Music Player", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Vivaldi", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

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
        lbTenBaiHat.setMaximumSize(new java.awt.Dimension(200, 20));
        lbTenBaiHat.setMinimumSize(new java.awt.Dimension(150, 22));

        lbTenCaSi.setForeground(new java.awt.Color(204, 204, 204));
        lbTenCaSi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbTenCaSi.setMaximumSize(new java.awt.Dimension(450, 16));
        lbTenCaSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTenCaSiMouseClicked(evt);
            }
        });

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
                    .addComponent(lbTenCaSi, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelFooterChill.add(PanelFooterLeft);

        PanelFooterCenter.setBackground(new java.awt.Color(13, 9, 14));

        lbThoiGianHienTai.setForeground(new java.awt.Color(255, 255, 255));
        lbThoiGianHienTai.setText("0:00");

        lbTongThoiGian.setForeground(new java.awt.Color(255, 255, 255));
        lbTongThoiGian.setText("0:00");

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
        btnPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-play.png"))); // NOI18N
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
                        .addComponent(progessTimeBaiHat, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
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
        btnOption.setToolTipText("Tùy chọn");
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

        btnComment.setBackground(null);
        btnComment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-comment-20.png"))); // NOI18N
        btnComment.setToolTipText("Bình luận");
        btnComment.setBorder(null);
        btnComment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnComment.setFocusPainted(false);
        btnComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCommentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelFooterRightLayout = new javax.swing.GroupLayout(PanelFooterRight);
        PanelFooterRight.setLayout(PanelFooterRightLayout);
        PanelFooterRightLayout.setHorizontalGroup(
            PanelFooterRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFooterRightLayout.createSequentialGroup()
                .addContainerGap(318, Short.MAX_VALUE)
                .addGroup(PanelFooterRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFooterRightLayout.createSequentialGroup()
                        .addComponent(btnOption)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnComment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                            .addComponent(btnOption, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnComment))
                        .addGap(8, 8, 8)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        PanelFooterChill.add(PanelFooterRight);

        javax.swing.GroupLayout PanelFooterLayout = new javax.swing.GroupLayout(PanelFooter);
        PanelFooter.setLayout(PanelFooterLayout);
        PanelFooterLayout.setHorizontalGroup(
            PanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFooterChill, javax.swing.GroupLayout.DEFAULT_SIZE, 1591, Short.MAX_VALUE)
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
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.setEnabled(false);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 204));
        jLabel7.setText("Tìm kiếm");

        btnTimKiem.setBackground(new java.awt.Color(23, 15, 35));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-search-20.png"))); // NOI18N
        btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnTimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnAvatar.setBackground(new java.awt.Color(102, 102, 102));
        btnAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-user.png"))); // NOI18N
        btnAvatar.setToolTipText("");
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

        btnMic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-mic-30.png"))); // NOI18N
        btnMic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMicMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ipTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMic)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ipTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnMic))
        );

        btnMiniApp.setBackground(null);
        btnMiniApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-device-30.png"))); // NOI18N
        btnMiniApp.setToolTipText("Mini App");
        btnMiniApp.setBorder(null);
        btnMiniApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMiniApp.setFocusPainted(false);
        btnMiniApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiniAppActionPerformed(evt);
            }
        });

        lblHenGio.setBackground(new java.awt.Color(153, 0, 0));
        lblHenGio.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHenGio.setForeground(new java.awt.Color(204, 0, 204));
        lblHenGio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHenGioMouseClicked(evt);
            }
        });

        tfChuChay.setEditable(false);
        tfChuChay.setBackground(null);
        tfChuChay.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        tfChuChay.setForeground(new java.awt.Color(153, 153, 153));
        tfChuChay.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfChuChay.setText("ssss");
        tfChuChay.setBorder(null);
        tfChuChay.setEnabled(false);
        tfChuChay.setFocusable(false);

        btnThongBao.setBackground(null);
        btnThongBao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons-bell-30.png"))); // NOI18N
        btnThongBao.setToolTipText("Thông báo");
        btnThongBao.setBorder(null);
        btnThongBao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThongBao.setFocusPainted(false);
        btnThongBao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongBaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelHeaderLayout = new javax.swing.GroupLayout(PanelHeader);
        PanelHeader.setLayout(PanelHeaderLayout);
        PanelHeaderLayout.setHorizontalGroup(
            PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHeaderLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnBack)
                .addGap(46, 46, 46)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfChuChay, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHenGio)
                .addGap(0, 0, 0)
                .addComponent(btnThongBao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addContainerGap()
                .addGroup(PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblHenGio)
                        .addComponent(tfChuChay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnThongBao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMiniApp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        PanelWrap.setBackground(new java.awt.Color(23, 15, 35));
        PanelWrap.setLayout(new java.awt.BorderLayout());

        PanelContent.setBackground(new java.awt.Color(23, 15, 35));
        PanelContent.setFocusable(false);
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
                .addComponent(PanelWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE))
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
        ShowPanel("KhamPha", new KhamPha());
        resetTabBarColor();
        btnKhamPha.setBackground(new Color(102, 102, 102));

    }//GEN-LAST:event_btnKhamPhaActionPerformed

    private void btnThuVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuVienActionPerformed
        // TODO add your handling code here:
        if (!Utils.getIsLogin()) {
            return;
        }

        ShowPanel("ThuVien", new ThuVien());
        resetTabBarColor();
        btnThuVien.setBackground(new Color(102, 102, 102));

    }//GEN-LAST:event_btnThuVienActionPerformed

    public boolean checkLicenseJxBrowser() {
        try {
            if (browser != null) {
                System.out.println("vao ấđâsd");
                return true;
            }
            Engine engine = Engine.newInstance(EngineOptions.newBuilder(RenderingMode.OFF_SCREEN)
                    .licenseKey("1BNDHFSC1G8RA8PYZSNW76QPH8UUQU4FTUIGJE23Y1HZ5EEYND8BED4IUWACS012LYXTSS").build());
            browser = engine.newBrowser();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void btnLiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiveActionPerformed
        // TODO add your handling code here:

        if (AppConstants.isBlockPage || !checkLicenseJxBrowser()) {
            MyCustomDialog customDialog = new MyCustomDialog(null, "", new PopupMessageLock());
            customDialog.setSize(400, 300);
            customDialog.setResizable(false);
            customDialog.setVisible(true);
            return;
        }
        ShowPanel("ListLive", new ListLive());
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
            ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-play.png"));
            btnPlayPause.setIcon(icon);

        } else {
            MyMusicPlayer.resume();
            ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-pause.png"));
            btnPlayPause.setIcon(icon);
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
//        if (MyMusicPlayer.loadingPlay) {
//            return;
//        }
        ItemMusic.anhNhac = null;
        new Thread(() -> {
            MyMusicPlayer.nextBaiHat();
        }).start();

    }//GEN-LAST:event_btnNextMusicActionPerformed

    private void btnPrevBaiHatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevBaiHatActionPerformed
        // TODO add your handling code here:
//        if (MyMusicPlayer.loadingPlay) {
//            return;
//        }
        ItemMusic.anhNhac = null;
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
            MyMusicPlayer.getListRandom(phatKeTiepObserver);
        }
        MyMusicPlayer.isRandom = !MyMusicPlayer.isRandom;
    }//GEN-LAST:event_btnRandomActionPerformed


    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String keyword = ipTimKiem.getText();
        if (!keyword.isEmpty()) {
            ShowPanel("TimKiem", new TimKiem(keyword));
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

            subject.detach(phatKeTiepObserver);
        } else {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon/menu-music-active.png"));
            btnMenuPhatKeTiep.setIcon(icon);

            JPanel phatKeTiep = new PhatKeTiepPanel();
            PanelWrap.add(phatKeTiep, BorderLayout.EAST);
            PanelWrap.revalidate();
            PanelWrap.repaint();

            phatKeTiepObserver = new PhatKeTiepObserver((PhatKeTiepPanel) phatKeTiep);
            subject.attach(phatKeTiepObserver);
        }
        isMenuPhatKeTiep = !isMenuPhatKeTiep;

    }//GEN-LAST:event_btnMenuPhatKeTiepActionPerformed

    private void btnKaraokeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKaraokeActionPerformed
        // TODO add your handling code here:
        ToggleShowKaraoke();

    }//GEN-LAST:event_btnKaraokeActionPerformed

    private void ipTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipTimKiemActionPerformed
        // TODO add your handling code here:
        String keyword = ipTimKiem.getText();
        if (!keyword.isEmpty()) {
            if (threadTimKiem != null && threadTimKiem.isAlive()) {
                threadTimKiem.interrupt();
            }
            ShowPanel("TimKiem", new TimKiem(keyword));
        }
    }//GEN-LAST:event_ipTimKiemActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        goBackPanel();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvatarActionPerformed
        // TODO add your handling code here:
        if (!Utils.getIsLogin()) {
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

                MainJFrame.ShowPanel("Live", new RoomLive(name, Utils.getAnhUser(userInfo)));
            }
        });

        popupMenu.add(option2);

        popupMenu.show(btnAvatar, -30, popupMenu.getHeight() + 45);

    }//GEN-LAST:event_btnAvatarActionPerformed

    private void btnYeuThichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYeuThichActionPerformed
        // TODO add your handling code here:
        if (!Utils.getIsLogin()) {
            return;
        }

        ShowPanel("YeuThich", new YeuThich());
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
        ShowPanel("Setting", new Setting());
    }//GEN-LAST:event_btnSettingActionPerformed

    private void btnDieuKhoanVaDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDieuKhoanVaDichVuActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Tình năng này chưa được cập nhật, vui lòng thử lại sau!", "Chưa cập nhật", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btnDieuKhoanVaDichVuActionPerformed

    private void btnGioiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGioiThieuActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Tình năng này chưa được cập nhật, vui lòng thử lại sau!", "Chưa cập nhật", JOptionPane.WARNING_MESSAGE);
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
        JOptionPane.showMessageDialog(null, "Tình năng này chưa được cập nhật, vui lòng thử lại sau!", "Chưa cập nhật", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btnLienHeActionPerformed

    private void btnMiniGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiniGameActionPerformed
        // TODO add your handling code here:
//        if (AppConstants.isBlockPage || !checkLicenseJxBrowser()) {
//            MyCustomDialog customDialog = new MyCustomDialog(null, "", new PopupMessageLock());
//            customDialog.setSize(400, 300);
//            customDialog.setResizable(false);
//            customDialog.setVisible(true);
//            return;
//        }
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
        if (AppConstants.isBlockPage || !checkLicenseJxBrowser()) {
            MyCustomDialog customDialog = new MyCustomDialog(null, "", new PopupMessageLock());
            customDialog.setSize(400, 300);
            customDialog.setResizable(false);
            customDialog.setVisible(true);
            return;
        }
        ShowPanel("Radio", new Radio());
        resetTabBarColor();
        btnRadio.setBackground(new Color(102, 102, 102));
    }//GEN-LAST:event_btnRadioActionPerformed

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

    private void btnMVMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMVMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnMV);
    }//GEN-LAST:event_btnMVMouseMoved

    private void btnMVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMVMouseExited
        // TODO add your handling code here:

        endHoverMenu(btnMV);
    }//GEN-LAST:event_btnMVMouseExited

    private void btnMVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMVActionPerformed
        // TODO add your handling code here:
        ShowPanel("MV", new MVSearch("a"));
        resetTabBarColor();
        btnMV.setBackground(new Color(102, 102, 102));
    }//GEN-LAST:event_btnMVActionPerformed

    private void btnCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCommentActionPerformed
        // TODO add your handling code here:
        ToggleShowComment();
    }//GEN-LAST:event_btnCommentActionPerformed

    private void btnMicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMicMouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Tình năng này chưa được cập nhật, vui lòng thử lại sau!", "Chưa cập nhật", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btnMicMouseClicked

    private void btnNhacNenMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhacNenMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnNhacNen);
    }//GEN-LAST:event_btnNhacNenMouseMoved

    private void btnNhacNenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhacNenMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnNhacNen);
    }//GEN-LAST:event_btnNhacNenMouseExited

    private void btnNhacNenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhacNenActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Tình năng này chưa được cập nhật, vui lòng thử lại sau!", "Chưa cập nhật", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btnNhacNenActionPerformed

    private void btnThongBaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongBaoActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Tình năng này chưa được cập nhật, vui lòng thử lại sau!", "Chưa cập nhật", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btnThongBaoActionPerformed

    private void btnEventMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEventMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnEvent);
    }//GEN-LAST:event_btnEventMouseMoved

    private void btnEventMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEventMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnEvent);
    }//GEN-LAST:event_btnEventMouseExited

    private void btnEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Tình năng này chưa được cập nhật, vui lòng thử lại sau!", "Chưa cập nhật", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btnEventActionPerformed

    private void btnKhoaHocMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhoaHocMouseMoved
        // TODO add your handling code here:
        onHoverMenu(btnKhoaHoc);
    }//GEN-LAST:event_btnKhoaHocMouseMoved

    private void btnKhoaHocMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhoaHocMouseExited
        // TODO add your handling code here:
        endHoverMenu(btnKhoaHoc);
    }//GEN-LAST:event_btnKhoaHocMouseExited

    private void btnKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoaHocActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Tình năng này chưa được cập nhật, vui lòng thử lại sau!", "Chưa cập nhật", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btnKhoaHocActionPerformed

    private void lbTenCaSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenCaSiMouseClicked
        // TODO add your handling code here:
        BaiHat bh = MyMusicPlayer.dsBaiHat.get(MyMusicPlayer.position);
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
                                    ShowPanel("TimKiem", new TimKiem(keyword));
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
        btnMV.setBackground(null);

    }

    public static void initDKGiongNoi() {
        Engine engine = Engine.newInstance(EngineOptions.newBuilder(RenderingMode.OFF_SCREEN)
                .licenseKey("1BNDHFSC1G8RA8PYZSNW76QPH8UUQU4FTUIGJE23Y1HZ5EEYND8BED4IUWACS012LYXTSS").build());
        browser = engine.newBrowser();
        BrowserView view = BrowserView.newInstance(browser);

        String key = Utils.randomKeyLogin();
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

    public void updateFooterHome(BaiHat baiHat) {
        new Thread(() -> {
            String urlAnh = baiHat.getAnhBia();
            if (MyMusicPlayer.typeMusic.equals("off")) {
                System.out.println("idbh in main: " + baiHat.getId());
                urlAnh = Utils.getAnhBHDownload(baiHat.getId());
            }
            ImageIcon anhBH = Utils.getImageBaiHat(urlAnh, 50, 50);
            imageBaiHat.setIcon(anhBH);
        }).start();

        lbTenBaiHat.setText(baiHat.getTenBaiHat());
        lbTenCaSi.setText(Utils.getTenCaSi(baiHat));

        ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-pause.png"));
        btnPlayPause.setIcon(icon);

        String tongTG = Utils.getThoiGianBaiHat((int) (baiHat.getThoiGian() / 1000));
        lbTongThoiGian.setText(tongTG);

        PanelFooter.setVisible(true);

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
    public static javax.swing.JButton btnComment;
    private javax.swing.JButton btnDaTai;
    private javax.swing.JButton btnDieuKhoanVaDichVu;
    private javax.swing.JButton btnEvent;
    private javax.swing.JButton btnGioiThieu;
    public static javax.swing.JButton btnKaraoke;
    private javax.swing.JButton btnKhamPha;
    private javax.swing.JButton btnKhoaHoc;
    private javax.swing.JButton btnLienHe;
    private javax.swing.JButton btnLive;
    private javax.swing.JButton btnLoop;
    private javax.swing.JButton btnMV;
    private javax.swing.JButton btnMenuPhatKeTiep;
    private javax.swing.JLabel btnMic;
    private javax.swing.JButton btnMiniApp;
    private javax.swing.JButton btnMiniGame;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnNextMusic;
    private javax.swing.JButton btnNhacNen;
    public static javax.swing.JButton btnOption;
    public static javax.swing.JButton btnPlayPause;
    private javax.swing.JButton btnPrevBaiHat;
    private javax.swing.JButton btnRadio;
    private javax.swing.JButton btnRandom;
    private javax.swing.JButton btnSetting;
    private javax.swing.JButton btnThongBao;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
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
