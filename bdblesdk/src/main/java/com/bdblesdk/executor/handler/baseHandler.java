package com.bdblesdk.executor.handler;

import com.bdblesdk.impl.BDBLEListener;
import com.bddomain.models.entity.protocal2_1.BSIMsg;
import com.bddomain.models.entity.protocal2_1.DWRMsg;
import com.bddomain.models.entity.protocal2_1.FKIMsg;
import com.bddomain.models.entity.protocal2_1.ICIMsg;
import com.bddomain.models.entity.protocal2_1.RMCMsg;
import com.bddomain.models.entity.protocal2_1.TXRMsg;
import com.bddomain.models.entity.protocal2_1.WAAMsg;
import com.bddomain.models.entity.protocalBDHZ.BDHZMsg;
import com.bddomain.repository.protcals.protocal2_1;
import com.bddomain.repository.protcals.protocal_BDHZ;

import java.io.UnsupportedEncodingException;

/**
 * Handler基类
 *
 * Created by admin on 2016/10/21.
 * Modified by admin on 2017/5/13
 */
public abstract class baseHandler {

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
    protected static class HeightFlag {
        public static final String HIGHT_USER = "H"; //高空
        public static final String COMMON_USER = "L"; // 普通
    }

    /**
     * 测高方式
     */
    protected static class CheckHighType {
        public static final int HAVE_HIGH_VALUE = 0; //有高程
        public static final int HAVE_NO_HIGH_VALUE = 1; //无高程
        public static final int HAVE_CHECK_HIGH_1 = 2; // 测高1
        public static final int HAVE_CHECK_HIGH_2 = 3; // 测高2
    }

    /**
     * 定位类型
     */
    protected static class LocationType {
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
     * 发送通信申请     *
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
     * 发送位置2报告申请
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
        send(protocal2_1.gen_ccpwd(type,pwd).getBytes());
    }

    /**
     * 终端自检输出设置(CCZDC)
     *
     * @param freq  输出频度  ‘0’-‘10’ 单位：秒，为‘0’时表示单次输出
     */
    protected void sendCCZDC(String freq) {
        send(protocal2_1.gen_cczdc(freq).getBytes());
    }

    public byte[] onMessageReceivered(byte[] paramByte) {
        if (protocal2_1.isBDHead_BDICI(paramByte)) {
            ICIMsg iciMsg = new ICIMsg(paramByte);
            if (iciMsg.getVaild()) {
                this.bdbleListener.onBDICIReceived(iciMsg);
            }
        } else if (protocal2_1.isBDHead_BDBSI(paramByte)) {
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
        }
        return paramByte;
    }

}
