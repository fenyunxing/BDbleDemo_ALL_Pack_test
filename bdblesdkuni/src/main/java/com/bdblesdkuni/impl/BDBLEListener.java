package com.bdblesdkuni.impl;


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

/**
 * 蓝牙监听接口类
 *
 * Created by admin on 2016/10/21.
 * Modified by admin on 2017/04/07
 */
public abstract interface BDBLEListener {


    public abstract void onConnectBleSuccess();

    public abstract void onDisConnectBleSuccess();

    //北斗盒子

    /**
     * 接收到盒子的工作模式信息
     * @param paraMsg
     */
    public abstract void onBDMSHReceived(BDMSH paraMsg);


    /**
     * 接收到盒子OK启动信息
     * @param paraMsg
     */
    public abstract void onBDQKXReceived(BDQKX paraMsg);


    /**
     * 接收到盒子SOS启动信息
     * @param paraMsg
     */
    public abstract void onBDQDXReceived(BDQDX paraMsg);



    /**
     * 接收到盒子极限追踪
     * @param paraMsg
     */
    public abstract void onBDQZXReceived(BDQZX paraMsg);

    /**
     * 接收到OKX
     * 修改日期 2019/11/15
     */
    public abstract void onBDOKXReceived(protocalEntity.CCOKSobj paraMsg);

    /**
     * 接收到ZZX
     * 修改日期 2019/11/15
     */
    public abstract void onBDZZXReceived(protocalEntity.CCZZMobj paraMsg);

    /**
     * 接收到HMX
     * 修改日期 2019/11/15
     */
    public abstract void onBDHMXReceived(protocalEntity.CCSHMobj paraMsg);


    /**
     * 接收到BDZDX
     * 修改日期 2019/11/15
     */
    public abstract void onBDZDXReceived(protocalEntity.BDZDXobj paraMsg);

    /**
     * 接收到BDPWX
     * 修改日期 2019/11/15
     */
    public abstract void onBDPWXReceived(protocalEntity.BDPWXobj paraMsg);


    // 2.1协议

    /**
     * 接收通信信息方法（2.1）
     * 修改日期 2017/4/7
     *
     * @param paraMsg
     *         通信信息对象
     */
    public abstract void onBDTXRReceived(TXRMsg paraMsg);

    /**
     * 接收定位信息方法（2.1）
     * 修改日期 2017/4/7
     *
     * @param paraMsg
     *         定位信息对象
     */
    public abstract void onBDDWRReceived(DWRMsg paraMsg);

    /**
     * 接收功率检测方法（2.1）
     * 修改日期 2017/4/7
     *
     * @param paraMsg
     *         功率检测对象
     */
    public abstract void onBDBSIReceived(BSIMsg paraMsg);

    /**
     * 接收IC检测方法（2.1）
     * 修改日期 2017/4/7
     *
     * @param paraMsg
     *         IC检测对象
     */
    public abstract void onBDICIReceived(ICIMsg paraMsg);

    /**
     * 接收反馈信息方法（2.1）
     * 修改日期 2017/4/7
     *
     * @param paraMsg
     *         反馈信息对象
     */
    public abstract void onBDFKIReceived(FKIMsg paraMsg);

    /**
     * 接收错误信息方法（2.1）
     *
     * @param parambyte
     *         反馈信息字节串
     */
    public abstract void onBDERROR(byte[] parambyte);

    /**
     * 接收--GSV方法（二代模块协议）
     *
     * @param parambyte
     *         GSV信息字节串
     */
    public abstract void onGSVReceived(byte[] parambyte);

    /**
     * 接收--RMC方法（二代模块协议）
     * 修改日期 2017/4/7
     *
     * @param paraMsg
     *         RMC信息对象
     */
    public abstract void onRMCReceived(RMCMsg paraMsg);

    /**
     * 接收--GGA方法（二代模块协议）
     * 修改日期 2018/3/27
     *
     * @param paraMsg
     *         RMC信息对象
     */
    public abstract void onGGAReceived(GGAMsg paraMsg);

    /**
     * 接收BDHZ信息方法（北斗盒子自定义协议）
     * 修改日期 2017/4/7
     *
     * @param paraMsg
     *         BDHZ信息对象
     */
    public abstract void onBDHZReceived(BDHZMsg paraMsg);


    /**
     * 接收--WAA信息方法（2.1协议）
     * 修改日期 2017/5/25
     *
     * @param paraMsg
     *        WAA信息对象
     */
    public abstract void onBDWAAeceived(WAAMsg paraMsg);

    /**
     * 接收BBXX信息（4.0）
     *
     * @param paraMsg
     *         版本信息对象
     */
    public abstract void onBBXXReceived(BBXXMsg paraMsg);

    /**
     * 接收DWXX信息（4.0）
     *
     * @param paraMsg
     *         定位信息对象
     */
    public abstract void onDWXXReceived(DWXXMsg paraMsg);

    /**
     * 接收FKXX信息（4.0）
     *
     * @param paraMsg
     *         反馈信息对象
     */
    public abstract void onFKXXReceived(FKXXMsg paraMsg);

    /**
     * 接收GLZK信息（4.0）
     *
     * @param paraMsg
     *         功率检测对象
     */
    public abstract void onGLZKReceived(GLZKMsg paraMsg);

    /**
     * 接收ICXX信息（4.0）
     *
     * @param paraMsg
     *         IC信息对象
     */
    public abstract void onICXXReceived(ICXXMsg paraMsg);

    /**
     * 接收SJXX信息（4.0）
     *
     * @param paraMsg
     *         时间信息对象
     */
    public abstract void onSJXXReceived(SJXXMsg paraMsg);

    /**
     * 接收TXXX信息（4.0）
     *
     * @param paraMsg
     *         通信信息对象
     */
    public abstract void onTXXXReceived(TXXXMsg paraMsg);

    /**
     * 接收ZJXX信息（4.0）
     *
     * @param paraMsg
     *         自检信息对象
     */
    public abstract void onZJXXReceived(ZJXXMsg paraMsg);
}
