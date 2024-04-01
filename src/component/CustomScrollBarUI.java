/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author tranv
 */
public class CustomScrollBarUI extends BasicScrollBarUI {

    private final int SCROLL_BAR_SIZE = 10;
    
    @Override
    protected void configureScrollBarColors() {
        thumbColor = new Color(233, 156, 255); // Màu của thanh cuộn
        trackColor = new Color(23, 15, 35); // Màu của track
        
    }
    
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }
    
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }
    
    private JButton createZeroButton() {
        JButton button = new JButton();
        Dimension zeroDim = new Dimension(0, 0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

//    @Override
//    protected Dimension getMinimumThumbSize() {
//        return new Dimension(SCROLL_BAR_SIZE, SCROLL_BAR_SIZE);
//    }
//
//    @Override
//    protected Dimension getMaximumThumbSize() {
//        return new Dimension(SCROLL_BAR_SIZE, SCROLL_BAR_SIZE);
//    }
    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(SCROLL_BAR_SIZE, SCROLL_BAR_SIZE);
    }
    
}
