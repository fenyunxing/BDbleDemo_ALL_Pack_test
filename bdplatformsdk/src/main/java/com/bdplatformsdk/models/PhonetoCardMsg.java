package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

import static com.bdplatformsdk.repository.protcals.protocal_platform.TYPE_CARD_2_PHONE_STR;
import static com.bdplatformsdk.repository.protcals.protocal_platform.TYPE_USER_2_PHONE_BYLOC_STR;

/**
 * (平台协议)手机对卡通信类
 *
 * Created by admin on 2017/2/13.
 */
public class PhonetoCardMsg {

    private String PhoneToUserMsgStr;
    private String Phone;
    private String User;
    private String Msg;
    private boolean locValid = false;
    private String lngOrin = "E";
    private String latOrin = "N";
    private double lng = 0;
    private double lat = 0;
    private String lngDegStr = "";
    private String latDegStr = "";
    private String userToPhoneType = "";
    private boolean ifMultiCrd = false;
    private String mainCrd = "";


    public PhonetoCardMsg() {
    }

    /**
     * 构造函数
     * 直接解析手机发至设备信息
     *
     * @param paramTXR
     *         短报文
     */
    public PhonetoCardMsg(TXRMsg paramTXR) {
        ifMultiCrd = paramTXR.getMsgHexStr().substring(2, 4).equals("AA") ? false : true;
        userToPhoneType = paramTXR.getPlatformDataType();

        if (userToPhoneType.equals(TYPE_CARD_2_PHONE_STR)) {
            //用户至手机间通信（无位置）
            if (ifMultiCrd) {
                //用户至手机间通信（无位置，多卡）
                if (paramTXR.getMsgHexStr().length() > 33) {
                    mainCrd = BDMethod.castHexStringToDcmString(paramTXR.getMsgHexStr().substring(6, 12));
                    PhoneToUserMsgStr = paramTXR.getMsgHexStr().substring(12);
                    setUser(PhoneToUserMsgStr.substring(1, 12));
                    setPhone(PhoneToUserMsgStr.substring(13, 24));
                    setMsg(PhoneToUserMsgStr.substring(24));
                } else {
                    // TODO: 2017/2/14  当得到用户对手机报文过短时的处理
                }
            } else {
                //用户至手机间通信（无位置，单卡）
                if (paramTXR.getMsgHexStr().length() > 30) {
                    PhoneToUserMsgStr = paramTXR.getMsgHexStr().substring(6);
                    setUser(PhoneToUserMsgStr.substring(1, 12));
                    setPhone(PhoneToUserMsgStr.substring(13, 24));
                    setMsg(PhoneToUserMsgStr.substring(24));
                } else {
                    // TODO: 2017/2/14  当得到用户对手机报文过短时的处理
                }
            }
        } else if (userToPhoneType.equals(TYPE_USER_2_PHONE_BYLOC_STR)) {
            //用户至手机间通信（含位置）
            if (ifMultiCrd) {
                //用户至手机间通信（含位置，多卡）
                if (paramTXR.getMsgHexStr().length() > 51) {
                    mainCrd = BDMethod.castHexStringToDcmString(paramTXR.getMsgHexStr().substring(6, 12));
                    PhoneToUserMsgStr = paramTXR.getMsgHexStr().substring(12);
                    setUser(PhoneToUserMsgStr.substring(1, 12));
                    setPhone(PhoneToUserMsgStr.substring(13, 24));
                    setLoc(PhoneToUserMsgStr.substring(24, 42));
                    setMsg(PhoneToUserMsgStr.substring(42));
                } else {
                    // TODO: 2017/2/14  当得到用户对手机报文过短时的处理
                }
            } else {
                //用户至手机间通信（含位置，单卡）
                if (paramTXR.getMsgHexStr().length() > 48) {
                    PhoneToUserMsgStr = paramTXR.getMsgHexStr().substring(6);
                    setUser(PhoneToUserMsgStr.substring(1, 12));
                    setPhone(PhoneToUserMsgStr.substring(13, 24));
                    setLoc(PhoneToUserMsgStr.substring(24, 42));
                    setMsg(PhoneToUserMsgStr.substring(42));
                } else {
                    // TODO: 2017/2/14  当得到用户对用户报文过短时的处理
                }
            }
        }
    }

    /**
     * 获取手机号码
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * 设置手机号码
     */
    public void setPhone(String phone) {
        Phone = phone;
    }

    /**
     * 获取接收用户
     */
    public String getUser() {
        return User;
    }

    /**
     * 设置接收用户
     */
    public void setUser(String user) {
        User = user;
    }

    /**
     * 获取消息内容
     */
    public String getMsg() {
        return BDMethod.castHexStringToHanziString(Msg);
    }

    /**
     * 获取十六进制消息内容
     */
    public String getHexMsg() {
        return Msg;
    }

    /**
     * 设置消息内容
     */
    public void setMsg(String msg) {
        Msg = msg;
    }

    private void setLoc(String loc) {
        byte[] locState = BDMethod.castHexStringToBytes(loc.substring(0, 2));
        this.locValid = (locState[0] & 0x04) == 0x04 ? true : false;
        this.lngOrin = (locState[0] & 0x02) == 0x02 ? "W" : "E";
        this.latOrin = (locState[0] & 0x01) == 0x02 ? "S" : "N";
        this.lng = Double.parseDouble(BDMethod.castHexStringToDcmString(loc.substring(2, 10))) / 1000000;
        this.lat = Double.parseDouble(BDMethod.castHexStringToDcmString(loc.substring(10, 18))) / 1000000;
        String mlngStr = String.valueOf(this.lng);
        String mlatStr = String.valueOf(this.lat);
        if (mlngStr.length() > 0 && mlngStr.indexOf(".") != -1) {
            String[] arrayH = mlngStr.split("\\.");
            String[] arrayM = String.valueOf(Double.parseDouble("0." + arrayH[1]) * 60).split("\\.");
            String[] arrayS = String.valueOf(Double.parseDouble("0." + arrayM[1]) * 60).split("\\.");
            this.lngDegStr = lngOrin + arrayH[0] + "°"
                    + String.format("%02d", Integer.parseInt(arrayM[0])) + "′"
                    + String.format("%02d", Integer.parseInt(arrayS[0])) + "″";
        }
        if (mlatStr.length() > 0 && mlatStr.indexOf(".") != -1) {
            String[] arrayH = mlatStr.split("\\.");
            String[] arrayM = String.valueOf(Double.parseDouble("0." + arrayH[1]) * 60).split("\\.");
            String[] arrayS = String.valueOf(Double.parseDouble("0." + arrayM[1]) * 60).split("\\.");
            this.latDegStr = latOrin + arrayH[0] + "°"
                    + String.format("%02d", Integer.parseInt(arrayM[0])) + "′"
                    + String.format("%02d", Integer.parseInt(arrayS[0])) + "″";
        }
        /*Log.v("FDBDTestLog", "PhonetoCardMsg setLoc(String loc) =>lat = " + lat + "lng = " + lng + " locValid = " + locValid + " lngOrin = " + lngOrin
                + " latOrin = " + latOrin + "  lngDegStr = " + lngDegStr + "  latDegStr = " + latDegStr);*/
    }

    public String getLngDegStr() {
        return lngDegStr;
    }

    public String getLatDegStr() {
        return latDegStr;
    }

    public boolean isLocValid() {
        return locValid;
    }

    public String getLngOrin() {
        return lngOrin;
    }

    public String getLatOrin() {
        return latOrin;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public String getUserToPhoneType() {
        return userToPhoneType;
    }


    public boolean isIfMultiCrd() {
        return ifMultiCrd;
    }

    public String getMainCrd() {
        return mainCrd;
    }
}
