package com.bddomainuni.models.entity.protocal2_1;


import com.bddomainuni.models.CheckImpl;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * 2.1协议反馈信息类
 *
 * Created by admin on 2016/10/20.
 */
public class FKIMsg implements CheckImpl {
    private String comStr;// 指令名称
    private boolean comExeSituation = false;// 指令执行情况
    private boolean freqSetIdication = false;// 频度设置指示
    private String txSuppressInd;// 发射抑制指示
    private String waitTimeMin;// 等待时间（分）
    private String waitTimeSec;// 等待时间（秒）
    private String fkidata;
    private boolean Ifvaild = false;

    public FKIMsg() {
    }

    /**
     * 构造函数
     * 直接解析FKI字节串
     *
     * @param parambytes
     *         FKI字节串
     */
    public FKIMsg(byte[] parambytes) {
        fkidata = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(fkidata);
        if (Ifvaild) {
            String[] items = fkidata.split(",");
            if (items.length > 5) {
                setComStr(items[1]);
                setComExeSituation(items[2]);
                setFreqSetIdication(items[3]);
                setTxSuppressInd(items[4]);
                setWaitTime(items[5]);
            }
        } else {
            setComStr("XXX");
            setComExeSituation("N");
            setFreqSetIdication("N");
            setTxSuppressInd("0");
            setWaitTime("0000");
        }

    }

    /**
     * 检测命令是否有效
     *
     * @return 有效标志位
     */
    public boolean getVaild() {
        return Ifvaild;
    }

    /**
     * 获取该反馈信息的指令源
     *
     * @return 指令名称字符串
     */
    public String getComStr() {
        return comStr;
    }

    /**
     * 设置反馈信息指令源名称
     *
     * @param paramStr
     *         反馈信息字节数组
     */
    public void setComStr(String paramStr) {
        this.comStr = paramStr;
    }

    /**
     * 获取指令执行情况
     *
     * @return 指令执行成功则返回true, 否则返回false
     */
    public boolean getComExeSituation() {

        return comExeSituation;
    }

    /**
     * 设置指令执行情况
     *
     * @param paramStr
     *         反馈信息字节数组
     */
    public void setComExeSituation(String paramStr) {

        if (paramStr.equals("Y")) {
            comExeSituation = true;
        } else {
            comExeSituation = false;
        }

    }

    /**
     * 获取频度设置指示
     *
     * @return 频度设置成功返回true, 否则返回false
     */
    public boolean getFreqSetIdication() {
        return freqSetIdication;
    }

    /**
     * 设置频度设置指示
     *
     * @param paramStr
     *         反馈信息字节数组
     */
    public void setFreqSetIdication(String paramStr) {

        if (paramStr.equals("Y")) {
            freqSetIdication = true;
        } else {
            freqSetIdication = false;
        }

    }

    /**
     * 获取发射抑制指示
     *
     * @return 发射抑制指示字符串
     */
    public String getTxSuppressInd() {
        return txSuppressInd;
    }

    /**
     * 设置发射抑制指示
     *
     * @param paramStr
     *         反馈信息字节数组
     */
    public void setTxSuppressInd(String paramStr) {
        switch (paramStr) {
            case "0":
                txSuppressInd = "发射抑制解除";
                break;
            case "1":
                txSuppressInd = "接收系统抑制指令，发射被抑制";
                break;
            case "2":
                txSuppressInd = "电量不足，发射被抑制";
                break;
            case "3":
                txSuppressInd = "无线电静默，发设被抑制";
                break;
            default:
                txSuppressInd = "发射抑制解除";
                break;
        }
    }

    /**
     * 获得等待时间(分)
     *
     * @return 等待时间
     */
    public String getWaitTimeMin() {
        return waitTimeMin;
    }

    /**
     * 获得等待时间(秒)
     *
     * @return 等待时间
     */
    public String getWaitTimeSec() {
        return waitTimeSec;
    }

    /**
     * 设置等待时间
     *
     * @param paramStr
     */
    public void setWaitTime(String paramStr) {
        waitTimeMin = paramStr.substring(0, 2);
        waitTimeSec = paramStr.substring(2, 4);
    }

    /**
     * 获取FKI原始协议数据
     *
     * @return FKI原始协议数据
     */
    public String getFkidata() {
        return fkidata.substring(0,fkidata.indexOf("*")+3);
    }

    /**
     * 设置FKI原始协议数据
     *
     * @param fkidata FKI 原始协议数据
     */
   /* private void setFkidata(String fkidata) {
        try {
            this.fkidata = fkidata.substring(0,fkidata.indexOf("*")+2);
        } catch (Exception e) {
            Log.v("FDBDErrorLog","fkidata设置原始数据出错");
        }
    }*/
}
