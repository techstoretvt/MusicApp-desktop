/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import services.MyMusicPlayer;

/**
 *
 * @author tranv
 */
public class BtnPauseObserver implements Observer {

    JButton btn;
    JLabel btnLB;

    public BtnPauseObserver(JButton btn) {
        this.btn = btn;
    }

    public BtnPauseObserver(JLabel btnLB) {
        this.btnLB = btnLB;
    }

    @Override
    public void update() {
        ImageIcon icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-play.png"));
        if(MyMusicPlayer.isPlay == true) {
            icon = new ImageIcon(MyMusicPlayer.class.getResource("/icon/icon-pause.png"));
        }
        
        if (btn != null) {
            btn.setIcon(icon);
        }

        if (btnLB != null) {
            btnLB.setIcon(icon);
        }

    }

}
