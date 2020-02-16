package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * (平台协议)平台登入反馈类
 *
 * Created by admin on 2017/2/13.
 */

public class AppNet2BdMsg {

    private String appNet2BdMsgStr = "";
    private String SenderPhone = "";
    private String Content = "";


    public AppNet2BdMsg() {
    }

    /**
     * 构造函数 直接解析登入报文信息
     *
     * @param paramTXR
     *         短报文
     */
    public AppNet2BdMsg(TXRMsg paramTXR) {
        int len = paramTXR.getMsgHexStr().length();
        if (len > 16) {
            appNet2BdMsgStr = paramTXR.getMsgHexStr().substring(6);
            setSenderPhone(BDMethod.castHexStringToDcmString(appNet2BdMsgStr.substring(0, 10)));
            if(appNet2BdMsgStr.substring(10).length()>1){
                setContent(BDMethod.castHexStringToHanziString(appNet2BdMsgStr.substring(10)));
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
