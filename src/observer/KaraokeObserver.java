/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observer;

import component.KaraokePanel;

/**
 *
 * @author tranv
 */
public class KaraokeObserver implements Observer{
    KaraokePanel pnKaraoke;

    public KaraokeObserver(KaraokePanel pnKaraoke) {
        this.pnKaraoke = pnKaraoke;
    }
    
    @Override
    public void update() {
       pnKaraoke.getLoiBaiHat();
    }
    
}
