/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import component.WrapItemMVPanel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import gson.BaiHat;
import gson.GetBaiHatById;
import gson.GetListBaiHat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.ApiServiceV1;
import services.AppConstants;
import services.utils;

/**
 *
 * @author tranv
 */
public class ChiTietMVPanel extends javax.swing.JPanel {

    public static String idMV = "a1d52f6e-63db-46b9-9bf2-7d4023e6b9e2";
    private Browser browser;

    /**
     * Creates new form ChiTietMVPanel
     */
    public ChiTietMVPanel(String idMV) {
        initComponents();
        this.idMV = idMV;
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);

        //init browser
        Engine engine = Engine.newInstance(EngineOptions.newBuilder(RenderingMode.OFF_SCREEN)
                .licenseKey(AppConstants.licenseKey_browser).build());
        browser = engine.newBrowser();
        BrowserView view = BrowserView.newInstance(browser);
        PanelVideo.add(view);

//        addVideo("zp-x4cY8fu0");
        getBaiHat(idMV);
        getListGoiYMV(idMV);
    }

    public void getBaiHat(String idMV) {

        ApiServiceV1.apiServiceV1.getBaiHatById(idMV).enqueue(new Callback<GetBaiHatById>() {
            @Override
            public void onResponse(Call<GetBaiHatById> call, Response<GetBaiHatById> rspns) {
                GetBaiHatById res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    BaiHat bh = res.getData();
                    String linkMV = bh.getLinkMV();
                    String videoId = linkMV.split("v=")[1].split("&")[0];
                    addVideo(videoId);

                    //set ui
                    ImageIcon imgBH = utils.getImageBaiHat(bh.getAnhBia(), 50, 50);
                    imgBaiHat.setIcon(imgBH);
                    lbTenBH.setText(bh.getTenBaiHat());
                    lbTenCS.setText(utils.getTenCaSi(bh));

                } else {
                    System.out.println("khong tim thay bai hat");
                }
            }

            @Override
            public void onFailure(Call<GetBaiHatById> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public void getListGoiYMV(String idMV) {
       
        Gson gson = new GsonBuilder().create();
        ArrayList<String> a = new ArrayList<>();
        a.add(idMV);
        String json = gson.toJson(a);

        ApiServiceV1.apiServiceV1.getGoiYMVBaiHat(json).enqueue(new Callback<GetListBaiHat>() {
            @Override
            public void onResponse(Call<GetListBaiHat> call, Response<GetListBaiHat> rspns) {
                GetListBaiHat res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    ArrayList<BaiHat> listBH = res.getData();
                    int size = listBH.size();

                    for (int i = 0; i < size; i = i + 2) {

                        if (i + 1 < size) {
                            BaiHat bh1 = listBH.get(i);
                            BaiHat bh2 = listBH.get(i + 1);

                            JPanel pn = new WrapItemMVPanel(bh1, bh2);
                            PanelListMV.add(pn);

                        } else {
                            BaiHat bh = listBH.get(i);

                            JPanel pn = new WrapItemMVPanel(bh, null);
                            PanelListMV.add(pn);
                        }
                    }

                } else {
                    System.out.println("khong tim thay ds mv");
                }
            }

            @Override
            public void onFailure(Call<GetListBaiHat> call, Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet.");
                // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public void addVideo(String idVideo) {

        String url = AppConstants.url_backend + "/view/youtube?id=" + idVideo;
        browser.navigation().loadUrlAndWait(url);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        PanelVideo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        imgBaiHat = new javax.swing.JLabel();
        lbTenBH = new javax.swing.JLabel();
        lbTenCS = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelListMV = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 102, 0));

        jScrollPane1.setBorder(null);

        jPanel2.setBackground(new java.awt.Color(23, 15, 35));

        PanelVideo.setBackground(new java.awt.Color(23, 15, 35));
        PanelVideo.setLayout(new java.awt.BorderLayout());

        lbTenBH.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTenBH.setForeground(new java.awt.Color(255, 255, 255));

        lbTenCS.setForeground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MV");

        PanelListMV.setBackground(new java.awt.Color(23, 15, 35));
        PanelListMV.setLayout(new javax.swing.BoxLayout(PanelListMV, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelVideo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(imgBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTenBH)
                            .addComponent(lbTenCS)))
                    .addComponent(jLabel2))
                .addGap(0, 502, Short.MAX_VALUE))
            .addComponent(PanelListMV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(PanelVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbTenBH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTenCS))
                    .addComponent(imgBaiHat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelListMV, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelListMV;
    private javax.swing.JPanel PanelVideo;
    private javax.swing.JLabel imgBaiHat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTenBH;
    private javax.swing.JLabel lbTenCS;
    // End of variables declaration//GEN-END:variables
}
