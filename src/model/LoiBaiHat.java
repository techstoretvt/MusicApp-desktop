package model;

public class LoiBaiHat {
    private String id, idBaiHat, loiBaiHat;
    private Double thoiGian;

    public LoiBaiHat(String id, String idBaiHat, String loiBaiHat, Double thoiGian) {
        this.id = id;
        this.idBaiHat = idBaiHat;
        this.loiBaiHat = loiBaiHat;
        this.thoiGian = thoiGian;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(String idBaiHat) {
        this.idBaiHat = idBaiHat;
    }

    public String getLoiBaiHat() {
        return loiBaiHat;
    }

    public void setLoiBaiHat(String loiBaiHat) {
        this.loiBaiHat = loiBaiHat;
    }

    public Double getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Double thoiGian) {
        this.thoiGian = thoiGian;
    }
}
