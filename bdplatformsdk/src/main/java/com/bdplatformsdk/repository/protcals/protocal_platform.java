package com.bdplatformsdk.repository.protcals;


import com.bddomainuni.repository.tools.BDMethod;


import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * 北斗平台通信协议类 protocal_platform ProtocalPlatform Created by admin on 2017/1/20. Modified by admin on
 * 2017/5/13
 */
public class protocal_platform {


    public final static String APP_BD_LOGIN = "10";
    public final static String APP_BD_LOGIN_FKI = "1A";
    public final static String APP_BD2NET = "11";
    public final static String APP_BD2NET_POS = "21";
    public final static String APP_NET2BD = "1F";
    public final static String APP_NET2BD_POS = "2F";
    public final static String APP_BD2BD = "12";
    public final static String APP_BD2BD_POS = "22";
    public final static String APP_BD2PHONE = "13";
    public final static String APP_BD2PHONE_POS = "23";
    public final static String APP_OFFLINE_MSG = "16";
    public final static String APP_SOS_FKI = "1E";


    public final static String appTypeList = APP_BD_LOGIN_FKI + "," + APP_NET2BD + "," + APP_NET2BD_POS + "," + APP_SOS_FKI
            + "," + APP_BD2NET + "," + APP_BD2NET_POS;


    public final static byte TYPE_CARD_2_CARD = 0x01;
    public final static String TYPE_CARD_2_CARD_STR = "01";
    public final static byte TYPE_CARD_2_PHONE = 0x02;
    public final static String TYPE_CARD_2_PHONE_STR = "02";
    public final static byte TYPE_USER_2_USER = 0x03;
    public final static String TYPE_USER_2_USER_STR = "03";
    public final static byte TYPE_POISITION_UPDATE = 0x04;
    public final static String TYPE_POISITION_UPDATE_STR = "04";
    public final static byte TYPE_TASK_REV = 0x05;
    public final static String TYPE_TASK_REV_STR = "05";
    public final static byte TYPE_TASK_REPORT = 0x06;
    public final static String TYPE_TASK_REPORT_STR = "06";
    public final static byte TYPE_FKI_TASK_REPORT = 0x07;
    public final static String TYPE_FKI_TASK_REPORT_STR = "07";
    public final static byte TYPE_LOGIN = 0x08;
    public final static String TYPE_LOGIN_STR = "08";
    public final static byte TYPE_FKI_LOGIN = 0x09;
    public final static String TYPE_FKI_LOGIN_STR = "09";
    public final static byte TYPE_FKI_TASK_REV = 0x10;
    public final static String TYPE_FKI_TASK_REV_STR = "10";
    public final static byte TYPE_USER_2_PHONE_BYLOC = 0x12;
    public final static String TYPE_USER_2_PHONE_BYLOC_STR = "12";
    public final static byte TYPE_USER_2_USER_BYLOC = 0x13;
    public final static String TYPE_USER_2_USER_BYLOC_STR = "13";

    //任务单完成情况
    public final static byte TASK_COMPLETED = 0x01;
    public final static String TASK_COMPLETED_STR = "01";
    public final static byte TASK_FAILED = 0x02;
    public final static String TASK_FAILED_STR = "02";
    public final static byte TASK_NOFOUND_PROBLEM = 0x03;
    public final static String TASK_NOFOUND_PROBLEM_STR = "03";
    public final static byte TASK_NOFOUND_POSITION = 0x04;
    public final static String TASK_NOFOUND_POSITION_STR = "04";

    //紧急报警
    public final static byte EMERGENCY_ALARM = 0x15;
    public final static byte EMERGENCY_ALARM_CANCEL = 0x16;
    public final static String EMERGENCY_PLATFORM_RESPONSE_STR = "17";

    //通信类别 0特快  1 普通
    public final static int COMMTYPE_FAST = 0;
    public final static int COMMTYPE_NORMAL = 1;

    //传输方式 0汉字  1 代码  2 混发 3平台
    public final static int HANZI = 0;
    public final static int DAIMA = 1;
    public final static int HUNFA = 2;

    //登录状态
    public final static String LOGIN_SUCCESSED = "00";
    public final static String LOGIN_PASSWORD_ERROR = "01";
    public final static String LOGIN_NO_USER = "02";

    //是否为多卡
    public final static boolean FLAG_MULTICARD = true;
    public final static boolean FLAG_SINGLECARD = false;

    /**
     * 组装卡对卡发送通信消息指令方法
     *
     * @param ic
     *         收方卡号
     * @param msg
     *         通信内容
     */
    public static byte[] gen_msg_tocard(String ic, String msg) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_CARD_2_CARD, FLAG_SINGLECARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(headHexBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    public static byte[] gen_msg_tocard(String ic, String msg, String hexLnltStr) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_CARD_2_CARD, FLAG_SINGLECARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] lnltHexBytes = ("A5" + hexLnltStr).getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(headHexBytes, lnltHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装北斗用户发送消息至手机用户指令方法
     *
     * @param ic
     *         收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param phonenum
     *         接收方手机号码
     * @param msg
     *         通信内容
     */
    public static byte[] gen_msg_tophone(String ic, String sendUser, String phonenum, String msg) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_CARD_2_PHONE, FLAG_SINGLECARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + phonenum).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装北斗用户发送消息至手机用户指令方法(多卡)
     *
     * @param ic
     *         接收方卡号（平台）
     * @param mainCrd
     *         主卡
     * @param sendUser
     *         发送方用户名
     * @param phone
     *         接收方手机
     * @param msg
     *         通信内容
     */
    public static byte[] gen_msg_tophone_multCrd(String ic, String mainCrd, String sendUser, String phone, String msg) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_CARD_2_PHONE, FLAG_MULTICARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes,
                StringUtils.leftPad(BDMethod.castDcmStringToHexString(mainCrd), 6, "0").getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + phone).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /*public static byte[] gen_msg_tophone(String ic, String sendUser, String phonenum, String msg, String hexLnltStr) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_CARD_2_PHONE, FLAG_SINGLECARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + phonenum).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] lnltHexBytes = ("A5" + hexLnltStr).getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, lnltHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }*/

    /**
     * 组装北斗用户发送消息至手机用户指令方法（带位置新）
     *
     * @param ic
     *         接收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param phone
     *         接收方手机
     * @param msg
     *         通信内容
     * @param lnlt
     *         位置信息
     */
    public static byte[] gen_msg_tophoneByLoc(String ic, String sendUser, String phone, String msg, String lnlt) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_USER_2_PHONE_BYLOC, FLAG_SINGLECARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + phone).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, lnlt.getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装北斗用户发送消息至手机用户指令方法（多卡，带位置新）
     *
     * @param ic
     *         接收方卡号（平台）
     * @param mainCrd
     *         主卡
     * @param sendUser
     *         发送方用户名
     * @param phone
     *         接收方手机
     * @param msg
     *         通信内容
     * @param lnlt
     *         位置信息
     */
    public static byte[] gen_msg_tophoneByLoc_multCrd(String ic, String mainCrd, String sendUser, String phone, String msg, String lnlt) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_USER_2_PHONE_BYLOC, FLAG_MULTICARD);

        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes,
                StringUtils.leftPad(BDMethod.castDcmStringToHexString(mainCrd), 6, "0").getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + phone).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, lnlt.getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装北斗用户发送给北斗用户通信消息的指令方法
     *
     * @param ic
     *         接收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param revUser
     *         接收方用户名
     * @param msg
     *         通信内容
     */
    public static byte[] gen_msg_touser(String ic, String sendUser, String revUser, String msg) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_USER_2_USER, FLAG_SINGLECARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + revUser).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装北斗用户发送给北斗用户通信消息的指令方法(多卡)
     *
     * @param ic
     *         接收方卡号（平台）
     * @param mainCrd
     *         主卡
     * @param sendUser
     *         发送方用户名
     * @param revUser
     *         接收方用户名
     * @param msg
     *         通信内容
     */
    public static byte[] gen_msg_touser_multCrd(String ic, String mainCrd, String sendUser, String revUser, String msg) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_USER_2_USER, FLAG_MULTICARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes,
                StringUtils.leftPad(BDMethod.castDcmStringToHexString(mainCrd), 6, "0").getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + revUser).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装北斗用户发送给北斗用户通信消息的指令方法（带位置旧）
     *
     * @param ic
     *         接收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param revUser
     *         接收方用户名
     * @param msg
     *         通信内容
     * @param hexLnltStr
     *         位置信息
     */
    /*public static byte[] gen_msg_touser(String ic, String sendUser, String revUser, String msg, String hexLnltStr) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_USER_2_USER, FLAG_SINGLECARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + revUser).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] lnltHexBytes = ("A5" + hexLnltStr).getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, lnltHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }*/

    /**
     * 组装北斗用户发送给北斗用户通信消息的指令方法（带位置新）
     *
     * @param ic
     *         接收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param revUser
     *         接收方用户名
     * @param msg
     *         通信内容
     * @param lnlt
     *         位置信息
     */
    public static byte[] gen_msg_touserByLoc(String ic, String sendUser, String revUser, String msg, String lnlt) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_USER_2_USER_BYLOC, FLAG_SINGLECARD);
        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + revUser).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, lnlt.getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装北斗用户发送给北斗用户通信消息的指令方法（多卡，带位置新）
     *
     * @param ic
     *         接收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param revUser
     *         接收方用户名
     * @param msg
     *         通信内容
     * @param lnlt
     *         位置信息
     */
    public static byte[] gen_msg_touserByLoc_multCrd(String ic, String mainCrd, String sendUser, String revUser, String msg, String lnlt) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_USER_2_USER_BYLOC, FLAG_MULTICARD);

        byte[] msgBytes = {};
        try {
            msgBytes = msg.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes,
                StringUtils.leftPad(BDMethod.castDcmStringToHexString(mainCrd), 6, "0").getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + revUser).getBytes());
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, lnlt.getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装位置上报指令方法
     *
     * @param ic
     *         收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param msgRMC
     *         位置信息
     */
    @Deprecated
    public static byte[] gen_position_upload(String ic, String sendUser, String msgRMC) {
        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_POISITION_UPDATE, FLAG_SINGLECARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, msgRMC.getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装任务单上报指令方法
     *
     * @param ic
     *         收方卡号(平台)
     * @param sendUser
     *         发送方用户名
     * @param taskNum
     *         任务单号
     * @param taskState
     *         任务完成状态
     * @param taskDescr
     *         任务上报备注
     */
    public static byte[] gen_task_report(String ic, String sendUser, String taskNum, byte taskState, String taskDescr) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_TASK_REPORT, FLAG_SINGLECARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, taskNum.getBytes());
        String taskStateHexStr = BDMethod.castBytesToHexString(new byte[]{(byte) taskState});
        byte[] taskStateHexBytes = taskStateHexStr.getBytes();
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, taskStateHexBytes);
        byte[] msgBytes = {};
        try {
            msgBytes = taskDescr.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String msgHexStr = BDMethod.castBytesToHexString(msgBytes);
        byte[] msgHexBytes = msgHexStr.getBytes();
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, msgHexBytes);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装登入申请指令方法
     *
     * @param ic
     *         收方卡号(平台)
     * @param sendUser
     *         登入用户名
     * @param password
     *         登入密码
     * @param lgln
     *         登入位置
     */
    public static byte[] gen_login(String ic, String sendUser, String password, String lgln) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_LOGIN, FLAG_SINGLECARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, password.getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, lgln.getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }


    public static String gen_location_add(boolean ifValid, boolean ifLngE, boolean ifLatN, double lng, double lat) {

        byte[] type = new byte[1];
        String loginLocMsg = "";
        if (ifValid) {
            if (ifLngE) {
                if (ifLatN) {
                    type[0] = 0x04;
                } else {
                    type[0] = 0x05;
                }
            } else {
                if (ifLatN) {
                    type[0] = 0x06;
                } else {
                    type[0] = 0x07;
                }
            }
        } else {
            type[0] = 0x00;
        }
        loginLocMsg = BDMethod.castBytesToHexString(type);
        //转换经纬度119°16′20.868″E 26°11′20.868″N  –>  26189130 ,119339130  –> 018F9D4A,071CF87A
        String latStr = "" + (int) (lat * 1000000);
        String lngStr = "" + (int) (lng * 1000000);
        loginLocMsg = loginLocMsg +
                StringUtils.leftPad(BDMethod.castDcmStringToHexString(lngStr), 8, "0") +
                StringUtils.leftPad(BDMethod.castDcmStringToHexString(latStr), 8, "0");

        return loginLocMsg;
    }


    /**
     * 组装登入申请指令方法(多卡)
     *
     * @param ic
     *         收方卡号(平台)
     * @param mainCrd
     *         主卡号
     * @param sendUser
     *         登入用户名
     * @param password
     *         登入密码
     * @param lgln
     *         登入位置
     */
    public static byte[] gen_login_multCrd(String ic, String mainCrd, String sendUser, String password, String lgln) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_LOGIN, FLAG_MULTICARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes,
                StringUtils.leftPad(BDMethod.castDcmStringToHexString(mainCrd), 6, "0").getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, password.getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, lgln.getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 组装任务单接单指令方法
     *
     * @param ic
     *         收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param taskNum
     *         任务单号
     */
    public static byte[] gen_task_confirm(String ic, String sendUser, String taskNum) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_FKI_TASK_REV, FLAG_SINGLECARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, taskNum.getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), totalheaderBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /*public static byte[] phoneCompress(String phonenum) {
        byte[] compressPhone = BDMethod.BytesTwoToOne("0" + phonenum);
        return compressPhone;
    }*/

    /**
     * 组装平台协议头方法
     *
     * @param dataStyle
     *         协议类型
     */
    public static byte[] gen_header(byte dataStyle, boolean ifMulticard) {
        byte[] hfHeaderByte;
        //组合0XA4与0XAA
        if (ifMulticard) {
            hfHeaderByte = BDMethod.conArrayOfBytes(new byte[]{(byte) 0xA4}, new byte[]{(byte) 0xAB});
        } else {
            hfHeaderByte = BDMethod.conArrayOfBytes(new byte[]{(byte) 0xA4}, new byte[]{(byte) 0xAA});
        }
        //组合 0XA4 0XAA与数据类型0X01
        byte[] headBytes = BDMethod.conArrayOfBytes(hfHeaderByte, new byte[]{dataStyle});
        String headHexStr = BDMethod.castBytesToHexString(headBytes);
        byte[] headHexBytes = headHexStr.getBytes();
        return headHexBytes;
    }

    /**
     * 紧急报警
     *
     * @param ic
     *         接收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param emergencyMsg
     *         报警内容
     */
    public static byte[] gen_msg_emergency_alarm(String ic, String sendUser, byte[] emergencyMsg) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(EMERGENCY_ALARM, FLAG_SINGLECARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, emergencyMsg);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 紧急报警(多卡)
     *
     * @param ic
     *         接收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param emergencyMsg
     *         报警内容
     */
    public static byte[] gen_msg_emergency_alarm_multCrd(String ic, String mainCrd, String sendUser, byte[] emergencyMsg) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(EMERGENCY_ALARM, FLAG_MULTICARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes,
                StringUtils.leftPad(BDMethod.castDcmStringToHexString(mainCrd), 6, "0").getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + sendUser).getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, emergencyMsg);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 警报解除
     *
     * @param ic
     *         接收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param emergencyMsg
     *         报警内容
     */
    public static byte[] gen_msg_emergency_cancel(String ic, String sendUser, byte[] emergencyMsg) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(EMERGENCY_ALARM_CANCEL, FLAG_SINGLECARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, emergencyMsg);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    /**
     * 警报解除(多卡)
     *
     * @param ic
     *         接收方卡号（平台）
     * @param sendUser
     *         发送方用户名
     * @param emergencyMsg
     *         报警内容
     */
    public static byte[] gen_msg_emergency_cancel_multCrd(String ic, String mainCrd, String sendUser, byte[] emergencyMsg) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(EMERGENCY_ALARM_CANCEL, FLAG_MULTICARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes,
                StringUtils.leftPad(BDMethod.castDcmStringToHexString(mainCrd), 6, "0").getBytes());
        totalheaderBytes = BDMethod.conArrayOfBytes(totalheaderBytes, ("0" + sendUser).getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, emergencyMsg);
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    public static byte[] gen_msg_pos_update(String ic, String sendUser, int locNum, String pos) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_POISITION_UPDATE, FLAG_SINGLECARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, "0000".getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, BDMethod.castIntToHexStringByNum(locNum, 2).getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, pos.getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }

    public static byte[] gen_msg_pos_update_multCrd(String ic, String sendUser, int locNum, String pos) {

        String cmd = "CCTXA," + ic + "," + COMMTYPE_NORMAL + "," + HUNFA + ",";
        byte[] headHexBytes = gen_header(TYPE_POISITION_UPDATE, FLAG_MULTICARD);
        byte[] totalheaderBytes = BDMethod.conArrayOfBytes(headHexBytes, ("0" + sendUser).getBytes());
        byte[] tmpBytes = BDMethod.conArrayOfBytes(totalheaderBytes, "0000".getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, BDMethod.castIntToHexStringByNum(locNum, 2).getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(tmpBytes, pos.getBytes());
        tmpBytes = BDMethod.conArrayOfBytes(cmd.getBytes(), tmpBytes);
        byte ret = BDMethod.CheckByte(tmpBytes, tmpBytes.length);
        String crc = BDMethod.castByteToHexString(ret);
        byte[] result = BDMethod.conArrayOfBytes("$".getBytes(), tmpBytes);
        result = BDMethod.conArrayOfBytes(result, "*".getBytes());
        result = BDMethod.conArrayOfBytes(result, crc.getBytes());
        result = BDMethod.conArrayOfBytes(result, "\r\n".getBytes());
        return result;
    }
}
