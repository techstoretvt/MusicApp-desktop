package services;

import helpers.AppConstants;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;

public class MySocketClient {

    public static Socket socket = null;

    public MySocketClient() {
       
    }

    public static Socket getSocket() {
        try {
            if (socket == null) {
                socket = IO.socket(AppConstants.url_socket);
                socket.connect();
                System.out.println("Ket nối Socket success");
            }
           

        } catch (URISyntaxException e) {
            System.out.println("Loi ket nối");
            e.printStackTrace();
        }
       return socket;
    }

}
