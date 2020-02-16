package com.bddomainuni.models.entity.protocal2_1;

import com.bddomainuni.repository.tools.BDMethod;

/**
 * Created by admin on 2017/5/25.
 */

public class WAAMsg {
/*    private String msgType;
    private String freq;
    private String ic;
    private String time;
    // 经纬度
    private String LatiH;// 纬度（度）
    private String LatiM;// 纬度（分）
    private String LatiS;// 纬度（秒）
    private double Latitude;// 纬度
    private String LatiDeg;// 纬度（度分秒形式）
    private String LatiOrin;// 纬度方向
    private String LongiH;// 经度（度）
    private String LongiM;// 经度（分）
    private String LongiS;// 经度（秒）
    private double Longitude;// 经度
    private String LongiDeg;// 经度（度分秒形式）
    private String LongOrin;// 经度方向*/
    private String waaStr;
    private boolean Ifvaild;

    /**
     * 构造函数
     * 直接解析ICI字节串
     *
     * @param parambytes ICI字节串
     */
    public WAAMsg(byte[] parambytes){
        waaStr = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(waaStr);
        /*if(Ifvaild){
            String[] items = waaStr.split(",");
            String head = waaStr.substring(3, 6);
            if (head.equals("WAA")){
                msgType = items[1];
                freq = items[2];
                ic = items[3];

            }
        }
        else{

        }*/
    }
/*
    public String getMsgType() {
        return msgType;
    }

    public String getFreq() {
        return freq;
    }

    public String getIc() {
        return ic;
    }

    public String getTime() {
        return time;
    }

    public double getLatitude() {
        return Latitude;
    }

    public String getLatiDeg() {
        return LatiDeg;
    }

    public String getLatiOrin() {
        return LatiOrin;
    }

    public double getLongitude() {
        return Longitude;
    }

    public String getLongiDeg() {
        return LongiDeg;
    }

    public String getLongOrin() {
        return LongOrin;
    }

    public String getWaaStr() {
        return waaStr;
    }

    */

    public String getWaaStr() {
        return waaStr.substring(0,waaStr.indexOf("*")+3);
    }
    public boolean getVaild() {
        return Ifvaild;
    }
}
