/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gson;

/**
 *
 * @author tranv
 */
public class NewComentLive {
    
    private String noiDung, nameUser, avatar;

    public NewComentLive(String noiDung, String nameUser, String avatar) {
        this.noiDung = noiDung;
        this.nameUser = nameUser;
        this.avatar = avatar;
    }
    
    

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    
    
}
