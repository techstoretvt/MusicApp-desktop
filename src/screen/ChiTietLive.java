/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import component.ItemComment;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import component.CustomScrollBarUI;
import model.NewComentLive;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import helpers.AppConstants;
import services.MySocketClient;
import helpers.Utils;
import frame.MainJFrame;

/**
 *
 * @author tranv
 */
public class ChiTietLive extends javax.swing.JPanel {

    private Browser browser;

    private boolean isLoading = false;

    private String idRoom, avatarRoom;

    private Thread threadAddCmt;


    /**
     * Creates new form ChiTietLivePanel
     */
    public ChiTietLive(String idRoom, String avatarRoom) {
        initComponents();

        this.idRoom = idRoom;
        this.avatarRoom = avatarRoom;

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        new Thread(() -> {
            Engine engine = Engine.newInstance(EngineOptions.newBuilder(RenderingMode.OFF_SCREEN)
                    .licenseKey(AppConstants.licenseKey_browser).build());
            browser = engine.newBrowser();
            BrowserView view = BrowserView.newInstance(browser);

            String url = AppConstants.url_livestream + "/receiver?id=" + idRoom;
            browser.navigation().loadUrlAndWait(url);

            panelVideo.add(view);
            panelVideo.revalidate();
            panelVideo.repaint();
        }).start();

        JPanel newPnCmt = new ItemComment(new NewComentLive("Chào mừng bạn đã đến", "author", avatarRoom));

        panelListCmt.add(newPnCmt);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("vao 1");
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                System.out.println("vao 2");
            }

            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("vao 3");
            }

            @Override
            public void componentHidden(ComponentEvent e) {

                if (isLoading) {
                    System.out.println("thoat panel");
                    browser.close();
                }
                isLoading = true;

            }
        });

        Socket mSocket = MySocketClient.getSocket();
        mSocket.on("room-close-" + idRoom, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                // Xử lý dữ liệu từ máy chủ ở đây
                removeAll();

                JLabel lb = new JLabel("Chủ phòng đã đi vắng");
                lb.setForeground(Color.WHITE);
                lb.setHorizontalAlignment(lb.CENTER);

                add(lb);

                revalidate();
                repaint();

            }
        });
        mSocket.on("new-cmt-live-" + idRoom, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                // Xử lý dữ liệu từ máy chủ ở đây
                Gson gson = new Gson();
                NewComentLive newCmt = gson.fromJson(args[0].toString(), NewComentLive.class);

                JPanel newPnCmt = new ItemComment(newCmt);

                panelListCmt.add(newPnCmt);

                if(threadAddCmt!=null) {
                    threadAddCmt.interrupt();
                }

                threadAddCmt = new Thread(() -> {
                   
                    try {
                        Thread.sleep(1000);
                       
                        System.out.println("Vao thread add cmt");
                        Rectangle rect = new Rectangle(0, 200, 10, 10);
                        newPnCmt.scrollRectToVisible(rect);
                    } catch (InterruptedException ex) {
                        System.out.println("'vao loi");
                    }
                });
                threadAddCmt.start();

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

        wrapCmt = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelListCmt = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        ipComment = new javax.swing.JTextField();
        btnComment = new javax.swing.JButton();
        panelVideo = new javax.swing.JPanel();

        setBackground(new java.awt.Color(23, 15, 35));
        setLayout(new java.awt.BorderLayout());

        wrapCmt.setBackground(new java.awt.Color(0, 204, 204));
        wrapCmt.setPreferredSize(new java.awt.Dimension(300, 450));
        wrapCmt.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        panelListCmt.setBackground(new java.awt.Color(23, 15, 35));
        panelListCmt.setLayout(new javax.swing.BoxLayout(panelListCmt, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(panelListCmt);

        wrapCmt.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(wrapCmt, java.awt.BorderLayout.LINE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(23, 15, 35));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        ipComment.setBorder(null);
        ipComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipCommentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ipComment, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ipComment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnComment.setBackground(new java.awt.Color(102, 0, 102));
        btnComment.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnComment.setForeground(new java.awt.Color(255, 255, 255));
        btnComment.setText("Gửi");
        btnComment.setFocusPainted(false);
        btnComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCommentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnComment)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnComment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelVideo.setBackground(new java.awt.Color(51, 0, 204));
        panelVideo.setLayout(new java.awt.BorderLayout());
        jPanel2.add(panelVideo, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCommentActionPerformed
        // TODO add your handling code here:
        addComment();
    }//GEN-LAST:event_btnCommentActionPerformed

    private void ipCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipCommentActionPerformed
        // TODO add your handling code here:
        addComment();
    }//GEN-LAST:event_ipCommentActionPerformed

    public void addComment() {
        if (!Utils.getIsLogin()) {
            return;
        }

        String noiDung = ipComment.getText();
        if (noiDung.isEmpty()) {
            return;
        }

        String nameUser = MainJFrame.userInfo.getFirstName() + " " + MainJFrame.userInfo.getLastName();
        String avatar = Utils.getAnhUser(MainJFrame.userInfo);

        Socket mSocket = MySocketClient.getSocket();
        mSocket.emit("new-cmt-live", idRoom, noiDung, nameUser, avatar);
        ipComment.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComment;
    private javax.swing.JTextField ipComment;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelListCmt;
    private javax.swing.JPanel panelVideo;
    private javax.swing.JPanel wrapCmt;
    // End of variables declaration//GEN-END:variables
}
