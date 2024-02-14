/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.io.File;

/**
 *
 * @author tranv
 */
public class AppConstants {

    public static String type_env = "dev";

    public static String url_socket = type_env.equals("dev") ? "http://localhost:4000" : "";

    public static String url_login = type_env.equals("dev") ? "http://localhost:3000/account/login" : "";

    public static String url_backend = type_env.equals("dev") ? "http://localhost:4000" : "";
    
     public static String url_frontend = type_env.equals("dev") ? "http://localhost:3000" : "";
    
}
