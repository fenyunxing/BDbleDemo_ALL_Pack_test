package com.bddomain40.models.entity.protocal4_0;

import com.bddomain40.repository.tools.BDMethod;

/**
 * 时间信息类
 *
 * Created by admin on 2017/5/24.
 */

public class SJXXMsg {
    private boolean vaild = false;
    private String ic;
    private String sjxxHexStr;
    private byte[] sjxxByte;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String second;

    public SJXXMsg(byte[] parambytes) {
        vaild = BDMethod.CheckCKS_40(parambytes);
        if (vaild) {
            this.sjxxHexStr = BDMethod.castBytesToHexString(parambytes);
            this.sjxxByte = parambytes;
            this.ic = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[7], parambytes[8], parambytes[9]});
            this.year = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, parambytes[10], parambytes[11]});
            this.month = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[12]});
            this.day = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[13]});
            this.hour = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[14]});
            this.minute = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[15]});
            this.second = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[16]});
        }
    }

    public boolean isVaild() {
        return vaild;
    }

    /*public String getIc() {
        return ic;
    }*/

    public String getSjxxHexStr() {
        return sjxxHexStr;
    }

    public byte[] getSjxxByte() {
        return sjxxByte;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSecond() {
        return second;
    }

    public String getDate(){
        return year+"-"+month+"-"+day;
    }
    public String getTime(){
        return hour+":"+minute+":"+second;
    }
}
