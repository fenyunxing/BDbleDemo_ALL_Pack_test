package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * (平台协议)平台登入反馈类
 *
 * Created by admin on 2017/2/13.
 */

public class AppSosFkiMsg {

    private String appSosFkiMsg = "";
    private String sosCode = "";
    private String phone = "";
    private String content = "";

    public AppSosFkiMsg() {
    }

    /**
     * 构造函数 直接解析登入报文信息
     *
     * @param paramTXR
     *         短报文
     */
    public AppSosFkiMsg(TXRMsg paramTXR) {
        int len = paramTXR.getMsgHexStr().length();
        if (len > 16) {
            appSosFkiMsg = paramTXR.getMsgHexStr().substring(2);
            setSosCode(BDMethod.castHexStringToDcmString(appSosFkiMsg.substring(0, 4)));
            setPhone(BDMethod.castHexStringToDcmString(appSosFkiMsg.substring(4, 14)));
            if(appSosFkiMsg.substring(14).length()>1){
                setContent(BDMethod.castHexStringToHanziString(appSosFkiMsg.substring(14)));
            }
        } else {
            // TODO: 2017/2/9  当得到登录回执报文过短时的处理
        }
    }

    public String getSosCode() {
        return sosCode;
    }

    public void setSosCode(String sosCode) {
        this.sosCode = sosCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
