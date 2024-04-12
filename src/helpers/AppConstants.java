/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

/**
 *
 * @author tranv
 */
public class AppConstants {

    public static String type_env = "pro";

    public static String url_socket = type_env.equals("dev") ? "http://localhost:4000" : "https://techstoretvtserver2.onrender.com";

    public static String url_login = type_env.equals("dev") ? "http://localhost:3000/account/login" : "https://thoaidev.store/account/login";

    public static String url_backend = type_env.equals("dev") ? "http://localhost:4000" : "https://techstoretvtserver2.onrender.com";
    
    public static String url_backend_live = type_env.equals("dev") ? "http://localhost:3030" : "https://livestream-server.onrender.com";

    public static String url_frontend = type_env.equals("dev") ? "http://localhost:3000" : "https://thoaitranstore.vercel.app";
    
    public static String url_livestream = type_env.equals("dev") ? "http://localhost:3030" : "https://livestream-server.onrender.com";

    public static String licenseKey_browser = 
            "OK6AEKNYF1EJ8GAVIH56GM8LGL6UFI8LQDEIR5NVXJW6BYLWH3TC1UMCPIS0LH6NBVQP50WD7COT3IXC8GF6F0XFPUPNJ2FO3SA9QQIJZ6G6HHSVECW2M1T30KY4URESFWTWNZPBH1DLASHPA";

}
