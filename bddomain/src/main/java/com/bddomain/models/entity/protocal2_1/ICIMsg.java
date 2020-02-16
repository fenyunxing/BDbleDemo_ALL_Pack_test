package com.bddomain.models.entity.protocal2_1;


import com.bddomain.models.CheckImpl;
import com.bddomain.repository.tools.BDMethod;

/**
 * 2.1协议IC信息类
 *
 * Created by admin on 2016/10/20.
 * Modified by admin on 2017/5/13
 */
public class ICIMsg implements CheckImpl {
    private int usernum;
    private int cardnum;
    private int rtnwitch;
    private String ic;
    private String icdata;
    private String castAddress;
    private String freq;
    private String level;
    private boolean Ifvaild = false;

    public ICIMsg() {
    }

    /**
     * 构造函数
     * 直接解析ICI字节串
     *
     * @param parambytes
     *         ICI字节串
     */
    public ICIMsg(byte[] parambytes) {
        icdata = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(icdata);
        if (Ifvaild) {
            String[] items = icdata.split(",");
            String head = icdata.substring(3, 6);
            if (head.equals("ICI")) {
                ic = items[1];
                castAddress = items[3];
                freq = items[5];
                level = items[6];
            }
        } else {
            ic = "0000000";
            castAddress = "00";
            freq = "00";
            level = "0";
        }

    }

    /**
     * 设置通播地址
     *
     * @param castAddress
     */
    public void setCastAddress(String castAddress) {
        this.castAddress = castAddress;
    }

    /**
     * 设置北斗卡对应的短报文服务频度
     *
     * @param freq 服务频度
     */
    public void setFreq(String freq) {
        this.freq = freq;
    }

    /**
     * 设置北斗卡服务等级
     *
     * @param level 服务等级
     */
    public void setLevel(String level) {
        this.level = level;
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
     * 获取出下属用户数量
     *
     * @return 用户数量
     */
    public int getUsernum() {

        return usernum;
    }

    /**
     * 设置出下属用户数量
     *
     * @param usernum
     *         下属用户数量
     */
    public void setUsernum(int usernum) {

        this.usernum = usernum;

    }
    /*public void setUsernum(byte[] parambyte) {

        usernum = Integer.valueOf(
                new String(BDMethod.subBytes(parambyte, 7, 2))).intValue();

    }*/

    /**
     * 设置指定的IC卡号（存在多个下属用户情况）
     *
     * @param ic
     *         IC信息字节设置
     * @param cardnum
     *         包含的卡号数量
     * @param rtnwitch
     *         返回第几个卡号
     */
    public void setICnum(String ic, int cardnum, int rtnwitch) {
        this.ic = ic;
        this.cardnum = cardnum;
        this.rtnwitch = rtnwitch;
    }

    /**
     * 设置IC卡号（只有一个用户情况）
     *
     * @param ic
     *         北斗卡号
     */
    void setICnum(String ic) {

        this.ic = ic;
    }


    /**
     * 设置指定的IC卡号（存在多个下属用户情况）
     *
     * @param parambyte
     *            IC信息字节设置
     * @param cardnum
     *            包含的卡号数量
     * @param rtnwitch
     *            返回第几个卡号
     */
    /*public void setICnum(byte[] parambyte, int cardnum, int rtnwitch) {

        int begin;
        begin = 10 + (rtnwitch - 1) * 8;
        this.ic = new String(BDMethod.subBytes(parambyte, begin, 7));

    }*/

    /**
     * 设置IC卡号（只有一个用户情况）
     *
     * @param parambyte
     *            IC信息字节设置
     *
     */
     /*void setICnum(byte[] parambyte) {

        this.ic = new String(BDMethod.subBytes(parambyte, 7, 7));
    }*/

    /**
     * 获取IC卡号
     *
     * @return IC卡号字符串
     */
    public String getICnum() {
        return this.ic;
    }

    /**
     * 获取通播地址
     *
     * @return 通播地址字符串
     */
    public String getCastAddress() {
        return castAddress;
    }

    /**
     * 获取服务频度
     *
     * @return 服务频度字符串
     */
    public String getFreq() {
        return freq;
    }

    /**
     * 获取北斗卡服务等级
     *
     * @return 北斗卡服务等级
     */
    public String getLevel() {
        return level;
    }

    /**
     * 获取原始IC信息
     *
     * @return 原始ICI协议信息
     */
    public String getIciStr() {
        return icdata.substring(0, icdata.indexOf("*") + 3);
    }



    /**
     * 设置原始ICI协议信息
     *
     * @param icdata 原始ICI信息
     */
    /*private void setIcdata(String icdata) {
        try {
            this.icdata = icdata.substring(0,icdata.indexOf("*")+2);
        } catch (Exception e) {
            Log.v("FDBDErrorLog","icdata设置原始数据出错");
        }
    }*/
}
