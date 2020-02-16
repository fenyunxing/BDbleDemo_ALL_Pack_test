package com.bdplatformsdk.models;

import android.util.Log;

import com.bddomainuni.repository.tools.BDMethod;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by admin on 2018/4/8.
 */

public class EmergencyAlarm {
    private int typeAlarm = 0;
    private String lngOrin = "E";
    private String latOrin = "N";
    private double lng = 0;
    private double lat = 0;
    private double altitude = 0;
    private double speed = 0;
    private String dateAndTime = "000000000000";
    private int battery = 0;
    private String emergencyMsg = "";
    private byte[] emergencyMsgBytes;
    private EmergencyAlarm emergencyAlarm;

    public EmergencyAlarm() {
        this.emergencyAlarm = this;
    }

    public byte[] getEmergencyMsgBytes() {
        byte[] type = new byte[1];
        String typeHexStr = "";
        if (lngOrin.equals("E")) {
            if (latOrin.equals("N")) {
                type[0] = (byte) (typeAlarm << 2);
            } else {
                type[0] = (byte) ((typeAlarm << 2) + 1);
            }
        } else {
            if (latOrin.equals("N")) {
                type[0] = (byte) ((typeAlarm << 2) + 2);
            } else {
                type[0] = (byte) ((typeAlarm << 2) + 3);
            }
        }
        typeHexStr = BDMethod.castBytesToHexString(type);
        String latStr = "" + (int) (lat * 1000000);
        String lngStr = "" + (int) (lng * 1000000);
        String lnltHexStr = StringUtils.leftPad(BDMethod.castDcmStringToHexString(lngStr), 8, "0")
                + StringUtils.leftPad(BDMethod.castDcmStringToHexString(latStr), 8, "0");
        byte[] altitudeByte = new byte[2];
        System.arraycopy(BDMethod.castIntToBytesPos((int) (altitude * 10)), 2, altitudeByte, 0, 2);
        String altitudeHexStr;
        if (altitude > 0) {
            //altitudeByte[0] = (byte) (altitudeByte[0] | 0x7F);
            altitudeHexStr = StringUtils.leftPad(BDMethod.castBytesToHexString(altitudeByte), 4, "0");
        } else {
            altitudeByte[0] = (byte) (altitudeByte[0] | 0x80);
            altitudeHexStr = BDMethod.castBytesToHexString(altitudeByte);
        }
        if (speed > 655) {
            speed = 0;
        }
        String speedHexStr = StringUtils.leftPad(BDMethod.castDcmStringToHexString("" + (int) (speed * 100)),
                4, "0");
        String batteryHexStr = StringUtils.leftPad(BDMethod.castDcmStringToHexString("" + battery),
                2, "0");
        String sosContentHexStr = "";
        try {
            sosContentHexStr = BDMethod.castBytesToHexString(emergencyMsg.getBytes("gbk"));
        } catch (UnsupportedEncodingException e) {
            Log.e("FDBDError","Error:EmergencyAlarm-->getEmergencyMsgBytes encode wrong");
            e.printStackTrace();
        }
        try {
            emergencyMsgBytes = (typeHexStr + lnltHexStr + altitudeHexStr + speedHexStr + dateAndTime + batteryHexStr + sosContentHexStr).getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            Log.e("FDBDError","Error:EmergencyAlarm-->getEmergencyMsgBytes encode wrong");
            e.printStackTrace();
        }
        return emergencyMsgBytes;
    }

    public int getTypeAlarm() {
        return typeAlarm;
    }

    public EmergencyAlarm setTypeAlarm(int typeAlarm) {
        this.typeAlarm = typeAlarm;
        return emergencyAlarm;
    }

    public String getLngOrin() {
        return lngOrin;
    }

    public EmergencyAlarm setLngOrin(String lngOrin) {
        this.lngOrin = lngOrin;
        return emergencyAlarm;
    }

    public String getLatOrin() {
        return latOrin;
    }

    public EmergencyAlarm setLatOrin(String latOrin) {
        this.latOrin = latOrin;
        return emergencyAlarm;
    }

    public double getLng() {
        return lng;
    }

    public EmergencyAlarm setLng(double lng) {
        this.lng = lng;
        return emergencyAlarm;
    }

    public double getLat() {
        return lat;
    }

    public EmergencyAlarm setLat(double lat) {
        this.lat = lat;
        return emergencyAlarm;
    }

    public double getAltitude() {
        return altitude;
    }

    public EmergencyAlarm setAltitude(double altitude) {
        this.altitude = altitude;
        return emergencyAlarm;
    }

    public double getSpeed() {
        return speed;
    }

    public EmergencyAlarm setSpeed(double speed) {
        this.speed = speed;
        return emergencyAlarm;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public EmergencyAlarm setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
        return emergencyAlarm;
    }

    public int getBattery() {
        return battery;
    }

    public EmergencyAlarm setBattery(int battery) {
        this.battery = battery;
        return emergencyAlarm;
    }

    public String getEmergencyMsg() {
        return emergencyMsg;
    }

    public EmergencyAlarm setEmergencyMsg(String emergencyMsg) {
        this.emergencyMsg = emergencyMsg;
        return emergencyAlarm;
    }
}
