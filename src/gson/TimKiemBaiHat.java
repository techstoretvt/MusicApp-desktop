package gson;

import java.util.ArrayList;

class Item {
    private BaiHat item;

    public BaiHat getItem() {
        return item;
    }

    public void setItem(BaiHat item) {
        this.item = item;
    }
}

public class TimKiemBaiHat {
    private int errCode, status;
    private String errMessage;
    private ArrayList<Item> data;

    public TimKiemBaiHat(int errCode, int status, ArrayList<Item> data) {
        this.errCode = errCode;
        this.status = status;
        this.data = data;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Item> getData() {
        return data;
    }

    public void setData(ArrayList<Item> data) {
        this.data = data;
    }

    public BaiHat getItemIndex(int index) {
        return data.get(index).getItem();
    }
}
