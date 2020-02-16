package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * (平台协议)平台登入反馈类
 *
 * Created by admin on 2017/2/13.
 */

public class AppLoginFKIMsg {

    private String loginFKIStr;
    private String offlineMsgNum = "";
    private String phone = "";
    private String loginStatus = "00";
    private int authlen = 0;
    private String auth = "";

    public AppLoginFKIMsg() {
    }

    /**
     * 构造函数 直接解析登入报文信息
     *
     * @param paramTXR
     *         短报文
     */
    public AppLoginFKIMsg(TXRMsg paramTXR) {
        int len = paramTXR.getMsgHexStr().length();
        if (len > 36) {
            loginFKIStr = paramTXR.getMsgHexStr().substring(18);
            setOfflineMsgNum(BDMethod.castHexStringToDcmString(loginFKIStr.substring(0, 4)));
            setPhone(BDMethod.castHexStringToDcmString(loginFKIStr.substring(4, 14)));
            setLoginStatus(loginFKIStr.substring(14, 16));
            setAuthlen(Integer.parseInt(BDMethod.castHexStringToDcmString(loginFKIStr.substring(16, 18))));
            if (getAuthlen() > 0 && len >= 18 + 2 * getAuthlen()) {
                setAuth(BDMethod.castHexStringToHanziString(loginFKIStr.substring(18, 18 + 2 * getAuthlen())));
            }
//            Log.v("FDBDTestLog", "setOfflineMsgNum=" + getOfflineMsgNum());
//            Log.v("FDBDTestLog", "setPhone=" + getPhone());
//            Log.v("FDBDTestLog", "setLoginStatus=" + getLoginStatus());
//            Log.v("FDBDTestLog", "setAuthlen=" + getAuthlen());
//            Log.v("FDBDTestLog", "setAuth=" + getAuth());

        } else {
            // TODO: 2017/2/9  当得到登录回执报文过短时的处理
        }
        //Log.v("FDBDChatTest","User ===> "+User+" Num ===> "+taskNum+" content ===> "+taskMsg);
    }

    public String getOfflineMsgNum() {
        return offlineMsgNum;
    }

    public void setOfflineMsgNum(String offlineMsgNum) {
        this.offlineMsgNum = offlineMsgNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getAuthlen() {
        return authlen;
    }

    public void setAuthlen(int authlen) {
        this.authlen = authlen;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
