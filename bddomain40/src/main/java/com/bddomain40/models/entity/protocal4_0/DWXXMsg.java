package com.bddomain40.models.entity.protocal4_0;

import com.bddomain40.repository.tools.BDMethod;

/**
 * 定位信息类
 *
 * Created by admin on 2017/5/24.
 */

public class DWXXMsg {
    private boolean vaild = false;
    private String ic;
    private int dwType;//定位类别
    private int ifKey;//秘钥
    private int accuracy;//精度
    private int ifEmergency;//紧急定位
    private int ifMultiValue;//多值解
    private int highType;//高程类型
    private String searchIC;//查询地址
    private String dwxxHexStr;
    private byte[] dwxxByte;
    private String timeH;
    private String timeM;
    private String timeS;
    private String timeSS;
    private String time;

    private String lngD;
    private String lngM;
    private String lngS;
    private String lngSS;
    private Double longitude;
    private String lngDeg;

    private String latD;
    private String latM;
    private String latS;
    private String latSS;
    private Double latitude;
    private String latDeg;

    private String altitude;
    private String altitudeAno;//高程异常

    public DWXXMsg(byte[] parambytes) {
        vaild = BDMethod.CheckCKS_40(parambytes);
        if (vaild) {
            this.dwxxHexStr = BDMethod.castBytesToHexString(parambytes);
            this.dwxxByte = parambytes;
            this.ic = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[7], parambytes[8], parambytes[9]});
            this.dwType = (parambytes[10] & 0x20) == 0x20 ? 1 : 0;
            this.ifKey = (parambytes[10] & 0x10) == 0x10 ? 1 : 0;
            this.accuracy = (parambytes[10] & 0x08) == 0x08 ? 1 : 0;
            this.ifEmergency = (parambytes[10] & 0x04) == 0x04 ? 1 : 0;
            this.ifMultiValue = (parambytes[10] & 0x02) == 0x02 ? 1 : 0;
            this.highType = (parambytes[10] & 0x01) == 0x01 ? 1 : 0;
            this.searchIC = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[11], parambytes[12], parambytes[13]});

            this.timeH = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[14]});
            this.timeM = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[15]});
            this.timeS = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[16]});
            this.timeSS = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[17]});
            this.time = timeH + ":" + timeM + ":" + timeS;

            this.lngD = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[18]});
            this.lngM = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[19]});
            this.lngS = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[20]});
            this.lngSS = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[21]});
            this.lngDeg = lngD + "°" + lngM + "′" + lngS + "″";
            this.longitude = Double.parseDouble(lngD) + Double.parseDouble(lngM) / 60 + Double.parseDouble(lngS) / 3600 + Double.parseDouble(lngSS) / 216000;

            this.latD = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[22]});
            this.latM = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[23]});
            this.latS = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[24]});
            this.latSS = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[25]});
            this.latDeg = latD + "°" + latM + "′" + latS + "″";
            this.latitude = Double.parseDouble(latD) + Double.parseDouble(latM) / 60 + Double.parseDouble(latS) / 3600 + Double.parseDouble(latSS) / 216000;

            if (parambytes[26] == 0) {
                this.altitude = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[27]});
            } else {
                this.altitude = "-" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[27]});
            }
            if (parambytes[28] == 0) {
                this.altitudeAno = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[29]});
            } else {
                this.altitudeAno = "-" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[29]});
            }
        }
    }

    public boolean isVaild() {
        return vaild;
    }

    /*public String getIc() {
        return ic;
    }*/

    public String getDwType() {
        if (dwType == 0) {
            return "定位";
        } else {
            return "查询";
        }
    }

    public boolean getIfKey() {
        if (ifKey == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getAccuracy() {
        if (accuracy == 0) {
            return "一档";
        } else {
            return "二档";
        }
    }

    public boolean getIfEmergency() {
        if (ifEmergency == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getIfMultiValue() {
        if (ifMultiValue == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getHighType() {
        if (highType == 0) {
            return "普通";
        } else {
            return "高空";
        }
    }

    public String getSearchIC() {
        return searchIC;
    }

    public String getTimeH() {
        return timeH;
    }

    public String getTimeM() {
        return timeM;
    }

    public String getTimeS() {
        return timeS;
    }

    public String getTimeSS() {
        return timeSS;
    }

    public String getLngD() {
        return lngD;
    }

    public String getLngM() {
        return lngM;
    }

    public String getLngS() {
        return lngS;
    }

    public String getLngSS() {
        return lngSS;
    }

    public String getLatD() {
        return latD;
    }

    public String getLatM() {
        return latM;
    }

    public String getLatS() {
        return latS;
    }

    public String getLatSS() {
        return latSS;
    }

    public String getAltitude() {
        return altitude;
    }

    public String getAltitudeAno() {
        return altitudeAno;
    }

    public String getTime() {
        return time;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getLngDeg() {
        return lngDeg;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getLatDeg() {
        return latDeg;
    }

    public String getDwxxHexStr() {
        return dwxxHexStr;
    }

    public byte[] getDwxxByte() {
        return dwxxByte;
    }
}
