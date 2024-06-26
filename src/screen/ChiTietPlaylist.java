/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import component.CustomScrollBarUI;
import component.ItemMusic;
import model.BaiHat;
import model.ChiTietDanhSachPhat;
import model.DanhSachPhat;
import model.GetDSPhatById;
import model.ResponseDefault;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import api.ApiServiceV1;
import component.ItemPlaylist;
import services.MyMusicPlayer;
import helpers.Utils;
import frame.MainJFrame;

/**
 *
 * @author tranv
 */
public class ChiTietPlaylist extends javax.swing.JPanel {

    public static String idPlaylist = "59408314-7bbb-4842-882f-ef946a858902";
    public static ArrayList<BaiHat> dsBaiHat;

    private DanhSachPhat playlist;

    /**
     * Creates new form ChiTietPlaylistPanel
     */
    public ChiTietPlaylist(String idPlaylist) {
        initComponents();

        this.idPlaylist = idPlaylist;
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        //test
        getPlaylist(idPlaylist);
    }

    public void getPlaylist(String idPlaylist) {
        String header = Utils.getHeader();
        dsBaiHat = new ArrayList<>();

        ApiServiceV1.apiServiceV1.getDanhSachPhatById(idPlaylist, header).enqueue(new Callback<GetDSPhatById>() {
            @Override
            public void onResponse(Call<GetDSPhatById> call, Response<GetDSPhatById> rspns) {
                GetDSPhatById res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    PanelListMusic.removeAll();
                    playlist = res.getData();
                    ArrayList<ChiTietDanhSachPhat> listBH = playlist.getChiTietDanhSachPhats();
                    int size = listBH.size();

                    // add info playlist
                    if (size == 0) {
                        ImageIcon icon = Utils.getImageBaiHat(getClass().getResource("/icon/playlist-empty.png").toString(),
                                200, 200);
                        imgPlaylist.setIcon(icon);
                    } else {
                        ImageIcon icon = Utils.getImageBaiHat(listBH.get(0).getBaihat().getAnhBia(),
                                200, 200);
                        imgPlaylist.setIcon(icon);
                    }
                    lbTenDS.setText(playlist.getTenDanhSach());

                    // add list music
                    for (int i = 0; i < size; i++) {
                        ChiTietDanhSachPhat bh = listBH.get(i);
                        JPanel pnMusic = new ItemMusic(bh.getBaihat(), i + 1, "Playlist");
                        PanelListMusic.add(pnMusic);

                        dsBaiHat.add(bh.getBaihat());
                    }
                    PanelListMusic.revalidate();
                    PanelListMusic.repaint();

                } else {
                    System.out.println("ko tim thay playlist");
                }
            }

            @Override
            public void onFailure(Call<GetDSPhatById> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });

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
        imgPlaylist = new javax.swing.JLabel();
        lbTenDS = new javax.swing.JLabel();
        btnOption = new javax.swing.JButton();
        btnPhatNhac = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        PanelListMusic = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(23, 15, 35));

        jPanel1.setBackground(new java.awt.Color(23, 15, 35));

        imgPlaylist.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgPlaylist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loadingBH.gif"))); // NOI18N

        lbTenDS.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTenDS.setForeground(new java.awt.Color(255, 255, 255));
        lbTenDS.setText("Đang tải...");

        btnOption.setBackground(new java.awt.Color(23, 15, 35));
        btnOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-more-20.png"))); // NOI18N
        btnOption.setBorder(null);
        btnOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOption.setFocusPainted(false);
        btnOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionActionPerformed(evt);
            }
        });

        btnPhatNhac.setBackground(new java.awt.Color(204, 0, 204));
        btnPhatNhac.setForeground(new java.awt.Color(255, 255, 255));
        btnPhatNhac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-play.png"))); // NOI18N
        btnPhatNhac.setText("Phát nhạc");
        btnPhatNhac.setFocusPainted(false);
        btnPhatNhac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhatNhacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPhatNhac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imgPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbTenDS)
                                .addGap(18, 18, 18)
                                .addComponent(btnOption, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 28, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOption, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTenDS, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnPhatNhac)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(null);

        PanelListMusic.setBackground(new java.awt.Color(23, 15, 35));
        PanelListMusic.setLayout(new javax.swing.BoxLayout(PanelListMusic, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Đang tải...");
        PanelListMusic.add(jLabel1);

        jScrollPane1.setViewportView(PanelListMusic);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPhatNhacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhatNhacActionPerformed
        // TODO add your handling code here:
        MyMusicPlayer.initMusicPlayer(dsBaiHat, 0);
    }//GEN-LAST:event_btnPhatNhacActionPerformed

    private void btnOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionActionPerformed
        // TODO add your handling code here:
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem option1 = new JMenuItem("Chỉnh sửa");
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/icon/icon-edit-black.png"));
        option1.setIcon(icon1);
        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String namePlaylist =(String) JOptionPane.showInputDialog(null, "Nhập tên danh sách:",
                        "Chỉnh sửa",
                        JOptionPane.INFORMATION_MESSAGE, icon1, null, playlist.getTenDanhSach());
                if (namePlaylist != null) {
                   handleDoiTenDS(playlist.getId(),namePlaylist);
                } 
            }
        });

        JMenuItem option2 = new JMenuItem("Xóa playlist");
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/icon/icon-delete-black.png"));
        option2.setIcon(icon2);
        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int kq = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa playlist này?","",
                        JOptionPane.YES_NO_OPTION);
                if(kq == 0){
                    handelDeletePlaylist(playlist.getId());
                }
            }
        });

        popupMenu.add(option1);
        popupMenu.add(option2);

        popupMenu.show(btnOption, 0, -popupMenu.getHeight() - 50);
    }//GEN-LAST:event_btnOptionActionPerformed

    public void handelDeletePlaylist(String id) {
        String header = Utils.getHeader();
        
        ApiServiceV1.apiServiceV1.xoaDanhSachPhatById(id, header).enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> rspns) {
                ResponseDefault res = rspns.body();
                if (res  != null && res.getErrCode() == 0) {
                    MainJFrame.goBackPanel();
                    MainJFrame.resetPanel();
                }
                else {
                    System.out.println("Loi xoa danh sach");
                    JOptionPane.showMessageDialog(null, res.getErrMessage(),
                                "Thông báo",JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }
    
    public void handleDoiTenDS(String id, String name) {
        String header = Utils.getHeader();
        
        ApiServiceV1.apiServiceV1.doiTenDanhSach(id, name, header).enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> rspns) {
                ResponseDefault res = rspns.body();
                if(res!=null && res.getErrCode() == 0){
                    lbTenDS.setText(name);
                    playlist.setTenDanhSach(name);
                    if(ItemPlaylist.tenDS != null) {
                        ItemPlaylist.tenDS.setText(name);
                    }
                    
                }
                else {
                    System.out.println("Loi chinh sua danh sach");
                    JOptionPane.showMessageDialog(null, res.getErrMessage(),
                                "Thông báo",JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelListMusic;
    private javax.swing.JButton btnOption;
    private javax.swing.JButton btnPhatNhac;
    private javax.swing.JLabel imgPlaylist;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTenDS;
    // End of variables declaration//GEN-END:variables
}
