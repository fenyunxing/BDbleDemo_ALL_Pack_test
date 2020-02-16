package com.bdplatformsdk.models;

import android.util.Log;

import com.bddomainuni.repository.tools.BDMethod;


/**
 * (平台协议)位置上报信息类
 *
 * Created by admin on 2017/2/13.
 */
@Deprecated
public class PositionUploadMsg {

    private static String lat = "";//纬度
    private static String lng = "";//经度
    private static String latOri = "";//纬度方向
    private static String lngOri = "";//经度方向
    private static String ifValid = "00";
    private static String high = "";//高度值
    private static String orin = "";//朝向
    private static String speed = "";//速度
    private static String uploadMsg;//位置上报数据
    private static String uploadLoginMsg;//登录位置信息上报数据

    private PositionUploadMsg mPositionUploadMsg;

    /**
     * 构造函数
     * 配置位置上报的信息
     *
     * @param b 信息构造器
     */
    @Deprecated
    private PositionUploadMsg(Builder b) {
        this.lat = b.lat;//纬度
        this.lng = b.lng;//经度
        this.latOri = b.latOri;//纬度方向
        this.lngOri = b.lngOri;//经度方向
        this.ifValid = b.ifValid;
        this.high = b.high;//高度值
        this.orin = b.orin;//朝向
        this.speed = b.speed;//速度
        this.uploadMsg = b.uploadMsg;//最终上报数据
        this.uploadLoginMsg = b.uploadLoginMsg;//最终上报数据
    }

    /**
     * 位置消息构造器类
     */
    @Deprecated
    public static class Builder {
        private String lat = "";//纬度
        private String lng = "";//经度
        private String latOri = "";//纬度方向
        private String lngOri = "";//经度方向
        private String ifValid = "00";
        private String high;//高度值
        private String orin = "";//朝向
        private String speed = "";//速度
        private String uploadMsg;//最终上报数据
        private String uploadLoginMsg;//登录位置信息上报数据

        public Builder() {
        }

        /**
         * 设置纬度
         *
         * @param latH 度
         * @param latM 分
         * @param latS 秒
         */
        public Builder setLat(String latH, String latM, String latS) {
            if (latH != null && latM != null && latS != null) {
                if (!latH.equals("") && !latM.equals("") && !latS.equals("")) {
                    if (latH.length() >= 2 && latM.length() >= 2 && latS.length() >= 2) {
                        this.lat = latH.substring(0, 2) + latM.substring(0, 2) + latS.substring(0, 2);
                        Log.v("FDBDChatTest", "纬度" + lat);
                    } else {
                        this.lat = "000000";
                    }
                } else {
                    this.lat = "000000";
                }
            } else {
                this.lat = "000000";
            }
            return this;
        }

        /**
         * 设置经度
         *
         * @param lngH 度
         * @param lngM 分
         * @param lngS 秒
         */
        public Builder setLng(String lngH, String lngM, String lngS) {
            if (lngH != null && lngM != null && lngS != null) {
                if (!lngH.equals("") && !lngM.equals("") && !lngS.equals("")) {
                    if (lngH.length() >= 3 && lngM.length() >= 2 && lngS.length() >= 2) {
                        this.lng = "0" +lngH.substring(0, 3) + lngM.substring(0, 2) + lngS.substring(0, 2);
                        Log.v("FDBDChatTest", "经度" + lng);
                    } else {
                        this.lng = "00000000";
                    }
                } else {
                    this.lng = "00000000";
                }
            } else {
                this.lng = "00000000";
            }
            return this;
        }

        /**
         * 设置纬度方向
         *
         * @param latOri 纬度方向
         */
        public Builder setLatOri(String latOri) {
            if (latOri != null && !latOri.equals("")) {
                switch (latOri) {
                    case "N":
                        this.latOri = "4E";
                        break;
                    case "S":
                        this.latOri = "53";
                        break;
                    default:
                        this.latOri = "00";
                        break;
                }
            } else {
                this.latOri = "00";
            }
            return this;
        }

        /**
         * 设置经度方向
         *
         * @param lngOri 经度方向
         */
        public Builder setLngOri(String lngOri) {
            if (lngOri != null && !lngOri.equals("")) {
                switch (lngOri) {
                    case "E":
                        this.lngOri = "45";
                        break;
                    case "W":
                        this.lngOri = "57";
                        break;
                    default:
                        this.lngOri = "00";
                        break;
                }
            } else {
                this.lngOri = "00";
            }
            return this;
        }

        /**
         * 设置有效位
         *
         * @param ifValid 有效标志
         */
        public Builder setIfValid(boolean ifValid) {
            if (ifValid) {
                this.ifValid = "01";
            } else {
                this.ifValid = "00";
            }
            return this;
        }

        /**
         * 设置海拔高度
         *
         * @param high 高度
         */
        public Builder setHigh(String high) {
            if (high != null && !high.equals("")) {
                this.high = BDMethod.castBytesToHexString(high.getBytes());
            } else {
                this.high = "";
            }
            return this;
        }

        /**
         * 设置航向
         *
         * @param orin 航向
         */
        public Builder setOrin(String orin) {
            if (orin != null && !orin.equals("")) {
                this.orin = BDMethod.castBytesToHexString(orin.getBytes());
            } else {
                this.orin = "";
            }
            return this;
        }

        /**
         * 设置速度
         *
         * @param speed 速度
         */
        public Builder setSpeed(String speed) {
            if (speed != null && !speed.equals("")) {
                this.speed = BDMethod.castBytesToHexString(speed.getBytes());
            } else {
                this.speed = "";
            }
            return this;
        }

        /**
         * 设置位置上报信息
         *
         * @param uploadMsg
         */
        private void setUploadMsg(String uploadMsg) {
            this.uploadMsg = uploadMsg;
        }

        /**
         * 设置登入位置信息
         *
         * @param uploadLoginMsg
         */
        @Deprecated
        public void setUploadLoginMsg(String uploadLoginMsg) {
            this.uploadLoginMsg = uploadLoginMsg;
        }

        /**
         *完成组装生成位置上报与登入位置信息
         */
        public PositionUploadMsg build() {
            setUploadMsg(this.ifValid + this.lat + this.lng + this.latOri + this.lngOri
                    + "2C" + this.high + "2C" + this.orin + "2C" + this.speed);
            setUploadLoginMsg("" + this.ifValid + this.lat + this.lng + this.latOri + this.lngOri);
            return new PositionUploadMsg(this);
        }

    }

    /**
     *获取位置上报组装信息
     */
    @Deprecated
    public static String getUploadMsg() {
        return uploadMsg;
    }

    /**
     *获取登入位置组装信息
     */
    @Deprecated
    public static String getUploadLoginMsg() {
        return uploadLoginMsg;
    }

}
