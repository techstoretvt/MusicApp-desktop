package model;

public class TaiKhoan {
    private String id;
    private String firstName;
    private String lastName;
    private String idTypeUser;
    private String typeAccount;
    private String avatar;
    private String avatarUpdate;
    private String avatarGoogle;
    private String avatarFacebook;

    public String getAvatarFacebook() {
        return avatarFacebook;
    }

    public void setAvatarFacebook(String avatarFacebook) {
        this.avatarFacebook = avatarFacebook;
    }

    public String getAvatarGoogle() {
        return avatarGoogle;
    }

    public void setAvatarGoogle(String avatarGoogle) {
        this.avatarGoogle = avatarGoogle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdTypeUser() {
        return idTypeUser;
    }

    public void setIdTypeUser(String idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarUpdate() {
        return avatarUpdate;
    }

    public void setAvatarUpdate(String avatarUpdate) {
        this.avatarUpdate = avatarUpdate;
    }
}
