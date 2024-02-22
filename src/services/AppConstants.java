/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author tranv
 */
public class AppConstants {

    public static String type_env = "pro";

    public static String url_socket = type_env.equals("dev") ? "http://localhost:4000" : "https://techstoretvtserver2.onrender.com";

    public static String url_login = type_env.equals("dev") ? "http://localhost:3000/account/login" : "";

    public static String url_backend = type_env.equals("dev") ? "http://localhost:4000" : "https://techstoretvtserver2.onrender.com";
    
    public static String url_backend_live = type_env.equals("dev") ? "http://localhost:3030" : "";

    public static String url_frontend = type_env.equals("dev") ? "http://localhost:3000" : "";
    
    public static String url_livestream = type_env.equals("dev") ? "http://localhost:3030" : "";

    public static String licenseKey_browser = 
            "4UNGPZMYCQW07ROIEN6UIELV0HX8GF7EXFSETBXM1EK7J955QEZTW35RQNS9U59LXYAZ66RG5ETCZ6IRX1BCC9M2S2RB30YSUSG7Z1SAVJMX4MRG4NJ3L2RR0R5U2VDENNPHKKN1U8KSG6P5NRK";

}
