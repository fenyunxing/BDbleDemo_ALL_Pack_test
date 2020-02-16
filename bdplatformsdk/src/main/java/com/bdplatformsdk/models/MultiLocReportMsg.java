package com.bdplatformsdk.models;

import com.bddomainuni.models.entity.protocal2_1.GGAMsg;
import com.bddomainuni.models.entity.protocal2_1.RMCMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * Created by lenovo on 2018/4/22.
 */

public class MultiLocReportMsg {

    private BaseLoc baseLoc = new BaseLoc();
    private DevLoc devLoc = new DevLoc();

    public String getBaseLocHexStr(RMCMsg rmcMsg, GGAMsg ggaMsg) {
        if (rmcMsg.getDWstaus()) {
            baseLoc.setIfValid(rmcMsg.getVaild())
                    .setTimeStamp(rmcMsg.getBjTimeStamp())
                    .setLng(rmcMsg.getLongitude())
                    .setLat(rmcMsg.getLatitude())
                    .setSpeed(BDMethod.castStringToDouble(rmcMsg.getSpeed()))
                    .setOrin(rmcMsg.getLongOrin(), rmcMsg.getLatiOrin(), BDMethod.castStringToDouble(rmcMsg.getDirection()));
        } else {
            baseLoc.setIfValid(false)
                    .setTimeStamp(0)
                    .setLng(0)
                    .setLat(0)
                    .setSpeed(0)
                    .setOrin("E", "N", 0);
        }
        if (ggaMsg.getStatus()) {
            baseLoc.setAltitude(BDMethod.castStringToDouble(ggaMsg.getAntannaHeight()));
        } else {
            baseLoc.setAltitude(0);
        }
        return baseLoc.getBaseLocHexStr();
    }

    public String getDevLocHexStr(RMCMsg rmcMsg, GGAMsg ggaMsg) {
        if (rmcMsg.getDWstaus()) {
            devLoc.setIfValid(rmcMsg.getVaild())
                    .setTimeStamp(rmcMsg.getBjTimeStamp())
                    .setLng(rmcMsg.getLongitude())
                    .setLat(rmcMsg.getLatitude())
                    .setSpeed(BDMethod.castStringToDouble(rmcMsg.getSpeed()))
                    .setOrin(rmcMsg.getLongOrin(), rmcMsg.getLatiOrin(), BDMethod.castStringToDouble(rmcMsg.getDirection()));
        } else {
            devLoc.setIfValid(false)
                    .setTimeStamp(0)
                    .setLng(0)
                    .setLat(0)
                    .setSpeed(0)
                    .setOrin("E", "N", 0);
        }
        if (ggaMsg.getStatus()) {
            devLoc.setAltitude(BDMethod.castStringToDouble(ggaMsg.getAntannaHeight()));
        } else {
            devLoc.setAltitude(0);
        }

        /*devLoc.setIfValid(rmcMsg.getVaild())
                .setTimeStamp(rmcMsg.getBjTimeStamp())
                .setLng(rmcMsg.getLongitude())
                .setLat(rmcMsg.getLatitude())
                .setAltitude(BDMethod.castStringToDouble(ggaMsg.getAntannaHeight()))
                .setSpeed(BDMethod.castStringToDouble(rmcMsg.getSpeed()))
                .setOrin(rmcMsg.getLongOrin(), rmcMsg.getLatiOrin(), BDMethod.castStringToDouble(rmcMsg.getDirection()));*/
        return devLoc.locDevCal(baseLoc, devLoc);
    }

    public class BaseLoc {
        private boolean ifValid = false;
        private String validHexStr = "00";
        private String timeStampHexStr = "00000000";//时间戳,单位秒
        private String lngHexStr = "00000000";//经度 = 度 * 1000000
        private String latHexStr = "00000000";//纬度 = 度 * 1000000
        private String altitudeHexStr = "0000";//米，整数部分
        private String speedHexStr = "0000";//单位为公里/小时，整数部分
        private String orinHexStr = "0000";//经纬度方向 以及航向 度*10
        private long timeStamp = 0;//时间戳,单位秒
        private int lng = 0;//经度 = 度 * 1000000
        private int lat = 0;//纬度 = 度 * 1000000
        private int altitude = 0;//米，整数部分
        private int speed = 0;//单位为公里/小时，整数部分
        private int orin = 0;//角度 度*10

        public boolean isIfValid() {
            return ifValid;
        }

        public BaseLoc setIfValid(boolean ifValid) {
            this.ifValid = ifValid;
            if (ifValid) {
                validHexStr = "01";
            } else {
                validHexStr = "00";
            }
            return this;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public BaseLoc setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp / 1000;
            this.timeStampHexStr = BDMethod.castLongToHexString(this.timeStamp);
            return this;
        }

        public int getLng() {
            return lng;
        }

        public BaseLoc setLng(double lng) {
            this.lng = (int) (lng * 1000000);
            //Log.v("FDBDTestLog", "lng:" + lng + " lng*1000000:" + this.lng);
            this.lngHexStr = BDMethod.castIntToHexStringByNum(this.lng, 8);
            return this;
        }

        public int getLat() {
            return lat;
        }

        public BaseLoc setLat(double lat) {
            this.lat = (int) (lat * 1000000);
            //Log.v("FDBDTestLog", "lat:" + lat + " lat*1000000:" + this.lat);
            this.latHexStr = BDMethod.castIntToHexStringByNum(this.lat, 8);
            return this;
        }

        public int getAltitude() {
            return altitude;
        }

        public BaseLoc setAltitude(double altitude) {
            if (altitude > 0 && altitude < 3277) {
                this.altitude = (int) (altitude * 10);
                this.altitudeHexStr = BDMethod.castIntToHexStringByNum(this.altitude, 4);
            }
            return this;
        }

        public int getSpeed() {
            return speed;
        }

        public BaseLoc setSpeed(double speed) {
            if (speed > 0 && speed < 655) {
                this.speed = (int) (speed * 100);
                this.speedHexStr = BDMethod.castIntToHexStringByNum(this.speed, 4);
            }
            return this;
        }

        public BaseLoc setOrin(String lngOrin, String latOrin, double orin) {
            if (orin >= 0 && orin < 361) {
                this.orin = (int) (orin * 10);
                this.orinHexStr = BDMethod.castIntToHexStringByNum(this.orin, 4);
                byte[] orinByte = BDMethod.castHexStringToBytes(this.orinHexStr);
                if (lngOrin.equals("E")) {
                    if (latOrin.equals("N")) {
                        orinByte[0] = (byte) (orinByte[0] | 0x00);
                    } else {
                        orinByte[0] = (byte) (orinByte[0] | 0x40);
                    }
                } else {
                    if (latOrin.equals("N")) {
                        orinByte[0] = (byte) (orinByte[0] | 0x80);
                    } else {
                        orinByte[0] = (byte) (orinByte[0] | 0xC0);
                    }
                }
                this.orinHexStr = BDMethod.castBytesToHexString(orinByte);
            }
            return this;
        }

        public String getBaseLocHexStr() {
            /*Log.v("FDBDTestLog", "validHexStr:" + validHexStr + " timeStampHexStr:" + timeStampHexStr
                    + " latHexStr:" + latHexStr + " lngHexStr:" + lngHexStr + " altitudeHexStr:" + altitudeHexStr
                    + " speedHexStr:"+speedHexStr+" orinHexStr:" + orinHexStr);*/
            return validHexStr + timeStampHexStr + latHexStr + lngHexStr
                    + altitudeHexStr + speedHexStr + orinHexStr;
        }
    }

    public class DevLoc {
        private boolean ifValid = false;
        private String validHexStr = "00";
        private String devTimeHexStr = "00";//时间2偏差值
        private String devLngHexStr = "0000";//经度偏差值
        private String devLatHexStr = "0000";//纬度偏差值
        private String altitudeHexStr = "0000";//米，整数部分
        private String speedHexStr = "0000";//单位为公里/小时，整数部分
        private String orinHexStr = "0000";//经纬度方向 以及航向 度*10
        private long timeStamp = 0;//时间戳,单位秒
        private int lng = 0;//经度 = 度 * 1000000
        private int lat = 0;//纬度 = 度 * 1000000
        private int altitude = 0;//米，整数部分
        private int speed = 0;//单位为公里/小时，整数部分
        private int orin = 0;//角度 度*10

        public DevLoc setIfValid(boolean ifValid) {
            this.ifValid = ifValid;
            if (ifValid) {
                validHexStr = "01";
            } else {
                validHexStr = "00";
            }
            return this;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public DevLoc setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp / 1000;
            return this;
        }

        public int getLng() {
            return lng;
        }

        public DevLoc setLng(double lng) {
            this.lng = (int) (lng * 1000000);
            //Log.v("FDBDTestLog", "devlng:" + lng + " devlng*1000000:" + this.lng);
            return this;
        }

        public int getLat() {
            return lat;
        }

        public DevLoc setLat(double lat) {
            this.lat = (int) (lat * 1000000);
            //Log.v("FDBDTestLog", "devlat:" + lat + " devlat*1000000:" + this.lat);
            return this;
        }

        public int getAltitude() {
            return altitude;
        }

        public DevLoc setAltitude(double altitude) {
            if (altitude > 0 && altitude < 3277) {
                this.altitude = (int) (altitude * 10);
                this.altitudeHexStr = BDMethod.castIntToHexStringByNum(this.altitude, 4);
            }
            return this;
        }

        public int getSpeed() {
            return speed;
        }

        public DevLoc setSpeed(double speed) {
            if (speed > 0 && speed < 655) {
                this.speed = (int) (speed * 100);
                this.speedHexStr = BDMethod.castIntToHexStringByNum(this.speed, 4);
            }
            return this;
        }

        public int getOrin() {
            return orin;
        }

        public DevLoc setOrin(String lngOrin, String latOrin, double orin) {
            if (orin >= 0 && orin < 361) {
                this.orin = (int) (orin * 10);
                this.orinHexStr = BDMethod.castIntToHexStringByNum(this.orin, 4);
                byte[] orinByte = BDMethod.castHexStringToBytes(this.orinHexStr);
                if (lngOrin.equals("E")) {
                    if (latOrin.equals("N")) {
                        orinByte[0] = (byte) (orinByte[0] | 0x00);
                    } else {
                        orinByte[0] = (byte) (orinByte[0] | 0x40);
                    }
                } else {
                    if (latOrin.equals("N")) {
                        orinByte[0] = (byte) (orinByte[0] | 0x80);
                    } else {
                        orinByte[0] = (byte) (orinByte[0] | 0xC0);
                    }
                }
                this.orinHexStr = BDMethod.castBytesToHexString(orinByte);
            }
            return this;
        }

        public String getValidHexStr() {
            return validHexStr;
        }

        public String getDevTimeHexStr() {
            return devTimeHexStr;
        }

        public String getDevLngHexStr() {
            return devLngHexStr;
        }

        public String getDevLatHexStr() {
            return devLatHexStr;
        }

        public String getAltitudeHexStr() {
            return altitudeHexStr;
        }

        public String getSpeedHexStr() {
            return speedHexStr;
        }

        public String getOrinHexStr() {
            return orinHexStr;
        }

        int devLngInt = 0;
        byte[] devLngByte;
        int devLatInt = 0;
        byte[] devLatByte;

        public String locDevCal(BaseLoc baseLoc, DevLoc devLoc) {
            if (devLoc.ifValid) {
                //计算时间差
                this.devTimeHexStr =
                        BDMethod.castLongToHexStringByNum(devLoc.getTimeStamp() - baseLoc.getTimeStamp(), 2);
                //经度差
                devLngInt = devLoc.getLng() - baseLoc.getLng();
                if (devLngInt < 32767 && devLngInt > -32767) {
                    if (devLngInt < 0) {
                        this.devLngHexStr = BDMethod.castIntToHexStringByNum(Math.abs(devLngInt), 4);
                    }else{
                        this.devLngHexStr = BDMethod.castIntToHexStringByNum(this.devLngInt, 4);
                    }
                    devLngByte = BDMethod.castHexStringToBytes(this.devLngHexStr);
                    if (devLngInt < 0) {
                        devLngByte[0] = (byte) (devLngByte[0] | 0x80);
                    }
                } else {
                    devLngHexStr = "0000";
                }
                this.devLngHexStr = BDMethod.castBytesToHexString(devLngByte);

                //纬度差
                devLatInt = devLoc.getLat() - baseLoc.getLat();
                if (devLatInt < 32767 && devLngInt > -32767) {
                    if (devLatInt < 0) {
                        this.devLatHexStr = BDMethod.castIntToHexStringByNum(Math.abs(devLatInt), 4);
                    }else {
                        this.devLatHexStr = BDMethod.castIntToHexStringByNum(this.devLatInt, 4);
                    }
                    devLatByte = BDMethod.castHexStringToBytes(this.devLatHexStr);
                    if (devLatInt < 0) {
                        devLatByte[0] = (byte) (devLatByte[0] | 0x80);
                    }
                } else {
                    devLatHexStr = "0000";
                }
                this.devLatHexStr = BDMethod.castBytesToHexString(devLatByte);
            } else {
                this.devTimeHexStr = "00";
                this.devLatHexStr = "0000";
                this.devLngHexStr = "0000";
                this.altitudeHexStr = "0000";
                this.speedHexStr = "0000";
                this.orinHexStr = "0000";
            }
            /*Log.v("FDBDTestLog", "validHexStr:" + validHexStr + " devTimeHexStr:" + devTimeHexStr
                    + " devLatHexStr:" + devLatHexStr + " devLngHexStr:" + devLngHexStr + " altitudeHexStr:" + altitudeHexStr
                    + " speedHexStr:" + speedHexStr + " orinHexStr:" + orinHexStr);*/
            return validHexStr + devTimeHexStr + devLatHexStr + devLngHexStr
                    + altitudeHexStr + speedHexStr + orinHexStr;
        }
    }
}
