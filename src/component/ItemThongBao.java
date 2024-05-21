/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import helpers.Utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import model.ThongBao;

/**
 *
 * @author tranv
 */
public class ItemThongBao extends javax.swing.JPanel {

    /**
     * Creates new form ItemThongBao
     */
    ThongBao tb;

    public ItemThongBao(ThongBao tb) {
        initComponents();
        this.tb = tb;

        setUI();
    }

    private void setUI() {
        new Thread(() -> {
            ImageIcon icon = Utils.getImageBaiHat(tb.getUrlImage(), 100, 100);
            lblAnhTB.setIcon(icon);
        }).start();

        lblTieuDe.setText(tb.getTitle());
        
        String content = tb.getContent();
        content += content;
        Pattern pattern = Pattern.compile("\\s+"); // Tìm kiếm một hoặc nhiều khoảng trắng
        Matcher matcher = pattern.matcher(content);
        tfNoiDung.setText( matcher.replaceAll(" "));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAnhTB = new javax.swing.JLabel();
        lblTieuDe = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tfNoiDung = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(23, 15, 35));

        lblAnhTB.setPreferredSize(new java.awt.Dimension(100, 100));

        lblTieuDe.setForeground(new java.awt.Color(255, 255, 255));
        lblTieuDe.setText("jLabel2");

        jScrollPane1.setBorder(null);

        tfNoiDung.setBackground(new java.awt.Color(23, 15, 35));
        tfNoiDung.setColumns(20);
        tfNoiDung.setForeground(new java.awt.Color(255, 255, 255));
        tfNoiDung.setLineWrap(true);
        tfNoiDung.setRows(5);
        tfNoiDung.setWrapStyleWord(true);
        jScrollPane1.setViewportView(tfNoiDung);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnhTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTieuDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTieuDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblAnhTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnhTB;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JTextArea tfNoiDung;
    // End of variables declaration//GEN-END:variables

}