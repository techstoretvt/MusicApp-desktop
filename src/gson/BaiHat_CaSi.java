package gson;

import java.io.Serializable;

public class BaiHat_CaSi implements Serializable{

    private String idCaSi, idBaiHat, id;
    private Casi casi;

    public BaiHat_CaSi(String idCaSi, String idBaiHat, String id, Casi casi) {
        this.idCaSi = idCaSi;
        this.idBaiHat = idBaiHat;
        this.id = id;
        this.casi = casi;
    }

    public String getIdCaSi() {
        return idCaSi;
    }

    public void setIdCaSi(String idCaSi) {
        this.idCaSi = idCaSi;
    }

    public String getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(String idBaiHat) {
        this.idBaiHat = idBaiHat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Casi getCasi() {
        return casi;
    }

    public void setCasi(Casi casi) {
        this.casi = casi;
    }
}
