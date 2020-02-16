package com.bddomain.models.entity.protocal2_1;


import com.bddomain.models.CheckImpl;
import com.bddomain.repository.tools.BDMethod;

/**
 * 2.1协议功率信息类
 *
 * Created by admin on 2016/11/22.
 */
public class BSIMsg implements CheckImpl {
    private String Responsebeam;//响应波束号
    private String Timedifbeam;//时差波束号

    private static String bpow1;
    private static String bpow2;
    private static String bpow3;
    private static String bpow4;
    private static String bpow5;
    private static String bpow6;
    private static String bpow7;
    private static String bpow8;
    private static String bpow9;
    private static String bpow10;
    private String bsidata;
    private boolean Ifvaild = false;

    public BSIMsg() {
    }

    /**
     * 构造函数
     * 直接解析BSI字节串
     *
     * @param parambytes
     *         BSI字节串
     */
    public BSIMsg(byte[] parambytes) {
        bsidata = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(bsidata);
        if (Ifvaild) {
            String[] items = bsidata.split(",");
            if (items.length > 12) {
                setResponsebeam(items[1]);
                setTimedifbeam(items[2]);
                setBeamPow(items[3], items[4], items[5], items[6], items[7], items[8], items[9], items[10], items[11], items[12].substring(0, items[12].indexOf("*")));
            }
        } else {
            setResponsebeam("00");
            setTimedifbeam("00");
            setBeamPow("0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
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
     * 设置响应波束号
     *
     * @param paramStr
     *         功率信息字节数组
     */
    public void setResponsebeam(String paramStr) {
        this.Responsebeam = paramStr;
    }

    /**
     * 设置时差波束号
     *
     * @param paramStr
     *         功率信息字节数组
     */
    public void setTimedifbeam(String paramStr) {
        this.Timedifbeam = paramStr;
    }

    /**
     * 获取响应波束值
     *
     * @return 响应波束值字符串
     */
    public String getResponsebeam() {
        return Responsebeam;
    }

    /**
     * 获取时差波束值
     *
     * @return 时差波束值字符串
     */
    public String getTimedifbeam() {
        return Timedifbeam;
    }

    /**
     * 设置波束值
     * b1~b10 功率信息
     */
    public void setBeamPow(String b1, String b2, String b3, String b4, String b5, String b6, String b7, String b8, String b9,
                           String b10) {
        bpow1 = b1;
        bpow2 = b2;
        bpow3 = b3;
        bpow4 = b4;
        bpow5 = b5;
        bpow6 = b6;
        bpow7 = b7;
        bpow8 = b8;
        bpow9 = b9;
        bpow10 = b10;
    }

    /**
     * 获取波束1的值
     *
     * @return 波束1数值字符串
     */
    public String getBeamPow1() {
        return bpow1;
    }

    /**
     * 获取波束2的值
     *
     * @return 波束2数值字符串
     */
    public String getBeamPow2() {
        return bpow2;
    }

    /**
     * 获取波束3的值
     *
     * @return 波束3数值字符串
     */
    public String getBeamPow3() {
        return bpow3;
    }

    /**
     * 获取波束4的值
     *
     * @return 波束4数值字符串
     */
    public String getBeamPow4() {
        return bpow4;
    }

    /**
     * 获取波束5的值
     *
     * @return 波束5数值字符串
     */
    public String getBeamPow5() {
        return bpow5;
    }

    /**
     * 获取波束6的值
     *
     * @return 波束6数值字符串
     */
    public String getBeamPow6() {
        return bpow6;
    }

    /**
     * 获取波束7的值
     *
     * @return 波束7数值字符串
     */
    public String getBeamPow7() {
        return bpow7;
    }

    /**
     * 获取波束8的值
     *
     * @return 波束8数值字符串
     */
    public String getBeamPow8() {
        return bpow8;
    }

    /**
     * 获取波束9的值
     *
     * @return 波束9数值字符串
     */
    public String getBeamPow9() {
        return bpow9;
    }

    /**
     * 获取波束10的值
     *
     * @return 波束10数值字符串
     */
    public String getBeamPow10() {
        return bpow10;
    }

    /**
     * 获取BSI协议原始信息
     *
     * @return BSI协议原始信息
     */
    public String getBsiStr() {
        return bsidata.substring(0,bsidata.indexOf("*")+3);
    }

    /**
     * 设置BSI协议原始信息
     *
     * @param bsidata BSI协议原始信息
     */
   /* private void setBsidata(String bsidata) {
        try {
            this.bsidata = bsidata.substring(0,bsidata.indexOf("*")+2);
        } catch (Exception e) {
            Log.v("FDBDErrorLog","bsidata设置原始数据出错");
        }
    }*/
}
