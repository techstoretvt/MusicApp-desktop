/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import model.BaiHat;
import javax.swing.JPanel;

/**
 *
 * @author tranv
 */
public class WrapItemMVPanel extends javax.swing.JPanel {

    /**
     * Creates new form WrapItemMVPanel
     */
    public WrapItemMVPanel(BaiHat bh1,BaiHat bh2) {
        initComponents();
        
        //test
        JPanel pn1 = new ItemMV(bh1);
        JPanel pn2 = new ItemMV(bh2);
        
        add(pn1);
        add(pn2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(23, 15, 35));
        setLayout(new java.awt.GridLayout(1, 0));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
