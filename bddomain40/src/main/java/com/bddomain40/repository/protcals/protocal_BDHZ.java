package com.bddomain40.repository.protcals;

import com.bddomain40.repository.tools.BDMethod;

import java.io.UnsupportedEncodingException;

/**
 * 北斗盒子自定协议类
 * Created by admin on 2016/11/22.
 */
public class protocal_BDHZ {

    public static String gen_dljc() {
        String result = "$BDHZ,DLJC,1*24\r\n";
        return result;
    }

    public static String gen_ztjc() {
        String result = "$BDHZ,ZTJC,1*22\r\n";
        return result;
    }

    /**
     * 组装设置命令(自定义命令)
     *
     * @param freq 频度
     * @param IC   SOS上报卡号
     * @return
     */
    public static String gen_set(String freq, String IC) {

        String cmd = "BDHZ,SET,1," + freq + "," + IC + ",0";
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 检测是否为包含$BDHZ头部 （北斗盒子自定协议）
     *
     * @param buf 接收到的指数据字节串
     * @return 包含true 不包含false
     */
    public static boolean isHead_BDHZ(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x48 && buf[4] == 0x5A) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 组装打开/关闭SOS功能命令(自定义命令)
     *
     * @param IC
     * @return
     */
    public static String gen_opnsos(String IC,boolean ifOpen,String sosContent) {

        String cmd = "BDHZ,SOS,1," + (ifOpen?1:0) + ",60," + IC + "," +sosContent;
        byte[] tmp = new byte[0];
        try {
            tmp = cmd.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }


}
