package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

import static com.bdplatformsdk.repository.protcals.protocal_platform.TYPE_USER_2_USER_BYLOC_STR;
import static com.bdplatformsdk.repository.protcals.protocal_platform.TYPE_USER_2_USER_STR;

/**
 * (平台协议)用户发至用户信息类
 *
 * Created by admin on 2017/2/13.
 */

public class UsertoUserMsg {

    private String UserToUserMsgStr;
    private String revCard;
    private String sendUser;
    private String revUser;
    private String Msg;
    private boolean locValid = false;
    private String lngOrin = "E";
    private String latOrin = "N";
    private double lng = 0;
    private double lat = 0;
    private String lngDegStr = "";
    private String latDegStr = "";
    private String userToUserType = "";
    private boolean ifMultiCrd = false;
    private String mainCrd = "";

    public UsertoUserMsg() {
    }

    /**
     * 构造函数
     * 直接解析用户至用户信息
     *
     * @param paramTXR
     *         短报文
     */
    public UsertoUserMsg(TXRMsg paramTXR) {
        ifMultiCrd = paramTXR.getMsgHexStr().substring(2, 4).equals("AA") ? false : true;
        userToUserType = paramTXR.getPlatformDataType();
        if (userToUserType.equals(TYPE_USER_2_USER_STR)) {
            //用户至用户间通信（无位置）
            if (ifMultiCrd) {
                //用户至用户间通信（无位置，多卡）
                if (paramTXR.getMsgHexStr().length() > 33) {
                    mainCrd = BDMethod.castHexStringToDcmString(paramTXR.getMsgHexStr().substring(6, 12));
                    UserToUserMsgStr = paramTXR.getMsgHexStr().substring(12);
                    setSendUser(UserToUserMsgStr.substring(1, 12));
                    setRevUser(UserToUserMsgStr.substring(13, 24));
                    setMsg(UserToUserMsgStr.substring(24));
                    setRevCard(paramTXR.getUserID());
                } else {
                    // TODO: 2017/2/14  当得到用户对用户报文过短时的处理
                }
            } else {
                //用户至用户间通信（无位置，单卡）
                if (paramTXR.getMsgHexStr().length() > 30) {
                    UserToUserMsgStr = paramTXR.getMsgHexStr().substring(6);
                    setSendUser(UserToUserMsgStr.substring(1, 12));
                    setRevUser(UserToUserMsgStr.substring(13, 24));
                    setMsg(UserToUserMsgStr.substring(24));
                    setRevCard(paramTXR.getUserID());
                } else {
                    // TODO: 2017/2/14  当得到用户对用户报文过短时的处理
                }
            }
        } else if (userToUserType.equals(TYPE_USER_2_USER_BYLOC_STR)) {
            //用户至用户间通信（含位置）
            if (ifMultiCrd) {
                //用户至用户间通信（含位置，多卡）
                if (paramTXR.getMsgHexStr().length() > 51) {
                    mainCrd = BDMethod.castHexStringToDcmString(paramTXR.getMsgHexStr().substring(6, 12));
                    UserToUserMsgStr = paramTXR.getMsgHexStr().substring(12);
                    setSendUser(UserToUserMsgStr.substring(1, 12));
                    setRevUser(UserToUserMsgStr.substring(13, 24));
                    setLoc(UserToUserMsgStr.substring(24, 42));
                    setMsg(UserToUserMsgStr.substring(42));
                    setRevCard(paramTXR.getUserID());
                } else {
                    // TODO: 2017/2/14  当得到用户对用户报文过短时的处理
                }
            } else {
                //用户至用户间通信（含位置，单卡）
                if (paramTXR.getMsgHexStr().length() > 48) {
                    UserToUserMsgStr = paramTXR.getMsgHexStr().substring(6);
                    setSendUser(UserToUserMsgStr.substring(1, 12));
                    setRevUser(UserToUserMsgStr.substring(13, 24));
                    setLoc(UserToUserMsgStr.substring(24, 42));
                    setMsg(UserToUserMsgStr.substring(42));
                    setRevCard(paramTXR.getUserID());
                } else {
                    // TODO: 2017/2/14  当得到用户对用户报文过短时的处理
                }
            }
        }

        //Log.v("FDBDChatTest","Msg ===> "+Msg);
    }

    /**
     * 获取消息内容
     */
    public String getMsg() {
        return BDMethod.castHexStringToHanziString(Msg);
    }

    /**
     * 设置消息内容
     */
    private void setMsg(String msg) {
        Msg = msg;
    }

    /**
     * 获取十六进制消息内容
     */
    public String getHexMsg() {
        return Msg;
    }

    /**
     * 获取发送用户
     */
    public String getSendUser() {
        return sendUser;
    }

    /**
     * 设置发送用户
     */
    private void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    /**
     * 获取接收用户
     */
    public String getRevUser() {
        return revUser;
    }

    /**
     * 设置接收用户
     */
    private void setRevUser(String revUser) {
        this.revUser = revUser;
    }

    /**
     * 获取接收卡号
     */
    public String getRevCard() {
        return revCard;
    }

    /**
     * 获取接收卡号
     */
    private void setRevCard(String revCard) {
        this.revCard = revCard;
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
        /*Log.v("FDBDTestLog", "UsertoUserMsg setLoc(String loc) =>lat = " + lat + "lng = " + lng + " locValid = " + locValid + " lngOrin = " + lngOrin
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

    public String getUserToUserType() {
        return userToUserType;
    }

    public boolean isIfMultiCrd() {
        return ifMultiCrd;
    }

    public String getMainCrd() {
        return mainCrd;
    }
}
