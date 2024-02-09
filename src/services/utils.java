/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import gson.BaiHat;
import gson.BaiHat_CaSi;
import gson.Casi;
import gson.LoiBaiHat;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import panels.ItemMusicPanel;

/**
 *
 * @author tranv
 */
public class utils {

    public static String getThoiGianBaiHat(int giay) {
        if (giay < 0) {
            return "Invalid input";
        }

        int minutes = giay / 60;
        int remainingSeconds = giay % 60;

        String formattedTime = String.format("%d:%02d", minutes, remainingSeconds);
        return formattedTime;
    }

    public static ImageIcon getImageBaiHat(String url, int w, int h) {
        URL imageUrl = null;
        try {
            imageUrl = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ItemMusicPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon originalIcon = new ImageIcon(imageUrl);

        // Thu nhỏ ảnh
        Image scaledImage = originalIcon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        return scaledIcon;
    }

    public static String getTenCaSi(BaiHat bh) {
        if (bh == null) {
            return "";
        }

        String tencs = "";
        ArrayList<BaiHat_CaSi> listcs = bh.getBaiHat_caSis();
        for (int i = 0; i < listcs.size(); i++) {
            Casi cs = listcs.get(i).getCasi();
            if (cs != null) {
                tencs += (i == 0 ? "" : ", ") + cs.getTenCaSi();
            }
        }

        return tencs;
    }

    public static HashMap createListIndexLoiBaiHat(ArrayList<LoiBaiHat> listLoiBH) {

        HashMap<String, Integer> map = new HashMap<>();

        int sizeLoiBH = listLoiBH.size();

        for (int i = 0; i < sizeLoiBH; i++) {
            LoiBaiHat current = listLoiBH.get(i);
            int tg =(int) current.getThoiGian().intValue();
            map.put(String.valueOf(tg), i);
           
        }
        
         // Duyệt qua tất cả các cặp key/value trong HashMap
//        for (String key : map.keySet()) {
//            int val = map.get(key);
//            System.out.println("Key: " + key + ", Value: " + val);
//        }

        return map;
    }

}