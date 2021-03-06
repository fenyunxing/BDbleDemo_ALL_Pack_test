package com.bdblesdkuni.executor.handler;

import android.content.Context;
import android.util.Log;

import com.bdblesdkuni.impl.AgentListener;
import com.bdblesdkuni.impl.BDBLEListener;
import com.bddomainuni.models.entity.protocal2_1.BSIMsg;
import com.bddomainuni.models.entity.protocal2_1.DWRMsg;
import com.bddomainuni.models.entity.protocal2_1.FKIMsg;
import com.bddomainuni.models.entity.protocal2_1.GGAMsg;
import com.bddomainuni.models.entity.protocal2_1.ICIMsg;
import com.bddomainuni.models.entity.protocal2_1.RMCMsg;
import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.models.entity.protocal2_1.WAAMsg;
import com.bddomainuni.models.entity.protocal4_0.BBXXMsg;
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
import com.bddomainuni.repository.protcals.protocalEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 终端蓝牙操作类 可进行蓝牙连接、断开、发送符合协议的命令、以及接收符合协议的数据，并解析保存入相应对象，方便后续获取使用。
 *
 * Created by admin on 2016/10/21 Modified by admin on 2017/05/23
 */
public class BLEManager implements BDBLEListener {
    public static BDBLEHandler bdbleHandler = null;
    private static BLEManager bleManager;
    public static List<AgentListener> agentListeners = new ArrayList();
    public static String address;
    public static String deviceName;
    public static boolean ConnectState = false;

    /**
     * 获得BLEManager单例
     *
     * @return 返回单例
     */
    public static BLEManager getInstance() {
        if (bleManager == null)
            bleManager = new BLEManager();
        return bleManager;
    }

    /**
     * 连接蓝牙
     *
     * @param context
     *         传入上下文（一般传入APP全局上下文）
     */
//    public boolean connectBle(Context context) {
//
//        if (bdbleHandler == null) {
//            bdbleHandler = new BDBLEHandler(context, bleManager);
//        }
//        if (bdbleHandler.initialize()) {
//            boolean tempboolean;
//            if (address != null || !address.equals(" ")) {
//                tempboolean = bdbleHandler.connectDevice(this.address);
//            } else {
//                tempboolean = false;
//                Log.e("BDError", "ERROR ==> Connect without any address!");
//            }
//            return tempboolean;
//        }
//        return false;
//    }

    /**
     * 连接蓝牙
     *
     * @param context
     *         传入上下文（一般传入APP全局上下文）
     * @param UUID_RX
     *         自定义接收通道UUID
     * @param UUID_TX
     *         自定义发送通道UUID
     */
//    public boolean connectBle(Context context, String UUID_RX, String UUID_TX) {
//
//        if (bdbleHandler == null) {
//            bdbleHandler = new BDBLEHandler(context, bleManager, UUID_RX, UUID_TX);
//        }
//        if (bdbleHandler.initialize()) {
//            boolean tempboolean;
//            if (address != null || !address.equals(" ")) {
//                tempboolean = bdbleHandler.connectDevice(this.address);
//            } else {
//                tempboolean = false;
//                Log.e("BDError", "ERROR ==> Connect without any address!");
//            }
//            return tempboolean;
//        }
//        return false;
//    }


    public void connectBle(Context context) {

        if (bdbleHandler == null) {
            bdbleHandler = new BDBLEHandler(context, bleManager);
        }
        if (bdbleHandler.initialize()) {
            if (address != null || !address.equals(" ")) {
                bdbleHandler.connectDevice(this.address);
            } else {
                Log.e("BDError", "ERROR ==> Connect without any address!");
            }
            return;
        }
        return;
    }

    /**
     * 断开当前蓝牙连接
     */
    public void disConnectBle() {
        if (bdbleHandler != null) {
            bdbleHandler.disconnectDevice();
            bdbleHandler = null;
        }
    }

    /**
     * 设置连接设备的物理地址
     *
     * @param mdeviceAddr
     *         蓝牙连接设备物理地址
     */
    public BLEManager setDeviceAddress(String mdeviceAddr) {
        address = mdeviceAddr;
        return this;
    }

    /**
     * 设置连接设备的名称
     *
     * @param mdeviceName
     *         蓝牙连接设备名称
     */
    public BLEManager setDeviceName(String mdeviceName) {
        deviceName = mdeviceName;
        return this;
    }

    /**
     * 获得当前连接设备名称
     *
     * @return 当前连接设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 发送功率检测申请（2.1） 默认发送:$CCRMO,BSI,2,1*27
     */
    public void sendCCRMO() {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCRMO();
        }
    }

    /**
     * 发送功率检测申请（2.1）
     *
     * @param state
     *         打开为2, 关闭为1
     * @param freq
     *         单位:秒
     */
    public void sendCCRMO(int state, int freq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCRMO(state, freq);
        }

    }


    /**
     * 发送IC检测申请（2.1） 默认发送:$CCICA,0,0*4B
     */
    public void sendCCICA() {

        if (bdbleHandler != null) {
            bdbleHandler.sendCCICA();
        }
    }

    /**
     * 发送IC检测申请（2.1）
     *
     * @param CardSlotNum
     *         卡槽号
     */
    public void sendCCICA(int CardSlotNum) {

        if (bdbleHandler != null) {
            bdbleHandler.sendCCICA(CardSlotNum);
        }
    }


    /**
     * 发送通信申请（2.1）
     *
     * @param revIC
     *         接收卡号 默认发送 $CCTXA,0000000,1,2,A4313233C4E3BAC3D5E2CAC7B2E2CAD4*7F 内容 "123你好这是测试"
     */
    public void sendCCTXA(String revIC) {
        try {
            if (bdbleHandler != null) {
                bdbleHandler.sendCCTXA(revIC);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送通信申请（2.1）
     *
     * @param revIC
     *         接收卡号
     * @param CommType
     *         通信类别 0特快  1 普通
     * @param MsgType
     *         传输方式 0汉字  1 代码  2 混发
     * @param MsgContent
     *         传输的内容
     */
    public void sendCCTXA(String revIC, int CommType, int MsgType, String MsgContent) {
        try {
            if (bdbleHandler != null) {
                bdbleHandler.sendCCTXA(revIC, CommType, MsgType, MsgContent);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    /**
     * 发送定位申请（2.1） 默认发送:$CCDWA,2097151,V,1,L,0,0.0,0.0,0.0,0*42
     */
    public void sendCCDWA(String revIC) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCDWA(revIC);
        }
    }

    /**
     * 发送定位申请（2.1）
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
    public void sendCCDWA(String ic, String LocationType, int CheckHighType, String HeightFlag, String Altitude,
                          double AntennaHigh, double PressValue, double TemperValue, int AppliFreq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCDWA(ic, LocationType, CheckHighType, HeightFlag, Altitude, AntennaHigh, PressValue, TemperValue, AppliFreq);
        }
    }

    /**
     * 关闭二代数据（2.1）
     */
    public void sendCLOGPS() {
        if (bdbleHandler != null) {
            bdbleHandler.sendCLOGPS();
        }
    }

    /**
     * 打开二代数据（2.1）
     */
    public void sendOPNGPS() {
        if (bdbleHandler != null) {
            bdbleHandler.sendOPNGPS();
        }
    }

    /**
     * 查询系统当前工作模式(CCMSC)
     * @return
     */
    public void sendCCMSC() {

        if (bdbleHandler != null) {
            //调用bdbleHandler对象父类的sendCCMSC()方法
            bdbleHandler.sendCCMSC();
        }
    }

    /**
     * 版本查询（CCVRQ）
     * @return
     */
    public void sendCCVRQ() {

        if (bdbleHandler != null) {
            bdbleHandler.sendCCVRQ();
        }
    }


    /**
     * 启动/关闭紧急求救
     *
     * @param ifOpen
     */
    public void sendCCQJY(boolean ifOpen) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCQJY(ifOpen);
        }
    }

    /**
     * 启动/关闭极限追踪
     *
     * @param ifOpen
     */
    public void sendCCQZZ(boolean ifOpen) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCQZZ(ifOpen);
        }
    }

    /**
     * 启动/关闭OK
     *
     * @param ifOpen
     */
    public void sendCCQOK(boolean ifOpen) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCQOK(ifOpen);
        }
    }

    /**
     * 开启与关闭SOS功能
     *
     * @param IC
     *         SOS上报卡号
     * @param ifOpen
     *         SOS控制状态，true表示开启，false表示关闭
     * @param sosContent
     *         SOS求救具体内容（最长20个汉字）
     *
     *         例：IC为0000000，发送内容为救命，状态为开启sos 则发送内容“$BDHZ,SOS,1,1,60,0000000,救命*24”。
     */
    public void sendSOS(String IC, boolean ifOpen, String sosContent) {
        if (bdbleHandler != null) {
            bdbleHandler.sendSOS(IC, ifOpen, sosContent);
        }
    }

    /**
     * 设置功能
     *
     * @param freq
     *         频度
     * @param IC
     *         SOS上报卡号
     *
     *         例：“$BDHZ,SET,1,60,0000001,0*4C”
     */

    public void sendSET(String freq, String IC) {
        if (bdbleHandler != null) {
            bdbleHandler.sendSET(freq, IC);
        }
    }

    /**
     * 开启电量检测功能
     *
     * 例：“$BDHZ,DLJC,1*24”
     */
    public void sendDLJC() {
        if (bdbleHandler != null) {
            bdbleHandler.sendDLJC();
        }
    }

    /**
     * 开启电量检测功能
     *
     * 例：“$BDHZ,ZTJC,1*22”
     */
    public void sendINIT() {
        if (bdbleHandler != null) {
            bdbleHandler.sendINIT();
        }
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
    public void sendWBA(String IC, String atitudeInd, String AntennaHigh, String freq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendWBA(IC, atitudeInd, AntennaHigh, freq);
        }
    }

    /**
     * 发送位置报告1申请 发送给自己的报告，ic填写本设备
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
    public void sendWAA(String ic, String time, String lat, String latDir,
                        String lng, String lngDir, String high, String highUnit) {
        if (bdbleHandler != null) {
            bdbleHandler.sendWAA(ic, time, lat, latDir, lng, lngDir, high, highUnit);
        }
    }

    /**
     * 发送位置报告1申请 按照设置频度发送给设置卡号
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
    public void sendWAA(String ic, String freq, String time, String lat, String latDir,
                        String lng, String lngDir, String high, String highUnit) {
        if (bdbleHandler != null) {
            bdbleHandler.sendWAA(ic, freq, time, lat, latDir, lng, lngDir, high, highUnit);
        }
    }

    /**
     * 发送功率检测指令（4.0）
     *
     * @param freq
     *         功率信息输出频度
     *
     *         例："0x24 0x47 0x4C 0x4A 0x43 0x00 0x0C 0x00 0x00 0x00 0x01 0x2B"
     */
    public void sendGLJC(int freq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendGLJC(freq);
        }
    }

/*    public void sendGLJC(int ic, int freq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendGLJC(ic, freq);
        }
    }*/

    /**
     * 发送IC检测指令（4.0）
     *
     * @param frameNum
     *         帧号
     *
     *         例："0x24 0x49 0x43 0x4A 0x43 0x00 0x0C 0x00 0x00 0x00 0x01 0x2A"
     */
    public void sendICJC(int frameNum) {
        if (bdbleHandler != null) {
            bdbleHandler.sendICJC(frameNum);
        }
    }

/*    public void sendICJC(int ic, int frameNum) {
        if (bdbleHandler != null) {
            bdbleHandler.sendICJC(ic, frameNum);
        }
    }*/

    /**
     * 发送系统自检指令
     *
     * @param freq
     *         自检频度
     *
     *         例"0x24 0x58 0x54 0x5A 0x4A 0x00 0x0D 0x00 0x00 0x00 0x00 0x00 0x35 "
     */
    public void sendXTZJ(int freq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendXTZJ(freq);
        }
    }

/*    public void sendXTZJ(int ic, int freq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendXTZJ(ic, freq);
        }
    }*/

    /**
     * 发送时间输出指令（4.0）
     *
     * @param freq
     *         输出频度
     *
     *         例"0x24 0x53 0x4A 0x53 0x43 0x00 0x0D 0x00 0x00 0x00 0x00 0x05 0x25"
     */
    public void sendSJSC(int freq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendSJSC(freq);
        }
    }

/*    public void sendSJSC(int ic, int freq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendSJSC(ic, freq);
        }
    }*/

    /**
     * 发送版本读取指令（4.0）
     *
     * 例"0x24 0x42 0x42 0x44 0x51 0x00 0x0B 0x00 0x00 0x00 0x3A"
     */
    public void sendBBDQ() {
        if (bdbleHandler != null) {
            bdbleHandler.sendBBDQ();
        }
    }

/*    public void sendBBDQ(int ic) {
        if (bdbleHandler != null) {
            bdbleHandler.sendBBDQ(ic);
        }
    }*/

    /**
     * 发送串口输出指令（4.0）
     *
     * @param baudRate
     *         设置传输速率（波特率）
     */
    public void sendCKSC(byte baudRate) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCKSC(baudRate);
        }
    }

/*    public void sendCKSC(int ic, byte baudRate) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCKSC(ic, baudRate);
        }
    }*/

    /**
     * 发送定位申请指令（4.0）
     *
     * 例"0x24 0x44 0x57 0x53 0x51 0x00 0x16 0x00 0x00 0x00 0x04 0x00 0x00 0x00 0x00 0x00 0x00 0x00
     * 0x00 0x00 0x00 0x27"
     */
    public void sendDWSQ() {
        if (bdbleHandler != null) {
            bdbleHandler.sendDWSQ();
        }
    }

    /**
     * 发送通信申请信息（4.0）
     *
     * @param revIc
     *         接收卡号
     * @param msgType
     *         发送信息类型
     * @param msg
     *         发送内容
     *
     *         例:混发发送 “你好ABC123” "0x24 0x54 0x58 0x53 0x51 0x00 0x1D 0x00 0x00 0x00 0x46 0x03 0xC9
     *         0x30 0x00 0x0B 0x00 0xA4 0xC4 0xE3 0xBA 0xC3 0x41 0x42 0x43 0x31 0x32 0x33 0x0A "
     */
    public void sendTXSQ(int revIc, int msgType, String msg) {
        if (bdbleHandler != null) {
            bdbleHandler.sendTXSQ(revIc, msgType, msg);
        }
    }

    /**
     * 终端密码设置/登录(CCPWD)
     *
     * @param type
     *         类型 ‘1’:密码设置 ‘2’:登录
     * @param pwd
     *         密码，六位数字或者英文字母
     */
    public void sendCCPWD(String type, String pwd) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCPWD(type, pwd);
        }
    }

    /**
     * 终端自检输出设置(CCZDC)
     *
     * @param freq
     *         输出频度  ‘0’-‘10’ 单位：秒，为‘0’时表示单次输出
     */
    public void sendCCZDC(String freq) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCZDC(freq);
        }
    }


    /**
     * OK键设置和查询（CCOKS）
     */
    public void sendCCOKS(protocalEntity.CCOKSobj obj) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendCCOKS(obj);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 极限追踪模式设置/查询(CCZZM)
     */
    public void sendCCZZM(protocalEntity.CCZZMobj obj) {
        if (bdbleHandler != null) {
            bdbleHandler.sendCCZZM(obj);
        }
    }

    /**
     * SOS参数设置/查询(CCSHM)
     */
    public void sendCCSHM(protocalEntity.CCSHMobj obj) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendCCSHM(obj);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 北斗网到北斗网
     */
    public void sendBD2BD(String centerIc,String msg) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendBD2BD(centerIc,msg);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 北斗网到北斗网(带位置)
     */
    public void sendBD2BD_POS(String centerIc,String msg,RMCMsg pos) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendBD2BD_POS(centerIc,msg,pos);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 北斗网到公网
     */
    public void sendBD2NET(String centerIc,String userphone,String msg) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendBD2NET(centerIc,userphone,msg);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 北斗网到公网(带位置)
     */
    public void sendBD2NET_POS(String centerIc,String userphone,String msg,RMCMsg pos) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendBD2NET_POS(centerIc,userphone,msg,pos);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 北斗网到手机短信
     */
    public void sendBD2PHONE(String centerIc,String userphone,String msg) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendBD2PHONE(centerIc,userphone,msg);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 北斗网app位置上报
     */
    public void sendAppPos(String centerIc, List<RMCMsg> rmcMsgs) {
        if (bdbleHandler != null) {
            bdbleHandler.sendAppPos(centerIc,rmcMsgs);
        }
    }

    /**
     * app发送紧急报警
     *
     * @param centerIc
     * @param phone
     * @param status
     * @param msg
     * @param pos
     */
    public void sendAppSos(String centerIc, String phone, String status,String msg, RMCMsg pos) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendAppSos(centerIc, phone, status,msg, pos);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 北斗网app上报签到
     *
     * @param centerIc
     * @param msg
     * @param pos
     */
    public void sendAppOk(String centerIc, String msg, RMCMsg pos) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendAppOk(centerIc, msg, pos);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 北斗网到手机短信（带位置)
     */
    public void sendBD2PHONE_POS(String centerIc,String userphone,String msg,RMCMsg pos) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendBD2PHONE_POS(centerIc,userphone,msg,pos);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 北斗网登录
     */
    public void sendBDLOGIN(String centerIc,String loginType,String phone,String pw) {
        if (bdbleHandler != null) {
            try {
                bdbleHandler.sendBDLOGIN(centerIc,loginType,phone,pw);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onConnectBleSuccess() {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onConnectBleSuccess();

        }
    }

    @Override
    public void onDisConnectBleSuccess() {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onDisConnectBleSuccess();

        }
    }

    @Override
    public void onBDMSHReceived(BDMSH paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDMSH(paraMsg);

        }
    }

    @Override
    public void onBDQKXReceived(BDQKX paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDQKX(paraMsg);
        }
    }

    @Override
    public void onBDQDXReceived(BDQDX paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDQDX(paraMsg);
        }
    }

    @Override
    public void onBDQZXReceived(BDQZX paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDQZX(paraMsg);
        }
    }

    @Override
    public void onBDOKXReceived(protocalEntity.CCOKSobj paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDOKX(paraMsg);

        }
    }

    @Override
    public void onBDZZXReceived(protocalEntity.CCZZMobj paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDZZX(paraMsg);

        }
    }

    @Override
    public void onBDHMXReceived(protocalEntity.CCSHMobj paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDHMX(paraMsg);

        }
    }

    @Override
    public void onBDZDXReceived(protocalEntity.BDZDXobj paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDZDX(paraMsg);

        }
    }

    @Override
    public void onBDPWXReceived(protocalEntity.BDPWXobj paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDPWX(paraMsg);

        }
    }

    /**
     * 解析BDTXR（通信）信息
     * <p>将解析信息保存至BDTXRMessage对象并传给onBDTXR方法
     *
     * @param paramTXRmsg
     *         通信信息解析对象
     */
    @Override
    public void onBDTXRReceived(TXRMsg paramTXRmsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDTXR(paramTXRmsg);

        }
    }

    /**
     * 解析BDBSI（波束功率）信息
     * <p>将解析信息保存至BDBSIMessage对象并传给onBDBSI方法
     *
     * @param paramBSImsg
     *         功率信息解析对象
     */
    @Override
    public void onBDBSIReceived(BSIMsg paramBSImsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDBSI(paramBSImsg);
        }
    }

    /**
     * 解析BDICI（卡号）信息
     * <p>将解析信息保存至BDICIMessage对象并传给onBDICI方法
     *
     * @param paramICImsg
     *         IC信息解析对象
     */
    @Override
    public void onBDICIReceived(ICIMsg paramICImsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDICI(paramICImsg);
        }

    }

    /**
     * 解析BDFKI（反馈）信息
     * <p>将解析信息保存至BDFKIMessage对象并传给onBDFKI方法
     *
     * @param paramFKImsg
     *         反馈信息解析对象
     */
    @Override
    public void onBDFKIReceived(FKIMsg paramFKImsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDFKI(paramFKImsg);
        }
    }

    /**
     * 解析BDDWR（定位）信息
     * <p>将解析信息保存至BDDWRMessage对象并传给onBDDWR方法
     *
     * @param paramDWRmsg
     *         定位信息解析对象
     */
    @Override
    public void onBDDWRReceived(DWRMsg paramDWRmsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDDWR(paramDWRmsg);
        }
    }

    /**
     * 解析RMC（定位）信息
     * <p>将解析信息保存至RMCMessage对象并传给onRMC方法
     *
     * @param paramRMCmsg
     *         RMC定位信息解析对象
     */
    @Override
    public void onRMCReceived(RMCMsg paramRMCmsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onRMC(paramRMCmsg);
        }
    }

    @Override
    public void onGGAReceived(GGAMsg paramGGAmsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onGGA(paramGGAmsg);
        }
    }

    /**
     * 解析BDHZ自定义协议信息
     * <p>将解析信息保存至BDHZMessage对象并传给onBDHZ方法
     *
     * @param paramBDHZmsg
     *         北斗盒子信息解析对象
     */
    @Override
    public void onBDHZReceived(BDHZMsg paramBDHZmsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBDHZ(paramBDHZmsg);
        }
    }

    @Override
    public void onBDWAAeceived(WAAMsg paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onWAA(paraMsg);
        }
    }

    /**
     * 解析BBXX版本信息（4.0） 将解析信息保存至BBXXMsg对象并传给onBBXX方法
     *
     * @param paraMsg
     *         版本信息解析对象
     */
    @Override
    public void onBBXXReceived(BBXXMsg paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onBBXX(paraMsg);
        }
    }

    /**
     * 解析DWXX定位信息（4.0） 将解析信息保存至DWXXMsg对象并传给onDWXX方法
     *
     * @param paraMsg
     *         定位信息解析对象
     */
    @Override
    public void onDWXXReceived(DWXXMsg paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onDWXX(paraMsg);
        }
    }

    /**
     * 解析FKXX定位信息（4.0） 将解析信息保存至FKXXMsg对象并传给onFKXX方法
     *
     * @param paraMsg
     *         反馈信息解析对象
     */
    @Override
    public void onFKXXReceived(FKXXMsg paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onFKXX(paraMsg);
        }
    }

    /**
     * 解析GLZK功率检测信息（4.0） 将解析信息保存至GLZKMsg对象并传给onGLZK方法
     *
     * @param paraMsg
     *         功率检测信息解析对象
     */
    @Override
    public void onGLZKReceived(GLZKMsg paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onGLZK(paraMsg);
        }
    }

    /**
     * 解析ICXX ic检测信息（4.0） 将解析信息保存至ICXXMsg对象并传给onICXX方法
     *
     * @param paraMsg
     *         IC信息解析对象
     */
    @Override
    public void onICXXReceived(ICXXMsg paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onICXX(paraMsg);
        }
    }

    /**
     * 解析SJXX时间信息（4.0） 将解析信息保存至SJXXMsg对象并传给onSJXX方法
     *
     * @param paraMsg
     *         时间信息解析对象
     */
    @Override
    public void onSJXXReceived(SJXXMsg paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onSJXX(paraMsg);
        }
    }

    /**
     * 解析TXXX通信信息（4.0） 将解析信息保存至TXXXMsg对象并传给onTXXX方法
     *
     * @param paraMsg
     *         通信信息解析对象
     */
    @Override
    public void onTXXXReceived(TXXXMsg paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onTXXX(paraMsg);
        }
    }

    /**
     * 解析ZJXX自检信息（4.0） 将解析信息保存至ZJXXMsg对象并传给onZJXX方法
     *
     * @param paraMsg
     *         自检信息解析对象
     */
    @Override
    public void onZJXXReceived(ZJXXMsg paraMsg) {
        Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
            AgentListener localAgentListener = (AgentListener) localIterator
                    .next();
            localAgentListener.onZJXX(paraMsg);
        }
    }

    @Override
    public void onBDERROR(byte[] parambyte) {

/*		Iterator localIterator = agentListeners.iterator();
        while (localIterator.hasNext()) {
			AgentListener localAgentListener = (AgentListener) localIterator
					.next();
			localAgentListener.onBDERR();
		}	*/
    }


    @Override
    public void onGSVReceived(byte[] parambyte) {
/*		GSVMessage paramGSVmsg = new GSVMessage(parambyte);
        Iterator localIterator = agentListeners.iterator();
		while (localIterator.hasNext()) {
			AgentListener localAgentListener = (AgentListener) localIterator
					.next();
			localAgentListener.onGSV(paramGSVmsg);
		}*/
    }

    /**
     * 通过蓝牙发送任意自定义内容
     *
     * @param sendMsg
     *         发送内容
     */
    public void send(byte[] sendMsg) {
        if (bdbleHandler != null) {
            bdbleHandler.send(sendMsg);
        }
    }

}

