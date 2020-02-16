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
 *  接收接口类
 * 可得到解析后的接收数据
 *
 * Created by admin on 2016/10/21.
 */
public abstract interface AgentListener {
    /**
     * 接收通信信息接口
     * @param paramTXRmsg 通信信息解析对象
     */
    public abstract void onBDTXR(TXRMsg paramTXRmsg);

    /**
     * 接收功率信息
     * @param paramMsg 功率信息解析对象
     */
    public abstract void onBDBSI(BSIMsg paramMsg);

    /**
     * 接收IC信息
     * @param paramMsg IC信息解析对象
     */
    public abstract void onBDICI(ICIMsg paramMsg);

    /**
     * 接收反馈信息
     * @param paramMsg 反馈信息解析对象
     */
    public abstract void onBDFKI(FKIMsg paramMsg);

    /**
     * 接收定位信息
     * @param paramMsg 定位信息解析对象
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
     * @param paraMsg RMC信息解析对象
     */
    public abstract void onRMC(RMCMsg paraMsg);

    /**
     * 接收BDHZ信息
     * @param paraMsg BDHZ信息解析对象
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
}

