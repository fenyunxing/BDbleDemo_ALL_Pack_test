package com.bddomainuni.repository.protcals;


import com.bddomainuni.models.entity.protocal2_1.RMCMsg;
import com.bddomainuni.repository.tools.BDMethod;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.bddomainuni.repository.protcals.protocal2_1.COMMTYPE_NORMAL;
import static com.bddomainuni.repository.protcals.protocal2_1.DAIMA;
import static com.bddomainuni.repository.protcals.protocal2_1.gen_cctxa;

/**
 * 北斗盒子自定协议类 Created by admin on 2016/11/22.
 */
public class protocal_BDHZ {

    /**
     * 组装终端密码设置/登录(CCPWD)
     *
     * @param type
     *         类型 ‘1’:密码设置 ‘2’:登录
     * @param pwd
     *         密码，六位数字或者英文字母
     */
    public static String gen_ccpwd(String type, String pwd) {
        String cmd = "CCPWD," + type + "," + pwd + ",";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }


    /**
     * 终端自检输出设置(CCZDC)
     *
     * @param freq
     *         输出频度  ‘0’-‘10’ 单位：秒，为‘0’时表示单次输出
     */
    public static String gen_cczdc(String freq) {
        String cmd = "CCZDC," + freq + ",";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }


    /**
     * OK键设置和查询（CCOKS）
     */
    public static String gen_ccoks(protocalEntity.CCOKSobj obj) {
        String cmd = "CCOKS," + obj.getType() + "," + obj.getCenterCrd() + "," + obj.getMsg() + ",";
        byte[] tmp = {};
        try {
            tmp = cmd.getBytes("gb18030");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 极限追踪模式设置/查询(CCZZM)
     */
    public static String gen_cczzm(protocalEntity.CCZZMobj obj) {
        String cmd = "CCZZM," + obj.getType() + "," + obj.getCenterCrd() + "," + obj.getFreq() + "," + obj.getMode() + ",";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * SOS参数设置/查询(CCSHM)
     */
    public static String gen_ccshm(protocalEntity.CCSHMobj obj) {
        String cmd = "CCSHM," + obj.getType() + "," + obj.getCenterCrd() + "," + obj.getFreq() + "," + obj.getMsg() + ",";
        byte[] tmp = {};
        try {
            tmp = cmd.getBytes("gb18030");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }


    /**
     * 启动/关闭SOS救援(CCQJY)
     */
    public static String gen_ccqjy(boolean ifopen) {
        String openFlag;
        if(ifopen){
            openFlag = "1";
        }else {
            openFlag = "0";
        }
        String cmd = "CCQJY," + openFlag +",";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 启动/关闭极限追踪(CCQZZ)
     */
    public static String gen_ccqzz(boolean ifopen) {
        String openFlag;
        if(ifopen){
            openFlag = "1";
        }else {
            openFlag = "0";
        }
        String cmd = "CCQZZ," + openFlag +",";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 启动/关闭极限追踪(CCQOK)
     */
    public static String gen_ccqok(boolean ifopen) {
        String openFlag;
        if(ifopen){
            openFlag = "1";
        }else {
            openFlag = "0";
        }
        String cmd = "CCQOK," + openFlag +",";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 查询系统当前工作模式(CCMSC)
     * @return
     */
    public static String gen_ccmsc() {
        String cmd = "CCMSC,";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 版本查询（CCVRQ）
     * @return
     */
    public static String gen_ccvrq() {
        String cmd = "CCVRQ,";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    public static final String TYPE_BDNET_BD_TO_NET = "11";//北斗网到公网0x11
    public static final String TYPE_BDNET_BD_TO_NET_BYPOS = "21";//北斗网到公网0x21(带位置)
    public static final String TYPE_BDNET_NET_TO_BD = "1F";//公网到北斗网0x1F
    public static final String TYPE_BDNET_NET_TO_BD_BYPOS = "2F";//公网到北斗网0x2F(带位置)
    public static final String TYPE_BDNET_BD_TO_BD = "12";//北斗网到北斗网0x12
    public static final String TYPE_BDNET_BD_TO_BD_BYPOS = "22";//北斗网到北斗网0x22(带位置)
    public static final String TYPE_BDNET_BD_TO_PHONE = "13";//北斗网到手机短信0x13
    public static final String TYPE_BDNET_BD_TO_PHONE_BYPOS = "23";//北斗网到手机短信0x23(带位置)
    public static final String TYPE_BDNET_POSUP = "14";//手机位置上报0x14
    public static final String TYPE_BDNET_APP_SOS = "15";//北斗网下APP报警0x15(带位置)
    public static final String TYPE_BDNET_GET_OFFLINE = "16";//获取离线消息
    public static final String TYPE_BDNET_SEND_OFFLINE = "1D";//发送离线消息
    public static final String TYPE_BDNET_SOS_FEEDBACK = "1E";//救援站消息
    public static final String TYPE_BDNET_LOGIN = "10";//北斗网登录
    public static final String TYPE_BDNET_LOGIN_FEEDBACK = "1A";//北斗网登录反馈
    public static final String TYPE_BDNET_OK = "F1";//手机APP的OK上报
    public static final String TYPE_BDNET_SOS = "F2";//手机APP的上报SOS
    public static final String TYPE_BDNET_ZZM = "F3";//手机APP的上报极限追踪


    //登录选项
    public static final String TYPE_LOGIN_NORMAL = "00";//正常登录
    public static final String TYPE_LOGIN_MSG = "01";//登录并获取离线消息
    public static final String TYPE_LOGIN_OTHER = "02";//其他类型，待定


    //登录状态
    public static final String STATE_LOGIN_OK = "00";//登录成功
    public static final String STATE_LOGIN_UNAUTH = "01";//账号或密码错误
    public static final String STATE_LOGIN_FAIL = "02";//登录失败
    public static final String STATE_LOGIN_ERROR = "03";//系统出错

    /**
     * 手机位置上报
     *
     * @param ic
     * @param rmcMsgs
     */
    public static String gen_txa_wzsb(String ic, List<RMCMsg> rmcMsgs) {
        String msgType = TYPE_BDNET_POSUP;
        String reserve = "000000000000";
        String number = "0" + rmcMsgs.size();
        String posData = "";

        for (int i = 0, len = rmcMsgs.size(); i < len; i++) {
            String time = rmcMsgs.get(i).gettimeStampHex();
            posData = posData + packPosition(rmcMsgs.get(i)) + time;
        }
        return gen_cctxa(ic, COMMTYPE_NORMAL, DAIMA, msgType + reserve + number + posData);
    }


    /**
     * 北斗网到北斗网通信
     * @param centerIc
     * @param msg
     *
     * @return
     */
    public static String gen_txa_bd2bd(String centerIc, String msg) {
        String msgType = TYPE_BDNET_BD_TO_BD;
        String reserve = "00";
        return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve  + BDMethod.castStrToHexStrByGB18030(msg));
    }

    /**
     * 北斗网至北斗网（带位置）
     *
     * @param centerIc
     * @param msg
     * @param pos
     *
     * @return
     */
    public static String gen_txa_bd2bd_bypos(String centerIc, String msg, RMCMsg pos) {
        String msgType = TYPE_BDNET_BD_TO_BD_BYPOS;
        String reserve = "00";
        return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve  + packPosition(pos) + BDMethod.castStrToHexStrByGB18030(msg));
    }


    /**
     * 北斗网发送至公网
     *
     * @param centerIc
     * @param phone
     * @param msg
     */
    public static String gen_txa_bd2net(String centerIc, long phone, String msg) {
        String msgType = TYPE_BDNET_BD_TO_NET;
        String reserve = "0000";
        String userPhone = BDMethod.castLongToHexStringByNum(phone, 10);
        return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve + userPhone + BDMethod.castStrToHexStrByGB18030(msg));
    }

    /**
     * 北斗网发送至公网（带位置）
     * @param centerIc
     * @param phone
     * @param msg
     * @param pos
     *
     * @return
     */
    public static String gen_txa_bd2net_bypos(String centerIc, long phone, String msg, RMCMsg pos) {
        String msgType = TYPE_BDNET_BD_TO_NET_BYPOS;
        String reserve = "0000";
        String userPhone = BDMethod.castLongToHexStringByNum(phone, 10);

        return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve + userPhone + packPosition(pos) + BDMethod.castStrToHexStrByGB18030(msg));
    }

    /**
     * 北斗网上报给手机短信
     *
     * @param centerIc
     * @param phone
     * @param msg
     *
     * @return
     */
    public static String gen_txa_bd2phone(String centerIc, long phone, String msg) {
        String msgType = TYPE_BDNET_BD_TO_PHONE;
        String reserve = "0000";
        String userPhone = BDMethod.castLongToHexStringByNum(phone, 10);
        return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve + userPhone + BDMethod.castStrToHexStrByGB18030(msg));
    }

    /**
     * 北斗网上报给手机（带位置）
     *
     * @param centerIc
     * @param phone
     * @param msg
     * @param pos
     *
     * @return
     */
    public static String gen_txa_bd2phone_bypos(String centerIc, long phone, String msg, RMCMsg pos) {
        String msgType = TYPE_BDNET_BD_TO_PHONE_BYPOS;
        String reserve = "0000";
        String userPhone = BDMethod.castLongToHexStringByNum(phone, 10);
        return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve + userPhone + packPosition(pos) + BDMethod.castStrToHexStrByGB18030(msg));
    }


    /**
     * 北斗网下登录
     *
     * @param centerIc
     * @param loginType
     * @param phone
     * @param pw
     *
     * @return
     */
    public static String gen_txa_bd_login(String centerIc, String loginType, long phone, String pw) {
        String msgType = TYPE_BDNET_LOGIN;
        String reserve = "0000000000000000";
        String startTime = "FFFFFFFF";
        String endTime = "FFFFFFFF";
        String userPhone = BDMethod.castLongToHexStringByNum(phone, 10);
        String pwLen = BDMethod.castIntToHexStringByNum(pw.length(), 2);
        return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve + loginType + startTime + endTime + userPhone + pwLen + BDMethod.castStrToHexStrByGB18030(pw));
    }

    /**
     * 北斗网下手机app上报OK信息
     *
     * @param centerIc
     * @param msg
     * @param pos
     *
     * @return
     */
    public static String gen_txa_bd_ok(String centerIc, String msg, RMCMsg pos) {
        String msgType = TYPE_BDNET_OK;
        String reserve = "00";
        return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve  + packPosition(pos) + BDMethod.castStrToHexStrByGB18030(msg));
    }


    public final static String TYPE_SOS_HELP = "00";//求助，默认状态
    public final static String TYPE_SOS_FOOD = "01";
    public final static String TYPE_SOS_DEVICE = "02";
    public final static String TYPE_SOS_HURT = "03";
    public final static String TYPE_SOS_LOST = "04";
    public final static String TYPE_SOS_LIFE = "05";

    /**
     * 北斗网下发送SOS
     *
     * @param centerIc
     * @param phone
     * @param status
     * @param msg
     * @param pos
     *
     * @return
     */
    public static String gen_txa_bd_sos(String centerIc, long phone, String status,String msg, RMCMsg pos) {
        String msgType = TYPE_BDNET_SOS;
        String reserve = "00";
        String userPhone = "";
        if(phone == 0){
            userPhone = "FFFFFFFFFF";
        }else{
            userPhone = BDMethod.castLongToHexStringByNum(phone, 10);
        }
        return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve  + userPhone +packPosition(pos)
                + status + BDMethod.castStrToHexStrByGB18030(msg));
    }


    /**
     * 北斗网手机app极限上报
     *
     * @param centerIc
     * @param rmcMsgs
     *
     * @return
     */
    public static String gen_txa_bd_zzm(String centerIc, List<RMCMsg> rmcMsgs) {
            String msgType = TYPE_BDNET_ZZM;
            String reserve = "000000000000";
            String number = "0" + rmcMsgs.size();
            String posData = "";

            for (int i = 0, len = rmcMsgs.size(); i < len; i++) {
                String time = rmcMsgs.get(i).gettimeStampHex();
                posData = posData + packPosition(rmcMsgs.get(i)) + time;
            }
            return gen_cctxa(centerIc, COMMTYPE_NORMAL, DAIMA, msgType + reserve + number + posData);
        }

    public static String gen_dljc() {
        String result = "$BDHZ,DLJC,1*24\r\n";
        return result;
    }

    public static String gen_ztjc() {
        String result = "$BDHZ,ZTJC,1*22\r\n";
        return result;
    }

    /**
     * 组装设置命令(自定义命令)
     *
     * @param freq
     *         频度
     * @param IC
     *         SOS上报卡号
     */
    public static String gen_set(String freq, String IC) {

        String cmd = "BDHZ,SET,1," + freq + "," + IC + ",0";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }


    /**
     * 检测是否为包含$BDOKX头部 （二代模块协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_BDOKX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x4F && buf[4] == 0x4B && buf[5] == 0x58) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDZZX头部 （二代模块协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_BDZZX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x5A && buf[4] == 0x5A && buf[5] == 0x58) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDHMX头部 （二代模块协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_BDHMX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x48 && buf[4] == 0x4D && buf[5] == 0x58) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDZDX头部 （二代模块协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_BDZDX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x5A && buf[4] == 0x44 && buf[5] == 0x58) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDPWX头部 （二代模块协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_BDPWX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x50 && buf[4] == 0x57 && buf[5] == 0x58) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean isHead_BDMSH(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x4D && buf[4] == 0x53 && buf[5] == 0x48) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean isHead_BDVRX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x56 && buf[4] == 0x52 && buf[5] == 0x58) {
                flag = true;
            }
        }
        return flag;
    }


    public static boolean isHead_BDQDX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x51 && buf[4] == 0x44 && buf[5] == 0x58) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean isHead_BDQZX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x51 && buf[4] == 0x5A && buf[5] == 0x58) {
                flag = true;
            }
        }
        return flag;
    }


    public static boolean isHead_BDQKX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 51 && buf[4] == 0x4B && buf[5] == 0x58) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDHZ头部 （北斗盒子自定协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_BDHZ(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x48 && buf[4] == 0x5A) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 组装打开/关闭SOS功能命令(自定义命令)
     *
     * @param IC
     */
    public static String gen_opnsos(String IC, boolean ifOpen, String sosContent) {

        String cmd = "BDHZ,SOS,1," + (ifOpen ? 1 : 0) + ",60," + IC + "," + sosContent;
        byte[] tmp = new byte[0];
        try {
            tmp = cmd.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    private static String packPosition(RMCMsg pos) {
        String lng;
        String lat;
        String height;

        if (pos.getDWstaus() == true) {
            lng = BDMethod.castLongToHexStringByNum(new Double(pos.getLongitude() * 1000000).longValue(), 8);
            lat = BDMethod.castLongToHexStringByNum(new Double(pos.getLatitude() * 1000000).longValue(), 8);
            height = BDMethod.castLongToHexStringByNum(new Float(pos.getHeight()).intValue(), 4);
        } else {
            lng = "FFFFFFFF";
            lat = "FFFFFFFF";
            height = "FFFF";
        }

        return lng + lat + height;
    }

}
