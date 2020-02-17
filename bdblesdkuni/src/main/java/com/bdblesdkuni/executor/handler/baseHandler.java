package com.bdblesdkuni.executor.handler;


import com.bdblesdkuni.impl.BDBLEListener;
import com.bddomainuni.models.entity.protocal2_1.BSIMsg;
import com.bddomainuni.models.entity.protocal2_1.DWRMsg;
import com.bddomainuni.models.entity.protocal2_1.FKIMsg;
import com.bddomainuni.models.entity.protocal2_1.GGAMsg;
import com.bddomainuni.models.entity.protocal2_1.ICIMsg;
import com.bddomainuni.models.entity.protocal2_1.RMCMsg;
import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.models.entity.protocal2_1.WAAMsg;
import com.bddomainuni.models.entity.protocal4_0.DWXXMsg;
import com.bddomainuni.models.entity.protocal4_0.FKXXMsg;
import com.bddomainuni.models.entity.protocal4_0.GLZKMsg;
import com.bddomainuni.models.entity.protocal4_0.ICXXMsg;
import com.bddomainuni.models.entity.protocal4_0.SJXXMsg;
import com.bddomainuni.models.entity.protocal4_0.TXXXMsg;
import com.bddomainuni.models.entity.protocal4_0.ZJXXMsg;
import com.bddomainuni.models.entity.protocalBDHZ.BDHZMsg;
import com.bddomainuni.models.entity.protocalBDHZ.BDMSH;
import com.bddomainuni.models.entity.protocalBDHZ.BDQDX;
import com.bddomainuni.models.entity.protocalBDHZ.BDQKX;
import com.bddomainuni.models.entity.protocalBDHZ.BDQZX;
import com.bddomainuni.repository.protcals.protocal2_1;
import com.bddomainuni.repository.protcals.protocal4_0;
import com.bddomainuni.repository.protcals.protocalEntity;
import com.bddomainuni.repository.protcals.protocal_BDHZ;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Handler基类
 *
 * Created by admin on 2016/10/21.
 * Modified by admin on 2017/5/13
 */
public abstract class baseHandler {

    public static final int TYPE_PROTOCAL_21 = 1; //2.1协议
    public static final int TYPE_PROTOCAL_40 = 2; //4.0协议

    /**
     * 信息类别
     */
    protected static class CommType {
        public static final int COMMON_MODE = 1; //普通通信
        public static final int FAST_MODE = 2;  //特快通信
        public static final int BROADCAST_MODE = 3; //通播通信
        public static final int MSG_QUERY_MODE = 4; //按最新存入电文查询获得的通信
        public static final int ADDRESS_QUERY_MODE = 5; //按发信方地址查询获得的通信
    }

    /**
     * 电文形式
     */
    protected static class MsgType {
        public static final int CHINESE = 0; //汉字
        public static final int CODE = 1; //代码
        public static final int MIX = 2; // 混发

    }

    /**
     * 高程类型指示
     */
    public static class HeightFlag {
        public static final String HIGHT_USER = "H"; //高空
        public static final String COMMON_USER = "L"; // 普通
    }

    /**
     * 测高方式
     */
    public static class CheckHighType {
        public static final int HAVE_HIGH_VALUE = 0; //有高程
        public static final int HAVE_NO_HIGH_VALUE = 1; //无高程
        public static final int HAVE_CHECK_HIGH_1 = 2; // 测高1
        public static final int HAVE_CHECK_HIGH_2 = 3; // 测高2
    }

    /**
     * 定位类型
     */
    public static class LocationType {
        public static final String EMERGENCY_LOCATION = "A"; //紧急定位
        public static final String COMMON_LOCATION = "V"; // 普通定位
    }

    protected static class RmoType {
        public static final int CLOSE = 1; //关闭指定语句
        public static final int OPEN = 2; // 打开指定语句
        public static final int CLOSE_ALL = 3; //关闭全部语句
        public static final int OPEN_ALL = 4; // 打开全部语句
        //若模式为3、4时，目标语句数据保留为空。
    }

    protected BDBLEListener bdbleListener;

    /**
     * 构造函数
     *
     * @param paramBDBLEListener
     *         传入监听
     */
    protected baseHandler(BDBLEListener paramBDBLEListener) {
        this.bdbleListener = paramBDBLEListener;
    }

    public void onConnectBleSuccess() {
        this.bdbleListener.onConnectBleSuccess();
    }

    public void onDisConnectBleSuccess() {
        this.bdbleListener.onDisConnectBleSuccess();
    }

    /**
     * 将发送的字节内容写入发送缓存中并通过蓝牙发送
     *
     * @param paramArrayOfByte
     */
    public abstract void send(byte[] paramArrayOfByte);


    /**
     * 发送功率检测申请
     */
    protected void sendCCRMO() {
        send((protocal2_1.gen_ccrmo(RmoType.OPEN, 1)).getBytes());
    }

    /**
     * 发送功率检测申请
     *
     * @param state
     *         打开为2, 关闭为1
     * @param freq
     *         单位:秒
     */
    protected void sendCCRMO(int state, int freq) {
        send((protocal2_1.gen_ccrmo(state, freq)).getBytes());
    }

    /**
     * 发送IC检测申请
     */
    protected void sendCCICA() {
        send((protocal2_1.gen_ccica(0, 0)).getBytes());
    }

    /**
     * 发送IC检测申请
     *
     * @param CardSlotNum
     *         卡槽号
     */
    protected void sendCCICA(int CardSlotNum) {
        send((protocal2_1.gen_ccica(0, CardSlotNum)).getBytes());
    }

    /**
     * 发送通信申请
     *
     * @param revIC
     *         接收通信信息的目的卡号
     */
    protected void sendCCTXA(String revIC) throws UnsupportedEncodingException {
        send((protocal2_1.gen_cctxa(revIC, CommType.COMMON_MODE, MsgType.MIX, "123你好这是测试")).getBytes());

    }


    /**
     * 发送通信申请
     *
     * @param revIC
     *         接收通信信息的目的卡号
     * @param CommType
     *         通信类别 0特快  1 普通
     * @param MsgType
     *         传输方式 0汉字  1 代码  2 混发
     * @param MsgContent
     *         传输的内容
     *
     * @throws UnsupportedEncodingException
     */
    protected void sendCCTXA(String revIC, int CommType, int MsgType, String MsgContent) throws UnsupportedEncodingException {
        send((protocal2_1.gen_cctxa(revIC, CommType, MsgType, MsgContent)).getBytes("gbk"));

    }

    /**
     * 发送定位申请
     *
     * @param revIC
     *         接收定位信息卡号
     */
    protected void sendCCDWA(String revIC) {
        send((protocal2_1.gen_ccdwa(revIC, LocationType.COMMON_LOCATION, CheckHighType.HAVE_NO_HIGH_VALUE,
                HeightFlag.COMMON_USER, "0", 0, 0, 0, 0))
                .getBytes());
    }

    /**
     * 发送定位申请
     *
     * @param ic
     *         卡号
     * @param LocationType
     *         定位类型 A:紧急定位   V:普通定位
     * @param CheckHighType
     *         测高方式 0:有高程；1:无高程；2:测高1；3:测高2
     * @param HeightFlag
     *         高程指示 H:高空；L:普通
     * @param Altitude
     *         高程数据
     * @param AntennaHigh
     *         天线高
     * @param PressValue
     *         气压数据
     * @param TemperValue
     *         温度值
     * @param AppliFreq
     *         申请频度
     */
    protected void sendCCDWA(String ic, String LocationType, int CheckHighType, String HeightFlag, String Altitude,
                             double AntennaHigh, double PressValue, double TemperValue, int AppliFreq) {
        send((protocal2_1.gen_ccdwa(ic, LocationType, CheckHighType, HeightFlag, Altitude, AntennaHigh, PressValue, TemperValue, AppliFreq))
                .getBytes());
    }

    /**
     * 发送关闭二代数据命令
     */
    protected void sendCLOGPS() {
        send(protocal2_1.gen_clogps().getBytes());
    }

    /**
     * 发送开启二代数据命令
     */
    protected void sendOPNGPS() {
        send(protocal2_1.gen_opngps().getBytes());
    }

    /**
     * 发送开启/关闭SOS功能命令
     *
     * @param IC
     *         SOS上报卡号
     * @param ifOpen
     * @param sosContent
     */
    protected void sendSOS(String IC, boolean ifOpen, String sosContent) {
        try {
            send(protocal_BDHZ.gen_opnsos(IC, ifOpen, sosContent).getBytes("gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送盒子电量检测命令
     */
    protected void sendDLJC() {
        send(protocal_BDHZ.gen_dljc().getBytes());
    }

    /**
     * 发送初始化命令
     */
    protected void sendINIT() {
        send(protocal_BDHZ.gen_ztjc().getBytes());
    }


    /**
     * 发送设置功能命令
     *
     * @param IC
     *         SOS上报卡号
     * @param freq
     *         频度
     */
    protected void sendSET(String freq, String IC) {
        send(protocal_BDHZ.gen_set(freq, IC).getBytes());
    }


    /**
     * 发送位置报告申请
     *
     * @param IC
     *         接收位置报告用户IC卡号
     * @param atitudeInd
     *         高程指示 H-高空用户 L-普通用户
     * @param AntennaHigh
     *         天线高
     * @param freq
     *         发送位置报告频度
     */
    protected void sendWBA(String IC, String atitudeInd, String AntennaHigh, String freq) {
        send(protocal2_1.gen_ccwba(IC, atitudeInd, AntennaHigh, freq).getBytes());
    }

    /**
     * 发送位置报告1申请
     * 发送给自己的报告，ic填写本设备
     *
     * @param ic
     *         本设备卡号
     * @param time
     *         时间
     * @param lat
     *         纬度
     * @param latDir
     *         纬度方向
     * @param lng
     *         经度
     * @param lngDir
     *         经度方向
     * @param high
     *         高程值
     * @param highUnit
     *         高程单位
     */
    protected void sendWAA(String ic, String time, String lat, String latDir,
                           String lng, String lngDir, String high, String highUnit) {
        send(protocal2_1.gen_ccwaa(ic, "0", "", time, lat, latDir, lng, lngDir, high, highUnit).getBytes());
    }

    /**
     * 发送位置报告1申请
     * 按照设置频度发送给设置卡号
     *
     * @param ic
     *         本设备卡号
     * @param freq
     *         报告频度
     * @param time
     *         时间
     * @param lat
     *         纬度
     * @param latDir
     *         纬度方向
     * @param lng
     *         经度
     * @param lngDir
     *         经度方向
     * @param high
     *         高程值
     * @param highUnit
     *         高程单位
     */
    protected void sendWAA(String ic, String freq, String time, String lat, String latDir,
                           String lng, String lngDir, String high, String highUnit) {
        send(protocal2_1.gen_ccwaa(ic, "1", freq, time, lat, latDir, lng, lngDir, high, highUnit).getBytes());
    }

    protected void sendGLJC(int freq) {
        send(protocal4_0.gen_gljc(freq));
    }

    protected void sendGLJC(int ic, int freq) {
        send(protocal4_0.gen_gljc(ic, freq));
    }

    protected void sendICJC(int frameNum) {
        send(protocal4_0.gen_icjc(frameNum));
    }

    protected void sendICJC(int ic, int frameNum) {
        send(protocal4_0.gen_icjc(ic, frameNum));
    }

    protected void sendXTZJ(int freq) {
        send(protocal4_0.gen_xtzj(freq));
    }

    protected void sendXTZJ(int ic, int freq) {
        send(protocal4_0.gen_xtzj(ic, freq));
    }

    protected void sendSJSC(int freq) {
        send(protocal4_0.gen_sjsc(freq));
    }

    protected void sendSJSC(int ic, int freq) {
        send(protocal4_0.gen_sjsc(ic, freq));
    }

    protected void sendBBDQ() {
        send(protocal4_0.gen_bbdq());
    }

    protected void sendBBDQ(int ic) {
        send(protocal4_0.gen_bbdq(ic));
    }

    protected void sendCKSC(byte baudRate) {
        send(protocal4_0.gen_cksc(baudRate));
    }

    protected void sendCKSC(int ic, byte baudRate) {
        send(protocal4_0.gen_cksc(ic, baudRate));
    }

    protected void sendDWSQ() {
        send(protocal4_0.gen_dwsq());
    }

    protected void sendTXSQ(int revIc, int msgType, String msg) {
        send(protocal4_0.gen_txsq(revIc, msgType, msg));
    }

    /**
     * 终端密码设置/登录(CCPWD)
     *
     * @param type 类型
     *             ‘1’:密码设置
     *             ‘2’:登录
     *
     * @param pwd  密码，六位数字或者英文字母
     */
    protected void sendCCPWD(String type, String pwd) {
        send(protocal_BDHZ.gen_ccpwd(type,pwd).getBytes());
    }

    /**
     * 终端自检输出设置(CCZDC)
     *
     * @param freq  输出频度  ‘0’-‘10’ 单位：秒，为‘0’时表示单次输出
     */
    protected void sendCCZDC(String freq) {
        send(protocal_BDHZ.gen_cczdc(freq).getBytes());
    }

    /**
     * OK键设置和查询（CCOKS）
     *
     */
    protected void sendCCOKS(protocalEntity.CCOKSobj obj) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_ccoks(obj).getBytes("gb18030"));
    }

    /**
     * 启动/关闭SOS救援（CCQJY）
     *
     */
    protected void sendCCQJY(boolean ifOpen){
        send(protocal_BDHZ.gen_ccqjy(ifOpen).getBytes());
    }

    /**
     * 启动/关闭极限追踪(CCQZZ)
     *
     */
    protected void sendCCQZZ(boolean ifOpen){
        send(protocal_BDHZ.gen_ccqzz(ifOpen).getBytes());
    }

    /**
     * 启动/关闭OK模式(CCQOK)
     *
     */
    protected void sendCCQOK(boolean ifOpen){
        send(protocal_BDHZ.gen_ccqok(ifOpen).getBytes());
    }

    /**
     * 极限追踪模式设置/查询(CCZZM)
     *
     */
    protected void sendCCZZM(protocalEntity.CCZZMobj obj){
        send(protocal_BDHZ.gen_cczzm(obj).getBytes());
    }

    /**
     * SOS参数设置/查询(CCSHM)
     *
     */
    protected void sendCCSHM(protocalEntity.CCSHMobj obj) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_ccshm(obj).getBytes("gb18030"));
    }


    /**
     * 查询系统当前工作模式(CCMSC)
     * @return
     */
    protected void sendCCMSC() {
        //传入CCMSC对应的参数到send（）函数中
        send(protocal_BDHZ.gen_ccmsc().getBytes());
    }

    /**
     * 版本查询（CCVRQ）
     * @return
     */
    protected void sendCCVRQ() {
        send(protocal_BDHZ.gen_ccvrq().getBytes());
    }

    /**
     * 北斗网到北斗网通信
     *
     * @param centerIc
     * @param msg
     *
     * @throws UnsupportedEncodingException
     */
    protected void sendBD2BD(String centerIc,String msg) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_txa_bd2bd(centerIc,msg).getBytes("gb18030"));
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
    protected void sendBD2BD_POS(String centerIc,String msg,RMCMsg pos) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_txa_bd2bd_bypos(centerIc,msg,pos).getBytes("gb18030"));
    }

    /**
     * 北斗网到公网
     *
     */
    protected void sendBD2NET(String centerIc,String userPhone,String msg) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_txa_bd2net(centerIc,Long.parseLong(userPhone),msg).getBytes("gb18030"));
    }

    /**
     * 北斗网到手机短信(带位置)
     *
     */
    protected void sendBD2PHONE_POS(String centerIc,String userPhone,String msg,RMCMsg pos) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_txa_bd2phone_bypos(centerIc,Long.parseLong(userPhone),msg,pos).getBytes("gb18030"));
    }

    /**
     * 北斗网到短信
     *
     */
    protected void sendBD2PHONE(String centerIc,String userPhone,String msg) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_txa_bd2phone(centerIc,Long.parseLong(userPhone),msg).getBytes("gb18030"));
    }

    /**
     * 北斗网到公网(带位置)
     *
     */
    protected void sendBD2NET_POS(String centerIc,String userPhone,String msg,RMCMsg pos) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_txa_bd2net_bypos(centerIc,Long.parseLong(userPhone),msg,pos).getBytes("gb18030"));
    }

    /**
     * 北斗网登录
     *
     */
    protected void sendBDLOGIN(String centerIc,String loginType,String phone,String pw) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_txa_bd_login(centerIc,loginType,Long.parseLong(phone),pw).getBytes("gb18030"));
    }

    /**
     * 北斗网app紧急报警
     *
     * @param centerIc
     * @param phone
     * @param status
     * @param msg
     * @param pos
     *
     * @throws UnsupportedEncodingException
     */
    protected void sendAppSos(String centerIc, String phone, String status,String msg, RMCMsg pos) throws UnsupportedEncodingException {
        if(phone == "" || phone == null){
            phone ="0";
        }
        send(protocal_BDHZ.gen_txa_bd_sos(centerIc,Long.parseLong(phone),status,msg,pos).getBytes("gb18030"));
    }

    /**
     * 北斗网app位置上报
     *
     */
    protected void sendAppPos(String centerIc, List<RMCMsg> rmcMsgs){
        send(protocal_BDHZ.gen_txa_bd_zzm(centerIc,rmcMsgs).getBytes());
    }

    /**
     * 北斗网app签到
     *
     * @param centerIc
     * @param msg
     * @param pos
     *
     * @throws UnsupportedEncodingException
     */
    protected void sendAppOk(String centerIc, String msg, RMCMsg pos) throws UnsupportedEncodingException {
        send(protocal_BDHZ.gen_txa_bd_ok(centerIc,msg,pos).getBytes("gb18030"));
    }

    public byte[] onMessageReceivered(byte[] paramByte, int typeProtocal) {
        if (typeProtocal == TYPE_PROTOCAL_21) {
            if (protocal2_1.isBDHead_BDICI(paramByte)) {
                ICIMsg iciMsg = new ICIMsg(paramByte);
                if (iciMsg.getVaild()) {
                    this.bdbleListener.onBDICIReceived(iciMsg);
                }
            }else if (protocal_BDHZ.isHead_BDQDX(paramByte)) {
                BDQDX msg = new BDQDX(paramByte);
                if (msg.getVaild()) {
                    this.bdbleListener.onBDQDXReceived(msg);
                }
            }else if (protocal_BDHZ.isHead_BDQZX(paramByte)) {
                BDQZX msg = new BDQZX(paramByte);
                if (msg.getVaild()) {
                    this.bdbleListener.onBDQZXReceived(msg);
                }
            }else if (protocal_BDHZ.isHead_BDMSH(paramByte)) {
                BDMSH msg = new BDMSH(paramByte);
                if (msg.getVaild()) {
                    this.bdbleListener.onBDMSHReceived(msg);
                }
            }
            else if (protocal_BDHZ.isHead_BDQKX(paramByte)) {
                BDQKX msg = new BDQKX(paramByte);
                if (msg.getVaild()) {
                    this.bdbleListener.onBDQKXReceived(msg);
                }
            } else if (protocal_BDHZ.isHead_BDOKX(paramByte)) {
                protocalEntity.CCOKSobj msg = new protocalEntity.CCOKSobj(paramByte);
                if (msg.getVaild()) {
                    this.bdbleListener.onBDOKXReceived(msg);
                }
            } else if (protocal_BDHZ.isHead_BDZZX(paramByte)) {
                protocalEntity.CCZZMobj msg = new protocalEntity.CCZZMobj(paramByte);
                if (msg.getVaild()) {
                    this.bdbleListener.onBDZZXReceived(msg);
                }
            }else if (protocal_BDHZ.isHead_BDHMX(paramByte)) {
                protocalEntity.CCSHMobj msg = new protocalEntity.CCSHMobj(paramByte);
                if (msg.getVaild()) {
                    this.bdbleListener.onBDHMXReceived(msg);
                }
            }else if (protocal_BDHZ.isHead_BDZDX(paramByte)) {
                protocalEntity.BDZDXobj msg = new protocalEntity.BDZDXobj(paramByte);
                if (msg.getVaild()) {
                    this.bdbleListener.onBDZDXReceived(msg);
                }
            }else if (protocal_BDHZ.isHead_BDPWX(paramByte)) {
                protocalEntity.BDPWXobj msg = new protocalEntity.BDPWXobj(paramByte);
                if (msg.getVaild()) {
                    this.bdbleListener.onBDPWXReceived(msg);
                }
            }else if (protocal2_1.isBDHead_BDBSI(paramByte)) {
                BSIMsg bsiMsg = new BSIMsg(paramByte);
                if (bsiMsg.getVaild()) {
                    this.bdbleListener.onBDBSIReceived(bsiMsg);
                }
            } else if (protocal2_1.isBDHead_BDTXR(paramByte)) {
                TXRMsg txrMsg = new TXRMsg(paramByte);
                if (txrMsg.getVaild()) {
                    this.bdbleListener.onBDTXRReceived(txrMsg);
                } else {
                    //todo 接收校验错误处理
                }
            } else if (protocal2_1.isBDHead_BDFKI(paramByte)) {
                FKIMsg fkiMsg = new FKIMsg(paramByte);
                if (fkiMsg.getVaild()) {
                    this.bdbleListener.onBDFKIReceived(fkiMsg);
                }
            } else if (protocal2_1.isHead_RMC(paramByte)) {
                RMCMsg rmcMsg = new RMCMsg(paramByte);
                if (rmcMsg.getVaild()) {
                    this.bdbleListener.onRMCReceived(rmcMsg);
                }
            } else if (protocal2_1.isBDHead_BDDWR(paramByte)) {
                DWRMsg dwrMsg = new DWRMsg(paramByte);
                if (dwrMsg.getVaild()) {
                    this.bdbleListener.onBDDWRReceived(dwrMsg);
                }
            } else if (protocal_BDHZ.isHead_BDHZ(paramByte)) {
                BDHZMsg bdhzMsg = new BDHZMsg(paramByte);
                if (bdhzMsg.getVaild()) {
                    this.bdbleListener.onBDHZReceived(bdhzMsg);
                }
            } else if (protocal2_1.isHead_WAA(paramByte)) {
                WAAMsg waaMsg = new WAAMsg(paramByte);
                if (waaMsg.getVaild()) {
                    this.bdbleListener.onBDWAAeceived(waaMsg);
                }
            }else if (protocal2_1.isHead_GNGGA(paramByte)) {
                GGAMsg ggaMsg = new GGAMsg(paramByte);
                if (ggaMsg.getVaild()) {
                    this.bdbleListener.onGGAReceived(ggaMsg);
                }
            }
        } else if (typeProtocal == TYPE_PROTOCAL_40) {

            if (protocal4_0.isHead_GLZK(paramByte)) {
                GLZKMsg glzkMsg = new GLZKMsg(paramByte);
                if (glzkMsg.isVaild()) {
                    this.bdbleListener.onGLZKReceived(glzkMsg);
                }
            } else if (protocal4_0.isHead_DWXX(paramByte)) {
                DWXXMsg dwxxMsg = new DWXXMsg(paramByte);
                if (dwxxMsg.isVaild()) {
                    this.bdbleListener.onDWXXReceived(dwxxMsg);
                }
            } else if (protocal4_0.isHead_ICXX(paramByte)) {
                ICXXMsg icxxMsg = new ICXXMsg(paramByte);
                if (icxxMsg.isVaild()) {
                    this.bdbleListener.onICXXReceived(icxxMsg);
                }
            } else if (protocal4_0.isHead_ZJXX(paramByte)) {
                ZJXXMsg zjxxMsg = new ZJXXMsg(paramByte);
                if (zjxxMsg.isVaild()) {
                    this.bdbleListener.onZJXXReceived(zjxxMsg);
                }
            } else if (protocal4_0.isHead_SJXX(paramByte)) {
                SJXXMsg sjxxMsg = new SJXXMsg(paramByte);
                if (sjxxMsg.isVaild()) {
                    this.bdbleListener.onSJXXReceived(sjxxMsg);
                }
            } else if (protocal4_0.isHead_FKXX(paramByte)) {
                FKXXMsg fkxxMsg = new FKXXMsg(paramByte);
                if (fkxxMsg.isVaild()) {
                    this.bdbleListener.onFKXXReceived(fkxxMsg);
                }
            } else if (protocal4_0.isHead_TXXX(paramByte)) {
                TXXXMsg txxxMsg = new TXXXMsg(paramByte);
                if (txxxMsg.isVaild()) {
                    this.bdbleListener.onTXXXReceived(txxxMsg);
                    //Log.v("FDBDTestLog", "校验成功");
                } else {
                    //Log.v("FDBDTestLog", "校验失败");
                }
            }
        }
        return paramByte;
    }

}
