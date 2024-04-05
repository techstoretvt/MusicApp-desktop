/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author tranv
 */
public class GetListLive {
    int errCode;
    ArrayList<LiveItem> data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public ArrayList<LiveItem> getData() {
        return data;
    }

    public void setData(ArrayList<LiveItem> data) {
        this.data = data;
    }
    
    
}
