/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frame;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
//            homeJFrame.setAlwaysOnTop(true);
//            homeJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            if (SystemTray.isSupported()) {
                SystemTray systemTray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().getImage(homeJFrame.getClass().getResource("/icon/anh-bia-am-nhac2.jpg"));
                TrayIcon trayIcon = new TrayIcon(image, "Music App");
                trayIcon.setImageAutoSize(true);

                // Thêm sự kiện cho Double-click để hiển thị cửa sổ JFrame
                trayIcon.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                             if (!MiniAppFrame.isOpen) {
                                homeJFrame.setVisible(true);
                            }
                        }
//                        if (e.getClickCount() == 2) {
//                            if (!MiniAppFrame.isOpen) {
//                                homeJFrame.setVisible(true);
//                            }
//                        }
                    }
                });

                try {
                    systemTray.add(trayIcon);
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("System tray is not supported");
            }

            homeJFrame.setVisible(true);

            rootFrame = homeJFrame;
        });
    }

}
