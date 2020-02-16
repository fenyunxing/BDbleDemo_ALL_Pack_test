package com.bdblesdk.impl;

import com.bddomain.models.entity.protocal2_1.BSIMsg;
import com.bddomain.models.entity.protocal2_1.DWRMsg;
import com.bddomain.models.entity.protocal2_1.FKIMsg;
import com.bddomain.models.entity.protocal2_1.ICIMsg;
import com.bddomain.models.entity.protocal2_1.RMCMsg;
import com.bddomain.models.entity.protocal2_1.TXRMsg;
import com.bddomain.models.entity.protocal2_1.WAAMsg;
import com.bddomain.models.entity.protocalBDHZ.BDHZMsg;

/**
 * 蓝牙监听接口类
 *
 * Created by admin on 2016/10/21.
 * Modified by admin on 2017/04/07
 */
public abstract interface BDBLEListener {

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
}
