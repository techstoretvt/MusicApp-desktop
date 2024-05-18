/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gamelatbai;

import frame.Main;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 *
 * @author tranv
 */
public class TrangChu extends javax.swing.JFrame {

    ArrayList<String> listAnh = new ArrayList<>();
    int manToiDa = 1;
    int manHienTai = 1;
    ArrayList<JLabel> listLBTheBai = new ArrayList<>();
    int indexChon_mot = -1;
    int indexChon_hai = -1;
    int indexGiaTriTheChon = -1;
    ArrayList<Integer> listIndexPass = new ArrayList<>();
    int thoiGian = 120000;
    int diem = 0;
    Thread threadCountDown;

    /**
     * Creates new form TrangChu
     */
    public TrangChu() {
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //init list anh
        initListAnh();

    }

    public void initListAnh() {
        for (int i = 1; i <= 22; i++) {
            listAnh.add("/resources/bai" + i + ".jpg");
        }
        manToiDa = listAnh.size();
    }

    public void handleChoi() {
        //reset mỗi màn chơi
        manHienTai++;
        diem = 0;
        pnListBai.removeAll();
        listIndexPass = new ArrayList<>();
        listLBTheBai = new ArrayList<>();

        //lấy các giá trị cần thiết
        int w = getWidth();
        int sizeMaTran = getKichThuocMaTran(manHienTai);
        int maxW = w / sizeMaTran;
        int wAnh = (int) (maxW * 0.6);
        int hAnh = (int) (maxW * 0.8);

        //set layout cho panel
        GridLayout gridLayout = new GridLayout(0, sizeMaTran);
        pnListBai.setLayout(gridLayout);
        lbManChoi.setText("Màn: " + (manHienTai - 1));

        //tao mang vitri
        ArrayList<Integer> arrViTri = new ArrayList<>();
        for (int i = 0; i < manHienTai; i++) {
            arrViTri.add(i);
            arrViTri.add(i);
        }
        Collections.shuffle(arrViTri); // Trộn ngẫu nhiên các số

        //thêm ảnh vào panel
        for (int i = 0; i < arrViTri.size(); i++) {
            int index = i;

            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/matsau.jpg"));
            Image scaledImage = icon.getImage().getScaledInstance(wAnh, hAnh, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel lb = new JLabel();
            lb.setIcon(scaledIcon);
            lb.setPreferredSize(new Dimension(wAnh, hAnh));
            lb.setHorizontalAlignment(SwingConstants.CENTER);
            lb.setBorder(new LineBorder(Color.CYAN));
            lb.setCursor(new Cursor(Cursor.HAND_CURSOR));

            lb.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    //double click
                    if (indexChon_mot == index) {
                        System.out.println("double click");
                        return;
                    }

                    //pass
                    if (listIndexPass.indexOf(arrViTri.get(index)) != -1) {
                        System.out.println("pass");
                        return;
                    }

                    System.out.println("click");

                    //lật thẻ 1
                    if (indexGiaTriTheChon == -1 && indexChon_mot == -1 && indexChon_hai == -1) {
                        indexGiaTriTheChon = arrViTri.get(index);
                        indexChon_mot = index;

                        xuLyMoThe(lb, listAnh.get(indexGiaTriTheChon), wAnh, hAnh);
                        return;
                    }

                    //lật thẻ 2
                    indexChon_hai = index;
                    int index_mot = indexChon_mot;
                    int index_hai = indexChon_hai;
                    int index_giaTri = indexGiaTriTheChon;

                    xuLyMoThe(lb, listAnh.get(arrViTri.get(index)), wAnh, hAnh);

                    if (arrViTri.get(index) != indexGiaTriTheChon) {
                        //chọn 2 thẻ sai
                        indexChon_mot = -1;
                        indexChon_hai = -1;
                        indexGiaTriTheChon = -1;

                        new Thread(() -> {
                            System.out.println("vao");
                            try {
                                Thread.sleep(500);
                                xuLyDongThe(index_hai, wAnh, hAnh);
                                Thread.sleep(200);
                                xuLyDongThe(index_mot, wAnh, hAnh);

                            } catch (InterruptedException ex) {
                                System.out.println("Loi sleep trong lat the 2");
                            }
                        }).start();

                    } else {
                        //chọn 2 thẻ đúng
                        indexChon_mot = -1;
                        indexChon_hai = -1;
                        indexGiaTriTheChon = -1;

                        listIndexPass.add(index_giaTri);

                        diem++;

                        if (diem == manHienTai && manHienTai < manToiDa) {
                            //qua man
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                    handleChoi();
                                } catch (InterruptedException ex) {

                                }
                            }).start();

                            return;
                        }

                        if (manHienTai == manToiDa) {
                            JOptionPane.showMessageDialog(null, "Bạn đã phá đảo trò chơi.");
                            int xacNhan = JOptionPane.showConfirmDialog(null, "Bạn có muốn chơi lại không?", "Bạn đã phá đảo trò chơi.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (xacNhan == JOptionPane.OK_OPTION) {
                                manHienTai = 1;
                                handleChoi();
                            } else {
                                Main.rootFrame.setVisible(true);
                                dispose();
                            }
                        }

                    }
                }
            });

            listLBTheBai.add(lb);

            JPanel pn = new JPanel(new FlowLayout(FlowLayout.CENTER));
            pn.add(lb);
            pn.setBorder(new LineBorder(Color.BLUE));

            pnListBai.add(pn);

        }

        pnListBai.revalidate();
        pnListBai.repaint();

        handleCountDown();
    }

    public void handleCountDown() {
        thoiGian = 120000;
        if (threadCountDown != null && threadCountDown.isAlive()) {
            System.out.println("ngat");
            threadCountDown.interrupt();
        }
        threadCountDown = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    thoiGian -= 1000;
                    lbThoiGian.setText("" + (thoiGian / 1000));

                    if (thoiGian == 0) {
                        int xacNhan = JOptionPane.showConfirmDialog(null, "Bạn có muốn chơi lại không?", "Đã hết thời gian", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (xacNhan == JOptionPane.OK_OPTION) {
                            manHienTai = 1;
                            handleChoi();
                        } else {
                            Main.rootFrame.setVisible(true);
                            dispose();
                        }
                        break;
                    }

                } catch (InterruptedException ex) {
                    break;
                }
            }
        });
        threadCountDown.start();
    }

    public void xuLyMoThe(JLabel lb, String url, int wAnh, int hAnh) {
        ImageIcon icon = new ImageIcon(getClass().getResource(url));
        Image scaledImage = icon.getImage().getScaledInstance(wAnh, hAnh, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        lb.setIcon(scaledIcon);
    }

    public void xuLyDongThe(int i, int wAnh, int hAnh) {
        JLabel lb = listLBTheBai.get(i);
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/matsau.jpg"));

        Image scaledImage = icon.getImage().getScaledInstance(wAnh, hAnh, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        lb.setIcon(scaledIcon);

    }

    public int getKichThuocMaTran(int sl) {
        int kichThuot = 1;
        while (kichThuot * kichThuot < sl * 2) {
            kichThuot++;
        }
        return kichThuot;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btnChoi = new javax.swing.JButton();
        lbManChoi = new javax.swing.JLabel();
        lbThoiGian = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnListBai = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Game lật bài");

        btnChoi.setText("Chơi");
        btnChoi.setPreferredSize(new java.awt.Dimension(80, 23));
        btnChoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoiActionPerformed(evt);
            }
        });

        lbManChoi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbManChoi.setText("Màn: 1");
        lbManChoi.setPreferredSize(new java.awt.Dimension(80, 16));

        lbThoiGian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbThoiGian.setText("lbThoiGian");

        pnListBai.setLayout(new java.awt.GridLayout(2, 0));
        jScrollPane1.setViewportView(pnListBai);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnChoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbThoiGian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbManChoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbManChoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbThoiGian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoiActionPerformed
        // TODO add your handling code here:
        handleChoi();
        btnChoi.setEnabled(false);
    }//GEN-LAST:event_btnChoiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChoi;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbManChoi;
    private javax.swing.JLabel lbThoiGian;
    private javax.swing.JPanel pnListBai;
    // End of variables declaration//GEN-END:variables
}
