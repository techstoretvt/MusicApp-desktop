/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author tranv
 */
public class Main {
    
    public static JFrame rootFrame;

    public static void main(String[] args) {
//       LoginJFrame loginJFrame = new LoginJFrame();
//       loginJFrame.setLocationRelativeTo(null);
//       loginJFrame.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            MainJFrame homeJFrame = new MainJFrame();
            homeJFrame.setLocationRelativeTo(null);
            homeJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            homeJFrame.setVisible(true);
            
            rootFrame = homeJFrame;
        });
    }

}
