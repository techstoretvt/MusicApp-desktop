/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import gson.BaiHat;
import gson.BaiHat_CaSi;
import gson.Casi;
import gson.LoiBaiHat;
import gson.ResponseDefault;
import gson.TaiKhoan;
import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import panels.ItemMusicPanel;
import panels.PanelLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import view.MainJFrame;

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
            System.out.println("loi url");
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
            int tg = (int) current.getThoiGian().intValue();
            map.put(String.valueOf(tg), i);

        }

        // Duyệt qua tất cả các cặp key/value trong HashMap
//        for (String key : map.keySet()) {
//            int val = map.get(key);
//            System.out.println("Key: " + key + ", Value: " + val);
//        }
        return map;
    }

    public static String getHeader() {
        String accessToken = LocalData.getData("accessToken");
        if (accessToken == null) {
            return null;
        }
        return "bearer " + accessToken;
    }

    public static void CheckLogin() {
        String header = getHeader();
        if (header == null) {
            System.out.println("vao 1");
            MainJFrame.isLogin = false;
            return;
        }

        ApiServiceV1.apiServiceV1.checkLogin(header).enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> rspns) {
                System.out.println("vao 2");
                ResponseDefault res = rspns.body();
                if (res != null && res.getErrCode() == 0) {
                    MainJFrame.isLogin = true;
                } else {
                    MainJFrame.isLogin = false;
                }

            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable thrwbl) {
                MainJFrame.isLogin = false;
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public static boolean getIsLogin() {
        // check login
        
        
        // show login
        if(!MainJFrame.isLogin) {
            MyCustomDialog dialog = new MyCustomDialog(null, "Đăng nhập", new PanelLogin());
            PanelLogin.dialog = dialog;
            dialog.setVisible(true);
        }

        return MainJFrame.isLogin;
    }

    public static String getAnhUser(TaiKhoan tk) {
        String urlAnh = "";
        String urlDefault = "https://res.cloudinary.com/dultkpqjp/image/upload/v1683860764/avatar_user/no-user-image_axhl6d.png";
        if (tk.getTypeAccount().equals("web")) {
            if (tk.getAvatarUpdate() != null) {
                urlAnh = tk.getAvatarUpdate();
            } else {
                urlAnh = urlDefault;
            }
        } else if (tk.getTypeAccount().equals("google")) {
            if (tk.getAvatarUpdate() != null) {
                urlAnh = tk.getAvatarUpdate();
            } else {
                urlAnh = tk.getAvatarGoogle();
            }
        } else if (tk.getTypeAccount().equals("facebook")) {
            if (tk.getAvatarUpdate() != null) {
                urlAnh = tk.getAvatarUpdate();
            } else {
                urlAnh = tk.getAvatarFacebook();
            }
        }

        return urlAnh;
    }
    
    public static String randomKeyLogin() {
        int length = 10;
        // Chuỗi chứa các ký tự mà bạn muốn sử dụng
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Tạo một đối tượng SecureRandom để tạo số ngẫu nhiên
        SecureRandom random = new SecureRandom();

        // Tạo một StringBuilder để xây dựng chuỗi ngẫu nhiên
        StringBuilder sb = new StringBuilder(length);

        // Tạo chuỗi ngẫu nhiên với độ dài cho trước
        for (int i = 0; i < length; i++) {
            // Lấy một ký tự ngẫu nhiên từ chuỗi characters
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);

            // Thêm ký tự ngẫu nhiên vào chuỗi
            sb.append(randomChar);
        }

        // Trả về chuỗi ngẫu nhiên
        return sb.toString();
    }
    
    public static String getAnhBHDownload(String id) {
        String a = null;
        try {
            a = MainJFrame.class.getResource("/download/"+id+".jpg").toString();
        }
        catch(Exception e) {
            
        }
        return a;
    }
    
    public static String getUrlBHDownload(String id) {
        String file_path = System.getProperty("user.dir")
                    + File.separator + "src"
                    + File.separator + "download" + File.separator + id +".mp3";
        return file_path;
    }

}
