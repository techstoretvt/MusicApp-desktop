package model;

public class GetBaiHatById {
    private int errCode, status;
    private String errMessage;
    private BaiHat data;

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

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public BaiHat getData() {
        return data;
    }

    public void setData(BaiHat data) {
        this.data = data;
    }
}
