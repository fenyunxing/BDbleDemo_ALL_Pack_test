package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.RMCMsg;
import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * (平台协议)平台登入反馈类
 *
 * Created by admin on 2017/2/13.
 */

public class AppNet2BdByPosMsg {

    private String appNet2BdByPosMsgStr = "";
    private String SenderPhone = "";
    private String Content = "";
    private RMCMsg rmcMsg = new RMCMsg();

    public AppNet2BdByPosMsg() {
    }

    /**
     * 构造函数 直接解析登入报文信息
     *
     * @param paramTXR
     *         短报文
     */
    public AppNet2BdByPosMsg(TXRMsg paramTXR) {
        int len = paramTXR.getMsgHexStr().length();
        if (len > 37) {
            appNet2BdByPosMsgStr = paramTXR.getMsgHexStr().substring(6);
            setSenderPhone(BDMethod.castHexStringToDcmString(appNet2BdByPosMsgStr.substring(0, 10)));
            setRmcMsg(BDMethod.parsePosition(appNet2BdByPosMsgStr.substring(10, 30),rmcMsg));
            if(appNet2BdByPosMsgStr.substring(30).length()>1){
                setContent(BDMethod.castHexStringToHanziString(appNet2BdByPosMsgStr.substring(30)));
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

    public RMCMsg getRmcMsg() {
        return rmcMsg;
    }

    public void setRmcMsg(RMCMsg rmcMsg) {
        this.rmcMsg = rmcMsg;
    }
}
