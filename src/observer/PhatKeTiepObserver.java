/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observer;

import component.PhatKeTiepPanel;
import model.BaiHat;

/**
 *
 * @author tranv
 */
public class PhatKeTiepObserver implements Observer {

    PhatKeTiepPanel phatKeTiep;

    public PhatKeTiepObserver(PhatKeTiepPanel phatKeTiep) {
        this.phatKeTiep = phatKeTiep;
    }

    @Override
    public void update() {
        if (PhatKeTiepPanel.isUpdate) {
            System.out.println("load data");
            phatKeTiep.loadData();
        }
        else {
            System.out.println("update index");
            phatKeTiep.updateIndex();
            
        }
    }

}
