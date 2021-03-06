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
 * 接收接口类
 * 可得到解析后的接收数据
 *
 * Created by admin on 2016/10/21.
 */
public abstract interface AgentListener {

    //北斗盒子

    public abstract void onConnectBleSuccess();

    public abstract void onDisConnectBleSuccess();

    /**
     * 接收到盒子的工作模式信息
     * @param paraMsg
     */
    public abstract void onBDMSH(BDMSH paraMsg);

    /**
     * 接收到盒子OK启动信息
     * @param paraMsg
     */
    public abstract void onBDQKX(BDQKX paraMsg);


    /**
     * 接收到盒子SOS启动信息
     * @param paraMsg
     */
    public abstract void onBDQDX(BDQDX paraMsg);



    /**
     * 接收到盒子极限追踪
     * @param paraMsg
     */
    public abstract void onBDQZX(BDQZX paraMsg);


    /**
     * 接收到ZDX
     * 修改日期 2019/11/15
     */
    public abstract void onBDZDX(protocalEntity.BDZDXobj paraMsg);

    /**
     * 接收到PWX
     * 修改日期 2019/11/15
     */
    public abstract void onBDPWX(protocalEntity.BDPWXobj paraMsg);

    /**
     * 接收到OKX
     * 修改日期 2019/11/15
     */
    public abstract void onBDOKX(protocalEntity.CCOKSobj paraMsg);

    /**
     * 接收到ZZX
     * 修改日期 2019/11/15
     */
    public abstract void onBDZZX(protocalEntity.CCZZMobj paraMsg);

    /**
     * 接收到HMX
     * 修改日期 2019/11/15
     */
    public abstract void onBDHMX(protocalEntity.CCSHMobj paraMsg);

    /**
     * 接收通信信息接口
     *
     * @param paramTXRmsg
     *         通信信息解析对象
     */
    public abstract void onBDTXR(TXRMsg paramTXRmsg);

    /**
     * 接收功率信息
     *
     * @param paramMsg
     *         功率信息解析对象
     */
    public abstract void onBDBSI(BSIMsg paramMsg);

    /**
     * 接收IC信息
     *
     * @param paramMsg
     *         IC信息解析对象
     */
    public abstract void onBDICI(ICIMsg paramMsg);

    /**
     * 接收反馈信息
     *
     * @param paramMsg
     *         反馈信息解析对象
     */
    public abstract void onBDFKI(FKIMsg paramMsg);

    /**
     * 接收定位信息
     *
     * @param paramMsg
     *         定位信息解析对象
     */
    public abstract void onBDDWR(DWRMsg paramMsg);

    /**
     * 接收错误信息，即不在协议范围内的信息
     */
    public abstract void onBDERR();

    /**
     * 接收GSV信息
     * @param paraMsg GSV信息解析对象
     */
   /* public abstract void onGSV(GSVMessage paraMsg);*/

    /**
     * 接收RMC信息
     *
     * @param paraMsg
     *         RMC信息解析对象
     */
    public abstract void onRMC(RMCMsg paraMsg);

    /**
     * 接收GGA信息
     *
     * @param paraMsg
     *         GGA信息解析对象
     */
    public abstract void onGGA(GGAMsg paraMsg);

    /**
     * 接收BDHZ信息
     *
     * @param paraMsg
     *         BDHZ信息解析对象
     */
    public abstract void onBDHZ(BDHZMsg paraMsg);


    /**
     * 接收--WAA信息方法（2.1协议）
     * 修改日期 2017/5/25
     *
     * @param paraMsg
     *        WAA信息对象
     */
    public abstract void onWAA(WAAMsg paraMsg);

    /**
     * 接收BBXX信息（4.0）
     *
     * @param paraMsg
     *         版本信息对象
     */
    public abstract void onBBXX(BBXXMsg paraMsg);

    /**
     * 接收DWXX信息（4.0）
     *
     * @param paraMsg
     *         定位信息对象
     */
    public abstract void onDWXX(DWXXMsg paraMsg);

    /**
     * 接收FKXX信息（4.0）
     *
     * @param paraMsg
     *         反馈信息对象
     */
    public abstract void onFKXX(FKXXMsg paraMsg);

    /**
     * 接收GLZK信息（4.0）
     *
     * @param paraMsg
     *         功率检测对象
     */
    public abstract void onGLZK(GLZKMsg paraMsg);

    /**
     * 接收ICXX信息（4.0）
     *
     * @param paraMsg
     *         IC信息对象
     */
    public abstract void onICXX(ICXXMsg paraMsg);

    /**
     * 接收SJXX信息（4.0）
     *
     * @param paraMsg
     *         时间信息对象
     */
    public abstract void onSJXX(SJXXMsg paraMsg);

    /**
     * 接收TXXX信息（4.0）
     *
     * @param paraMsg
     *         通信信息对象
     */
    public abstract void onTXXX(TXXXMsg paraMsg);

    /**
     * 接收ZJXX信息（4.0）
     *
     * @param paraMsg
     *         自检信息对象
     */
    public abstract void onZJXX(ZJXXMsg paraMsg);

}

