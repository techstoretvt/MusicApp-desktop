/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observer;

import frame.MainJFrame;
import model.BaiHat;
import services.MyMusicPlayer;

/**
 *
 * @author tranv
 */
public class FooterHomeObserver implements Observer {

    MainJFrame frame;
    

    public FooterHomeObserver(MainJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void update() {
        BaiHat baiHat = MyMusicPlayer.dsBaiHat.get(MyMusicPlayer.position);
        frame.updateFooterHome(baiHat);
    }

}
