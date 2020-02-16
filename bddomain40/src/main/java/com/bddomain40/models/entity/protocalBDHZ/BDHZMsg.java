package com.bddomain40.models.entity.protocalBDHZ;

import android.util.Log;

import com.bddomain40.models.CheckImpl;
import com.bddomain40.repository.tools.BDMethod;


/**
 * 北斗盒子终端信息类
 *
 * Created by admin on 2016/10/20.
 */
public class BDHZMsg implements CheckImpl {

    public final static String BDHZ_STYLE_DLFK = "DLFK";
    public final static String BDHZ_STYLE_SOS = "SOS";
    public final static String BDHZ_STYLE_SET = "SET";
    public final static String BDHZ_STYLE_CHECK = "CHECK";

    private static String bdhzData = "00";
    private static String pwrnumStr = "0";
    private static String SOSnum = "0000000";
    private boolean ifOpenSos = false;
    private static String SetStatus = "0";
    private String checkRst = "A";
    private String checkTyp = "X";
    private boolean IfVaild = false;

    private String BDHZMsgStyle = " ";

    public BDHZMsg() {
    }

    /**
     * 构造函数
     * 直接解析北斗盒子专用协议数据
     *
     * @param parambytes 北斗盒子专用协议数据字节串
     */
    public BDHZMsg(byte[] parambytes) {
        bdhzData = new String(parambytes);
        Log.v("FDBDChatTest","BDHZMsg ===>" + bdhzData);
        IfVaild = BDMethod.CheckCKS(bdhzData);
        if(IfVaild){
            String[] items = bdhzData.split(",");
            if (items.length > 3) {
                setBDHZMsgStyle(items[1]);
                if (items[1].equals("DLFK")) {
                    if (items[3].length() > 0) {
                        pwrnumStr = items[3].substring(0, items[3].indexOf("*"));
                    }
                } else if (items[1].equals("SOS")) {
                    /*if (items[4].length() > 3) {
                        SOSnum = items[4].substring(0, items[4].indexOf("*"));
                    }*/
                    //todo 开启新SOS功能
                    if (items[3].length() > 0) {
                        setIfOpenSos(items[3].equals("1")?true:false);
                    }
                    if (items[5].length() > 3) {
                        SOSnum = items[5];
                    }
                }else if (items[1].equals("SET")) {
                    if (items.length > 5 && items[5].length() > 0) {
                        SetStatus = items[5].substring(0, items[5].indexOf("*"));
                    }
                }else if (items[1].equals("CHECK")) {
                    if (items.length > 4 &&items[4].length() > 3) {
                        checkRst = items[4].substring(0, items[4].indexOf("*"));
                        checkTyp = items[3];
                    }
                }
            }
        }else{
            Log.v("FDBDChatTest","BDHZMsg ====验证失败===");
            pwrnumStr = "0";
            SOSnum = "0000000";
            SetStatus = "V";
            checkRst = "V";
            checkTyp = "XXX";
        }

    }
    /**
     * 检测命令是否有效
     * @return 有效标志位
     */
    public boolean getVaild(){
        return IfVaild;
    }

    /**
     * 获取盒子电量信息
     */
    public String getPwrnumStr() {
        return pwrnumStr;
    }

    /**
     * 获取北斗盒子保存的SOS上报地址
     */
    public String getSOSnumStr() {
        return SOSnum;
    }
    /**
     * 获取北斗盒子设置的反馈状态
     */
    public String getSetStatus() {
        return SetStatus;
    }

    /**
     * 获取北斗盒子校验接收指令结果反馈
     */
    public String getcheckRst() {
        return checkRst;
    }

    /**
     *获取北斗盒子接收校验的指令类型
     */
    public String getCheckTyp() {
        return checkTyp;
    }

    /**
     *获取北斗盒子专用指令类型
     */
    public String getBDHZMsgStyle() {
        return BDHZMsgStyle;
    }

    /**
     * 设置盒子指令类型
     * @param BDHZMsgStyle
     */
    private void setBDHZMsgStyle(String BDHZMsgStyle) {
        this.BDHZMsgStyle = BDHZMsgStyle;
    }

    /**
     *获取盒子当前的SOS状态
     * @return
     */
    public boolean isIfOpenSos() {
        return ifOpenSos;
    }

    /**
     * 设置盒子当前SOS状态
     */
    private void setIfOpenSos(boolean ifOpenSos) {
        this.ifOpenSos = ifOpenSos;
    }
}
