/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import component.ItemCommentPanel;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import gson.NewComentLive;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import services.AppConstants;
import services.MySocketClient;
import services.utils;

/**
 *
 * @author tranv
 */
public class LivePanel extends javax.swing.JPanel {

    private Browser browser;
    private boolean isLoading = false;
    String idRoom;
    private Thread threadAddCmt;

    /**
     * Creates new form LivePanel
     */
    public LivePanel(String name, String avatar) {
        initComponents();

        idRoom = utils.randomKeyLogin();
        System.out.println("idRoom: " + idRoom);
        new Thread(() -> {
            Engine engine = Engine.newInstance(EngineOptions.newBuilder(RenderingMode.OFF_SCREEN)
                    .licenseKey(AppConstants.licenseKey_browser).build());
            browser = engine.newBrowser();
            BrowserView view = BrowserView.newInstance(browser);

            String url = AppConstants.url_livestream + "/streamer?id=" + idRoom + "&name=" + name
                    + "&avatar=" + avatar;
            browser.navigation().loadUrlAndWait(url);

            panelVideo.add(view);
            panelVideo.revalidate();
            panelVideo.repaint();
        }).start();

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

                    Socket mSocket = MySocketClient.getSocket();
                    mSocket.emit("room-close", idRoom);
                }
                isLoading = true;

            }
        });

        Socket mSocket = MySocketClient.getSocket();
        mSocket.on("new-cmt-live-" + idRoom, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                // Xử lý dữ liệu từ máy chủ ở đây
                Gson gson = new Gson();
                NewComentLive newCmt = gson.fromJson(args[0].toString(), NewComentLive.class);

                JPanel newPnCmt = new ItemCommentPanel(newCmt);

                panelListCmt.add(newPnCmt);

                if (threadAddCmt != null) {
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelListCmt = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelWrap = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelVideo = new javax.swing.JPanel();

        setBackground(new java.awt.Color(0, 0, 51));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(23, 15, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 462));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(null);

        panelListCmt.setBackground(new java.awt.Color(23, 15, 35));
        panelListCmt.setLayout(new javax.swing.BoxLayout(panelListCmt, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane2.setViewportView(panelListCmt);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.LINE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelWrap.setBackground(new java.awt.Color(204, 204, 0));
        panelWrap.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelVideo.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(panelVideo);

        panelWrap.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(panelWrap, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelListCmt;
    private javax.swing.JPanel panelVideo;
    private javax.swing.JPanel panelWrap;
    // End of variables declaration//GEN-END:variables
}