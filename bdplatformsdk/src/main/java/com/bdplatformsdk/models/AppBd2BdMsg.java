package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * (平台协议)
 *
 * Created by admin on 2017/2/13.
 */

public class AppBd2BdMsg {

    private String appBd2BdMsgStr = "";
    private String SenderPhone = "";
    private String Content = "";


    public AppBd2BdMsg() {
    }

    /**
     * 构造函数
     *
     * @param paramTXR
     *         短报文
     */
    public AppBd2BdMsg(TXRMsg paramTXR) {
        int len = paramTXR.getMsgHexStr().length();
        if (len > 16) {
            appBd2BdMsgStr = paramTXR.getMsgHexStr().substring(6);
            setSenderPhone(BDMethod.castHexStringToDcmString(appBd2BdMsgStr.substring(0, 10)));
            if(appBd2BdMsgStr.substring(10).length()>1){
                setContent(BDMethod.castHexStringToHanziString(appBd2BdMsgStr.substring(10)));
            }
        } else {
            // TODO: 2017/2/9  当得到登录回执报文过短时的处理
        }
    }

    public String getSenderPhone() {
        return SenderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        SenderPhone = senderPhone;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
