/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import gson.BaiHat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author tranv
 */
public class LocalData {
    
    public static void setData(String key, String data) {
        Properties properties = new Properties();
        File file = new File("data.properties");
        try {
            // Đọc dữ liệu từ file vào đối tượng Properties
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            fileInputStream.close();

            // Thêm dữ liệu mới
            properties.setProperty(key, data);

            // Ghi đối tượng Properties đã cập nhật vào file
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, "Data");
            fileOutputStream.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    public static void setData(String key, String data) {
//        Properties properties = new Properties();
//        properties.setProperty(key, data);
//        File file = new File("data.properties");
//        try {
//            properties.store(new FileOutputStream(file), "Data");
//        } catch (FileNotFoundException ex) {
//
//        } catch (IOException ex) {
//
//        }
//    }

    public static String getData(String key) {
        Properties properties = new Properties();

        // Đọc dữ liệu từ file
        File file = new File("data.properties");
        try {
            properties.load(new FileInputStream(file));
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
        }

        // Lấy dữ liệu
        String value = properties.getProperty(key);
        return value;
    }

    public static void removeData(String key) {
        Properties properties = new Properties();
        properties.remove(key);
//        properties.setProperty("key2", "value2");
        File file = new File("data.properties");
        try {
            properties.store(new FileOutputStream(file), "Data");
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }

    public static void saveListDownLoad(ArrayList<BaiHat> listBH) {
        String filename = "dataDownload.txt";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(listBH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveListDaNghe(ArrayList<BaiHat> listBH) {
        String filename = "dataListDaNghe.txt";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(listBH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<BaiHat> getListDownload() {
        String filename = "dataDownload.txt";
        ArrayList<BaiHat> listBH = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            listBH = (ArrayList<BaiHat>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listBH;
    }
    
    public static ArrayList<BaiHat> getListDaNghe() {
        String filename = "dataListDaNghe.txt";
        ArrayList<BaiHat> listBH = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            listBH = (ArrayList<BaiHat>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listBH;
    }
}
