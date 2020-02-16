package com.bddomainuni.models.entity.protocal4_0;

import android.util.Log;

import com.bddomainuni.repository.tools.BDMethod;


/**
 * 通信信息类
 *
 * Created by admin on 2017/5/24.
 */

public class TXXXMsg {
    private boolean vaild = false;
    private String ic;
    private String txxxHexStr;
    private byte[] txxxByte;

    private int msgType;//电文形式
    private int feedback;//是否回执
    private int commuType;//通信方式
    private int key;//是否有秘钥

    private String sendIc;
    private String sendTimeH;
    private String sendTimeM;
    private String sendTime;
    private int msgBitLength;
    private int msgByteLength;
    private byte[] msgByte;
    private String msg;
    private int crcFlag;

    public TXXXMsg(byte[] parambytes) {
        vaild = BDMethod.CheckCKS_40(parambytes);
        if (vaild) {
            this.txxxHexStr = BDMethod.castBytesToHexString(parambytes);
            this.txxxByte = parambytes;
            this.ic = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[7], parambytes[8], parambytes[9]});

            this.msgType = (parambytes[10] & 0x20) == 0x20 ? 1 : 0;
            /*Log.v("FDBDTestLog","parambytes[10] & 0x20 ==>"+(parambytes[10] & 0x20));
            Log.v("FDBDTestLog","msgTypeHexStr ==>"+BDMethod.castByteToHexString(parambytes[10]));
            Log.v("FDBDTestLog","msgType ==>"+msgType);*/
            this.feedback = (parambytes[10] & 0x10) == 0x10 ? 1 : 0;
            this.commuType = (parambytes[10] & 0x08) == 0x08 ? 1 : 0;
            this.key = (parambytes[10] & 0x04) == 0x04 ? 1 : 0;

            this.sendIc = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[11], parambytes[12], parambytes[13]});
            this.sendTimeH = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[14]});
            this.sendTimeM = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[15]});
            this.sendTime = sendTimeH + ":" + sendTimeM;
            this.msgBitLength = BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, parambytes[16], parambytes[17]});
            this.msgByteLength = this.msgBitLength / 8;
            this.crcFlag = BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[txxxHexStr.length() / 2 - 2]});

            msgByte = new byte[msgByteLength];

            try {
                if (msgType == 0) {
                    //汉字
                    System.arraycopy(parambytes, 18, msgByte, 0, msgByteLength);
                    msg = new String(msgByte, "GBK");
                } else {
                    Log.v("FDBDTestLog","parambytes[18] ==>"+BDMethod.castByteToHexString(parambytes[18]));
                    if (parambytes[18] != (byte)0xA4) {
                        System.arraycopy(parambytes, 18, msgByte, 0, msgByteLength);
                        msg = new String(msgByte);
                        //daima
                    } else {
                        msgByte = new byte[msgByteLength-1];
                        this.msgType = 2;
                        System.arraycopy(parambytes, 19, msgByte, 0, msgByteLength-1);
                        msg = new String(msgByte, "GBK");
                        //hunfa
                    }
                }
            } catch (Exception e) {
                Log.v("FDBDErrorLog", "TXXX截取通信内容出错");
                e.printStackTrace();
            }
        }
    }

    public boolean isVaild() {
        return vaild;
    }

    public String getIc() {
        return ic;
    }

    public String getTxxxHexStr() {
        return txxxHexStr;
    }

    public byte[] getTxxxByte() {
        return txxxByte;
    }

    public String getSendIc() {
        return sendIc;
    }

    public String getSendTimeH() {
        return sendTimeH;
    }

    public String getSendTimeM() {
        return sendTimeM;
    }

    public String getSendTime() {
        return sendTime;
    }

    public int getMsgBitLength() {
        return msgBitLength;
    }

    public int getMsgByteLength() {
        return msgByteLength;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getCrcFlag() {
        return this.crcFlag == 0 ? true : false;
    }

    public String getMsgType() {
        switch (this.msgType) {
            case 0:
                return "汉字";
            case 1:
                return "代码";
            case 2:
                return "混发";
            default:
                return "代码";
        }
    }

    public boolean ifFeedback() {
        return this.feedback == 0 ? true : false;
    }

    public String getCommuType() {
        return this.commuType == 0 ? "通信" : "查询";
    }

    public boolean ifKey() {
        return this.key == 0 ? false : true;
    }
}
