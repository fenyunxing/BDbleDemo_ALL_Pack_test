package com.bddomainuni.models.entity.protocal2_1;

import android.util.Log;

import com.bddomainuni.models.CheckImpl;
import com.bddomainuni.repository.tools.BDMethod;

import java.io.UnsupportedEncodingException;


/**
 * 2.1协议通信信息类
 * Created by admin on 2016/10/20.
 * 修改日期 2017/4/9
 *
 * @version: V1.0.3
 */
public class TXRMsg implements CheckImpl {
    private String TXRstr;// 定位信息字符串
    private String InfoTypeStr;// 信息类别
    private String UserID;// 用户ID
    private String MsgTypeStr;// 电文形式
    private int MsgTypeInt = 2;// 电文形式
    private String SendTime;// 发信时间
    private String msgContent;// 电文内容
    private String platformDataType = "";
    private boolean Ifvaild = false;
    private boolean IfPlatform = false;


    private String msgHexStr;

    public TXRMsg() {
    }

    /**
     * 构造函数
     * 直接解析TXR字节串
     *
     * @param parambytes
     *         TXR字节串
     */
    public TXRMsg(byte[] parambytes) {
        try {
            TXRstr = new String(parambytes, "gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Ifvaild = BDMethod.CheckCKS(TXRstr);
        if (Ifvaild) {
            String[] items = TXRstr.split(",");
            if (items.length > 5) {
                this.setInfoTypeStr(items[1]);
                this.setUserID(items[2]);
                this.setMsgType(items[3]);
                this.setSendTime(items[4]);
                this.setMsgContent(items[3], items[5]);
            } else {
                Log.e("FDBDHZerror", "BDTXRMessage items.length<=12");
            }
        } else {
            this.setInfoTypeStr("1");
            this.setUserID("0000000");
            this.setMsgType("0");
            this.setSendTime("0000");
            //this.setMsgContent("0","aaa*00");//“该条报文出现误码*00”
        }

    }

    /**
     * 检测命令是否有效
     *
     * @return 有效标志位
     */
    public boolean getVaild() {
        return Ifvaild;
    }

    /**
     * 设置信息类型
     * @param infoStr 信息类型字符串（“1”~“5”）
     */
    public void setInfoTypeStr(String infoStr) {
        switch (infoStr) {
            case "1":
                InfoTypeStr = "普通";
                break;
            case "2":
                InfoTypeStr = "特快";
                break;
            case "3":
                InfoTypeStr = "通播";
                break;
            case "4":
                InfoTypeStr = "按最新存入查询通信";
                break;
            case "5":
                InfoTypeStr = "按发信地址查询通信";
                break;
            default:
                InfoTypeStr = "普通";
                break;
        }
    }

    /**
     * 获得用户ID
     *
     * @return 用户ID字符串
     */
    public String getUserID() {
        return UserID;
    }

    /**
     * 设置发送方卡号
     *
     * @param IDstr
     *         通信信息字节数组
     */
    public void setUserID(String IDstr) {
        this.UserID = IDstr;
    }

    /**
     * 获取电文形式
     *
     * @return 电文形式字符串
     */
    public String getMsgType() {
        return MsgTypeStr;
    }

    /**
     * 设置电文类型
     *
     * @param msgTypeStr
     *         通信信息字节数组
     */
    public void setMsgType(String msgTypeStr) {
        switch (msgTypeStr) {
            case "0":
                MsgTypeStr = "汉字";
                MsgTypeInt = 0;
                break;
            case "1":
                MsgTypeStr = "代码";
                MsgTypeInt = 1;
                break;
            case "2":
                MsgTypeStr = "混传";
                MsgTypeInt = 2;
                break;
            default:
                MsgTypeStr = "混传";
                MsgTypeInt = 2;
                break;
        }
    }

    /**
     * 获取信息类别
     *
     * @return 信息类别字符串
     */
    public String getInfoType() {
        return InfoTypeStr;
    }

    /**
     * 获取发信时间
     *
     * @return 发射时间字符串
     */
    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String timeStr) {
        if (!timeStr.equals("")) {
            String hourStr = timeStr.substring(0, 2);
            String minStr = timeStr.substring(2, 4);
            SendTime = hourStr + ":" + minStr;
        } else {
            SendTime = "null";
        }
    }

    /**
     * 获取通信内容
     *
     * @return 通信内容字符串
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * 设置通信内容
     *
     * @param Msgstr
     *         通信信息字节数组
     *修改日期2017/4/9
     * 修改内容: 修改代码解析方式
     * @throws UnsupportedEncodingException
     */
    public void setMsgContent(String msgTypeStr, String Msgstr) {
        switch (msgTypeStr) {
            case "0":
                msgContent = Msgstr.substring(0, Msgstr.indexOf("*"));
                break;
            case "1":
                /*if (Msgstr.indexOf("F") != -1) {
                    msgContent = Msgstr.substring(0, Msgstr.indexOf("F"));
                } else {
                    msgContent = Msgstr.substring(0, Msgstr.indexOf("*"));
                }*/
                msgContent = Msgstr.substring(0, Msgstr.indexOf("*"));
                setMsgHexStr(msgContent);
                break;
            case "2":
                //Log.v("FDBDChatTest",Msgstr.substring(2,4));
                setMsgHexStr(Msgstr
                        .substring(0, Msgstr.indexOf("*")));
                msgContent = BDMethod.castHexStringToHanziString(Msgstr
                        .substring(2, Msgstr.indexOf("*")));
                break;
            default:
                break;
        }
    }


    /**
     * 获取十六进制形式通信内容
     * @return
     */
    public String getMsgHexStr() {
        return msgHexStr;
    }

    /**
     * 设置十六进制形式通信内容
     * @param msgHexStr
     */
    private void setMsgHexStr(String msgHexStr) {
        this.msgHexStr = msgHexStr.toUpperCase();
    }

    /**
     * 获取TXR协议原始信息
     *
     * @return TXR协议原始信息
     */
    public String getTxrStr() {
        return TXRstr.substring(0,TXRstr.indexOf("*")+3);
    }

    /**
     * 判断是否为平台信息
     */
    public boolean isIfPlatform() {
        return IfPlatform;
    }

    /**
     * 设置平台信息标志位
     * @param ifPlatform
     */
    public void setIfPlatform(boolean ifPlatform) {
        IfPlatform = ifPlatform;
    }

    /**
     *获取平台信息类别
     */
    public String getPlatformDataType() {
        return platformDataType;
    }

    /**
     * 设置平台信息类别
     */
    public void setPlatformDataType(String platformDataType) {
        this.platformDataType = platformDataType;
    }

    /**
     * 获取电文形式int形式标志
     *
     * @return 文形式int形式标志 0：汉字 1：代码 2：混发
     */
    public int getMsgTypeInt() {
        return MsgTypeInt;
    }
}
