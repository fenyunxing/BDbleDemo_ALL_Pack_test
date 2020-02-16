package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * (平台协议)卡对卡通信类
 *
 * Created by admin on 2017/2/13.
 */

public class CardtoCardMsg {

    private String Msg;
    private String revCard;


    public CardtoCardMsg() {
    }

    /**
     * 构造函数
     * 直接解析卡对卡用户信息
     *
     * @param paramTXR
     *         TXR北斗短报文
     */
    public CardtoCardMsg(TXRMsg paramTXR) {
        if (paramTXR.getMsgHexStr().length() > 7) {
            setMsg(paramTXR.getMsgHexStr().substring(6));
            setRevCard(paramTXR.getUserID());
        } else {
            // TODO: 2017/2/13  当得到卡对卡报文过短时的处理
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
     * 获取十六进制消息内容
     */
    public String getHexMsg() {
        return Msg;
    }

    /**
     * 设置消息内容
     */
    private void setMsg(String msg) {
        Msg = msg;
    }

    /**
     * 获取收方卡号
     */
    public String getRevCard() {
        return revCard;
    }

    /**
     * 设置收方卡号
     */
    private void setRevCard(String revCard) {
        this.revCard = revCard;
    }
}
