package com.bddomainuni.models.entity.protocal4_0;


import com.bddomainuni.repository.tools.BDMethod;

/**
 * 自检信息类
 *
 * Created by admin on 2017/5/24.
 */

public class ZJXXMsg {
    private boolean vaild = false;
    private String ic;
    private String zjxxHexStr;
    private byte[] zjxxByte;
    private String icStatus;
    private String hardwareStatus;
    private String batteryStatus;
    private String inboundStatus;
    private String pw1;
    private String pw2;
    private String pw3;
    private String pw4;
    private String pw5;
    private String pw6;

    public ZJXXMsg(byte[] parambytes) {
        vaild = BDMethod.CheckCKS_40(parambytes);
        if (vaild) {
            this.zjxxHexStr = BDMethod.castBytesToHexString(parambytes);
            this.zjxxByte = parambytes;
            this.ic = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[7], parambytes[8], parambytes[9]});
            this.icStatus = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[10]});
            this.hardwareStatus = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[11]});
            this.batteryStatus = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[12]});
            this.inboundStatus = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[13]});
            this.pw1 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[14]});
            this.pw2 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[15]});
            this.pw3 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[16]});
            this.pw4 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[17]});
            this.pw5 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[18]});
            this.pw6 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[19]});

        }
    }

    public boolean isVaild() {
        return vaild;
    }

    public String getIc() {
        return ic;
    }

    public String getZjxxHexStr() {
        return zjxxHexStr;
    }

    public byte[] getZjxxByte() {
        return zjxxByte;
    }

    public String getIcStatus() {
        return icStatus;
    }

    public String getHardwareStatus() {
        return hardwareStatus;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public String getInboundStatus() {

        return inboundStatus;
    }

    public String getPw1() {
        return pw1;
    }

    public String getPw2() {
        return pw2;
    }

    public String getPw3() {
        return pw3;
    }

    public String getPw4() {
        return pw4;
    }

    public String getPw5() {
        return pw5;
    }

    public String getPw6() {
        return pw6;
    }
}
