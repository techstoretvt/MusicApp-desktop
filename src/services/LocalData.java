/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranv
 */
public class LocalData {
    
    public static void setData(String key,String data) {
         Properties properties = new Properties();
        properties.setProperty(key, data);
//        properties.setProperty("key2", "value2");
        File file = new File("data.properties");
        try {
            properties.store(new FileOutputStream(file), "Data");
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }
    }
    
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
}
