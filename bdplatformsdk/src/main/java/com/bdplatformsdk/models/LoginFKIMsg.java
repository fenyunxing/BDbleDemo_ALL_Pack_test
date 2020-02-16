package com.bdplatformsdk.models;


import android.util.Log;

import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * (平台协议)平台登入反馈类
 *
 * Created by admin on 2017/2/13.
 */

public class LoginFKIMsg {

    private String loginFKIStr;
    private String User;
    private String loginState;
    private EmergencyContactMsg emergencyContactMsg = new EmergencyContactMsg();

    public LoginFKIMsg() {
    }

    /**
     * 构造函数
     * 直接解析登入报文信息
     *
     * @param paramTXR
     *         短报文
     */
    public LoginFKIMsg(TXRMsg paramTXR) {
        int len = paramTXR.getMsgHexStr().length();
        if (len > 12) {
            loginFKIStr = paramTXR.getMsgHexStr().substring(6);
            setUser(loginFKIStr.substring(1, 12));
            setLoginState(loginFKIStr.substring(12, 14));
            //包含有紧急联系人信息
            if (len > 20) {
                setEmergencyContactMsg(loginFKIStr.substring(14));
            }

        } else {
            // TODO: 2017/2/9  当得到登录回执报文过短时的处理
        }
        //Log.v("FDBDChatTest","User ===> "+User+" Num ===> "+taskNum+" content ===> "+taskMsg);
    }

    /**
     * 获取登入用户名
     */
    public String getUser() {
        return User;
    }

    /**
     * 设置登入用户名
     */
    private void setUser(String user) {
        User = user;
    }

    /**
     * 获取登入状态
     */
    public String getLoginState() {
        return loginState;
    }

    /**
     * 设置登入状态
     */
    private void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public EmergencyContactMsg getEmergencyContactMsg() {
        return emergencyContactMsg;
    }

    public void setEmergencyContactMsg(String emergContactStr) {
        String state = emergContactStr.substring(0, 2);
        Log.v("FDBDTestLog","emergContactStr："+emergContactStr);
        Log.v("FDBDTestLog","state 紧急联系人数："+state);
        switch (state) {
            case "00":
                emergencyContactMsg.setContactNum(0);
                break;
            case "01":
                emergencyContactMsg.setContactNum(1);
                emergencyContactMsg.setContFrtName(BDMethod.castHexStringToHanziString(emergContactStr.substring(2, 14)));
                emergencyContactMsg.setContFrtPhone(emergContactStr.substring(14, 26));
                break;
            case "02":
                emergencyContactMsg.setContactNum(2);
                emergencyContactMsg.setContFrtName(BDMethod.castHexStringToHanziString(emergContactStr.substring(2, 14)));
                emergencyContactMsg.setContFrtPhone(emergContactStr.substring(15, 26));
                emergencyContactMsg.setContSecName(BDMethod.castHexStringToHanziString(emergContactStr.substring(26, 38)));
                emergencyContactMsg.setContSecPhone(emergContactStr.substring(39, 50));
            default:
                break;
        }

        this.emergencyContactMsg = emergencyContactMsg;
    }
}
