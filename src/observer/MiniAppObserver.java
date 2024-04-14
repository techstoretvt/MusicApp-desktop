/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observer;

import frame.MiniAppFrame;
import model.BaiHat;

/**
 *
 * @author tranv
 */
public class MiniAppObserver implements Observer{

    private MiniAppFrame frame;

    public MiniAppObserver(MiniAppFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void update() {
//        System.out.println("update mini app");
       frame.loadGiaoDienBaiHat();
    }
    
}
